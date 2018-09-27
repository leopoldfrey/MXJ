import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxBox;

import lf.LfObject;

public class PTalk extends LfObject
{
	public static MaxBox pattrstore;
	private String prefix = "";
	
	public PTalk(Atom[] atoms)
	{
		version = 0.1f;
		build = "12/06/07";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "" };
		OUTLET_ASSIST = new String[] { "" };
		
		init(0,false,atoms);
	}
	
	public void exist(String objname)
	{
		MaxBox mb = getParentPatcher().getNamedBox(objname);
		if(mb != null)
			outlet(0,1);
		else outlet(0,0);
	}
	
	public void pnamereceive()
	{
		getpattrstore();
		for(MaxBox b : getParentPatcher().getAllBoxes())
		{
			if(b.getName() != null && b.isPatcher())
				for(MaxBox bb : b.getSubPatcher().getAllBoxes())
					if(bb.getName() != null && bb.getName().equals("ptalk"))
						bb.send("sendto", Atom.parse(b.getName()));
		}
	}
	
	public void getpattrstore()
	{
		for(MaxBox b : getParentPatcher().getAllBoxes())
			if(b.getName() != null && b.getMaxClass().equals("pattrstorage"))
				pattrstore = b;
	}
	
	public void pattrsend(Atom[] atoms)
	{
		if(atoms.length > 1)
		{
			atoms[0] = Atom.newAtom(prefix+atoms[0].toString());
			pattrstore.send("priority", atoms);
		}
	}
	
	public void sendto(Atom[] atoms)
	{
		prefix = Atom.toOneString(atoms)+"::";
		outletBang(0);
	}
}
