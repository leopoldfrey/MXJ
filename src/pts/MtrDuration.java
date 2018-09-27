package pts;

import java.io.BufferedReader;
import java.io.FileReader;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;

import lf.util.IntegerVectorSort;

public class MtrDuration extends MaxObject
{
	IntegerVectorSort times = new IntegerVectorSort();

	public MtrDuration()
	{
		declareIO(1, 1);
		createInfoOutlet(false);
	}

	public void read(String filename)
	{
		String fname = MaxSystem.locateFile(filename);
		if (fname != null)
		{
			try
			{
				times.clear();
				times.add(0);
				BufferedReader in = new BufferedReader(new FileReader(fname));
				String line = in.readLine();
				int tps = 0;
				String[] elements;
				while (line != null)
				{
					if(line.startsWith("track"))
					{
						tps = 0;
					} else if(line.startsWith("end")) {
					} else {
						elements = line.split(" ");
						tps += new Integer(elements[0]).intValue();
						times.add(tps);
					}
					line = in.readLine();
				}
				in.close();
				outlet(0,Atom.parse("duration "+times.lastElement()));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
