import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;


public class RGBToHSL extends LfObject
{
	private int red = 0;
	private int green = 0;
	private int blue = 0;

	public RGBToHSL(Atom atoms[])
	{
		version = 0.1f;
		build = "02/03/07";
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.INT, DataTypes.INT };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "red value or RGB list (int 0-255) / inv HLS list (int 0-255) / bang", "green value (int 0-255)", "blue value (int 0-255)" };
		OUTLET_ASSIST = new String[] { "Result String : Hexadecimal Color." };

		declareAttribute("red","getRed","setRed");
		declareAttribute("green","getGreen","setGreen");
		declareAttribute("blue","getBlue","setBlue");

		init(0, false, atoms);

		list(atoms);
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

	public int getBlue()
	{
		return blue;
	}

	public void setBlue(int blue)
	{
		this.blue = clip(blue, 0, 255);
	}

	public int getGreen()
	{
		return green;
	}

	public void setGreen(int green)
	{
		this.green = clip(green, 0, 255);
	}

	public int getRed()
	{
		return red;
	}

	public void setRed(int red)
	{
		this.red = clip(red, 0, 255);
	}
	
	public void bang()
	{
		int m = Math.min(Math.min(red, green),blue);
		int M = Math.max(Math.max(red, green),blue);
		int delta = M - m;
		
		float t0;
		if(red > green && red > blue) {
			t0 = (green - blue) / delta + 0;
		} else if(green > red && green > blue) {
			t0 = (blue - red) / delta + 2;
		} else {
			t0 = (red - green) / delta + 4;
		}
		
		// TODO RGBTOHSL finish it 
		// http://fr.wikipedia.org/wiki/Codage_informatique_des_couleurs
	}
}
