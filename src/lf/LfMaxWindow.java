package lf;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.cycling74.max.MaxWindow;

public class LfMaxWindow
{
	public static final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private MaxWindow window;
	private int x;
	private int y;
	private int width;
	private int height;
	private String title;
	private int ox;
	private int oy;
	private int owidth;
	private int oheight;
	private String otitle;
	private String name;
	private boolean fullscreen;
	
	public LfMaxWindow(String _name, MaxWindow _win)
	{
		init(_name, _win);
	}
	
	private void init(String _name, MaxWindow _win)
	{
		this.name = _name;
		this.window = _win;
		x = ox = window.getLocation()[0];
		y = oy = window.getLocation()[1];
		width = owidth = window.getSize()[0];
		height = oheight = window.getSize()[1];
		title = otitle = window.getTitle() != null ? window.getTitle() : "Untitled";
//		System.out.println("Init LfMaxWindow "+name+" "+title);
	}
	
	public MaxWindow getWindow()
	{
		return window;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void restore()
	{
		setLocation(ox, oy);
		setSize(owidth, oheight);
		setTitle(otitle);
		setGrow(true);
		setTitleBar(true);
		setFloat(false);
		setDirty(false);
		setZoom(true);
		setVisible(true);
	}
	
	public void setFullscreen(boolean b)
	{
		fullscreen = b;
		if(fullscreen)
		{
			x = window.getLocation()[0];
			y = window.getLocation()[1];
			width = window.getSize()[0];
			height = window.getSize()[1];
			window.setLocation(0, 0, dim.width, dim.height);
		} else {
			window.setLocation(x, y, x+width, y+height);
		}	
	}
	
	public void setSize(int _w, int _h)
	{
		if(fullscreen)
			return;
		//System.out.println(name+" "+_w+" "+_h);
		width = _w;
		height = _h;
		window.setSize(width, height);
		x = window.getLocation()[0];
		y = window.getLocation()[1];
	}
	
	public void setLocation(int _x, int _y)
	{
		if(fullscreen)
			return;
		//System.out.println(name+" "+_x+" "+_y);
		x = _x;
		y = _y;
		width = window.getSize()[0];
		height = window.getSize()[1];
		window.setLocation(x, y, x+width, y+height);
	}
	
	public void setTitle(String _t)
	{
		title = _t;
		window.setTitle(title);
	}
	
	public String getTitle()
	{
		return title;
	}

	public void restoreTitle()
	{
		setTitle(otitle);
	}
	
	public void setFloat(boolean b)
	{
		window.setFloat(b);
	}
	
	public void setGrow(boolean b)
	{
		window.setGrow(b);
	}
	
	public void setClose(boolean b)
	{
		window.setClose(b);
	}
	
	public void setTitleBar(boolean b)
	{
		window.setTitleBar(b);
	}
	
	public void setDirty(boolean b)
	{
		window.setDirty(b);
	}
	
	public void setZoom(boolean b)
	{
		window.setZoom(b);
	}

	public void setVisible(boolean b)
	{
		window.setVisible(b);
	}
	
	public boolean isVisible()
	{
		return window.isVisible();
	}
	
	public int getX()
	{
		return x = window.getLocation()[0];
	}

	public void setX(int _x)
	{
		if(fullscreen)
			return;
		setLocation(_x, window.getLocation()[1]);
	}

	public int getY()
	{
		return y = window.getLocation()[1];
	}

	public void setY(int _y)
	{
		if(fullscreen)
			return;
		setLocation(window.getLocation()[0], _y);
	}

	public int getWidth()
	{
		return width = window.getSize()[0];
	}

	public void setWidth(int _w)
	{
		if(fullscreen)
			return;
		setSize(_w, window.getSize()[1]);
	}

	public int getHeight()
	{
		return height = window.getSize()[1];
	}

	public void setHeight(int _h)
	{
		if(fullscreen)
			return;
		setSize(window.getSize()[0], _h);
	}
	
	public void setBackGround(int R, int G, int B)
	{
		window.getPatcher().setBackgroundColor(R, G, B);
	}
}
