package jit;


// Created on 3-Sep-2005
import com.cycling74.jitter.JitterEvent;
import com.cycling74.max.Atom;
import com.cycling74.max.Callback;

/**
 * @author bbn
 *
 * gui slider
 */
public class JitterGuiPointElement extends JitterGuiElement {
	boolean locked = false;
	boolean texcoord = false;
	boolean zenable = false;
	boolean enable = false;
	boolean prevdown = false;
	boolean mousedown = false;
	boolean first = false;
	float color[] = new float[] {1.0f,0.f,0.f,1.0f};
	float color2[] = new float[] {0f,0f,1.0f,1.0f};
	float position[] = new float[] {0.0f,0.0f,0.0f};
	float rotate[] = new float[] {0.0f,0.0f,0.0f};
	float scale[] = new float[] {0.05f,0.05f,1f};
	float posz = 0, oposz = 0, px = 0, opx = 0, dx = 0, py = 0, opy = 0, dy = 0;
	Callback c=null;
	Callback c2=null;

	public JitterGuiPointElement(Callback c, Callback c2)
	{
		this.c = c;
		this.c2 = c2;
	}
	
	public void update()
	{
		//JitterObject jo, an instance of jit.gl.sketch, is created in element class
		jo.send("reset");
		if(!enable)
			return;
		if (highlight)
			jo.send("glcolor", new float[] {1.f,1.f,1.f,1.f});
		jo.send("framequad", new float[] {0.f,0.f,0.f,0.f,1.f,0.f,1.f,1.f,0.f,1.f,0.f,0.f});
		jo.send("glcolor", texcoord ? color2 : color);		
		jo.send("quad", new float[] {0.f,0.f,0.f,0.f,1.f,0.f,1.f,1.f,0.f,1.f,0.f,0.f});
	}
	
	public void handleEvent(JitterEvent event)
	{
		if(!enable)
			return;
		boolean oldhighlight = highlight;
		Atom args[] = event.getArgs();
		
		String eventname = event.getEventName();
		if (eventname.equals("mouse"))
		{
			if(locked)
				return;
			//event arguments are (x,y,button,cmd,shift,capslock,option,ctrl)
			boolean button = args[2].toBoolean();
			boolean cmd = args[3].toBoolean();
			boolean shift = args[4].toBoolean();
			boolean option = args[6].toBoolean();
			prevdown = mousedown;
			mousedown = button;
			
			//set stilldown
			if (intersect)
				stilldown = mousedown && prevdown;
			else
				stilldown = stilldown && mousedown;
			
			if ((intersect && mousedown) || stilldown)
			{
				if(texcoord)
				{
					if (first)
					{
						px = args[0].toFloat();
						py = args[1].toFloat();
						first = false;
					}
					opx = px;
					opy = py;
					px = args[0].toFloat();
					py = args[1].toFloat();
					dx = (px - opx) / (shift ? 1000.f : 100.f);
					dy = (py - opy) / (shift ? 1000.f : 100.f);
					c2.execute();
				}
				else
				{
					if (zenable && option)
					{
						if (first)
						{
							posz = args[1].toFloat();
							first = false;
						}
						oposz = posz;
						posz = args[1].toFloat();
						position[2] = position[2] + (posz - oposz) / 100.f;
					}
					else
					{
						position[0] = worldint[0] - scale[0] / 2;
						position[1] = worldint[1] - scale[1] / 2;
					}
					jo.setAttr("position", position);
					c.execute();
				}
			}
		}
		else if (eventname.equals("mouseidle"))
		{
			first = true;
			highlight = true;
		}
		else if (eventname.equals("mouseidleout"))
		{
			first = true;
			highlight = false;
		}
		
		update();
	}
	
	public void setColor(Atom ac[])
	{
		if (ac.length == 4)
		{
			color = Atom.toFloat(ac);
			update();
		}
	}
	
	public void setColor2(Atom ac[])
	{
		if (ac.length == 4)
		{
			color2 = Atom.toFloat(ac);
			update();
		}
	}
	
	public void setPosition(Atom ap[])
	{
		if (ap.length == 3)
		{
			position = Atom.toFloat(ap);
			jo.setAttr("position", position);
		}
	}
	
	public void setPosition(float x, float y, float z)
	{
		position = new float[3];
		position[0] = x;
		position[1] = y;
		position[2] = z;
		jo.setAttr("position", position);
	}
	
	public void setRotate(Atom ar[])
	{
		if (ar.length == 3)
		{
			rotate = Atom.toFloat(ar);
			jo.setAttr("rotate", rotate);
		}
	}
	
	public void setScale(Atom as[]) 
	{
		if (as.length == 3)
		{
			scale = Atom.toFloat(as);
			jo.setAttr("scale", scale);
		}
	}
	
	public void enable(boolean b)
	{
		enable = b;
		visible = b;
		jo.setAttr("enable", b ? 1 : 0);
	}
	
	public void anything(String message, Atom[] args)
	{
		jo.send(message, args);
	}
	
	public void layer(int i)
	{
		layer = i;
		jo.send("layer",i);
	}
}
