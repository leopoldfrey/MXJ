package aaa;
import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public class HasName extends MaxObject
{
	public HasName(Atom[] atoms)
	{
		createInfoOutlet(false);
	}

	public void anything(String message, Atom[] atoms)
	{
		if (message != null && !message.equalsIgnoreCase(""))
			if (message.startsWith("/"))
				outlet(0, message + " " + Atom.toOneString(atoms));
			else outlet(0, Atom.toOneString(atoms) + " /name " + message + " ");
	}
}
