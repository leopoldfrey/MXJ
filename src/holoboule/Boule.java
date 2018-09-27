package holoboule;

import java.awt.Color;
import java.awt.geom.Point2D;

import lf.util.Formatter;

import com.cycling74.jitter.JitterMatrix;
import com.cycling74.max.Atom;

public class Boule
{
	public static final int MAX_BOULES = 64;
	public static final int PLANE_BOULE = 2;
	private static final int COL_ON = 0;
	private static final int COL_X = 1;
	private static final int COL_Y = 2;
	private static final int COL_Z = 3;
	private static final int COL_SIZEX = 4;
	private static final int COL_SIZEY = 5;
	private static final int COL_SIZEZ = 6;
	private static final int COL_SHAPE = 7;
	private static final int COL_RED = 8;
	private static final int COL_GREEN = 9;
	private static final int COL_BLUE = 10;
	private static final int COL_ALPHA = 11;
	private static final int COL_NAME = 12;
	private static final char STR_END = '\0';
	public static final float MAGICAL_FLOAT = 14000f;
	//
	public static JitterMatrix matref;
	private int matrow;
	//
	public static final Formatter ff = new Formatter(-1, -1, -1, 4);
	public static final String[] shape_stringi = { "G", "L", "E", "E2", "T" };
	public static final String[] shape_string = { "Gaussian", "Linear", "Exponential", "Exponential2", "Threshold" };

	// private float x = 0, y = 0, z = 0;
	// private float sizex = 1, sizey = 1, sizez = 1;
	// private int shape = 0;
	// private String name = "none";
	// private float[] color = { 0.5f, 0.5f, 0.5f, 0.1f };
	public float getCell(int col)
	{
		return matref.getcell2dFloat(matrow, col)[PLANE_BOULE];
	}

	public void setCell(int col, float val)
	{
		matref.setcell(new int[] { matrow, col }, PLANE_BOULE, val);
	}

	public char getChar(int col)
	{
		return (char) matref.getcell2dFloat(matrow, col)[PLANE_BOULE];
	}

	public void setChar(int col, char c)
	{
		matref.setcell(new int[] { matrow, col }, PLANE_BOULE, (float) c);
	}

	public boolean isOn()
	{
		return getCell(COL_ON) == 1f;
	}

	public void setOn(boolean on)
	{
		setCell(COL_ON, on ? 1f : 0f);
	}

	public float getX()
	{
		return getCell(COL_X);
	}

	public void setX(float x)
	{
		setCell(COL_X, x);
	}

	public float getY()
	{
		return getCell(COL_Y);
	}

	public void setY(float y)
	{
		setCell(COL_Y, y);
	}

	public float getZ()
	{
		return getCell(COL_Z);
	}

	public void setZ(float z)
	{
		setCell(COL_Z, z);
	}

	public float getSizex()
	{
		return getCell(COL_SIZEX);
	}

	public void setSizex(float sizex)
	{
		setCell(COL_SIZEX, sizex);
	}

	public float getSizey()
	{
		return getCell(COL_SIZEY);
	}

	public void setSizey(float sizey)
	{
		setCell(COL_SIZEY, sizey);
	}

	public float getSizez()
	{
		return getCell(COL_SIZEZ);
	}

	public void setSizez(float sizez)
	{
		setCell(COL_SIZEZ, sizez);
	}

	public String dispX()
	{
		return ff.format(getX());
	}

	public String dispY()
	{
		return ff.format(getY());
	}

	public String dispZ()
	{
		return ff.format(getZ());
	}

	public String dispSizeX()
	{
		return ff.format(getSizex());
	}

	public String dispSizeY()
	{
		return ff.format(getSizey());
	}

	public String dispSizeZ()
	{
		return ff.format(getSizez());
	}

	public int getShape()
	{
		return (int) getCell(COL_SHAPE);
	}

	public String dispShape()
	{
		return shape_string[getShape()];
	}

	public String getShapeStringi()
	{
		return shape_stringi[getShape()];
	}

	public void setShape(int shape)
	{
		setCell(COL_SHAPE, shape);
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
		setSizex(1);
		setSizey(1);
		setSizez(1);
		setShape(0);
		setName("none");
		randColor();
	}

	public Boule(int matrow, boolean dummy_noinit)
	{
		this.matrow = matrow;
	}

	public Boule(int matrow)
	{
		this.matrow = matrow;
		init();
	}

