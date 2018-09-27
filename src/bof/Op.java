package bof;
import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;
import com.cycling74.msp.MSPBuffer;

/**
 * a collection of buffer utilities
 * 
 * created on Apr 12, 2004 
 * @author bbn
 * 
 * TODO support joint operations on more than one channel.  
 * ie, DC offset
 *
 */
public class Op extends MaxObject {
	
	String bufname = null;
	
	Op(Atom[] a) {
		declareInlets(new int[]{DataTypes.ALL});
		declareOutlets(new int[]{DataTypes.ALL});
		createInfoOutlet(false);
		if (a.length>0) 
			set(a[0].toString());
	}
	
	public void set(String s) {
		bufname = s;
	}
	
	public void merge(final Atom[] a) {
		MaxSystem.deferLow(new Executable() {
			public void execute() {
				long maxFrames = 0;
				int channelSum = 0;
				int[] channels = new int[a.length];
				long[] frames = new long[a.length];
				String[] name = new String[a.length];
				for (int i=0;i<a.length;i++) {
					name[i] = a[i].toString();
					channels[i] = MSPBuffer.getChannels(name[i]);
					channelSum+=channels[i];
					frames[i] = MSPBuffer.getFrames(name[i]);
					if (frames[i] > maxFrames)
						maxFrames = frames[i]; 
				}
				MSPBuffer.setFrames(bufname, channelSum, maxFrames);
				int count = 1;
				for (int i=0;i<a.length;i++) {
					for (int j=0;j<channels[i];j++) {
						float[] data = MSPBuffer.peek(name[i], j+1);
						MSPBuffer.poke(bufname, count++, data);
					}
				}
			}
		}
		);
	}
	
	public void split(final Atom[] a) {
		MaxSystem.deferLow(new Executable() {
			public void execute() {
				int channels = MSPBuffer.getChannels(bufname);
				if (a.length != channels) {
					error("buf.Op: need " +channels+ " buffer name arguments "
							+" to split "+bufname);
					return;
				}
				long chanSize = MSPBuffer.getSize(bufname)/channels;
				for (int i=0;i<channels;i++) {
					String name = a[i].toString();
					MSPBuffer.setSize(name, 1, chanSize);
					MSPBuffer.poke(name, MSPBuffer.peek(bufname, i+1));
				}
			}
		}
		);
	}
	
	public void equals(String thatbuf) {
		boolean equal = true;
		long size = MSPBuffer.getSize(bufname);
		if (size != MSPBuffer.getSize(thatbuf))
			equal = false;
		else if (MSPBuffer.getChannels(bufname) 
					!= MSPBuffer.getChannels(thatbuf))
			equal = false;
		else {
			float[] thisData = MSPBuffer.peek(bufname);
			float[] thatData = MSPBuffer.peek(thatbuf);
			int index = 0;
			while ((equal)&&(index < size)) {
				if (thisData[index] != thatData[index])
					equal = false;
				index++;
			}
			outlet(0, equal);
		}
	}
	
	public void copyFrom(final String frombuf) {
		MaxSystem.deferLow(new Executable() { 
			public void execute() { 
				long size = MSPBuffer.getSize(frombuf);
				int channels = MSPBuffer.getChannels(frombuf);
				MSPBuffer.setSize(bufname, channels, size);
				MSPBuffer.poke(bufname, MSPBuffer.peek(frombuf));
			}
		}
		);
	}
	
	public void copyInto(final String destbuf) {
		MaxSystem.deferLow(new Executable() {
			public void execute() {
				long size = MSPBuffer.getSize(bufname);
				int channels = MSPBuffer.getChannels(bufname);
				MSPBuffer.setSize(destbuf, channels, size);
				MSPBuffer.poke(destbuf, MSPBuffer.peek(bufname));
			}
		}
		);
	}
	
	public void dataFrom(String frombuf) {
		dataMove(frombuf, bufname);
	}
	public void dataInto(String tobuf) {
		dataMove(bufname, tobuf);
	}
	
	private void dataMove(String frombuf, String tobuf)
	{
		long size = Math.min(MSPBuffer.getSize(frombuf), MSPBuffer.getSize(tobuf));
		int channels = Math.min(MSPBuffer.getChannels(frombuf), MSPBuffer.getChannels(tobuf));
		for (int c=1;c<=channels;c++)
		{
			MSPBuffer.poke(tobuf, c, 0, MSPBuffer.peek(frombuf, c, 0, size));
		}
	}
	
	public void max(int channel) {
		float[] samps = MSPBuffer.peek(bufname, channel);
		float max = Float.MIN_VALUE;
		int index = 0;
		for (int i=0;i<samps.length;i++) 
			if (samps[i] > max) {
				max = samps[i];
				index = i;
			}
		outlet(0, new Atom[] {Atom.newAtom(index), Atom.newAtom(max)});
	}
	
