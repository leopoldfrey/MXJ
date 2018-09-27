package jit;

// Created on 31-Aug-2005
import java.util.ArrayList;
import java.util.HashMap;

import com.cycling74.jitter.Jitter3DUtils;
import com.cycling74.jitter.JitterEvent;
import com.cycling74.jitter.JitterObject;
import com.cycling74.max.Atom;
import com.cycling74.max.MaxSystem;

/**
 * @author bbn
 *
 * gui objects should subclass this Element object, and then Max objects
 * are made as wrappers around these Elements.  The Element interacts with the 
 * tracker for the context.  The subclass should override the handleEvent method
 * to do the appropriate thing with mouse clicks, and the update method to 
 * draw the appropriate shapes.
 */
public class JitterGuiElement {
	
	//public MaxObject maxobj = null;
	public int layer = 0;
	public boolean visible = false;
	public boolean deleteMe = false;
	public boolean unblockable = false;
	boolean frontmost = false;
	boolean wasFrontmost = false;
	public boolean intersect = false;
	public boolean stilldown = false;
	public boolean highlight = false;
	public String destination = null;
	public JitterObject jo = null;
	public float localint[] = new float[3];
	public float worldint[] = new float[3];
	public int uniqueIndex = 0;
	HashMap attributes = new HashMap();
	
	//cache a reference to the one and only environment
	private static JitterGuiEnvironment gui = JitterGuiEnvironment.getEnvironment();

	public JitterGuiElement()
	{
	}
	
	public void free()
	{
		deleteMe = true;
		gui.deleteBogusClients(destination);
		if (jo!=null)
			jo.freePeer();
	}
	
	public void init(String d)
	{
		jo = new JitterObject("jit.gl.sketch");
		destination = d;
		jo.setAttr("drawto", destination);
		jo.setAttr("blend_enable", 1);
		jo.setAttr("glclearcolor", new float[]{0.f,0.f,0.f,0.f});
		
		//if our max object is created before the 
		//tracker's max object we need to make the
		//tracker ourselves
		gui.addTracker(destination);
		gui.init(destination);
		
		//add self to tracker clients for destination
		gui.addClient(destination, this);
		
		update();
	}	
	
	public void drawto(String d)
	{
		gui.removeClient(destination, this);
		destination = d;
		jo.setAttr("drawto", destination);
		
		//if our max object is created before the 
		//tracker's max object we need to make the
		//tracker ourselves
		gui.addTracker(destination);
		gui.init(destination);
		
		//add self to tracker clients for destination
		gui.addClient(destination, this);
		
		update();
	}
	
	//adding the uniqueIndex property allows one mxj instance
	//to own multiple elements
	
	public void setUniqueIndex(int k)
	{
		uniqueIndex = k;
	}
		
	public void update()
	{
		MaxSystem.post("override update method of gui.element to draw");
	}
	
	public boolean getIntersect(float raystart[], float rayend[], float p1[])
	{
		intersect = Jitter3DUtils.intersectLineQuad(raystart, 
													rayend, 
													jo.getAttrFloatArray("position"),
													jo.getAttrFloatArray("rotate"),
													jo.getAttrFloatArray("scale"),
													worldint,
													localint);
		Jitter3DUtils.vcopy(worldint, p1);
		return intersect;
	}	
	
	/**
	 * handle a UI event
	 * @param e the JitterEvent
	 */
	public void handleEvent(JitterEvent e)
	{
		MaxSystem.post("override handleEvent method of gui.element to handle events");
	}
	


	//	 parse jitter-style arguments for jitterobject
	public void setAttrArgs(Atom args[])
	{
		for(int i=0; i<args.length; i++)
		{	
			Atom a = args[i];
			if (a.isString())
			{
				String s = a.toString();
				if (s.charAt(0) == "@".charAt(0))
				{
					String attrname = s.substring(1, s.length()-1); 
					i++;
					ArrayList<Atom> attrvals = new ArrayList<Atom>();
					for (boolean done=false; !done;)
					{
						a = args[i];
						if (a.isString()) 
						{
							if (s.charAt(0) == "@".charAt(0)) 
							{
								done = true;
								i--;
							}
						}
						if (!done)
						{
							attrvals.add(a);
							i++;
						}
						if (i > args.length-1) done = true;
					}
					Atom[] attrvalarray = (Atom[])attrvals.toArray();
					jo.setAttr(attrname, attrvalarray);
				}
			}
		}
	}	
}
