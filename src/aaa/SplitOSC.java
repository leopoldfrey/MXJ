package aaa;
import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public class SplitOSC extends MaxObject
{
	private String exp1 = "/";
	private String exp2 = " /";

	public SplitOSC(Atom[] atoms)
	{
		declareIO(1,3);
		createInfoOutlet(false);
	}

	public void anything(String message, Atom[] atoms)
	{
		String result = "";
		if (message != null && !message.equalsIgnoreCase(""))
		{
			if (atoms != null && !message.equalsIgnoreCase(""))
				result = message.concat(" " + Atom.toOneString(atoms));
			if(result.startsWith(exp1))
				result = result.substring(1);
			if (result.lastIndexOf(exp2) != -1)
			{
				String[] res2 = result.split(exp2);
				for(String s : res2)
					if(s.startsWith("sound") || s.startsWith("cue"))
						outletBang(2);
				boolean found = false;
				int i = 0;
				String s;
				while(!found && i < res2.length-1)
				{
					s = res2[i];
					if(s.equalsIgnoreCase("sound"))
					{
						found = true;
						res2[i] = res2[i] + " /" + res2[i+1];
						res2[i+1] = "none";
					}
					i++;
				}
				outlet(1,res2.length);
				for(i = res2.length-1; i >= 0  ; i--)
					outlet(0, Atom.parse(res2[i]));
			}
			else {
				if(result.startsWith("sound") || result.startsWith("cue"))
					outletBang(2);					
				outlet(1,1);
				outlet(0, Atom.parse(result));
			}
		}
	}
}
