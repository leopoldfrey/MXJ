import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxSystem;
import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.awt.TextRenderer;
import com.sun.opengl.util.gl2.GLUT;

import lf.LfObject;
import lf.util.Formatter;

public class Xrand extends LfObject implements WindowListener, MouseListener, MouseMotionListener, MouseWheelListener, GLEventListener, KeyListener
{
	Vector<Float> probs;
	Vector<Float> normProbs;
	Vector<Atom> vals;
	float sum = 0;
	int size = 0;
	boolean quiet = false;
	boolean autoclear = true;
	boolean learning = false;
//	int vars = 1;
//	int steps = 1;
	private JFrame win;
	private JMenuBar menubar;
	private boolean menu = false;
	private GLCanvas canvas;
	private Container pane;
	private GLU glu = new GLU();
	private GL2 gl;
	private GLUT glut = new GLUT();
	private IntBuffer selectBuf;
	private FPSAnimator anim;
	// params
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
	private float[] bgcolor = new float[]{0.95f,0.95f,0.95f,1};
	private float[] bordercolor = new float[]{0,0,0,0.25f};
	private float[] headerbgcolor = new float[]{0.82f,0.82f,0.82f,1f};
	private float[] headerfgcolor = new float[]{0,0,0,1};
	private float[] fgcolor = new float[]{0,0,0,1};
	private float[] slidercolor = new float[]{0,0,0,0.2f};
	private float[] sliderbordercolor = new float[]{0,0,0,0.25f};
	private float[] overcolor = new float[]{1, 1, 0.65f, 0.2f};
	private float[] selectcolor = new float[]{0.65f, 0.65f, 1, 0.2f};
	private float[] draggingcolor = new float[]{1, 0.65f, 0.65f, 0.2f};
	private float[] cluecolor = new float[]{1, 1, 0.65f, 0.5f};
	private float header_h = 20;
	private float status_h = 20;
	private float scroll_w = 15;
	private float string_pos_h = 13;
	private float string_pos_w = 5;
	private float row_h = 20;
	private float col_w = (width-scroll_w) / 2;
	private float slider_margin = 2;
	private float scroll_margin = 3;
	private float max = 0;
	private boolean orientation = false;
	private int font_size = 10;
	private int status_font_size = 10;
	private int header_font_size = 10;
	private TextRenderer values_font;
	private TextRenderer status_font;
	private TextRenderer header_font;
	// MOUSE
	private final static int MOUSE_SELECT_SIZE = 5;
	private Point mousepos = new Point();
	private Point oldmousepos = new Point();
	private int over_index = -1;
	private int select_index = -1;
	private boolean dragging = false;
	private boolean prepadragging = false;
	private int dx, dy;
	private boolean relative = true;
	private boolean multislider = true;
	private boolean automax = true;
	// OVER CLUE
	private boolean over_clue = true;
	private String over = "";
	private float overx = 0;
	private int clue_max = 35;
	private int clue_count = clue_max;
	private float clue_trans = 15f;

	// INTERP
	private boolean interp = true;
	private float select_value = 0;
	private int delta_index = 0;
	private int last_dragged_index = -1;
	private float last_dragged_value = 0;
	// EDIT
	private boolean editing = false;
	private String edit_name = "";
	private int carret_pos = 0;
	private boolean first_key;
	private boolean shift_down = false;
	private boolean alt_down = false;
	private boolean ctrl_down = false;
	private boolean meta_down = false;
	private boolean right_down = false;
	private int fp = 2;
	private Formatter ff = new Formatter();
	// V_SCROLLBAR
	private float draw_h = 0;
	private float y_ref = 0;
	private float _offscreen_h = 0;
	private float v_scrollbar_nub_y = 0;
	private float v_scrollbar_nub_h = 0;
	private boolean v_scrollbar_is_showing = false;
	private float _v_nub_off_y = 0;
	private float _content_pane_y_offset = 0;
	private boolean dragged_vscroll = false;
	// H_SCROLLBAR
	private float draw_w = 0;
	private float x_ref = 0;
	private float _offscreen_w = 0;
	private float h_scrollbar_nub_x = 0;
	private float h_scrollbar_nub_w = 0;
	private boolean h_scrollbar_is_showing = false;
	private float _h_nub_off_x = 0;
	private float _content_pane_x_offset = 0;
	private boolean dragged_hscroll = false;
	// work in progress
	boolean autosize = false;
	boolean autoopen = false;
	// exclude N last random values
	int nxclude = 0;
	Vector<Atom> nlast = new Vector<Atom>();
	
	public Xrand(Atom atoms[])
	{
		version = 0.4f;
		build = "05/10/09";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		INLET_ASSIST = new String[] { "list of probs and values, bang" };
		OUTLET_ASSIST = new String[] { "random value", "chaining bang" };
		init(0, true, atoms);
		declareAttribute("quiet");
		declareAttribute("autoclear");
		declareAttribute("relative");
		declareAttribute("multislider");
		declareAttribute("fp", "getFp", "setFp");
		declareAttribute("row_h");
		declareAttribute("col_w");
		declareAttribute("fgcolor");
		declareAttribute("bgcolor");
		declareAttribute("headerbgcolor");
		declareAttribute("headerfgcolor");
		declareAttribute("slidercolor");
		declareAttribute("sliderbordercolor");
		declareAttribute("bordercolor");
		declareAttribute("selectcolor");
		declareAttribute("draggingcolor");
		declareAttribute("cluecolor");
		declareAttribute("overcolor");
		declareAttribute("header_h");
		declareAttribute("status_h");
		declareAttribute("string_pos_h");
		declareAttribute("string_pos_w");
		declareAttribute("slider_margin");
		declareAttribute("scroll_margin");
		declareAttribute("over_clue");
		declareAttribute("interp");
		declareAttribute("orientation");
		declareAttribute("font_size", "getFont_size", "setFont_size");
		declareAttribute("header_font_size", "getHeader_font_size", "setHeader_font_size");
		declareAttribute("status_font_size", "getStatus_font_size", "setStatus_font_size");
		declareAttribute("scroll_w");
		declareAttribute("max");
		declareAttribute("automax");
		declareAttribute("menu", "getMenu", "setMenu");
		declareAttribute("title", "getTitle", "setTitle");
		declareAttribute("width", "getWidth", "setWidth");
		declareAttribute("height", "getHeight", "setHeight");
		declareAttribute("posx", "getPosx", "setPosx");
		declareAttribute("posy", "getPosy", "setPosy");
		declareAttribute("nxclude", "getNxclude", "setNxclude");
		clear();
//		switch (atoms.length)
//		{
//		case 5:
//			posy = atoms[4].toInt();
//			posx = atoms[3].toInt();
//			break;
//		case 3:
//			height = atoms[2].toInt();
//			width = atoms[1].toInt();
//		case 1:
//			title = atoms[0].toString();
//		default:
//			break;
//		}
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
		constructMenu();
		pane.add(canvas, BorderLayout.CENTER);
		MaxSystem.registerCommandAccelerators(__accs);
		updateWin();
		ff.max_right = fp;
		ff.min_right = fp;
		anim = new FPSAnimator(24);
		anim.start();
		
		if(autoopen)
			open();
	}

