


import com.cycling74.max.Atom;

import lf.util.Interpreter;

public class AtModeInterpreter extends Interpreter
{
	private static final float version = 0.2f;
	
	public AtModeInterpreter(Atom[] args)
	{
		super(args);
		post("... AtModeInterpreter ... "+version);
	}
	protected void thru()
	{
		if (!isFloat)
		{
			if(number!=-1)
			{
				finishNumber();
				inter = inter.concat(" thru ");
			}
		}
	}
	protected void ch()
	{
		boolean fl = isFloat;
		if (number != -1 || fnumber != -1)
		{
			finishNumber();
			if(!inter.startsWith("ch") && !inter.startsWith("se") && !fl)
				inter = "select ".concat(inter);
		} else
		{
			inter = "select all";
		}
		if(!fl || inter.startsWith("atlevel"))
			out(inter);
		inter="";
	}
	protected void atlevel()
	{
		if (number == -1 && fnumber == -1)
		{
			if (!inter.startsWith("se"))
			{
				inter = "atlevel ";
			} else
			{
				inter = inter.concat("full");
				out();
			}
		} else
		{
			if(!isFloat || !inter.startsWith("atlevel"))
			{
				isFloat = false;
				fnumber = -1;
			}
			finishNumber();
			out();
			inter = "atlevel ";
		}
	}
	protected void ch_minus()
	{
		finishNumber();
		out();
		inter = "ch- ";
	}
	protected void ch_plus()
	{
		finishNumber();
		out();
		inter = "ch+ ";
	}
	protected void out()
	{
		if(isFloat && !inter.startsWith("atlevel"))
		{
			isFloat = false;
			fnumber = -1;
		}
		if (number != -1)
			finishNumber();
		if (inter.length() != 0)
		{
			if (!inter.startsWith("se") && !inter.startsWith("ch"))
				inter = "select ".concat(inter);
			outlet(0, Atom.parse(inter));
		}
		inter = "";
	}

//	OLD : pas de visualisation en direct de la s�lection	
/*	protected void atlevel()
	{
		if (!isFloat)
		{
			if (number == -1)
			{
				if (!inter.startsWith("se"))
				{
					inter = "set to ";
				} else
				{
					inter = inter.concat("full");
					out();
				}
			} else
			{
				finishNumber();
				inter = "set ".concat(inter).concat(" to ");
			}
		}
	}
	protected void ch()
	{
		if (!isFloat)
		{
			if (number != -1)
			{
				finishNumber();
				inter = "select ".concat(inter);
			} else
			{
				inter = "select all";
			}
			out();
		}
	}
	protected void ch_minus()
	{
		if (!isFloat)
		{
			finishNumber();
			inter = inter.concat(" - ");
		}
	}
	protected void ch_plus()
	{
		if (!isFloat)
		{
			finishNumber();
			inter = inter.concat(" + ");
		}
	}
	protected void out()
	{
		if (number != -1)
			finishNumber();
		if (inter.length() != -1)
		{
			if (!inter.startsWith("se"))
				inter = "select ".concat(inter);
			outlet(0, Atom.parse(inter));
		}
		inter = "";
	}//*/
}

