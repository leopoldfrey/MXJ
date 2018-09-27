package test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;
import com.cycling74.net.UdpReceiver;
import com.cycling74.net.UdpSender;

public class StringEncoding extends MaxObject
{
	private int port_in = 14000;
	private int port_out = 14001;
	private String adress_out = "127.0.0.1";
	private UdpReceiver r;
	private UdpSender s;
	public static File logfile;

	// arguments port_in port_out adress_out
	public StringEncoding(Atom args[])
	{
		switch(args.length)
		{
		case 3:
			adress_out = args[2].getString();
		case 2:
			port_out = args[1].getInt();
		case 1:
			port_in = args[0].getInt();
			break;
		}
		
		r = new UdpReceiver(port_in);
		r.setCallback(this, "receive");
//		r.run();
		
		s = new UdpSender(adress_out,port_out);
	}
	
	private void receive(Atom[] a)
	{
		log(Atom.toOneString(a));
	}
	
	public void send()
	{
		s.send(new Atom[]{Atom.newAtom("test"),Atom.newAtom("test"),Atom.newAtom("test")});
//		s.send(new Atom[]{Atom.newAtom("test test test")});
	}
	
	public void anything(String message, Atom atoms[])
	{
		post(message+" "+Atom.toOneString(atoms));
	}
	
	private static void initlog()
	{
		logfile = new File("/Users/"+System.getProperty("user.name")+"/Desktop/amis_log.txt");
		if(!logfile.exists())
			try
			{
				logfile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				error("Could not create log file.");
				return;
			}
	}
	
	private static void log(String s)
	{
		try
		{
			FileWriter fw = new FileWriter(logfile,true);
			fw.append(s);
			fw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
