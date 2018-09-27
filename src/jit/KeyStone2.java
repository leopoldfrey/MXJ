package jit;

import java.util.Vector;

import com.cycling74.jitter.JitterEvent;
import com.cycling74.jitter.JitterListener;
import com.cycling74.jitter.JitterNotifiable;
import com.cycling74.jitter.JitterObject;
import com.cycling74.max.Atom;
import com.cycling74.max.Callback;
import com.cycling74.max.MaxObject;

public class KeyStone2 extends MaxObject implements JitterNotifiable
{
	private float[] worldcoords = new float[] { 0.f, 0.f, 0.f };
	private float[] oldworldcoords = new float[] { 0.f, 0.f, 0.f };
	private Vector<float[]> plane = new Vector<float[]>();
	private float color[] = new float[] { 1.0f, 0.f, 0.f, 1.0f };
	private float color2[] = new float[] { 0.f, 0.f, 1.0f, 1.0f };
	private JitterObject sketch = null;
	private JitterListener listener = null;
	private JitterGuiPointElement p1 = null;
	private JitterGuiPointElement p2 = null;
	private JitterGuiPointElement p3 = null;
	private JitterGuiPointElement p4 = null;
	private boolean enable = false;
	private boolean texctrl = false;
	private float halfpointscale = 0.025f;
	private float linewidth = 3;
	private boolean selected = false;
	private boolean changed = false;

	public KeyStone2(Atom args[])
	{
		if (args.length == 0)
			bail("keystone : need a context argument");
		declareIO(1, 2);
		String context = args[0].toString();
		sketch = new JitterObject("jit.gl.sketch", context);
		sketch.setAttr("blend_enable", 1);
		sketch.setAttr("glclearcolor", new float[] { 0.f, 0.f, 0.f, 0.f });
		listener = new JitterListener(context, this);
		plane.add(new float[] { 0.f, 0.f, 0.f, 0.f, 1.f });
		plane.add(new float[] { 0.f, 0.f, 0.f, 1.f, 1.f });
		plane.add(new float[] { 0.f, 0.f, 0.f, 1.f, 0.f });
		plane.add(new float[] { 0.f, 0.f, 0.f, 0.f, 0.f });
		p1 = new JitterGuiPointElement(new Callback(this, "out1"), new Callback(this, "outt1"));
		p2 = new JitterGuiPointElement(new Callback(this, "out2"), new Callback(this, "outt2"));
		p3 = new JitterGuiPointElement(new Callback(this, "out3"), new Callback(this, "outt3"));
		p4 = new JitterGuiPointElement(new Callback(this, "out4"), new Callback(this, "outt4"));
		p1.init(context);
		p2.init(context);
		p3.init(context);
		p4.init(context);
		setPointScale(2 * halfpointscale);
		setPosition(Atom.parse("1 -0.5 0.5 0"));
		setPosition(Atom.parse("2 0.5 0.5 0"));
		setPosition(Atom.parse("3 0.5 -0.5 0"));
		setPosition(Atom.parse("4 -0.5 -0.5 0"));
		declareAttribute("color", null, "setColor");
		declareAttribute("color2", null, "setColor2");
		declareAttribute("position", null, "setPosition");
		declareAttribute("pointscale", null, "setPointScale");
		declareAttribute("enable", null, "setEnabled");
		declareAttribute("layer", null, "setLayer");
		declareAttribute("linewidth", null, "setLinewidth");
		declareAttribute("zenable", null, "setZenable");
		declareAttribute("texctrl", null, "setTexCtrl");
		setTexCtrl(false);
		setEnabled(false);
		update();
		
	//	post("Keystone build LF 08/03/11");
	}

	public void name(String name)
	{
		sketch.send("name", name);
		p1.anything("name", Atom.newAtom(new String[]{name+"_p1"}));
		p2.anything("name", Atom.newAtom(new String[]{name+"_p2"}));
		p3.anything("name", Atom.newAtom(new String[]{name+"_p3"}));
		p4.anything("name", Atom.newAtom(new String[]{name+"_p4"}));
	}

	public void outt1()
	{
		plane.get(0)[3] -= p1.dx;
		plane.get(0)[4] += p1.dy;
		String ps = p2str(plane.get(0));
		outlet(1, Atom.parse("setcell 0 1 val " + ps));
		outlet(0, Atom.parse("position 1 " + ps));
	}

