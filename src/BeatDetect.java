import java.lang.reflect.Method;
import java.util.LinkedList;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxClock;
import com.cycling74.msp.MSPSignal;

import lf.LfMspObject;

// TODO BEATDETECT thres � revoir

public class BeatDetect extends LfMspObject implements Executable
{
    private Method _p;
    private MaxClock c;
    private float sr = 44.1f;
    private LinkedList<Float> fifo;
    private int size = 200;
    private int size_smp = 0;
    private float[] vec = new float[4096];
    private int vec_len = 0;
    private float vec_dur = 0;
    private float mean = 0;
    private float ecart = 0;
    private float meanE = 0;
    private float ecartE = 0;
    private float deltaE = 0;
    private int cpt = 0;
    private float thres = 2f;
    private int release = 50;
    private int interval = 5;
    private int release_iter = 10;
    private float release_smp = 0;
    private boolean outbang = false;
    private boolean outbang2 = false;
    private boolean previous = false;
    private boolean previous2 = false;
    private int mode = 1;
    
	public BeatDetect(Atom[] atoms)
	{
		version = 0.1f;
		build = "20/10/08";	
		INLET_TYPES = new int[] { SIGNAL };
		OUTLET_TYPES = new int[] { SIGNAL, DataTypes.ALL};
		INLET_ASSIST = new String[] { "" };
		OUTLET_ASSIST = new String[] { "" };
		init();
		switch (atoms.length)
		{
		case 5:
		case 4:
		case 3:
		case 2:
		case 1:
		case 0:
			break;
		}
		fifo=new LinkedList<Float>();
		declareAttribute("thres");
		declareAttribute("size");
		declareAttribute("mode");
		declareAttribute("release","getRelease","setRelease");
		declareAttribute("interval","getInterval","setInterval");
		_p = getPerformMethod("p");
		c = new MaxClock(this);
		c.delay(interval);
	}

	public Method dsp(MSPSignal[] in, MSPSignal[] out)
	{
		return _p;
	}

	public void p(MSPSignal[] in, MSPSignal[] out)
	{
		sr = (float)in[0].sr / 1000;
		vec_len = in[0].vec.length;
		vec_dur = vec_len/sr;
		release_smp = release/vec_dur;
		size_smp = (int)(size * interval / vec_dur + 1);
		switch(mode)
		{
		case 0:
			for(int i = 0 ; i < in[0].vec.length ; i++)
				vec[i] = Math.abs(in[0].vec[i]);
			break;			
			
		case 1:
			float S = 0;
			float Q = 0;
			float M = 0;
			float N = vec_len;
			for(Float f : in[0].vec)
			{
				S += f;
				Q += f*f;
			}
			mean = M = S/N;
			ecart = (float)Math.sqrt(Q/N - M*M);
	
			fifo.offer(ecart);
			if(fifo.size() > size_smp)
				fifo.poll();
			
			S = 0;
			N = fifo.size();
			for(Float f : fifo)
			{
				S += f;
			}
			meanE = S/N;
			deltaE = Math.abs(ecart - meanE);
			cpt++;
			if(deltaE > thres*meanE)
			{
				if(cpt > release_smp)
				{
					outbang = true;
					outbang2 = true;
				}
				cpt = 0;
			}
			break;
		}
		for(int i = 0 ; i < vec_len ; i++)
			out[0].vec[i] = 0;
		out[0].vec[0] = outbang && previous != outbang ? 1 : 0;
		previous = outbang;
		outbang = false;

	}
	
	public int getRelease()
	{
		return release;
	}
	
	public void setRelease(int r)
	{
		release = clip(r, 0);
		release_iter = release/interval;
		cpt = 0;
	}
	
	public int getInterval()
	{
		return interval;
	}
	
	public void setInterval(int i)
	{
		interval = clip(i, 1);
		release_iter = release/interval;
		cpt = 0;
	}
	
	public void execute()
	{
		switch(mode)
		{
		case 0:
			float S = 0;
			float Q = 0;
			float M = 0;
			float N = vec_len;
			float f = 0;
			for(int i = 0 ; i < vec_len ; i++)
			{
				f = vec[i];
				S += f;
				Q += f*f;
			}
			mean = M = S/N;
			ecart = (float)Math.sqrt(Q/N - M*M);

			fifo.offer(ecart);
			if(fifo.size() > size)
				fifo.poll();
			
			S = 0;
			N = fifo.size();
			for(Float f2 : fifo)
			{
				S += f2;
			}
			meanE = S/N;
			deltaE = Math.abs(ecart - meanE);
			cpt++;
			if(deltaE > thres*meanE)
			{
				if(cpt > release_iter)
				{
					outbang2 = true;
					outbang = true;
					outletBang(1);
				}
				cpt = 0;
			}
			break;
		case 1:
			
			break;
		}
		
		if(outbang2 && outbang2 != previous2)
			outletBang(1);
		previous2 = outbang2;
		outbang2 = false;
		
		c.delay(interval);

	}
}