	protected float _maxabs(int channel) {
		float[] samps = MSPBuffer.peek(bufname, channel);
		float max = Float.MIN_VALUE;
		int index = 0;
		for (int i=0;i<samps.length;i++) 
			if (Math.abs(samps[i]) > max) {
				max = Math.abs(samps[i]);
				index = i;
			}
		return max;
	}

	public void maxabs(int channel)
	{
		outlet(0, _maxabs(channel));
	}

	public void maxabsT()
	{
		float max = Float.MIN_VALUE;
		for (int c = 1 ; c <= MSPBuffer.getChannels(bufname) ; c++)
			max = Math.max(max, _maxabs(c));
		outlet(0,max);
	}

	public void min(int channel) {
		float[] samps = MSPBuffer.peek(bufname, channel);
		float min = Float.MAX_VALUE;
		int index = 0;
		for (int i=0;i<samps.length;i++) 
			if (samps[i] < min) {
				min = samps[i];
				index = i;
			}
		outlet(0, new Atom[] {Atom.newAtom(index), Atom.newAtom(min)});
	}

	public void minabs(int channel) {
		float[] samps = MSPBuffer.peek(bufname, channel);
		float min = Float.MAX_VALUE;
		int index = 0;
		for (int i=0;i<samps.length;i++) 
			if (Math.abs(samps[i]) < min) {
				min =  Math.abs(samps[i]);
				index = i;
			}
		outlet(0, new Atom[] {Atom.newAtom(index), Atom.newAtom(min)});
	}

	public void removeDC(int channel) {
		float[] samps = MSPBuffer.peek(bufname, channel);
		double total = 0;
		for (int i=0;i<samps.length;i++) {
			total += samps[i];
		}
		float shift = (float)(total/(double)samps.length);
		for (int i=0;i<samps.length;i++) 
			samps[i] -= shift;
		MSPBuffer.poke(bufname, channel, samps);
	}
	
	public void normalize(int channel, float f) {
		float[] samps = MSPBuffer.peek(bufname, channel);
		double max = Double.MIN_VALUE;
		for (int i=0;i<samps.length;i++) 
			if (Math.abs(samps[i]) > max)
				max = Math.abs(samps[i]);
		double scale = ((double)Math.abs(f))/max;
		for (int i=0;i<samps.length;i++) 
			samps[i] *= scale;
		MSPBuffer.poke(bufname, channel, samps);
	}
	
	public void multiply(int channel, float f) {
		float[] samps = MSPBuffer.peek(bufname, channel);
		for (int i=0;i<samps.length;i++) 
			samps[i] *= f;
		MSPBuffer.poke(bufname, channel, samps);
	}
	
	public void gain(float f) {
		float factor = (float)Math.pow(10.0, f/20.);
		int chans = MSPBuffer.getChannels(bufname);
		for (int i=1;i<=chans;i++) {
			multiply(i, factor);
		}
	}
	
	public void add(int channel, float f) {
		float[] samps = MSPBuffer.peek(bufname, channel);
		for (int i=0;i<samps.length;i++) 
			samps[i] += f;
		MSPBuffer.poke(bufname, channel, samps);
	}
	
	public void reverse(int channel) {
		float[] samps = MSPBuffer.peek(bufname, channel);
		float[] temp = new float[samps.length];
		for (int i=0;i<samps.length;i++) 
			temp[i] = samps[samps.length-i-1];
		MSPBuffer.poke(bufname, channel, temp);
	}
	
	public void peek(int chan, long index) {
		outlet(0, MSPBuffer.peek(bufname, chan, index));
	}
	
	public void poke(int chan, long index, float f) {
		MSPBuffer.poke(bufname, chan, index, f);
	}
	
	public void pokeAll(int chan, float f) {
		float[] data = new float[(int)MSPBuffer.getSize(bufname)];
		for (int i=0;i<data.length;i++) {
			data[i] = f;
		}
		MSPBuffer.poke(bufname, chan, data);
	}
	
	protected double _sum(int channel)
	{
		float[] samps = MSPBuffer.peek(bufname, channel);
		double total = 0;
		for (int i=0;i<samps.length;i++) {
			total += samps[i];
		}
		return total;
	}
	
	protected double _sumabs(int channel)
	{
		float[] samps = MSPBuffer.peek(bufname, channel);
		double total = 0;
		for (int i=0;i<samps.length;i++) {
			total += Math.abs(samps[i]);
		}
		return total;
	}
	
	public void sum(int channel) {

		outlet(0, _sum(channel));
	}
	
	public void sumabs(int channel) {
		outlet(0, _sumabs(channel));
	}