	public void outt2()
	{
		plane.get(1)[3] -= p2.dx;
		plane.get(1)[4] += p2.dy;
		String ps = p2str(plane.get(1));
		outlet(1, Atom.parse("setcell 1 1 val " + ps));
		outlet(0, Atom.parse("position 2 " + ps));
	}

	public void outt3()
	{
		plane.get(2)[3] -= p3.dx;
		plane.get(2)[4] += p3.dy;
		String ps = p2str(plane.get(2));
		outlet(1, Atom.parse("setcell 1 0 val " + ps));
		outlet(0, Atom.parse("position 3 " + ps));
	}

	public void outt4()
	{
		plane.get(3)[3] -= p4.dx;
		plane.get(3)[4] += p4.dy;
		String ps = p2str(plane.get(3));
		outlet(1, Atom.parse("setcell 0 0 val " + ps));
		outlet(0, Atom.parse("position 4 " + ps));
	}

	public void out1()
	{
		plane.get(0)[0] = p1.position[0] + halfpointscale;
		plane.get(0)[1] = p1.position[1] + halfpointscale;
		plane.get(0)[2] = p1.position[2];
		planedraw();
		String ps = p2str(plane.get(0));
		outlet(1, Atom.parse("setcell 0 1 val " + ps));
		outlet(0, Atom.parse("position 1 " + ps));
	}

	public void out2()
	{
		plane.get(1)[0] = p2.position[0] + halfpointscale;
		plane.get(1)[1] = p2.position[1] + halfpointscale;
		plane.get(1)[2] = p2.position[2];
		planedraw();
		String ps = p2str(plane.get(1));
		outlet(1, Atom.parse("setcell 1 1 val " + ps));
		outlet(0, Atom.parse("position 2 " + ps));
	}

	public void out3()
	{
		plane.get(2)[0] = p3.position[0] + halfpointscale;
		plane.get(2)[1] = p3.position[1] + halfpointscale;
		plane.get(2)[2] = p3.position[2];
		planedraw();
		String ps = p2str(plane.get(2));
		outlet(1, Atom.parse("setcell 1 0 val " + ps));
		outlet(0, Atom.parse("position 3 " + ps));
	}

	public void out4()
	{
		plane.get(3)[0] = p4.position[0] + halfpointscale;
		plane.get(3)[1] = p4.position[1] + halfpointscale;
		plane.get(3)[2] = p4.position[2];
		planedraw();
		String ps = p2str(plane.get(3));
		outlet(1, Atom.parse("setcell 0 0 val " + ps));
		outlet(0, Atom.parse("position 4 " + ps));
	}

	public void notifyDeleted()
	{
		sketch.send("reset");
		if (sketch != null)
			sketch.freePeer();
		listener.freePeer();
		p1.free();
		p2.free();
		p3.free();
		p4.free();
	}

	public void bang()
	{
		String ps1 = p2str(plane.get(0));
		String ps2 = p2str(plane.get(1));
		String ps3 = p2str(plane.get(2));
		String ps4 = p2str(plane.get(3));
		outlet(1, Atom.parse("setcell 0 1 val " + ps1));
		outlet(1, Atom.parse("setcell 1 1 val " + ps2));
		outlet(1, Atom.parse("setcell 1 0 val " + ps3));
		outlet(1, Atom.parse("setcell 0 0 val " + ps4));
		outlet(0, Atom.parse("position 1 " + ps1));
		outlet(0, Atom.parse("position 2 " + ps2));
		outlet(0, Atom.parse("position 3 " + ps3));
		outlet(0, Atom.parse("position 4 " + ps4));
	}

	public static String p2str(float[] p)
	{
		return p[0] + " " + p[1] + " " + p[2] + " " + p[3] + " " + p[4];
	}

	public void texcoords(Atom[] args)
	{
		switch (args.length)
		{
		case 3:
			int p = args[0].toInt();
			if (p >= 1 && p <= plane.size())
			{
				plane.get(p - 1)[3] = args[1].toFloat();
				plane.get(p - 1)[4] = args[2].toFloat();
				bang();
			}
			break;
		}
	}

	private void update()
	{
		planedraw();
		p1.update();
		p2.update();
		p3.update();
		p4.update();
	}

	private void planeupdate()
	{
		plane.get(0)[0] = p1.position[0] + halfpointscale;
		plane.get(0)[1] = p1.position[1] + halfpointscale;
		plane.get(0)[2] = p1.position[2];
		plane.get(1)[0] = p2.position[0] + halfpointscale;
		plane.get(1)[1] = p2.position[1] + halfpointscale;
		plane.get(1)[2] = p2.position[2];
		plane.get(2)[0] = p3.position[0] + halfpointscale;
		plane.get(2)[1] = p3.position[1] + halfpointscale;
		plane.get(2)[2] = p3.position[2];
		plane.get(3)[0] = p4.position[0] + halfpointscale;
		plane.get(3)[1] = p4.position[1] + halfpointscale;
		plane.get(3)[2] = p4.position[2];
	}

