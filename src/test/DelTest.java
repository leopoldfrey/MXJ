package test;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.cycling74.max.MaxObject;

public class DelTest extends MaxObject
{
	JFrame frame;

	public DelTest()
	{
		frame = new JFrame("test");
		frame.setSize(200, 200);
		frame.setLocationRelativeTo(null);
	}

	public void notifyDeleted()
	{
		post("deleting...");
		close();
	}

	public void open()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				frame.setVisible(true);
			}
		});
	}

	public void close()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				frame.setVisible(false);
			}
		});
	}

	public void dblclick()
	{
		open();
	}
}