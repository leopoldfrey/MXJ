import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class Prepend extends LfObject {

	Vector<Atom> v;
	Vector<Atom> out;
	
	public Prepend(Atom[] atoms)
	{
		version = 0.1f;
		build = "24/04/16";
		INLET_ASSIST = new String[] { "", "" };
		OUTLET_ASSIST = new String[] { "" };
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		init(0, false, atoms);
		v = new Vector<Atom>();
		out = new Vector<Atom>();
		for(Atom a : atoms)
			v.add(a);
	}
	
	public void _set(Atom[] atoms)
	{
		v.clear();
		for(Atom a : atoms)
			v.add(a);
	}
	
	public void _clear()
	{
		v.clear();
	}
	
	public void anything(String message, Atom[] atoms)
	{
		if(getInlet() == 0)
		{
			out.clear();
			out.addAll(v);
			out.add(Atom.newAtom(message));
			for(Atom a : atoms)
				out.add(a);
			
			Atom[] oo = new Atom[v.size()+1+atoms.length];
			int i = 0;
			for(Atom a : out)
			{
				oo[i] = a;
				i++;
			}
			outlet(0,oo);
		} else {
			v.clear();
			v.add(Atom.newAtom(message));
			for(Atom a : atoms)
				v.add(a);
		}
	}
	
	public void list(Atom[] atoms)
	{
		if(getInlet() == 0)
		{
			out.clear();
			out.addAll(v);
			for(Atom a : atoms)
				out.add(a);
			
			Atom[] oo = new Atom[v.size()+atoms.length];
			int i = 0;
			for(Atom a : out)
			{
				oo[i] = a;
				i++;
			}
			outlet(0,oo);
		} else {
			v.clear();
			for(Atom a : atoms)
				v.add(a);	
		}
	}
	
	public void inlet(int ii)
	{
		if(getInlet() == 0)
		{
			out.clear();
			out.addAll(v);
			out.add(Atom.newAtom(ii));
			
			Atom[] oo = new Atom[v.size()+1];
			int i = 0;
			for(Atom a : out)
			{
				oo[i] = a;
				i++;
			}
			outlet(0,oo);
		} else {
			v.clear();
			v.add(Atom.newAtom(ii));
		}
	}
	
	public void inlet(float f)
	{
		if(getInlet() == 0)
		{
			out.clear();
			out.addAll(v);
			out.add(Atom.newAtom(f));
			
			Atom[] oo = new Atom[v.size()+1];
			int i = 0;
			for(Atom a : out)
			{
				oo[i] = a;
				i++;
			}
			outlet(0,oo);
		} else {
			v.clear();
			v.add(Atom.newAtom(f));	
		}
	}
}