	private void planedraw()
	{
		// JitterObject jo, an instance of jit.gl.sketch, is created in element class
		sketch.send("reset");
		if (!enable)
			return;
		sketch.send("gllinewidth", selected ? linewidth : 1);
		sketch.send("glcolor", texctrl ? color2 : color);
		sketch.send("glbegin", "line_loop");
		sketch.send("glvertex", plane.get(0));
		sketch.send("glvertex", plane.get(1));
		sketch.send("glvertex", plane.get(2));
		sketch.send("glvertex", plane.get(3));
		sketch.send("glend");// */
	}

	public void setColor(Atom a[])
	{
		p1.setColor(a);
		p2.setColor(a);
		p3.setColor(a);
		p4.setColor(a);
		color = Atom.toFloat(a);
		update();
	}

	public void setColor2(Atom a[])
	{
		p1.setColor2(a);
		p2.setColor2(a);
		p3.setColor2(a);
		p4.setColor2(a);
		color2 = Atom.toFloat(a);
	}

	public void setPosition(Atom a[])
	{
		switch (a[0].toInt())
		{
		case 1:
			p1.setPosition(a[1].toFloat() - halfpointscale, a[2].toFloat() - halfpointscale, a[3].toFloat());
			out1();
			break;
		case 2:
			p2.setPosition(a[1].toFloat() - halfpointscale, a[2].toFloat() - halfpointscale, a[3].toFloat());
			out2();
			break;
		case 3:
			p3.setPosition(a[1].toFloat() - halfpointscale, a[2].toFloat() - halfpointscale, a[3].toFloat());
			out3();
			break;
		case 4:
			p4.setPosition(a[1].toFloat() - halfpointscale, a[2].toFloat() - halfpointscale, a[3].toFloat());
			out4();
			break;
		}
		update();
	}

	public void setPointScale(float f)
	{
		float old = halfpointscale;
		halfpointscale = f / 2.f;
		float delta = halfpointscale - old;
		Atom[] a = new Atom[] { Atom.newAtom(f), Atom.newAtom(f), Atom.newAtom(1.f) };
		p1.setScale(a);
		p1.setPosition(p1.position[0] - delta, p1.position[1] - delta, p1.position[2]);
		p2.setScale(a);
		p2.setPosition(p2.position[0] - delta, p2.position[1] - delta, p2.position[2]);
		p3.setScale(a);
		p3.setPosition(p3.position[0] - delta, p3.position[1] - delta, p3.position[2]);
		p4.setScale(a);
		p4.setPosition(p4.position[0] - delta, p4.position[1] - delta, p4.position[2]);
		update();
	}

	public void notify(JitterEvent event)
	{
		if (enable)
		{
			String eventname = event.getEventName();
			int eventargs[] = event.getArgsIntArray();
			//event arguments are (x,y,button,cmd,shift,capslock,option,ctrl)
			if (eventargs.length >= 2)
			{
				boolean button = eventargs[2] == 1;
				boolean option = eventargs[6] == 1;
				boolean cmd = eventargs[3] == 1;
				float mouse[] = new float[] { eventargs[0], eventargs[1] };
				oldworldcoords = worldcoords;
				worldcoords = Atom.toFloat(sketch.send("screentoworld", mouse));
				worldcoords[0] -= halfpointscale;
				worldcoords[1] -= halfpointscale;
				if (eventname.equalsIgnoreCase("mouseidle"))
				{
					selected = inside(worldcoords[0], worldcoords[1]);
					changed = false;
					planedraw();
				}
				else if (eventname.equalsIgnoreCase("mouseidleout"))
				{
					selected = false;
					changed = false;
					planedraw();
				}
				else if (eventname.equalsIgnoreCase("mouse"))
				{
					if (selected && button)
					{
						if (cmd && texctrl)
						{
							resetTexcoords();
							update();
							bang();
						} else if (option && !changed)
						{	
							setTexCtrl(!texctrl);
							update();
							changed = true;
						} else if (cmd && !texctrl)
						{
							float dx = oldworldcoords[0] - worldcoords[0];
							float dy = oldworldcoords[1] - worldcoords[1];
							p1.setPosition(p1.position[0] - dx, p1.position[1] - dy, p1.position[2]);
							p2.setPosition(p2.position[0] - dx, p2.position[1] - dy, p2.position[2]);
							p3.setPosition(p3.position[0] - dx, p3.position[1] - dy, p3.position[2]);
							p4.setPosition(p4.position[0] - dx, p4.position[1] - dy, p4.position[2]);
							planeupdate();
							update();
							bang();
						}
					}
				}
			}
		}
	}

