import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxBox;

import lf.LfObject;

public class Pos extends LfObject
{
	public Pos(Atom[] atoms)
	{
		version = 0.1f;
		build = "07/03/07";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "Name of the object to localize" };
		OUTLET_ASSIST = new String[] { "Patcher coordinates of the object [x:y]" };
		
		init(0,false,atoms);
	}
	
	public void anything(String objname, Atom[] atoms)
	{
		MaxBox mb = getParentPatcher().getNamedBox(objname);
		if(mb != null)
			outlet(0,mb.getRect());
		else outlet(0, "notfound");
	}
	
	public void delta(String patchname, String objname)
	{
		MaxBox mb = getParentPatcher().getNamedBox(patchname);
		if(mb != null)
		{
			int mbX = mb.getRect()[0];
			int mbY = mb.getRect()[1];
			MaxBox mb2 = mb.getSubPatcher().getNamedBox(objname);
			if(mb2 != null)
			{
				int mbX2 = mb2.getRect()[0];
				int mbY2 = mb2.getRect()[1];
				outlet(0,new int[]{(mbX2-mbX),(mbY2-mbY)});
			}
			else outlet(0, "notfound");
		}
		else outlet(0, "notfound");
	}
	
	public void exist(String objname)
	{
		MaxBox mb = getParentPatcher().getNamedBox(objname);
		if(mb != null)
			outlet(0,1);
		else outlet(0,0);
	}
	
	public void count(String objclass)
	{
		MaxBox[] mb = getParentPatcher().getAllBoxes();
		for(MaxBox b : mb)
		{
			if(b.getName() != null && b.isPatcher())
			{
				
				post(b.getName()+" "+b.getSubPatcher().getName());
			}
		}
	}
}
