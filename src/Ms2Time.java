import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;
import lf.util.Formatter;

public class Ms2Time extends LfObject {

	public static Formatter f2 = new Formatter(2,2,0,0,false);
	private static Formatter f3 = new Formatter(3,3,0,0,false);
	public static Formatter f4 = new Formatter(2,5,2,2,false);
	private static Formatter f5 = new Formatter(1,1,0,0,false);
	private static Formatter f6 = new Formatter(4,2,0,0,false);

	public static int max(int a, int b)
	{
		return a >= b ? a : b;
	}

	public static int clipL(int value, int lowlim)
	{
		return max(value, lowlim);
	}

	public Ms2Time(Atom[] atoms) 
	{
		version = 0.1f;
		build = "7/12/12";
		INLET_ASSIST = new String[] { "Time in ms" };
		OUTLET_ASSIST = new String[] { "String Time in hh:mm:ss:ms" };
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		init(0, false, atoms);
	}
	
	public void inlet(int ms)
	{
		ms = clipL(ms,0);
		int h = (int)(ms/3600000);
		int m = (int)((ms-h*3600000)/60000);
		int s = (int)((ms-h*3600000-m*60000)/1000);
		int u = (int)(ms-h*3600000-m*60000-s*1000)/100;
		outlet(0, Atom.newAtom(f2.format(h)+":"+f2.format(m)+":"+f2.format(s)+"'"+f5.format(u)));
	}
}
