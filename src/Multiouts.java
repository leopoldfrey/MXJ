import java.lang.reflect.Method;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.msp.MSPObject;
import com.cycling74.msp.MSPPerformable;
import com.cycling74.msp.MSPSignal;

public class Multiouts extends MSPObject implements MSPPerformable
{
	protected final static String ppp = " ...  ";
	public float version = 0.2f;
	public String build = "11/10/2007";
	public String message;
	public final static String libAuthor = "dvlpt@gmem.org";
	protected int[] INLET_TYPES = new int[] { SIGNAL, SIGNAL, SIGNAL, DataTypes.ALL };
	protected String[] INLET_ASSIST = new String[] { "(Signal) Signal In","(Signal/Float) Angle [0:1]","(Signal/Float) Distance [0:+inf]","(Float) Interpolation Time (ms)" };
	protected int[] OUTLET_TYPES = new int[] { SIGNAL };

	private static final int MAX_SPEAKERS = 32;
    private double sr = 44.1f;
    int nsamp = 2205;
    private int init_speakers = MAX_SPEAKERS;
	int nspeakers = 8;
	float distance = 1;
	float angle = 0;
	float angle_offset = 0.5f;
	float[] amps, amps_prev, amps_delta, amps_current;
	int position = 0;
	int interp;
	int start_index = 0;
	
	Method _p1  = null;
    Method _p2 = null;
    Method _p3 = null;
    Method _p4 = null;
	
	public Multiouts(Atom atoms[])
	{
		declareInlets(INLET_TYPES);
		setInletAssist(INLET_ASSIST);
		createInfoOutlet(false);
		setName(this.getClass().getName().toString());
		message = ppp + "\t\t" + this.getClass().getName().toString() + " - v" + version + " - " + libAuthor + " - build : " + build
		+"\n\t\t\t\t\tCopyright © 2007 GMEM Marseille F";
		
		switch(atoms.length)
		{
		case 3 :
			angle_offset = modulof2(atoms[2].toFloat(),1) + 0.5f;
		case 2 :
			interp = atoms[1].toInt();
			nsamp = (int)(interp * sr);
		case 1 :
			init_speakers = nspeakers = clip(atoms[0].toInt(),1,init_speakers);
			OUTLET_TYPES = new int[nspeakers];
			for(int i = 0 ; i < nspeakers ; i++)
				OUTLET_TYPES[i] = SIGNAL;
			declareOutlets(OUTLET_TYPES);
			for(int i = 0 ; i < nspeakers ; i++)
				setOutletAssist(i, "(Signal) Signal to Speaker "+(i+1));
		default :
			init_speakers = nspeakers;
			OUTLET_TYPES = new int[nspeakers];
			for(int i = 0 ; i < nspeakers ; i++)
				OUTLET_TYPES[i] = SIGNAL;
			declareOutlets(OUTLET_TYPES);
			for(int i = 0 ; i < nspeakers ; i++)
				setOutletAssist(i, "(Signal) Signal to Speaker "+(i+1));
			break;
		}
		declareAttribute("interp","getInterp","setInterp");
		declareAttribute("distance","getDistance","setDistance");
		declareAttribute("angle","getAngle","setAngle");
		declareAttribute("angle_offset","getAngleOffset","setAngleOffset");
		declareAttribute("nspeakers","getNSpeakers","setNSpeakers");
		declareAttribute("start_index","getStart_index","setStart_index");
		
		_p1 = getPerformMethod("p1");
		_p2 = getPerformMethod("p2");
		_p3 = getPerformMethod("p3");
		_p4 = getPerformMethod("p4");
		
		amps = new float[MAX_SPEAKERS];
		amps_prev = new float[MAX_SPEAKERS];
		amps_delta = new float[MAX_SPEAKERS];
		amps_current = new float[MAX_SPEAKERS];
		for(int i = 0 ; i < MAX_SPEAKERS ; i++)
			amps_prev[i] = amps[i] = amps_delta[i] = 0;
		
		updateAmps();
	}
	
	private int getNSpeakers() {
		return nspeakers;
	}

