import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxClock;
import com.cycling74.max.MaxObject;


public class HtpMixer extends MaxObject implements Executable
{
	protected int[] INLET_TYPES = {};
	protected int[] OUTLET_TYPES = {};
	protected String[] INLET_ASSIST = {};
	protected String[] OUTLET_ASSIST = {};
	//
	public static final int OUT_RESULT = 0;
	public static final int OUT_OUT = 1;
	public static final int OUT_IN = 2;
	public static final int OUT_FADE = 3;
	public static final int OUT_BANG = 4;
	//
	private MaxClock clock = null;
	private int inputs = 2;
	private int length = 80;
	private float[] weight;
	private Vector<float[]> lists;
	private float[] output, master, sequence, A, B;
	//
	private float stepdur = 20;
	private float stepfactor = 1;
	private boolean[] sens;
	private float[] from, to;
	private float grandmaster = 1;
	private int timeIn = 1000;
	private int timeOut = 1000;
	private int numstepIn, numstepOut, curstepIn, curstepOut, numcycle, curcycle;
	private float curstep = 0;
	private boolean paused = false;
	private boolean running = false;
	private long startTime, currentTime, idealTime;
	
	public HtpMixer(Atom[] atoms)
	{
		OUTLET_TYPES = new int[] { DataTypes.LIST, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL };
		OUTLET_ASSIST = new String[] { "Sortie" , "Time Out", "Time In", "Transfert", "Bang en fin de transfert"};
		
		switch(atoms.length)
		{
		case 2 :
			if (atoms[1].isInt())
				inputs = clip(atoms[1].getInt(), 2, 20);
		case 1 :
			if (atoms[0].isInt())
				length = clip(atoms[0].getInt(), 2, 512);
		default :
			break;
		}
		
		INLET_TYPES = new int[inputs+3];
		INLET_TYPES[0] = DataTypes.ALL;
		for (int i = 1; i < inputs+3; i++)
			INLET_TYPES[i] = DataTypes.LIST;
		INLET_ASSIST = new String[inputs+3];
		INLET_ASSIST[0] = "Commands";
		INLET_ASSIST[1] = "A";
		INLET_ASSIST[2] = "B";
		for (int i = 3; i < inputs+3; i++)
			INLET_ASSIST[i] = "Master " + (i-2);
		weight = new float[inputs];
		lists = new Vector<float[]>(inputs);
		A = new float[length];
		B = new float[length];
		master = new float[length];
		output = new float[length];
		sequence = new float[length];
		from = new float[length];
		sens = new boolean[length];
		for(int i = 0 ; i < inputs ; i++)
		{
			lists.add(i,new float[length]);
			weight[i] = 0;
		}
		for(int i = 0 ; i < length ; i++)
		{
			master[i] = 0;
			sequence[i] = 0;
			output[i] = 0;
		}
		
		declareAttribute("stepdur");
		declareAttribute("stepfactor");
		clock = new MaxClock(this);
		declareInlets(INLET_TYPES);
		declareOutlets(OUTLET_TYPES);
		setInletAssist(INLET_ASSIST);
		setOutletAssist(OUTLET_ASSIST);
		createInfoOutlet(false);
	}
	
	public void list(Atom[] args)
	{
		if (getInlet() == 0)
		{
			if(args.length == 2)
			{
				weight[args[0].toInt()-1] = args[1].toFloat();
			} else if(args.length == inputs) {
				for(int i = 0 ; i < inputs ; i++)
					weight[i] = args[i].toFloat();
			}
			bang();
		} else if (getInlet() == 1) {
			if(args.length == length)
			{
				A = Atom.toFloat(args);
			} else if(args.length < length) {
				int c = 0;
				for(float f : Atom.toFloat(args))
				{
					A[c] = f;
					c++;
				}
			} else {
				for(int c = 0 ; c < length ; c++)
					A[c] = args[c].toFloat();
			}
			if(!running)
				fade(curstep);
		} else if (getInlet() == 2) {
			if(args.length == length)
			{
				B = Atom.toFloat(args);
			} else if(args.length < length) {
				int c = 0;
				for(float f : Atom.toFloat(args))
				{
					B[c] = f;
					c++;
				}
			} else {
				for(int c = 0 ; c < length ; c++)
					B[c] = args[c].toFloat();
			}
			if(!running)
				fade(curstep);
		} else {
			if(args.length == length)
			{
				lists.set(getInlet()-3, Atom.toFloat(args));
			} else if(args.length < length) {
				int c = 0;
				for(float f : Atom.toInt(args))
				{
					lists.get(getInlet()-3)[c] = f;
					c++;
				}
			} else {
				for(int c = 0 ; c < length ; c++)
					lists.get(getInlet()-3)[c] = args[c].toFloat();
			}
			bang();
		}
	}
	
