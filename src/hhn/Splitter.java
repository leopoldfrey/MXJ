package hhn;
import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;


// FIXME Splitter
public class Splitter extends LfObject
{
	public Splitter()
	{
		version = 0.1f;
		build = "24/04/09";
		INLET_ASSIST = new String[] { "" };
		OUTLET_ASSIST = new String[] { "" };
		INLET_TYPES = new int[] { DataTypes.ALL, };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };

		init();
	}
	
	public void anything(String message, Atom[] args)
	{
		String res[] = message.split("/", 2);
		switch (res.length)
		{
		case 2:
			outlet(1,Atom.newAtom(res[0]));
			outlet(0,Atom.newAtom(res[1]));
			break;
		case 1:
			outlet(1,Atom.newAtom(res[0]));
			outlet(0,Atom.newAtom(0));
			break;
		case 0:
			return;
		}
	}
}
