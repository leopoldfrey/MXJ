import com.cycling74.max.Atom;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxPatcher;
import com.cycling74.max.MaxSystem;
import com.cycling74.msp.MSPBuffer;

public class BufSplitBak extends MaxObject implements Executable
{
	MaxPatcher parent;
	int bufcount;
	long duration;
	int durSmp;
	int max;
	int current;
	String name;
	String frombuf;

	public BufSplitBak()
	{
		parent = getParentPatcher();
	}

	public void split(String from, String n, int dur, float sr)
	{
		frombuf = from;
		duration = dur;
		name = n;
		System.out.println(frombuf + " " + name + " " + duration + " " + sr);
		float size = MSPBuffer.getSize(frombuf) / MSPBuffer.getChannels(frombuf);
		System.out.println("size : " + size);
		durSmp = (int) (duration * sr);
		System.out.println("durSmp : " + durSmp);
		max = (int) size / durSmp;
		System.out.println("max : " + max);
		for (int i = 0; i < max; i++)
		{
			parent.newDefault(10, bufcount * 25 + 5, "buffer~", Atom.parse((name + i)+" "+duration));
			bufcount++;
		}
		outlet(0, max);
		MaxSystem.deferLow(this);
	}
	
	public void execute()
	{
		current = 0;
		for (int i = 0; i < max; i++)
		{
			System.out.println("getsize : "+MSPBuffer.getSize(name+i));
//			System.out.println("resize : "+(name+i)+" "+durSmp);
//			MSPBuffer.setSize(name + i, 1, durSmp);
			MSPBuffer.poke(name + i,0,0,MSPBuffer.peek(frombuf, 0, current, durSmp));
			current += durSmp;
		}
	}
}
