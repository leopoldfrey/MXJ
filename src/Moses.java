import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;
import lf.util.FloatVectorSort;

public class Moses extends LfObject
{
	FloatVectorSort moses;
	float lvalue = 0;

	public Moses(Atom[] atoms)
	{
		version = 0.1f;
		build = "8/12/08";
		INLET_ASSIST = new String[] { "Float to be treated", "List of moses values" };
		OUTLET_ASSIST = new String[] { "value prepended by ", "" };
		INLET_TYPES = new int[] { DataTypes.FLOAT, DataTypes.LIST };
		OUTLET_TYPES = new int[] { DataTypes.LIST };
		init(0, false, atoms);
		moses = new FloatVectorSort();
	}

	public void moses(Atom[] atoms)
	{
		for (Atom a : atoms)
			if (a.isFloat())
				moses.add(a.getFloat());
	}

	public void addmoses(float f)
	{
		moses.add(f);
	}

	public void bang()
	{
		process();
	}

	public void inlet(float f)
	{
		lvalue = f;
		bang();
	}

	private void process()
	{
		if (moses.isEmpty())
		{
			outlet(0, lvalue);
			return;
		}
		float tmp = 0;
		for (float f : moses)
		{
			if (lvalue < f)
			{
				outlet(0, tmp);
				return;
			}
			tmp = f;
		}
	}
}
