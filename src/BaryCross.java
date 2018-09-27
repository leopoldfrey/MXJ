import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxClock;

import lf.LfObject;

// TODO BARYCROSS RENAME CENTROID ???
public class BaryCross extends LfObject implements Executable
{
	private MaxClock clock = null;
	private Vector<double[]> lists;
	private double[] weights = new double[] {0};
	private double weightsSum = 0;
	private double cross = 0;
	private int inputs = 2;
	public final static int MODE_CROSS = 0;
	public final static int MODE_BARYCENTER = 1;
	private int mode = MODE_CROSS;
	private int loop = 0;
	private int sens = 0;
	private int crosstime = 1000;
	private int stepdur = 10;
	private int from = 0, to = 1;
	private double step = (double)stepdur/crosstime;

	public BaryCross(Atom[] atoms)
	{
		version = 0.2f;
		build = "11/06/07";
		INLET_TYPES = new int[] { DataTypes.LIST, DataTypes.LIST, DataTypes.LIST };
		OUTLET_TYPES = new int[] { DataTypes.LIST, DataTypes.FLOAT };
		INLET_ASSIST = new String[] { "Crossfade value (0.-number of inputs) | list of weights (floats[]).", "Input 1 (float/list)", "Input 2 (float/list)" };
		OUTLET_ASSIST = new String[] { "Result", "Coefficient report" };
		
		switch(atoms.length)
		{
		case 2 :
			if (atoms[1].isInt())
				setMode(atoms[1].getInt());
		case 1 :
			if (atoms[0].isInt())
				inputs = clip(atoms[0].getInt(), 2);
		default :
			break;
		}
		
		if (inputs > 2)
		{
			INLET_TYPES = new int[inputs+1];
			for (int i = 0; i < inputs+1; i++)
				INLET_TYPES[i] = DataTypes.LIST;
			INLET_ASSIST = new String[inputs+1];
			INLET_ASSIST[0] = "Crossfade value (0.-number of inputs) | list of weights (floats[])";
			for (int i = 1; i < inputs+1; i++)
				INLET_ASSIST[i] = "Input " + i + " (float/list)";
		}
		
		lists = new Vector<double[]>(inputs);
		weights = new double[inputs];
		for(int i = 0 ; i < inputs ; i++)
		{
			lists.add(i,new double[]{});
			weights[i] = 0;
		}
		weightsSum = 0;
		
		init(0, false, atoms);
		declareAttribute("mode", "getMode", "setMode");
	}

	public void inlet(int i)
	{
		inlet((float)i);
	}

	public void inlet(float f)
	{
		if (getInlet() == 0)
			if (mode == MODE_CROSS)
			{
				cross = clip(f, 0, inputs-1);
				outlet(1,cross);
			}
			else
			{
				for (int i = 0; i < inputs; i++)
					weights[i] = f;
				weightsSum = inputs * f;
				outlet(1,weights);
			}
		else lists.set(getInlet()-1, new double[] { f });
		bang();
	}

	public void list(Atom[] atoms)
	{
		if (getInlet() == 0)
			if (mode == MODE_CROSS)
			{
				cross = clip(atoms[0].getFloat(), 0, inputs-1);
				outlet(1,cross);
			}
			else  
			{
				if(atoms.length == inputs)
				{
					weightsSum = 0;
					for (int i = 0; i < inputs; i++)
					{
						weights[i] = atoms[i].toDouble();
						weightsSum += weights[i];
					}
					outlet(1,weights);
				}
				else error();
			}
		else lists.set(getInlet()-1, Atom.toDouble(atoms));
		bang();
	}

	public void bang()
	{
		if (mode == MODE_CROSS)
			cross();
		else barycenter();
	}

	private void cross()
	{
		int i1 = (int) cross;
		int i2 = clipH(i1 + 1,inputs-1);
		double crossValue = cross - i1;
		cross(i1, i2, crossValue);
	}
	
	public void cross(Atom[] atoms)
	{
		if(atoms.length >= 3)
		{
			int i1 = clip(atoms[0].toInt(),0,inputs-1);
			int i2 = clip(atoms[1].toInt(),0,inputs-1);
			double crossValue = clip(atoms[2].toFloat(),0.0f,1.0f);
			cross(i1,i2,crossValue);
		} else error();
	}
	
	private void cross(int i1, int i2, double crossValue)
	{
		double[] tmp;
		double[] aList = lists.get(i1);
		double[] bList = lists.get(i2);
		if (aList.length <= bList.length)
		{
			tmp = new double[bList.length];
			for (int c = 0; c < aList.length; c++)
				tmp[c] = (1 - crossValue) * aList[c] + crossValue * bList[c];
			for (int c = aList.length; c < bList.length; c++)
				tmp[c] = bList[c];
		}
		else
		{
			tmp = new double[aList.length];
			for (int c = 0; c < bList.length; c++)
				tmp[c] = (1 - crossValue) * aList[c] + crossValue * bList[c];
			for (int c = bList.length; c < aList.length; c++)
				tmp[c] = aList[c];
		}
		outlet(0, tmp);
	}

