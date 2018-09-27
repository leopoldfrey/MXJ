package lf.net;

public class Friend
{
	private String ip;
	private String name;
	
	public Friend(String _ip, String _name)
	{
		ip = _ip;
		name = _name;
	}
	
	public Friend(String _n)
	{
		name = _n;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean equals(Object o)
	{
		if(o.getClass().toString().equalsIgnoreCase("class lf.net.Friend"))
		{
			Friend f = (Friend)o;
			return equals(f);
		}
		return false;
	}
	
	public boolean equals(Friend f)
	{
		return this.name.equalsIgnoreCase(f.name);
	}
	
	public String toString()
	{
		return ip + " : " + name;
	}
}
