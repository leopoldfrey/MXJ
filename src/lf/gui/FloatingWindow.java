package lf.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FloatingWindow extends JFrame implements MouseListener, MouseMotionListener, FocusListener
{
	private static final long serialVersionUID = 1L;
	
	public int sizW,sizH,posX,posY;
	public boolean visible = true;
	private Container cp;
	protected TitleBar tb;
	protected JPanel pan;
	protected Font fo = new Font("Verdana",0, 10);
	private int dTX, dTY;
	private boolean displaced = false;
	
	public FloatingWindow(String title, int sizeW, int sizeH, int posiX, int posiY, boolean visi)
	{
		super();

		sizW = sizeW;
		sizH = sizeH;
		posX = posiX;
		posY = posiY;
		visible = visi;

		cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.setFocusable(false);
		
		tb = new TitleBar(title,sizW);
		tb.addMouseListener(this);
		tb.addMouseMotionListener(this);
		tb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						close();
					}
				});
			}
		});
		
		pan = new JPanel();
		pan.setVisible(true);
		pan.setFocusable(false);
		
		cp.add(tb, BorderLayout.NORTH);
		cp.add(pan, BorderLayout.CENTER);
		
		setUndecorated(true);
		setResizable(false);
		setSize(sizW,sizH);
		setLocation(posX,posY);
		setFocusable(true);
		addFocusListener(this);
	}
	
	public Component add(Component c)
	{
		return pan.add(c);
	}
	
	public void add(Component c,  String constraints) 
	{
		pan.add(c,constraints);
	}
	
	public void unsetLayout()
	{
		pan.setLayout(null);
	}
	
	public void setLayout(FlowLayout fl)
	{
		pan.setLayout(fl);
	}
	
	public void setLayout(GridBagLayout gbl)
	{
		pan.setLayout(gbl);
	}
	
	public void setLayout(BorderLayout bl)
	{
		pan.setLayout(bl);
	}
	
	public void setLayout(GridLayout gl)
	{
		pan.setLayout(gl);
	}
	
	public void close()
	{
		visible = false;
		setVisible(visible);
	}
	
	public void open()
	{
		visible = true;
		setVisible(visible);
		toFront();
	}
	
	public void mousePressed(MouseEvent arg0)
	{
		dTX = arg0.getX();
		dTY = arg0.getY();
		displaced = true;
	}

	public void mouseDragged(MouseEvent arg0)
	{
		if(displaced)
		{
			displace(arg0);
		}
	}

	public void displace(MouseEvent e)
	{
		if(displaced)
		{
			int tmpX = e.getX() - dTX;
			int tmpY = e.getY() - dTY;
			posX = this.getLocation().x;
			posY = this.getLocation().y;
			posX += tmpX;
			posY += tmpY;
			this.setLocation(posX, posY);
		} else {
			dTX = e.getX();
			dTY = e.getY();
			displaced = true;
		}
	}
	
	public void setLocation(int x,int y)
	{
		posX = x;
		posY = y;
		super.setLocation(posX,posY);
	}
	
	public Point getLocation()
	{
		return new Point(super.getLocation().x,super.getLocation().y);
	}
	
	public void setSize(int w, int h)
	{
		sizW = w;
		sizH = h;
//		if(tb != null)
//			tb.setWidth(sizW);
		super.setSize(sizW,sizH);
	}
	
	public void mouseReleased(MouseEvent arg0){}
	public void mouseClicked(MouseEvent arg0){}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mouseMoved(MouseEvent arg0){}

	public String getTitle()
	{
		if(tb.isVisible())
			return tb.getTitle();
		return super.getTitle();
	}

	public void setTitle(String title)
	{
		if(tb.isVisible()) tb.setTitle(title);
		else super.setTitle(title);
	}

	public void focusGained(FocusEvent arg0)
	{
//		hmb.update();
	}

	public void focusLost(FocusEvent arg0)
	{
		
	}
	
	public void setTitleBar(boolean b)
	{
		tb.setVisible(b);
	}
	
}

