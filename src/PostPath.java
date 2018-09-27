import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public class PostPath extends MaxObject
{
	public PostPath(Atom[] atoms)
	{
		bang();
	}
	
	public void bang()
	{
		post("this class is here >" + this.getCodeSourcePath() + "<");
	}
}
