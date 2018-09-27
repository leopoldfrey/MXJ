package test;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;


public class Test extends MaxObject
{
	
	JFrame f;
	
	public Test(Atom[] atoms)
	{	
		declareIO(2, 2);
		createInfoOutlet(false);
	}
	
	public void inlet(int i)
	{	
		int inl = getInlet();
		post("je suis rentré la : "+inl+" et je suis : "+i);
		outlet(1-inl, i);
	}	
	
	public void prout(Atom[] a)
	{	
		SwingUtilities.invokeLater(new Runnable()
		{	
			public void run()
			{	
				f = new JFrame();
				f.setVisible(true);
			}
		});
	}	
	
	
	public void notifyDeleted()
	{	
			SwingUtilities.invokeLater(new Runnable()
			{	
				public void run()
				{	
					if(f != null)
						f.dispose();
				}
			});
	}
}
