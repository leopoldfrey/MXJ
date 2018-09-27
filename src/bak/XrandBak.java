package bak;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.IntBuffer;
import java.util.Vector;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxSystem;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.awt.TextRenderer;
import com.sun.opengl.util.gl2.GLUT;

import lf.LfObject;

public class XrandBak extends LfObject implements WindowListener, MouseListener, MouseMotionListener, MouseWheelListener, GLEventListener, KeyListener
{
	Vector<Float> probs;
	Vector<Float> normProbs;
	Vector<Atom> vals;
	float sum = 0;
	int size = 0;
	boolean quiet = false;
	boolean autoclear = false;
	boolean learning = false;
	private JFrame win;
	private JMenuBar menu;
	private GLCanvas canvas;
	private Container pane;
	private GLU glu = new GLU();
	private GL2 gl;
	private GLUT glut = new GLUT();
	private IntBuffer selectBuf;
	private String title = "Xrand";
	private int DEF_W = 200;
	private int DEF_H = 500;
	private int DEF_X = 0;
	private int DEF_Y = 0;
	private int width = DEF_W;
	private int height = DEF_H;
	private int posx = DEF_X;
	private int posy = DEF_Y;
	private static final char[] __accs = new char[] { 'W' };
	private static float[] bgcolor = new float[]{0.95f,0.95f,0.95f,1};
	private static float[] bordercolor = new float[]{0,0,0,0.25f};
	private static float[] headercolor = new float[]{0,0,0,0.10f};
	private static float[] fgcolor = new float[]{0,0,0,1};
	private static float[] slidercolor = new float[]{0,0,0,0.5f};
	private static final int H_HEADER = 20;
	private static final int W_SCROLL = 15;
	private static final int STRING_POS_H = 14;
	private static final int STRING_POS_W = 5;
	private static final int FONT_SIZE = 10;
	private float row_h = 20;
	private float col_w;
	private float max = 0;
	private FPSAnimator anim;
	private TextRenderer arial;
	
