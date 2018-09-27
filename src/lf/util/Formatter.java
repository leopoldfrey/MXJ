package lf.util;


public class Formatter
{
	public int min_right = -1;
	public int max_right = -1;
	public int min_left = -1;
	public int max_left = -1;
	public boolean plus = false; 
	
	public Formatter()
	{
	}
	
	public Formatter(int minl, int maxl, int minr, int maxr, boolean plus)
	{
		min_left = minl;
		max_left = maxl;
		min_right = minr;
		max_right = maxr;
		this.plus = plus;
	}
	
	public Formatter(int minl, int maxl, int minr, int maxr)
	{
		min_left = minl;
		max_left = maxl;
		min_right = minr;
		max_right = maxr;
	}
	
	public String format(float f)
	{
		boolean neg = f < 0;
		String tmp = "" + Math.abs(f);
		int EXP = tmp.indexOf('E');
		if(EXP != -1)
		{
			String a = tmp.substring(0,tmp.indexOf('.'));
			String b = tmp.substring(tmp.indexOf('.')+1,tmp.indexOf('.')+2);
			int E = Integer.parseInt(tmp.substring(EXP+1));
			String tmp2 = "0.";
			while(E < -a.length())
			{
				tmp2 += '0';
				E++;
			}
			tmp2 += a + (b.equalsIgnoreCase("0") ? "" : b);
			
			tmp = tmp2;
		}
		int ind = tmp.indexOf('.');
		if(ind != -1)
		{
			String left = tmp.substring(0,ind);
			String right = tmp.substring(ind+1);
			if(max_right != -1 && right.length() > max_right)
			{
				right = right.substring(0,max_right);
			}
			if(min_right != -1 && right.length() < min_right)
			{
				while (right.length() < min_right)
					right = right + '0';
			}	
			if(max_left != -1 && left.length() > max_left)
			{
				left = left.substring(left.length() - max_left);
			}
			if(min_left != -1 && left.length() < min_left)
			{
				while (left.length() < min_left)
					left = '0' + left;
			}	
			tmp = left + '.' + right;
			if(tmp.endsWith("."))
				return (neg ? '-' : (plus ? '+' : "")) + tmp.substring(0, tmp.indexOf('.'));
			return (neg ? '-' : (plus ? '+' : "")) + tmp;
		}
		if(max_left != -1 && tmp.length() > max_left)
		{
			tmp = tmp.substring(tmp.length() - max_left);
		}
		if(min_left != -1 && tmp.length() < min_left)
		{
			while (tmp.length() < min_left)
				tmp = '0' + tmp;
		}	
		return (neg ? '-' : (plus ? '+' : "")) + tmp;
	}
}
