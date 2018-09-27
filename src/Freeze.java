import java.lang.reflect.Method;

import com.cycling74.max.Atom;
import com.cycling74.msp.MSPBuffer;
import com.cycling74.msp.MSPSignal;

import lf.LfMspObject;

// BUG FREEZE problem qd start > stop
// TODO FREEZE rec/play_pointer + ind�pendant de start en mode speed
// TODO FREEZE unfreeze sans clic
// TODO FREEZE fade quand croisement play/rec > buffer temporaire
// TODO FREEZE freeze + decay
// TODO FREEZE start/stop signal ?
// TODO FREEZE unit (smp/ms)

public class Freeze extends LfMspObject
{
	private int DEFAULT_BUFFER_CHANNELS = 2;
    private int DEFAULT_BUFFER_LENGTH = 220500;
    private int bufchan = DEFAULT_BUFFER_CHANNELS;
	private int buflen = DEFAULT_BUFFER_LENGTH;
    private float[][] buffer;
    private Method _p1;
//  private Method _p2;
    private int vec_len = 0;
    private boolean record = true;
    private boolean play = true;
	private int rec_pointer = 0;
	private int play_pointer = 0;
	private float fplay_pointer = 0;
    private int start = 0;
    private int stop = 0;
    private float speed = 1;
    private int interp = 1;
	private int xfade = 0;
	private int freeze = 0;
	private float dry = 0;
	private float wet = 1;
  
	private int indexB, cxfade;
	private float v1, v2, posX, step;

	public Freeze(Atom[] atoms)
	{
		switch (atoms.length)
		{
		case 5:
		case 4:
		case 3:
		case 2:
			bufchan = atoms[1].toInt();
		case 1:
			buflen = atoms[0].toInt();
			break;
		}
		
		version = 0.1f;
		build = "18/10/08";
		INLET_TYPES = new int[bufchan+1];
		for(int i =0 ; i < INLET_TYPES.length ; i++)
			INLET_TYPES[i] = SIGNAL;
		OUTLET_TYPES = new int[bufchan+2];
		for(int i =0 ; i < OUTLET_TYPES.length ; i++)
			OUTLET_TYPES[i] = SIGNAL;
		INLET_ASSIST = new String[] { "" };
		OUTLET_ASSIST = new String[] { "" };
		init();

		declareAttribute("record","getRecord","setRecord");
		declareAttribute("play","getPlay","setPlay");
		declareAttribute("interp");
		declareAttribute("speed");
		declareAttribute("debug");
		declareAttribute("xfade","fetXfade","setXfade");
		declareAttribute("start","getStart","setStart");
		declareAttribute("stop","getStop","setStop");
		declareAttribute("freeze","getFreeze","setFreeze");
		declareAttribute("dry");
		declareAttribute("wet");
		
		buffer = new float[bufchan][buflen];
		
		_p1 = getPerformMethod("p1");
//		_p2 = getPerformMethod("p2");
		
		reset();
	}
	
	public void reset()
	{
		start = 0;
		stop = buflen-1;
		speed = 1;
		rec_pointer = 0;
		play_pointer = stop - 10;
		fplay_pointer = 0;
		clear();
	}

	public void clear()
	{
		buffer = new float[bufchan][buflen];
	}
	
	public void fill(String buf)
	{
		try{
			bufchan = MSPBuffer.getChannels(buf);
			buflen = (int)MSPBuffer.getFrames(buf);
			buffer = new float[bufchan][buflen];
			
			for(int i =0 ; i < bufchan ; i++)
				for(int j =0 ; j < buflen ; j++)
					buffer[i][j] = MSPBuffer.peek(buf, i+1, j);
			
		} catch (Exception e) {
			error("Freeze : "+e.toString());
			debug(e);
		}
	}
	
