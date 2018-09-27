package ergo;

import java.net.URI;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import ergo.WebsocketClientEndpoint.MessageHandler;
import lf.LfObject;

public class ErgoController extends LfObject implements MessageHandler {
	WebsocketClientEndpoint clientEndPoint;
	boolean connected = false;
	int protocol = 0;
	String defmessage = "FOCUS ON THE CONFERENCE";
	public static String server = "http://ergonomics.lri.fr/uploads/";
	public static String socket = "ws://ergonomics.lri.fr/ws";
	public static String local = "/Users/leo/Documents/__PROJETS/_pulso/Ergonomics/Serveur/ErgoController/img/";

	public ErgoController(Atom[] atoms) {
		version = 0.1f;
		build = "12/09/17";
		INLET_ASSIST = new String[] { "Commands to server" };
		OUTLET_ASSIST = new String[] { "Info from server", "Connection Info" };
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.INT };
		init(0, false, atoms);
		post("ErgoController | 25 Sep 2018");
		Downloader.initOSC();
	}
	
	public void setServer(String serv)
	{
		server = serv;
		post("ErgoController | Server url : "+server);
	}
	
	public void setLocal(String loc)
	{
		local = loc;
		post("ErgoController | Local path : "+local);
	}
	
	public void setSocket(String soc)
	{
		socket = soc;
		post("ErgoController | Socket url : "+socket);
	}
	
	public void connect() {
		try {
			System.out.println("ErgoController | Trying to connect to "+socket);
			clientEndPoint = new WebsocketClientEndpoint(new URI(socket));
			clientEndPoint.addMessageHandler(this);
			connected = true;
			System.out.println("ErgoController | Connected to "+socket);
        } catch (Exception e) {
        		System.out.println("ErgoController | Connection to "+socket+" failed");
            	error("Exception: "+e.getMessage());
        		//e.printStackTrace();
			connected = false;
		}
		outlet(1, connected);
	}
	
	public void connectLocal() {
		try {
			String address = "ws://127.0.0.1:8082/";
			System.out.println("Connecting to "+address);
			clientEndPoint = new WebsocketClientEndpoint(new URI(address));
			clientEndPoint.addMessageHandler(this);
			connected = true;
        } catch (Exception e) {
        	error("Exception: "+e.getMessage());
        	e.printStackTrace();
			connected = false;
		}
		outlet(1, connected);
	}
	
	public void test()
	{
		String json = "{\"type\":\"newimage\",\"stage\":\"5\",\"standbyMsg\":\"/opt/app-root/src/uploads/9h31m37s259_5_image.jpg\"}";
		handleMessage(json);
	}
	
	 public void handleMessage(String message)
	 {	 	 
		 if(message.contains("newimage"))
		 {
			 String m = message.replaceAll("\"", "").replaceAll(":", "").replaceAll(",", "");
			 m = m.substring(m.indexOf("uploads")+8,m.length()-1);
			 //post("NEW IMAGE : "+m);
			 new Downloader(m);
			 outlet(0, "newimage "+m);
		 } else 
			 outlet(0, message);
     }
	 
	 public void protocol(int i) {
		 if(!connected)
			 connect();
		 protocol = clip(i,0,5);
		 if(!connected)
		 {
			 error("Websocket Not Connected");
			 return;
		 }
		 if(protocol == 0)
			 pause(defmessage);
		 else {
			 try {
		          clientEndPoint.sendMessage("{\"type\":\"broadcast\",\"stage\":\""+i+"\"}");//,\"standbyMsg\":\"oij oij oij\"}");
			 } catch (Exception e) {
				 error("Exception: " + e.getMessage());
				 e.printStackTrace();
			 }			 
		 }
	 }
	 
	 public void pause(String message) {
		 if(!connected)
			 connect();
		 if(!connected)
		 {
			 error("ErgoController | Websocket Not Connected");
			 return;
		 }
		 if(message.equalsIgnoreCase(""))
			 pause(defmessage);
		 else
			 try {
		          clientEndPoint.sendMessage("{\"type\":\"broadcast\",\"stage\":\"0\",\"standbyMsg\":\""+message+"\"}");
			 } catch (Exception e) {
				 error("Exception: " + e.getMessage());
				 //e.printStackTrace();
			 }			  
	 }
	 
	 public void deleteData()
	 {
		 if(!connected)
			 connect();
		 if(!connected)
		 {
			 error("ErgoController | Websocket Not Connected");
			 return;
		 }
		 try {
	          clientEndPoint.sendMessage("{\"type\":\"deleteAllServerData\"}");
		 } catch (Exception e) {
			 error("Exception: " + e.getMessage());
			 e.printStackTrace();
		 }
	 }
}

