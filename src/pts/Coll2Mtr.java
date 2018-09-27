package pts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;

public class Coll2Mtr extends MaxObject
{
	public Coll2Mtr()
	{
		declareIO(1, 2);
		createInfoOutlet(false);
	}
	
	public void read(String filename)
	{
		String fname = MaxSystem.locateFile(filename);
		String outfname = fname.substring(0,fname.lastIndexOf(".coll.txt")).concat(".mtr.txt");
		if (fname != null)
		{
			try
			{
				outlet(0, Atom.newAtom("clear"));
				BufferedWriter out = new BufferedWriter(new FileWriter(outfname));
				int v;
				int t;
				int tmpT = 0;
				int maxtime = 0;
				int prevV;
				int prevT;
				String[] elems;
				for(int tk = 1 ; tk <= 10 ; tk++)
				{
					out.write("track "+tk+";");
					out.newLine();
					out.write("0 0;");
					out.newLine();
					t = 0;
					tmpT = 0;
					prevV = 0;
					BufferedReader in = new BufferedReader(new FileReader(fname));
					String line = in.readLine();
					while (line != null)
					{
						elems = line.split(";")[0].split(" ");
						int cT = new Float((new Float(elems[2])*1000.f)).intValue();
						tmpT += cT;
						t += cT;
						v = new Integer(elems[tk+2]).intValue();
						if(v != prevV)
						{
							out.write(t+" "+v+";");
							out.newLine();
							prevV = v;
							t = 0;
						}
						line = in.readLine();
					}
					maxtime = Math.max(tmpT, maxtime);
					in.close();
					out.write("end;");
					out.newLine();
				}
				out.close();
				outlet(1, Atom.parse("duration "+maxtime));
				outlet(0, new Atom[]{Atom.newAtom("read"),Atom.newAtom(outfname)});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
