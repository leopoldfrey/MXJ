import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class UserName extends LfObject
{
	String name;
	
	UserName(Atom[] atoms)
	{
		version = 0.3f;
		build = "02/03/07";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "bang" };
		OUTLET_ASSIST = new String[] { "username" };
		init(0,true,atoms);
		
		name = System.getProperties().getProperty("user.name");
	}

	public void loadbang()
	{
		bang();
	}
	
	public void bang()
	{
		outlet(0,Atom.newAtom(name));
	}
}
