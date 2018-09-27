import java.util.Stack;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;


public class ListMean extends MaxObject
{
	Stack<float[]> lists = new Stack<float[]>();
	int size = 10;
	int rsize = 0;
	
	public void inlet(int i)
	{
		size = i > 0 ? i : 1;
	}
	
	public void list(Atom[] args)
	{
		lists.push(Atom.toFloat(args));
		if (lists.size() > size)
			lists.remove(0);

		rsize = lists.size();
		outlet(1, rsize);
		if(rsize == 1)
		{
			outlet(0, args);
		}
		else
		{
			float[] output = new float[args.length];
			
			for (int i = 0 ; i < output.length ; i++)
				output[i] = 0;
			
			for (float[] l : lists)
				for(int i = 0 ; i < output.length ; i++)
					output[i] += l[i];

			for (int i = 0 ; i < output.length ; i++)
				output[i] /= rsize;
			
			outlet(1, rsize);
			outlet(0, Atom.newAtom(output));
		}
	}
	
	public void clear()
	{
		lists = new Stack<float[]>();	
		rsize = 0;
		outlet(1, rsize);
	}
}