	public void sumT()
	{
		int chans = MSPBuffer.getChannels(bufname);
		double total = 0;
		for (int c = 1 ; c <= chans ; c++)
			total += _sum(c);
		outlet(0, total/chans);
	}
	
	public void sumabsT()
	{
		int chans = MSPBuffer.getChannels(bufname);
		double total = 0;
		for (int c = 1 ; c <= chans ; c++)
			total += _sumabs(c);
		outlet(0, total/chans);
	}
	
	public void stddev(int channel) {
		outlet(0, Math.sqrt(doVariance(channel)));
	}  

	public void stddevabs(int channel) {
		outlet(0, Math.sqrt(doVarianceabs(channel)));
	}  

	public void stddevT()
	{	
		int chans = MSPBuffer.getChannels(bufname);
		double total = 0;
		for (int c = 1 ; c <= chans ; c++)
		{
			total += Math.sqrt(doVariance(c));
		}
		outlet(0, total/chans);
	}
	
	public void stddevabsT()
	{	
		int chans = MSPBuffer.getChannels(bufname);
		double total = 0;
		for (int c = 1 ; c <= chans ; c++)
		{
			total += Math.sqrt(doVarianceabs(c));
		}
		outlet(0, total/chans);
	}
	
	public void variance(int channel) {
		outlet(0, doVariance(channel));
	}
	
	public void varianceabs(int channel) {
		outlet(0, doVarianceabs(channel));
	}
	
	public void varianceT()
	{	
		int chans = MSPBuffer.getChannels(bufname);
		double total = 0;
		for (int c = 1 ; c <= chans ; c++)
		{
			total += doVariance(c);
		}
		outlet(0, total/chans);
	}
	
	public void varianceabsT()
	{	
		int chans = MSPBuffer.getChannels(bufname);
		double total = 0;
		for (int c = 1 ; c <= chans ; c++)
		{
			total += doVarianceabs(c);
		}
		outlet(0, total/chans);
	}
	
	private double doVariance(int channel) {
		float[] samps = MSPBuffer.peek(bufname, channel);
		double total = 0;
		for (int i=0;i<samps.length;i++) {
			total += samps[i];
		}
		double mean = total / (double)samps.length;
		total = 0;
		for (int i=0;i<samps.length;i++) {
			total += Math.pow(samps[i]-mean,2.);
		}
		return total/(double)(samps.length - 1);
	}
	
	private double doVarianceabs(int channel) {
		float[] samps = MSPBuffer.peek(bufname, channel);
		double total = 0;
		for (int i=0;i<samps.length;i++) {
			total += Math.abs(samps[i]);
		}
		double mean = total / (double)samps.length;
		total = 0;
		for (int i=0;i<samps.length;i++) {
			total += Math.pow(Math.abs(samps[i])-mean,2.);
		}
		return total/(double)(samps.length - 1);
	}
	
	protected double _average(int channel)
	{
		float[] samps = MSPBuffer.peek(bufname, channel);
		double total = 0;
		for (int i=0;i<samps.length;i++) {
			total += samps[i];
		}
		return _sum(channel)/(double)samps.length;
	}
	
	protected double _averageabs(int channel)
	{
		float[] samps = MSPBuffer.peek(bufname, channel);
		double total = 0;
		for (int i=0;i<samps.length;i++) {
			total += Math.abs(samps[i]);
		}
		return total/(double)samps.length;
	}
	
	public void average(int channel)
	{
		outlet(0, _average(channel));
	}
	
	public void averageabs(int channel)
	{
		outlet(0, _averageabs(channel));
	}
	
	public void averageT()
	{
		int chans = MSPBuffer.getChannels(bufname);
		double total = 0;
		for (int c = 1 ; c <= chans ; c++)
			total += _average(c);
		outlet(0, total/chans);
	}
	
	public void averageabsT()
	{
		int chans = MSPBuffer.getChannels(bufname);
		double total = 0;
		for (int c = 1 ; c <= chans ; c++)
			total += _averageabs(c);
		outlet(0, total/chans);
	}
	
	public int _numsmpabove(float value)
	{
		float[] samps = MSPBuffer.peek(bufname);
		int num = 0;
		for (int i=0;i<samps.length;i++)
		{
			if(Math.abs(samps[i]) > value)
				num++;
		}
		return num;
	}
	
	public void numsmpabove(float value)
	{
		outlet(0, _numsmpabove(value));
	}
	
	public void getConvThres(int numsmp)
	{
		float [] samps = MSPBuffer.peek(bufname);
		FloatVectorSort vs = new FloatVectorSort(samps.length);
		vs.addabs(samps);
		outlet(0, new Atom[] {Atom.newAtom("convthres"), Atom.newAtom(vs.get(samps.length-numsmp))});
	}
	
	public void mean(int channel) {
		average(channel);
	}
	
	public void getLength() {
		outlet(0, MSPBuffer.getLength(bufname));
	}
		
