import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;


public class StandAloneTest extends MaxObject
{
	public void bang()
	{
		System.out.println("Is Standalone ? "+MaxSystem.isStandAlone());
	}
}
