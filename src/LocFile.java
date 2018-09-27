import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;


public class LocFile extends MaxObject
{
	public void locate(String filename)
	{
		String locateFile = MaxSystem.locateFile(filename);
		if(locateFile != null)
			outlet(0, locateFile);
		else
			outlet(0, "not found");
	}
}
