package ergo;

import java.net.URI;

import org.json.JSONObject;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import ergo.WebsocketClientEndpoint.MessageHandler;
import lf.LfObject;

public class Biosync extends LfObject implements MessageHandler {
	WebsocketClientEndpoint clientEndPoint;
	boolean connected = false;
	public static String socket = "wss://biosync.herokuapp.com/wss";

	public Biosync(Atom[] atoms) {
		version = 0.1f;
		build = "12/10/18";
		INLET_ASSIST = new String[] { "Commands to server" };
		OUTLET_ASSIST = new String[] { "Info from server", "Connection Info" };
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.INT };
		init(0, false, atoms);
		post("Biosync Controller | 12 oct 2018");
	}
	
	public void setSocket(String soc)
	{
		socket = soc;
		post("Biosync Controller | Socket url : "+socket);
	}
	
	public void connect() {
		try {
			System.out.println("Biosync Controller | Trying to connect to "+socket);
			clientEndPoint = new WebsocketClientEndpoint(new URI(socket));
			clientEndPoint.addMessageHandler(this);
			connected = true;
			System.out.println("Biosync Controller | Connected to "+socket);
        } catch (Exception e) {
        		System.out.println("Biosync Controller | Connection to "+socket+" failed");
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
	
	 public void handleMessage(String message)
	 {	 	 
		 /*ObjectMapper mapper = new ObjectMapper();
		 JsonNode rootNode = mapper.readTree(message);//*/
		 JSONObject jsonO = new JSONObject(message);
		 
		 //String jsoOStr = jsonO.toString();
		 //post(jsoOStr);
		 if(jsonO.getString("type").equalsIgnoreCase("newimage"))
		 {
			 post("(ignored)newimage");
		 } else if(jsonO.getString("type").equalsIgnoreCase("name"))
		 {
			 post("name="+jsonO.getString("stage"));
			 outlet(0, "name "+jsonO.getString("stage"));
		 } else if(jsonO.getString("type").equalsIgnoreCase("match"))
		 {
			 post("match="+jsonO.getString("stage"));
			 outlet(0, "matchready "+jsonO.getString("stage"));
		 } else if(jsonO.getString("type").equalsIgnoreCase("changeState"))
		 {	 
			 post("(ignored)changeState");
		 } else
			 outlet(0, message);
     }
	 
	 public void match(String n1, String n2) {
		 //post("MATCH "+n1+" "+n2);
		 if(!connected)
			 connect();
		 if(!connected)
		 {
			 error("Websocket Not Connected");
			 return;
		 }
	 	 try {
	          clientEndPoint.sendMessage("{\"type\":\"domatch\",\"u1\":\""+n1+"\",\"u2\":\""+n2+"\"}");
		 } catch (Exception e) {
			 error("Exception: " + e.getMessage());
			 e.printStackTrace();
		 }	
	 }
	 
	 public void phase(int i) {
		 if(!connected)
			 connect();
		 if(!connected)
		 {
			 error("Websocket Not Connected");
			 return;
		 }
	 	 try {
	          clientEndPoint.sendMessage("{\"type\":\"broadcast\",\"stage\":\""+i+"\"}");
		 } catch (Exception e) {
			 error("Exception: " + e.getMessage());
			 e.printStackTrace();
		 }	
	 }
}

