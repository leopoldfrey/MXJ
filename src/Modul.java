import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class Modul extends LfObject
{
	private float mod = 1;
	private float toMod = 0;

	public Modul(Atom[] atoms)
	{
		version = 0.2f;
		build = "18/10/2008";
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.INT };
		OUTLET_TYPES = new int[] { DataTypes.FLOAT, DataTypes.INT };
		INLET_ASSIST = new String[] { "(a) Number.", "(n) Modulus." };
		OUTLET_ASSIST = new String[] { "(b = a[n]) Remainder." , "Reports modulus value." };

		init(0, false, atoms);
		
		if (atoms.length > 0)
			setMod(atoms[0].toFloat());
		declareAttribute("mod", "getMod", "setMod");
	}

	public void inlet(int i)
	{
		inlet((float)i);
	}

	public void inlet(float f)
	{
		if (getInlet() == 0)
		{
			toMod = f;
			bang();
		}
		else
		{
			setMod(f);
		}
	}

	public void bang()
	{
		outlet(0, modulof2(toMod, mod));
		outlet(1, mod);
	}

	protected float getMod()
	{
		return mod;
	}

	protected void setMod(float _mod)
	{
		this.mod = _mod != 0 ? _mod : 1;
		mod = Math.abs(mod);
		debug("Modulus set to "+this.mod);
	}

	public void usage()
	{
		post(ppp+"Usage:\n"+ppp+"   Optional argument : modulus mod \'n\'");
	}

	public void info()
	{
		post(ppp+"Info:\n"+ppp+"   Finds the remainder of division of \'a\' by \'n\'.\n"+ppp+"   Wikipedia: Two integers a, b are said to be congruent modulo n if their difference is a multiple of n");
	}
	
	public void state()
	{
		post(ppp+"State:\n"+ppp+"   Modulus set to "+this.mod);
		outlet(1,this.mod);
	}
}