	public Boule(int matrow, float x, float y)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
	}

	public Boule(int matrow, float x, float y, float z)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setZ(z);
	}

	public Boule(int matrow, float x, float y, int shape)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setShape(shape);
	}

	public Boule(int matrow, float x, float y, String name)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setName(name);
	}

	public Boule(int matrow, float x, float y, float z, String name)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setZ(z);
		setName(name);
	}

	public Boule(int matrow, float x, float y, String name, int shape)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setName(name);
		setShape(shape);
	}

	public Boule(int matrow, float x, float y, float z, int shape)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setZ(z);
		setShape(shape);
	}

	public Boule(int matrow, float x, float y, float sizex, float sizey)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setSizex(sizex);
		setSizey(sizey);
	}

	public Boule(int matrow, float x, float y, float z, float sizex, float sizey, float sizez)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setZ(z);
		setSizex(sizex);
		setSizey(sizey);
		setSizez(sizez);
	}

	public Boule(int matrow, float x, float y, float sizex, float sizey, int shape)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setSizex(sizex);
		setSizey(sizey);
		setShape(shape);
	}

	public Boule(int matrow, float x, float y, float sizex, float sizey, String name)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setSizex(sizex);
		setSizey(sizey);
		setName(name);
	}

	public Boule(int matrow, float x, float y, float z, float sizex, float sizey, float sizez, int shape)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setZ(z);
		setSizex(sizex);
		setSizey(sizey);
		setSizez(sizez);
		setShape(shape);
	}

	public Boule(int matrow, float x, float y, float sizex, float sizey, int shape, String name)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setSizex(sizex);
		setSizey(sizey);
		setShape(shape);
		setName(name);
	}

	public Boule(int matrow, float x, float y, float z, float sizex, float sizey, float sizez, int shape, String name)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setZ(z);
		setSizex(sizex);
		setSizey(sizey);
		setSizez(sizez);
		setShape(shape);
		setName(name);
	}

	public Boule(int matrow, float x, float y, float z, float sizex, float sizey, float sizez, String name)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setZ(z);
		setSizex(sizex);
		setSizey(sizey);
		setSizez(sizez);
		setName(name);
	}

	public Boule(int matrow, float x, float y, float sizex, float sizey, int shape, String name, float[] color)
	{
		this.matrow = matrow;
		init();
		setX(x);
		setY(y);
		setSizex(sizex);
		setSizey(sizey);
		setShape(shape);
		setName(name);
		setColor(color);
	}

	public Boule(int matrow, float x, float y, float z, float sizex, float sizey, float sizez, int shape, String name, float[] color)
	{
		this.matrow = matrow;
		setOn(true);
		setX(x);
		setY(y);
		setZ(z);
		setSizex(sizex);
		setSizey(sizey);
		setSizez(sizez);
		setShape(shape);
		setName(name);
		setColor(color);
	}

	public static Boule createBoule(Atom[] args, int matrow)
	{
		if (args == null)
			return new Boule(matrow);
		switch (args.length)
		{
		case 2:
			// X Y
			return new Boule(matrow, args[0].toFloat(), args[1].toFloat());
		case 3:
			// X Y Z
			if (args[2].isFloat())
				return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toFloat());
			// X Y SHAPE
			else if (args[2].isInt())
				return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toInt());
			// X Y NAME
			return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toString());
		case 4:
			if (args[2].isFloat())
			{
				// X Y SIZEX SIZEY
				if (args[3].isFloat())
					return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toFloat(), args[3].toFloat());
				// X Y Z SHAPE
				else if (args[3].isInt())
					return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toFloat(), args[3].toInt());
				// X Y Z NAME
				else if (args[3].isString())
					return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toFloat(), args[3].toString());
			}
			// X Y NAME SHAPE
			return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toString(), args[3].toInt());
		case 5:
			// X Y SIZEX SIZEY SHAPE
			if (args[4].isInt())
				return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toFloat(), args[3].toFloat(), args[4].toInt());
			// X Y SIZEX SIZEY NAME
			return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toFloat(), args[3].toFloat(), args[4].toString());
		case 6:
			// X Y Z SIZEX SIZEY SIZEZ
			if (args[4].isFloat())
				return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toFloat(), args[3].toFloat(), args[4].toFloat(), args[5].toFloat());
			// X Y SIZEX SIZEY SHAPE NAME
			return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toFloat(), args[3].toFloat(), args[4].toInt(), args[5].toString());
		case 7:
			// X Y Z SIZEX SIZEY SIZEZ SHAPE
			if (args[6].isInt())
				return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toFloat(), args[3].toFloat(), args[4].toFloat(), args[5].toFloat(), args[6].toInt());
			// X Y Z SIZEX SIZEY SIZEZ NAME
			return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toFloat(), args[3].toFloat(), args[4].toFloat(), args[5].toFloat(), args[6].toString());
		case 8:
			// X Y Z SIZEX SIZEY SIZEZ SHAPE NAME
			return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toFloat(), args[3].toFloat(), args[4].toFloat(), args[5].toFloat(), args[6].toInt(), args[7].toString());
		case 12:
			// X Y Z SIZEX SIZEY SIZEZ SHAPE NAME COLOR
			return new Boule(matrow, args[0].toFloat(), args[1].toFloat(), args[2].toFloat(), args[3].toFloat(), args[4].toFloat(), args[5].toFloat(), args[6].toInt(), args[7].toString(), new float[] { args[8].toFloat(), args[9].toFloat(), args[10].toFloat(), args[11].toFloat() });
		}
		return new Boule(matrow);
	}

	public static Boule createBoule(String args, int matrow)
	{
		String[] a = args.split(" ");
		float[] c = new float[] { new Float(a[8]).floatValue(), new Float(a[9]).floatValue(), new Float(a[10]).floatValue(), new Float(a[11]).floatValue() };
		return new Boule(matrow, new Float(a[0]).floatValue(), new Float(a[1]).floatValue(), new Float(a[2]).floatValue(), new Float(a[3]).floatValue(), new Float(a[4]).floatValue(), new Float(a[5]).floatValue(), new Integer(a[6]).intValue(), a[7], c);
	}

	public void randColor()
	{
		setCell(COL_RED, random(0.4f, 0.8f));
		setCell(COL_GREEN, random(0.4f, 0.8f));
		setCell(COL_BLUE, random(0.4f, 0.8f));
		setCell(COL_ALPHA, 0.25f);
	}

	public void anything(Atom[] args)
	{
		if (args[0].isString())
		{
			String msg = args[0].toString();
			if (msg.equalsIgnoreCase("size"))
			{
				switch (args.length)
				{
				case 4:
					setSizez(args[3].toFloat());
				case 3:
					scale(args[2].toFloat(), args[1].toFloat());
					break;
				case 2:
					scale(args[1].toFloat(), args[1].toFloat(), args[1].toFloat());
					break;
				case 1:
					scale(1, 1, 1);
					break;
				}
			}
			else if (msg.equalsIgnoreCase("shape"))
			{
				setShape(args[1].toInt());
			}
			else if (msg.equalsIgnoreCase("name"))
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
			else if (msg.equalsIgnoreCase("on"))
			{
				setOn(args[1].toBoolean());
			}
		}
		else
		{
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
				// X Y SHAPE
				else if (args[2].isInt())
				{
					setX(args[0].toFloat());
					setY(args[1].toFloat());
					setShape(args[2].toInt());
				}
				// X Y NAME
				else
				{
					setX(args[0].toFloat());
					setY(args[1].toFloat());
					setName(args[2].toString());
				}
				break;
			case 4:
				if (args[2].isFloat())
				{
					// X Y SIZEX SIZEY
					if (args[3].isFloat())
					{
						setX(args[0].toFloat());
						setY(args[1].toFloat());
						setSizex(args[2].toFloat());
						setSizey(args[3].toFloat());
					}
					// X Y Z SHAPE
					else if (args[3].isInt())
					{
						setX(args[0].toFloat());
						setY(args[1].toFloat());
						setZ(args[2].toFloat());
						setShape(args[3].toInt());
					}
					// X Y Z NAME
					else if (args[3].isString())
					{
						setX(args[0].toFloat());
						setY(args[1].toFloat());
						setZ(args[2].toFloat());
						setName(args[3].toString());
					}
				}
				else
				{
					// X Y NAME SHAPE
					setX(args[0].toFloat());
					setY(args[1].toFloat());
					setName(args[2].toString());
					setShape(args[3].toInt());
				}
				break;
			case 5:
				// X Y SIZEX SIZEY SHAPE
				if (args[4].isInt())
				{
					setX(args[0].toFloat());
					setY(args[1].toFloat());
					setSizex(args[2].toFloat());
					setSizey(args[3].toFloat());
					setShape(args[4].toInt());
				}
				else
				{
					// X Y SIZEX SIZEY NAME
					setX(args[0].toFloat());
					setY(args[1].toFloat());
					setSizex(args[2].toFloat());
					setSizey(args[3].toFloat());
					setName(args[4].toString());
				}
				break;
			case 6:
				// X Y Z SIZEX SIZEY SIZEZ
				if (args[4].isFloat())
				{
					setX(args[0].toFloat());
					setY(args[1].toFloat());
					setZ(args[2].toFloat());
					setSizex(args[3].toFloat());
					setSizey(args[4].toFloat());
					setSizez(args[5].toFloat());
				}
				else
				{
					// X Y SIZEX SIZEY SHAPE NAME
					setX(args[0].toFloat());
					setY(args[1].toFloat());
					setSizex(args[2].toFloat());
					setSizey(args[3].toFloat());
					setShape(args[4].toInt());
					setName(args[5].toString());
				}
				break;
			case 7:
				// X Y Z SIZEX SIZEY SIZEZ SHAPE
				if (args[6].isInt())
				{
					setX(args[0].toFloat());
					setY(args[1].toFloat());
					setZ(args[2].toFloat());
					setSizex(args[3].toFloat());
					setSizey(args[4].toFloat());
					setSizez(args[5].toFloat());
					setShape(args[6].toInt());
				}
				else
				{
					// X Y Z SIZEX SIZEY SIZEZ NAME
					setX(args[0].toFloat());
					setY(args[1].toFloat());
					setZ(args[2].toFloat());
					setSizex(args[3].toFloat());
					setSizey(args[4].toFloat());
					setSizez(args[5].toFloat());
					setName(args[6].toString());
				}
				break;
			case 8:
				// X Y Z SIZEX SIZEY SIZEZ SHAPE NAME
				setX(args[0].toFloat());
				setY(args[1].toFloat());
				setZ(args[2].toFloat());
				setSizex(args[3].toFloat());
				setSizey(args[4].toFloat());
				setSizez(args[5].toFloat());
				setShape(args[6].toInt());
				setName(args[7].toString());
				break;
			case 12:
				// X Y Z SIZEX SIZEY SIZEZ SHAPE NAME COLOR
				setX(args[0].toFloat());
				setY(args[1].toFloat());
				setZ(args[2].toFloat());
				setSizex(args[3].toFloat());
				setSizey(args[4].toFloat());
				setSizez(args[5].toFloat());
				setShape(args[6].toInt());
				setName(args[7].toString());
				setColor(new float[] { args[8].toFloat(), args[9].toFloat(), args[10].toFloat(), args[11].toFloat() });
				break;
			}
		}
	}

	public void move(float _x, float _y)
	{
		setX(_x);
		setY(_y);
	}

	public void translate(float _dx, float _dy)
	{
		setX(getX() + _dx);
		setY(getY() + _dy);
	}

	public void scale(float _sx, float _sy)
	{
		setSizex(_sx);
		setSizey(_sy);
	}

	public void move(float _x, float _y, float _z)
	{
		setX(_x);
		setY(_y);
		setZ(_z);
	}

	public void translate(float _dx, float _dy, float _dz)
	{
		setX(getX() + _dx);
		setY(getY() + _dy);
		setZ(getZ() + _dz);
	}

	public void scale(float _sx, float _sy, float _sz)
	{
		setSizex(_sx);
		setSizey(_sy);
		setSizez(_sz);
	}

	public String out()
	{
		return getX() + " " + getY() + " " + getZ() + " " + getSizex() + " " + getSizey() + " " + getSizez() + " " + getShape() + " " + getName() + " " + getCell(COL_RED) + " " + getCell(COL_GREEN) + " " + getCell(COL_BLUE) + " " + getCell(COL_ALPHA);
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
			return "" + ff.format(getX());
		case 2:
			return "" + ff.format(getY());
		case 3:
			return "" + ff.format(getZ());
		case 4:
			return "" + ff.format(getSizex());
		case 5:
			return "" + ff.format(getSizey());
		case 6:
			return "" + ff.format(getSizez());
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
			case 4:
				setSizex(new Float(s));
				break;
			case 5:
				setSizey(new Float(s));
				break;
			case 6:
				setSizez(new Float(s));
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
		int oldmatrow = this.matrow;
		for (int i = 0; i < Source.MAX_SOURCES; i++)
			matref.setcell(new int[] { newmatrow, i }, PLANE_BOULE, matref.getcell2dFloat(oldmatrow, i)[PLANE_BOULE]);
		this.matrow = newmatrow;
	}

	public void clear()
	{
		for (int i = 0; i < Source.MAX_SOURCES; i++)
			matref.setcell(new int[] { matrow, i }, PLANE_BOULE, 0);
	}

	public static void clearAll()
	{
		for (int i = 0; i < Boule.MAX_BOULES; i++)
			for (int j = 0; j < Source.MAX_SOURCES; j++)
				matref.setcell(new int[] { i, j }, PLANE_BOULE, 0);
	}

	public static void setEnd(int i)
	{
		matref.setcell(new int[] { i, COL_ON }, PLANE_BOULE, MAGICAL_FLOAT);
	}
}