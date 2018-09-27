package holoboule;

import java.awt.geom.Point2D;

import com.cycling74.jitter.JitterMatrix;

import lf.util.Formatter;

public class Link
{
	public static final int PLANE_LINK = 4;
	private static final int COL_ON = 0;
	private static final int COL_TO = 1;
	private static final int COL_MODE = 2;
	private static final int COL_PARAMS = 3;
	private static final int MAX_PARAMS = 3;
	//
	public static JitterMatrix matref;
	private int matrow;
	//
	public static final Formatter ff = new Formatter(-1, -1, -1, 5);
	public static final int LINK_FREE = 0;
	public static final int LINK_CENTRAL = 1;
	public static final int LINK_HORIZONTAL = 2;
	public static final int LINK_VERTICAL = 3;
	public static final int LINK_DELTA = 4;
	public static final int LINK_DELTAPOL = 5;
	public static final String[] link_stringi = { "F", "C", "H", "V", "dC", "dP" };
	public static final String[] link_action = { "Free", "Central", "Horizontal", "Vertical", "DeltaCar", "DeltaPol" };
	public static final String[] link_string = { "Free (no params)", "Central (no params)", "Horizontal (no params)", "Vertical (no params)", "Cartesian Delta (X Y Z)", "Polar Delta (Distance Angle Z)" };

	// int to = -1;
	// int mode = LINK_FREE;
	// float[] params;
	public float getCell(int col)
	{
		return matref.getcell2dFloat(matrow, col)[PLANE_LINK];
	}

	public void setCell(int col, float val)
	{
		matref.setcell(new int[] { matrow, col }, PLANE_LINK, val);
	}

	public boolean isOn()
	{
		return getCell(COL_ON) != 0;
	}
	
	public void setOn(boolean on)
	{
		setCell(COL_ON, on ? 1 : 0);
	}
	
	public Link(int matcol)
	{
		this.matrow = matcol;
	}

	public Link(int matcol, int to)
	{
		this.matrow = matcol;
		setOn(true);
		setMode(LINK_FREE);
		setParams(new float[] { 0, 0, 0 });
		setTo(to);
	}

	public Link(int matcol, int to, int mode, float[] params)
	{
		this.matrow = matcol;
		setTo(to);
		setMode(mode);
		setParams(params);
	}

	public static Link createLink(String args, int matcol)
	{
		String[] a = args.split(" ");
		float[] pams = new float[a.length - 2];
		for (int i = 2; i < a.length; i++)
			pams[i - 2] = new Float(a[i]).floatValue();
		int mode = 0;
		try
		{
			mode = new Integer(a[1]).intValue();
		}
		catch (NumberFormatException nfe)
		{
			for (int i = 0; i < link_action.length; i++)
				if (a[1].equalsIgnoreCase(link_action[i]))
				{
					mode = i;
					break;
				}
		}
		return new Link(matcol, new Integer(a[0]).intValue(), mode, pams);
	}

	public String out()
	{
		String tmp = getTo() + " " + getMode();
		float params[] = getParams();
		for (float f : params)
			tmp += " " + f;
		return tmp;
	}

