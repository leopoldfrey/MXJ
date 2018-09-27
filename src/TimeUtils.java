import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class TimeUtils extends LfObject implements PropertyChangeListener
{
	private static PropertyChangeSupport pcs;
	
	private static float bpm = 120;
	private static float hz = 2;
	private static float ms = 500;
		
	private static float local_bpm = 120;
	private static float local_hz = 2;
	private static float local_ms = 500;
	
	public boolean me = false;
	public boolean local = false;
	
	public TimeUtils(Atom args[])
	{
		version = 0.1f;
		build = "21/01/07";
		INLET_TYPES = new int[] { DataTypes.FLOAT, DataTypes.FLOAT, DataTypes.FLOAT };
		OUTLET_TYPES = new int[] { DataTypes.FLOAT, DataTypes.FLOAT, DataTypes.FLOAT };
		INLET_ASSIST = new String[] { "ms", "hz", "bpm" };
		OUTLET_ASSIST = new String[] { "ms", "hz", "bpm" };
		init();
		
		declareAttribute("local","setLocal","isLocal");
		
		if(args.length > 0)
			local = (args[0].toInt() > 0);
		
		if(!local)
		{
			if(pcs == null)
				pcs = new PropertyChangeSupport(this);
				
			addPropertyChangeListener(this);
		}
	}
	
	public void inlet(float f)
	{
		if(!local)
		{
			switch (getInlet())
			{
			case 0:
				if (ms != f)
				{
					ms = f;
					setHz(1000 / ms);
					setBpm(hz * 60);
					bang();
				}
				break;
			case 1:
				if (hz != f)
				{
					hz = f;
					setMs(1000 / hz);
					setBpm(hz * 60);
					bang();
				}
				break;
			case 2:
				if (bpm != f)
				{
					bpm = f;
					setHz(bpm / 60);
					setMs(1000 / hz);
					bang();
				}
				break;
			}
			me = true;
	   		pcs.firePropertyChange("time",0,1);
		} else {
			switch (getInlet())
			{
			case 0:
				if (local_ms != f)
				{
					local_ms = f;
					setHz(1000 / local_ms);
					setBpm(local_hz * 60);
					bang();
				}
				break;
			case 1:
				if (local_hz != f)
				{
					local_hz = f;
					setMs(1000 / local_hz);
					setBpm(local_hz * 60);
					bang();
				}
				break;
			case 2:
				if (bpm != f)
				{
					local_bpm = f;
					setHz(local_bpm / 60);
					setMs(1000 / local_hz);
					bang();
				}
				break;
			}
		}
	}
	
	public void set(float f)
	{
		switch(getInlet())
		{
		case 0:
			ms = f;
			break;
		case 1:
			hz = f;
			break;
		case 2:
			bpm = f;
			break;
		}
	}
	
	public void bang()
	{
		if(local)
		{
			outlet(0,local_ms);
			outlet(1,local_hz);
			outlet(2,local_bpm);
		} else {
			outlet(0,ms);
			outlet(1,hz);
			outlet(2,bpm);
		}
 	}
	
    public void setMs( float m )
    {
    	if(local)
    		local_ms = m;
    	else
    		ms = m;
    }

    public void setHz( float h )
    {
    	if(local)
    		local_hz = h;
    	else
	    	hz = h;
    }
    
    public void setBpm( float b )
    {
    	if(local)
    		local_bpm = b;
    	else
	    	bpm = b;
    }
    
    public void addPropertyChangeListener( PropertyChangeListener listener )
    {
        pcs.addPropertyChangeListener( listener );
    }

    public void removePropertyChangeListener( PropertyChangeListener listener )
    {
        pcs.removePropertyChangeListener( listener );
    }
    
	public void propertyChange(PropertyChangeEvent evt)
	{
		if(!local)
		{
			if(!me)
				bang();
			me = false;
		}
	}

	public boolean isLocal()
	{
		return local;
	}

	public void setLocal(boolean l)
	{
		if(local != l)
		{
			local = l;
			if(!local)
				addPropertyChangeListener(this);
			else
				removePropertyChangeListener(this);
			bang();
		}
	}
	
	public void sync()
	{
		local_ms = ms;
		local_hz = hz;
		local_bpm = bpm;
		bang();
	}
}