	public XrandBak(Atom atoms[])
	{
		version = 0.2f;
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		INLET_ASSIST = new String[] { "list of probs and values, bang" };
		OUTLET_ASSIST = new String[] { "random value", "chaining bang" };
		init(0, false, atoms);
		declareAttribute("quiet");
		declareAttribute("autoclear");
		declareAttribute("row_h");
		declareAttribute("title", "getTitle", "setTitle");
		declareAttribute("width", "getWidth", "setWidth");
		declareAttribute("height", "getHeight", "setHeight");
		declareAttribute("posx", "getPosx", "setPosx");
		declareAttribute("posy", "getPosy", "setPosy");
		clear();
		switch (atoms.length)
		{
		case 5:
			posy = atoms[4].toInt();
			posx = atoms[3].toInt();
			break;
		case 3:
			height = atoms[2].toInt();
			width = atoms[1].toInt();
		case 1:
			title = atoms[0].toString();
		default:
			break;
		}
		win = new JFrame()
		{
			public void requestFocus()
			{
				canvas.requestFocus();
			}
		};
		win.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		win.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				close();
			}
		});
		pane = win.getContentPane();
		pane.setLayout(new BorderLayout());
		canvas = new GLCanvas();
		canvas.addGLEventListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
		canvas.addKeyListener(this);
		win.addKeyListener(this);
		menu = new JMenuBar();
		JMenu filMenu = new JMenu("File");
		menu.add(filMenu);
		win.setJMenuBar(menu);
		pane.add(canvas, BorderLayout.CENTER);
		anim = new FPSAnimator(10);
		MaxSystem.registerCommandAccelerators(__accs);
		updateWin();
		anim.start();
	}

	private void updateWin()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				win.setTitle(title);
				win.setSize(width, height);
				win.setLocation(posx, posy);
			}
		});
	}

	public void bang()
	{
		if (sum == 0)
		{
			outlet(1, "bang");
			return;
		}
		float d = (float) Math.random();
		for (int i = 0; i < size; i++)
		{
			if (d < normProbs.get(i))
			{
				outlet(1, "bang");
				outlet(0, vals.get(i));
				return;
			}
		}
	}

	public void list(Atom[] l)
	{
		clear();
		add(l);
		update();
	}

	private void update()
	{
		calcSum();
		if (quiet)
			return;
		getvals();
		getprobs();
	}

	public void probs(Atom[] l)
	{
		probs = new Vector<Float>();
		for (Atom a : l)
			probs.add(a.toFloat());
		update();
	}

	public void vals(Atom[] l)
	{
		vals = new Vector<Atom>();
		for (Atom a : l)
			vals.add(a);
		update();
	}

	public void add(Atom[] l)
	{
		if (modulo(l.length, 2) != 0)
			error("list must have a pair number of element (value, probability)");
		for (int i = 0; i < l.length; i += 2)
		{
			vals.add(l[i]);
			probs.add(l[i + 1].toFloat());
		}
		update();
	}

	public void clear()
	{
		probs = new Vector<Float>();
		normProbs = new Vector<Float>();
		vals = new Vector<Atom>();
		sum = 0;
		size = 0;
		if (quiet)
			return;
		getvals();
		getprobs();
	}

	public void calcSum()
	{
		sum = 0;
		normProbs = new Vector<Float>();
		size = Math.min(probs.size(), vals.size());
		for (int i = 0; i < size; i++)
			sum += probs.get(i);
		if (sum == 0)
			return;
		float tmp = 0;
		for (int i = 0; i < size; i++)
		{
			tmp += (Float) probs.get(i) / sum;
			normProbs.add(tmp);
		}
	}

	public void getprobs()
	{
		outlet(0, Atom.parse("probs " + toString(probs)));
	}

	public void getvals()
	{
		outlet(0, Atom.parse("vals " + toString(vals)));
	}

	public static String toString(Vector v)
	{
		if (v.isEmpty())
			return "empty";
		String tmp = "";
		for (int i = 0; i < v.size(); i++)
			tmp += v.get(i) + (i + 1 != v.size() ? " " : "");
		return tmp;
	}

	public void learn(boolean b)
	{
		learning = b;
		if (learning && autoclear)
			clear();
	}

	public void anything(String message, Atom[] list)
	{
		if(list.length != 0)
			list(Atom.union(new Atom[]{Atom.newAtom(message)}, list));
		else
			inlet(Atom.newAtom(message));
	}
	
	public void inlet(int i)
	{
		inlet(Atom.newAtom(i));
	}
	
	public void inlet(float f)
	{
		inlet(Atom.newAtom(f));
	}
	
	public void inlet(Atom a)
	{
		if (learning)
		{
			if (vals.contains(a))
			{
				int pos = vals.indexOf(a);
				probs.set(pos, probs.get(pos) + 1);
			}
			else
			{
				vals.add(a);
				probs.add(1f);
			}
			update();
		}		
	}

	public void open()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				win.setVisible(true);
				win.requestFocus();
			}
		});
	}

	public void close()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				win.setVisible(false);
			}
		});
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
		updateWin();
	}

	public int getPosx()
	{
		return posx;
	}

	public void setPosx(int posx)
	{
		this.posx = posx;
		updateWin();
	}

	public int getPosy()
	{
		return posy;
	}

	public void setPosy(int posy)
	{
		this.posy = posy;
		updateWin();
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
		updateWin();
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
		updateWin();
	}

	public void windowActivated(WindowEvent e)
	{
		canvas.requestFocus();
	}

	public void windowClosed(WindowEvent e)
	{
	}

	public void windowClosing(WindowEvent e)
	{
		close();
	}

	public void windowDeactivated(WindowEvent e)
	{
	}

	public void windowDeiconified(WindowEvent e)
	{
		canvas.requestFocus();
	}

	public void windowIconified(WindowEvent e)
	{
	}

	public void windowOpened(WindowEvent e)
	{
		canvas.requestFocus();
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
	}

	public void mouseDragged(MouseEvent e)
	{
	}

	public void mouseMoved(MouseEvent e)
	{
	}

	public void mouseWheelMoved(MouseWheelEvent e)
	{
	}

	public void keyPressed(KeyEvent e)
	{
		//System.out.println(e.getKeyChar()+"");
		if(e.isMetaDown())
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_W:
				close();
			break;
			}
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
	}

	public void keyTyped(KeyEvent e)
	{
	}

	public void display(GLAutoDrawable drawable)
	{
		if(!win.isVisible())
			return;
		gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glViewport(0,0,width,height);
		glu.gluOrtho2D(0,width,0,height);
		gl.glPolygonMode(GL2.GL_FRONT,GL2.GL_FILL);
		glColor(bgcolor);
		gl.glRectf(0,0,width,height);
		glColor(headercolor);
		gl.glRectf(0,height-H_HEADER,width,height);
		gl.glRectf(0,height - H_HEADER - (int)(vals.size() * row_h),col_w,height - H_HEADER - (int)((vals.size()+1) * row_h));
		col_w = (width-W_SCROLL) / 3;
		glColor(bordercolor);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2f(0,height - H_HEADER);
		gl.glVertex2f(width,height - H_HEADER);
		for(int i = 1 ; i <= vals.size() ; i++)
		{
			gl.glVertex2f(0,height - H_HEADER - i * row_h);
			gl.glVertex2f(width-W_SCROLL,height - H_HEADER - i * row_h);
		}
		gl.glVertex2f(col_w,0);
		gl.glVertex2f(col_w,height);
		gl.glVertex2f(2*col_w,0);
		gl.glVertex2f(2*col_w,height);
		gl.glVertex2f(width-W_SCROLL,0);
		gl.glVertex2f(width-W_SCROLL,height);
		gl.glEnd();
		arial.beginRendering(width, height);
		arial.setColor(fgcolor[0], fgcolor[1], fgcolor[2], fgcolor[3]);
		arial.draw("Value", STRING_POS_W, height - STRING_POS_H);
		arial.draw("Proba(num)", (int)col_w + STRING_POS_W, height - STRING_POS_H);
		arial.draw("Proba(slider)", 2 * (int)col_w + STRING_POS_W, height - STRING_POS_H);
		for(int i = 0 ; i < vals.size() ; i++)
		{
			arial.draw(vals.get(i)+"", STRING_POS_W, height - H_HEADER - STRING_POS_H - (int)(i * row_h));
			arial.draw(probs.get(i)+"", (int)col_w + STRING_POS_W, height - H_HEADER - STRING_POS_H - (int)(i * row_h));
		}
		arial.draw("new",STRING_POS_W, height - H_HEADER - STRING_POS_H - (int)(vals.size() * row_h));
		arial.endRendering();
		glColor(slidercolor);
		updateMaximum();
		for(int i = 0 ; i < vals.size() ; i++)
			gl.glRectf(2*col_w + 3,height - H_HEADER - (int)(i * row_h) - 2, map(probs.get(i),2*col_w + 2,width-W_SCROLL - 2), height - H_HEADER - (int)((i+1) * row_h) + 3);
		gl.glLoadIdentity();
	}

	
	public float updateMaximum()
	{
		max = 0;
		for(Float f : probs)
			max = Math.max(f, max);
		return max;
	}
	
	private float map(float f, float x1, float x2)
	{
		return f/max * (x2 - x1) + x1;
	}
	
	public void displayChanged(GLAutoDrawable drawable, boolean arg1, boolean arg2)
	{
	}

	public void init(GLAutoDrawable drawable)
	{
		gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_FILL);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLineWidth(1);
		anim.add(drawable);
		gl.glClearColor(bgcolor[0],bgcolor[1],bgcolor[2],bgcolor[3]); // Background
		arial = new TextRenderer(new Font("Arial",Font.PLAIN,FONT_SIZE),true,true);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
	{
		width = w;
		height = h;
	}

	public void glColor(float[] color)
	{
		gl.glColor4f(color[0], color[1], color[2], color[3]);
	}

	public void glColor(int[] color)
	{
		gl.glColor4f((float) color[0] / 255, (float) color[1] / 255, (float) color[2] / 255, 1);
	}
	
	public void glColor(int[] color, float alpha)
	{
		gl.glColor4f((float) color[0] / 255, (float) color[1] / 255, (float) color[2] / 255, alpha);
	}
	
	public void glColor(Color c)
	{
		gl.glColor4f((float) c.getRed() / 255, (float) c.getGreen() / 255, (float) c.getBlue() / 255, 1);
	}
	
	public void dblclick()
	{
		open();
	}
	
	public void notifyDeleted()
	{
		close();
	}
	
	public void fgcolor(Atom[] list)
	{
		fgcolor = new float[]{0,0,0,1};
		switch(list.length)
		{
		case 4:
			fgcolor[3] = list[3].toFloat();
		case 3:
			fgcolor[2] = list[2].toFloat();
			fgcolor[1] = list[1].toFloat();
			fgcolor[0] = list[0].toFloat();
			break;
		case 1:
			fgcolor[2] = list[0].toFloat();
			fgcolor[1] = list[0].toFloat();
			fgcolor[0] = list[0].toFloat();
			break;
		}
	}
	public void bgcolor(Atom[] list)
	{
		bgcolor = new float[]{0,0,0,1};
		switch(list.length)
		{
		case 4:
			bgcolor[3] = list[3].toFloat();
		case 3:
			bgcolor[2] = list[2].toFloat();
			bgcolor[1] = list[1].toFloat();
			bgcolor[0] = list[0].toFloat();
			break;
		case 1:
			bgcolor[2] = list[0].toFloat();
			bgcolor[1] = list[0].toFloat();
			bgcolor[0] = list[0].toFloat();
			break;
		}
	}
	public void bordercolor(Atom[] list)
	{
		bordercolor = new float[]{0,0,0,1};
		switch(list.length)
		{
		case 4:
			bordercolor[3] = list[3].toFloat();
		case 3:
			bordercolor[2] = list[2].toFloat();
			bordercolor[1] = list[1].toFloat();
			bordercolor[0] = list[0].toFloat();
			break;
		case 1:
			bordercolor[2] = list[0].toFloat();
			bordercolor[1] = list[0].toFloat();
			bordercolor[0] = list[0].toFloat();
			break;
		}
	}	
	public void headercolor(Atom[] list)
	{
		headercolor = new float[]{0,0,0,1};
		switch(list.length)
		{
		case 4:
			headercolor[3] = list[3].toFloat();
		case 3:
			headercolor[2] = list[2].toFloat();
			headercolor[1] = list[1].toFloat();
			headercolor[0] = list[0].toFloat();
			break;
		case 1:
			headercolor[2] = list[0].toFloat();
			headercolor[1] = list[0].toFloat();
			headercolor[0] = list[0].toFloat();
			break;
		}
	}
	public void slidercolor(Atom[] list)
	{
		slidercolor = new float[]{0,0,0,1};
		switch(list.length)
		{
		case 4:
			slidercolor[3] = list[3].toFloat();
		case 3:
			slidercolor[2] = list[2].toFloat();
			slidercolor[1] = list[1].toFloat();
			slidercolor[0] = list[0].toFloat();
			break;
		case 1:
			slidercolor[2] = list[0].toFloat();
			slidercolor[1] = list[0].toFloat();
			slidercolor[0] = list[0].toFloat();
			break;
		}
	}

	public void dispose(GLAutoDrawable arg0)
	{
	}
}
