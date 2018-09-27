package aaa;
import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public class PrintDebugString extends MaxObject
{
	String dstr = "";
	
	public PrintDebugString()
	{
		init();
	}
	
	public PrintDebugString(Atom[] atoms)
	{
		init();
		if(atoms.length > 0)
			dstr = Atom.toOneString(atoms);
	}

	public void init()
	{
		declareIO(1,0);
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
}
