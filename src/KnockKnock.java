import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxSystem;
import com.cycling74.net.MultiReceiver;
import com.cycling74.net.MultiSender;

import lf.LfObject;
import lf.net.Connection;
import lf.net.Friend;
import lf.net.TcpConnection;
import lf.net.UdpConnection;

// TODO KNOCKKNOCK continue & usage

public class KnockKnock extends LfObject
{
	private static final String MAXGROUP = "224.224.224.224";
	private static final int MAXPORT = 2044;
	private static final String OBJECTNAME = "KnockKnock";
	private static final byte TIMETOLIVE = (byte) 1;
	private MultiSender ms = null;
	private MultiReceiver mr = null;
	private int port = MAXPORT;
	private String ip = MAXGROUP;
	private boolean me = false;
	private InetAddress myIp;
	private String myIpStr;
	private String myIpName;
	private Vector<Friend> friends;
	private Vector<Connection> connections;
	
	public KnockKnock(Atom[] atoms)
	{
		version = 0.2f;
		build = "17/06/08";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "Knock Knock" };
		OUTLET_ASSIST = new String[] { "Who's there ?" };
		ms = new MultiSender();
		ms.setGroup(MAXGROUP);
		ms.setPort(MAXPORT);
		ms.setDebugString(OBJECTNAME);
		ms.setTimeToLive(TIMETOLIVE);
		mr = new MultiReceiver();
		mr.setPort(MAXPORT);
		mr.join(MAXGROUP);
		mr.setDebugString(OBJECTNAME);
		mr.setCallback(this, "receiver");
		friends = new Vector<Friend>(1,1);
		connections = new Vector<Connection>(1,1);
		declareAttribute("port", "getPort", "setPort");
		declareAttribute("ip", "getIP", "setIP");
		init(0, false, atoms);
		
