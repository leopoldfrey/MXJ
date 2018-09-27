import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxPatcher;

import lf.LfObject;

public class PName extends LfObject
{
	public PName(Atom[] atoms)
	{
		version = 0.1f;
		build = "07/03/07";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "Bang to get the patcher's name" };
		OUTLET_ASSIST = new String[] { "Name of the patcher" };
		
		init(0,false,atoms);
	}
	
	public void loadbang()
	{
		bang();
	}
	
	public void bang()
	{
		MaxPatcher p = getParentPatcher();
		outlet(0,Atom.parse("Name "+p.getName()+" Class "+p.getClass()+" BP? "+p.isBPatcher()+" PMC "+p.getParentMaxClass()));
	}
}
