import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class RemDup extends LfObject
{
	public RemDup(Atom[] args)
	{
		version = 0.1f;
		build = "05/02/09";
		INLET_ASSIST = new String[] { "List input" };
		OUTLET_ASSIST = new String[] { "List output" };
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };

		init();
	}
	
	public void list(Atom[] atoms_in)
	{
		Vector<String> input = new Vector<String>();
		int i = 0;
		while(i < atoms_in.length)
		{
			if(input.contains(atoms_in[i].toString()))
				atoms_in = Atom.removeOne(atoms_in, i);
			else {
				input.add(atoms_in[i].toString());
				i++;
			}
		}	
		outlet(0, atoms_in);
	}
	
	public void anything(String message, Atom[] atoms_in)
	{
		list(concat(message, atoms_in));
	}
}
