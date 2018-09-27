import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class Clipper extends LfObject
{
	private Atom[] out;
	private float low = Float.NaN;
	private float up = Float.NaN;
	
	public Clipper(Atom[] atoms)
	{
		version = 0.1f;
		build = "02/04/06";
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.FLOAT, DataTypes.FLOAT };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "Int, Float or List to be clipped.", "Lower Limit. (float)", "Upper Limit. (float)" };
		OUTLET_ASSIST = new String[] { "Int, Float or List Result." };
		
		init(0,false,atoms);

		declareAttribute("low","getLowLim","setLowLim");
		declareAttribute("up","getUpLim","setUpLim");
		
		switch(atoms.length)
		{
		default :
		case 2 :
			setUpLim(atoms[1].toFloat());
		case 1 :
			setLowLim(atoms[0].toFloat());
		case 0 :
			break;
		}
	}

	protected float getLowLim()
	{
		return low;
	}

	protected void setLowLim(float lowLim)
	{
		this.low = lowLim;
		check();
		debug("Low:"+this.low);
	}

	protected float getUpLim()
	{
		return up;
	}

	protected void setUpLim(float upLim)
	{
		this.up = upLim;
		check();
		debug("Up:"+this.up);
	}
	
	public void inlet(int i)
	{
		inlet((float)i);
	}
	
	public void inlet(float f)
	{
		switch(getInlet())
		{
		case 0 :
			out = clipAtom(new Atom[] { Atom.newAtom(f)});
			bang();
			break;
		case 1 :
			setLowLim(f);
			break;
		case 2 :
			setUpLim(f);
			break;
		}	}
	
	public void list(Atom[] atoms)
	{
		out = clipAtom(atoms);
		bang();
	}
	
	public void bang()
	{
		outlet(0,out);
	}
	
	public Atom[] clipAtom(Atom[] toClip)
	{
		Atom[] o = new Atom[toClip.length];
		for(int i = 0 ; i < toClip.length ; i++)
		{
			o[i] = clip(toClip[i]);
		}
		return o;
	}

	public Atom clip(Atom at)
	{
		if(at.isInt() || at.isFloat())
		{
			float f = at.toFloat();
			if(low == Float.NaN && up == Float.NaN)
			{
				return at;
			}

			if(low == Float.NaN)
			{
				return Atom.newAtom(clipH(f,up));
			} else if (up == Float.NaN)
			{
				return Atom.newAtom(clip(f,low));
			} else {
				return Atom.newAtom(clip(f,low,up));
			}
				
		}
		return null;
	}
	
	public void unsetLow()
	{
		low = Float.NaN;
		debug("Low:NaN");
	}
	
	public void unsetUp()
	{
		up = Float.NaN;
		debug("Up:NaN");
	}
	
	private void check()
	{
		if(up == Float.NaN || low == Float.NaN)
			return;
		if(up < low)
		{
			float tmp = low;
			low = up;
			up = tmp;
		}
	}

	public void usage()
	{
		post(ppp+"Usage:\n"
				+ppp+"   Optional argument :\n"
				+ppp+"   float arg 1 : clipping lower limit \'low\'\n"
				+ppp+"   float arg 2 : clipping upper limit \'up\'");
	}

	public void info()
	{
		post(ppp+"Info:\n"
				+ppp+"   Clip/Limit the input (int, float or list) between \'low\' and \'up\'.\n"
				+ppp+"   (One or both of these limits can be set to null.)");
	}
	
	public void state()
	{
		post(ppp+"State:\n"
				+ppp+"   Low:"+low+"   Up:"+up);
	}
}
