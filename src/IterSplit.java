import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class IterSplit extends LfObject
{
	private String exp = "";

	public IterSplit(Atom[] atoms)
	{
		version = 0.1f;
		build = "17/09/06";
		INLET_ASSIST = new String[] { "String to be splitted.", "Regular expression to be matched for splitting" };
		OUTLET_ASSIST = new String[] { "Splitted strings, one after the other.", "Number of splitted strings" };
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.INT };

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
				String[] res2 = result.split(exp);
				outlet(1,res2.length);
				for(int i = 0, last = res2.length ; i < last ; i++)
					outlet(0, Atom.parse(res2[i]));
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
			+ppp+"   Output :"
			+ppp+"   	outlet 1 : itered splitted strings"
			+ppp+"   	outlet 2 : number of itered splitted strings"
			);
	}

	public void info()
	{
		post(ppp+"Info:\n"+ppp+"   Splits and iters strings around matches of the given regular expression.");
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