	public void dump(String buf)
	{
		try{
			MSPBuffer.setFrames(buf, bufchan, buflen);
			for(int i =0 ; i < bufchan ; i++)
				for(int j =0 ; j < buflen ; j++)
					MSPBuffer.poke(buf, i+1, j, buffer[i][j]);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	public void dumpXfade(String buf)
	{
		try{
			MSPBuffer.setFrames(buf, bufchan, buflen);
			for(int i =0 ; i < bufchan ; i++)
				for(int j =start ; j <= stop && j < buflen ; j++)
					MSPBuffer.poke(buf, i+1, j, getVal(i, j));
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	public Method dsp(MSPSignal[] in, MSPSignal[] out)
	{
//		if(in[bufchan].connected)
//			return _p2;
		return _p1;
	}

	public void p1(MSPSignal[] in, MSPSignal[] out)
	{
		vec_len = in[0].vec.length;
		
		try{
			
			if(record)
			{
				for(int i =0 ; i < vec_len ; i++)
				{
					for(int j =0 ; j < bufchan ; j++)
						setVal(j,rec_pointer,in[j].vec[i]);
					out[bufchan].vec[i] = rec_pointer;
					rec_pointer++;
					if(rec_pointer > stop)
						rec_pointer = start;
					if(rec_pointer >= buflen)
						rec_pointer = 0;
				}
			}
			
			if(!play)
				return;
			
//			if(speed == 1)
//			{
			if(freeze > 0)
				for(int i =0 ; i < vec_len ; i++)
				{
					for(int j =0 ; j < bufchan ; j++)
						out[j].vec[i] = wet * buffer[j][play_pointer]/*getVal(j,play_pointer)*/ + dry * in[j].vec[i];
					out[bufchan+1].vec[i] = play_pointer;
					play_pointer++;
					if(play_pointer > stop)
						play_pointer = start;
					if(play_pointer >= buflen)
						play_pointer = 0;
				}
			else
				for(int i =0 ; i < vec_len ; i++)
				{
					for(int j =0 ; j < bufchan ; j++)
						out[j].vec[i] = getVal(j,play_pointer);
					out[bufchan+1].vec[i] = play_pointer;
					play_pointer++;
					if(play_pointer > stop)
						play_pointer = start;
					if(play_pointer >= buflen)
						play_pointer = 0;
				}
//				fplay_pointer = play_pointer;
//			}
//			else if(speed == -1)
//			{
//				for(int i =0 ; i < vec_len ; i++)
//				{
//					for(int j =0 ; j < bufchan ; j++)
//						out[j].vec[i] = getVal(j,play_pointer + start);
//					out[bufchan+1].vec[i] = play_pointer + start;
//					play_pointer--;
//					if(play_pointer < 0)
//						play_pointer = stop - start;
//				}
//				fplay_pointer = play_pointer;
//			}
//			else
//			{
//				switch(interp)
//				{
//				case 0: // NOINTERPOLATION
//					for(int i =0 ; i < vec_len ; i++)
//					{
//						for(int j =0 ; j < bufchan ; j++)
//							out[j].vec[i] = nointerpVal(fplay_pointer + start, j);
//						out[bufchan+1].vec[i] = fplay_pointer + start;
//						fplay_pointer += speed;
//						if(speed > 0 && fplay_pointer + start >= stop)
//							fplay_pointer = 0;
//						else if(fplay_pointer < 0)
//							fplay_pointer = stop - start;
//					}
//					break;
//				case 1:
//					for(int i =0 ; i < vec_len ; i++)
//					{
//						for(int j =0 ; j < bufchan ; j++)
//							out[j].vec[i] = interpVal(fplay_pointer + start, j);
//						out[bufchan+1].vec[i] = fplay_pointer + start;
//						fplay_pointer += speed;
//						if(speed > 0 && fplay_pointer + start >= stop)
//							fplay_pointer = 0;
//						else if(fplay_pointer < 0)
//							fplay_pointer = stop - start;
//					}
//					break;
//				case 2:
//					for(int i =0 ; i < vec_len ; i++)
//					{
//						for(int j =0 ; j < bufchan ; j++)
//							out[j].vec[i] = interpVal4p(fplay_pointer + start, j);
//						out[bufchan+1].vec[i] = fplay_pointer + start;
//						fplay_pointer += speed;
//						if(speed > 0 && fplay_pointer + start >= stop)
//							fplay_pointer = 0;
//						else if(fplay_pointer < 0)
//							fplay_pointer = stop - start;
//					}
//					break;
//				}
//				play_pointer = (int)fplay_pointer;
//			}

		} catch (Exception e){
			e.printStackTrace(System.err);
		}
	}
	
	private float getVal(int channel, int index)
	{
		if(xfade > 0)
		{
			cxfade = clip(xfade,0,(stop-start)/2);
			// OPTIMISE FREEZE XFADE 
			if (index > stop - cxfade)
			{
				posX = index - stop + cxfade;
				indexB = clip((int)(start + posX - cxfade),0);
				v1 = buffer[channel][index];
				v2 = buffer[channel][indexB];
				step = posX / (2*cxfade);
//				if(channel == 0)
//					debug("A ch:"+channel+" i1:"+index + " v1:" + v1 + " i2:" + indexB + " v2:" + v2 + " posX:" +posX + " step:"+ step + " > " + interp(v1, v2, step));
				return interp(v1, v2, step);
			} else if(index < start + cxfade) {
				posX = index - start;
				indexB = clipH((int)(stop + posX), buflen-1);
				v1 = buffer[channel][index];
				v2 = buffer[channel][indexB];
				step = (cxfade - posX) / (2*cxfade);
//				if(channel == 0)
//					debug("B ch:"+channel+" i1:"+index + " v1:" + v1 + " i2:" + indexB + " v2:" + v2 + " posX:" +posX + " step:"+ step + " > " + interp(v1, v2, step));
				return interp(v1, v2, step);
			}
//		if (index > stop - cxfade)
//		{
//			float v1, v2, step, pos, posX, indf;
//			posX = index - stop + cxfade - 1;
//			v1 = buffer[channel][index];
//			v2 = 0;
//			step = posX / (2*cxfade-1);
//			if(channel == 0)
//				debug("A ch:"+channel+" i1:"+index + " v1:" + v1 + " v2:" + v2 + " posX:" +posX + " step:"+ step + " > " + interp(v1, v2, step));
//			return interp(v1, v2, step);
//		} else if(index < start + cxfade) {
//			float v1, v2, step, pos, posX, indf;
//			posX = index - start;
//			v1 = buffer[channel][index];
//			v2 = 0;
//			step = (cxfade - posX) / (2*cxfade-1);
//			if(channel == 0)
//				debug("B ch:"+channel+" i1:"+index + " v1:" + v1 + " v2:" + v2 + " posX:" +posX + " step:"+ step + " > " + interp(v1, v2, step));
//			return interp(v1, v2, step);
//		}
		}//*/
		
		return buffer[channel][index];
	}
	
	private void setVal(int channel, int index, float val)
	{
		buffer[channel][index] = val;
	}

//	public void p2(MSPSignal[] in, MSPSignal[] out)
//	{
//		sr = (float)in[0].sr / 1000;
//		vec_len = in[0].vec.length;
//		vec_dur = vec_len/sr;
//	}
	
	private float nointerpVal(float p, int chan)
	{
		int pointer = (int)p;
		return getVal(chan,pointer);
	}

	private float interpVal(float p, int chan)
	{
		if(p < buflen - 1)
		{
			int pointer = (int)p;
			float delta = p - pointer;
			return interp(getVal(chan,pointer),getVal(chan,pointer+1),delta);
		}
		return getVal(chan,(int)p);
	}
	
	private float interpVal4p(float p, int chan)
	{
		if(p < buflen - 2 && p >= 1)
		{
			int pointer = (int)p;
			float delta = p - pointer;
			float fa = getVal(chan,pointer-1);
			float fb = getVal(chan,pointer);
			float fc = getVal(chan,pointer+1);
			float fd = getVal(chan,pointer+2);
			float f1 = 0.5f*(delta-1.0f);
			float f3 = delta*3.0f-1.0f;
			
			float amdf = (fa-fd)*delta;
			float cmb = fc-fb;
			float bma = fb-fa;
			return fb + delta*( cmb - f1 * ( amdf+bma+cmb*f3 ) );		
		}
		return getVal(chan,(int)p);
	}
	
	private float interp(float _f1, float _f2, float _step)
	{
		return _f1 + _step * (_f2 - _f1);
	}
			
	public float getStart()
	{
		return start;
	}

	public void setStart(float start)
	{
		this.start = (int)clip(start,0,buflen-1);
//		int tmp;
//		if(stop < start)
//		{
//			tmp = this.stop;
//			this.stop = this.start;
//			this.start = tmp;
//		}
	}

	public float getStop()
	{
		return stop;
	}

	public void setStop(float stop)
	{
		this.stop = (int)clip(stop,0,buflen-1);
//		int tmp;
//		if(stop < start)
//		{
//			tmp = this.stop;
//			this.stop = this.start;
//			this.start = tmp;
//		}
	}

	public int getXfade()
	{
		return xfade;
	}

	public void setXfade(int xfade)
	{
		this.xfade = clip(xfade,0);
	}

	public void state()
	{
		post("###### Freeze ######");
		post("buffer channel : "+bufchan);
		post("buffer length : "+buflen);
		post("start : "+start);
		post("stop : "+stop);
		post("recording : "+record+" / pointer : "+rec_pointer);
		post("playing : "+play+" / pointer : "+play_pointer+" / fpointer : "+fplay_pointer);
		post("speed : "+speed);
		post("interpolation : "+(interp == 1 ? "linear" : (interp == 2 ? "4-pointerpolation" : "no interpolation")));
		post("xfade : "+xfade);
	}

	public boolean isPlay()
	{
		return play;
	}

	public void setPlay(boolean play)
	{
		if(record)
			fplay_pointer = play_pointer = rec_pointer - 10 >= start ? rec_pointer - 10 : rec_pointer - 10 + (stop - start) ;
		this.play = play;
	}

	public boolean isRecord()
	{
		return record;
	}

	public void setRecord(boolean record)
	{
		if(record)
			rec_pointer = play_pointer + 10 < stop ? play_pointer + 10 : play_pointer + 10 - (stop - start) ;
		this.record = record;
	}

	public int isFreeze()
	{
		return freeze;
	}

	public void setFreeze(int freeze)
	{
		this.freeze = freeze;
		if(freeze > 0)
		{
			record = false;
			stop = rec_pointer;
			start = modulo2(rec_pointer-freeze, buflen);
//			post("freezing : "+freeze+" "+start+" "+stop);
		} else {
			stop = buflen - 1;
			rec_pointer = play_pointer + 10 < stop ? play_pointer + 10 : play_pointer + 10 - (stop - start) ;
			record = true;
			start = 0;
//			post("unfreezing");
		}
			
	}
}