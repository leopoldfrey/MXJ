import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class Xsel extends LfObject
{
	Vector<Atom> match_v = new Vector<Atom>();
	Atom last_in = Atom.newAtom("bang");
	
	public Xsel(Atom atoms[])
	{
		version = 0.2f;
		build = "11/06/07";
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		INLET_ASSIST = new String[] { "Input to be Tested", "Set Multiple Items to match" };
		OUTLET_ASSIST = new String[] { "Input if input matches", "Input if input doesn't match" };
		init(0,false,atoms);
		set_match(atoms);
	}
	
	public void bang()
	{
		if(match_v.contains(last_in))
			outlet(0, last_in);
		else
			outlet(1, last_in);
	}
	
	public void anything(String msg, Atom[] args)
	{
		switch(getInlet())
		{
		case 0:
			match(msg,args);
			break;
		case 1:
			set_match(msg, args);
			break;
		}
	}
	
	private void match(String msg, Atom[] args)
	{
		if((msg.equalsIgnoreCase("list") || msg.equalsIgnoreCase("int") || msg.equalsIgnoreCase("float")) && args.length > 0)
			last_in = args[0];
		else
			last_in = Atom.newAtom(msg);
		
		bang();
	}
	
	private void set_match(String msg, Atom[] args)
	{
		match_v.clear();
		if(!(msg.equalsIgnoreCase("list") || msg.equalsIgnoreCase("int") || msg.equalsIgnoreCase("float")))
			match_v.add(Atom.newAtom(msg));
		set_match(args);
	}
	
	private void set_match(Atom[] args)
	{
		for(Atom a:args)
			match_v.add(a);
	}
}
