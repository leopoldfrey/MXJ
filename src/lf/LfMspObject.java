package lf;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxSystem;
import com.cycling74.msp.MSPObject;

public abstract class LfMspObject extends MSPObject
{
	protected static int uid = -1;
	protected final static String ppp = " ...  ";
	public float version;
	public int[] maxversion;
	public boolean max5;
	public String javaversion;
	public boolean mac;
	public boolean standalone;
	protected String build = "";
	private String message;
	public final static float libVersion = 1.4f;
	public final static String libAuthor = "leopold.frey@free.fr";
	public final static String libBuild = "03/09/2009";
	public final static String libMessage = ppp + "   lf.Objects - v" + libVersion + " - " + libAuthor + " - build : " + libBuild;
	protected int[] INLET_TYPES = {};
	protected int[] OUTLET_TYPES = {};
	protected String[] INLET_ASSIST = {};
	protected String[] OUTLET_ASSIST = {};
	protected boolean debug;
	private final String debugStr = ppp + "¥ ";

	protected void usage()
	{
		post("Usage : TODO");
	}

	protected void info()
	{
		post("Info : TODO");
	}

	protected void state()
	{
		post("State : TODO");
	}

	protected void setDebug(boolean b)
	{
		debug = b;
		if (debug)
			System.out.println(ppp+"¥ Debug Mode On");
		else System.out.println(ppp+"o Debug Mode Off");
	}

	protected boolean getDebug()
	{
		return debug;
	}

	public void version()
	{
		System.out.println(message);
	}

	public void libversion()
	{
		System.out.println(ppp+"Library Version:\n"+libMessage);
	}

	protected void debug(String s)
	{
		if (debug)
			System.err.println(this.getClass().getSimpleName()+": " + s);
	}
	
	protected void debug(Exception e)
	{
		if (debug)
			e.printStackTrace(System.err);
	}

	protected void dblclick()
	{
		version();
	}
	
	public void whereami()
	{
		if (debug)
			System.out.println(this.getCodeSourcePath());
	}

	protected void init(int requiredArgsNum, boolean infoOutlet, Atom[] atomList)
	{
		init();
		createInfoOutlet(infoOutlet);
		if (atomList.length < requiredArgsNum)
		{
			version();
			bail(ppp+"Missing arguments :");
			usage();
		}
	}
	
	protected void init()
	{
		maxversion = MaxSystem.getMaxVersionInts();
		max5 = maxversion[0] == 5;
		javaversion = System.getProperty("java.version");
		standalone = MaxSystem.isStandAlone();
		mac = MaxSystem.isOsMacOsX();
		declareAttribute("debug", "getDebug", "setDebug");
		declareInlets(INLET_TYPES);
		declareOutlets(OUTLET_TYPES);
		setInletAssist(INLET_ASSIST);
		setOutletAssist(OUTLET_ASSIST);
		createInfoOutlet(false);
		setName(this.getClass().getName().toString()+"_"+LfObject.getUID());
		message = ppp + "   " + this.getClass().getName().toString() + " - v" + version + " - " + libAuthor + " - build : " + build;
	}
	
	public void contact()
	{
		post("If you're not directly redirected, please send me a mail at leopold.frey-at-free.fr.\nThanks. LŽo.");
		MaxSystem.sendMessageToBoundObject("max","launchbrowser",new Atom[]{Atom.newAtom("mailto:leopold.frey@free.fr?subject=[lf.objects]["+this.getClass().getName().toString()+"]")});
	}
	
	public void bugreport()
	{
		post("If you're not directly redirected, please send me a mail at leopold.frey-at-free.fr.\nDescribe the bug as precisely as possible.\nBe sure you're able to reproduce it.\nThanks. LŽo.");
		String[] prop = { "user.name", "os.name", "os.arch", "os.version", "java.version", "java.vm.specification.version", "java.vm.version", "java.class.version", "java.library.path" };
		
		String tmp = "";
		for(int i = 0 ; i < prop.length ; i++)
		{
			tmp = tmp.concat(prop[i]+" : "+System.getProperty(prop[i])+"\n");
		}
		tmp = tmp.concat("max.version : "+toString(MaxSystem.getMaxVersionInts(),".")+"\n");
		tmp = tmp.concat("max.class.path :\n"+toString(MaxSystem.getClassPath(),"\n")+"\n");
		tmp = tmp.concat("max.system.standalone : "+MaxSystem.isStandAlone()+"\n");
		MaxSystem.sendMessageToBoundObject("max","launchbrowser",new Atom[]{
				Atom.newAtom("mailto:leopold.frey@free.fr?subject=[lf.objects]["
						+ this.getClass().getName().toString() 
						+ "][bug]&body=----------------------------------------\n"+tmp+"----------------------------------------\nPlease describe the bug as precisely as possible.\nBe sure you're able reproduce it.\nThanks. Leo")});
	}
	
	public void licence()
	{
		MaxSystem.sendMessageToBoundObject("max","launchbrowser",
				new Atom[]{Atom.newAtom("http://creativecommons.org/licenses/by/3.0/")});
	}
	
	protected void error()
	{
		post(debugStr + "[" + this.getClass().getName().toString()+"]");
		usage();
	}
	
	public void javaversion()
	{
		post("java.version:"+System.getProperty("java.version"));
	}
	
	private String toString(int[] a, String inter)
	{
		String tmp = "";
		for(int i = 0; i < a.length ; i++)
			tmp = tmp.concat(a[i]+inter);
		return tmp;
	}
	
	private String toString(String[] a, String inter)
	{
		String tmp = "";
		for(int i = 0; i < a.length ; i++)
			tmp = tmp.concat(a[i]+inter);
		return tmp;
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
}
