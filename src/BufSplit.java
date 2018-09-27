import com.cycling74.max.Atom;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxBox;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxPatcher;
import com.cycling74.max.MaxSystem;
import com.cycling74.msp.MSPBuffer;

public class BufSplit extends MaxObject implements Executable
{
	MaxPatcher parent;
	MaxBox tmpbuf;
	int bufcount = 0;
	int duration;
	long durSmp;
	int max;
	int current;
	String tobuf;
	String frombuf;
	String path;
	String name;

	public BufSplit()
	{
		parent = getParentPatcher();
	}

	public void split(String from, String to, int dur, float sr, String n, String p)
	{
		frombuf = from;
		duration = dur;
		tobuf = to;
		name = n;
		path = p;
		float size = MSPBuffer.getSize(frombuf) / MSPBuffer.getChannels(frombuf);
		durSmp = (long) (duration * sr);
		max = (int) (size / durSmp);
		tmpbuf = parent.getNamedBox("tmp");
		MaxSystem.deferLow(this);
	}
	
	public void execute()
	{
		current = 0;
		for (int i = 0; i < max; i++)
		{
			MSPBuffer.setSize(tobuf, 1, durSmp);
			MSPBuffer.poke(tobuf,0,0,MSPBuffer.peek(frombuf, 0, current, durSmp));
			tmpbuf.send("writeaiff", Atom.parse(path+name+bufcount+".aiff"));
			current += durSmp;
			bufcount++;
		}
		outlet(0, max);
	}
}
