import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;

public class PrintDebugString extends MaxObject
{
	protected int[] INLET_TYPES = {};
	protected int[] OUTLET_TYPES = {};
	protected String[] INLET_ASSIST = {};
	protected String[] OUTLET_ASSIST = {};

	String dstr = "";
	
	public PrintDebugString()
	{
		init();
	}
	
	public PrintDebugString(Atom[] atoms)
	{
		init();
		if(atoms.length > 0)
			dstr = toOneString(atoms);
	}

	public void init()
	{
		INLET_ASSIST = new String[] { "" };
		OUTLET_ASSIST = new String[] { };
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { };

		declareInlets(INLET_TYPES);
		declareOutlets(OUTLET_TYPES);
		setInletAssist(INLET_ASSIST);
		setOutletAssist(OUTLET_ASSIST);
		createInfoOutlet(false);
	}

	public void anything(String message, Atom[] atoms)
	{
		if (message != null && atoms != null)
			process(concat(message,atoms));
	}

	public void list(Atom[] atoms)
	{
		if(atoms != null)
			process(atoms);
	}
	
	private void process(Atom[] atoms)
	{
		post(dstr+" "+Atom.toDebugString(atoms));
	}

	protected String toOneString(Atom[] array)
	{
		StringBuffer sb = new StringBuffer();
		boolean appendSpace = false;

		for(int i=0;i<array.length;i++)
		{
			sb.append(' ');
			sb.append(array[i].toString());
		}

		return sb.toString();
	}

	protected String toOneString(String[] array)
	{
		StringBuffer sb = new StringBuffer();

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

	// TODO SPLITTER vérifier sub
	protected static Atom[] sub(Atom[] args, int start, int length)
	{
		return Atom.removeFirst(Atom.removeLast(args,length),start);
	}
}
