package holoboule;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Stack;

import com.cycling74.jitter.JitterMatrix;
import com.cycling74.max.Atom;

public class Source
{
	public static final int MAX_SOURCES = 64;
	public static final int PLANE_SOURCE = 3;
	private static final int COL_ON = 0;
	private static final int COL_X = 1;
	private static final int COL_Y = 2;
	private static final int COL_Z = 3;
	private static final int COL_RED = 4;
	private static final int COL_GREEN = 5;
	private static final int COL_BLUE = 6;
	private static final int COL_ALPHA = 7;
	private static final int COL_NAME = 8;
	private static final char STR_END = '\0';
	//
	public static JitterMatrix matref;
	private int matrow;
	//
	public static final int MODE_SPAT = 0;
	public static final int[] MSPAT = new int[] { MODE_SPAT };
	public static final int MODE_DIRECT = 1;
	public static final int[] MDIRECT = new int[] { MODE_DIRECT };
	public static final int MODE_EXCLUDE = 2;
	public static final int[] MEXCLUDE = new int[] { MODE_EXCLUDE };
	public static final int MODE_IGNORE = 3;
	public static final int[] MIGNORE = new int[] { MODE_IGNORE };
	public static final String[] mode_stringi = { "Ã", "d", "x", "-" };
	public static final String[] mode_string = { "Spat", "Direct", "Exclude", "Ignore" };
	// private float x = 0, y = 0, z = 0;
	// private String name = "none";
	// private float[] color = { 0.5f, 0.5f, 0.5f, 0.1f };
	private Link link = null;
	private Stack<Point2D.Float> buffer = new Stack<Point2D.Float>();

	public float getCell(int col)
	{
		return matref.getcell2dFloat(matrow,col)[PLANE_SOURCE];
	}

	public void setCell(int col, float val)
	{
		matref.setcell(new int[] { matrow, col }, PLANE_SOURCE, val);
	}

	public char getChar(int col)
	{
		return (char) matref.getcell2dFloat(matrow, col)[PLANE_SOURCE];
	}

	public void setChar(int col, char c)
	{
		matref.setcell(new int[] { matrow, col }, PLANE_SOURCE, (float) c);
	}

	public boolean isOn()
	{
		return getCell(COL_ON) != 0;
	}
	
	public void setOn(boolean on)
	{
		setCell(COL_ON, on ? 1 : 0);
	}
	
	public float getX()
	{
		// return x;
		return getCell(COL_X);
	}

	public void setX(float x)
	{
		// this.x = x;
		setCell(COL_X, x);
	}

	public float getY()
	{
		// return y;
		return getCell(COL_Y);
	}

	public void setY(float y)
	{
		// this.y = y;
		setCell(COL_Y, y);
	}

	public float getZ()
	{
		// return z;
		return getCell(COL_Z);
	}

	public void setZ(float z)
	{
		// this.z = z;
		setCell(COL_Z, z);
	}

	public String dispX()
	{
		return Boule.ff.format(getX());
	}

	public String dispY()
	{
		return Boule.ff.format(getY());
	}

	public String dispZ()
	{
		return Boule.ff.format(getZ());
	}

	public String getName()
	{
		String tmp = "";
		int i = COL_NAME;
		char c = getChar(i);
		while (c != STR_END)
		{
			tmp = tmp + c;
			i++;
			c = getChar(i);
		}
		return tmp;
	}

	public void setName(String name)
	{
		int i = COL_NAME;
		char[] cc = name.toCharArray();
		for (char c : cc)
		{
			setChar(i, c);
			i++;
		}
		setChar(i, STR_END);
	}

	public String dispName()
	{
		String name = getName();
		return name.equalsIgnoreCase("none") ? "" : name;
	}

	public String dispName2(int i)
	{
		String name = getName();
		return (name.equalsIgnoreCase("none") ? "" + i : name.substring(0, Math.min(name.length(), 6)));
	}

	public float[] getColor()
	{
		return new float[] { getCell(COL_RED), getCell(COL_GREEN), getCell(COL_BLUE), getCell(COL_ALPHA) };
	}

	public Color getJColor()
	{
		return new Color(getCell(COL_RED), getCell(COL_GREEN), getCell(COL_BLUE), getCell(COL_ALPHA));
	}

	public void setJColor(Color c)
	{
		float color[] = new float[] { 0f, 0f, 0f, 0f };
		c.getColorComponents(color);
		setCell(COL_RED, color[0]);
		setCell(COL_GREEN, color[1]);
		setCell(COL_BLUE, color[2]);
		// setCell(COL_ALPHA, color[3]);
	}

	public void setColor(float[] color)
	{
		setCell(COL_RED, color[0]);
		setCell(COL_GREEN, color[1]);
		setCell(COL_BLUE, color[2]);
		setCell(COL_ALPHA, color[3]);
	}

