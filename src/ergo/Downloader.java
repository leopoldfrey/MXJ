package ergo;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.Vector;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

public class Downloader implements Runnable {
	
	static Vector<String> downloading = new Vector<String>();
	static Vector<String> doneDL = new Vector<String>();
	static Vector<String> errorDL = new Vector<String>();
	
	String imgStr;
	String urlStr;
	String fileStr;
	Thread t;
	 	
	public static OSCPortOut sender = null;
	
	public static void initOSC() {
		try {
	        sender = new OSCPortOut(InetAddress.getLocalHost(), 54000);
	        System.out.println("ErgoController | Init OSC");
	    } catch (Exception e) {
	        System.out.println("ErgoController | Init OSC Failed");
	        e.printStackTrace();
	    }
	}
	
	/*Downloader(String img) {
		if(!downloading.contains(img) && !doneDL.contains(img))
		{
			imgStr = img;
			urlStr = ErgoController.server+imgStr; 
			fileStr = ErgoController.local+"/"+imgStr;
			downloading.add(imgStr);
			System.out.println("ErgoController | < Starting Download " +  imgStr );
			//System.out.println("->>>> fileStr : "+fileStr);
			t = new Thread (this, imgStr);
			t.start ();
		}
	}//*/

	Downloader(String server, String local, String img) {
		if(!downloading.contains(img) && !doneDL.contains(img))
		{
			imgStr = img;
			urlStr = server+imgStr; 
			fileStr = local+"/"+imgStr;
			downloading.add(imgStr);
			System.out.println("ErgoController | < Starting Download " +  imgStr );
			//System.out.println("->>>> fileStr : "+fileStr);
			t = new Thread (this, imgStr);
			t.start ();
		}
	}

	
	public void run() {
		 try {
			 URL url = new URL(urlStr);
			 BufferedInputStream bis = new BufferedInputStream(url.openStream());
			 FileOutputStream fis = new FileOutputStream(fileStr);
			 byte[] buffer = new byte[1024];
			 int count=0;
			 while((count = bis.read(buffer,0,1024)) != -1)
			 {
				 fis.write(buffer, 0, count);
			 }
			 fis.close();
			 bis.close();
			 System.out.println("ErgoController | > Done Downloading " +  imgStr );
			 Object args [] = new Object[1];
		     args[0] = imgStr;
		     OSCMessage msg = new OSCMessage("/ergo/newimage", args);
		     try {
		            sender.send(msg);
		        } catch (Exception e) {
		            System.out.println("ErgoController | Can not send !!!");
		            e.printStackTrace();
		        }
		     
			 downloading.remove(imgStr);
			 doneDL.addElement(imgStr);
		 } catch (Exception e) {
			 System.out.println("ErgoController | Error while Downloading " +  imgStr );
			 e.printStackTrace();
			 downloading.remove(imgStr);
			 errorDL.addElement(imgStr);
		 }
	}
}