	private void setNSpeakers(int nspeakers) {
		if(this.nspeakers == nspeakers)
			return;
		this.nspeakers = clip(nspeakers,1,init_speakers);
		updateAmps();
	}

	private float getDistance() {
		return distance;
	}

	private void setDistance(float distance) {
		this.distance = clip(distance,0,1);
		updateAmps();
	}

	private float getAngle() {
		return angle;
	}

	private void setAngle(float angle) {
		this.angle = modulof2(angle,1);
		updateAmps();
	}

	private float getAngleOffset() {
		return angle_offset;
	}

	private void setAngleOffset(float angle_offset) {
		this.angle_offset = modulof2(angle_offset,1) + 0.5f;
		updateAmps();
	}
	
	private int getInterp() {
		return (int)(nsamp / sr);
	}
	
	private void setInterp(int interp) {
		this.interp = clip(interp,0);
		nsamp = (int)(interp * sr);
		updateAmps();
	}

	public int getStart_index() {
		return start_index;
	}

	public void setStart_index(int start_index) {
		if(this.start_index == start_index - 1)
			return;
		if(init_speakers == nspeakers)
		{
			this.start_index = 0;
			return;
		}
		this.start_index = clip(start_index,1,init_speakers - nspeakers+1)-1;
		updateAmps();
	}

	public void inlet(int i)
	{
		switch(getInlet())
		{
		case 0:
			break;
		case 1:
		case 2:
			inlet((float)i);
			break;
		case 3:
			setInterp(i);
			break;
		}
	}
	
	public void inlet(float f)
	{
		switch(getInlet())
		{
		case 0:
			break;
		case 1:
			setAngle(f);
			break;
		case 2:
			setDistance(f);
			break;
		case 3:
			setInterp((int)f);
			break;
		}
	}
	
	public void dspsetup(MSPSignal[] sigs_in, MSPSignal[] sigs_out)
	{
		sr = sigs_in[0].sr / 1000;
		nsamp = (int)(interp * sr);
		dsp(sigs_in,sigs_out); 
	}

	public void perform(MSPSignal[] in, MSPSignal[] out)
	{
		sr = in[0].sr / 1000;
		nsamp = (int)(interp * sr);
		if(in[1].connected && in[2].connected)//signal rate angle and radius
		    p4(in,out);
		else if(in[2].connected)//signal rate angle
			p3(in,out);
		else if(in[1].connected)//signal rate radius
			p2(in,out);
		else
			p1(in,out);
	}

	public Method dsp(MSPSignal[] in, MSPSignal[] out)
	{
		sr = in[0].sr / 1000;
		nsamp = (int)(interp * sr);
		if(in[1].connected && in[2].connected)//signal rate angle and distance
		    return _p4;
//		else if(in[2].connected)//signal rate angle
//		    return _p3;
//		else if(in[1].connected)//signal rate distance
//			return _p2;
	    return _p1;
    }

	//no signal inputs for angle or distance
    private void p1(MSPSignal[] sigin,MSPSignal[] sigout)
    {
    	if(start_index != 0)
    		for(int i = 0 ; i < sigin[0].vec.length ; i++)
    			for(int k = 0 ; k < start_index ; k++)
    				sigout[k].vec[i] = 0;
 
    	for(int k = start_index ; k < start_index + nspeakers ; k++)
    		amps_current[k] = amps_prev[k];

    	for(int i = 0 ; i < sigin[0].vec.length ; i++)
    	{
    		if(position > 0)
    		{
    			for(int k = start_index ; k < start_index + nspeakers ; k++)
    			{
    				amps_current[k] += amps_delta[k];
    				sigout[k].vec[i] = sigin[0].vec[i] * amps_current[k];
    			}
    		} else {
    			for(int k = start_index ; k < start_index + nspeakers ; k++)
    			{
    				amps_current[k] = amps[k];
    				sigout[k].vec[i] = sigin[0].vec[i] * amps_current[k];
    				amps_delta[k] = 0;
    			}
    		}
    		position--;
    	}
    	
    	for(int k = start_index ; k < start_index + nspeakers ; k++)
    		amps_prev[k] = amps_current[k];

    	if(sigout.length > nspeakers)
    		for(int i = 0 ; i < sigin[0].vec.length ; i++)
    			for(int k = start_index + nspeakers ; k < sigout.length ; k++)
    				sigout[k].vec[i] = 0;
    }
    