	private void barycenter()
	{
		int size = 0;
		for(int i = 0 ; i < inputs ; i++)
			size = Math.max(size, lists.get(i).length);

		if(size == 0) return;

		double[] tmp = new double[size];
		if(weightsSum != 0)
		{
			for(int j = 0 ; j < size ; j++)
				for(int k = 0 ; k < inputs ; k++)
					if(lists.get(k).length > j)
							tmp[j] += lists.get(k)[j] * weights[k] / weightsSum;
		} else {
			for(int j = 0 ; j < size ; j++)
				tmp[j] = 0;
		}
		outlet(0,tmp);
	}

	public void usage()
	{
		post(
				ppp+"Usage:"
				+"\n"+ppp+"   Optional arguments :"
				+"\n"+ppp+"    arg1 = number of inputs."
				+"\n"+ppp+"    arg2 = mode :"
				+"\n"+ppp+"      0 (default) : Crossfade between values in input."
				+"\n"+ppp+"      1 : Calculates the barycenter of values in input."
			);
		post(
				ppp+"   Attributes :"
				+"\n"+ppp+"      @mode : see \'arg2\'."
			);
		post(
				ppp+"   Messages :"
				+"\n"+ppp+"      cross i1 i2 f1 : crossfade between non-adjacent inputs"
				+"\n"+ppp+"            i1,i2 : inputs,"
				+"\n"+ppp+"            f1 : crossvalue."
				+"\n"+ppp+"      autocross i1 i2 i3 (i4) (i5) : autocrossfade between non-adjacent inputs"
				+"\n"+ppp+"            i1,i2 : inputs,"
				+"\n"+ppp+"            i3 : duration,"
				+"\n"+ppp+"            optional i4 (default >) : direction (>,<,>< (loop),"
				+"\n"+ppp+"            optional i5 (default 10) : step duration."
				+"\n"+ppp+"      stop : stops autocross."
				+"\n"+ppp+"      go : launchs autocross."
				
			);
		post(
				ppp+"   Inputs :"
				+"\n"+ppp+"      1 : float/float list : cross fade or list of weights."
				+"\n"+ppp+"      2 to n : inputs to be treated."
			);
		post(
				ppp+"   Outputs :"
				+"\n"+ppp+"      1 : Results."
				+"\n"+ppp+"      2 : Reports the coefficients."
			);
	}

	public void info()
	{
		post(ppp+"Info:\n"+ppp+"   Crossfade || Barycenter between ints/floats/lists (see usage for more...)");
	}

	public void state()
	{
		String tmp = "";
		for(int i = 0 ; i < weights.length ; i++)
			tmp = tmp.concat(weights[i]+ " ");
		
		post(ppp+"State:\n"
				+ppp+"   inputs:"+inputs+"\n"
				+ppp+"   mode:"+(mode==0 ? "CrossFade" : "Barycenter")+"\n"
				+ppp+"   cross:"+cross+"\n"
				+ppp+"   barycenter weights:"+tmp+"\n"
   				+ppp+"   weightsSum:"+weightsSum
			);
	}

	protected int getMode()
	{
		return mode;
	}

	protected void setMode(int mode)
	{
		this.mode = clip(mode, 0, 1);
		debug("Mode:" + this.mode);
	}

	public void stop()
	{
		clock.unset();
	}
	
	public void go()
	{
		switch(loop)
		{
		case 0 :
		case 2 :
			cross = 0;
			sens = 0;
			break;
		case 1 :
			cross = 1;
			sens = 1;
			break;
		}
		outlet(1,cross);
		
		clock.delay(0);
	}
	
	public void loop(int i)
	{
		loop = clip(i,0,2);
		debug("loop:"+loop);
	}
	
	public void autocross(Atom[] atoms)
	{
		if(atoms.length >= 3)
		{
			from = clip(atoms[0].toInt(),0,inputs-1);
			to = clip(atoms[1].toInt(),0,inputs-1);
			crosstime = clip(atoms[2].toInt(),0);
			if(atoms.length >= 4)
			{
				loop = clip(atoms[3].toInt(),0,2);
				if(atoms.length >= 5)
					stepdur = clip(atoms[4].toInt(),10);
			}
			
			switch(loop)
			{
			case 0 :
			case 2 :
				cross = 0;
				sens = 0;
				break;
			case 1 :
				cross = 1;
				sens = 1;
				break;
			}
			
			cross(from, to, cross);
			outlet(1,cross);
			
			step = (double)stepdur/crosstime;
			clock = new MaxClock(this);
			clock.delay(0);
		} else error();
	}
	
	public void execute()
	{
		switch(sens)
		{
		case 0 :
			cross += step;
			break;
		case 1 :
			cross -= step;
			break;
		}
		
		cross(from, to, cross);
		outlet(1,cross);
		clock.delay(stepdur);
		
		if(cross <= 0)
		{
			switch(loop)
			{
			case 1 :
				clock.unset();
				break;
			case 2 :
				sens = 0;
				break;
			}
		}
		if(cross >= 1)
		{
			switch(loop)
			{
			case 0 :
				clock.unset();
				break;
			case 2 :
				sens = 1;
				break;
			}
		}
	}
	
	protected void notifyDeleted()
	{
		if(clock != null)
		{
			clock.unset();
			clock.release();
		}
		
	}
}
