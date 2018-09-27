import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class XrandNoGUI extends LfObject
{
	Vector<Float> probs;
	Vector<Float> normProbs;
	Vector<Float> vals;
	float sum = 0;
	int size = 0;
	boolean quiet = true;
	boolean learning = false;
	
	public XrandNoGUI(Atom atoms[])
	{
		version = 0.2f;
		build = "08/05/08";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		INLET_ASSIST = new String[] { "list of probs and values, bang" };
		OUTLET_ASSIST = new String[] { "random value", "chaining bang" };
		init(0,false,atoms);
		declareAttribute("quiet");
		clear();
	}
	
	public void bang()
	{
		if(sum==0)
		{
			outlet(1,"bang");
			return;
		}
		float d = (float)Math.random();
		for(int i = 0 ; i < size ; i++)
		{
			if(d < normProbs.get(i))
			{
				outlet(1,"bang");
				outlet(0,vals.get(i));
				return;
			}
		}
	}
	
	public void list(Atom[] l)
	{
		clear();
		add(l);
		if(quiet)
			 return;
		getvals();
		getprobs();
	}
	
	public void probs(Atom[] l)
	{
		probs = new Vector<Float>();
		for(Atom a:l)
			probs.add(a.toFloat());
		calcSum();
		if(quiet)
		 return;
		getvals();
		getprobs();
	}
	
	public void vals(Atom[] l)
	{
		vals = new Vector<Float>();
		for(Atom a:l)
			vals.add(a.toFloat());
		calcSum();
		if(quiet)
			 return;
		getvals();
		getprobs();
	}

	public void add(Atom[] l)
	{
		if(modulo(l.length,2) != 0)
			error("list must have a pair number of element (value, probability)");
		for(int i = 0 ; i < l.length ; i += 2)
		{
			vals.add(l[i].toFloat());
			probs.add(l[i+1].toFloat());
		}
		calcSum();
		if(quiet)
			 return;
		getvals();
		getprobs();
	}
	
	public void clear()
	{
		probs = new Vector<Float>();
		normProbs = new Vector<Float>();
		vals = new Vector<Float>();
		sum = 0;
		size = 0;
		if(quiet)
			 return;
		getvals();
		getprobs();
	}
	
	public void calcSum()
	{
		sum = 0;
		normProbs = new Vector<Float>();
		size = Math.min(probs.size(), vals.size());
		for(int i = 0 ; i < size ; i++)
			sum += probs.get(i);
		if(sum == 0)
			return;
		float tmp = 0;
		for(int i = 0 ; i < size ; i++)
		{
			tmp += (Float)probs.get(i)/sum;
			normProbs.add(tmp);
		}
	}
	
	public void getprobs()
	{
		outlet(0,Atom.parse("probs "+toString(probs)));
	}

	public void getvals()
	{
		outlet(0,Atom.parse("vals "+toString(vals)));
	}
	
	public static String toString(Vector v)
	{
		if(v.isEmpty())
			return "empty";
		String tmp = "";
		for(int i = 0 ; i < v.size() ; i++)
			tmp += v.get(i)+(i+1 != v.size() ? " " : "");
		return tmp;
	}
	
	public void learn(boolean b)
	{
		learning = b;
		if(learning)
			clear();
	}
	
	public void inlet(int i)
	{
		inlet((float)i);
	}
	
	public void inlet(float f)
	{
		if(learning)
		{
			if(vals.contains(f))
			{
				int pos = vals.indexOf(f);
				probs.set(pos, probs.get(pos)+1);
			} else {
				vals.add(f);
				probs.add(1f);
			}
			calcSum();
			if(quiet)
				 return;
			getvals();
			getprobs();
		}
	}
}
