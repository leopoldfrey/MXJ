package lf.net;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxSystem;

public abstract class Connection
{
	// Connection name
	protected String connectionName;
	// IP address
	protected String host;
	// IP name 
	protected String hostname;
	// my name
	protected String thishost;
	// port
	protected int port;
	
	public Connection(String _cn, String _th, String _h, String _hn, int _p)
	{
		connectionName = _cn;
		thishost = _th;
		host = _h;
		hostname = _h;
		port = _p;
	}
	
	protected void receive(Atom[] a)
	{
//		if(a[0].isString())
//			if(a[0].getString().equalsIgnoreCase(thishost))
				MaxSystem.post(connectionName+" received : "/*from "+hostname+" : //*/+Atom.toOneString(a));
	}
	
	public abstract void close();
	
	public abstract void send(Atom[] a);
	
	public boolean equals(Connection c)
	{
		return this.connectionName.equalsIgnoreCase(c.connectionName);
	}
	
	public boolean equals(Object o)
	{
		if((o.getClass().toString()).endsWith("Connection"))
			return equals((Connection)o);
		return false;
	}
	
	public String getName()
	{
		return connectionName;
	}
	
	public String toString()
	{
		return connectionName + " " + hostname + " " + port;
	}
}