		try
		{
			myIp = InetAddress.getLocalHost();
			myIpStr = myIp.getHostAddress();
			if(MaxSystem.isOsMacOsX())
				myIpName = myIp.getHostName().substring(0,myIp.getHostName().indexOf("."));
			else myIpName = myIp.getHostName();
			post("Who am I ? "+myIpName+" @ "+myIpStr);
		}
		catch (UnknownHostException e)
		{
			bail("UnknownHostException");
			e.printStackTrace();
		}
	}

	public void loadbang()
	{
		knock();
	}

	// attributes
	protected Atom[] getPort()
	{
		return new Atom[] { Atom.newAtom(port) };
	}

	protected void setPort(Atom[] a)
	{
		port = a[0].toInt();
		mr.setPort(port);
		ms.setPort(port);
	}

	protected Atom[] getIP()
	{
		return new Atom[] { Atom.newAtom(ip) };
	}

	protected void setIP(Atom[] a)
	{
		mr.leave(ip);
		ip = a[0].toString();
		ms.setGroup(ip);
		mr.join(ip);
	}

	// input
	public void inlet(int i)
	{
		ms.send(i);
	}

	public void inlet(float f)
	{
		ms.send(f);
	}

	public void list(Atom[] a)
	{
		ms.send(a);
	}
	
	public void con(Atom[] a)
	{
		if(a.length == 4)
		{
			me = true;
			createConnection(a);
			return;
		}
		if(a.length == 2)
		{
			closeConnection(a);
			return;
		}
	}

	
	// TODO KNOCK KNOCK tester l'existence de la connexion
	private void createConnection(Atom[] a)
	{
		debug("knock_con_new:"+Atom.toOneString(a));
		
		if(a.length < 4) return;
		
		if(!a[0].isString() || !a[1].isString() || !a[2].isString() || !a[3].isInt()) return;

		String conType = a[0].getString();
		String conName = a[1].getString();
		String friendName = a[2].getString();
		int tmpPort = a[3].getInt();
		
		Friend f = getFriend(friendName);
		
		if(f == null)
			return;

		Connection c = null;
		
		if(conType.equalsIgnoreCase("udp"))
			c = new UdpConnection(conName,myIpName,f.getIp(),f.getName(),tmpPort);
		else if(conType.equalsIgnoreCase("tcp"))
			c = new TcpConnection(conName,myIpName,f.getIp(),f.getName(),tmpPort);
		else
			return;
				
		connections.add(c);
		if(me)
			ms.send(Atom.parse(friendName+" con "+conType+" "+conName+" "+myIpName+" "+tmpPort));
		me = false;
	}
	
	private void closeConnection(Atom[] a)
	{
		debug("knock_con_close:"+Atom.toOneString(a));
		
		if(a.length < 2) return;
		
		if(!a[0].isString() || !a[1].isString()) return;

		String conName = a[0].getString();
//		String friendName = a[1].getString();
		
		Connection c = getConnection(conName);
		
		if(c == null) return;

		c.close();
		connections.remove(c);
		// TODO KNOCK KNOCK r�venir de la fermeture de la connection
	}
	
	public void closeAll()
	{
		for(int i = 0 ; i < connections.size() ; i++)
			connections.get(i).close();
	}
	
	public void anything(String s, Atom[] a)
	{
		if(a.length == 0)
		{
			me = true;
			ms.send(s,a);
			debug("knock_any:"+s+Atom.toOneString(a));
			return;
		}
		Connection c = getConnection(s);
		if(c == null)
		{
			me = true;
			debug("knock_any:"+s+Atom.toOneString(a));
			ms.send(s, a);
		}
		else
		{
			c.send(a);
			debug(c.getName()+"_send:"+Atom.toOneString(a));
		}
	}
	
	public void bang()
	{
		me = true;
		ms.send("knockknock",new Atom[] {});
		debug("knock_knock");
	}

	// output
	protected void receiver(Atom[] a)
	{
		debug("knock_receive:"+Atom.toOneString(a));
		if(!me)
		{
			if(a.length > 0)
				if(a[0].isString())
					if(a[0].getString().equalsIgnoreCase("knockknock"))
					{
						knock();
						return;
					} else if(a[0].getString().equalsIgnoreCase("whosthere"))
					{
						whosthere(Atom.removeFirst(a));
						return;
					} else if(a[0].getString().equalsIgnoreCase("leaving"))
					{
						leaving(Atom.removeFirst(a));
						return;
					} else {
						post(a[0].getString() + " " + myIpName);
						if(a[0].getString().equalsIgnoreCase(myIpName));
						{
							privateMessage(Atom.removeFirst(a));
							return;
						}
					}
			outlet(0, a);
		}
		else me = false;
	}

	private void privateMessage(Atom[] a)
	{
		debug("knock_private_msg:"+Atom.toOneString(a));
		if(a.length == 0) return;
		if(!a[0].isString()) return;
		if(a[0].getString().equalsIgnoreCase("con"))
			createConnection(Atom.removeFirst(a));
		else
			post("Private : "+Atom.toOneString(a));
	}
	
	private void knock()
	{
		me = true;
		ms.send("whosthere",Atom.parse(myIpStr+" "+myIpName));
		debug("knock_whosthere:"+myIpStr+" "+myIpName);
	}
	
	private void whosthere(Atom[] a)
	{
		Friend tmpFriend = new Friend(a[0].getString(),a[1].getString());
		
		if(!friends.contains(tmpFriend))
		{
			friends.add(tmpFriend);
			debug("knock_friend:new"+tmpFriend.toString());
//			post("You have a new friend !\n"+tmpFriend.toString());
			knock();
		}
	}
	
	private void leave()
	{
		debug("knock_leaving:"+myIpStr+" "+myIpName);
		me = true;
		ms.send("leaving",Atom.parse(myIpStr+" "+myIpName));
	}
	
	private void leaving(Atom[] a)
	{
		Friend tmpFriend = new Friend(a[0].getString(),a[1].getString());
		
		if(friends.contains(tmpFriend))
		{
			friends.remove(tmpFriend);
			debug("knock_friend:leaving:"+tmpFriend.toString());
//			post("Your friend is leaving !\n"+tmpFriend.toString());
		}
	}
	
	public void friends()
	{
		post("---------------------------------------");
		post("Friends : ");
		for(int i = 0 ; i < friends.size() ; i++)
		{
			post(friends.get(i).toString());
		}
		post("---------------------------------------");
	}
	
	public void connections()
	{
		post("---------------------------------------");
		post("Connections : ");
		for(int i = 0 ; i < connections.size() ; i++)
		{
			post(connections.get(i).toString());
		}
		post("---------------------------------------");
	}
	
	private Friend getFriend(String name)
	{
		int c = 0;
		while(c < friends.size())
		{
			if(friends.get(c).getName().equalsIgnoreCase(name))
				return friends.get(c);
			c++;
		}
		return null;
	}
	
	private Connection getConnection(String name)
	{
		int c = 0;
		while(c < connections.size())
		{
			if(connections.get(c).getName().equalsIgnoreCase(name))
			{
				return connections.get(c);
			}
			c++;
		}
		return null;
	}
	
	// termination
	protected void notifyDeleted()
	{
		me = true;
		leave();
		mr.close();
		mr = null;
		closeAll();
		connections.removeAllElements();
	}
}
