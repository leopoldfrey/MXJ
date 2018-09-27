import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxClock;
import com.cycling74.net.MultiReceiver;
import com.cycling74.net.MultiSender;
import com.cycling74.net.TcpReceiver;
import com.cycling74.net.TcpSender;

import lf.LfObject;

public class RetesConnection extends LfObject implements Executable
{
	private static final int POLL = 1000;
	private static final String RETESGROUP = "224.0.0.2";
	private static final int RETESPORT = 14000;
	private static final int RETESPORT2 = 14200;
	private static final String OBJECTNAME = "RetesConnection";
	private static final byte TIMETOLIVE = (byte) 1;
	private MultiSender udpSender = null;
	private MultiReceiver udpReceiver = null;
	private Vector<TcpConnection> tcpSenders;
	private TcpReceiver tcpReceiver;
	public int port = RETESPORT;
	public String ip = RETESGROUP;
	public String name = "default";
	public InetAddress addr;
	public MaxClock clock;

	// creation
	RetesConnection(Atom args[])
	{
		version = 0.1f;
		build = "12/12/07";
		declareIO(1, 2);
		createInfoOutlet(false);
		udpSender = new MultiSender(RETESGROUP,RETESPORT,OBJECTNAME+"_udp_send",TIMETOLIVE);
		udpReceiver = new MultiReceiver(RETESGROUP,RETESPORT,this,"UDPreceiver");
		udpReceiver.setDebugString(OBJECTNAME+"_udp_recv");
		switch(args.length)
		{
		case 1:
			name = args[0].toString();
			break;
		case 0:
			name = System.getProperties().getProperty("user.name");
			break;
		}
		outlet(0,concat("name",new Atom[]{Atom.newAtom(name)}));
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e){}
		tcpReceiver = new TcpReceiver(RETESPORT2,this,"TCPreceiver");
		tcpSenders = new Vector<TcpConnection>();
		udpSender.send(Atom.parse("connect "+name+" "+addr.getHostAddress()));
		clock = new MaxClock(this);
		clock.delay(POLL);
	}

	// input
	public void inlet(int i)
	{
		sendTcp(Atom.parse(""+i));
	}

	public void inlet(float f)
	{
		sendTcp(Atom.parse(""+f));
	}

	public void list(Atom[] a)
	{
		sendTcp(a);
	}

	public void anything(String s, Atom[] a)
	{
		if(s.equalsIgnoreCase("/chat"))
		{
			sendTcp(Atom.parse("/chat "+name+": "+Atom.toOneString(a)));
			outlet(0,Atom.parse("/chat "+name+": "+Atom.toOneString(a)));
		}
		else if(s.equalsIgnoreCase("name"))
		{
			if(!Atom.toOneString(a).equalsIgnoreCase(name))
			{
				sendTcp(Atom.parse("disconnect "+name+" "+addr.getHostAddress()));
				name = Atom.toOneString(a);
				sendTcp(Atom.parse("connect "+name+" "+addr.getHostAddress()));
				outlet(0,concat("name",new Atom[]{Atom.newAtom(name)}));
			}
		}
		else
			sendTcp(concat(s, a));
	}

	private void sendTcp(Atom[] a)
	{
		for(TcpConnection tcpi : tcpSenders)
			tcpi.send(a);
	}
	
	private boolean addTcp(String n, String ipstr)
	{
		if(addr.getHostAddress().equalsIgnoreCase(ipstr))
			return false;
		TcpConnection tcpi = new TcpConnection(n, ipstr,RETESPORT2);
		if(!tcpSenders.contains(tcpi))
		{
			tcpSenders.add(tcpi);
			tcpi.send(Atom.parse("connect "+addr.getHostAddress()));
			System.out.println("Adding connection to : "+tcpi.toString());
			outlet(0,Atom.parse("/chat <"+tcpi.getName()+" joined Retes>"));
			return true;
		}
		for(TcpConnection tc : tcpSenders)
			if(tc.equals(tcpi))
				tc.setName(n);
		return false;
	}
	
	private boolean remTcp(String n, String ipstr)
	{
		TcpConnection tcpi = new TcpConnection(n, ipstr,RETESPORT2);
		if(tcpSenders.contains(tcpi) && !addr.getHostAddress().equalsIgnoreCase(ipstr))
		{
			tcpSenders.remove(tcpi);
			System.out.println("Removing connection to : "+tcpi.toString());
			outlet(0,Atom.parse("/chat <"+tcpi.getName()+" leaved...>"));
			return true;
		}
		return false;
	}
	
	public void listTcp()
	{
		for(TcpConnection tcpi : tcpSenders)
			System.out.println(tcpi.toString());
	}
	
	public void menuTcp()
	{
		udpSender.send(Atom.parse("connect "+name+" "+addr.getHostAddress()));
		outlet(1, Atom.parse("clear"));
		for(TcpConnection tcpi : tcpSenders)
		{
			outlet(1, Atom.parse("append "+tcpi.getName()+"@"+tcpi.getAddress()));
		}
	}
	
	// TCP RECEIVER
	protected void TCPreceiver(Atom[] a)
	{
		if(a[0].toString().equals("connect"))
		{
			addTcp(a[1].toString(),a[2].toString());
		} else if(a[0].toString().equals("disconnect")) {
			remTcp(a[1].toString(),a[2].toString());
		} else
			outlet(0, a);
	}
	
	// UDP SOCKET RECEIVER
	protected void UDPreceiver(Atom[] a)
	{
		if(a[0].toString().equals("connect"))
		{
			if(addTcp(a[1].toString(),a[2].toString()))
			{
				udpSender.send(a);
				udpSender.send(Atom.parse("connect "+name+" "+addr.getHostAddress()));
			}
			return;
		}
	}

	// termination
	protected void notifyDeleted()
	{
		sendTcp(Atom.parse("disconnect "+name+" "+addr.getHostAddress()));
		udpReceiver.leaveAllGroups();
		udpReceiver.close();
		for(TcpSender tcpi : tcpSenders)
			tcpi.close();
		tcpReceiver.close();
		clock.release();
	}

	public void execute()
	{
		menuTcp();
		clock.delay(POLL);
	}
}

class TcpConnection extends TcpSender
{
	private String name;
	
	public TcpConnection(String name, String ip, int port)
	{
		super(ip,port);
		this.name = name;
	}
	
	public boolean equals(TcpConnection t)
	{
		return this.getAddress().equalsIgnoreCase(t.getAddress()) && this.getPort() == t.getPort();
	}

	public boolean equals(Object o)
	{
		if(o.getClass().equals(TcpConnection.class))
		{
			TcpConnection t = (TcpConnection)o;
			return this.getAddress().equalsIgnoreCase(t.getAddress()) && this.getPort() == t.getPort();
		}
		return false;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public String toString()
	{
		return name+" "+this.getAddress();
	}
}
