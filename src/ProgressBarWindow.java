import javax.swing.SwingUtilities;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;
import lf.gui.BarreProgression;

public class ProgressBarWindow extends LfObject
{
	private BarreProgression bp;
	private String title = "Loading...";
	private int max = 100;

	public ProgressBarWindow(Atom[] atoms)
	{
		version = 0.1f;
		build = "03/04/06";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.FLOAT };
		INLET_ASSIST = new String[] { "bang:increment, int:set value, float:set percentage" };
		OUTLET_ASSIST = new String[] { "Reports current value." };

		init(0, false, atoms);
		
		bp = new BarreProgression();

		switch(atoms.length)
		{
		case 2 :
			setTitle(atoms[1].getString());
		case 1 :
			setMax(atoms[0].getInt());
			break;
		default :
			setTitle(title);
			setMax(max);
			break;
		}

		declareAttribute("max", "getMax", "setMax");
		declareAttribute("title", "getTitle", "setTitle");
	}
	
	public void bang()
	{
		bp.inc();
		report();
	}
	
	public void inlet(int i)
	{
		bp.setValue(i);
		report();
	}
	
	public void inlet(float f)
	{
		bp.setValue((int)((f/100)*max));
		report();
	}
	
	public void inc(int i)
	{
		if(i == 0) bp.inc();
		else bp.inc(i);
		report();
	}
	
	public void dec(int i)
	{
		if(i == 0) bp.dec();
		else bp.dec(i);
		report();
	}
	
	protected void setMax(int i)
	{
		max = clip(i,0);
		bp.setMaximum(max);
		debug("Max:"+max);
	}
	
	protected int getMax()
	{
		max =bp.getMaximum();
		return max;
	}
	
	protected String getTitle()
	{
		return title;
	}
	
	protected void setTitle(String s)
	{
		title = s;
		bp.setTitle(title);
		debug("Title:"+title);
	}
	
	protected void report()
	{
		if(bp.getValue() >= max)
		{
			close();
			bp.setValue(0);
		}
		
		outlet(0,clipH(bp.getValue(),bp.getMaximum()));
	}
	
	public void open()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				bp.pack();
				bp.open();
			}
		});
	}
	
	public void close()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				bp.close();
			}
		});
	}
	
	public void notifyDeleted()
	{
		bp.close();
		bp = null;
	}
	
	public void usage()
	{
		post(ppp+"Usage:\n"+ppp+"   Optional argument : 1: Maximum 2: Title");
	}

	public void info()
	{
		post(ppp+"Info:\n"+ppp+"   Provides a progress bar.");
	}
	
	public void state()
	{
		post(ppp+"State:\n"+ppp+"   Current:"+bp.getValue()+"Max:"+bp.getMaximum());
	}
}