	public Link getLink()
	{
		return link;
	}

	public void setLink(Link link)
	{
		this.link = link;
		if (link != null)
			link.setMatrow(this.matrow);
	}

	public Stack<Point2D.Float> getBuffer()
	{
		return buffer;
	}

	public Point2D.Float getPoint()
	{
		return new Point2D.Float(getX(), getY());
	}

	public void init()
	{
		setOn(true);
		setX(0);
		setY(0);
		setZ(0);
		setName("none");
		randColor();
	}

	public Source(int matcol, boolean dummy_noinit)
	{
		this.matrow = matcol;
		this.link = new Link(matcol);
		buffer.push(getPoint());
	}

	public Source(int matcol)
	{
		this.matrow = matcol;
		init();
		buffer.push(getPoint());
	}

	public Source(int matcol, float x, float y)
	{
		this.matrow = matcol;
		init();
		setX(x);
		setY(y);
		buffer.push(getPoint());
	}

	public Source(int matcol, float x, float y, float z)
	{
		this.matrow = matcol;
		init();
		setX(x);
		setY(y);
		setZ(z);
		buffer.push(getPoint());
	}

	public Source(int matcol, float x, float y, String name)
	{
		this.matrow = matcol;
		init();
		setX(x);
		setY(y);
		setName(name);
		buffer.push(getPoint());
	}

	public Source(int matcol, float x, float y, float z, String name)
	{
		this.matrow = matcol;
		init();
		setX(x);
		setY(y);
		setZ(z);
		setName(name);
		buffer.push(getPoint());
	}

	public Source(int matcol, float x, float y, String name, float[] color)
	{
		this.matrow = matcol;
		init();
		setX(x);
		setY(y);
		setName(name);
		setColor(color);
		buffer.push(getPoint());
	}

	public Source(int matcol, float x, float y, float z, String name, float[] color)
	{
		this.matrow = matcol;
		setOn(true);
		setX(x);
		setY(y);
		setZ(z);
		setName(name);
		setColor(color);
		buffer.push(getPoint());
	}

	static public Source createSource(Atom[] args, int matcol)
	{
		if (args == null)
			return new Source(matcol);
		switch (args.length)
		{
		case 2:
			// X Y
			return new Source(matcol, args[0].toFloat(), args[1].toFloat());
		case 3:
			// X Y Z
			if (args[2].isFloat())
				return new Source(matcol, args[0].toFloat(), args[1].toFloat(), args[2].toFloat());
			// X Y NAME
			return new Source(matcol, args[0].toFloat(), args[1].toFloat(), args[2].toString());
		case 4:
			// X Y Z NAME
			return new Source(matcol, args[0].toFloat(), args[1].toFloat(), args[2].toFloat(), args[3].toString());
		case 7:
			// X Y NAME COLOR
			return new Source(matcol, args[0].toFloat(), args[1].toFloat(), args[2].toString(), new float[]{ args[3].toFloat(), args[4].toFloat(), args[5].toFloat(), args[6].toFloat() });
		case 8:
			// X Y Z NAME COLOR
			return new Source(matcol, args[0].toFloat(), args[1].toFloat(), args[2].toFloat(), args[3].toString(), new float[]{ args[4].toFloat(), args[5].toFloat(), args[6].toFloat(), args[7].toFloat() });
		}
		return new Source(matcol);
	}

	public static Source createSource(String args, int matcol)
	{
		String[] al = args.split(" _l ");
		String[] a = al[0].split(" ");
		float[] c = new float[] { new Float(a[4]).floatValue(), new Float(a[5]).floatValue(), new Float(a[6]).floatValue(), new Float(a[7]).floatValue() };
		Source s = new Source(matcol, new Float(a[0]).floatValue(), new Float(a[1]).floatValue(), new Float(a[2]).floatValue(), a[3], c);
		if (al.length > 1)
			s.setLink(Link.createLink(al[1], matcol));
		return s;
	}

	public void randColor()
	{
		Color c = Color.getHSBColor((float) Math.random(), 1f, 0.95f);
		setCell(COL_RED, c.getRed() / 255f);
		setCell(COL_GREEN, c.getGreen() / 255f);
		setCell(COL_BLUE, c.getBlue() / 255f);
		setCell(COL_ALPHA, 0.5f);
	}

