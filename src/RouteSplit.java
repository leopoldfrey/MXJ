import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;
import lf.util.FloatVectorSort;

public class RouteSplit extends LfObject
{
	FloatVectorSort range;
	float val = 0;
	
	public RouteSplit(Atom[] atoms)
	{
		version = 0.2f;
		build = "11/06/07";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL,DataTypes.ALL };
		INLET_ASSIST = new String[] { "list of float forming ranges" };
		OUTLET_ASSIST = new String[] { "list {interval value}", "info" };
		init(0,false,atoms);
		
		clear();
		range(atoms);
	}
	
	public void clear()
	{
		range = new FloatVectorSort();
	}
	
	public void bang()
	{
		if(range.isEmpty())
			outlet(0,new float[]{-1,val});
		else {
			for(int i = 0 ; i < range.size() ; i++)
				if(val < range.get(i))
				{
					outlet(0,new float[]{i,val});
					return;
				}
			outlet(0,new float[]{range.size(),val});
		}
	}
	
	public void inlet(int i)
	{
		val = i;
		bang();
	}
	
	public void inlet(float f)
	{
		val = f;
		bang();
	}
	
	public void range(Atom atoms[])
	{
		if(atoms.length != 0)
		{
			clear();
			range.add(Atom.toFloat(atoms));
		}
		getrange();
		getsize();
	}
	
	public void getrange()
	{
		if(!range.isEmpty())
			outlet(1,Atom.parse("range "+range.toString()));
		else
			outlet(1,Atom.parse("range empty"));
	}
	
	public void getsize()
	{
		if(!range.isEmpty())
			outlet(1,Atom.parse("size "+(range.size()+1)));
		else
			outlet(1,Atom.parse("size 0"));
	}
	
	public void setrange(Atom atoms[])
	{
		if(atoms.length >= 2)
		{
			int pos = clip(atoms[0].toInt(), 0, range.size());
			float value = atoms[1].toFloat();
			if(!range.isEmpty() && pos < range.size())
				range.setElementAt(value, pos);
			else if(pos == range.size())
				range.add(value);
			else
				outlet(1,Atom.parse("range empty"));
			getrange();
			getsize();
		}
	}
	
	public void getrange(int i)
	{
		if(!range.isEmpty())
		{
			int pos = clip(i, 0, range.size()-1);
			outlet(1,Atom.parse("range "+pos+" "+range.get(pos)));
		} else
			outlet(1,Atom.parse("range empty"));
	}
}
