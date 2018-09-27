package lf.net;

public class Param
{
	private String name;
	private boolean R = true;
	private boolean W = true;
	
	public Param(String n)
	{
		name = n;
	}
	
	public Param(String n, int i)
	{
		name = n;
		switch(i)
		{
		case 3 :
			break;
		case 2 :
			R = false;
			W = true;
		case 1 :
			R = true;
			W = false;
		case 0 :
			R = false;
			W = false;
		default :
			System.out.println("Malformed authorization number : "+i);
			break;
		}
	}
	
	public Param(String n, boolean r, boolean w)
	{
		name = n;
		R = r;
		W = w;
	}
	
	public boolean isReadable()
	{
		return R;
	}
	
	public boolean isWritable()
	{
		return W;
	}
	
	public void setReadable(boolean b)
	{
		R = b;
	}
	
	public void setWritable(boolean b)
	{
		W = b;
	}
	
	public int getAuth()
	{
		if(R && W)
			return 3;
		else if(W)
			return 2;
		else if(R)
			return 1;
		else return 0;
	}

	public String toString()
	{
		return name + " " + getAuth();
	}
}