	private void constructMenu()
	{
		// TODO XRAND update menu when attributes change
		menubar = new JMenuBar();
		JMenu filMenu = new JMenu("File");
		final JMenuItem clearMI = new JMenuItem("Clear");
		clearMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				clear();
			}
		});
		filMenu.add(clearMI);
		menubar.add(filMenu);
		JMenu optionMenu = new JMenu("Options");
		menubar.add(optionMenu);
		final JCheckBoxMenuItem relMI = new JCheckBoxMenuItem("Relative", relative);
		relMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				relative = relMI.isSelected();
			}
		});
		optionMenu.add(relMI);
		final JCheckBoxMenuItem quietMI = new JCheckBoxMenuItem("Quiet", quiet);
		quietMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				quiet = quietMI.isSelected();
			}
		});
		optionMenu.add(quietMI);
		final JCheckBoxMenuItem multiMI = new JCheckBoxMenuItem("Multislider", multislider);
		optionMenu.add(multiMI);
		multiMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				multislider = multiMI.isSelected();
			}
		});
		optionMenu.addSeparator();
		final JCheckBoxMenuItem autoclearMI = new JCheckBoxMenuItem("Autoclear", autoclear);
		autoclearMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				autoclear = autoclearMI.isSelected();
			}
		});
		optionMenu.add(autoclearMI);
		final JCheckBoxMenuItem learnMI = new JCheckBoxMenuItem("Learn", learning);
		learnMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				learn(learnMI.isSelected());
			}
		});
		optionMenu.add(learnMI);
