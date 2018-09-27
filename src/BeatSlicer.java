import java.util.TreeSet;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.msp.MSPBuffer;

import lf.LfObject;

public class BeatSlicer extends LfObject implements Runnable
{
	private static final int MODE_AUTO = 0;
	private static final int MODE_AUTO_REL = 1;
	private static final int MODE_DIVIDE = 2;
	private BeatSlicerBuffer _buf;
	private BeatSlicerXPoints xpoints;
	private BeatSlicerXPoints xpeaks;
	private int[] xpeaksArray;
	private float[] xpeaksArrayMs;
	private boolean mono = false;
	private float release = 100;
	private float threshold = 0.5f;
	private int mode = 0;
	private boolean unit_ms = true;
	private int division = 8;
	private boolean clip = false;
	private float clip_length = 500;

	public BeatSlicer(Atom[] atoms)
	{
		version = 0.1f;
		build = "13/09/07";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "Message in" };
		OUTLET_ASSIST = new String[] { "Message out" };

		init();
		xpoints = new BeatSlicerXPoints();
		xpeaks = new BeatSlicerXPoints();
		switch (atoms.length)
		{
		case 5:
			division = atoms[4].getInt();
		case 4:
			release = atoms[3].getFloat();
		case 3:
			threshold = atoms[2].getFloat();
		case 2:
			mode = atoms[1].getInt();
		case 1:
			set(atoms[0].getString());
		case 0:
			break;
		}
		declareAttribute("mono");
		declareAttribute("threshold");
		declareAttribute("release");
		declareAttribute("mode");
		declareAttribute("division");
		declareAttribute("unit_ms");
		declareAttribute("clip");
		declareAttribute("clip_length");
	}

	public void set(String buf)
	{
		this._buf = new BeatSlicerBuffer(buf);
	}

	public void infobuf()
	{
		if (_buf != null)
		{
			_buf.init();
			_buf.info();
		}
		else
		{
			System.out.println("_________BeatSlicer_Info___________");
			System.out.println("Buffer not set");
		}
	}

	public void bang()
	{
		slice();
	}

	public void clear()
	{
		xpoints.clear();
		xpeaks.clear();
	}

	public void slice()
	{
		new Thread(this).start();
	}

	public void run()
	{
		_buf.init();
		clear();
		if (!_buf._empty)
		{
			findZeroCrossingPoints();
			findPeaks();
			convMs();
			outlet(0, Atom.parse("slices " + (xpeaksArray.length - 2)));
			outlet(0, Atom.newAtom("done"));
		}
		else
		{
			System.out.println("_________BeatSlicer_Info___________");
			System.out.println("Buffer empty");
		}
	}

	private void findZeroCrossingPoints()
	{
		int channum = _buf._channels;
		float[] prev = new float[channum];
		for (int i = 0; i < channum; i++)
			prev[i] = 0;
		float[] current = new float[channum];
		xpoints.add(0);
		if (channum == 1 || mono)
		{
			for (int index = 0; index < _buf._frames; index++)
			{
				current[0] = MSPBuffer.peek(_buf._name, 1, index);
				if (current[0] == 0 || signB(prev[0]) == !signB(current[0]))
					xpoints.add(index);
				prev[0] = current[0];
			}
		}
		else
		{
			boolean[] xcross = new boolean[channum];
			boolean ok = true;
			for (int index = 0; index < _buf._frames; index++)
			{
				for (int chan = 0; chan < channum; chan++)
				{
					current[chan] = MSPBuffer.peek(_buf._name, chan + 1, index);
					if (current[chan] == 0 || signB(prev[chan]) == !signB(current[chan]))
						xcross[chan] = true;
					else xcross[chan] = false;
					prev[chan] = current[chan];
				}
				ok = true;
				for (int chan = 0; chan < channum; chan++)
					ok = ok && xcross[chan];
				if (ok)
					xpoints.add(index);
			}
		}
		xpoints.add(_buf._frames);
	}

	private void findPeaks()
	{
		int channum = _buf._channels;
		int relsmp = _buf.msToSmp(release);
		int index, rel;
		boolean peak;
		switch (mode)
		{
		case MODE_DIVIDE:
			if (division >= 1)
			{
				xpeaks.add(0);
				int subdiv = _buf._frames / division;
				int current = subdiv;
				for (int i = 0; i < division; i++)
				{
					xpeaks.add(xpoints.nearest(current));
					current += subdiv;
				}
				xpeaks.add(_buf._frames);
			}
			break;
		case MODE_AUTO:
			// TODO BEATSLICER stereo auto find peaks
			xpeaks.add(0);
			index = 0;
			peak = false;
			rel = 0;
			while (index < _buf._frames)
			{
				float curval = Math.abs(MSPBuffer.peek(_buf._name, 1, index));
				if (curval >= threshold && !peak && rel == 0)
				{
					xpeaks.add(xpoints.previous(index));
					peak = true;
					rel = relsmp;
				}
				else if (rel != 0)
				{
					rel--;
				}
				else
				{
					peak = false;
				}
				index++;
			}
			xpeaks.add(_buf._frames);
			break;
		case MODE_AUTO_REL:
			// TODO BEATSLICER stereo auto find peaks
			xpeaks.add(0);
			index = 0;
			peak = false;
			rel = 0;
			while (index < _buf._frames)
			{
				float curval = Math.abs(MSPBuffer.peek(_buf._name, 1, index));
				if (curval >= threshold)
				{
					if (!peak && rel == 0)
						xpeaks.add(xpoints.previous(index));
					peak = true;
					rel = relsmp;
				}
				else if (rel != 0)
				{
					rel--;
					peak = false;
				}
				else
				{
					peak = false;
				}
				index++;
			}
			xpeaks.add(_buf._frames);
			break;
		}
	}

	public void convMs()
	{
		Object[] a = xpeaks.toArray();
		xpeaksArray = new int[a.length];
		xpeaksArrayMs = new float[a.length];
		for (int i = 0; i < a.length; i++)
		{
			xpeaksArray[i] = (Integer) a[i];
			xpeaksArrayMs[i] = _buf.smpToMs(xpeaksArray[i]);
		}
	}

	public void inlet(int sliceIndex)
	{
		try
		{
			if (!_buf._empty && !xpeaks.isEmpty())
			{
				if (unit_ms)
				{
					float peakBeg = xpeaksArrayMs[sliceIndex];
					float peakEnd = xpeaksArrayMs[sliceIndex + 1];
					if (clip && (peakEnd - peakBeg) > clip_length)
						peakEnd = _buf.smpToMs(xpoints.nearest(_buf.msToSmp(peakBeg + clip_length)));
					if (peakBeg != peakEnd)
						outlet(0, Atom.parse("slice " + peakBeg + " " + peakEnd));
				}
				else
				{
					int peakBeg = xpeaksArray[sliceIndex];
					int peakEnd = xpeaksArray[sliceIndex + 1];
					if (clip && (peakEnd - peakBeg) > clip_length)
						peakEnd = xpoints.nearest(peakBeg) + (int) clip_length;
					if (peakBeg != peakEnd)
						outlet(0, Atom.parse("slice " + peakBeg + " " + peakEnd));
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
		}
	}

	public void list(Atom[] list)
	{
		try
		{
			int sliceIndex = list[0].toInt();
			int size = list[1].toInt();
			size = size > 0 ? size : 1;
			if (!_buf._empty && !xpeaks.isEmpty())
			{
				if (unit_ms)
				{
					float peakBeg = xpeaksArrayMs[sliceIndex];
					float peakEnd = xpeaksArrayMs[sliceIndex + size];
					if (clip && (peakEnd - peakBeg) > clip_length)
						peakEnd = _buf.smpToMs(xpoints.nearest(_buf.msToSmp(peakBeg + clip_length)));
					if (peakBeg != peakEnd)
						outlet(0, Atom.parse("slice " + peakBeg + " " + peakEnd));
				}
				else
				{
					int peakBeg = xpeaksArray[sliceIndex];
					int peakEnd = xpeaksArray[sliceIndex + size];
					if (clip && (peakEnd - peakBeg) > clip_length)
						peakEnd = xpoints.nearest(peakBeg) + (int) clip_length;
					if (peakBeg != peakEnd)
						outlet(0, Atom.parse("slice " + peakBeg + " " + peakEnd));
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
		}
	}

	public void nearest(Atom[] list)
	{
		if (!_buf._empty && !xpeaks.isEmpty())
		{
			int[] res = new int[list.length];
			for (int i = 0; i < list.length; i++)
			{
				if (unit_ms)
					res[i] = xpoints.nearest(_buf.msToSmp(list[i].toFloat()));
				else res[i] = xpoints.nearest(list[i].toInt());
			}
			Atom[] n = Atom.parse("nearest");
			Atom[] r;
			if (unit_ms)
			{
				float[] resMs = new float[res.length];
				for (int i = 0; i < res.length; i++)
					resMs[i] = _buf.smpToMs(res[i]);
				r = Atom.newAtom(resMs);
			}
			else r = Atom.newAtom(res);
			Atom[] tmp = new Atom[r.length + 1];
			System.arraycopy(n, 0, tmp, 0, 1);
			System.arraycopy(r, 0, tmp, 1, r.length);
			outlet(0, tmp);
		}
	}

	public void peaks(Atom[] list)
	{
		if (!_buf._empty && !xpeaks.isEmpty())
		{
			int[] res = new int[list.length];
			for (int i = 0; i < list.length; i++)
				if (unit_ms)
					res[i] = xpeaks.nearest(_buf.msToSmp(list[i].toFloat()));
				else res[i] = xpeaks.nearest(list[i].toInt());
			Atom[] n = Atom.parse("peaks");
			Atom[] r;
			if (unit_ms)
			{
				float[] resMs = new float[res.length];
				for (int i = 0; i < res.length; i++)
					resMs[i] = _buf.smpToMs(res[i]);
				r = Atom.newAtom(resMs);
			}
			else r = Atom.newAtom(res);
			Atom[] tmp = new Atom[r.length + 1];
			System.arraycopy(n, 0, tmp, 0, 1);
			System.arraycopy(r, 0, tmp, 1, r.length);
			outlet(0, tmp);
		}
	}

	private static boolean signB(float f)
	{
		return f >= 0;
	}

	public void tocoll()
	{
		if (!_buf._empty && !xpeaks.isEmpty())
		{
			if (unit_ms)
				for (int i = 0; i < xpeaksArrayMs.length; i++)
					outlet(0, Atom.parse("tocoll " + i + " " + xpeaksArrayMs[i]));
			else
				for (int i = 0; i < xpeaksArray.length; i++)
					outlet(0, Atom.parse("tocoll " + i + " " + xpeaksArray[i]));
		}
	}
}

class BeatSlicerBuffer
{
	String _name = "";
	int _channels = 0;
	int _frames = 0;
	double _length = 0;
	long _size = 0;
	boolean _empty = true;
	float _sr = 44.1f;

	public BeatSlicerBuffer(String name)
	{
		_name = name;
		init();
	}

	public void init()
	{
		_empty = true;
		_channels = MSPBuffer.getChannels(_name);
		if (_channels != 0)
		{
			_frames = (int) MSPBuffer.getFrames(_name);
			_size = MSPBuffer.getSize(_name);
			_length = MSPBuffer.getLength(_name);
			_sr = (float) (_frames / _length);
			_empty = false;
		}
	}

	public void clear()
	{
		_name = "";
		_channels = 0;
		_frames = 0;
		_length = 0;
		_size = 0;
		_empty = true;
		_sr = 44.1f;
	}

	public void info()
	{
		System.out.println("_________BeatSlicer_Info___________");
		System.out.println("Name : " + _name);
		System.out.println("Channels : " + _channels);
		System.out.println("Frames : " + _frames);
		System.out.println("Size : " + _size);
		System.out.println("Length(ms) : " + _length);
		System.out.println("Sampling Rate : " + _sr);
	}

	public void run()
	{
	}

	public float smpToMs(int smp)
	{
		return smp / _sr;
	}

	public int msToSmp(float ms)
	{
		return (int) (ms * _sr);
	}
}


class BeatSlicerXPoints extends TreeSet<Integer>
{
	public BeatSlicerXPoints()
	{
		super();
	}

	public int previous(int entry)
	{
		if (isEmpty())
			return -1;
		if (contains(entry))
			return entry;
		int first = first(), last = last();
		if (entry < first)
			return first;
		if (entry > last)
			return last;
		int i = entry;
		while (i > first)
		{
			i--;
			if (contains(i))
				return i;
		}
		return first;
	}

	public int next(int entry)
	{
		if (isEmpty())
			return -1;
		if (contains(entry))
			return entry;
		int first = first(), last = last();
		if (entry < first)
			return first;
		if (entry > last)
			return last;
		int i = entry;
		while (i < last)
		{
			i++;
			if (contains(i))
				return i;
		}
		return last;
	}

	public int nearest(int entry)
	{
		if (isEmpty())
			return -1;
		if (contains(entry))
			return entry;
		int first = first(), last = last();
		if (entry < first)
			return first;
		if (entry > last)
			return last;
		int i = entry, j = entry;
		while (i > first && j < last)
		{
			i--;
			j++;
			if (contains(i))
				return i;
			if (contains(j))
				return j;
		}
		return first;
	}

	public int getIndex(int index)
	{
		if (isEmpty() || index < 0 || index >= this.last())
			return -1;
		if (index < this.size() / 2)
		{
			int cpt = 0;
			for (int k : this)
			{
				if (cpt == index)
					return k;
				cpt++;
			}
		}
		else
		{
			int cpt = this.size() / 2;
			for (int k : this)
			{
				if (cpt == index)
					return k;
				cpt--;
			}
		}
		return -1;
	}

	public String toString()
	{
		if (isEmpty())
			return "empty";
		StringBuffer b = new StringBuffer("");
		for (int i : this)
			b.append(i + " ");
		return b.toString();
	}
}