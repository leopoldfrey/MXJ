import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class SymbolSplit extends LfObject
{
	public SymbolSplit(Atom[] atoms)
	{
		version = 0.1f;
		build = "30/10/10";
		INLET_ASSIST = new String[] { "Symbol to be splitted." };
		OUTLET_ASSIST = new String[] { "Symbol begin", "Symbol end" };
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		init();
	}

	public void anything(String message, Atom[] atoms)
	{
		int split = 0;
		if(atoms.length >= 1)
			split = atoms[0].toInt();
		if(split == 0 || message.length() <= split)
		{
			outlet(0, message);
		} else {
			outlet(1, message.substring(split+1));
			outlet(0, message.substring(0, split));
		}
	}
}
