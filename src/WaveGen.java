import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.msp.MSPBuffer;

import lf.LfObject;

// Based on el_gwaves.js by Eric Lyon
public class WaveGen extends LfObject
{
	private String bufname, bufname2;
	private int current_buf = 0;
	private int len = 8192;
	private Vector<Float> adsyn = new Vector<Float>();
	private Vector<Float> adphase = new Vector<Float>();
	private Vector<Float> adspeed = new Vector<Float>();
	private float twopi = (float)(8 * Math.atan(1));
	private float pi = (float)Math.PI;
	private boolean randphase = false;
	private boolean harmony = true;
	private float noisiness = 0;
	private float wow = 0;
	private int wrap = 0;
	private boolean wowenabled = false;
	private boolean autocalc = false;
	private int wrapmode = 0;
	private int function = 0;
	
	public WaveGen(Atom atoms[])
	{
		version = 0.2f;
		build = "02/10/08";
		INLET_ASSIST = new String[] { "" };
		OUTLET_ASSIST = new String[] { "" };
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		init(0, false, atoms);
		switch (atoms.length)
		{
		case 3:
			len = atoms[2].toInt();
		case 2:
			set2(atoms[1].toString());
		case 1:
			set(atoms[0].toString());
			break;
		case 0:
			bail("Missing buffername");
			break;
		}
		
		declareAttribute("wow", "wow", "getwow");
		declareAttribute("wowenabled", "wowenabled", "getwowenabled");
		declareAttribute("wrap", "wrap", "getwrap");
		declareAttribute("randphase", "randphase", "getrandphase");
		declareAttribute("harmony", "harmony", "getharmony");
		declareAttribute("noisiness", "noisiness", "getnoisiness");
		declareAttribute("autocalc");
		declareAttribute("wrapmode","wrapmode","getwrapmode");
		declareAttribute("function","function","getfunction");
	}
	
	public void set(String bn)
	{
		bufname = bn;
		if(MSPBuffer.getChannels(bufname) == 0)
			error("Buffer "+bufname+" doesn't exist ");
	}
	
	public void set2(String bn)
	{
		bufname2 = bn;
		if(MSPBuffer.getChannels(bufname2) == 0)
			error("Buffer "+bufname2+" doesn't exist ");
	}
	
	public void noisiness(float f)
	{
		noisiness = clip(f, 0, 1);
		if(autocalc)
			calc();
	}
	
	public float getnoisiness()
	{
		return noisiness;
	}
	
	public void wow(float f)
	{
		wow = f;
		if(wowenabled && autocalc)
			calc();
	}
	
	public float getwow()
	{
		return wow;
	}
	
	public void wowenabled(boolean b)
	{
		wowenabled = b;
		if(autocalc)
			calc();
	}
	
	public boolean getwowenabled()
	{
		return wowenabled;
	}
	
	public void harmony(boolean b)
	{
		harmony = b;
		if(autocalc)
			calc();
	}
	
	public boolean getharmony()
	{
		return harmony;
	}
	
	public void randphase(boolean b)
	{
		randphase = b;
		if(autocalc)
			calc();
	}

	public boolean getrandphase()
	{
		return randphase;
	}
	
	public void wrap(int w)
	{
		wrap = w;
		if(autocalc)
			calc();
	}
	
	public float getwrap()
	{
		return wrap;
	}
	
	public void wrapmode(int wm)
	{
		wrapmode = wm;
		if(autocalc)
			calc();
	}
	
	public int getwrapmode()
	{
		return wrapmode;
	}
	
	public void function(int i)
	{
		function = i;
		if(autocalc)
			calc();
	}
	
	public int getfunction()
	{
		return function;
	}
	
	/////////////////////////////////////
	
	public void adsyn(Atom args[])
	{
		adsyn = new Vector<Float>();
		for(Atom a:args)
			adsyn.add(a.toFloat());
		if(autocalc)
			calc();
	}	
	
	public void phase(Atom args[])
	{
		adphase = new Vector<Float>();
		for(Atom a:args)
			adphase.add(a.toFloat());
		if(!randphase && autocalc)
			calc();
	}

	public void speed(Atom args[])
	{
		adspeed = new Vector<Float>();
		for(Atom a:args)
			adspeed.add(a.toFloat());
		if(!harmony && autocalc)
			calc();
	}
		
	public void sine()
	{
		adsyn = new Vector<Float>();
		adsyn.add(new Float(1));
		if(autocalc)
			calc();
	}
	
	public void clear()
	{
		adphase = new Vector<Float>();
		adspeed = new Vector<Float>();
		adsyn = new Vector<Float>();
		long l = (int)MSPBuffer.getSize(bufname);
		for(long i = 0; i < l; i++)
			MSPBuffer.poke(bufname, 0, i, 0);
	}
	
