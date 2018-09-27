package lf;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxBox;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;

public abstract class LfObject extends MaxObject
{
	private static int id_inc = 0;
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
	private final static float libVersion = 1.5f;
	private final static String libAuthor = "leopold.frey@free.fr";
	private final static String libBuild = "16/03/2011";
	private String libMessage = "";
	protected int[] INLET_TYPES = {};
	protected int[] OUTLET_TYPES = {};
	protected String[] INLET_ASSIST = {};
	protected String[] OUTLET_ASSIST = {};
	protected boolean debug;
	private final String debugStr = ppp + "� ";

	public void usage()
	{
		post("Usage : TODO");
	}

	public void info()
	{
		post("Info : TODO");
	}

	public void state()
	{
		post("State : TODO");
	}

	protected void setDebug(boolean b)
	{
		debug = b;
		if (debug)
			post(ppp+"� Debug Mode On");
		else post(ppp+"o Debug Mode Off");
	}

	protected boolean getDebug()
	{
		return debug;
	}

	public void version()
	{
		post(message);
	}

	public void libversion()
	{
		post(ppp+"Library Version:\n"+libMessage);
	}

	protected void debug(String s)
	{
		if (debug)
			error(this.getClass().getSimpleName()+": " + s);
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
			post(this.getCodeSourcePath());
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
//		System.out.println("LfObject Init");
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
		setName(this.getClass().getName().toString()+"_"+getUID());
		message = ppp + "   " + this.getClass().getName().toString() + " - v" + version + " - " + libAuthor + " - build : " + build;
		libMessage = ppp + "   lf.Objects - v" + libVersion + " - " + libAuthor + " - build : " + libBuild;
	}
	
	public void contact()
	{
		post("If you're not directly redirected, please send me a mail at leopold.frey-at-free.fr.\nThanks. L�o.");
		MaxSystem.sendMessageToBoundObject("max","launchbrowser",new Atom[]{Atom.newAtom("mailto:leopold.frey@free.fr?subject=[lf.objects]["+this.getClass().getName().toString()+"]")});
	}
	
	public void bugreport()
	{
		post("If you're not directly redirected, please send me a mail at leopold.frey-at-free.fr.\nDescribe the bug as precisely as possible.\nBe sure you're able to reproduce it.\nThanks. L�o.");
		String[] prop = { "user.name", "os.name", "os.arch", "os.version", "java.version", "java.vm.specification.version", "java.vm.version", "java.class.version", "java.library.path" };
		
		String tmp = "";
		for(int i = 0 ; i < prop.length ; i++)
		{
			tmp = tmp.concat(prop[i]+" : "+System.getProperty(prop[i])+"\n");
		}
		tmp = tmp.concat("path : "+this.getCodeSourcePath()+"\n");
		tmp = tmp.concat("max.version : "+toString(maxversion,".")+"\n");
		tmp = tmp.concat("max.class.path :\n"+toString(MaxSystem.getClassPath(),"\n")+"\n");
		tmp = tmp.concat("max.system.standalone : "+standalone+"\n");
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
	
	protected String toString(int[] a, String inter)
	{
		String tmp = "";
		for(int i = 0; i < a.length ; i++)
			tmp = tmp.concat(a[i]+inter);
		return tmp;
	}
	
	protected String toString(String[] a, String inter)
	{
		String tmp = "";
		for(int i = 0; i < a.length ; i++)
			tmp = tmp.concat(a[i]+inter);
		return tmp;
	}	
		
	protected String toString(Atom[] a, String inter)
	{
		String tmp = "";
		for(int i = 0; i < a.length ; i++)
			tmp = tmp.concat(a[i].toString()+inter);
		return tmp;
	}	
		
	// UTILS
	
	protected static boolean between(int i, int l, int u)
	{
		return i >= l && i <= u;
	}

	protected static boolean between(float i, float l, float u)
	{
		return i >= l && i <= u;
	}
	
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
	
	protected static Atom[] concat(Atom[] a, Atom[] b)
	{
		Atom[] tmp = new Atom[a.length + b.length];
		System.arraycopy(a, 0, tmp, 0, a.length);
		System.arraycopy(b, 0, tmp, a.length, b.length);
		return tmp;
	}
	
	protected static Atom[] concat(String s, Atom[] args)
	{
		return concat(new Atom[]{Atom.newAtom(s)},args);
	}
	
	protected static Atom[] concat(Atom[] args, String s)
	{		
		return concat(args,new Atom[]{Atom.newAtom(s)});
	}
	
	protected static Atom[] concat(Atom a, Atom[] args)
	{
		return concat(new Atom[]{a},args);
	}
	
	protected static Atom[] concat(Atom[] args, Atom a)
	{		
		return concat(args,new Atom[]{a});
	}

	// TODO LFOBJECT v�rifier sub
	protected static Atom[] sub(Atom[] args, int start, int length)
	{
		return Atom.removeFirst(Atom.removeLast(args,length),start);
	}
	
	protected static int sign(float f)
	{
		return f >= 0 ? 1 : -1;
	}
	
	protected static float abs(float f)
	{
		return f * sign(f);
	}

	protected static String getUID()
	{
		uid = id_inc++;
		return "uid"+uid;
	}
	
	public String toString()
	{
		return getName();
	}
	
	public static boolean isPatcher(MaxBox b)
	{
		return b.getMaxClass().equalsIgnoreCase("patcher") || b.getMaxClass().equalsIgnoreCase("jpatcher");
	}

	public float dbtoa(float db)
	{
		if(db <= -96)
			return 0;
		return (float)Math.pow(10, db/20f);
	}
	
	public float atodb(float a)
	{
		if(a < 0.000017)
			return -96;
		return (float)(20 * Math.log10(a));
	}
}

