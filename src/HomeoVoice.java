import java.util.Arrays;
import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class HomeoVoice extends LfObject
{
	Vector<String> sentences;
	
	public HomeoVoice(Atom[] atoms)
	{
		version = 0.1f;
		build = "24/04/16";
		INLET_ASSIST = new String[] { "To Speak", "Done Talking" };
		OUTLET_ASSIST = new String[] { "Speech Oout", "Voice", "Rate", "Takling" };
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL };
		init(0, false, atoms);
		sentences = new Vector<String>();
	}
	
	public void bang()
	{
		if(getInlet() == 1)
		{
			next();
		}
	}
	
	public void speak(Atom[] atoms)
	{
		String str = Atom.toOneString(atoms);
		str = str.replace('\'', ' ');
		//post(str);
		String[] strA = str.split("/s ");
		sentences.clear();
		sentences.addAll(Arrays.asList(strA));
		outlet(3,1);
		
		//for(String s : sentences)
		//	post(s);
		
		next();
	}
	
	public void next()
	{
		if(sentences.isEmpty())
		{
			outlet(3,0);
			return;
		}
		
		String s = sentences.remove(0);
		String o = s;
		int i1 = 0, i2 = 0, i3 = 0;
		String r, v, d = "0";
		int rateI = o.indexOf("/r");
		if(rateI != -1)
		{
			s = o.substring(rateI+2);
			i1 = s.indexOf(" ");
			r = s.substring(0, i1);
			//s = s.substring(i1+1);
			//post("RATE "+r);// + " " + i1 + " _ " + s);
			outlet(2, r);
		}
		int voiceI = o.indexOf("/v");
		if(voiceI != -1)
		{
			s = o.substring(voiceI+2);
			i2 = s.indexOf(" ");
			v = s.substring(0, i2);
			//s = s.substring(i2+1);
			//post("VOICE "+v);// + " " + i2 + " _ " + s);
			outlet(1,v);
		}
		int delayI = o.indexOf("/d");
		if(delayI != -1)
		{
			s = o.substring(delayI+2);
			i3 = s.indexOf(" ");
			d = s.substring(0, i3);
			//s = s.substring(i3+1);
			//post("DELAY "+ d);// + " " + i3 + " _ " + s);
		}
		int imax = max(max(rateI,voiceI), delayI);
		if(imax != -1)
			outlet(0, d + " " + o.substring(o.indexOf(" ", imax)));//.replace('0', ' '));
		else
			outlet(0, d + " " + o);//.replace('0', ' '));
	}
	
	public void stop()
	{
		sentences.clear();
	}
	
	public static int max(int a, int b)
	{
		return a >= b ? a : b;
	}
}
