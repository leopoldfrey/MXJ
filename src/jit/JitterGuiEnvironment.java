package jit;

// Created on 31-Aug-2005
import java.util.HashMap;


/**
 * @author bbn
 *
 * The gui utilities exist within this Environment.  
 * A private constructor ensures that only one copy of the environment will ever be created.
 */
public class JitterGuiEnvironment {

	HashMap<String, JitterGuiTracker> trackers = new HashMap<String, JitterGuiTracker>();
	private static JitterGuiEnvironment theonlyone = null;
	
	//this class does not have a public constructor.
	private JitterGuiEnvironment() {}
	
	public static JitterGuiEnvironment getEnvironment()
	{
		if (theonlyone == null)
			theonlyone = new JitterGuiEnvironment();
		return theonlyone;
	}
	
	public void addTracker(String destination)
	{
		if (!trackers.containsKey(destination))
		{
			trackers.put(destination, new JitterGuiTracker(destination));
		}
	}

	public void init(String destination) 
	{
		trackers.get(destination).makeListener();
	}
	
	public void addClient(String destination, JitterGuiElement newclient)
	{
		trackers.get(destination).addClient(newclient);
	}
	
	public void removeClient(String destination, JitterGuiElement client)
	{
		trackers.get(destination).removeClient(client);
	}
	
	public void deleteBogusClients(String destination)
	{
		trackers.get(destination).deleteBogusClients();
	}
}
