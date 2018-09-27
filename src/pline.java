import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxClock;

import lf.LfObject;


public class pline extends LfObject implements Executable
{
	private MaxClock clo;
	private double currentValue = 0;
	private double destination = 0;
	private int ramptime = -1;
	private int grain = 20;
	private int currentStep = 0;
	private double stepVal = 0;
	private int totalStep = 0;
	private int lastStep = 0;
	private boolean playing;
	private boolean paused;
	
	public pline(Atom[] atoms)
	{
		version = 0.1f;
		build = "07/03/07";
		INLET_TYPES = new int[] { DataTypes.ALL,DataTypes.ALL,DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		INLET_ASSIST = new String[] { "Destination Value of Ramp", "Total Ramp Time in Milliseconds", "Time Grain in Milliseconds" };
		OUTLET_ASSIST = new String[] { "Ramp Output", "Signals End of Ramp" };
		
		init(0,false,atoms);
		
		clo = new MaxClock(this);
		
		switch(atoms.length)
		{
		case 2:
			setGrain(atoms[1].toInt());
		case 1:
			currentValue = atoms[0].toFloat();
			break;
		}
	}
	
	public void inlet(int i)
	{
		switch(getInlet())
		{
		case 0:
			setDestination(i);
			break;
		case 1:
			setRamptime(i);
			break;
		case 2:
			setGrain(i);
			break;
		}
	}
	
	public void inlet(float f)
	{
		switch(getInlet())
		{
		case 0:
			setDestination(f);
			break;
		case 1:
			setRamptime((int)f);
			break;
		case 2:
			setGrain((int)f);
			break;
		}
	}
	
	public void list(Atom[] atoms)
	{
		switch(atoms.length)
		{
		case 3:
			setGrain(atoms[2].toInt());
		case 2:
			setRamptime(atoms[1].toInt());
			setDestination(atoms[0].toFloat());
			break;
		}
	}
	
	public void execute()
	{
		if(playing)
		{
			currentStep++;
			currentValue += stepVal;
			outletHigh(0, currentValue);
			if(lastStep == 0)
			{
				if(currentStep == totalStep)
				{
					outletBangHigh(1);
					stop();
				}
				else
					clo.delay(grain);
			} else {
				if(currentStep == totalStep)
				{
					stop();
					outletBangHigh(1);
				}
				else if(currentStep+1 == totalStep)
					clo.delay(lastStep);
				else
					clo.delay(grain);
			}
		}
	}
	
	public void pause()
	{		
		playing = false;
	}
	
	public void resume()
	{	
		playing = true;
		clo.delay(grain);
	}
	
	public void stop()
	{	
		ramptime = 0;
		playing = false;
		currentStep = 0;
	}

	private void setGrain(int grain)
	{
		this.grain = clip(grain,1);
	}

	private void setRamptime(int ramptime)
	{
		this.ramptime = clip(ramptime,0);
	}

	private void setDestination(float dest)
	{
		destination = dest;
		if(ramptime < 1)
		{
			currentValue = destination;
			outletHigh(0, currentValue);
		} else {
			totalStep = ramptime / grain;
			lastStep = ramptime - (totalStep * grain);
			totalStep = lastStep != 0 ? totalStep + 1 : totalStep;
			stepVal = (destination - currentValue) / totalStep;
			currentStep = 0;
			playing = true;
			paused = false;
			clo.delay(grain);
		}
	}
}
