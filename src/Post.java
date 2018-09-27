import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class Post extends LfObject
{
	private int mode = 0;
	private String suffix = "";
	private String prefix = "";
	private String interfix = "";
	private String memory = "";
	private boolean autoline = true;

	public Post(Atom[] atoms)
	{
		version = 0.1f;
		build = "03/04/08";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "String to be posted to the console." };
		OUTLET_ASSIST = new String[] { "Resulting String."};

		init(0, false, atoms);
		
		declareAttribute("suffix","getSuffix","setSuffix");
		declareAttribute("prefix","getPrefix","setPrefix");
		declareAttribute("interfix","getInterfix","setInterfix");
		declareAttribute("autoline","isAutoline","setAutoline");
		declareAttribute("mode","getMode","setMode");
		
		switch(atoms.length)
		{
		case 2 :
			if(atoms[1].isInt())
				setAutoline(atoms[0].toBoolean());
		case 1 :
			if(atoms[0].isInt())
				setMode(atoms[0].getInt());
		case 0 :
		default :
			break;
		}
	}

	public void anything(String message, Atom[] atoms)
	{
		if (autoline)
		{
			if (!memory.equalsIgnoreCase(""))
				memory = "";
			if (!message.equalsIgnoreCase("") && atoms.length != 0)
				message = message.concat(interfix);
			output(prefix + message + (atoms.length > 1 ? " " : "") + interfix(atoms) + suffix);
		}
		else
		{
			if (!memory.equalsIgnoreCase(""))
				memory = memory.concat((interfix.equalsIgnoreCase("") ? " " : interfix));
			memory = memory.concat(message + (atoms.length > 1 ? " " : "") + Atom.toOneString(atoms));
		}
	}
	
	private void output(String s)
	{
		if (mode == 0)
			post(s);
		else if (mode == 1)
			outlet(0, Atom.parse(s));
		else
		{
			outlet(0, Atom.parse(s));
			post(s);
		}
	}

	public void inlet(int i)
	{
		anything("", new Atom[] { Atom.newAtom(i) });
	}

	public void inlet(float f)
	{
		anything("", new Atom[] { Atom.newAtom(f) });
	}

	public void list(Atom[] atoms)
	{
		anything("", atoms);
	}

	public void bang()
	{
		if (autoline)
			anything("", new Atom[] { Atom.newAtom("bang") });
		else
		{
			if (!memory.equalsIgnoreCase(""))
			{
				output(prefix + memory + suffix);
				clear();
			}
		}
	}

	private String interfix(Atom[] atoms)
	{
		if (interfix.equalsIgnoreCase(""))
			return Atom.toOneString(atoms);
		
		String tmp = "";
		for (int i = 0; i < atoms.length; i++)
		{
			if (tmp.equalsIgnoreCase(""))
				tmp = atoms[i].toString();
			else tmp = tmp + interfix + atoms[i];
		}
		return tmp;
	}

	public void clear()
	{
		memory = "";
		debug("Memory Cleared");
	}
	
	protected boolean isAutoline()
	{
		return autoline;
	}

	private void setAutoline(boolean autoline)
	{
		this.autoline = autoline;
		debug("Autoline:"+autoline);
	}

	protected String getInterfix()
	{
		return interfix;
	}

	protected void setInterfix(String interfix)
	{
		this.interfix = interfix;
		debug("Interfix:\'"+interfix+"\'");
	}

	protected String getPrefix()
	{
		return prefix;
	}

	protected void setPrefix(String prefix)
	{
		this.prefix = prefix;
		debug("Prefix:\'"+prefix+"\'");
	}

	protected String getSuffix()
	{
		return suffix;
	}

	protected void setSuffix(String suffix)
	{
		this.suffix = suffix;
		debug("Suffix:\'"+suffix+"\'");
	}

	protected int getMode()
	{
		return mode;
	}

	protected void setMode(int _mode)
	{
		mode = clip(_mode,0,2);
		debug("Mode:"+mode);
	}
	
	public void usage()
	{
		post(
				ppp+"Usage:"
				+"\n"+ppp+"   Optional arguments :"
				+"\n"+ppp+"    arg1 = mode (output mode) :"
				+"\n"+ppp+"      0 (default) : Prints the result in max console."
				+"\n"+ppp+"      1 : Outputs the result in the first outlet."
				+"\n"+ppp+"      2 : Both."
				+"\n"+ppp+"    arg2 = autoline (format mode) :"
				+"\n"+ppp+"      0 : Format & outputs the result directly after receiving a message in input."
				+"\n"+ppp+"      1 (default) : Format the input string and store it (outputs on next bang, clear with \'clear' message)."
			);
		post(
				ppp+"   Attributes :"
				+"\n"+ppp+"      @mode : see \'arg1\'."
				+"\n"+ppp+"      @autoline : see \'arg2\'."
				+"\n"+ppp+"      @prefix : string to insert at the beginning of the input string (empty string resets it)."
				+"\n"+ppp+"      @interfix :"
				+"\n"+ppp+"                  if autoline = 1 : string to insert between each elements of the input string,"
				+"\n"+ppp+"                  if autoline = 0 : string to insert between each input string."
				+"\n"+ppp+"                  (empty string resets it)."
				+"\n"+ppp+"      @suffix  string to insert at the end of the input string (empty string resets it)."
			);
		post(
				ppp+"   Messages :"
				+"\n"+ppp+"      bang : if autoline = 0 outputs a bang,"
				+"\n"+ppp+"             if autoline = 1 outputs the current stored string."
				+"\n"+ppp+"      clear : clears the stored string."
			);
		post(
				ppp+"   Inputs :"
				+"\n"+ppp+"      anything : considered as a string to be formatted and output."
				+"\n"+ppp+"   Outputs :"
				+"\n"+ppp+"      if mode = 0 : nothing,"
				+"\n"+ppp+"      else the formatted string."
			);
	}

	public void info()
	{
		post(ppp+"Info:\n"+ppp+"   Console Manager | String Formatter (see usage for more...)");
	}
	
	public void state()
	{
		post(ppp+"State:\n"
			+"\n"+ppp+"      Mode:"+mode
			+"\n"+ppp+"      Autoline:"+autoline
			+"\n"+ppp+"      Prefix:\'"+prefix+"\'"
			+"\n"+ppp+"      Interfix:\'"+interfix+"\'"
			+"\n"+ppp+"      Suffix:\'"+suffix+"\'"
		);
	}

}