	public int ccw(float x0, float y0, float x1, float y1, float x2, float y2)
	{
		float dx1, dx2, dy1, dy2;
		dx1 = x1 - x0;
		dx2 = x2 - x1;
		dy1 = y1 - y0;
		dy2 = y2 - y1;
		if (dx1 * dy2 > dy1 * dx2)
			return 1;
		if (dx1 * dy2 < dy1 * dx2)
			return -1;
		if (dx1 * dx2 < 0 || dy1 * dy2 < 0)
			return -1;
		if (dx1 * dx1 + dy1 * dy1 >= dx2 * dx2 + dy2 * dy2)
			return 0;
		return 1;
	}

	public boolean intersect(float x11, float y11, float x12, float y12, float x21, float y21, float x22, float y22)
	{
		return (ccw(x11, y11, x12, y12, x21, y21) * ccw(x11, y11, x12, y12, x22, y22) <= 0 && ccw(x21, y21, x22, y22, x11, y11) * ccw(x21, y21, x22, y22, x12, y12) <= 0);
	}

	public boolean inside(float x, float y)
	{
		int count = 0;
		int j = 0;
		float[][] p = new float[5][];
		p[0] = p4.position;
		p[1] = p1.position;
		p[2] = p2.position;
		p[3] = p3.position;
		p[4] = p4.position;
		float lx1 = x;
		float ly1 = y;
		float lx2 = Float.MAX_VALUE;
		float ly2 = y;
		float lpx1, lpy1, lpx2, lpy2;
		for (int i = 0; i < p.length - 1; i++)
		{
			lpx1 = p[i][0];
			lpy1 = p[i][1];
			lpx2 = p[i + 1][0];
			lpy2 = p[i + 1][1];
			if (intersect(lx1, ly1, lx2, ly2, lpx1, lpy1, lpx2, lpy2) || intersect(lpx1, lpy1, lpx2, lpy2, lx1, ly1, lx2, ly2))
				count++;
		}
		return (count % 2) > 0;
	}

	public void anything(String message, Atom[] args)
	{
		sketch.send(message, args);
		p1.anything(message, args);
		p2.anything(message, args);
		p3.anything(message, args);
		p4.anything(message, args);
	}

	public void setLayer(int i)
	{
		sketch.send("layer", i);
		p1.layer(i);
		p2.layer(i);
		p3.layer(i);
		p4.layer(i);
	}

	public void setLinewidth(float f)
	{
		linewidth = f;
		planedraw();
	}

	public void drawto(String context)
	{
		sketch.setAttr("drawto", context);
		listener.setSubjectName(context);
		p1.drawto(context);
		p2.drawto(context);
		p3.drawto(context);
		p4.drawto(context);
	}

	public void setEnabled(boolean b)
	{
		enable = b;
		sketch.send("enable", b ? 1 : 0);
		p1.enable(b);
		p2.enable(b);
		p3.enable(b);
		p4.enable(b);
		update();
	}

	public void setTexCtrl(boolean b)
	{
		texctrl = b;
		p1.texcoord = b;
		p2.texcoord = b;
		p3.texcoord = b;
		p4.texcoord = b;
		update();
	}

	public void setZenable(boolean b)
	{
		p1.zenable = b;
		p2.zenable = b;
		p3.zenable = b;
		p4.zenable = b;
	}

	public void resetZ()
	{
		p1.setPosition(p1.position[0], p1.position[1], 0);
		p2.setPosition(p2.position[0], p2.position[1], 0);
		p3.setPosition(p3.position[0], p3.position[1], 0);
		p4.setPosition(p4.position[0], p4.position[1], 0);
		planeupdate();
		update();
		bang();
	}

	public void resetTexcoords()
	{
		plane.get(0)[3] = 0;
		plane.get(0)[4] = 1;
		plane.get(1)[3] = 1;
		plane.get(1)[4] = 1;
		plane.get(2)[3] = 1;
		plane.get(2)[4] = 0;
		plane.get(3)[3] = 0;
		plane.get(3)[4] = 0;
		bang();
	}
}