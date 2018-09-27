package test;
import com.cycling74.max.MaxBox;
import com.cycling74.max.MaxObject;


public class JPatcherBug extends MaxObject
{
	public void findPatchers()
	{
		for(MaxBox b : getParentPatcher().getAllBoxes())
		{
			if(b.isPatcher())
				post("Found patcher : "+b.toString());
		}
	}
	
	public void workaround()
	{
		for(MaxBox b : getParentPatcher().getAllBoxes())
		{
			if(b.getMaxClass().equalsIgnoreCase("patcher") || b.getMaxClass().equalsIgnoreCase("jpatcher"))
				post("Found patcher : "+b.toString());
		}
	}
}
