package ergo;

import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 * ChatServer Client
 *
 * @author Jiji_Sasidharan
 */
@ClientEndpoint
public class WebsocketClientEndpoint {

    Session userSession = null;
    private MessageHandler messageHandler;

    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("ErgoController | Websocket opened");
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
    	try {
    		if (this.messageHandler != null) {
                this.messageHandler.handleMessage(message);
            }	
    	} catch (Error e) {
    		e.printStackTrace();
    	}
    }

    /**
     * register message handler
     *
     * @param msgHandler
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    /**
     * Message handler.
     *
     * @author Jiji_Sasidharan
     */
    public static interface MessageHandler {

        public void handleMessage(String message);
    }
    
    public static void main(String args[])
    {
	    	WebsocketClientEndpoint clientEndPoint;
	    	try {
	    		//String address = "ws://ergoconf2-ergoconf.193b.starter-ca-central-1.openshiftapps.com/";
	    		String address = "ws://ergonomics.lri.fr";
	    		System.out.println("Connecting to "+address);
	    		clientEndPoint = new WebsocketClientEndpoint(new URI(address));
	    		clientEndPoint.addMessageHandler(new MessageHandler() {
	    			public void handleMessage(String message) {
	    			 	 
	    				 if(message.contains("newimage"))
	    				 {
	    					 String m = message.replaceAll("\"", "").replaceAll(":", "").replaceAll(",", "");
	    					 m = m.substring(m.indexOf("uploads")+8,m.length()-1);
	    					 System.out.println( "newimage "+m);
	    				 } else 
	    					 System.out.println(message);
	    		     }
	    		});
	        } catch (Exception e) {
	        		System.err.println("Exception: "+e.getMessage());
	        		e.printStackTrace();
	    	}	
    }
}