	public void calc()
	{
		//clear();
		float[] vals = new float[len];
		for(int i = 0 ; i < len ; i++)
			vals[i] = 0;
		if(adsyn.size() != 0)
		{
			float speed = 0;
			float amp = 0;
			for (int i = 0; i < adsyn.size(); i++)
			{
				if(harmony)
					speed = i + 1;
				else {
					try
					{
						speed = i + 1 + adspeed.get(i);
					} catch (ArrayIndexOutOfBoundsException e) {
						speed = i + 1;
					}
				}
				if(wowenabled)
					speed += (float)(Math.random() * wow);
				amp = adsyn.get(i);
				float phase = 0;
				if (amp != 0)
				{
					if(randphase)
						phase = (float)(Math.random() * Math.PI);
					else {
						try
						{
							phase = adphase.get(i) * pi;
						} catch (ArrayIndexOutOfBoundsException e) {
							phase = 0;
						}
					}
					for (int j = 0; j < len; j++)
						vals[j] += calc_func(speed, amp, phase, j);
				}
			}
			normalize(vals);
		}
		if(wrap > 0)
		{
			float begin = vals[vals.length-wrap];
			float end = vals[wrap];
			switch(wrapmode)
			{
			case 0:
				// Linear interpolation
				//post("------- WRAP "+wrap);
				//post("begin: "+begin+" end:"+end);
				for(int i = 0 ; i < 2 * wrap ; i++)
				{
					int pos = (vals.length - wrap + i) % vals.length;
				//	post("i: "+i+" pos:"+pos+" val:"+interp(begin, end, ((float)i)/(2*wrap)));
					vals[pos] = interp(begin, end, ((float)i)/(2*wrap));
				}
				break;
			case 1:
				// Zero
				for(int i = 0 ; i < wrap ; i++)
					vals[vals.length - wrap + i] = interp(begin, 0, ((float)i)/wrap);
				for(int i = 0 ; i < wrap ; i++)
					vals[i] = interp(0, end, ((float)i)/wrap);
				break;
			case 2:
				// Median
				float[] v1 = new float[2*wrap];
				float[] v2 = new float[2*wrap];
				int k = 0;
				for(int i = 0 ; i < wrap ; i++)
				{
					try
					{
						v1[k] = vals[i];
						v1[k+1] = interp(vals[i],vals[i+1],0.5f);
						v2[k] = vals[vals.length-wrap+i];
						v2[k+1] = interp(vals[vals.length-wrap+i],vals[vals.length-wrap+i+1],0.5f);
					} catch (ArrayIndexOutOfBoundsException e) {}
					k+= 2;
				}	
				for(int j = 0 ; j < 2 * wrap ; j++)
				{
					int pos = (vals.length - wrap + j) % vals.length;
					vals[pos] = interp(v2[j], v1[j], ((float)j)/(2*wrap));
				}
				break;
			}
		}
		if(noisiness != 0 && getMax(vals) != 0)
		{
			for(int i = 0 ; i < len ; i++)
				vals[i] += (float)((Math.random()*2-1) * noisiness);
			normalize(vals);
		}
		switch(current_buf)
		{
		case 0:
		case 2:
			MSPBuffer.poke(bufname, 0, vals);		
			current_buf = 1;
			break;
		case 1:
			MSPBuffer.poke(bufname2, 0, vals);			
			current_buf = 2;
			break;
		}
		outlet(0,current_buf);
	}

	private float calc_func(float speed, float amp, float phase, float j)
	{
		switch(function)
		{
		case 0:
			// Sine
			return amp * sin(speed, phase, j);
		case 1:
			// Saw
			return amp * saw(phase/pi + speed * j / len);
		case 2:
			// Sin^2
			return amp * sin2(speed, phase, j);
		case 3:
			// Sin^3
			return amp * sin3(speed, phase, j);
		case 4:
			// Sin^4
			return amp * sin4(speed, phase, j);
		case 5:
			// Tri
			return amp * tri(phase/pi + speed * j / len);
		case 6:
			// Square
			return amp * square(phase/pi + speed * j / len);
		}
		return 0;
	}

	private float sin2(float speed, float phase, float j)
	{
		float f = sin(speed, phase, j);
		if(f > 0)
			return f*f;
		return -1*f*f;
	}

	private float sin3(float speed, float phase, float j)
	{
		float f = sin(speed, phase, j);
		return f*f*f;
	}

	private float sin4(float speed, float phase, float j)
	{
		float f = sin(speed, phase, j);
		if(f > 0)
			return f*f*f*f;
		return -1*f*f*f*f;
	}

	private float sin(float speed, float phase, float j)
	{
		return (float)Math.sin(phase + twopi * speed * j / len);
	}
	
	private float saw(float f)
	{
		return interp(-1,1,(f+1)%1);
	}
	
	private float tri(float f)
	{
		f = (f+1)%1;
		if(f <= 0.25f)
			return interp(0,1,4*f);
		else if(f <= 0.75f)
			return interp(1,-1,2*(f-0.25f));
		else
			return interp(-1, 0, 4*(f-0.75f));
	}
	
	private float square(float f)
	{
		f = (f+1)%1;
		return f <= 0.5f ? -1 : 1;
	}
	
	// UTILS
	
	private void normalize(float[] vals)
	{
		float maxy = getMax(vals);
		if (maxy != 0)
			for (int i = 0; i < len; i++)
				vals[i] /= maxy;
	}

	private float getMax(float[] vals)
	{
		float maxy = 0;
		for (int i = 0; i < len; i++)
			maxy = Math.max(Math.abs(vals[i]), maxy);
		return maxy;
	}
	 
	private float interp(float f1, float f2, float step)
	{
		return f1 + step * (f2 - f1);
	}
}
