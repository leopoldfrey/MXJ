import java.util.Map;
import java.util.Properties;

import com.cycling74.max.Atom;

import lf.LfObject;

public class PropsEnvs extends LfObject
{
	Properties props;
	Map<String, String> envs;

	public PropsEnvs()
	{
		declareIO(1, 1);
		bang();
	}

	public void bang()
	{
		props = System.getProperties();
		System.out.println("System Properties : ");
		for (Object o : props.keySet())
			System.out.println("\t" + o.toString() + " = " + props.get(o));
		envs = System.getenv();
		System.out.println("Environnement variables : ");
		for (String s : envs.keySet())
			System.out.println("\t" + s + " = " + envs.get(s));
	}

	public void env()
	{
		envs = System.getenv();
		for (String s : envs.keySet())
			outlet(0, Atom.parse("env " + s + " " + envs.get(s)));
	}

	public void env(String s)
	{
		outlet(0, Atom.parse("env " + s + " " + envs.get(s)));
	}

	public void prop()
	{
		props = System.getProperties();
		for (Object o : props.keySet())
			outlet(0, Atom.parse("prop " + o.toString() + " " + props.get(o)));
	}

	public void prop(String s)
	{
		outlet(0, Atom.parse("prop " + s + " " + props.get(s).toString()));
	}
	
	public void setProp(String s, String v)
	{
		System.setProperty(s, v);
	}
	
	public void appendProp(String s, String v)
	{
		System.setProperty(s, System.getProperty(s)+v);
	}
}
