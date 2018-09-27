import java.util.StringTokenizer;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

// SPLITS ALL ATOMS AS ONE STRING

public class Splitter2 extends LfObject
{
	private String exp = ".";
	
	public Splitter2(Atom[] atoms)
	{
		version = 0.1f;
		build = "8/12/12";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "String to be splitted" };
		OUTLET_ASSIST = new String[] { "Splitted String" };
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
		if (message != null && !message.equalsIgnoreCase("") && atoms != null && !message.equalsIgnoreCase(""))
			process(Atom.union(Atom.newAtom(new String[]{message}),atoms));
	}

	public void list(Atom[] atoms)
	{
		if(atoms != null)
			process(atoms);
	}
	
	private void process(Atom[] atoms)
	{
		String str = Atom.toOneString(atoms);
		//post("String to be splitted : "+str);
		StringTokenizer st = new StringTokenizer(str, exp); 
		String fistr = "";
		while (st.hasMoreTokens()) { 
			fistr += st.nextToken() + " ";
		}
		//post("Result : "+fistr);
		outlet(0, Atom.parse(fistr));
		/*if (!exp.equalsIgnoreCase("") && str.lastIndexOf(exp) != -1)
		{
			String[] res2 = str.split(exp,2);
			if(res2.length > 0)
			{
				Atom[] res2a = Atom.removeFirst(Atom.newAtom(res2));
				res2a = Atom.parse(exp+toOneString(res2a).substring(1));
				//post("end: "+Atom.toDebugString(res2a));
				outlet(1, res2a);
			}
			Atom[] test = Atom.parse(res2[0]);
			//post("begin: "+Atom.toDebugString(test));
			outlet(0, test);
		}
		else outlet(0, atoms);//*/
	}

/*	public void usage()
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
	}//*/

	protected String getExp()
	{
		return exp;
	}

	protected void setExp(String exp)
	{
		this.exp = exp;
		//debug("Exp:\""+this.exp+"\"");
	}

/*	protected String toOneString(Atom[] array)
	{
		StringBuffer sb = new StringBuffer();
		boolean appendSpace = false;

		for(int i=0;i<array.length;i++)
		{
			if(i > 0)
				sb.append(' ');
			sb.append(array[i].toString());
		}

		return sb.toString();
	}

	protected String toOneString(String[] array)
	{
		StringBuffer sb = new StringBuffer();
		boolean appendSpace = false;

		for(int i=0;i<array.length;i++)
		{
			if(i > 0)
				sb.append(' ');
			sb.append(array[i].toString());
		}

		return sb.toString();
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

	// TODO SPLITTER v�rifier sub
	protected static Atom[] sub(Atom[] args, int start, int length)
	{
		return Atom.removeFirst(Atom.removeLast(args,length),start);
	}//*/
}