	public Link anything(Atom[] args)
	{
		if (args[0].isString())
		{
			String msg = args[0].toString();
			if (msg.equalsIgnoreCase("name"))
			{
				setName(args[1].toString());
			}
			else if (msg.equalsIgnoreCase("color"))
			{
				setCell(COL_RED, args[1].toFloat());
				setCell(COL_GREEN, args[2].toFloat());
				setCell(COL_BLUE, args[3].toFloat());
				setCell(COL_ALPHA, args[4].toFloat());
			}
			else if (msg.equalsIgnoreCase("link"))
			{
				if (args.length == 1)
				{
					link = null;
					return null;
				}
				float[] params = new float[args.length - 3];
				for (int i = 3; i < args.length; i++)
					params[i - 3] = args[i].toFloat();
				link = new Link(matrow, args[1].toInt(), args[2].toInt(), params);
			}
			else if (msg.equalsIgnoreCase("on"))
			{
				setOn(args[1].toBoolean());
			}
		}
		else
		{
			buffer.push(getPoint());
			switch (args.length)
			{
			case 2:
				// X Y
				setX(args[0].toFloat());
				setY(args[1].toFloat());
				break;
			case 3:
				// X Y Z
				if (args[2].isFloat())
				{
					setX(args[0].toFloat());
					setY(args[1].toFloat());
					setZ(args[2].toFloat());
				}
				else
				{
					// X Y NAME
					setX(args[0].toFloat());
					setY(args[1].toFloat());
					setName(args[2].toString());
				}
				break;
			case 4:
				// X Y Z NAME
				setX(args[0].toFloat());
				setY(args[1].toFloat());
				setZ(args[2].toFloat());
				setName(args[3].toString());
				break;
			case 7:
				// X Y NAME COLOR
				setX(args[0].toFloat());
				setY(args[1].toFloat());
				setName(args[2].toString());
				setColor(new float[] { args[3].toFloat(), args[4].toFloat(), args[5].toFloat(), args[6].toFloat() });
				break;
			case 8:
				// X Y Z NAME COLOR
				setX(args[0].toFloat());
				setY(args[1].toFloat());
				setZ(args[2].toFloat());
				setName(args[3].toString());
				setColor(new float[] { args[4].toFloat(), args[5].toFloat(), args[6].toFloat(), args[7].toFloat() });
				break;
			}
			return link;
		}
		return null;
	}

	public Link move(float _x, float _y)
	{
		buffer.push(getPoint());
		setX(_x);
		setY(_y);
		return link;
	}

	public void translate(float _dx, float _dy)
	{
		buffer.push(getPoint());
		setX(getX() + _dx);
		setY(getY() + _dy);
	}

	public Link move(float _x, float _y, float _z)
	{
		buffer.push(getPoint());
		setX(_x);
		setY(_y);
		setZ(_z);
		return link;
	}

	public void translate(float _dx, float _dy, float _dz)
	{
		buffer.push(getPoint());
		setX(getX() + _dx);
		setY(getY() + _dy);
		setZ(getZ() + _dz);
	}

	public String out()
	{
		return getX() + " " + getY() + " " + getZ() + " " + getName() + " " + getCell(COL_RED) + " " + getCell(COL_GREEN) + " " + getCell(COL_BLUE) + " " + getCell(COL_ALPHA);
	}

	public static float random(float low, float high)
	{
		return (float) Math.random() * (high - low) + low;
	}

	public String getInfoString(int col)
	{
		switch (col)
		{
		case 1:
			return "" + getX();
		case 2:
			return "" + getY();
		case 3:
			return "" + getZ();
		}
		return "";
	}

	public void setInfoString(int col, String s)
	{
		try
		{
			switch (col)
			{
			case 1:
				setX(new Float(s));
				break;
			case 2:
				setY(new Float(s));
				break;
			case 3:
				setZ(new Float(s));
				break;
			}
		}
		catch (NumberFormatException nfe)
		{
		}
	}

	public int getMatrow()
	{
		return matrow;
	}

	public void setMatrow(int newmatrow)
	{
		if (newmatrow == this.matrow)
			return;
		int oldmatrow = this.matrow;
		for (int i = 0; i < Source.MAX_SOURCES; i++)
			matref.setcell(new int[] { newmatrow, i }, PLANE_SOURCE, matref.getcell2dFloat(oldmatrow, i)[PLANE_SOURCE]);
		this.matrow = newmatrow;
		if (link != null)
			link.setMatrow(this.matrow);
	}

	public void clear()
	{
		for (int i = 0; i < Source.MAX_SOURCES; i++)
			matref.setcell(new int[] { matrow, i }, PLANE_SOURCE, 0);
		if (link != null)
			link.clear();
	}

	public static void clearAll()
	{
		for (int i = 0; i < Boule.MAX_BOULES; i++)
			for (int j = 0; j < Source.MAX_SOURCES; j++)
				matref.setcell(new int[] { i, j }, PLANE_SOURCE, 0);
		Link.clearAll();
	}

	public static void setEnd(int i)
	{
		matref.setcell(new int[] { i, COL_ON }, PLANE_SOURCE, Boule.MAGICAL_FLOAT);
		Link.setEnd(i);
	}
}
