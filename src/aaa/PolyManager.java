package aaa;
import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public class PolyManager extends MaxObject
{
	boolean wait_voice_number = false;
	boolean wait_voice_name = false;
	int look_voice = -1;
	String complete;
	String current_name;
	boolean debug = false;
	
	public PolyManager()
	{
		createInfoOutlet(false);
		this.declareIO(3, 3);
		declareAttribute("debug");
	}
	
	public void inlet(int v)
	{
		switch(getInlet())
		{
		case 1:
			if(wait_voice_number)
			{
				wait_voice_number = false;
				if(v == -1)
				{
					if(complete.indexOf("loop") == -1)
					{
						if(debug)
							error("PolyManager : Can not find voice named \""+current_name+"\" and the cmd is not a /play nor a /loop cmd : \""+complete+"\"");
					}
					else {
						// new voice
						outlet(0, Atom.parse("note "+complete));						
					}
				} else {
					// check voice name
					look_voice = v;
					wait_voice_name = true;
					outlet(2, look_voice);
				}
			}
			break;
		}
	}
	
	public void anything(String message, Atom[] args)
	{
		switch(getInlet())
		{
		case 0:
			if(wait_voice_name)
				post("PolyManager (warning) : Allready looking for voice : "+current_name+" "+look_voice+" (should not happen !!!!)");
			if(wait_voice_number)
				post("PolyManager (warning) : Allready looking for voice number : "+current_name+" (should not happen !!!!)");

			current_name = args[0].toString();
			if(current_name.startsWith("/"))
			{
				
				// anonymous command
				if(message.startsWith("/loop"))
					complete = Atom.toOneString(args)+" /loop 1";
				else if(message.startsWith("/play"))
					complete = Atom.toOneString(args)+" /loop 0";
				else
				{
					if(debug)
						error("PolyManager : Can not allocate new voice without a /loop or a /play command, or route to existing voice without a name");
					return;
				}
				complete = "note "+complete;

				// output
				outlet(0, Atom.parse(complete));
				
			} else {
				// named command
				if(message.startsWith("/loop"))
					complete = Atom.toOneString(Atom.removeFirst(args))+" /loop 1 /name "+current_name;
				else if(message.startsWith("/play"))
					complete = Atom.toOneString(Atom.removeFirst(args))+" /loop 0 /name "+current_name;
				else
					complete = Atom.toOneString(concat(message,Atom.removeFirst(args)));

				//post("COMPLETE "+complete);

				// check exist
				wait_voice_number = true;
				look_voice = -1;
				outlet(1, args[0]);
			}
			break;
		case 1:
			//post("received from polyvoices "+ message+" "+Atom.toOneString(args));
			break;
		case 2:
			//post("received from rev-polyvoices "+ message+" "+Atom.toOneString(args));
			if(wait_voice_name)
			{
				wait_voice_name = false;
				if(message.equalsIgnoreCase("none"))
				{
					outlet(0, Atom.parse("note "+complete));
					//post("Current name : "+current_name+" Voice name : "+args[0].toString());
				}
				else if(message.equalsIgnoreCase("symbol"))
				{
					String name = args[0].toString();
					if(name.startsWith("anonymous") || name.equalsIgnoreCase("none") || !name.equalsIgnoreCase(current_name))
					{
						error("PolyManager : Voice already allocated for : "+name);
					} else {
						outlet(0, Atom.parse("target "+look_voice));
						if(complete.indexOf("loop") != -1)
							outlet(0, Atom.parse("/stoptmp"));
						outlet(0, Atom.parse(complete));
						outlet(0, Atom.parse("target 0"));
					}
				}
					
			}
			break;
		}

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
