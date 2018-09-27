package lf.net;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxSystem;
import com.cycling74.net.TcpReceiver;
import com.cycling74.net.TcpSender;

public class TcpConnection extends Connection
{
	private TcpSender sender;
	private TcpReceiver receiver;
	
	public TcpConnection(String _cn, String _th, String _h, String _hn, int _p)
	{
		super(_cn, _th,_h,_hn,_p);
		
		sender = new TcpSender(host,port);
		sender.setFailureCallback(this,"failed");
		sender.setSuccessCallback(this,"success");
		receiver = new TcpReceiver(port,this,"receive");
		receiver.setDebugString("TcpConnection");
	}
	
	protected void receive(Atom[] a)
	{
		super.receive(a);
	}
	
	public void close()
	{
		sender.close();
		receiver.close();
	}
	
	public void send(Atom[] a)
	{
		sender.send(Atom.union(new Atom[] { Atom.newAtom(thishost)},a));
	}
	
	public void failed(Atom[] a)
	{
		MaxSystem.error(connectionName+" send failed");
	}
	
	public void succes(Atom[] a)
	{
		MaxSystem.post(connectionName+" send succeed");
	}

	public String toString()
	{
		return "tcp "+super.toString();
	}
}
