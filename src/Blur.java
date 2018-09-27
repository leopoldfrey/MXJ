import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

// TODO BLUR Rename oddmode > vector mode or convolution mod

public class Blur extends LfObject
{
	private final static int MODE_FLOW = 0;
	private final static int MODE_LIST = 1;
	private int blur = 1;
	private int[] blurArray;
	private int sumBlur = 1;
	private boolean odd = false;
	private Vector<Double> stack = new Vector<Double>(10,10);
	private Vector<double[]> stackList = new Vector<double[]>(10,10);
	private int mode = MODE_FLOW;
	
	public Blur(Atom[] atoms)
	{
		version = 0.2f;
		build = "11/06/07";
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "ints/floats/lists flow to be smooth.","smooth value(s)" };
		OUTLET_ASSIST = new String[] { "Result." };
		
		init(0,false,atoms);
		
		switch(atoms.length)
		{
		case 2 :
			mode(atoms[1].toInt());
		case 1 :
			blur(atoms[0].toInt());
		case 0:
			break;
		}
		declareAttribute("blur");
		declareAttribute("mode");
	}
	
	public void clear()
	{
		stack.clear();
		stackList.clear();
	}
	
	public void blur(int i)
	{
		blur = clip(i,1);
		odd = false;
	}
	
	public void blurArray(int[] d)
	{
		odd = true;
		blurArray = d;
		sumBlur = 0;
		for(int i = 0 ; i < blurArray.length ; i++)
		{
			sumBlur += blurArray[i];
		}
	}
	public void mode(int i)
	{
		mode = clip(i,0,1);
	}
	
	public void inlet(int i)
	{
		if(getInlet() == 1)
			blur(i);
		inlet((float)i);
	}
	
	public void inlet(float f)
	{
		if(getInlet() != 0)
			return;
		
		stack.add(new Double(f));

		if(!odd)
			blur(f);
		else oddblur(f);
	}

	private void blur(double d)
	{
		if(blur<=stack.size())
		{
			double tmp = 0;
			int max = stack.size() - 1;
			for(int i = 0 ; i < blur ; i++)
			{
				tmp += stack.get(max-i);
			}
			tmp /= blur;
			outlet(0,tmp);
		} else if(!stack.isEmpty())
		{
			double tmp = 0;
			int max = stack.size() - 1;
			for(int i = 0 ; i <= max ; i++)
			{
				tmp += stack.get(max-i);
			}
			tmp /= max+1;
			outlet(0,tmp);
		} else {
			outlet(0,d);
		}
	}
	
	private void oddblur(double d)
	{
		if(blurArray.length<=stack.size())
		{
			double tmp = 0;
			int max = stack.size() - 1;
			for(int i = 0 ; i < blurArray.length ; i++)
			{
				tmp += stack.get(max-i) * blurArray[blurArray.length-i-1];
			}
			if(sumBlur != 0 && sumBlur != 1) tmp /= sumBlur;
			outlet(0,tmp);
		} else if(!stack.isEmpty())
		{
			double tmp = 0;
			int max = stack.size() - 1;
			for(int i = 0 ; i <= max ; i++)
			{
				tmp += stack.get(max-i).doubleValue() * blurArray[blurArray.length-i-1];
			}
			if(sumBlur != 0 && sumBlur != 1) tmp /= sumBlur;
			outlet(0,tmp);
		} else {
			outlet(0,d);
		}
	}
	
	private void blur(double[] darray)
	{
		if(blur<=stackList.size())
		{
			double[] tmp = new double[darray.length];
			int max = stackList.size() - 1;
			for(int i = 0 ; i < blur ; i++)
			{
				double[] ttmp = stackList.get(max-i);
				for(int k = 0 ; k < ttmp.length ; k++)
					tmp[k] += ttmp[k];
			}
			for(int c = 0 ; c < tmp.length ; c++)
				tmp[c] /= blur;
			outlet(0,tmp);
		} else if(!stackList.isEmpty())
		{
			double[] tmp = new double[darray.length];
			int max = stackList.size() - 1;
			for(int i = 0 ; i <= max ; i++)
			{
				double[] ttmp = stackList.get(max-i);
				for(int k = 0 ; k < ttmp.length ; k++)
					tmp[k] += ttmp[k];
			}
			for(int c = 0 ; c < tmp.length ; c++)
				tmp[c] /= max+1;
			outlet(0,tmp);
		} else {
			outlet(0,darray);
		}
	}
	
	private void oddblur(double[] darray)
	{
		if(blurArray.length<=stackList.size())
		{
			double[] tmp = new double[darray.length];
			int max = stackList.size() - 1;
			for(int i = blurArray.length-1 ; i >= 0  ; i--)
			{
				double[] ttmp = stackList.get(max-i);
				for(int k = 0 ; k < ttmp.length ; k++)
					tmp[k] += ttmp[k] * blurArray[blurArray.length-i-1];
			}
			if(sumBlur != 0 && sumBlur != 1)
				for(int c = 0 ; c < tmp.length ; c++)
					tmp[c] /= sumBlur;
			outlet(0,tmp);
		} else if(!stackList.isEmpty())
		{
			double[] tmp = new double[darray.length];
			int max = stackList.size() - 1;
			for(int i = max ; i >= 0 ; i--)
			{
				double[] ttmp = stackList.get(max-i);
				for(int k = 0 ; k < ttmp.length ; k++)
					tmp[k] += ttmp[k] * blurArray[blurArray.length-i-1];
			}
			if(sumBlur != 0 && sumBlur != 1)
				for(int c = 0 ; c < tmp.length ; c++)
					tmp[c] /= sumBlur;
			outlet(0,tmp);
		} else {
			outlet(0,darray);
		}
	}
	
	private void blurlist(double[] d)
	{
		if(d.length < blur)
			return;
		
		if(blur == 1)
		{
			outlet(0,d);
			return;
		}
		
		double[] res = new double[d.length];
		int max = d.length - 1;
		int demi = blur/2;
		int demi2 = isPair(blur) ? demi-1 : demi;
		
		for(int i = 0 ; i <= max ; i++)
		{
			int imin,imax;
			if(i<demi)
			{
				imin = 0;
				imax = i + demi2;
			} else if(i>max-demi)
			{
				imin = i - demi;
				imax = max;
			} else {
				imin = i - demi;
				imax = i + demi2;
			}
			
			double tmp = 0;
			for(int k = imin ; k <= imax ; k++)
			{
				tmp += d[k];
			}
			res[i] = tmp/((imax-imin)+1);
		}
		outlet(0,res);
	}

	private void oddblurlist(double[] d)
	{
		if(d.length < blurArray.length)
			return;
		
		if(blurArray.length == 1)
		{
			outlet(0,d);
			return;
		}
		
		double[] res = new double[d.length];
		int max = d.length - 1;
		int demi = blurArray.length/2;
		int demi2 = isPair(blurArray.length) ? demi-1 : demi;
		
		for(int i = 0 ; i <= max ; i++)
		{
			int imin,imax;
			if(i<=demi)
			{
				imin = 0;
				imax = i + demi;
			} else if(i>=max-demi2)
			{
				imin = i - demi2;
				imax = max;
			} else {
				imin = i - demi2;
				imax = imin + blurArray.length;
			}
			
			double tmp = 0;
			int c = blurArray.length-1-(imax-1-imin);
			for(int k = imin ; k < imax ; k++)
			{
				tmp += d[k] * blurArray[c];
				c++;
			}
			if(sumBlur != 0 && sumBlur != 1)
				tmp /= sumBlur;
			res[i] = tmp;
		}
		outlet(0,res);
	}

	public void list(Atom[] atoms)
	{
		if(getInlet() != 0)
		{
			blurArray(Atom.toInt(atoms));
			return;
		}

		if(mode == MODE_FLOW)
		{
			double[] d = Atom.toDouble(atoms);
			stackList.add(d);
			if(!odd)
				blur(d);
			else oddblur(d);
			return;
		}
		if(mode == MODE_LIST)
		{
			if(!odd)
				blurlist(Atom.toDouble(atoms));
			else oddblurlist(Atom.toDouble(atoms));
			return;
		}
	}
	
	public void usage()
	{
	}

	public void info()
	{
	}

	public void state()
	{
		String tmp ="[";
		for(int i = 0 ; i < blurArray.length ; i++)
		{
			tmp = tmp.concat(""+blurArray[i]);
			if(i < blurArray.length-1) tmp = tmp.concat(",");
			else tmp = tmp.concat("]");
		}
		post("blurArray:"+tmp+ " sum:"+sumBlur);
	}
}
