import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class SubStringer extends LfObject
{
	private String sub = "";
	private int mode = 0;

	public SubStringer(Atom[] atoms)
	{
		version = 0.2f;
		build = "03/04/06";
		INLET_ASSIST = new String[] { "String to be changed." };
		OUTLET_ASSIST = new String[] { "Result String." };
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };

		declareAttribute("sub","getSub","setSub");
		declareAttribute("mode","getMode","setMode");
		
		init(0,false,atoms);
		
		switch(atoms.length)
		{
		default :
			if(atoms[0].isInt())
			{
				setMode(atoms[0].getInt());
				setSub(Atom.toOneString(subAtom(atoms,1,atoms.length)));
			} else {
				setSub(Atom.toOneString(atoms));
			}
			break;
		case 1 :
			if(atoms[0].isInt())
				setMode(atoms[0].getInt());
			else setSub(atoms[0].toString());
			break;
		case 0 :
			break ;
		}
	}

	public void anything(String message, Atom[] atoms)
	{
		String result = "";
		if (message != null && !message.equalsIgnoreCase(""))
		{
			if (atoms != null && !message.equalsIgnoreCase(""))
				result = message.concat(" " + Atom.toOneString(atoms));
			if (!sub.equalsIgnoreCase("") && result.lastIndexOf(sub) != -1)
			{
				int i = result.lastIndexOf(sub);
				if (i <= result.length())
				{
					if (mode == 0)
						outlet(0, Atom.parse(result.substring(0, i)));
					else outlet(0, Atom.parse(result.substring(i + sub.length(), result.length())));
				}
			}
			else outlet(0, Atom.parse(result));
		}
	}

	public void usage()
	{
		post(ppp+"Usage:\n"+ppp+"   Optional arguments :\n"+ppp+"   \'mode\' arg1 (int 0-1)\n"+ppp+"   \'sub\' arg1|arg2 ... argN\n"+ppp+"   \'mode\' = 0 (default) : returns the input string starting from the last occurence of \'sub\'\n"+ppp+"   \'mode\' = 1 : returns the input string until the last occurence of \'sub\'.");
	}

	public void info()
	{
		post(ppp+"Info:\n"+ppp+"   Deletes a substring from a string (see info for details).");
	}
	
	public void state()
	{
		post(ppp+"State:\n"+ppp+"   Sub:\""+sub+"\"   Mode:"+mode);
	}

	protected int getMode()
	{
		return mode;
	}

	protected void setMode(int mode)
	{
		this.mode = clip(mode,0,1);
		debug("Mode:"+this.mode);
	}

	protected String getSub()
	{
		return sub;
	}

	protected void setSub(String sub)
	{
		this.sub = sub;
		debug("Sub:\""+this.sub+"\"");
	}

}
