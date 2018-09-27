import java.awt.Color;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class RGBToHex extends LfObject
{
	private int red = 0;
	private int green = 0;
	private int blue = 0;

	public RGBToHex(Atom[] atoms)
	{
		version = 0.1f;
		build = "02/04/06";
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.INT, DataTypes.INT };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "red value or RGB list (int 0-255) / inv HexaColor (Symbol) / bang", "green value (int 0-255)", "blue value (int 0-255)" };
		OUTLET_ASSIST = new String[] { "Result String : Hexadecimal Color." };

		declareAttribute("red","getRed","setRed");
		declareAttribute("green","getGreen","setGreen");
		declareAttribute("blue","getBlue","setBlue");

		init(0, false, atoms);

		if (atoms.length == 3)
		{
			list(atoms);
		}
	}

	public void bang()
	{
		outlet(0, RGBToH(new Color(red, green, blue)));
	}

	public static String RGBToH(Color c)
	{
		String s = Integer.toHexString(c.getRGB() & 0xffffff);
		if (s.length() < 6)
		{
			s = "000000".substring(0, 6 - s.length()) + s;
		}
		return s.toUpperCase();
	}

	public void inlet(int i)
	{
		int in = getInlet();
		switch (in)
		{
		case 0:
			setRed(i);
			bang();
			break;
		case 1:
			setGreen(i);
			break;
		case 2:
			setBlue(i);
			break;
		default:
			break;
		}
	}

	public void list(Atom[] atoms)
	{
		if (atoms.length >= 3)
		{
			setRed(atoms[0].toInt());
			setGreen(atoms[1].getInt());
			setBlue(atoms[2].getInt());
			bang();
		}
	}

	public void inv(String c)
	{
		int r, g, b;
		r = Integer.parseInt(c.substring(0, 2), 16);
		g = Integer.parseInt(c.substring(2, 4), 16);
		b = Integer.parseInt(c.substring(4), 16);
		int[] l = { r, g, b };
		outlet(0, l);
	}

	public void usage()
	{
		post(ppp+"Usage:\n"+ppp+"   Optional arguments : red green blue (0-255)\n"
				+ppp+"   RGB list > hexadecimal value\n"
				+ppp+"   inv hexadecimal value > RGB list");
	}

	public void info()
	{
		post(ppp+"Info:\n"+ppp+"   Returns the Hexadecimal RGB value of this RGB Color.");
	}
	
	public void state()
	{
		post(ppp+"State:\n"+ppp+"   R:"+red+" G:"+green+" B:"+blue);
	}

	public int getBlue()
	{
		return blue;
	}

	public void setBlue(int blue)
	{
		this.blue = clip(blue, 0, 255);
		debug("B:"+this.blue);
	}

	public int getGreen()
	{
		return green;
	}

	public void setGreen(int green)
	{
		this.green = clip(green, 0, 255);
		debug("G:"+this.green);
	}

	public int getRed()
	{
		return red;
	}

	public void setRed(int red)
	{
		this.red = clip(red, 0, 255);
		debug("R:"+this.red);
	}
}
