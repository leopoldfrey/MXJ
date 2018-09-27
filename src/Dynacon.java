import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxBox;
import com.cycling74.max.MaxPatcher;
import com.cycling74.max.MaxSystem;

import lf.LfObject;

// TODO DYNACON Revoir l'ensemble

public class Dynacon extends LfObject
{
	MaxPatcher thispatcher;
	MaxBox input;
	MaxBox output;
	Vector<Fx> fxs;
	int anchorx = 0;
	int anchory = 0;
	int defwidth = 50;
	int defheight = 50;
	int deltay = 0;
	static int id = 0;
	
	public Dynacon(Atom atoms[])
	{
		version = 0.1f;
		build = "21/03/08";
		INLET_ASSIST = new String[] { "Instructions in", "Presets in" };
		OUTLET_ASSIST = new String[] { "Instructions out", "Presets out" };
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };

		init(0,false,atoms);
		
		switch(atoms.length)
		{
		case 5 :
			deltay = atoms[4].toInt();
		case 4 :
			defheight = atoms[3].toInt();
			defwidth = atoms[2].toInt();
		case 2 :
			anchory = atoms[1].toInt();
			anchorx = atoms[0].toInt();
		default :
			break ;
		}
		thispatcher = this.getParentPatcher();
		fxs = new Vector<Fx>();
		clear();
	}
	
	public void clear()
	{
		for(Fx f : fxs)
			f.mb.remove();
		fxs.clear();
		input = thispatcher.getNamedBox("input");
		output = thispatcher.getNamedBox("output");
		thispatcher.connect(input, 0, output, 0);
	}
	
	public void add(Atom atoms[])
	{
		String fxname;
		switch(atoms.length)
		{
		case 3 :
			defheight = atoms[2].toInt();
			defwidth = atoms[1].toInt();
		case 1 :
			fxname = atoms[0].toString();
			break ;
		default :
			error("Missing arguments...");
			return ;
		}
		
		id++;
		if(fxs.isEmpty())
		{
			MaxBox newmb = thispatcher.newObject("bpatcher", Atom.parse(anchorx+" "+anchory+" "+defwidth+" "+defheight+" 0 0 "+fxname+" 0 "+id));
			thispatcher.disconnect(input, 0, output, 0);
			MaxSystem.sendMessageToBoundObject(id+"pos", "int", Atom.newAtom(new int[]{fxs.size()+1}));
			fxs.add(new Fx(newmb,id));
			thispatcher.connect(input, 0, newmb, 0);
			thispatcher.connect(newmb, 0, output, 0);
		} else {
			MaxBox lastmb = fxs.lastElement().mb;
			MaxBox newmb = thispatcher.newObject("bpatcher", Atom.parse(anchorx+" "+(lastmb.getRect()[3]+deltay)+" "+defwidth+" "+defheight+" 0 0 "+fxname+" 0 "+id));
			thispatcher.disconnect(lastmb, 0, output, 0);
			MaxSystem.sendMessageToBoundObject(id+"pos", "int", Atom.newAtom(new int[]{fxs.size()+1}));
			fxs.add(new Fx(newmb,id));
			thispatcher.connect(lastmb, 0, newmb, 0);
			thispatcher.connect(newmb, 0, output, 0);
		}
	}
	
	public void insert(Atom atoms[])
	{
		String fxname;
		int num;
		switch(atoms.length)
		{
		case 4 :
			defheight = atoms[3].toInt();
			defwidth = atoms[2].toInt();
		case 2 :
			fxname = atoms[1].toString();
			num = atoms[0].toInt();
			break ;
		default :
			error("Missing arguments...");
			return ;
		}

		if(num <= fxs.size())
		{
			id++;
			int vecpos = num - 1;
			MaxBox newmb = thispatcher.newObject("bpatcher", Atom.parse(anchorx + " " + anchory + " " + defwidth + " " + defheight + " 0 0 " + fxname + " 0 "+id));
			MaxSystem.sendMessageToBoundObject(id+"pos", "int", Atom.newAtom(new int[]{num}));
			fxs.insertElementAt(new Fx(newmb,id), vecpos);
			if (fxs.size() > 1)
			{
				if (vecpos == 0)
				{
					Fx next = fxs.get(vecpos+1);
					MaxBox nextmb = next.mb;
					thispatcher.disconnect(input, 0, nextmb, 0);
					thispatcher.connect(input, 0, newmb, 0);
					thispatcher.connect(newmb, 0, nextmb, 0);
					for (int i = vecpos+1; i < fxs.size(); i++)
					{
						Fx mv = fxs.get(i);
						MaxSystem.sendMessageToBoundObject(mv.id + "pos", "int", Atom.newAtom(new int[] { i + 1 }));
						displace(mv.mb, 0, defheight + deltay);
					}					
				} else {
					Fx prev = fxs.get(vecpos-1);
					Fx next = fxs.get(vecpos+1);
					MaxBox prevmb = prev.mb;
					MaxBox nextmb = next.mb;
					thispatcher.disconnect(prevmb, 0, nextmb, 0);
					thispatcher.connect(prevmb, 0, newmb, 0);
					thispatcher.connect(newmb, 0, nextmb, 0);
//					int sumh = deltay;
//					for (int i = 0; i < vecpos; i++)
//					{
//						MaxBox m = fxs.get(i).mb;
//						int mr[] = m.getRect();
//						sumh += (mr[3] - mr[1] + deltay);
//					}
					moveto(newmb, newmb.getRect()[0], prevmb.getRect()[3]+deltay);
					for (int i = vecpos+1; i < fxs.size(); i++)
					{
						Fx mv = fxs.get(i);
						MaxSystem.sendMessageToBoundObject(mv.id + "pos", "int", Atom.newAtom(new int[] { i + 1 }));
						displace(mv.mb, 0, defheight + deltay);
					}
				}
			}
			else
			{
				thispatcher.connect(input, 0, newmb, 0);
				thispatcher.connect(newmb, 0, output, 0);
			}
		} else 
			add(Atom.parse(fxname+" "+defwidth+" "+defheight));
		
		
	}
	
	public void replace(Atom atoms[])
	{
		// TODO DYNACON replace	
	}
	
	public void remove(int i)
	{
		// TODO DYNACON remove
//		if(i >= 0 && i < fxs.size())
//		{
//			fxs.remove(i-1).mb.remove();
//			thispatcher.connect(fxs.get(i-1).mb, 0, fxs.get(i).mb, 0);
//		}
	}
	
	public void monitor(Atom atoms[])
	{
		// TODO DYNACON monitor
		
	}
	
	private void displace(MaxBox mb, int x, int y)
	{
		int rect[] = mb.getRect();
		mb.setRect(rect[0]+x, rect[1]+y, rect[2]+x, rect[3]+y);
	}
	
	private void moveto(MaxBox mb, int x, int y)
	{
		int rect[] = mb.getRect();
		int w = rect[2] - rect[0];
		int h = rect[3] - rect[1];
		mb.setRect(x,y,x+w,y+h);
	}
	
	public void displace(String mbname, int x, int y)
	{
		MaxBox mb = thispatcher.getNamedBox(mbname);
		if(mb != null)
			displace(mb, x, y);
	}
	// TODO DYNACON preset functions
}

class Fx
{
	MaxBox mb;
	int id;
	
	public Fx(MaxBox mb, int id)
	{
		this.mb = mb;
		this.id = id;
	}
}
