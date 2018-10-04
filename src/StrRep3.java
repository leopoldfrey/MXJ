import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class StrRep3 extends LfObject {

	public StrRep3(Atom[] atoms)
	{
		version = 0.1f;
		build = "14/01/18";
		INLET_ASSIST = new String[] { "String to be changed." };
		OUTLET_ASSIST = new String[] { "Result String." };
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };

		init(0,false,atoms);
	}

	public void anything(String message, Atom[] atoms)
	{
		String result = "";
		if (message != null && !message.equalsIgnoreCase(""))
		{
			if (atoms != null && !message.equalsIgnoreCase(""))
			{
				if(atoms.length == 0)
					result = message;
				else
					result = message.concat(" " + Atom.toOneString(atoms));
				outlet(0, result.toLowerCase().replaceAll(" ", "_"));
			}
			
		}
	}
}
