package lf.util;


import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public abstract class Interpreter extends MaxObject
{
	protected int number = -1;
	protected float fnumber = -1;
	protected String inter = "";
	protected boolean isFloat = false;
	protected int maxChannelLevel = 100;
	protected int maxDMXChannels = 512;
	protected final static int CH = 42; // '*'
	protected final static int CH_PLUS = 43; // '+'
	protected final static int CH_MINUS = 45; // '-'
	protected final static int THRU = 47; // '/'
	protected final static int ATLEVEL = 61; // '='
	protected final static int ENTER = 3; // ?
	protected final static int RETURN = 13; // '\n'
	protected final static int COMMA = 44; // '.'
	protected final static int TAB = 9; // '\t'
	protected final static int ESCAPE = 27; // esc
	protected final static int DELETE = 8; // del
	protected final static int SUPPR = 127; // suppr
	protected final static int UP = 30; // up arrow
	protected final static int DOWN = 31; // down arrow
	protected final static int LEFT = 28; // left arrow
	protected final static int RIGHT = 29; // right arrow
	protected final static int HOME = 1; // home
	protected final static int END = 4; // end
	protected final static int PAGE_UP = 11; // page up
	protected final static int PAGE_DOWN = 12; // page down
	// 0,1,2,3,....
	protected final static int ZERO = 48;
	protected final static int ONE = 49;
	protected final static int TWO = 50;
	protected final static int THREE = 51;
	protected final static int FOUR = 52;
	protected final static int FIVE = 53;
	protected final static int SIX = 54;
	protected final static int SEVEN = 55;
	protected final static int EIGHT = 56;
	protected final static int NINE = 57;
	// F1,F2 ... F10
	protected final static int FONE = 122;
	protected final static int FTWO = 120;
	protected final static int FTHREE = 99;
	protected final static int FFOUR = 118;
	protected final static int FFIVE = 96;
	protected final static int FSIX = 97;
	protected final static int FSEVEN = 98;
	protected final static int FEIGHT = 100;
	protected final static int FNINE = 101;
	protected final static int FTEN = 109;
	public Interpreter(Atom[] args)
	{
		declareIO(1, 2);
		createInfoOutlet(false);
		setInletAssist(0, "Sequential Ascii codes of typed keys.");
		setOutletAssist(0, "Interpretation of typed keys.");
		setOutletAssist(1, "Number being typed");
		declareAttribute("maxDMXChannels");
		declareAttribute("maxChannelLevel");
	}
	public void inlet(int i)
	{
		switch (i)
		{
			case ENTER:
			case RETURN:
				out();
				break;
			case CH_PLUS:
				ch_plus();
				break;
			case CH_MINUS:
				ch_minus();
				break;
			case ATLEVEL:
				atlevel();
				break;
			case THRU:
				thru();
				break;
			case CH:
				ch();
				break;
			case COMMA:
				isFloat = true;
				break;
//			case TAB:
//				out("TAB");
//				break;
			case ESCAPE:
				cancel();
				out("ESCAPE");
				break;
			case DELETE:
			case SUPPR:
				removeNum();
				break;
//			case UP:
//				out("UP");
//				break;
//			case DOWN:
//				out("DOWN");
//				break;
//			case LEFT:
//				out("LEFT");
//				break;
//			case RIGHT:
//				out("RIGHT");
//				break;
//			case HOME:
//				out("HOME");
//				break;
//			case END:
//				out("END");
//				break;
//			case PAGE_UP:
//				out("PAGE_UP");
//				break;
//			case PAGE_DOWN:
//				out("PAGE_DOWN");
//				break;
//			case FONE:
//				out("F1");
//				break;
//			case FTWO:
//				out("F2");
//				break;
//			case FTHREE:
//				out("F3");
//				break;
//			case FFOUR:
//				out("F4");
//				break;
//			case FFIVE:
//				out("F5");
//				break;
//			case FSIX:
//				out("F6");
//				break;
//			case FSEVEN:
//				out("F7");
//				break;
//			case FEIGHT:
//				out("F8");
//				break;
//			case FNINE:
//				out("F9");
//				break;
//			case FTEN:
//				out("F10");
//				break;
			case ZERO:
			case ONE:
			case TWO:
			case THREE:
			case FOUR:
			case FIVE:
			case SIX:
			case SEVEN:
			case EIGHT:
			case NINE:
				addNumber(Character.getNumericValue((char) i));
				break;
			default:
				// post("OTHER : " + i);
				break;
		}
	}
	protected abstract void ch_plus();
	protected abstract void ch_minus();
	protected abstract void ch();
	protected abstract void atlevel();
	protected abstract void thru();
	protected abstract void out();
	protected void finishNumber()
	{
		if ((number != -1 || fnumber != -1))
		{
			if ((!isFloat || inter.startsWith("ch")) && !inter.startsWith("setto"))
				inter = inter.concat((new Integer(clip(1, maxDMXChannels, number))).toString());
			else if (fnumber == -1)
				inter = inter.concat((new Integer(clip(0, maxChannelLevel, number))).toString());
			else	
				inter = inter.concat(floatToString(clip(0, maxChannelLevel, fnumber)));
			number = -1;
			fnumber = -1;
			isFloat = false;
		}
	}
	protected void addNumber(int i)
	{
		if (!isFloat)
		{
			if (number == -1)
			{
				number = i;
			} else
			{
				int tmp = ((new Integer(number)).toString()).length();
				if (tmp < 4)
				{
					number = number * 10 + i;
				} else if (tmp == 4)
				{
					int mil = number / 1000;
					number = number - (mil * 1000);
					number = number * 10 + i;
				}
			}
			outlet(1,number);
		} else
		{
			if (fnumber == -1)
			{
				fnumber = number + ((float) i) / 10;
			} else
			{
				fnumber += ((float) i) / 100;
			}
			outlet(1,fnumber);
		}
	}
	protected void removeNum()
	{
		if (!isFloat)
		{
			if (number == -1)
			{
				number = 0;
			} else
			{
				number = number / 10;
			}
			outlet(1,number);
		} else
		{
			if (fnumber == -1)
			{
				fnumber = 0;
			} else
			{
				fnumber += fnumber / 10;
			}
			outlet(1,fnumber);
		}
	}
	protected void cancel()
	{
		number = -1;
		fnumber = -1;
		inter = "";
		isFloat = false;
	}
	protected void out(String s)
	{
		outlet(0, Atom.parse(s));
		cancel();
	}
	protected float clip(float lowerLimit, float upperLimit, float toClip)
	{
		float tmp = (toClip >= lowerLimit ? toClip : lowerLimit);
		tmp = (tmp <= upperLimit ? toClip : upperLimit);
		return tmp;
	}
	protected int clip(int lowerLimit, int upperLimit, int toClip)
	{
		int tmp = (toClip >= lowerLimit ? toClip : lowerLimit);
		tmp = (tmp <= upperLimit ? toClip : upperLimit);
		return tmp;
	}
	private String floatToString(float f)
	{
		Formatter rF = new Formatter();
		rF.max_right = 2;
		rF.min_right = 2;
		return rF.format(f);
	}
}