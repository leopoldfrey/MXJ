import java.util.Map;
import java.util.Properties;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public class JoglLoader extends MaxObject
{
	private boolean DEBUG;
	public static String libpath;

	public JoglLoader(Atom[] args)
	{
		if (args != null)
		{
			switch (args.length)
			{
			case 1:
				DEBUG = args[0].toBoolean();
			default:
				break;
			}
		}

	}

	public void props()
	{
		if (DEBUG)
		{
			Properties props = System.getProperties();
			System.err.println("System Properties : ");
			for (Object o : props.keySet())
				System.err.println("\t" + o.toString() + " = " + props.get(o));
			Map<String, String> env = System.getenv();
			System.err.println("Environnement variables : ");
			for (String s : env.keySet())
				System.err.println("\t" + s + " = " + env.get(s));
		}
		System.out.println("java.library.path=" + System.getProperty("java.library.path"));
	}
	
	public void bang()
	{
		/*boolean ok = true;
		ok = ok && loadLib("jogl_gl2");
		ok = ok && loadLib("gluegen-rt");
		ok = ok && loadLib("nativewindow_awt");
		ok = ok && loadLib("nativewindow_jvm");
		if (ok)
		{
			System.out.println("OK OK OK");//*/
			try
			{
				Object o = Class.forName("com.jogamp.opengl.awt.GLJPanel").getConstructor((Class[])null).newInstance((Object[])null);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
//		}
	}	
	
	public static boolean loadLib(String s)
	{
		try
		{
			System.out.print("Loading library : " + s + " ... ");
			System.loadLibrary(s);
			System.out.println(" done.");
			return true;
		}
		catch (Exception e)
		{
			System.err.println(" failed");
			e.printStackTrace();
		}
		return false;
	}
}
