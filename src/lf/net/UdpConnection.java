package lf.net;

import com.cycling74.max.Atom;
import com.cycling74.net.UdpReceiver;
import com.cycling74.net.UdpSender;

public class UdpConnection extends Connection
{
	private UdpSender sender;
	private UdpReceiver receiver;
	
	public UdpConnection(String _cn, String _th, String _h, String _hn, int _p)
	{
		super(_cn,_th,_h,_hn,_p);
		
		sender = new UdpSender(host,port);
		receiver = new UdpReceiver(port);
		receiver.setCallback(this,"receive");
		receiver.setDebugString("UdpConnection");
	}
	
	protected void receive(Atom[] a)
	{
//		MaxSystem.post(connectionName+" received "+Atom.toOneString(a));
		super.receive(a);
	}
	
	public void close()
	{
		receiver.close();
	}
	
	public void send(Atom[] a)
	{
//		MaxSystem.post(connectionName+" send "+Atom.toOneString(a));
		sender.send(a);
	}

	public String toString()
	{
		return "udp "+super.toString();
	}
}
