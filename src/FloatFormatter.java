import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;
import lf.util.Formatter;

public class FloatFormatter extends LfObject
{
	private int max_right = -1;
	private int min_right = -1;
	private int max_left = -1;
	private int min_left = -1;
	private String tmp;
	private int tmp_sign;
	private Formatter rf;

	public FloatFormatter(Atom[] atoms)
	{
		version = 0.2f;
		build = "20/10/2008";
		INLET_TYPES = new int[]{ DataTypes.ALL, DataTypes.INT, DataTypes.INT, DataTypes.INT, DataTypes.INT };
		OUTLET_TYPES = new int[]{ DataTypes.FLOAT, DataTypes.MESSAGE };
		INLET_ASSIST = new String[]{ "Float to be formatted.", "Max Right Digits", "Min Right Digits", "Max Left Digits", "Min Left Digits" };
		OUTLET_ASSIST = new String[]{ "Formatted float.", "Formatted string." };
		
		init(0,false,atoms);
		
		rf = new Formatter();
		extractList(atoms);
		
		declareAttribute("max_right", "getMax_right", "setMax_right");
		declareAttribute("min_right", "getMin_right", "setMin_right");
		declareAttribute("max_left", "getMax_left", "setMax_left");
		declareAttribute("min_left", "getMin_left", "setMin_left");
	}

	private void extractList(Atom[] atoms)
	{
		switch (atoms.length)
		{
		case 4:
			setMin_left(atoms[3].getInt());
		case 3:
			setMax_left(atoms[2].getInt());
		case 2:
			setMin_right(atoms[1].getInt());
		case 1:
			setMax_right(atoms[0].getInt());
			break;
		}
	}

	public void bang()
	{
		try
		{
			outlet(0, tmp_sign * new Float(tmp).floatValue());
		}
		catch (NumberFormatException e)
		{
			debug(e);
		}
		outlet(1, (tmp_sign == -1 ? "-" : "") +tmp);
	}

	public void inlet(float f)
	{
		if (getInlet() == 0)
		{
			tmp_sign = sign(f);
			f *= tmp_sign;
			if (f - (int) f == 0 && max_left != -1)
			{
				f = modulo((int) f, (int) Math.pow(10, max_left));
			}
			tmp = rf.format(abs(f));
			bang();
		}
	}

	public void inlet(int i)
	{
		if (getInlet() == 0)
			inlet((float) i);
		else if (getInlet() == 1)
			setMax_right(i);
		else if (getInlet() == 2)
			setMin_right(i);
		else if (getInlet() == 3)
			setMax_left(i);
		else if (getInlet() == 4)
			setMin_left(i);
	}

	public void list(Atom[] atoms)
	{
		if (getInlet() == 0)
		{
			extractList(atoms);
		}
	}

	protected int getMax_left()
	{
		return max_left;
	}

	protected void setMax_left(int max_left)
	{
		this.max_left = clip(max_left, min_left);
		rf.max_left = this.max_left;
		debug("Max Left : "+this.max_left);
	}

	protected int getMax_right()
	{
		return max_right;
	}

	protected void setMax_right(int max_right)
	{
		this.max_right = clip(max_right, min_right);
		rf.max_right = this.max_right;
		debug("Max Right : "+this.max_right);
	}

	protected int getMin_left()
	{
		return min_left;
	}

	protected void setMin_left(int min_left)
	{
		this.min_left = clip(min_left, -1, max_left);
		rf.min_left = this.min_left;
		debug("Min Left : "+this.min_left);
	}

	protected int getMin_right()
	{
		return min_right;
	}

	protected void setMin_right(int min_right)
	{
		this.min_right = clip(min_right, -1, max_right);
		rf.min_right = this.min_right;
		debug("Min Right : "+this.min_right);
	}

	public void usage()
	{
		post(ppp+"Usage:\n"+ppp+"   Optional argument : max_right, min_right, max_left, min_left");
	}

	public void info()
	{
		post(ppp+"Info:\n"+ppp+"   Format the float in input with the corresponding constraints.\n"
				+ppp+"   max/min right digits, max/min left digits.");
	}
	
	public void state()
	{
		post(ppp+"State:\n"+ppp+"   MR:"+max_right+" mR:"+min_right+" ML:"+max_left+" mL:"+min_left);
	}
}
