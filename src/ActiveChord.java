import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;
import lf.util.IntegerVectorSort;

public class ActiveChord extends LfObject
{
	private boolean octave = false;
	private boolean sustain = false;
	private boolean report_clear = false;
	private IntegerVectorSort notes = new IntegerVectorSort();
	
	public ActiveChord(Atom[] args)
	{
		version = 0.1f;
		build = "05/02/09";
		INLET_ASSIST = new String[] { "Notes (pitch, velocity)", "Remove octave (%12)" , "Sustain" };
		OUTLET_ASSIST = new String[] { "List of active notes", "Number of active notes" };
		INLET_TYPES = new int[] { DataTypes.LIST, DataTypes.INT , DataTypes.INT };
		OUTLET_TYPES = new int[] { DataTypes.LIST, DataTypes.INT };

		declareAttribute("octave");
		declareAttribute("sustain");
		declareAttribute("report_clear");
		
		init();
	}
	
	public void list(Atom[] notes_in)
	{
		if (notes_in[1].toInt() > 0 && !notes.contains(notes_in[0].toInt()))
		{
			notes.addElement(notes_in[0].toInt());
			bang();
		} else if (notes_in[1].toInt() == 0 && !sustain) {
			notes.removeElement(notes_in[0].toInt());
			bang();
		}
	}

	public void inlet(int i)
	{
		switch (getInlet())
		{
		case 1:
			octave = i > 0;
			break;
		case 2:
			sustain = i > 0;
			if(!sustain)
				clear();
			break;
		default:
			break;
		}
	}

	public void bang()
	{
		if(notes.size() == 0)
		{
			outlet(1, 0);
			if(report_clear)
				outlet(0,Atom.newAtom("clear"));
		}
		else
		{
			int[] activs = new int[notes.size()];
			int cpt = 0;
			if (octave)
			{
				for (int i : notes)
				{
					activs[cpt] = i % 12;
					cpt++;
				}
			}
			else
			{
				for (int i : notes)
				{
					activs[cpt] = i;
					cpt++;
				}
			}
			outlet(1, activs.length);
			outlet(0, Atom.newAtom(activs));
		}
	}
	
	public void clear()
	{
		notes.clear();
		bang();
	}	
}
