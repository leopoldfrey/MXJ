


import com.cycling74.max.Atom;

import lf.util.Interpreter;

public class RPNInterpreter extends Interpreter
{
	private static final float version = 0.2f;
	
	public RPNInterpreter(Atom[] args)
	{
		super(args);
		post("... RPNInterpreter ... "+version);
	}
	protected void thru()
	{
		if(number!=-1)
		{
			isFloat = false;
			inter = "thru ";
			finishNumber();
			out(inter);
			cancel();
		}
	}
	protected void ch()
	{
		if(number!=-1)
		{
			isFloat = false;
			inter = "select ";
			finishNumber();
			out(inter);
			cancel();
		}
	}
	protected void atlevel()
	{
		if (number == -1 && fnumber == -1)
			inter = "atlevel full";
		else {
			inter = "atlevel ";
			finishNumber();
		}
		out(inter);
		cancel();
	}
	protected void ch_minus()
	{
		if(number!=-1)
		{
			isFloat = false;
			inter = "ch- ";
			finishNumber();
			out(inter);
			cancel();
		}
	}
	protected void ch_plus()
	{
		if(number!=-1)
		{
			isFloat = false;
			inter = "ch+ ";
			finishNumber();
			out(inter);
			cancel();
		}
	}
	protected void out()
	{
		ch()	;
	}
}