	public void bang()
	{
		for(int j = 0 ; j < length ; j++)
			master[j] = 0;
		for(int i = 0 ; i < lists.size() ; i++)
			if(weight[i] > 0)
			{
				for(int j = 0 ; j < length ; j++)
				{
					master[j] = Math.max(master[j],weight[i]*lists.get(i)[j]);
				}
			}
		for(int j = 0 ; j < length ; j++)
			output[j] = grandmaster*Math.max(sequence[j],master[j]);
		
		outlet(OUT_RESULT, Atom.newAtom(output));
	}
	
	public void anything(String msg, Atom[] args)
	{
		if(msg.equalsIgnoreCase("go"))
		{
//			stop();
			stepfactor = 1;
			paused = false;
			to = B;//curstep < 0.5 ? B : A;
//			if(curstep < 0.5)
//				curstep = 1;
//			else
				curstep = 0;
			timeOut = args[0].toInt();
			timeIn = args[1].toInt();
			if(timeIn == 0 && timeOut == 0)
			{
				for(int i = 0 ; i < length ; i++)
					sequence[i] = to[i];
				curstep = 1;
				outlet(OUT_FADE,curstep);
				bang();
			}
			else
			{
				numstepOut = (int) (timeOut / stepdur);
				numstepIn = (int) (timeIn / stepdur);
				numcycle = Math.max(numstepIn, numstepOut);
				curstepIn = 0;
				curstepOut = 0;
				curcycle = 0;
				for (int i = 0; i < length; i++)
				{
					from[i] = sequence[i];
					sens[i] = to[i] - from[i] > 0;
				}
				running = true;
				outlet(OUT_BANG, Atom.parse("running 1"));
				startTime = System.currentTimeMillis();
				idealTime = 0;
				clock.delay(0);
			}
		}
	}
	
	public void fade(float step)
	{
		curstep = step;
		stop();
		for (int i = 0; i < length; i++)
			sequence[i] = interpol(A[i], B[i], step);
		outlet(OUT_FADE,curstep);
		bang();	
	}
	
	public void grandmaster(float gm)
	{
		grandmaster = gm;
		bang();
	}
	
	public void execute()
	{
		if(paused)
			clock.delay(stepdur);
		else
		{
			if(curcycle <= numcycle)
			{
				for (int i = 0; i < length; i++)
				{
					if (sens[i])
					{
						if(numstepIn == 0)
							sequence[i] = to[i];
						else if (curstepIn <= numstepIn)
							sequence[i] = interpol(from[i], to[i], curstepIn / (float) numstepIn);
					}
					else
					{
						if(numstepOut == 0)
							sequence[i] = to[i];
						if (curstepOut <= numstepOut)
							sequence[i] = interpol(from[i], to[i], curstepOut / (float) numstepOut);
					}
				}
				curstepIn++;
				curstepOut++;
				curcycle++;
				outlet(OUT_FADE, Atom.newAtom(clip(curcycle/(float)numcycle,0,1)));
				if(numstepIn == 0)
					outlet(OUT_IN, Atom.newAtom(1f));
				else
					outlet(OUT_IN, Atom.newAtom(clip(curstepIn/(float)numstepIn,0,1)));
				if(numstepOut == 0)
					outlet(OUT_OUT, Atom.newAtom(1f));
				else
					outlet(OUT_OUT, Atom.newAtom(clip(curstepOut/(float)numstepOut,0,1)));
				bang();
				currentTime = System.currentTimeMillis() - startTime;
				long dt = currentTime-idealTime;
//				System.out.println("Delta Time : "+dt);
				float realStepDur = clip(stepdur - dt,0);
				idealTime += stepdur;
				if(curcycle == numcycle)
				{
					System.out.println("Final Delta Time : "+dt);
					System.out.println("End Time : "+currentTime);
					outletBang(OUT_BANG);
					clock.unset();
					idealTime = 0;
				} else {
					clock.delay(realStepDur);//*stepfactor);
				}
			} else {
//				currentTime = System.currentTimeMillis() - startTime;
//				long dt = currentTime-idealTime;
//				System.out.println("Final Delta Time : "+dt);
//				System.out.println("End Time : "+currentTime);
//				outletBang(OUT_BANG);
//				clock.unset();
//				idealTime = 0;
			}
		}
	}
	
	public void pause()
	{
		paused = true;
		outlet(OUT_BANG, Atom.parse("paused 1"));
	}
	
	public void resume()
	{
		paused = false;
		outlet(OUT_BANG, Atom.parse("paused 0"));
	}
	
	protected static float interpol(float a, float b, float step)
	{
		return (a + step * (b - a));
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
	
	public void stop()
	{
		running = false;
		paused = false;
		outlet(OUT_BANG, Atom.parse("paused 0"));
		outlet(OUT_BANG, Atom.parse("running 0"));
		clock.unset();
	}
	
	protected void notifyDeleted()
	{
		if(clock != null)
		{
			clock.unset();
			clock.release();
		}	
	}
}