	// fonction de conversion de coordonnees cartesiennes vers coordonnees polaires
	public static Point2D.Float car2pol(Point2D.Float p)
	{
		double dist = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2));
		double theta = 0;
		if (dist != 0)
		{
			theta = Math.asin(p.y / dist) / Math.PI * 180;
			if ((p.x <= 0 && p.y >= 0) || (p.x <= 0 && p.y <= 0))
			{
				theta = 180 - theta;
			}
			else if (p.x > 0 && p.y < 0)
			{
				theta = 360 + theta;
			}
		}
		return new Point2D.Float((float) dist, (float) theta);
	}

	// fonction de conversion de coordonnees polaires vers coordonnees cartesiennes
	static public Point2D.Float pol2car(float dist, float theta)
	{
		double XX = 0;
		double YY = 0;
		theta = (theta + 360) % 360;
		if (theta == 0)
		{
			XX = dist;
			YY = 0;
		}
		else if (theta == 90)
		{
			XX = 0;
			YY = dist;
		}
		else if (theta == 180)
		{
			XX = -1 * dist;
			YY = 0;
		}
		else if (theta == 270)
		{
			XX = 0;
			YY = -1 * dist;
		}
		else
		{
			YY = Math.sin(theta / 180 * Math.PI) * dist;
			if (theta < 90)
			{
				YY = Math.abs(YY);
				XX = Math.sqrt(Math.pow(dist, 2) - Math.pow(YY, 2));
			}
			else if (theta < 180)
			{
				YY = Math.abs(YY);
				XX = -1 * Math.sqrt(Math.pow(dist, 2) - Math.pow(YY, 2));
			}
			else if (theta < 270)
			{
				XX = -1 * Math.sqrt(Math.pow(dist, 2) - Math.pow(YY, 2));
			}
			else
			{
				XX = Math.sqrt(Math.pow(dist, 2) - Math.pow(YY, 2));
			}
		}
		return new Point2D.Float((float) XX, (float) YY);
	}

	public int getTo()
	{
		return (int) getCell(COL_TO);
	}

	public void setTo(int to)
	{
		setCell(COL_TO, to);
	}

	public int getMode()
	{
		return (int) getCell(COL_MODE);
	}

	public void setMode(int mode)
	{
		setCell(COL_MODE, mode);
		switch (mode)
		{
		case LINK_FREE:
		case LINK_CENTRAL:
		case LINK_HORIZONTAL:
		case LINK_VERTICAL:
			setParams(new float[] {});
			break;
		case LINK_DELTA:
		case LINK_DELTAPOL:
			setParams(new float[] { 0, 0, 0 });
			break;
		}
	}

	public void setMode(String mode)
	{
		for (int i = 0; i < link_action.length; i++)
			if (mode.equalsIgnoreCase(link_action[i]))
			{
				setMode(i);
				return;
			}
	}

	public static int getModeFromString(String mode)
	{
		for (int i = 0; i < link_action.length; i++)
			if (mode.equalsIgnoreCase(link_action[i]))
				return i;
		return 0;
	}

	public void setInvertedMode(Link l)
	{
		int m = l.getMode();
		setMode(m);
		switch (m)
		{
		case LINK_DELTA:
		case LINK_DELTAPOL:
			for (int i = 0; i < MAX_PARAMS; i++)
				setParam(i, -1 * l.getParamF(i));
		default:
			break;
		}
	}

	public float[] getParams()
	{
		float[] params = new float[MAX_PARAMS];
		for (int i = 0; i < MAX_PARAMS; i++)
			params[i] = getCell(COL_PARAMS + i);
		return params;
	}

	public void setParams(float[] params)
	{
		for (int i = 0; i < params.length; i++)
			setParam(i, params[i]);
	}

	public float getParamF(int i)
	{
		if (i < MAX_PARAMS)
			return getCell(COL_PARAMS + i);
		return 0;
	}

	public String getParam(int i)
	{
		return ff.format(getParamF(i));
	}

	public void setParam(int p, String s)
	{
		try
		{
			setParam(p, new Float(s).floatValue());
		}
		catch (NumberFormatException nfe)
		{
		}
	}

	public void setParam(int p, float v)
	{
		setCell(COL_PARAMS + p, v);
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
			matref.setcell(new int[] { newmatrow, i }, PLANE_LINK, matref.getcell2dFloat(oldmatrow, i)[PLANE_LINK]);
		this.matrow = newmatrow;
	}

	public void clear()
	{
		for (int i = 0; i < Source.MAX_SOURCES; i++)
			matref.setcell(new int[] { matrow, i }, PLANE_LINK, 0);
	}

	public static void clearAll()
	{
		for (int i = 0; i < Boule.MAX_BOULES; i++)
			for (int j = 0; j < Source.MAX_SOURCES; j++)
				matref.setcell(new int[] { i, j }, PLANE_LINK, 0);
	}

	public static void clearRow(int row)
	{
		for (int i = 0; i < Source.MAX_SOURCES; i++)
			matref.setcell(new int[] { row, i }, PLANE_LINK, 0);
	}

	public static void setEnd(int i)
	{
		matref.setcell(new int[] { i, COL_ON }, PLANE_LINK, Boule.MAGICAL_FLOAT);
	}
}