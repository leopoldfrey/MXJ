import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class Splitter extends LfObject
{
	private String exp = "";

	public Splitter(Atom[] atoms)
	{
		version = 0.1f;
		INLET_ASSIST = new String[] { "String to be splitted.", "Regular expression to be matched for splitting" };
		OUTLET_ASSIST = new String[] { "Splitted string 1","Splitted string 2" };
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };

		declareAttribute("exp","getExp","setExp");
		
		init(0,false,atoms);
		
		switch(atoms.length)
		{
		default :
		case 1 :
			if(atoms[0].isString())
				setExp(atoms[0].toString());
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
			if (!exp.equalsIgnoreCase("") && result.lastIndexOf(exp) != -1)
			{
				String[] res2 = result.split(exp,2);
				Atom[] res2a = Atom.removeFirst(Atom.newAtom(res2));
				outlet(1, res2a);
				outlet(0, Atom.parse(res2[0]));
			}
			else outlet(0, Atom.parse(result));
		}
	}

	public void usage()
	{
		post(ppp+"Usage:\n"
			+ppp+"   Optional arguments :\n"
			+ppp+"   	arg1 : \'exp\' regular expression to be matched\n"
			+ppp+"   Attributes :"
			+ppp+"   	@exp (see arguments)\n"
			);
	}

	public void info()
	{
		post(ppp+"Info:\n"+ppp+"   Splits this string in two around matches of the given regular expression.");
	}
	
	public void state()
	{
		post(ppp+"State:\n"+ppp+"   Sub:\""+exp+"\"");
	}

	protected String getExp()
	{
		return exp;
	}

	protected void setExp(String exp)
	{
		this.exp = exp;
		debug("Exp:\""+this.exp+"\"");
	}

}