//		setMenu(true);
	}

	private void updateWin()
	{
		draw_h = height - header_h - status_h;
		_offscreen_h = draw_h;
		v_scrollbar_is_showing = false;
		_v_nub_off_y = 0;
		v_scrollbar_nub_y = 0;
		v_scrollbar_nub_h = 0;
		_content_pane_y_offset = 0;

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
		if(nxclude > 0)
			randValue();
		else
		{
			float d = (float) Math.random();
			for (int i = 0; i < normProbs.size(); i++)
			{
				if (d < normProbs.get(i))
				{
					outlet(1, "bang");
					outlet(0, vals.get(i));//Atom.parse(vals.get(i).toString()));
					return;
				}
			}
		}
	}

	private void randValue()
	{
		float d = (float) Math.random();
		for (int i = 0; i < normProbs.size(); i++)
		{
			if (d < normProbs.get(i))
			{
				if(!nlast.contains(vals.get(i)))
				{
					output(vals.get(i));
					return;
				}
				randValue();
				return;
			}
		}
	}

	private void output(Atom a)
	{
		if(nlast.size() == nxclude)
			nlast.remove(0);
		nlast.add(a);
		outlet(1, "bang");
		outlet(0, a);//Atom.parse(vals.get(i).toString()));
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
		getlist();
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
		getlist();
	}

	private boolean ready = true;
	public void calcSum()
	{
		ready = false;
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
		ready = true;
	}

	public void getprobs()
	{
		outlet(0, Atom.parse("probs " + toString(probs)));
	}

	public void getvals()
	{
		outlet(0, Atom.parse("vals " + toString(vals)));
	}
	public void getlist()
	{
		if(vals.isEmpty())
		{
			outlet(0, Atom.parse("res empty"));
		}
		else
		{
			Atom[] result = new Atom[] { Atom.newAtom("res") };
			for (int i = 0; i < vals.size(); i++)
			{
				result = concat(result, vals.get(i));
				result = concat(result, Atom.newAtom(probs.get(i)));
			}
			outlet(0, result);
		}
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
		if(message.equalsIgnoreCase("learn_values"))
			inlet(Atom.newAtom(Atom.toOneString(list)));
		else if(list.length != 0)
			list(concat(message, list));
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
				win.dispose();
			}
		});
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int h)
	{
		height = h;
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				win.setSize(win.getWidth(),height);
			}
		});
	}

	public int getPosx()
	{
		return posx;
	}

	public void setPosx(int x)
	{
		posx = x;
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				win.setLocation(posx, win.getY());
			}
		});
	}

	public int getPosy()
	{
		return posy;
	}

	public void setPosy(int y)
	{
		posy = y;
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				win.setLocation(win.getX(), posy);
			}
		});
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String t)
	{
		title = t;
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				win.setTitle(title);
			}
		});
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int w)
	{
		width = w;
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				win.setSize(width, win.getHeight());
			}
		});
	}

	public void windowActivated(WindowEvent e)
	{
		over_index = -1;
		select_index = -1;
		dragging = false;
		prepadragging = false;
		last_dragged_index = -1;
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
		over_index = -1;
		select_index = -1;
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
		over_index = -1;
		select_index = -1;
	}

	public void mousePressed(MouseEvent e)
	{
		clue_count = clue_max;
		shift_down = e.isShiftDown();
		alt_down = e.isAltDown();
		ctrl_down = e.isControlDown();
		meta_down = e.isMetaDown();

		oldmousepos = new Point(mousepos);
		mousepos = new Point(e.getPoint());
		
		switch(over_index)
		{
		case -3: // SCROLL NUB
			_v_nub_off_y = mousepos.y - v_scrollbar_nub_y;
			dragged_vscroll = true;
			break;
		case -2: // SCROLL BG
			if (mousepos.y < row_h + v_scrollbar_nub_y)
				move_v_scrollbar_nub(v_scrollbar_nub_y - v_scrollbar_nub_h);
			else
				move_v_scrollbar_nub(v_scrollbar_nub_y + v_scrollbar_nub_h);
			_v_nub_off_y = mousepos.y - v_scrollbar_nub_y;
			dragged_vscroll = true;
			break;
		case -1:
			select_index = -1;
			editing = false;
			break;
		case 0:
			// new value
			select_index = vals.size()+1;
			vals.add(Atom.newAtom("?"));
			probs.add(0f);
			editing = true;
			edit_name = "";
			break;
		default:
			select_index = over_index;
			last_dragged_index = select_index;
			editing = e.getClickCount() >= 2;
			if(editing)
			{
				edit_name = select_index < 1000 ? vals.get(select_index-1).toString() : getProba(select_index)+"";
				first_key = true;
				carret_pos = edit_name.length();
			} else if(select_index >= 1000) {
				prepadragging = true;
				if(alt_down)
				{
					setProba(select_index, 0f);
					update();
				}
				else if(meta_down)
				{
					setProba(select_index, 1f);
					update();
				}
				else if(!relative)
				{
					select_value = orientation ? clip(unmap(height-mousepos.y, status_h+col_w+scroll_w, height),0): clip(unmap(mousepos.x, col_w, width - scroll_w),0);
					setProba(select_index, select_value);
					last_dragged_value = select_value;
					update();
				}
			} else if(alt_down) {
				vals.removeElementAt(select_index-1);
				probs.removeElementAt(select_index-1);
				update();
			}
			break;
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		clue_count = clue_max;
		shift_down = e.isShiftDown();
		alt_down = e.isAltDown();
		ctrl_down = e.isControlDown();
		meta_down = e.isMetaDown();

		dragged_vscroll = false;
		dragging = false;
		prepadragging = false;
		last_dragged_index = -1;
	}

	public void mouseDragged(MouseEvent e)
	{
		clue_count = clue_max;
		shift_down = e.isShiftDown();
		alt_down = e.isAltDown();
		ctrl_down = e.isControlDown();
		meta_down = e.isMetaDown();

		oldmousepos = new Point(mousepos);
		mousepos = new Point(e.getPoint());
		dx = mousepos.x - oldmousepos.x;
		dy = mousepos.y - oldmousepos.y;

		if(prepadragging)
		{
			prepadragging = false;
			dragging = true;
		} else if(dragging) {
			if(select_index > 1000)
			{
				if (relative)
				{
					float percent = (Math.abs(dy) > Math.abs(dx) ? -dy : dx) / (width - scroll_w - col_w);
					setProba(select_index, clip(getProba(select_index) + (shift_down ? percent * max / 5f : percent * max), 0));
				} else {
					if (over_index != select_index)
					{
						if (over_index > 1000)
							select_index = over_index;
						else if(over_index > 0)
							select_index = over_index+1000;
					}
					
					select_value = orientation ? clip(unmap(height-mousepos.y, status_h+col_w+scroll_w, height),0): clip(unmap(mousepos.x, col_w, width - scroll_w),0);
					setProba(select_index, select_value);
//					System.out.println("SET : "+select_index+" "+select_value+" "+last_dragged_index+" "+last_dragged_value);
					if(Math.abs(delta_index = select_index-last_dragged_index) > 1 && interp)
						interpolate(select_index, select_value, last_dragged_index, last_dragged_value, delta_index, (delta_index > 0) ? 1 : -1);
					last_dragged_index = select_index;
					last_dragged_value = select_value;
				}
				update();
				canvas.display();
			}
		} else if (dragged_vscroll) {
			move_v_scrollbar_nub(mousepos.y - _v_nub_off_y);
		}
	}
			
	public void interpolate(int index, float value, int oldindex, float oldvalue, int internum, int sign)
	{
		float valstep = sign * (value - oldvalue) / internum;
		value = oldvalue + valstep;
		index = oldindex + sign;
		internum *= sign;
		while (--internum != 0)
		{
			setProba(index, value);
			index = index + sign;
			value += valstep;
		}
	}

	private void setProba(int index, float value)
	{
		probs.set(index - 1001, value);
	}
	
	private float getProba(int index)
	{
		return probs.get(index - 1001);
	}
	
	private void interpProb(int a, int b, int i)
	{
		setProba(i, interpol(getProba(a),getProba(b),(i - a)/(float)(b - a)));
	}
	
	public static float interpol(float f1, float f2, float step)
	{
		return f1 + step * (f2 - f1);
	}

	public void mouseMoved(MouseEvent e)
	{
		clue_count = clue_max;
		oldmousepos = new Point(mousepos);
		mousepos = new Point(e.getPoint());
	}

	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
		{
			if(orientation)
				move_h_scrollbar_nub(h_scrollbar_nub_x + e.getUnitsToScroll() * e.getScrollAmount());
			else
				move_v_scrollbar_nub(v_scrollbar_nub_y + e.getUnitsToScroll() * e.getScrollAmount());
		}
	}

	public void keyPressed(KeyEvent e)
	{
		int current_key = e.getKeyCode();
		shift_down = e.isShiftDown();
		alt_down = e.isAltDown();
		ctrl_down = e.isControlDown();
		meta_down = e.isMetaDown();
		if(meta_down)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_W:
				close();
			break;
			}
		} else {
			if(editing && !alt_down && !ctrl_down && !meta_down)
			{
				switch (current_key)
				{
				case KeyEvent.VK_SHIFT:
					break;
				case KeyEvent.VK_ESCAPE:
					cancel_edit_name();
					break;
				case KeyEvent.VK_ENTER:
					valid_edit_name();
					break;
				case KeyEvent.VK_BACK_SPACE:
					if(alt_down || first_key)
					{
						edit_name = "";
						carret_pos = 0;
						first_key = false;
					} else {
						if (edit_name.length() != 0 && carret_pos > 0)
						{
							if (carret_pos >= edit_name.length())
							{
								edit_name = edit_name.substring(0, edit_name.length() - 1);
								carret_pos--;
							} else {
								edit_name = edit_name.substring(0, carret_pos - 1) + edit_name.substring(carret_pos);
								carret_pos--;
							}
						}
					}
					break;
				case KeyEvent.VK_DELETE:
					if(first_key)
					{
						edit_name = "";
						carret_pos = 0;
						first_key = false;					
					}
					if (carret_pos < edit_name.length())
						edit_name = edit_name.substring(0, carret_pos) + edit_name.substring(carret_pos + 1);
					break;
				case KeyEvent.VK_LEFT:
					carret_pos = clip(carret_pos - 1, 0);
					first_key = false;
					break;
				case KeyEvent.VK_RIGHT:
					carret_pos = clip(carret_pos + 1, edit_name.length());
					first_key = false;
					break;
				case KeyEvent.VK_UP:
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_TAB:
					break;
				case KeyEvent.VK_SPACE:
				default:
					String c = (""+e.getKeyChar());
					if(first_key)
					{
						edit_name = shift_down ? c : c.toLowerCase();
						carret_pos = 1;
						first_key = false;
						break;
					}
					if (carret_pos >= edit_name.length())
					{
						edit_name += shift_down ? c : c.toLowerCase();
					} else {
						edit_name = edit_name.substring(0, carret_pos) + (shift_down ? c : c.toLowerCase()) + edit_name.substring(carret_pos);
					}
					carret_pos++;
					break;
				}
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
		try
		{
			clue_count--;
			if (!win.isVisible())
				return;
			gl = drawable.getGL().getGL2();
			gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
			gl.glLoadIdentity();
			gl.glViewport(0, 0, width, height);
		    gl.glLoadIdentity();
			glu.gluOrtho2D(0, width, 0, height);
			gl.glMatrixMode(GL2.GL_MODELVIEW);
		    gl.glLoadIdentity();
			gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_FILL);
			if (!dragged_vscroll && (!dragging || (dragging && !relative && multislider)))
				getUmouse();
			draw(true);
			gl.glLoadIdentity();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getUmouse()
	{
		int hits = 0;
		int[] viewport = new int[4];
		selectBuf = BufferUtil.newIntBuffer(512);//ByteBuffer.allocateDirect(2048).asIntBuffer();
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
		gl.glSelectBuffer(selectBuf.capacity(), selectBuf);
		gl.glRenderMode(GL2.GL_SELECT);
		gl.glInitNames();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPushName(-1);
		gl.glPushMatrix();
		gl.glLoadIdentity(); // reset the proj. matrix
		// !!! leave gluPickMatrix after glloadidentity
		/* create MOUSE_SELECT_SIZExMOUSE_SELECT_SIZE pixel picking region near cursor location */
		double my = height - mousepos.y;
		glu.gluPickMatrix(mousepos.x, my, MOUSE_SELECT_SIZE, MOUSE_SELECT_SIZE, viewport, 0);
		// !!! leave gluOrtho after glupickmatrix
		glu.gluOrtho2D(0,width,0,height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		draw(false);
		gl.glPopMatrix();
		hits = gl.glRenderMode(GL2.GL_RENDER);
		if (hits == 0) {
			over_index = -1;
			return;
		}
		int offset = 0;
		int names = -1;
		for (int i = 0; i < hits; i++) {
			names = selectBuf.get(offset);
			offset++;
			offset++;
			offset++;
			for (int j = 0; j < names; j++) {
				if (j == (names - 1)) {
					over_index = selectBuf.get(offset);
				}
				offset++;
			}
		}
	}

	private void draw(boolean render)
	{
		if (!orientation)
		{
			draw_h = height - header_h - status_h;
			y_ref = height - header_h - _content_pane_y_offset;
			drawBackGround(render);
			drawSelection(render);
			drawOverAndDragging(render);
			drawSliders(render);
			drawValues(render);
			drawNew(render);
			draw_v_scrollbar(render);
			drawClue(render);
			drawHeaderStatusAndBorders(render);
		} else {
			draw_w = width - header_h;
			x_ref = header_h + _content_pane_x_offset;
			drawBackGround(render);
			drawSelectionH(render);
			drawOverAndDraggingH(render);
			drawSlidersH(render);
			drawValuesH(render);
			drawNewH(render);
			draw_h_scrollbar(render);
			drawClueH(render);
			drawHeaderStatusAndBordersH(render);
		}
	}

	private void drawClue(boolean render)
	{
		if(render && status_font_size > 0 && over_index >= 0 && over_clue && clue_count > 0)
		{
			float newalpha = clipH(clue_count / clue_trans,1);
			int real_index = over_index > 1000 ? over_index - 1001 : over_index - 1;
			try
			{
				if(over_index == 0)
				{
					over = "add a new value";
					overx = vals.size() * row_h;
				}
				else
				{
					over = vals.get(real_index) + " : " + ff.format(probs.get(real_index)) + (over_index > 1000 && !dragging && !relative ? "/" + ff.format(unmap(mousepos.x, col_w,width-scroll_w)) : "");
					overx = (over_index - 1001) * row_h;
				}
			} catch (Exception e)
			{
				over = e.toString();
				e.printStackTrace();
			}

			
			float px = mousepos.x + 2 * 5;
			float py = y_ref - overx;
			float pd = 3 * 5;
			over = "("+over+")";
			if(dragging)
			{
				if(relative)
					px = map(getProba(select_index),col_w,width-scroll_w) + 2 * 5;
			}
			float str_len = (float)status_font.getBounds(over).getWidth() + 2 * 5;
			float str_height = (float)status_font.getBounds(over).getHeight() + 2 * 5;
			if(px + str_len > width)
				px -= str_len + 4 * 5;
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
			glColor(cluecolor, cluecolor[3] * newalpha);
			gl.glRectf(px, py-str_height, px + str_len, py);
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_LINE);
			glColor(bordercolor, bordercolor[3] * newalpha);
			gl.glRectf(px, py-str_height, px + str_len, py);
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
			status_font.beginRendering(width, height);
			status_font.setColor(headerfgcolor[0], headerfgcolor[1], headerfgcolor[2], headerfgcolor[3] * newalpha);
			drawString(status_font, over, px + 5, py - str_height + 2 * 5);
			status_font.endRendering();
		}
	}
	private void drawClueH(boolean render)
	{
		if(render && status_font_size > 0 && over_index >= 0 && over_clue && clue_count > 0)
		{
			float newalpha = clipH(clue_count / clue_trans,1);
			int real_index = over_index > 1000 ? over_index - 1001 : over_index - 1;
			try
			{
				if(over_index == 0)
				{
					over = "add a new value";
					overx = vals.size() * row_h;
				}
				else
				{
					over = vals.get(real_index) + " : " + ff.format(probs.get(real_index)) + (over_index > 1000 && !dragging && !relative ? "/" + ff.format(unmap(height-mousepos.y, status_h+col_w+scroll_w, height)) : "");
					overx = (over_index - 1001) * row_h;
				}
			} catch (Exception e)
			{
				over = e.toString();
				e.printStackTrace();
			}
			
			
			float px = x_ref + overx + row_h + 5;
			float py = height - mousepos.y;
			over = "("+over+")";
			if(dragging)
			{
				if(relative)
					py = map(getProba(select_index),scroll_w+status_h+col_w,height);
			}
			float str_len = (float)status_font.getBounds(over).getWidth() + 2 * 5;
			float str_height = (float)status_font.getBounds(over).getHeight() + 2 * 5;
			if(px + str_len > width)
				px -= str_len + 1.5f * row_h;
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
			glColor(cluecolor, cluecolor[3] * newalpha);
			gl.glRectf(px, py-str_height, px + str_len, py);
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_LINE);
			glColor(bordercolor, bordercolor[3] * newalpha);
			gl.glRectf(px, py-str_height, px + str_len, py);
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
			status_font.beginRendering(width, height);
			status_font.setColor(headerfgcolor[0], headerfgcolor[1], headerfgcolor[2], headerfgcolor[3] * newalpha);
			drawString(status_font, over, px + 5, py - str_height + 2*5);
			status_font.endRendering();
		}
	}

	private void draw_v_scrollbar(boolean render)
	{
		float old_offscreen_h = _offscreen_h;
		_offscreen_h = (vals.size() + 1) * row_h;
		v_scrollbar_is_showing = _offscreen_h > draw_h || _content_pane_y_offset != 0;
		float x = width - scroll_w;
		float y = height - header_h;
		float nub_percent = draw_h / _offscreen_h;
		v_scrollbar_nub_h = clip((int) (draw_h * nub_percent), 5);
		if(render)
		{
			// draw bar area
			glColor(bgcolor);
			gl.glRectf(x, y, x+scroll_w, y-draw_h);
			if (v_scrollbar_is_showing)
			{
				// draw nub
				glColor(slidercolor);
				gl.glRectf(x + scroll_margin, y - v_scrollbar_nub_y - scroll_margin, x + scroll_w - scroll_margin, y - v_scrollbar_nub_y - v_scrollbar_nub_h + scroll_margin);
				glColor(bordercolor);
				gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
				gl.glRectf(x + scroll_margin, y - v_scrollbar_nub_y - scroll_margin, x + scroll_w - scroll_margin, y - v_scrollbar_nub_y - v_scrollbar_nub_h + scroll_margin);
				gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
//				System.out.println("_offscreen "+_offscreen_h+" _content_pane_y_offset "+_content_pane_y_offset+" v_scrollbar_nub_y "+v_scrollbar_nub_y+" v_scrollbar_nub_h "+v_scrollbar_nub_h);
			} else if(_content_pane_y_offset == 0)
				v_scrollbar_nub_y = 0;
		} else if (v_scrollbar_is_showing) {
			loadNameRect(x, y, scroll_w, draw_h, -2);
			loadNameRect(x, y - v_scrollbar_nub_y, scroll_w, v_scrollbar_nub_h, -3);
		}
	}
	
	private void move_v_scrollbar_nub(float y)
	{
		if (v_scrollbar_is_showing)
		{
			if (y < 0)
				y = 0;
			if (y > draw_h - v_scrollbar_nub_h)
				y = draw_h - v_scrollbar_nub_h;
			v_scrollbar_nub_y = y;
			float percent_offset = v_scrollbar_nub_y / draw_h;
			_content_pane_y_offset = clipH((int) -(_offscreen_h * percent_offset),0);
		}
	}

	private void draw_h_scrollbar(boolean render)
	{
		float old_offscreen_w = _offscreen_w;
		_offscreen_w = (vals.size() + 1) * row_h;
		h_scrollbar_is_showing = _offscreen_w > draw_w || _content_pane_x_offset != 0;
		float x = header_h;
		float y = status_h + scroll_w;
		float nub_percent = draw_w / _offscreen_w;
		h_scrollbar_nub_w = clip((int) (draw_w * nub_percent), 5);
		if(render)
		{
			// draw bar area
			glColor(bgcolor);
			gl.glRectf(x, y, x+draw_w, y-scroll_w);
			if (h_scrollbar_is_showing)
			{
				// draw nub
				glColor(slidercolor);
				gl.glRectf(x + h_scrollbar_nub_x + scroll_margin, y - scroll_margin, x + h_scrollbar_nub_x + h_scrollbar_nub_w - scroll_margin, y - scroll_w + scroll_margin);
				glColor(bordercolor);
				gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
				gl.glRectf(x + h_scrollbar_nub_x + scroll_margin, y - scroll_margin, x + h_scrollbar_nub_x + h_scrollbar_nub_w - scroll_margin, y - scroll_w + scroll_margin);
				gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
			} else if(_content_pane_x_offset == 0)
				h_scrollbar_nub_x = 0;
		} else if (h_scrollbar_is_showing) {
			loadNameRect(x, y, draw_w, scroll_w, -2);
			loadNameRect(x + h_scrollbar_nub_x, y, h_scrollbar_nub_w, scroll_w, -3);
		}
	}
	
	private void move_h_scrollbar_nub(float x)
	{
		if (h_scrollbar_is_showing)
		{
			if (x < 0)
				x = 0;
			if (x > draw_w - h_scrollbar_nub_w)
				x = draw_w - h_scrollbar_nub_w;
			h_scrollbar_nub_x = x;
			float percent_offset = h_scrollbar_nub_x / draw_w;
			_content_pane_x_offset = clipH((int) -(_offscreen_w * percent_offset),0);
		}
	}
	
	private void drawOverAndDragging(boolean render)
	{
		if(!editing)
		{
			for (int i = 0; i < vals.size(); i++)
			{
				loadNameRect(0, y_ref - i * row_h, col_w, row_h, i + 1);
				loadNameRect(col_w, y_ref - i * row_h, width - scroll_w - col_w, row_h, 1000 + i + 1);
			}
		} else if(render) {	
			if(select_index != -1)
			{
				if(select_index < 1000)
					editRect(0, y_ref - (select_index-1) * row_h, col_w, row_h);
				else
					editRect(col_w, y_ref - (select_index-1001) * row_h, width-scroll_w-col_w, row_h);
			}
		}
	}

	private void drawOverAndDraggingH(boolean render)
	{
		if(!editing)
		{
			for (int i = 0; i < vals.size(); i++)
			{
				loadNameRect(x_ref + i * row_h, status_h+scroll_w+col_w, row_h, col_w, i + 1);
				loadNameRect(x_ref + i * row_h, height, row_h, height - scroll_w - col_w - status_h, 1000 + i + 1);
			}
		} else if(render) {	
			if(select_index != -1)
			{
				if(select_index < 1000)
					editRect(x_ref + (select_index-1) * row_h, status_h+scroll_w+col_w, row_h, col_w);
				else
					editRect(x_ref + (select_index-1001) * row_h, height, row_h, height - scroll_w - col_w - status_h);
			}
		}
	}

	private void drawSliders(boolean render)
	{
		if(render)
		{
			updateMaximum();
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
			glColor(slidercolor);
			for(int i = 0 ; i < vals.size() ; i++)
				gl.glRectf(col_w + slider_margin,y_ref - (i * row_h) - slider_margin, map(probs.get(i),col_w + slider_margin,width-scroll_w - slider_margin), y_ref - ((i+1) * row_h) + slider_margin);
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_LINE);
			glColor(sliderbordercolor);
			for(int i = 0 ; i < vals.size() ; i++)
				gl.glRectf(col_w + slider_margin,y_ref - (i * row_h) - slider_margin, map(probs.get(i),col_w + slider_margin,width-scroll_w - slider_margin), y_ref - ((i+1) * row_h) + slider_margin);
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
		}
	}

	private void drawSlidersH(boolean render)
	{
		if(render)
		{
			updateMaximum();
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
			glColor(slidercolor);
			for(int i = 0 ; i < vals.size() ; i++)
				gl.glRectf(x_ref + (i * row_h) + slider_margin, status_h + scroll_w + col_w + slider_margin, x_ref + ((i+1) * row_h) - slider_margin, map(probs.get(i),status_h + scroll_w + col_w + slider_margin,height - slider_margin));
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_LINE);
			glColor(sliderbordercolor);
			for(int i = 0 ; i < vals.size() ; i++)
				gl.glRectf(x_ref + (i * row_h) + slider_margin, status_h + scroll_w + col_w + slider_margin, x_ref + ((i+1) * row_h) - slider_margin, map(probs.get(i),status_h + scroll_w + col_w + slider_margin,height - slider_margin));
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
		}
	}

	private void drawSelection(boolean render)
	{
		if(render && !editing && !dragging && select_index != -1)
		{
			if(select_index < 1000)
				selectRect(0, y_ref - (select_index-1) * row_h, col_w, row_h);
			else
				selectRect(col_w, y_ref - (select_index-1001) * row_h, width-scroll_w-col_w, row_h);
		}
	}
	
	private void drawSelectionH(boolean render)
	{
		if(render && !editing && !dragging && select_index != -1)
		{
			if(select_index < 1000)
				selectRect(x_ref + (select_index-1) * row_h, height, row_h, height - scroll_w - col_w - status_h);
			else
				selectRect(x_ref + (select_index-1001) * row_h, height, row_h, height - scroll_w - col_w - status_h);
		}
	}
	
	private void drawHeaderStatusAndBorders(boolean render)
	{
		if(render)
		{
			glColor(bordercolor);
			gl.glBegin(GL2.GL_LINES);
			for(int i = 1 ; i <= vals.size() ; i++)
			{
				gl.glVertex2f(0, y_ref - i * row_h);
				gl.glVertex2f(width-scroll_w, y_ref - i * row_h);
			}
			gl.glEnd();
			if(header_h > 0)
			{
				glColor(headerbgcolor);
				gl.glRectf(0,height-header_h,width,height);
				if(header_font_size != 0)
				{
					header_font.beginRendering(width, height);
					header_font.setColor(headerfgcolor[0], headerfgcolor[1], headerfgcolor[2], headerfgcolor[3]);
					if(col_w > 0)
						drawString(header_font, "Value", string_pos_w, height - string_pos_h);
					drawString(header_font, "Proba", col_w + string_pos_w, height - string_pos_h);
					header_font.endRendering();
				}
			}
			if(status_h > 0)
			{
				glColor(headerbgcolor);
				gl.glRectf(0,0,width,status_h);
				if(status_font_size != 0)
				{
					status_font.beginRendering(width, height);
					status_font.setColor(headerfgcolor[0], headerfgcolor[1], headerfgcolor[2], headerfgcolor[3]);
					drawString(status_font, "("+over+")", string_pos_w, status_h - string_pos_h);
					status_font.endRendering();
				}
				
			}
			glColor(bordercolor);
			gl.glBegin(GL2.GL_LINES);
			gl.glVertex2f(0,height - header_h);
			gl.glVertex2f(width,height - header_h);
			gl.glVertex2f(0,status_h);
			gl.glVertex2f(width,status_h);
			gl.glVertex2f(col_w,status_h);
			gl.glVertex2f(col_w,height);
			gl.glVertex2f(width-scroll_w,status_h);
			gl.glVertex2f(width-scroll_w,height);
			gl.glEnd();
		} else {
			
		}
	}

	private void drawHeaderStatusAndBordersH(boolean render)
	{
		if(render)
		{
			glColor(bordercolor);
			gl.glBegin(GL2.GL_LINES);
			for(int i = 1 ; i <= vals.size() ; i++)
			{
				gl.glVertex2f(x_ref + i * row_h, status_h + scroll_w);
				gl.glVertex2f(x_ref + i * row_h, height);
			}
			gl.glEnd();
			if(header_h > 0)
			{
				glColor(headerbgcolor);
				gl.glRectf(0,0,header_h,height);
				if(header_font_size != 0)
				{
					header_font.beginRendering(width, height);
					gl.glMatrixMode(GL2.GL_MODELVIEW);
					header_font.setColor(headerfgcolor[0], headerfgcolor[1], headerfgcolor[2], headerfgcolor[3]);
					if(col_w > 0)
					{
						gl.glTranslatef(string_pos_h, status_h + scroll_w + string_pos_w, 0);
						gl.glRotatef(90, 0, 0, 1);
						drawString(header_font, "Value", 0, 0);
						header_font.flush();
					}
					gl.glLoadIdentity();
					gl.glTranslatef(string_pos_h, status_h + scroll_w + col_w + string_pos_w, 0);
					gl.glRotatef(90, 0, 0, 1);
					drawString(header_font, "Proba", 0, 0);
					header_font.flush();
					header_font.endRendering();
					gl.glMatrixMode(GL2.GL_PROJECTION);
				}
			}
			if(status_h > 0)
			{
				glColor(headerbgcolor);
				gl.glRectf(header_h,0,width,status_h);
				if(status_font_size != 0)
				{
					status_font.beginRendering(width, height);
					status_font.setColor(headerfgcolor[0], headerfgcolor[1], headerfgcolor[2], headerfgcolor[3]);
					drawString(status_font, "("+over+")", header_h + string_pos_h, string_pos_w);
					status_font.endRendering();
				}
			}
			glColor(bordercolor);
			gl.glBegin(GL2.GL_LINES);
			gl.glVertex2f(header_h, 0);
			gl.glVertex2f(header_h, height);
			gl.glVertex2f(0, status_h + scroll_w + col_w);
			gl.glVertex2f(width, status_h + scroll_w + col_w);
			gl.glVertex2f(0, status_h + scroll_w);
			gl.glVertex2f(width, status_h + scroll_w);
			gl.glVertex2f(header_h, status_h);
			gl.glVertex2f(width, status_h);
			gl.glEnd();
		} else {
			
		}
	}
	
	private void drawValues(boolean render)
	{
		if(render && font_size > 0)
		{
			values_font.beginRendering(width, height);
			values_font.setColor(fgcolor[0], fgcolor[1], fgcolor[2], fgcolor[3]);
			if(col_w > 0)
				for(int i = 0 ; i < vals.size() ; i++)
					drawString(values_font, i == select_index-1 && editing ? view_edit_string() : vals.get(i)+"", string_pos_w, y_ref - string_pos_h - (i * row_h));
			for(int i = 0 ; i < vals.size() ; i++)
				drawString(values_font, i == select_index-1001 && editing ? view_edit_string() : ff.format(probs.get(i)), col_w + string_pos_w, y_ref - string_pos_h - (i * row_h));
			values_font.endRendering();			
		}
	}

	private void drawValuesH(boolean render)
	{
		if(render && font_size > 0)
		{
			values_font.beginRendering(width, height);
			gl.glMatrixMode(GL2.GL_MODELVIEW);
			values_font.setColor(fgcolor[0], fgcolor[1], fgcolor[2], fgcolor[3]);
			if(col_w > 0)
				for(int i = 0 ; i < vals.size() ; i++)
				{
					gl.glLoadIdentity();
					gl.glTranslatef(x_ref + string_pos_h + (i * row_h), status_h + scroll_w + string_pos_w, 0);
					gl.glRotatef(90, 0, 0, 1);
					drawString(values_font, i == select_index-1 && editing ? view_edit_string() : vals.get(i)+"", 0, 0);
					values_font.flush();
//					drawString(values_font, i == select_index-1 && editing ? view_edit_string() : vals.get(i)+"", x_ref + string_pos_w + (i * row_h), status_h + scroll_w + string_pos_h);
				}
			for(int i = 0 ; i < vals.size() ; i++)
			{
				gl.glLoadIdentity();
				gl.glTranslatef(x_ref + string_pos_h + (i * row_h), status_h + scroll_w + col_w + string_pos_w, 0);
				gl.glRotatef(90, 0, 0, 1);
				drawString(values_font, i == select_index-1001 && editing ? view_edit_string() : ff.format(probs.get(i)), 0,0);
				values_font.flush();
//				drawString(values_font, i == select_index-1001 && editing ? view_edit_string() : ff.format(probs.get(i)), x_ref + string_pos_w + (i * row_h), status_h + scroll_w + col_w + string_pos_h);
			}
			values_font.endRendering();			
			gl.glMatrixMode(GL2.GL_PROJECTION);
		}
	}

	private void drawBackGround(boolean render)
	{
		if(render)
		{
			glColor(bgcolor);
			gl.glRectf(0,0,width,height);
		} else if(!editing && !dragging) {
			loadNameRect(0, 0, width, height, -1);
		}
	}

	private void drawNew(boolean render)
	{
		if(render && font_size > 0)
		{
			glColor(headerbgcolor);
			gl.glRectf(0,y_ref - (vals.size() * row_h),row_h,y_ref - ((vals.size()+1) * row_h));
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_LINE);
			glColor(bordercolor);
			gl.glRectf(0,y_ref - (vals.size() * row_h),row_h,y_ref - ((vals.size()+1) * row_h));
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
			values_font.beginRendering(width, height);
			values_font.setColor(headerfgcolor[0], headerfgcolor[1], headerfgcolor[2], headerfgcolor[3]);
			drawString(values_font,"+",string_pos_w+2, y_ref - string_pos_h - (vals.size() * row_h));
			values_font.endRendering();			
		} else if(!editing && !dragging){
			loadNameRect(0, y_ref - vals.size() * row_h, row_h, row_h, 0);
		}
	}

	private void drawNewH(boolean render)
	{
		if(render && font_size > 0)
		{
			glColor(headerbgcolor);
			gl.glRectf(x_ref + (vals.size() * row_h), status_h + scroll_w, x_ref + ((vals.size()+1) * row_h), status_h + scroll_w + row_h);
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_LINE);
			glColor(bordercolor);
			gl.glRectf(x_ref + (vals.size() * row_h), status_h + scroll_w, x_ref + ((vals.size()+1) * row_h), status_h + scroll_w + row_h);
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
			values_font.beginRendering(width, height);
			gl.glMatrixMode(GL2.GL_MODELVIEW);
			values_font.setColor(headerfgcolor[0], headerfgcolor[1], headerfgcolor[2], headerfgcolor[3]);
			gl.glTranslatef(x_ref + string_pos_h + (vals.size() * row_h), status_h + scroll_w + string_pos_w, 0);
			gl.glRotatef(90, 0, 0, 1);
			drawString(values_font,"+",0, 0);
			values_font.flush();
			values_font.endRendering();			
			gl.glMatrixMode(GL2.GL_PROJECTION);
		} else if(!editing && !dragging){
			loadNameRect(x_ref + vals.size() * row_h, status_h + scroll_w + row_h, row_h, row_h, 0);
		}
	}

	private void drawString(TextRenderer font, String ss, float xx, float yy)
	{
		font.draw(ss, (int)xx, (int)yy);
	}

	private void valid_edit_name()
	{
		if(select_index < 1000)
			vals.set(select_index-1,Atom.newAtom(edit_name));
		else {
			float old_prob = getProba(select_index);
			try
			{
				setProba(select_index, new Float(edit_name));
			} catch (NumberFormatException e)
			{
				setProba(select_index, old_prob);
			}
		}
		editing = false;
		edit_name = "";
		update();
	}
	
	private void cancel_edit_name()
	{
		editing = false;
		edit_name = "";
	}
	
	private String view_edit_string()
	{
		String view_string;
		if (edit_name.length() != 0)
		{
			if (carret_pos >= edit_name.length())
				view_string = edit_name + "|";
			else view_string = edit_name.substring(0, carret_pos) + "|" + edit_name.substring(carret_pos);
		}
		else
		{
			view_string = "|";
		}
		return view_string;
	}
	
	private void loadNameRect(float x, float y, float w, float h, int index)
	{
		gl.glColor4f(1, 1, 1, 0);
		if(over_index == index)
			glColor(dragging ? draggingcolor : overcolor);
		gl.glLoadName(index);
		gl.glRectf(x, y, x+w, y-h);
	}
	
	private void selectRect(float x, float y, float w, float h)
	{
		glColor(editing ? draggingcolor : selectcolor);
		gl.glRectf(x, y, x+w, y-h);
	}
	
	private void editRect(float x, float y, float w, float h)
	{
		glColor(draggingcolor);
		gl.glRectf(x, y, x+w, y-h);
	}
	
	private void updateMaximum()
	{
		if(automax)
		{
			if (dragging && !relative)
				return;
			max = 0.1f;
			for (Float f : probs)
				max = Math.max(f, max);
		}
	}
	
	private float map(float f, float x1, float x2)
	{
		return f/max * (x2 - x1) + x1;
	}
	
	private float unmap(float f, float x1, float x2)
	{
		return (f - x1) / (x2 - x1) * max;
//		return f/max * (x2 - x1) + x1;
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
		values_font = new TextRenderer(new Font("Arial",Font.PLAIN,font_size),font_size >= 10,false);
		header_font = new TextRenderer(new Font("Arial",Font.PLAIN,header_font_size),header_font_size >= 10,false);
		status_font = new TextRenderer(new Font("Arial",Font.PLAIN,status_font_size),status_font_size >= 10,false);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
	{
		int old_height = height;
		int old_width = width;
		width = w;
		height = h;
		int dt;
		if(orientation && (dt = width - old_width) > 0 && _content_pane_x_offset < 0)
		{
			_content_pane_x_offset = clipH(_content_pane_x_offset + dt, 0);
			h_scrollbar_nub_x = - _content_pane_x_offset / _offscreen_w * draw_w;
		}
		else if((dt = height - old_height) > 0 && _content_pane_y_offset < 0)
		{
			_content_pane_y_offset = clipH(_content_pane_y_offset + dt,0);
			v_scrollbar_nub_y = - _content_pane_y_offset / _offscreen_h * draw_h;
		}
		draw_h = height - header_h;
		if(autosize && vals.size() != 0)
		{
			row_h = clip(draw_h / (vals.size() + 1),10);
			header_h = row_h;
			slider_margin = 0.1f * row_h;
			setFont_size((int)(0.6f * row_h));
			string_pos_w = 0.2f * row_h;
			string_pos_h = 0.7f * row_h;
		}
	}

	public void glColor(float[] color)
	{
		gl.glColor4f(color[0], color[1], color[2], color[3]);
	}

	public void glColor(float[] color, float alpha)
	{
		gl.glColor4f(color[0], color[1], color[2], alpha);
	}
	
	private void glColor(int[] color)
	{
		gl.glColor4f((float) color[0] / 255, (float) color[1] / 255, (float) color[2] / 255, 1);
	}
	
	private void glColor(int[] color, float alpha)
	{
		gl.glColor4f((float) color[0] / 255, (float) color[1] / 255, (float) color[2] / 255, alpha);
	}
	
	private void glColor(Color c)
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
	
	public float[] getBgcolor()
	{
		return bgcolor;
	}

	public void setBgcolor(float[] bgcolor)
	{
		this.bgcolor = bgcolor;
	}

	public float[] getBordercolor()
	{
		return bordercolor;
	}

	public void setBordercolor(float[] bordercolor)
	{
		this.bordercolor = bordercolor;
	}

	public float[] getFgcolor()
	{
		return fgcolor;
	}

	public void setFgcolor(float[] fgcolor)
	{
		this.fgcolor = fgcolor;
	}

	public float[] getHeadercolor()
	{
		return headerbgcolor;
	}

	public void setHeadercolor(float[] headercolor)
	{
		this.headerbgcolor = headercolor;
	}

	public float[] getSlidercolor()
	{
		return slidercolor;
	}

	public void setSlidercolor(float[] slidercolor)
	{
		this.slidercolor = slidercolor;
	}

	public float[] getSliderbordercolor()
	{
		return sliderbordercolor;
	}

	public void setSliderbordercolor(float[] sliderbordercolor)
	{
		this.sliderbordercolor = sliderbordercolor;
	}

	public float[] getOvercolor()
	{
		return overcolor;
	}

	public void setOvercolor(float[] overcolor)
	{
		this.overcolor = overcolor;
	}

	public float[] getSelectcolor()
	{
		return selectcolor;
	}

	public void setSelectcolor(float[] selectcolor)
	{
		this.selectcolor = selectcolor;
	}

	public float[] getDraggingcolor()
	{
		return draggingcolor;
	}

	public void setDraggingcolor(float[] draggingcolor)
	{
		this.draggingcolor = draggingcolor;
	}
	public float[] getCluecolor()
	{
		return cluecolor;
	}

	public void setCluecolor(float[] cluecolor)
	{
		this.cluecolor = cluecolor;
	}

	public float[] getHeaderfgcolor()
	{
		return headerfgcolor;
	}

	public void setFont_size(int size)
	{
		font_size = clip(size,0);
		values_font = new TextRenderer(new Font("Arial",Font.PLAIN,font_size),font_size>=10,false);
	}
	
	public int getFont_size()
	{
		return font_size;
	}

	public void setHeaderfgcolor(float[] headerfgcolor)
	{
		this.headerfgcolor = headerfgcolor;
	}

	public int getStatus_font_size()
	{
		return status_font_size;
	}

	public void setStatus_font_size(int status_font_size)
	{
		this.status_font_size = clip(status_font_size,0);
		status_font = new TextRenderer(new Font("Arial",Font.PLAIN,status_font_size),status_font_size>=10,false);
	}

	public int getHeader_font_size()
	{
		return header_font_size;
	}

	public void setHeader_font_size(int header_font_size)
	{
		this.header_font_size = clip(header_font_size,0);
		header_font = new TextRenderer(new Font("Arial",Font.PLAIN,header_font_size),header_font_size>=10,false);
	}

	public int getFp()
	{
		return fp;
	}

	public void setFp(int fp)
	{
		this.fp = fp;
		ff.max_right = fp;
		ff.min_right = fp;
	}
	
	public boolean getMenu()
	{
		return menu;
	}
	
	public void setMenu(boolean b)
	{
		menu = b;
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				win.setSize(win.getWidth()+1, win.getHeight());
				win.setSize(win.getWidth()-1, win.getHeight());
				if(menu)
					win.setJMenuBar(menubar);
				else
					win.setJMenuBar(null);
			}
		});
	}
	
	// SAVE STATE
	public void save()
	{
		embedMessage("state", Atom.parse(
					title
				+" "+width
				+" "+height
				+" "+posx
				+" "+posy
				+" "+orientation
				+" "+col_w
				+" "+row_h
				+" "+header_h
				+" "+status_h
				+" "+string_pos_h
				+" "+string_pos_w
				+" "+slider_margin
				+" "+scroll_w
				+" "+scroll_margin
				+" "+font_size
				+" "+header_font_size
				+" "+status_font_size
				+" "+fp
				+" "+automax
				+" "+max
				+" "+autoclear
				+" "+menu
				+" "+relative
				+" "+quiet
				+" "+multislider
				+" "+over_clue
				+" "+arrayToString(bgcolor)
				+" "+arrayToString(fgcolor)
				+" "+arrayToString(bordercolor)
				+" "+arrayToString(headerbgcolor)
				+" "+arrayToString(slidercolor)
				+" "+arrayToString(overcolor)
				+" "+arrayToString(selectcolor)
				+" "+arrayToString(draggingcolor)
				+" "+arrayToString(headerfgcolor)
				+" "+arrayToString(sliderbordercolor)
				+" "+arrayToString(cluecolor)
				));
	}
	
	public void state(Atom[] attr)
	{
		try
		{
			int p = 0;
			setTitle(attr[p++].toString());
			setWidth(attr[p++].toInt());
			setHeight(attr[p++].toInt());
			setPosx(attr[p++].toInt());
			setPosy(attr[p++].toInt());
			orientation = attr[p++].toBoolean();
			col_w = attr[p++].toFloat();
			row_h = attr[p++].toFloat();
			header_h = attr[p++].toFloat();
			status_h = attr[p++].toFloat();
			string_pos_h = attr[p++].toFloat();
			string_pos_w = attr[p++].toFloat();
			slider_margin = attr[p++].toFloat();
			scroll_w = attr[p++].toFloat();
			scroll_margin = attr[p++].toFloat();
			setFont_size(attr[p++].toInt());
			setHeader_font_size(attr[p++].toInt());
			setStatus_font_size(attr[p++].toInt());
			setFp(attr[p++].toInt());
			automax = attr[p++].toBoolean();
			max = attr[p++].toFloat();
			autoclear = attr[p++].toBoolean();
			setMenu(attr[p++].toBoolean());
			relative = attr[p++].toBoolean();
			quiet = attr[p++].toBoolean();
			multislider = attr[p++].toBoolean();
			over_clue = attr[p++].toBoolean();
			bgcolor = subFloat(attr, p, 4);
			p+=4;
			fgcolor = subFloat(attr, p, 4);
			p+=4;
			bordercolor = subFloat(attr, p, 4);
			p+=4;
			headerbgcolor = subFloat(attr, p, 4);
			p+=4;
			slidercolor = subFloat(attr, p, 4);
			p+=4;
			overcolor = subFloat(attr, p, 4);
			p+=4;
			selectcolor = subFloat(attr, p, 4);
			p+=4;
			draggingcolor = subFloat(attr, p, 4);
			p+=4;
			headerfgcolor = subFloat(attr, p, 4);
			p+=4;
			sliderbordercolor = subFloat(attr, p, 4);
			p+=4;
			cluecolor = subFloat(attr, p, 4);
			p+=4;
		} catch (ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
	}

	private float[] subFloat(Atom[] fromArray, int start, int length)
	{
		float[] toArray = new float[length];
		for(int i = 0; i < length ; i++)
			toArray[i] = fromArray[start+i].toFloat();
		return toArray;
	}
	
	private String arrayToString(float[] array)
	{
		String tmp = "";
		for(float f:array)
			tmp += f+" ";
		return tmp.substring(0, tmp.length()-1);
	}
	
	public void defaults()
	{
		state(Atom.parse(
				"Xrand" 	// title
				+" "+200 	// width
				+" "+500 	// height
				+" "+0		// posx
				+" "+0		// poxy
				+" "+false	// orientation
				+" "+100	// col_w
				+" "+20		// row_h
				+" "+20		// header_h
				+" "+20		// status_h
				+" "+13		// string_pos_h
				+" "+5		// string_pos_w
				+" "+2		// slider_margin
				+" "+15		// scroll_w
				+" "+3		// scroll_margin
				+" "+10		// font_size
				+" "+10		// header_font_size
				+" "+10		// status_font_size
				+" "+2		// fp
				+" "+true	// automax
				+" "+0		// max
				+" "+true	// autoclear
				+" "+false	// menu
				+" "+true	// relative
				+" "+false	// quiet
				+" "+true	// multislider
				+" "+true	// over_clue
				+" "+arrayToString(new float[]{0.95f,0.95f,0.95f,1})	// bgcolor
				+" "+arrayToString(new float[]{0,0,0,1})				// fgcolor
				+" "+arrayToString(new float[]{0,0,0,0.25f})			// bordercolor
				+" "+arrayToString(new float[]{0.82f,0.82f,0.82f,1f})	// headerbgcolor
				+" "+arrayToString(new float[]{0,0,0,0.2f})				// slidercolor
				+" "+arrayToString(new float[]{1, 1, 0.65f, 0.2f})		// overcolor
				+" "+arrayToString(new float[]{0.65f, 0.65f, 1, 0.2f})	// selectcolor
				+" "+arrayToString(new float[]{1, 0.65f, 0.65f, 0.2f})	// draggingcolor
				+" "+arrayToString(new float[]{0,0,0,1})				// headerfgcolor
				+" "+arrayToString(new float[]{0,0,0,0.25f})			// sliderbordercolor
				+" "+arrayToString(new float[]{1, 1, 0.65f, 0.5f})			// cluecolor
				));
	}//*/

	public int getNxclude()
	{
		return nxclude;
	}

	public void setNxclude(int nxclude)
	{
		this.nxclude = nxclude;
	}

	public void dispose(GLAutoDrawable arg0)
	{
	}
}
