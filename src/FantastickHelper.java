/**
 * Leopold Frey / leopold.frey@free.fr
 * This work is licensed under a Creative Commons Attribution-Noncommercial 3.0 Unported License.
 * http://creativecommons.org/licenses/by-nc/3.0/
**/

import java.awt.Point;
import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;

public class FantastickHelper extends MaxObject
{
	private static final int OUTLET_FINGERS = 0;
	private static final int OUTLET_FINGERS_NUM = 1;
	private static final int OUTLET_ROUTER = 2;
	private Vector<Point> fingers = new Vector<Point>();
	
	private static float version = 0.2f;
	private static String build = "17/02/09";
	protected final static String ppp = " ...  ";
	private final static String libAuthor = "leopold.frey@free.fr";
	private static String[] INLET_ASSIST = new String[] { "Fantastick Input" };
	private static String[] OUTLET_ASSIST = new String[] { "Fingers Activity", "Number of fingers", "Routed Messages" };
	private static int[] INLET_TYPES = new int[] { DataTypes.ALL };
	private static int[] OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.INT, DataTypes.ALL };
	
	public FantastickHelper(Atom[] args)
	{
		declareInlets(INLET_TYPES);
		declareOutlets(OUTLET_TYPES);
		setInletAssist(INLET_ASSIST);
		setOutletAssist(OUTLET_ASSIST);
		createInfoOutlet(false);
	}
	
	public void version()
	{
		System.out.println(" ...     " + this.getClass().getName().toString() + " - v" + version + " - " + libAuthor + " - build : " + build);
	}

	public void clear()
	{
		fingers = new Vector<Point>();
		outlet(OUTLET_FINGERS_NUM,fingers.size());
	}
	
	public void B(Atom[] args)
	{
//		fingers.add(atom2point(args));
		int alloc = allocate(atom2point(args));
		outlet(OUTLET_FINGERS_NUM,size());
		outlet(OUTLET_FINGERS, "B"+alloc, args);
//		outlet(OUTLET_FINGERS, concat(Atom.newAtom(fingers.size()), args));
	}
	
	public void E(Atom[] args)
	{
		int nearest = nearest(atom2point(args));
		if(nearest != -1)
		{
//			fingers.remove(nearest);
			free(nearest);
		}
		outlet(OUTLET_FINGERS_NUM,size());
		outlet(OUTLET_FINGERS, "E"+(nearest+1), args);
//		outlet(OUTLET_FINGERS, concat(Atom.newAtom(nearest+1), args));
	}
	
	public void M(Atom[] args)
	{
		Point newp = atom2point(args);
		int nearest = nearest(newp);
		if(nearest != -1)
		{
			Point oldp = fingers.get(nearest);
			fingers.set(nearest,newp);
			outlet(OUTLET_FINGERS_NUM,size());
			outlet(OUTLET_FINGERS, new Atom[]{Atom.newAtom("d"+(nearest+1)), Atom.newAtom(newp.x - oldp.x), Atom.newAtom(newp.y - oldp.y)});
			outlet(OUTLET_FINGERS, "M"+(nearest+1), args);
		}
	}
	
	public void anything(String msg, Atom[] args)
	{
		outlet(OUTLET_ROUTER, msg, args);
	}
	
	private int nearest(Point p)
	{
		double dist = 10000;
		double curdist = 0;
		int nearest = -1;
		for(int i = 0 ; i < fingers.size() ; i++)
		{
			curdist = p.distance(fingers.get(i));
			if(curdist < dist)
			{
				dist = curdist;
				nearest = i;
			}
		}
		return nearest;
	}
	
	private void free(int index)
	{
		fingers.set(index, new Point(-1000,-1000));
//		System.out.println("Freeing : "+index+" "+fingers.size());
	}
	
	private int allocate(Point p)
	{
		for(int i = 0 ; i < fingers.size() ; i++)
		{
			Point pp = fingers.get(i);
			if(pp.x == -1000 && pp.y == -1000)
			{
				pp.setLocation(p);
				return i+1;
			}
		}
		fingers.add(p);
//		System.out.println("Allocate : "+p+" "+fingers.size());
		return fingers.size();
	}
	
	private int size()
	{
		int c = 0;
		for(Point p : fingers)
			if(p.x != -1000 && p.y != -1000)
				c++;
		return c;
	}
	
	private static Point atom2point(Atom[] args)
	{
		return new Point(args[0].toInt(), args[1].toInt());
	}
}