	public void getLengthSmp()
	{
		outlet(0, MSPBuffer.getSize(bufname)/MSPBuffer.getChannels(bufname));
	}
		
	public void setLength(int i, double d) {
		MSPBuffer.setLength(bufname, i, d);
	}
	
	public void getSize() {
		outlet(0, MSPBuffer.getSize(bufname));
	}
	
	public void setSize(int channels, long size) {
		MSPBuffer.setSize(bufname, channels, size);
	}
	
	public void getChannels() {
		outlet(0, MSPBuffer.getChannels(bufname));
	}

	//redFrik 050513, 051128, 060927
	public void copyFrom(String frombuf, long destoffset) {
		copyFrom(frombuf, destoffset, 0);
	}

	public void copyFrom(String frombuf, long destoffset, long srcoffset) {
		long length= MSPBuffer.getSize(frombuf);
		copyFrom(frombuf, destoffset, srcoffset,  length-srcoffset);
	}

	public void copyFrom(final String frombuf, final long destoffset, final long srcoffset, final long srcsize) {
		MaxSystem.deferLow(new Executable() {	
			public void execute() {	
				int channels= MSPBuffer.getChannels(frombuf);
				for(int i= 1; i<=channels; i++) {
					MSPBuffer.poke(bufname, i, destoffset, MSPBuffer.peek(frombuf, i, srcoffset, srcsize));
				}
				outlet(0, "done");
			}
		});
	}

}

class FloatVectorSort extends Vector<Float>
{
	public boolean changes = false;
	
	public FloatVectorSort()
	{
		super();
	}
	
	public FloatVectorSort(int cap)
	{
		super(cap);
	}
	
	public FloatVectorSort(int cap, int inc)
	{
		super(cap,inc);
	}
	
	public FloatVectorSort(Vector v)
	{
		super(v.size(),1);
		for(int i = 0;i<v.size();i++)
		{
			insertSort((Float)v.get(i));
		}
	}
	
	public Vector<Float> getVector()
	{
		Vector<Float> tmp = new Vector<Float>(capacity(),1);
		for(int i = 0;i<size();i++)
			tmp.add(get(i));
		return tmp;
	}
	
	public boolean lessThan(Float f1, Float f2)
	{
		return (f1 < f2);
	}

	public boolean lessThanOrEqual(Float f1, Float f2)
	{
		return (f1 <= f2);
	}
	

	/**
	 * rules for sorting elements 
	 * true if obj1 > obj2
	 */
	public boolean greaterThan(Float f1, Float f2)
	{
		return !lessThanOrEqual(f1,f2);
	}

	/**
	 * rules for sorting elements 
	 * true if obj1 > obj2
	 */
	public boolean greaterThanOrEqual(Float f1, Float f2)
	{
		return !lessThan(f1,f2);
	}

	/**
	 * insert an element at the right index in the vector
	 */
	public void insertSort(float element)
	{
		int i = size() - 1;
		addElement(element);
		while ((i >= 0) && (greaterThan(elementAt(i), element)))
		{
			setElementAt(elementAt(i), i + 1);
			i--;
		}
		setElementAt(element, i + 1);
	}
	
	public boolean add(float element)
	{
		if(!contains(element))
		{
			insertSort(element);
			return true;
		} 
		return false;
	}
	
	public void add(float[] array)
	{
		for(float f : array)
			addElement(f);
		sort();
	}
	
	public void addabs(float[] array)
	{
		for(float f : array)
			addElement(Math.abs(f));
		sort();
	}
	
	public boolean sort()
	{
		changes = false;
		quickSort(0, size() - 1);
		return changes;
	}
	
	private void quickSort(int left, int right)
	{
		if (right > left)
		{
			float o1 = elementAt(right);
			int i = left - 1;
			int j = right;
			while (true)
			{
				while (lessThan(elementAt(++i), o1)){}
				while (j > 0)
					if (lessThanOrEqual(elementAt(--j), o1))
						break; // out of while
				if (i >= j)
					break;
				swap(i, j);
			}
			swap(i, right);
			quickSort(left, i - 1);
			quickSort(i + 1, right);
		}
	}
	
	private void swap(int loc1, int loc2)
	{
		if(loc1 != loc2)
		{
			changes = true;
			float tmp = elementAt(loc1);
			setElementAt(elementAt(loc2), loc1);
			setElementAt(tmp, loc2);
		}
	}
	
	public Float elementAt(int pos)
	{
		return super.elementAt(pos);
	}
	
	public Float get(int pos)
	{
		return super.get(pos);
	}
	
	public String toString()
	{
		String tmp = "";
		for(int i = 0 ; i < size() ; i++)
			tmp += get(i)+(i+1 != size() ? " " : "");
		return tmp;
	}
}



