import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxBox;
import com.cycling74.max.MaxPatcher;
import com.cycling74.max.MaxSystem;

import lf.LfMaxWindow;
import lf.LfObject;

// TODO PATCHUTIL > � faire en C

public class PatchUtil extends LfObject
{
	private static Vector<LfMaxWindow> regwin = new Vector<LfMaxWindow>();
	private Vector<LfMaxWindow> myregwin = new Vector<LfMaxWindow>();
	private LfMaxWindow window;
	private int width, height, x, y;
	private boolean force_unvisible = false;
	private boolean local = true;
	private boolean digdeeper = false;
	private String findme = "";
	
	public PatchUtil(Atom[] atoms)
	{
		version = 0.2f;
		build = "20/10/2008";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] {};
		INLET_ASSIST = new String[] { "Patch Control Messages (see help file)"};
		OUTLET_ASSIST = new String[] {};

		init();
		
		declareAttribute("force_unvisible");
		declareAttribute("local");
		declareAttribute("findme");
		declareAttribute("digdeeper");
	}
		
	public void loadbang()
	{
		MaxPatcher pp = this.getParentPatcher();
//		System.out.println("parent patcher :"+(pp != null ? pp.getName() : "NULL"));
		window = new LfMaxWindow("controler", this.getParentPatcher().getWindow());
		if(!regwin.contains(window))
			regwin.add(window);
	}
	
	public void grow(boolean g)
	{
		if(myregwin.isEmpty())
			window.setGrow(g);
		else
			for(LfMaxWindow w : myregwin)
				w.setGrow(g);
	}
	
	public void floating(boolean f)
	{
		if(myregwin.isEmpty())
			window.setFloat(f);
		else
			for(LfMaxWindow w : myregwin)
				w.setFloat(f);
	}
	
	public void close(boolean c)
	{
		if(myregwin.isEmpty())
			window.setClose(c);
		else
			for(LfMaxWindow w : myregwin)
				w.setClose(c);
	}
	
	public void dirty(boolean d)
	{
		if(myregwin.isEmpty())
			window.setDirty(d);
		else
			for(LfMaxWindow w : myregwin)
				w.setDirty(d);
	}
	
	public void bar(boolean b)
	{
		if(myregwin.isEmpty())
			window.setTitleBar(b);
		else
			for(LfMaxWindow w : myregwin)
				w.setTitleBar(b);
	}
	
	public void zoom(boolean z)
	{
		if(myregwin.isEmpty())
			window.setZoom(z);
		else 
			for(LfMaxWindow w : myregwin)
				w.setZoom(z);
	}
	
	public void size(int _w,int _h)
	{
		if(myregwin.isEmpty())
		{
			width = _w;
			height = _h;
			window.setSize(width,height);
		}
		else
			for(LfMaxWindow w : myregwin)
				w.setSize(_w,_h);
	}
	
	public void width(int _w)
	{
		if(myregwin.isEmpty())
		{
			width = _w;
			window.setWidth(width);
		}
		else
			for(LfMaxWindow w : myregwin)
				w.setWidth(_w);
	}
	
	public void height(int _h)
	{
		if(myregwin.isEmpty())
		{
			height = _h;
			window.setHeight(height);
		}
		else
			for(LfMaxWindow w : myregwin)
				w.setHeight(_h);
	}
	
	public void location(int _x, int _y)
	{
		if(myregwin.isEmpty())
		{
			x = _x;
			y = _y;
			window.setLocation(x,y);
		}
		else
			for(LfMaxWindow w : myregwin)
				w.setLocation(_x,_y);
	}
	
	public void x(int _x)
	{
		if(myregwin.isEmpty())
		{
			x = _x;
			window.setX(x);
		}
		else
			for(LfMaxWindow w : myregwin)
				w.setX(_x);
	}
	
	public void y(int _y)
	{
		if(myregwin.isEmpty())
		{
			y = _y;
			window.setY(y);
		}
		else
			for(LfMaxWindow w : myregwin)
				w.setY(_y);
	}
	
	public void title(String s)
	{
		if(myregwin.isEmpty())
			window.setTitle(s);
		else
			for(LfMaxWindow w : myregwin)
				w.setTitle(s);
	}
	
	public void restoretitle()
	{
		if(myregwin.isEmpty())
			window.restoreTitle();
		else
			for(LfMaxWindow w : myregwin)
				w.restoreTitle();
	}
	
	public void restore()
	{
		if(myregwin.isEmpty())
			window.restore();
		else
			for(LfMaxWindow w : myregwin)
				w.restore();
	}

	public void brgb(Atom[] atoms)
	{
		int R, G, B;
		R = G = B = 0;
		switch(atoms.length)
		{
		case 3:
			B = atoms[2].toInt();
		case 2:
			G = atoms[1].toInt();
		case 1:
			R = atoms[0].toInt();
		}
		if(myregwin.isEmpty())
			window.setBackGround(R, G, B);
		else
			for(LfMaxWindow w : myregwin)
				w.setBackGround(R, G, B);
	}
	
	public void notifyDeleted()
	{
		myregwin.clear();
		regwin.remove(window);
	}
	
	public void unvisible()
	{
		if(!max5 && !force_unvisible)
		{
			error("PatchUtil : for stability reasons, \"visible\" is only available in Max5");
			return;
		}
		if(myregwin.isEmpty())
			window.setVisible(false);
		else
			for(LfMaxWindow w : myregwin)
				w.setVisible(false);
	}
	
	public void visible()
	{
		if(!max5 && !force_unvisible)
		{
			error("PatchUtil : for stability reasons, \"visible\" is only available in Max5");
			return;
		}
		if(myregwin.isEmpty())
			window.setVisible(true);
		else
			for(LfMaxWindow w : myregwin)
				w.setVisible(true);
	}
	
	public void allvisible()
	{
		if(!max5)
		{
			error("PatchUtil : for stability reasons, \"unvisible\" is only available in Max5");
			return;
		}
		for(LfMaxWindow w : regwin)
			w.setVisible(true);
	}
	
	public void refer(Atom[] atoms)
	{
		if(atoms.length == 0)
			myregwin.clear();
		else {
			String objname;
			LfMaxWindow w;
			for(Atom a : atoms)
			{
				objname = a.toString();
				w = getWindowNamedDeep(objname);
				if(w == null)
					error("PatchUtil : could not find patcher named : \""+objname+"\".");
				else if(myregwin.contains(w))
					error("PatchUtil : \""+objname+"\" already registered.");
				else
					myregwin.add(w);
			}
		}
	}
	
	public void unrefer(Atom[] atoms)
	{
		if(atoms.length == 0)
			myregwin.clear();
		else {
			LfMaxWindow w;
			for(Atom a : atoms)
			{
				int i = 0;
				boolean found = false;
				while(i < myregwin.size() && !found)
				{
					w = myregwin.get(i);
					if(w.getName().equalsIgnoreCase(a.toString()))
					{
						found = true;
						myregwin.remove(w);
					}
					i++;
				}
			}
		}
	}
	
