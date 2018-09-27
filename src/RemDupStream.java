import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class RemDupStream extends LfObject
{
	private Vector<String> stream = new Vector<String>();
	private String tmp;
	
	public RemDupStream(Atom[] args)
	{
		version = 0.1f;
		build = "05/02/09";
		INLET_ASSIST = new String[] { "Stream Input" };
		OUTLET_ASSIST = new String[] { "Filtered Stream Output", "Bang when rejected" };
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };

		init();
	}
	
	public void bang()
	{
		stream = new Vector<String>();
//		post("------------------ Cleared ! ------------------");
	}
	
	public void clear()
	{
		bang();
	}
	
	public void inlet(int i)
	{
		tmp = i+"";
		if(!stream.contains(tmp))
		{
//			post("Ok : "+i);
			stream.add(tmp);
			outlet(0, i);
		} else {
//			post("Reject : "+i);
			outletBang(1);
		}
	}
	
	public void inlet(float f)
	{
		tmp = f+"";
		if(!stream.contains(tmp))
		{
			stream.add(tmp);
			outlet(0, f);
		} else {
			outletBang(1);
		}
	}
	
	public void list(Atom[] list)
	{
		tmp = toString(list," ");
		if(!stream.contains(tmp))
		{
			stream.add(tmp);
			outlet(0,list);
		} else {
			outletBang(1);
		}
	}
	
	public void anything(String message, Atom[] atoms_in)
	{
		list(concat(message, atoms_in));
	}
}