	//no signal inputs for distance
    private void p2(MSPSignal[] sigin,MSPSignal[] sigout)
    {}
    
	//no signal inputs for angle
    private void p3(MSPSignal[] sigin,MSPSignal[] sigout)
    {}
    
	//all signal
    private void p4(MSPSignal[] sigin,MSPSignal[] sigout)	
    {
    	float delta = 1f/nspeakers;

    	if(start_index != 0)
    		for(int i = 0 ; i < sigin[0].vec.length ; i++)
    			for(int k = 0 ; k < start_index ; k++)
    				sigout[k].vec[i] = 0;
       	
    	for(int i = 0 ; i < sigin[0].vec.length ; i++)
			for(int k = start_index ; k < start_index + nspeakers ; k++)
				sigout[k].vec[i] = sigin[0].vec[i] * spat(sigin[1].vec[i] + angle_offset - (k - start_index) * delta, sigin[2].vec[i], nspeakers);
    	
    	if(sigout.length > nspeakers)
    		for(int i = 0 ; i < sigin[0].vec.length ; i++)
    			for(int k = start_index + nspeakers ; k < sigout.length ; k++)
    				sigout[k].vec[i] = 0;
    }
    
    private float spat(float t, float d, int n)
	{
		return (float)(Math.pow(clip((float)(1 - (Math.abs(modulof2(t, 1) - 0.5) * n * d)),0), 0.5) * (d +1)/ 2) ;
	}
    
    private void updateAmps()
    {
    	float delta = 1f/nspeakers;
    	for(int i = start_index ; i < start_index + nspeakers ; i++)
    	{
    		amps[i] = spat(angle + angle_offset - (i - start_index) * delta, distance, nspeakers);
    		amps_delta[i] = (amps[i] - amps_prev[i]) / (nsamp + 1);
    	}
    	position = nsamp;
    }
    
    // UTILS
    
	protected static int modulo(int i, int mod)
	{
		if(mod == 0)
			return i;
		while (i >= mod)
			i -= mod;
		return i;
	}

	protected static int modulo2(int i, int mod)
	{
		if(mod == 0)
			return i;
		while (i < 0) 
			i += mod;
		while (i >= mod)
			i -= mod;
		return i;
	}
	
	protected static float modulof(float f, float mod)
	{
		if(mod == 0)
			return f;
		while (f >= mod)
			f -= mod;
		return f;
	}

	protected static float modulof2(float f, float mod)
	{
		if(mod == 0)
			return f;
		while (f < 0) 
			f += mod;
		while (f >= mod)
			f -= mod;
		return f;
	}

	protected static int clip(int i, int l, int u)
	{
		int tmp = clip(i, l);
		return clipH(tmp,u);
	}

	protected static int clip(int i, int l)
	{
		return (i < l ? l : i);
	}
	
	protected static int clipH(int i, int u)
	{
		return (i > u ? u : i);
	}
	
	protected static float clip(float i, float l, float u)
	{
		float tmp = clip(i, l);
		return clipH(tmp,u);
	}

	protected static float clip(float f, float l)
	{
		return (f < l ? l : f);
	}
	
	protected static float clipH(float f, float u)
	{
		return (f > u ? u : f);
	}
	
	protected static Atom[] subAtom(Atom[] in, int beg, int end)
	{
		if(in != null)
			if(end-beg > 0 && end-beg < in.length)
			{
				Atom[] out = new Atom[end-beg];
				for(int i = 0 ; i < end-beg ; i++)
				{
					out[i] = in[beg+i];
				}
				return out;
			}
		return null;
	}
	
	protected static boolean isPair(int i)
	{
		return modulo(i,2) == 0;
	}
	
	public void javaversion()
	{
		post("java.version:"+System.getProperty("java.version"));
	}
	
	public void version()
	{
		System.out.println(message);
	}
	
	protected void dblclick()
	{
		version();
	}
}