//	private LfMaxWindow getWindowNamed(String name)
//	{
//		MaxBox o = null;
//		if(local)
//		{
//			o = this.getParentPatcher().getNamedBox(name);
//		} else {
//			PatchUtil ob;
//			Set s = getContext().getAllObjects();
//			Iterator i = s.iterator();
//			boolean found = false;
//			while(i.hasNext() && !found)
//			{
//				Map.Entry e = (Map.Entry)i.next();
//				if(e.getKey().getClass().getName().equalsIgnoreCase("PatchUtil"))
//				{
//					ob = (PatchUtil)e.getValue();
//					if(ob.findme.equalsIgnoreCase(name))
//						return new LfMaxWindow(name, ob.getParentPatcher().getWindow());
//					o = ob.getParentPatcher().getNamedBox(name);
//					if(o != null)
//						found = true;
//				}
//			}
//		}
//		
//		if(o != null)
//			if((o.getMaxClass().equalsIgnoreCase("patcher") || o.getMaxClass().equalsIgnoreCase("jpatcher")))
//				return new LfMaxWindow(name, o.getSubPatcher().getWindow());
//		return null;
//	}	
	
	private LfMaxWindow getWindowNamedDeep(String name)
	{
		MaxBox o = null;
		if(local)
			return getWindowNamedFromMaxPatcher(getParentPatcher(), name);
		
		PatchUtil ob;
		Set s = getContext().getAllObjects();
		Iterator i = s.iterator();
		LfMaxWindow w = null;
		boolean found = false;
		while(i.hasNext() && !found)
		{
			Map.Entry e = (Map.Entry)i.next();
			debug("In context : "+e.getKey().getClass().getName());
			if(e.getKey().getClass().getName().equalsIgnoreCase("PatchUtil"))
			{
				ob = (PatchUtil)e.getValue();
				debug("PatchUtil : "+ob.toString());
				if(ob.findme.equalsIgnoreCase(name))
					return new LfMaxWindow(name, ob.getParentPatcher().getWindow());
				debug("Here : "+ob.toString());
				w = getWindowNamedFromMaxPatcher(ob.getParentPatcher(), name);
				if(w != null)
					found = true;
			}
		}
	
		return w;
	}
	
	private LfMaxWindow getWindowNamedFromMaxPatcher(MaxPatcher p, String name)
	{
		LfMaxWindow w = null;
		debug("looking for \""+name+"\" in "+(p != null ? p.getName() : "NULL"));
		if(p != null)
		{
			MaxBox b = p.getNamedBox(name);
			if(b != null && isPatcher(b))
			{
				debug('\t'+b.getName());
				return new LfMaxWindow(name, b.getSubPatcher().getWindow());
			}
			debug("\t"+name+" not found ! ");
			if(digdeeper)
			{				
				debug("\t\tDigging deeper");
				MaxBox[] boArray = p.getAllBoxes();
				int i = 0;
				while(i < boArray.length)
				{
					b = boArray[i];
					debug("\t\t\t"+b.getName()+" "+isPatcher(b));
					if(isPatcher(b))
					{
						w = getWindowNamedFromMaxPatcher(b.getSubPatcher(),name);
						if(w != null)
						{
							debug("\t\t\t\tFound : "+w.getTitle());
							return w;
						}
					}
					i++;
				}
			}
		}
		return w;
	}
	
	public void fullscreen(Atom[] atoms)
	{
		boolean fullscreen;
		switch(atoms.length)
		{
		case 1:
			fullscreen = atoms[0].toBoolean();
			if(myregwin.isEmpty())
				window.setFullscreen(fullscreen);
			else
				for(LfMaxWindow w : myregwin)
					w.setFullscreen(fullscreen);
			break;
		case 2:
			fullscreen = atoms[0].toBoolean();
			if(myregwin.isEmpty())
				window.setFullscreen(fullscreen);
			else
				for(LfMaxWindow w : myregwin)
					w.setFullscreen(fullscreen);
			menu(atoms[1].toBoolean() ? !fullscreen : fullscreen);
			break;
		}
	}
	
	public void menu(boolean m)
	{
		MaxSystem.sendMessageToBoundObject("max",m ?  "showmenubar" : "hidemenubar",new Atom[]{});
	}
	
	public void cursor(boolean c)
	{
		if(c)
			MaxSystem.showCursor();
		else
			MaxSystem.hideCursor();
	}
	
	public void distributeX(int dX)
	{
		int XX = myregwin.firstElement().getX();
		for(LfMaxWindow w : myregwin)
		{
			w.setX(XX);
			XX = XX + w.getWidth() + dX;
		}
	}
	
	public void distributeY(int dY)
	{
		int YY = myregwin.firstElement().getY();
		for(LfMaxWindow w : myregwin)
		{
			w.setY(YY);
			YY += w.getHeight() + dY;
		}
	}
	
	public void usage()
	{
	}

	public void info()
	{
	}

	public void state()
	{
	}

}