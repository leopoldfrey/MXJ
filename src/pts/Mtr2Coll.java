package pts;
import java.io.BufferedReader;
import java.io.FileReader;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;

import lf.util.IntegerVectorSort;

public class Mtr2Coll extends MaxObject
{
	IntegerVectorSort times = new IntegerVectorSort();
	
	public Mtr2Coll()
	{
		declareIO(1, 2);
		createInfoOutlet(false);
	}

	public void read(String filename)
	{
		String fname = MaxSystem.locateFile(filename);
		if (fname != null)
		{
			try
			{
				outlet(0, Atom.newAtom("clear"));
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
					} else if (line.startsWith("end")) {
					} else {
						elements = line.split(" ");
						tps += new Integer(elements[0]).intValue();
						times.add(tps);
					}
					line = in.readLine();
				}
				// Init Times
				int prev = 0;
				int c = 0;
				for(int i : times)
				{
					outlet(0, Atom.parse(c+" 0 "+((i-prev)/1000.f)+" 0 0 0 0 0 0 0 0 0 0"+(c == 0 ? " Start" : "")));
					prev = i;
					c++;
				}
				outlet(0, Atom.parse(times.size()+" 0 0 0 0 0 0 0 0 0 0 0 0 End"));
				in.close();
				in = new BufferedReader(new FileReader(fname));
				line = in.readLine();
				tps = 0;
				int tk = 0;
				int value = 0;
				int tim = 0;
				int pos = 0;
				int prevPos = 0;
				int prevValue = 0;
				while (line != null)
				{
					if(line.startsWith("track"))
					{
						tps = 0;
						tk = new Integer(line.split(" ")[1].substring(0,1)).intValue();
					} else if (line.startsWith("end")) {
					} else {
						elements = line.split(" ");
						tim = new Integer(elements[0]).intValue();
						if(elements[1].equalsIgnoreCase("int"))
						{
							value = new Integer(elements[2].substring(0,elements[2].length()-1)).intValue();
						} else {
							value = new Integer(elements[1].substring(0,elements[1].length()-1)).intValue();
						}
						tps += tim;
						pos = times.indexOf(tps);
						for(int i = prevPos+1 ; i < pos ; i++)
						{
							outlet(0, Atom.parse("nsub "+i+" "+(tk+2)+" "+prevValue));
						}
						outlet(0, Atom.parse("nsub "+pos+" "+(tk+2)+" "+value));
						prevPos = pos;
						prevValue = value;
					}
					line = in.readLine();
				}
				in.close();
				outlet(0,new Atom[]{Atom.newAtom("write"),Atom.newAtom(filename.substring(0,filename.lastIndexOf(".txt")).concat(".coll.txt"))});
				outletBang(1);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
