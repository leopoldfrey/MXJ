import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class polyplay extends LfObject
{
	public static final int OUT_DEL = 0;
	public static final int OUT_MATRIX = 1;
	public static final int OUT_SFPLAY = 2;
	public static final int OUT_PAN = 3;
	public static final int OUT_POLY = 4;
	public static final Atom preload = Atom.newAtom("preload");
	public static final Atom cue = Atom.newAtom(2);
	public static final Atom file = Atom.newAtom("none");
	public static final Atom zero = Atom.newAtom(0);
	public static final Atom busy = Atom.newAtom(1);
	public static final Atom free = Atom.newAtom(0);
	public static final Atom left = Atom.newAtom(0);
	public static final Atom right = Atom.newAtom(1);
	public static final Atom on = Atom.newAtom(1);
	public static final Atom off = Atom.newAtom(0);
	public static final Atom chn = Atom.newAtom(0);
	public static final Atom symramp = Atom.newAtom("ramp");
	public Atom[] ramp = new Atom[] { symramp, zero };
	public Atom[] atomcue = new Atom[] { preload, cue, file, zero, zero };
	public Atom[] leftmatoff = new Atom[] { left, chn, off };
	public Atom[] leftmaton = new Atom[] { left, chn, on };
	public Atom[] rightmatoff = new Atom[] { right, chn, off };
	public Atom[] rightmaton = new Atom[] { right, chn, on };
	public float nspk = 10;
	public float maxramp = 100;
	public float minramp = 10;

	public polyplay()
	{
		version = 0.1f;
		build = "29/03/09";
		INLET_ASSIST = new String[] { "Sound Duration + Filename" };
		OUTLET_ASSIST = new String[] { "to del", "to matrix~", "to sfplay", "to cospan~", "to poly" };
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.FLOAT, DataTypes.INT };
		declareAttribute("nspk");
		declareAttribute("maxramp");
		declareAttribute("minramp");
		init();
	}

	public void list(Atom[] args)
	{
		outlet(OUT_POLY, busy);
		outlet(OUT_PAN, (float) (Math.random() * 2. - 1.));
		float snd_dur = args[0].toFloat();
		float snd_beg = (float) Math.random() * snd_dur;
		float snd_end = (float) Math.random() * (snd_dur - snd_beg) + snd_beg;
		atomcue[2] = Atom.newAtom(Atom.toOneString(Atom.removeFirst(args)));
		atomcue[3] = Atom.newAtom(snd_beg);
		atomcue[4] = Atom.newAtom(snd_end);
		outlet(OUT_SFPLAY, atomcue);
		outlet(OUT_SFPLAY, cue);
		float ramptime = (int) (Math.random() * maxramp + minramp);
		ramp[1] = Atom.newAtom(ramptime);
		outlet(OUT_MATRIX, ramp);
		switch (randChn())
		{
		case 0: // MONO
			if (decide(0.5f))
			{
				leftmatoff[1] = leftmaton[1] = Atom.newAtom(randSpk());
				outlet(OUT_MATRIX, leftmaton);
			}
			else
			{
				rightmatoff[1] = rightmaton[1] = Atom.newAtom(randSpk());
				outlet(OUT_MATRIX, rightmaton);
			}
			break;
		case 1: // STEREO
			leftmatoff[1] = leftmaton[1] = Atom.newAtom(randSpk());
			rightmatoff[1] = rightmaton[1] = Atom.newAtom(randSpk());
			outlet(OUT_MATRIX, leftmaton);
			outlet(OUT_MATRIX, rightmaton);
			break;
		}
		outlet(OUT_DEL, new Atom[] { ramp[1], Atom.newAtom(snd_end - snd_beg - ramptime), leftmatoff[0], leftmatoff[1], leftmatoff[2], rightmatoff[0], rightmatoff[1], rightmatoff[2]});
	}

	public static boolean decide(float chance)
	{
		return Math.random() > chance;
	}

	public static int randChn()
	{
		return (int) (Math.random() * 2);
	}

	public Vector<Integer> spks = new Vector<Integer>();
	public static final int MEM_LEN = 10;

	public int randSpk()
	{
		int i = (int) (Math.random() * nspk);
		while (spks.contains(i))
		{
			i = (int) (Math.random() * nspk);
		}
		spks.add(i);
		if (spks.size() > MEM_LEN)
			spks.remove(0);
		return i;
	}
}
