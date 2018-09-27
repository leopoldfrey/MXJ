package holoboule;

import java.awt.BorderLayout;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import java.awt.geom.Point2D;
import java.nio.IntBuffer;
import java.util.Vector;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import lf.util.Formatter;

import com.cycling74.jitter.JitterMatrix;
import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;
import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.j2d.TextRenderer;

public class Holoboule extends MaxObject implements WindowListener, MouseListener, MouseMotionListener, MouseWheelListener, GLEventListener, KeyListener
{
	public float version;
	protected int[] INLET_TYPES = {};
	protected int[] OUTLET_TYPES = {};
	protected String[] INLET_ASSIST = {};
	protected String[] OUTLET_ASSIST = {};
	protected String build = "";

	public static Vector<Holoboule> holoboule_system = new Vector<Holoboule>();
	
	private static final int OUTMODE_MATRIX = 0;
	private static final int OUTMODE_LIST = 1;
	private static final int OUTMODE_RECALLMULTI = 2;
	private static final int OUTMODE_POSITIONS = 3;
	private static final int OUTMODE_MONOMATRIX = 4;
	private static final int OUTMODE_STEREOMATRIX = 5; // Entropie
	private static final int DISPMODE_SOURCE = 0;
	private static final int DISPMODE_SOURCELIST = 1;
	private static final int DISPMODE_SOURCELINKS = 2;
	private static final int DISPMODE_BOULE = 3;
	private static final int DISPMODE_BOULELIST = 4;
	private static final int DISPMODE_MATRIX = 5;
	private static final int DISPMODE_MULT = 6;
	//
	private static final double MIN_DIST = 0.02;
	private static final double MIN_VALUE = 0.01;
	//
	private static final int PLANE_MATRIX_MODE = 0;
	private static final int PLANE_MATRIX_MULT = 1;
	//
	private JFrame win;
	private GLJPanel canvas;
	private Container pane;
	private GLU glu = new GLU();
	private GL gl;
	private GLUT glut = new GLUT();
	private GLUquadric quad;
	private IntBuffer selectBuf;
	private FPSAnimator anim;
	// params
	private String title = "Holoboule";
	private int DEF_W = 500;
	private int DEF_H = 500;
	private int DEF_X = 50;
	private int DEF_Y = 50;
	private int width = DEF_W;
	private int height = DEF_H;
	private int posx = DEF_X;
	private int posy = DEF_Y;
	private float ratio = 1;
	private int fp = 2;
	private Formatter ff = new Formatter();
	private int font_size = 10;
	private TextRenderer font;
	// ui
	private Font ffont = new Font("Arial", Font.PLAIN, font_size);
	private JTextField tf = null;
	private int padY = 13 + 10;
	private int padX = 45;
	private int padX2 = 30;
	private int padX3 = 45;
	private int row_num, col_num, param_num;
	// colors
	private float[] bgcolor_source = new float[] { 0.99f, 0.99f, 0.99f, 1 };
	private float[] bgcolor_boule = new float[] { 1, 1, 1, 1 };
	private float[] bginfocolor = new float[] { 1, 1, 1, 0.7f };
	private float[] fontcolor = new float[] { 0, 0, 0, 1 };
	private float[] selected_color = { 1, 0, 0, 0.5f };
	private float[] inactive_color = { 0.5f, 0.5f, 0.5f, 0.5f };
	private float sphere_alpha1 = 0.05f;
	private float sphere_alpha2 = 0.025f;
	private float links_alpha = 0.1f;
	// MOUSE
	private final static int MOUSE_SELECT_SIZE = 5;
	private boolean idlemouse = false;
	private Point2D.Float mousepos = new Point2D.Float();
	private Point2D.Float worldpos = new Point2D.Float();
	private Point2D.Float oldmousepos = new Point2D.Float();
	private Point2D.Float oldworldpos = new Point2D.Float();
	private Point2D.Float startworldpos = new Point2D.Float();
	private Point2D.Float startmousepos = new Point2D.Float();
	private int over_index = -1;
	private int select_index = -1;
	private boolean dragging = false;
	private boolean prepadragging = false;
	private boolean drag_boule = false;
	private boolean drag_source = false;
	private boolean drag_boulesize = false;
	private boolean drag_bouleweight = false;
	private boolean drag_menu = false;
	private boolean drag_menu2 = false;
	private float dx, dy;
	private float refx, refy;
	private Boule curboule;
	private Source cursource;
	private Link curlink;
	private int mods;
	PopupMenu shapepop;
	PopupMenu matrixpop;
	PopupMenu linkmodepop;
	PopupMenu linktopop;
	// options
	boolean display_names = true;
	boolean display_shapes = true;
	boolean display_infos = true;
	boolean display_links = true;
	boolean display_menu = false;
	boolean mult_db = true;
	boolean calc_inactiv_boules = false;
	int menu_row_height = 15;
	int display_mode = DISPMODE_SOURCE;
	int output_mode = OUTMODE_MATRIX;
	float zoom = 1;
	String helper_str = "";
	// data
	Vector<Boule> boules = new Vector<Boule>();
	Vector<Source> sources = new Vector<Source>();
	Vector<Source> movedsources = new Vector<Source>();
	Vector<Integer> movedsources_index = new Vector<Integer>();
	// plane 0 : matrix_mode, 1 : matrix_mult, ?????? 2 : boule (row= x y z sizex sizey sizez shape r g b a), 3 : source (col= x y z r g b a), 4 : links (col= to mode params...)
	JitterMatrix matrix;
	int matrow, matcol;
	// matrix modes default values
	public float matrix_direct_val = 1;
	public float matrix_exclude_val = 0;
	public float matrix_ignore_val = 0; // -1 ????
	private ItemListener linkToItemListener;
	private String mapfile = title + "matrix.jxf";

	// tmp
	Boule b;
	Atom outmatrix[];
	Vector<Atom> atomvec;
	
	public Holoboule(Atom[] args)
	{
		// matrix init
		matrix = new JitterMatrix(title + "matrix", 5, "float32", Boule.MAX_BOULES, Source.MAX_SOURCES);
		initMatrix();
		Boule.matref = matrix;
		Source.matref = matrix;
		Link.matref = matrix;
		// UI init
		win = new JFrame();
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
		canvas = new GLJPanel();
		canvas.addGLEventListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
		canvas.addKeyListener(this);
		canvas.setLayout(null);
		win.addKeyListener(this);
		pane.add(canvas, BorderLayout.CENTER);
		shapepop = new PopupMenu("Shape");
		ItemListener shapeItemListener = new ItemListener()
		{
			public void itemStateChanged(ItemEvent ie)
			{
				CheckboxMenuItem m = (CheckboxMenuItem) ie.getSource();
				int shape = 0;
				for (int i = 0; i < Boule.shape_string.length; i++)
				{
					if (m.getLabel().equalsIgnoreCase(Boule.shape_string[i]))
					{
						shape = i;
						break;
					}
				}
				if (curboule != null)
					curboule.setShape(shape);
				else for (Boule _b : boules)
					_b.setShape(shape);
			}
		};
		for (String s : Boule.shape_string)
		{
			CheckboxMenuItem m = new CheckboxMenuItem(s);
			m.addItemListener(shapeItemListener);
			shapepop.add(m);
		}
		matrixpop = new PopupMenu("Matrix");
		ItemListener matrixItemListener = new ItemListener()
		{
			public void itemStateChanged(ItemEvent ie)
			{
				String mode = ((CheckboxMenuItem) ie.getSource()).getLabel();
				if (matcol == -1 && matrow == -1)
					setAllMode(mode);
				else if (matcol == -1)
					setBouleMode(mode, matrow);
				else if (matrow == -1)
					setSourceMode(mode, matcol);
				else setBouleSourceMode(mode, matrow, matcol);
			}
		};
		for (String s : Source.mode_string)
		{
			CheckboxMenuItem m = new CheckboxMenuItem(s);
			m.addItemListener(matrixItemListener);
			matrixpop.add(m);
		}
		linkmodepop = new PopupMenu("Link Mode");
		ItemListener linkModeItemListener = new ItemListener()
		{
			public void itemStateChanged(ItemEvent ie)
			{
				String mode = ((CheckboxMenuItem) ie.getSource()).getActionCommand();
				int modi = Link.getModeFromString(mode);
				if (curlink != null)
					curlink.setMode(modi);
				else
				{
					Link lk;
					for (Source s : sources)
					{
						lk = s.getLink();
						if (lk != null)
							lk.setMode(modi);
					}
				}
			}
		};
		for (int i = 0; i < Link.link_action.length; i++)
		{
			CheckboxMenuItem m = new CheckboxMenuItem(Link.link_string[i]);
			m.setActionCommand(Link.link_action[i]);
			m.addItemListener(linkModeItemListener);
			linkmodepop.add(m);
		}
		linkToItemListener = new ItemListener()
		{
			public void itemStateChanged(ItemEvent ie)
			{
				if (cursource != null)
				{
					int to = new Integer(((CheckboxMenuItem) ie.getSource()).getActionCommand()).intValue();
					if (curlink != null)
						curlink.setTo(to);
					else
					{
						curlink = new Link(cursource.getMatrow(), to);
						cursource.setLink(curlink);
					}
				}
				else
				{
					String mode = ((CheckboxMenuItem) ie.getSource()).getActionCommand();
					if (mode.equalsIgnoreCase("pairs"))
					{
						Link lk1, lk2;
						Source s1, s2;
						for (int i = 0; i < sources.size() - 1; i += 2)
						{
							s1 = sources.get(i);
							s2 = sources.get(i + 1);
							lk1 = s1.getLink();
							lk2 = s2.getLink();
							if (lk1 != null)
							{
								lk1.setTo(i + 2);
							}
							else
							{
								s1.setLink(new Link(s1.getMatrow(), i + 2));
							}
							if (lk2 != null)
							{
								lk2.setTo(i + 1);
							}
							else
							{
								s2.setLink(new Link(s2.getMatrow(), i + 1));
							}
							s2.getLink().setInvertedMode(s1.getLink());
						}
					}
					else if (mode.equalsIgnoreCase("all"))
					{
						Source s;
						Link lk;
						float cumul[] = new float[] { 0, 0 };
						for (int i = 0; i < sources.size(); i++)
						{
							s = sources.get(i);
							lk = s.getLink();
							if (i == sources.size() - 1)
							{
								if (lk != null)
								{
									lk.setTo(1);
								}
								else
								{
									s.setLink(new Link(s.getMatrow(), 1));
								}
								s.getLink().setParams(cumul);
							}
							else
							{
								if (lk != null)
								{
									lk.setTo(i + 2);
									cumul[0] -= lk.getParamF(0);
									cumul[1] -= lk.getParamF(1);
								}
								else
								{
									s.setLink(new Link(s.getMatrow(), i + 2));
								}
							}
						}
					}
				}
			}
		};
		linktopop = new PopupMenu("Link To");
		win.add(shapepop);
		win.add(matrixpop);
		win.add(linkmodepop);
		win.add(linktopop);
		ff.max_right = fp;
		ff.min_right = fp;
		anim = new FPSAnimator(12);
		anim.start();
		// MXJ init
		version = 0.1f;
		build = "10/04/09";
		INLET_ASSIST = new String[] { "Commands in", "Preset In / Bang " };
		OUTLET_ASSIST = new String[] { "Output", "Preset Out" };
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		declareAttribute("display_mode");
		declareAttribute("output_mode");
		declareAttribute("display_names");
		declareAttribute("display_shapes");
		declareAttribute("display_infos");
		declareAttribute("display_links");
		declareAttribute("zoom");
		declareAttribute("idlemouse");
		declareAttribute("title", "getTitle", "setTitle");
		declareAttribute("size", null, "setSize");
		declareAttribute("location", null, "setLocation");
		declareAttribute("matrix_direct_val");
		declareAttribute("matrix_exclude_val");
		declareAttribute("matrix_ignore_val");
		declareAttribute("mult_db");
		declareAttribute("mapfile", "getMapfile", "setMapfile");
		declareAttribute("calc_inactiv_boules");

		declareInlets(INLET_TYPES);
		declareOutlets(OUTLET_TYPES);
		setInletAssist(INLET_ASSIST);
		setOutletAssist(OUTLET_ASSIST);
		createInfoOutlet(false);
		
		switch (args.length)
		{
		case 2:
			setMapfile(args[1].toString());
		case 1:
			title = args[0].toString();
		default:
			break;
		}
		updateWin();
		matrix.setName(title + "matrix");
		holoboule_system.add(this);
	}
	

	public void setBouleSourceMode(String mode, int row, int col)
	{
		if (mode.equalsIgnoreCase("Spat"))
			setMatrixMode(row, col, Source.MODE_SPAT);
		else if (mode.equalsIgnoreCase("Direct"))
			setMatrixMode(row, col, Source.MODE_DIRECT);
		else if (mode.equalsIgnoreCase("Exclude"))
			setMatrixMode(row, col, Source.MODE_EXCLUDE);
		else if (mode.equalsIgnoreCase("Ignore"))
			setMatrixMode(row, col, Source.MODE_IGNORE);
	}

	public void setBouleMode(String mode, int row)
	{
		if (mode.equalsIgnoreCase("Spat"))
			for (int i = 0; i < sources.size(); i++)
				setMatrixMode(row, i, Source.MODE_SPAT);
		else if (mode.equalsIgnoreCase("Direct"))
			for (int i = 0; i < sources.size(); i++)
				setMatrixMode(row, i, Source.MODE_DIRECT);
		else if (mode.equalsIgnoreCase("Exclude"))
			for (int i = 0; i < sources.size(); i++)
				setMatrixMode(row, i, Source.MODE_EXCLUDE);
		else if (mode.equalsIgnoreCase("Ignore"))
			for (int i = 0; i < sources.size(); i++)
				setMatrixMode(row, i, Source.MODE_IGNORE);
	}

	public void setSourceMode(String mode, int col)
	{
		if (mode.equalsIgnoreCase("Spat"))
			for (int i = 0; i < boules.size(); i++)
				setMatrixMode(i, col, Source.MODE_SPAT);
		else if (mode.equalsIgnoreCase("Direct"))
			for (int i = 0; i < boules.size(); i++)
				setMatrixMode(i, col, Source.MODE_DIRECT);
		else if (mode.equalsIgnoreCase("Exclude"))
			for (int i = 0; i < boules.size(); i++)
				setMatrixMode(i, col, Source.MODE_EXCLUDE);
		else if (mode.equalsIgnoreCase("Ignore"))
			for (int i = 0; i < boules.size(); i++)
				setMatrixMode(i, col, Source.MODE_IGNORE);
	}

	public void setAllMode(String mode)
	{
		if (mode.equalsIgnoreCase("Spat"))
			for (int i = 0; i < boules.size(); i++)
				for (int j = 0; j < sources.size(); j++)
					setMatrixMode(i, j, Source.MODE_SPAT);
		else if (mode.equalsIgnoreCase("Direct"))
			for (int i = 0; i < boules.size(); i++)
				for (int j = 0; j < sources.size(); j++)
					setMatrixMode(i, j, Source.MODE_DIRECT);
		else if (mode.equalsIgnoreCase("Exclude"))
			for (int i = 0; i < boules.size(); i++)
				for (int j = 0; j < sources.size(); j++)
					setMatrixMode(i, j, Source.MODE_EXCLUDE);
		else if (mode.equalsIgnoreCase("Ignore"))
			for (int i = 0; i < boules.size(); i++)
				for (int j = 0; j < sources.size(); j++)
					setMatrixMode(i, j, Source.MODE_IGNORE);
	}

	public void bouleRenumber()
	{
		for (int i = 0; i < boules.size(); i++)
			boules.get(i).setMatrow(i);
	}

	public void bouleRemove(int i)
	{
		boules.get(i).clear();
		boules.remove(i);
		bouleRenumber();
	}

	private void errmaxboules()
	{
		System.err.println("Maximum boules = " + Boule.MAX_BOULES);
	}

	public void sourceRenumber()
	{
		for (int i = 0; i < sources.size(); i++)
			sources.get(i).setMatrow(i);
	}

	public void sourceRemove(int i)
	{
		Link lk;
		for (Source s : sources)
		{
			lk = s.getLink();
			if (lk != null)
			{
				if (lk.getTo() == i + 1) // this source will be removed
				{
					lk.clear();
					s.setLink(null);
				}
				else if (lk.getTo() > i + 1) // this source num will decrease
					lk.setTo(lk.getTo() - 1);
			}
		}
		sources.get(i).clear();
		sources.remove(i);
		sourceRenumber();
	}

	private void errmaxsources()
	{
		System.err.println("Maximum sources = " + Source.MAX_SOURCES);
	}

	public void dblclick()
	{
		open();
	}

	public void notifyDeleted()
	{
		close();
	}

	public void open()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				win.setVisible(true);
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

	private void updateWin()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				win.setTitle(title);
				win.setSize(width, height + 22);
				win.setLocation(posx, posy);
			}
		});
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

	public void setSize(int w, int h)
	{
		this.width = w;
		this.height = h;
		updateWin();
	}

	public void setLocation(int x, int y)
	{
		this.posx = x;
		this.posy = y;
		updateWin();
	}

	public void clear()
	{
		boules.clear();
		sources.clear();
		initMatrix();
	}

	public void windowActivated(WindowEvent e)
	{
		over_index = -1;
		select_index = -1;
		dragging = false;
		prepadragging = false;
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
	}

	public void windowIconified(WindowEvent e)
	{
	}

	public void windowOpened(WindowEvent e)
	{
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
		// over_index = -1;
		// select_index = -1;
	}

	public void mousePressed(MouseEvent e)
	{
		int ry;
		mods = (e.isMetaDown() ? 1 : 0) + (e.isAltDown() ? 2 : 0) + (e.isShiftDown() ? 4 : 0) + (e.isControlDown() ? 8 : 0);
		mousepos = new Point2D.Float(e.getPoint().x, e.getPoint().y);
		if (display_menu)
		{
			display_menu = false;
			if (mousepos.x < 100)
				treatMenu((int) mousepos.y);
			return;
		}
		if (mousepos.x < 100 && mousepos.y < 20)
		{
			display_menu = true;
			prepadragging = true;
			drag_menu = true;
			drag_menu2 = false;
			if (tf != null && tf.isVisible())
				canvas.remove(tf);
			return;
		}
		worldpos = screen2world(mousepos);
		startmousepos = new Point2D.Float(mousepos.x, mousepos.y);
		startworldpos = new Point2D.Float(worldpos.x, worldpos.y);
		if (idlemouse)
			outlet(0, new Atom[] { Atom.newAtom("mouse"), Atom.newAtom(worldpos.x), Atom.newAtom(worldpos.y) });
		switch (display_mode)
		{
		case DISPMODE_BOULE:
			switch (mods)
			{
			case 0: // N0 MODIFIERS
				if (over_index != -1)
				{
					select_index = over_index;
					curboule = boules.get(select_index);
					if (curboule != null)
					{
						prepadragging = true;
						drag_boule = true;
					}
				}
				break;
			case 1: // META
				if (boules.size() < Boule.MAX_BOULES)
					boules.add(new Boule(boules.size(), worldpos.x, worldpos.y));
				else errmaxboules();
				break;
			case 2: // ALT
				if (over_index != -1)
				{
					select_index = over_index;
					curboule = boules.get(select_index);
					if (curboule != null)
					{
						prepadragging = true;
						refx = curboule.getSizex();
						refy = curboule.getSizey();
						drag_bouleweight = true;
					}
				}
				break;
			case 3: // META + ALT
				display_mode = display_mode == DISPMODE_BOULE ? DISPMODE_SOURCE : DISPMODE_BOULE;
				break;
			case 4: // SHIFT
				if (over_index != -1 && over_index < boules.size())
				{
					bouleRemove(over_index);
					over_index = -1;
				}
			case 6: // ALT + SHIFT
				if (over_index != -1)
				{
					select_index = over_index;
					curboule = boules.get(select_index);
					if (curboule != null)
					{
						prepadragging = true;
						refx = curboule.getSizex();
						refy = curboule.getSizey();
						drag_boulesize = true;
					}
				}
				break;
			case 8: // CTRL
				if (over_index != -1)
				{
					select_index = over_index;
					curboule = boules.get(select_index);
					if (curboule != null)
					{
						for (int i = 0; i < shapepop.getItemCount(); i++)
							((CheckboxMenuItem) shapepop.getItem(i)).setState(i == curboule.getShape());
						shapepop.show(win, e.getX(), e.getY());
					}
				}
				break;
			}
			break;
		case DISPMODE_SOURCE:
			switch (mods)
			{
			case 0: // N0 MODIFIERS
				if (over_index != -1)
				{
					select_index = over_index;
					cursource = sources.get(select_index);
					if (cursource != null)
					{
						prepadragging = true;
						drag_source = true;
					}
				}
				break;
			case 1: // META
				if (sources.size() < Source.MAX_SOURCES)
					sources.add(new Source(sources.size(), worldpos.x, worldpos.y));
				else errmaxsources();
				break;
			case 3: // META + ALT
				display_mode = display_mode == DISPMODE_BOULE ? DISPMODE_SOURCE : DISPMODE_BOULE;
				break;
			case 4: // SHIFT
				if (over_index != -1 && over_index < sources.size())
				{
					sourceRemove(over_index);
					over_index = -1;
				}
				break;
			}
			break;
		case DISPMODE_SOURCELIST:
			if (tf != null && tf.isVisible())
				canvas.remove(tf);
			if (mods == 3)
			{
				display_mode = DISPMODE_SOURCE;
				return;
			}
			ry = (int) mousepos.y - padY + 13 - menu_row_height;
			if (ry > menu_row_height)
			{
				row_num = (int) ((ry - menu_row_height) / (float) menu_row_height);
				if (row_num < sources.size())
				{
					cursource = sources.get(row_num);
					if (mousepos.x < padX - 25)
					{
						if (JOptionPane.showConfirmDialog(win, "Delete source " + (row_num + 1) + " " + cursource.dispName() + " ?", "Delete source", JOptionPane.OK_CANCEL_OPTION) == 0)
						{
							sourceRemove(row_num);
							cursource = null;
						}
						return;
					}
					else if (mousepos.x < padX + 5)
					{
						if (mods == 1)
						{
							cursource.setOn(!cursource.isOn());
							return;
						}
						Color c = JColorChooser.showDialog(win, "Choose color for source " + (row_num + 1) + " " + cursource.dispName(), cursource.getJColor());
						if (c != null)
							cursource.setJColor(c);
						return;
					}
					else if (mousepos.x < 3 * padX + 5)
					{
						col_num = 0;
					}
					else
					{
						col_num = (int) ((mousepos.x - 5) / padX) - 2;
					}
					switch (col_num)
					{
					case 0:
						createJTextField(padX, padY - 10 + (row_num + 2) * menu_row_height, 2 * padX, menu_row_height, cursource.getName());
						tf.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent ae)
							{
								cursource.setName(((JTextField) ae.getSource()).getText());
								tf.setVisible(false);
								canvas.remove(tf);
							}
						});
						canvas.add(tf);
						canvas.validate();
						break;
					default:
						createJTextField((col_num + 2) * padX, padY - 10 + (row_num + 2) * menu_row_height, padX, menu_row_height, cursource.getInfoString(col_num));
						tf.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent ae)
							{
								cursource.setInfoString(col_num, ((JTextField) ae.getSource()).getText());
								tf.setVisible(false);
								canvas.remove(tf);
							}
						});
						canvas.add(tf);
						canvas.validate();
						break;
					}
				}
				else if (row_num == sources.size())
				{
					if (sources.size() < Source.MAX_SOURCES)
						sources.add(new Source(sources.size()));
					else errmaxsources();
				}
			}
			else if (ry > 0)
			{
				cursource = null;
				if (mousepos.x < padX - 25)
				{
					if (JOptionPane.showConfirmDialog(win, "Delete all sources ?", "Delete source", JOptionPane.OK_CANCEL_OPTION) == 0)
					{
						sources.clear();
						Source.clearAll();
					}
					return;
				}
				else if (mousepos.x < padX + 5)
				{
					if (mods == 1)
					{
						for (Source s : sources)
							s.setOn(!s.isOn());
						return;
					}
					Color c = JColorChooser.showDialog(win, "Choose color for all sources", new Color(0.5f, 0.5f, 0.5f, 0.5f));
					if (c != null)
						for (Source s : sources)
							s.setJColor(c);
					return;
				}
				else if (mousepos.x < 3 * padX + 5)
				{
					col_num = 0;
				}
				else
				{
					col_num = (int) ((mousepos.x - 5) / padX) - 2;
				}
				switch (col_num)
				{
				case 0:
					createJTextField(padX, padY - 10 + menu_row_height, 2 * padX, menu_row_height, "none");
					tf.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent ae)
						{
							setSourceName(((JTextField) ae.getSource()).getText());
							tf.setVisible(false);
							canvas.remove(tf);
						}
					});
					canvas.add(tf);
					canvas.validate();
					break;
				default:
					createJTextField((col_num + 2) * padX, padY - 10 + menu_row_height, padX, menu_row_height, "0.0");
					tf.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent ae)
						{
							for (Source s : sources)
								s.setInfoString(col_num, ((JTextField) ae.getSource()).getText());
							tf.setVisible(false);
							canvas.remove(tf);
						}
					});
					canvas.add(tf);
					canvas.validate();
					break;
				}
			}
			break;
		case DISPMODE_BOULELIST:
			if (tf != null && tf.isVisible())
				canvas.remove(tf);
			if (mods == 3)
			{
				display_mode = DISPMODE_SOURCE;
				return;
			}
			ry = (int) mousepos.y - padY + 13 - menu_row_height;
			if (ry > menu_row_height)
			{
				row_num = (int) ((ry - menu_row_height) / (float) menu_row_height);
				if (row_num < boules.size())
				{
					curboule = boules.get(row_num);
					if (mousepos.x < padX - 25)
					{
						if (JOptionPane.showConfirmDialog(win, "Delete boule " + (row_num + 1) + " " + curboule.dispName() + " ?", "Delete boule", JOptionPane.OK_CANCEL_OPTION) == 0)
						{
							bouleRemove(row_num);
							curboule = null;
						}
						return;
					}
					else if (mousepos.x < padX + 5)
					{
						if (mods == 1)
						{
							curboule.setOn(!curboule.isOn());
							return;
						}
						Color c = JColorChooser.showDialog(win, "Choose color for boule " + (row_num + 1) + " " + curboule.dispName(), curboule.getJColor());
						if (c != null)
							curboule.setJColor(c);
						return;
					}
					else if (mousepos.x < 3 * padX + 5)
					{
						col_num = 0;
					}
					else
					{
						col_num = (int) ((mousepos.x - 5) / padX) - 2;
					}
					switch (col_num)
					{
					case 0:
						createJTextField(padX, padY - 10 + (row_num + 2) * menu_row_height, 2 * padX, menu_row_height, curboule.getName());
						tf.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent ae)
							{
								curboule.setName(((JTextField) ae.getSource()).getText());
								tf.setVisible(false);
								canvas.remove(tf);
							}
						});
						canvas.add(tf);
						canvas.validate();
						break;
					case 7:
						for (int i = 0; i < shapepop.getItemCount(); i++)
							((CheckboxMenuItem) shapepop.getItem(i)).setState(i == curboule.getShape());
						shapepop.show(win, (col_num + 2) * padX, padY - 10 + (row_num + 3) * menu_row_height);
						break;
					default:
						createJTextField((col_num + 2) * padX, padY - 10 + (row_num + 2) * menu_row_height, padX, menu_row_height, curboule.getInfoString(col_num));
						tf.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent ae)
							{
								curboule.setInfoString(col_num, ((JTextField) ae.getSource()).getText());
								tf.setVisible(false);
								canvas.remove(tf);
							}
						});
						canvas.add(tf);
						canvas.validate();
						break;
					}
				}
				else if (row_num == boules.size())
				{
					if (boules.size() < Boule.MAX_BOULES)
						boules.add(new Boule(boules.size()));
					else errmaxboules();
				}
			}
			else if (ry > 0)
			{
				// header
				curboule = null;
				if (mousepos.x < padX - 25)
				{
					if (JOptionPane.showConfirmDialog(win, "Delete all boules ?", "Delete boule", JOptionPane.OK_CANCEL_OPTION) == 0)
					{
						boules.clear();
						Boule.clearAll();
					}
					return;
				}
				else if (mousepos.x < padX + 5)
				{
					if (mods == 1)
					{
						for (Boule _b : boules)
							_b.setOn(!_b.isOn());
						return;
					}
					Color c = JColorChooser.showDialog(win, "Choose color for all boules", Color.gray);
					if (c != null)
						for (Boule _b : boules)
							_b.setJColor(c);
					return;
				}
				else if (mousepos.x < 3 * padX + 5)
				{
					col_num = 0;
				}
				else
				{
					col_num = (int) ((mousepos.x - 5) / padX) - 2;
				}
				switch (col_num)
				{
				case 0:
					createJTextField(padX, padY - 10 + menu_row_height, 2 * padX, menu_row_height, "none");
					tf.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent ae)
						{
							setBouleName(((JTextField) ae.getSource()).getText());
							tf.setVisible(false);
							canvas.remove(tf);
						}
					});
					canvas.add(tf);
					canvas.validate();
					break;
				case 7:
					for (int i = 0; i < shapepop.getItemCount(); i++)
						((CheckboxMenuItem) shapepop.getItem(i)).setState(false);
					shapepop.show(win, (col_num + 2) * padX, padY - 10 + 2 * menu_row_height);
					break;
				default:
					createJTextField((col_num + 2) * padX, padY - 10 + menu_row_height, padX, menu_row_height, "0.0");
					tf.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent ae)
						{
							for (Boule _b : boules)
								_b.setInfoString(col_num, ((JTextField) ae.getSource()).getText());
							tf.setVisible(false);
							canvas.remove(tf);
						}
					});
					canvas.add(tf);
					canvas.validate();
					break;
				}
			}
			else if (row_num == boules.size())
			{
				if (boules.size() < Boule.MAX_BOULES)
					boules.add(new Boule(boules.size()));
				else errmaxboules();
			}
			break;
		case DISPMODE_MATRIX:
			if (tf != null && tf.isVisible())
				canvas.remove(tf);
			if (mods == 3)
			{
				display_mode = DISPMODE_SOURCE;
				return;
			}
			ry = (int) mousepos.y - padY + 13 - menu_row_height;
			if (ry > menu_row_height)
			{
				row_num = (int) ((ry - menu_row_height) / (float) menu_row_height);
				if (row_num < boules.size())
				{
					if (mousepos.x > padX + 5)
					{
						// cell
						col_num = (int) ((mousepos.x - padX + 5) / padX2);
						if (col_num < sources.size())
						{
							matrow = row_num;
							matcol = col_num;
							for (int i = 0; i < matrixpop.getItemCount(); i++)
								((CheckboxMenuItem) matrixpop.getItem(i)).setState(i == getMatrixMode(matrow, matcol));
							matrixpop.show(win, padX + 5 + col_num * padX2, padY + (row_num + 3) * menu_row_height);
						}
					}
					else
					{
						// row
						matrow = row_num;
						matcol = -1;
						for (int i = 0; i < matrixpop.getItemCount(); i++)
							((CheckboxMenuItem) matrixpop.getItem(i)).setState(false);
						matrixpop.show(win, 5, padY + (row_num + 3) * menu_row_height);
					}
				}
			}
			else if (ry > 0)
			{
				if (mousepos.x > padX + 5)
				{
					// coll
					col_num = (int) ((mousepos.x - padX + 5) / padX2);
					if (col_num < sources.size())
					{
						matrow = -1;
						matcol = col_num;
						for (int i = 0; i < matrixpop.getItemCount(); i++)
							((CheckboxMenuItem) matrixpop.getItem(i)).setState(false);
						matrixpop.show(win, padX + 5 + col_num * padX2, padY + 2 * menu_row_height);
					}
				}
				else
				{
					// all
					matrow = -1;
					matcol = -1;
					for (int i = 0; i < matrixpop.getItemCount(); i++)
						((CheckboxMenuItem) matrixpop.getItem(i)).setState(false);
					matrixpop.show(win, 5, padY + 2 * menu_row_height);
				}
			}
			break;
		case DISPMODE_MULT:
			if (tf != null && tf.isVisible())
				canvas.remove(tf);
			if (mods == 3)
			{
				display_mode = DISPMODE_SOURCE;
				return;
			}
			ry = (int) mousepos.y - padY + 13 - menu_row_height;
			if (ry > menu_row_height)
			{
				row_num = (int) ((ry - menu_row_height) / (float) menu_row_height);
				if (row_num < boules.size())
				{
					if (mousepos.x > padX + 5)
					{
						// cell
						col_num = (int) ((mousepos.x - padX + 5) / padX2);
						if (col_num < sources.size())
						{
							matrow = row_num;
							matcol = col_num;
							float val = getMatrixMult(matrow, matcol);
							createJTextField(padX + col_num * padX2, padY + 5 + (row_num + 1) * menu_row_height, padX2, menu_row_height, Boule.ff.format(mult_db ? atodb(val) : val));
							tf.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent ae)
								{
									try
									{
										float gval = new Float(((JTextField) ae.getSource()).getText()).floatValue();
										setMatrixMult(matrow, matcol, gval);
									}
									catch (NumberFormatException nfe)
									{
									}
									tf.setVisible(false);
									canvas.remove(tf);
								}
							});
							canvas.add(tf);
							canvas.validate();
						}
					}
					else
					{
						// row
						matrow = row_num;
						matcol = -1;
						createJTextField(1, padY + 5 + (row_num + 1) * menu_row_height, padX2, menu_row_height, Boule.ff.format(mult_db ? 0 : 1));
						tf.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent ae)
							{
								try
								{
									float gval = new Float(((JTextField) ae.getSource()).getText()).floatValue();
									for (int i = 0; i < sources.size(); i++)
										setMatrixMult(matrow, i, gval);
								}
								catch (NumberFormatException nfe)
								{
								}
								tf.setVisible(false);
								canvas.remove(tf);
							}
						});
						canvas.add(tf);
						canvas.validate();
					}
				}
			}
			else if (ry > 0)
			{
				// col
				if (mousepos.x > padX + 5)
				{
					col_num = (int) ((mousepos.x - padX + 5) / padX2);
					if (col_num < sources.size())
					{
						matrow = -1;
						matcol = col_num;
						createJTextField(padX + col_num * padX2, padY + 5, padX2, menu_row_height, Boule.ff.format(mult_db ? 0 : 1));
						tf.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent ae)
							{
								try
								{
									float gval = new Float(((JTextField) ae.getSource()).getText()).floatValue();
									for (int i = 0; i < boules.size(); i++)
										setMatrixMult(i, matcol, gval);
								}
								catch (NumberFormatException nfe)
								{
								}
								tf.setVisible(false);
								canvas.remove(tf);
							}
						});
						canvas.add(tf);
						canvas.validate();
					}
				}
				else
				{
					// all
					matrow = -1;
					matcol = -1;
					createJTextField(1, padY + 5, padX2, menu_row_height, Boule.ff.format(mult_db ? 0 : 1));
					tf.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent ae)
						{
							try
							{
								float gval = new Float(((JTextField) ae.getSource()).getText()).floatValue();
								for (int i = 0; i < boules.size(); i++)
									for (int j = 0; j < sources.size(); j++)
										setMatrixMult(i, j, gval);
							}
							catch (NumberFormatException nfe)
							{
							}
							tf.setVisible(false);
							canvas.remove(tf);
						}
					});
					canvas.add(tf);
					canvas.validate();
				}
			}
			break;
		case DISPMODE_SOURCELINKS:
			if (tf != null && tf.isVisible())
				canvas.remove(tf);
			if (mods == 3)
			{
				display_mode = DISPMODE_SOURCE;
				return;
			}
			ry = (int) mousepos.y - padY + 13 - menu_row_height;
			if (ry > menu_row_height)
			{
				row_num = (int) ((ry - menu_row_height) / (float) menu_row_height);
				if (row_num < sources.size())
				{
					cursource = sources.get(row_num);
					curlink = cursource.getLink();
					if (mousepos.x < padX - 25)
					{
						if (JOptionPane.showConfirmDialog(win, "Clear source link " + (row_num + 1) + " " + cursource.dispName() + " ?", "Clear link", JOptionPane.OK_CANCEL_OPTION) == 0)
						{
							curlink.clear();
							cursource.setLink(null);
							curlink = null;
						}
						return;
					}
					else if (mousepos.x > padX + 5 + padX3)
					{
						col_num = (int) ((mousepos.x - padX - 5 - padX3) / padX3);
					}
					else
					{
						return;
					}
					switch (col_num)
					{
					case 0:
						// LINK TO
						Source ss;
						linktopop.removeAll();
						for (int toi = 0; toi < sources.size(); toi++)
						{
							ss = sources.get(toi);
							if (toi != row_num)
							{
								CheckboxMenuItem m = new CheckboxMenuItem(ss.dispName2(toi + 1));
								if (curlink != null && curlink.getTo() - 1 == toi)
									m.setState(true);
								m.setActionCommand((toi + 1) + "");
								m.addItemListener(linkToItemListener);
								linktopop.add(m);
							}
						}
						linktopop.show(win, padX + 5 + padX3, padY + (row_num + 3) * menu_row_height);
						break;
					case 1:
						// LINK MODE
						if (curlink != null)
						{
							for (int i = 0; i < linkmodepop.getItemCount(); i++)
								((CheckboxMenuItem) linkmodepop.getItem(i)).setState(curlink.getMode() == i);
							linkmodepop.show(win, padX + 5 + 2 * padX3, padY + (row_num + 3) * menu_row_height);
						}
						break;
					default:
						// PARAMS
						if (curlink != null)
						{
							param_num = col_num - 2;
							if (param_num < curlink.getParams().length)
							{
								createJTextField(padX + (col_num + 1) * padX3, padY - 10 + (row_num + 2) * menu_row_height, padX3, menu_row_height, curlink.getParam(param_num));
								tf.addActionListener(new ActionListener()
								{
									public void actionPerformed(ActionEvent ae)
									{
										curlink.setParam(param_num, ((JTextField) ae.getSource()).getText());
										tf.setVisible(false);
										canvas.remove(tf);
									}
								});
								canvas.add(tf);
								canvas.validate();
							}
						}
						break;
					}
				}
			}
			else if (ry > 0)
			{
				cursource = null;
				curlink = null;
				if (mousepos.x < padX - 25)
				{
					if (JOptionPane.showConfirmDialog(win, "Clear all source's links ?", "Clear link", JOptionPane.OK_CANCEL_OPTION) == 0)
					{
						for (Source s : sources)
							s.setLink(null);
						Link.clearAll();
					}
					return;
				}
				else if (mousepos.x > padX + 5 + padX3)
				{
					col_num = (int) ((mousepos.x - padX - 5 - padX3) / padX3);
				}
				else
				{
					return;
				}
				switch (col_num)
				{
				case 0:
					// LINK TO
					linktopop.removeAll();
					CheckboxMenuItem m = new CheckboxMenuItem("Link pairs");
					m.setActionCommand("pairs");
					m.addItemListener(linkToItemListener);
					linktopop.add(m);
					CheckboxMenuItem m2 = new CheckboxMenuItem("Link all");
					m2.setActionCommand("all");
					m2.addItemListener(linkToItemListener);
					linktopop.add(m2);
					linktopop.show(win, padX + 5 + padX3, padY + 2 * menu_row_height);
					break;
				case 1:
					// LINK MODE
					for (int i = 0; i < linkmodepop.getItemCount(); i++)
						((CheckboxMenuItem) linkmodepop.getItem(i)).setState(false);
					linkmodepop.show(win, padX + 5 + 2 * padX3, padY + 2 * menu_row_height);
					break;
				default:
					// PARAMS
					param_num = col_num - 2;
					createJTextField(padX + (col_num + 1) * padX3, padY - 10 + menu_row_height, padX3, menu_row_height, "0.0");
					tf.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent ae)
						{
							Link lk;
							for (Source s : sources)
							{
								lk = s.getLink();
								if (lk != null && param_num < lk.getParams().length)
									lk.setParam(param_num, ((JTextField) ae.getSource()).getText());
							}
							tf.setVisible(false);
							canvas.remove(tf);
						}
					});
					canvas.add(tf);
					canvas.validate();
					break;
				}
			}
			break;
		default:
			break;
		}
	}

	private void createJTextField(int XX, int YY, int WW, int HH, String txt)
	{
		tf = new JTextField();
		tf.setFont(ffont);
		tf.setText(txt);
		tf.setBounds(XX, YY, WW, HH);
		tf.setVisible(true);
		tf.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent e)
			{
				tf.selectAll();
			}
		});
		tf.requestFocus();
		tf.selectAll();
	}

	private void treatMenu(int y)
	{
		int menu = y / menu_row_height - 1;
		switch (menu)
		{
		case 8:
			MaxSystem.deferLow(new Executable()
			{
				public void execute()
				{
					write();
				}
			});
			break;
		case 7:
			MaxSystem.deferLow(new Executable()
			{
				public void execute()
				{
					read();
				}
			});
			break;
		case 6:
		case 5:
		case 4:
		case 3:
		case 2:
		case 1:
		case 0:
			display_mode = menu;
			break;
		default:
			display_mode = 0;
			break;
		}
		// display_mode = clip((y / menu_row_height) - 1, 0, 6);
		if (tf != null && tf.isVisible())
			canvas.remove(tf);
	}

	public void mouseReleased(MouseEvent e)
	{
		if (drag_menu2)
		{
			display_menu = false;
			if (e.getX() < 100)
				treatMenu(e.getY());
		}
		dragging = false;
		prepadragging = false;
		drag_boule = false;
		drag_source = false;
		drag_boulesize = false;
		drag_bouleweight = false;
		drag_menu = false;
		drag_menu2 = false;
		helper_str = "";
	}

	public void mouseDragged(MouseEvent e)
	{
		oldmousepos = new Point2D.Float(mousepos.x, mousepos.y);
		oldworldpos = new Point2D.Float(worldpos.x, worldpos.y);
		mousepos = new Point2D.Float(e.getPoint().x, e.getPoint().y);
		worldpos = screen2world(mousepos);
		if (idlemouse)
			outlet(0, new Atom[] { Atom.newAtom("mouse"), Atom.newAtom(worldpos.x), Atom.newAtom(worldpos.y) });
		dx = worldpos.x - oldworldpos.x;
		dy = worldpos.y - oldworldpos.y;
		if (prepadragging)
		{
			prepadragging = false;
			dragging = true;
		}
		else if (dragging)
		{
			if (drag_boule)
			{
				helper_str = " " + (select_index + 1) + "  -  ";
				curboule.move(worldpos.x, worldpos.y);
				helper_str += "pos : " + ff.format(worldpos.x) + " " + ff.format(worldpos.y);
				output(curboule, select_index + 1);
			}
			else if (drag_bouleweight)
			{
				helper_str = " " + (select_index + 1) + "  -  ";
				float dt = (float) Math.exp(worldpos.y - startworldpos.y);
				curboule.scale(dt * refx, dt * refy);
				helper_str += "size : " + ff.format(curboule.getSizex()) + " " + ff.format(curboule.getSizey());
				output(curboule, select_index + 1);
			}
			else if (drag_boulesize)
			{
				helper_str = " " + (select_index + 1) + "  -  ";
				curboule.scale((float) Math.exp(worldpos.x - startworldpos.x) * refx, (float) Math.exp(worldpos.y - startworldpos.y) * refy);
				helper_str += "size : " + ff.format(curboule.getSizex()) + " " + ff.format(curboule.getSizey());
				output(curboule, select_index + 1);
			}
			else if (drag_source)
			{
				helper_str = " " + (select_index + 1) + "  -  ";
				Link l = cursource.move(worldpos.x, worldpos.y);
				movedsources.clear();
				movedsources_index.clear();
				movedsources.add(cursource);
				movedsources_index.add(select_index + 1);
				moveLink(l, cursource);
				helper_str += "pos : " + ff.format(worldpos.x) + " " + ff.format(worldpos.y);
				for (int i = 0; i < movedsources.size(); i++)
					output(movedsources.get(i), movedsources_index.get(i));
			}
			else if (drag_menu)
			{
				drag_menu2 = true;
			}
		}
	}

	private Link moveLink(Link l, Source s)
	{
		if (l != null && l.getTo() != -1 && l.getMode() != Link.LINK_FREE)
		{
			Source destsource = sources.get(l.getTo() - 1);
			if (movedsources.contains(destsource))
				return null;
			movedsources.add(destsource);
			movedsources_index.add(l.getTo());
			switch (l.getMode())
			{
			case Link.LINK_CENTRAL:
				return moveLink(destsource.move(-s.getX(), -s.getY(), -s.getZ()), destsource);
			case Link.LINK_HORIZONTAL:
				return moveLink(destsource.move(s.getX(), -s.getY(), s.getZ()), destsource);
			case Link.LINK_VERTICAL:
				return moveLink(destsource.move(-s.getX(), s.getY(), s.getZ()), destsource);
			case Link.LINK_DELTA:
				return moveLink(destsource.move(s.getX() + l.getParams()[0], s.getY() + l.getParams()[1], s.getZ() + (l.getParams().length == 3 ? l.getParams()[2] : 0)), destsource);
			case Link.LINK_DELTAPOL:
				Point2D.Float dt = Link.car2pol(s.getPoint());
				Point2D.Float xy = Link.pol2car(dt.x + l.getParams()[0], dt.y + l.getParams()[1]);
				return moveLink(destsource.move(xy.x, xy.y, s.getZ() + +(l.getParams().length == 3 ? l.getParams()[2] : 0)), destsource);
			}
		}
		return null;
	}

	public void mouseMoved(MouseEvent e)
	{
		oldmousepos = new Point2D.Float(mousepos.x, mousepos.y);
		mousepos = new Point2D.Float(e.getPoint().x, e.getPoint().y);
		worldpos = screen2world(mousepos);
		if (idlemouse)
			outlet(0, new Atom[] { Atom.newAtom("mouse"), Atom.newAtom(worldpos.x), Atom.newAtom(worldpos.y) });
	}

	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
		{
			zoom += e.getUnitsToScroll() * e.getScrollAmount() / 500f;
			zoom = clip(zoom, 0.1f, 10f);
			helper_str = "  -  zoom : " + ff.format(zoom);
		}
	}

	public void keyPressed(KeyEvent e)
	{
		if (e.isMetaDown())
		{
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_W:
				close();
				break;
			}
		}
		else
		{
		}
	}

	public void keyReleased(KeyEvent e)
	{
	}

	public void keyTyped(KeyEvent e)
	{
	}

	// RENDERING
	public void display(GLAutoDrawable drawable)
	{
		try
		{
			if (!win.isVisible())
				return;
			gl = drawable.getGL();
			if (display_mode == DISPMODE_BOULE)
				gl.glClearColor(bgcolor_boule[0], bgcolor_boule[1], bgcolor_boule[2], bgcolor_boule[3]);
			else gl.glClearColor(bgcolor_source[0], bgcolor_source[1], bgcolor_source[2], bgcolor_source[3]);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT);
			gl.glLoadIdentity();
			gl.glViewport(0, 0, width, height);
			gl.glLoadIdentity();
			if (width >= height)
			{
				ratio = width / (float) height;
				glu.gluOrtho2D(-zoom * ratio, zoom * ratio, -zoom, zoom);
			}
			else
			{
				ratio = height / (float) width;
				glu.gluOrtho2D(-zoom, zoom, -zoom * ratio, zoom * ratio);
			}
			gl.glMatrixMode(GL.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPolygonMode(GL.GL_FRONT, GL.GL_FILL);
			if (!dragging)
				getUmouse();
			draw(true);
			gl.glLoadIdentity();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void getUmouse()
	{
		int hits = 0;
		int[] viewport = new int[4];
		selectBuf = BufferUtil.newIntBuffer(512);// ByteBuffer.allocateDirect(2048).asIntBuffer();
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
		gl.glSelectBuffer(selectBuf.capacity(), selectBuf);
		gl.glRenderMode(GL.GL_SELECT);
		gl.glInitNames();
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPushName(-1);
		gl.glPushMatrix();
		gl.glLoadIdentity(); // reset the proj. matrix
		// !!! leave gluPickMatrix after glloadidentity
		/* create MOUSE_SELECT_SIZExMOUSE_SELECT_SIZE pixel picking region near cursor location */
		double my = height - mousepos.y;
		glu.gluPickMatrix(mousepos.x, my, MOUSE_SELECT_SIZE, MOUSE_SELECT_SIZE, viewport, 0);
		// !!! leave gluOrtho after glupickmatrix
		// glu.gluOrtho2D(0,width,0,height);
		if (width >= height)
		{
			ratio = width / (float) height;
			glu.gluOrtho2D(-zoom * ratio, zoom * ratio, -zoom, zoom);
		}
		else
		{
			ratio = height / (float) width;
			glu.gluOrtho2D(-zoom, zoom, -zoom * ratio, zoom * ratio);
		}
		gl.glMatrixMode(GL.GL_PROJECTION);
		draw(false);
		gl.glPopMatrix();
		hits = gl.glRenderMode(GL.GL_RENDER);
		if (hits == 0)
		{
			over_index = -1;
			return;
		}
		int offset = 0;
		int names = -1;
		for (int i = 0; i < hits; i++)
		{
			names = selectBuf.get(offset);
			offset++;
			offset++;
			offset++;
			for (int j = 0; j < names; j++)
			{
				if (j == (names - 1))
				{
					over_index = selectBuf.get(offset);
				}
				offset++;
			}
		}
	}

	private void draw(boolean render)
	{
		if (render && display_links)
			drawLinks();
		for (int c = 0; c < boules.size(); c++)
			drawSphere(boules.get(c));
		for (int c = 0; c < boules.size(); c++)
			draw(boules.get(c), render, c);
		for (int c = 0; c < sources.size(); c++)
			draw(sources.get(c), render, c);
		if ((display_mode != DISPMODE_BOULE && display_mode != DISPMODE_SOURCE) || display_menu)
		{
			gl.glColor4fv(bginfocolor, 0);
			gl.glRectf(-10, -10, 10, 10);
		}
		if (render && display_infos)
			drawInfos();
	}

	private void drawInfos()
	{
		int py;
		if (display_menu)
		{
			font.beginRendering(width, height);
			font.setColor(fontcolor[0], fontcolor[1], fontcolor[2], fontcolor[3]);
			py = height - 13;
			font.draw("� Mode", 5, py);
			py -= menu_row_height;
			font.draw("o Source", 5, py - DISPMODE_SOURCE * menu_row_height);
			font.draw("o List of sources", 5, py - DISPMODE_SOURCELIST * menu_row_height);
			font.draw("o Links", 5, py - DISPMODE_SOURCELINKS * menu_row_height);
			font.draw("o Boule", 5, py - DISPMODE_BOULE * menu_row_height);
			font.draw("o List of boules", 5, py - DISPMODE_BOULELIST * menu_row_height);
			font.draw("o Matrix", 5, py - DISPMODE_MATRIX * menu_row_height);
			font.draw("o Matrix *", 5, py - DISPMODE_MULT * menu_row_height);
			font.draw("� Read Map", 5, py - (DISPMODE_MULT + 1) * menu_row_height);
			font.draw("� Write Map", 5, py - (DISPMODE_MULT + 2) * menu_row_height);
			font.endRendering();
		}
		else
		{
			int i, k, mode;
			float val;
			switch (display_mode)
			{
			case DISPMODE_SOURCE:
				font.beginRendering(width, height);
				font.setColor(fontcolor[0], fontcolor[1], fontcolor[2], fontcolor[3]);
				font.draw("� Source" + helper_str, 5, height - 13);
				font.endRendering();
				break;
			case DISPMODE_BOULE:
				font.beginRendering(width, height);
				font.setColor(fontcolor[0], fontcolor[1], fontcolor[2], fontcolor[3]);
				font.draw("� Boule" + helper_str, 5, height - 13);
				font.endRendering();
				break;
			case DISPMODE_SOURCELIST:
				py = height - padY - menu_row_height;
				font.beginRendering(width, height);
				font.setColor(fontcolor[0], fontcolor[1], fontcolor[2], fontcolor[3]);
				font.draw("� List of source", 5, height - 13);
				font.draw("del", 5, py);
				font.draw("n�", padX - 15, py);
				font.draw("name", padX + 5, py);
				font.draw("x", 3 * padX + 5, py);
				font.draw("y", 4 * padX + 5, py);
				font.draw("z", 5 * padX + 5, py);
				Source s;
				for (i = 0; i < sources.size(); i++)
				{
					s = sources.get(i);
					py = height - padY - (i + 2) * menu_row_height;
					if (s.isOn())
					{
						font.draw("X", 5, py);
						font.setColor(s.getColor()[0], s.getColor()[1], s.getColor()[2], fontcolor[3]);
						font.draw((i + 1) + "", padX - 15, py);
						font.setColor(fontcolor[0], fontcolor[1], fontcolor[2], fontcolor[3]);
						font.draw(s.dispName(), padX + 5, py);
						font.draw(s.dispX(), 3 * padX + 5, py);
						font.draw(s.dispY(), 4 * padX + 5, py);
						font.draw(s.dispZ(), 5 * padX + 5, py);
					}
					else
					{
						font.setColor(inactive_color[0], inactive_color[1], inactive_color[2], inactive_color[3]);
						font.draw("X", 5, py);
						font.draw((i + 1) + "", padX - 15, py);
						font.draw(s.dispName(), padX + 5, py);
						font.draw(s.dispX(), 3 * padX + 5, py);
						font.draw(s.dispY(), 4 * padX + 5, py);
						font.draw(s.dispZ(), 5 * padX + 5, py);
						font.setColor(fontcolor[0], fontcolor[1], fontcolor[2], fontcolor[3]);
					}
				}
				py = height - padY - (sources.size() + 2) * menu_row_height;
				font.draw("+", 5, py);
				font.draw("add source", padX - 15, py);
				font.endRendering();
				break;
			case DISPMODE_BOULELIST:
				py = height - padY - menu_row_height;
				font.beginRendering(width, height);
				font.setColor(fontcolor[0], fontcolor[1], fontcolor[2], fontcolor[3]);
				font.draw("� List of boules", 5, height - 13);
				font.draw("del", 5, py);
				font.draw("n�", padX - 15, py);
				font.draw("name", padX + 5, py);
				font.draw("x", 3 * padX + 5, py);
				font.draw("y", 4 * padX + 5, py);
				font.draw("z", 5 * padX + 5, py);
				font.draw("sizex", 6 * padX + 5, py);
				font.draw("sizey", 7 * padX + 5, py);
				font.draw("sizez", 8 * padX + 5, py);
				font.draw("shape", 9 * padX + 5, py);
				for (i = 0; i < boules.size(); i++)
				{
					b = boules.get(i);
					py = height - padY - (i + 2) * menu_row_height;
					if (b.isOn())
					{
						font.draw("X", 5, py);
						font.setColor(b.getColor()[0], b.getColor()[1], b.getColor()[2], fontcolor[3]);
						font.draw((i + 1) + "", padX - 15, py);
						font.setColor(fontcolor[0], fontcolor[1], fontcolor[2], fontcolor[3]);
						font.draw(b.dispName(), padX + 5, py);
						font.draw(b.dispX(), 3 * padX + 5, py);
						font.draw(b.dispY(), 4 * padX + 5, py);
						font.draw(b.dispZ(), 5 * padX + 5, py);
						font.draw(b.dispSizeX(), 6 * padX + 5, py);
						font.draw(b.dispSizeY(), 7 * padX + 5, py);
						font.draw(b.dispSizeZ(), 8 * padX + 5, py);
						font.draw(b.dispShape(), 9 * padX + 5, py);
					}
					else
					{
						font.setColor(inactive_color[0], inactive_color[1], inactive_color[2], inactive_color[3]);
						font.draw("X", 5, py);
						font.draw((i + 1) + "", padX - 15, py);
						font.draw(b.dispName(), padX + 5, py);
						font.draw(b.dispX(), 3 * padX + 5, py);
						font.draw(b.dispY(), 4 * padX + 5, py);
						font.draw(b.dispZ(), 5 * padX + 5, py);
						font.draw(b.dispSizeX(), 6 * padX + 5, py);
						font.draw(b.dispSizeY(), 7 * padX + 5, py);
						font.draw(b.dispSizeZ(), 8 * padX + 5, py);
						font.draw(b.dispShape(), 9 * padX + 5, py);
						font.setColor(fontcolor[0], fontcolor[1], fontcolor[2], fontcolor[3]);
					}
				}
				py = height - padY - (boules.size() + 2) * menu_row_height;
				font.draw("+", 5, py);
				font.draw("add boule", padX - 15, py);
				font.endRendering();
				break;
			case DISPMODE_MATRIX:
				py = height - padY - menu_row_height;
				font.beginRendering(width, height);
				font.setColor(fontcolor[0], fontcolor[1], fontcolor[2], fontcolor[3]);
				font.draw("� Matrix", 5, height - 13);
				font.draw("bl\\src", 5, py);
				for (k = 0; k < sources.size(); k++)
					font.draw(sources.get(k).dispName2(k + 1), padX + 5 + k * padX2, py);
				for (i = 0; i < boules.size(); i++)
				{
					b = boules.get(i);
					py = height - padY - (i + 2) * menu_row_height;
					font.draw(b.dispName2(i + 1), 5, py);
					for (k = 0; k < sources.size(); k++)
						font.draw(Source.mode_stringi[getMatrixMode(i, k)], padX + 5 + k * padX2, py);
				}
				font.endRendering();
				break;
			case DISPMODE_MULT:
				py = height - padY - menu_row_height;
				font.beginRendering(width, height);
				font.setColor(fontcolor[0], fontcolor[1], fontcolor[2], fontcolor[3]);
				font.draw("� Matrix * " + (mult_db ? "(dB)" : "(lin)"), 5, height - 13);
				font.draw("bl\\src", 5, py);
				for (k = 0; k < sources.size(); k++)
					font.draw(sources.get(k).dispName2(k + 1), padX + 5 + k * padX2, py);
				for (i = 0; i < boules.size(); i++)
				{
					b = boules.get(i);
					py = height - padY - (i + 2) * menu_row_height;
					font.draw(b.dispName2(i + 1), 5, py);
					for (k = 0; k < sources.size(); k++)
					{
						val = getMatrixMult(i, k);
						mode = getMatrixMode(i, k);
						if (mode == Source.MODE_SPAT || mode == Source.MODE_DIRECT)
							font.draw(ff.format(mult_db ? atodb(val) : val), padX + 5 + k * padX2, py);
						else font.draw("-", padX + 5 + k * padX2, py);
					}
				}
				font.endRendering();
				break;
			case DISPMODE_SOURCELINKS:
				py = height - padY - menu_row_height;
				font.beginRendering(width, height);
				font.setColor(fontcolor[0], fontcolor[1], fontcolor[2], fontcolor[3]);
				font.draw("� Links", 5, height - 13);
				font.draw("del", 5, py);
				font.draw("from", padX - 15, py);
				font.draw("to", padX + 5 + padX3, py);
				font.draw("mode", padX + 5 + 2 * padX3, py);
				font.draw("params...", padX + 5 + 3 * padX3, py);
				Source sl;
				Link lk;
				float params[];
				int c,
				to;
				for (i = 0; i < sources.size(); i++)
				{
					sl = sources.get(i);
					py = height - padY - (i + 2) * menu_row_height;
					font.draw("X", 5, py);
					font.draw(sl.dispName2(i + 1), padX - 15, py);
					lk = sl.getLink();
					if (lk != null)
					{
						to = lk.getTo() - 1;
						font.draw(sources.get(to).dispName2(to + 1), padX + 5 + padX3, py);
						if (lk.getTo() != -1)
						{
							font.draw(Link.link_stringi[lk.getMode()], padX + 5 + 2 * padX3, py);
							params = lk.getParams();
							for (int v = 0; v < params.length; v++)
								font.draw(lk.getParam(v), padX + 5 + (v + 3) * padX3, py);
						}
					}
				}
				font.endRendering();
				break;
			}
		}
	}

	private void initMatrix()
	{
		matrix.clear();
		matrix.setall(new float[] { 0, 1, 0, 0, 0 });
	}

	private int getMatrixMode(int i, int k)
	{
		return (int) (matrix.getcell2dFloat(i, k)[PLANE_MATRIX_MODE]);
	}

	private float getMatrixMult(int i, int k)
	{
		return matrix.getcell2dFloat(i, k)[PLANE_MATRIX_MULT];
	}

	private void setMatrixMode(int row, int col, int value)
	{
		matrix.setcell(new int[] { row, col }, PLANE_MATRIX_MODE, (float) value);
	}

	private void setMatrixMult(int row, int col, float value)
	{
		matrix.setcell(new int[] { row, col }, PLANE_MATRIX_MULT, mult_db ? dbtoa(value) : value);
	}

	public float getCell(int row, int col, int plane)
	{
		return matrix.getcell2dFloat(row, col)[plane];
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2)
	{
	}

	public void init(GLAutoDrawable drawable)
	{
		gl = drawable.getGL();
		gl.glViewport(0, 0, width, height);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glPolygonMode(GL.GL_FRONT, GL.GL_FILL);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLineWidth(1);
		anim.add(drawable);
		quad = glu.gluNewQuadric();
		font = new TextRenderer(new Font("Arial", Font.PLAIN, font_size), font_size >= 10, false);
	}

	public void reshape(GLAutoDrawable drawable, int _x, int _y, int w, int h)
	{
		width = w;
		height = h;
	}

	private void draw(Boule _b, boolean render, int num)
	{
		if (!_b.isOn())
			return;
		float[] c = _b.getColor();
		if (render)
		{
			// c[3] = display_mode == DISPMODE_BOULE ? c[3] : sphere_alpha1;
			gl.glColor4fv(num == over_index && display_mode == DISPMODE_BOULE ? selected_color : c, 0);
			gl.glPushMatrix();
			gl.glTranslatef(_b.getX(), _b.getY(), _b.getZ());
			if (display_mode == DISPMODE_BOULE)
				glu.gluSphere(quad, 0.05 * zoom, 36, 36);
			else glu.gluDisk(quad, 0.045 * zoom, 0.05 * zoom, 36, 2);
			font.beginRendering(width, height);
			font.setColor(fontcolor[0], fontcolor[1], fontcolor[2], fontcolor[3]);
			Point2D.Float fpos = world2screen(_b.getPoint());
			font.draw("" + (num + 1), (int) fpos.x - (num > 8 ? 6 : 3), (int) fpos.y - 3);
			if (display_names && !_b.getName().equalsIgnoreCase("none"))
				font.draw(_b.getName(), (int) fpos.x + 20, (int) fpos.y - 3);
			if (display_shapes)
				font.draw(Boule.shape_stringi[_b.getShape()], (int) fpos.x - 25, (int) fpos.y - 13);
			font.endRendering();
			gl.glPopMatrix();
		}
		else if (display_mode == DISPMODE_BOULE)
		{
			gl.glLoadName(num);
			gl.glPushMatrix();
			gl.glTranslatef(_b.getX(), _b.getY(), _b.getZ());
			glu.gluSphere(quad, 0.05, 36, 36);
			gl.glPopMatrix();
		}
	}

	private void drawSphere(Boule _b)
	{
		if(!_b.isOn())
			return;
		float[] c = _b.getColor();
		gl.glPushMatrix();
		gl.glTranslatef(_b.getX(), _b.getY(), _b.getZ());
		gl.glColor4f(c[0], c[1], c[2], display_mode == DISPMODE_BOULE ? sphere_alpha1 : sphere_alpha2);
		gl.glScalef(_b.getSizex(), _b.getSizey(), _b.getSizez());
		glu.gluSphere(quad, 1, 36, 36);
		gl.glPopMatrix();
	}

	private void draw(Source s, boolean render, int num)
	{
		if (!s.isOn())
			return;
		float[] c = s.getColor();
		if (render)
		{
			gl.glColor4fv(num == over_index && display_mode == DISPMODE_SOURCE ? selected_color : c, 0);
			gl.glBegin(GL.GL_LINE_STRIP);
			Point2D.Float p;
			while (!s.getBuffer().isEmpty())
			{
				p = s.getBuffer().pop();
				gl.glVertex2d(p.x, p.y);
			}
			gl.glEnd();
			gl.glPushMatrix();
			gl.glTranslatef(s.getX(), s.getY(), s.getZ());
			gl.glBegin(display_mode == DISPMODE_SOURCE ? GL.GL_POLYGON : GL.GL_LINE_LOOP);
			gl.glVertex2d(-0.05 * zoom, 0);
			gl.glVertex2d(0, 0.05 * zoom);
			gl.glVertex2d(0.05 * zoom, 0);
			gl.glVertex2d(0, -0.05 * zoom);
			gl.glEnd();
			font.beginRendering(width, height);
			font.setColor(fontcolor[0], fontcolor[1], fontcolor[2], fontcolor[3]);
			Point2D.Float fpos = world2screen(s.getPoint());
			font.draw("" + (num + 1), (int) fpos.x - (num > 8 ? 6 : 3), (int) fpos.y - 3);
			if (display_names && !s.getName().equalsIgnoreCase("none"))
				font.draw(s.getName(), (int) fpos.x + 20, (int) fpos.y - 3);
			font.endRendering();
			gl.glPopMatrix();
		}
		else if (display_mode == DISPMODE_SOURCE)
		{
			gl.glLoadName(num);
			gl.glPushMatrix();
			gl.glTranslatef(s.getX(), s.getY(), s.getZ());
			gl.glBegin(GL.GL_POLYGON);
			gl.glVertex2d(-0.05 * zoom, 0);
			gl.glVertex2d(0, 0.05 * zoom);
			gl.glVertex2d(0.05 * zoom, 0);
			gl.glVertex2d(0, -0.05 * zoom);
			gl.glEnd();
			gl.glPopMatrix();
		}
	}

	private void drawLinks()
	{
		gl.glLineWidth(2);
		gl.glBegin(GL.GL_LINES);
		for (Source s : sources)
			drawLink(s);
		gl.glEnd();
		gl.glLineWidth(1);
	}

	private void drawLink(Source s)
	{
		if (!s.isOn())
			return;
		if (s.getLink() != null && s.getLink().getTo() > 0 && s.getLink().getTo() <= sources.size())
		{
			Source dest = sources.get(s.getLink().getTo() - 1);
			if (!dest.isOn())
				return;
			gl.glColor4f(s.getColor()[0], s.getColor()[1], s.getColor()[2], links_alpha);
			gl.glVertex3f(s.getX(), s.getY(), s.getZ());
			gl.glColor4f(dest.getColor()[0], dest.getColor()[1], dest.getColor()[2], links_alpha);
			gl.glVertex3f(dest.getX(), dest.getY(), dest.getZ());
		}
		else
		{
			s.setLink(null);
		}
	}

	// GROUPS
	// BOULES
	public void boule(Atom[] args)
	{
		if (args[0].isString())
		{
			String msg = args[0].toString();
			if (msg.equalsIgnoreCase("add"))
				if (boules.size() < Boule.MAX_BOULES)
					boules.add(Boule.createBoule(Atom.removeFirst(args), boules.size()));
				else errmaxboules();
			else if (msg.equalsIgnoreCase("clear"))
			{
				boules.clear();
				Boule.clearAll();
			}
			else if (msg.equalsIgnoreCase("all"))
			{
				if (args[1].isString() && args[1].toString().equalsIgnoreCase("name"))
					setBouleName(args[2].toString());
				else for (int i = 0; i < boules.size(); i++)
					boules.get(i).anything(Atom.removeFirst(args));
			}
			else
			{
				for (int i = 0; i < boules.size(); i++)
					if (boules.get(i).getName().equalsIgnoreCase(args[0].toString()))
					{
						boules.get(i).anything(Atom.removeFirst(args));
						return;
					}
			}
		}
		else if (args[0].isInt())
		{
			int i = args[0].getInt() - 1;
			while (boules.size() < i)
				if (boules.size() < Boule.MAX_BOULES)
					boules.add(new Boule(boules.size()));
				else errmaxboules();
			if (i < boules.size())
			{
				if (args.length > 1 && args[1].toString().equalsIgnoreCase("remove"))
					bouleRemove(i);
				else boules.get(i).anything(Atom.removeFirst(args));
			}
			else if (boules.size() < Boule.MAX_BOULES)
				boules.add(Boule.createBoule(Atom.removeFirst(args), boules.size()));
			else errmaxboules();
		}
	}

	private void setBouleName(String name)
	{
		if (name.equalsIgnoreCase("none"))
			for (int i = 0; i < boules.size(); i++)
				boules.get(i).setName("none");
		else for (int i = 0; i < boules.size(); i++)
			boules.get(i).setName(name + (i + 1));
	}

	// SOURCES
	public void source(Atom[] args)
	{
		if (args[0].isString())
		{
			String msg = args[0].toString();
			if (msg.equalsIgnoreCase("add"))
				if (sources.size() < Source.MAX_SOURCES)
					sources.add(Source.createSource(Atom.removeFirst(args), sources.size()));
				else errmaxsources();
			else if (msg.equalsIgnoreCase("clear"))
			{
				sources.clear();
				Source.clearAll();
			}
			else if (msg.equalsIgnoreCase("all"))
			{
				if (args[1].isString() && args[1].toString().equalsIgnoreCase("name"))
					setSourceName(args[2].toString());
				else for (int i = 0; i < sources.size(); i++)
					sources.get(i).anything(Atom.removeFirst(args));
			}
			else
			{
				for (int i = 0; i < sources.size(); i++)
					if (sources.get(i).getName().equalsIgnoreCase(args[0].toString()))
					{
						sources.get(i).anything(Atom.removeFirst(args));
						return;
					}
			}
		}
		else if (args[0].isInt())
		{
			int i = args[0].getInt() - 1;
			if(i < 0)
				return;
			while (sources.size() < i)
				if (sources.size() < Source.MAX_SOURCES)
					sources.add(new Source(sources.size()));
				else errmaxsources();
			if (i < sources.size())
			{
				if (args.length > 1 && args[1].toString().equalsIgnoreCase("remove"))
					sourceRemove(i);
				else
				{
					// FIXME HOLOBOULE FILTRER LES SOURCES DEJA EN DEPLACEMENT (SOURIS)
					Source s = sources.get(i);
					Link l = s.anything(Atom.removeFirst(args));
					movedsources.clear();
					movedsources_index.clear();
					movedsources.add(s);
					movedsources_index.add(i + 1);
					moveLink(l, s);
					for (int k = 0; k < movedsources.size(); k++)
						output(movedsources.get(k), movedsources_index.get(k));
				}
			}
			else
			{
				if (sources.size() < Source.MAX_SOURCES)
					sources.add(Source.createSource(Atom.removeFirst(args), sources.size()));
				else errmaxsources();
			}
		}
	}

	private void setSourceName(String name)
	{
		if (name.equalsIgnoreCase("none"))
			for (int i = 0; i < sources.size(); i++)
				sources.get(i).setName("none");
		else for (int i = 0; i < sources.size(); i++)
			sources.get(i).setName(name + (i + 1));
	}

	// PRESETS
	public void bang()
	{
		if (getInlet() == 1)
		{
			presetOut();
		}
	}

	public void presetOut()
	{
		String preset = "set boules";
		for (Boule _b : boules)
		{
			preset += " _b " + _b.out();
		}
		preset += " sources";
		for (Source s : sources)
			preset += " _s " + s.out() + (s.getLink() != null ? " _l " + s.getLink().out() : "");
		outlet(1, Atom.parse(preset));
	}

	public void preset(Atom[] pstargs)
	{
		boules.clear();
		Boule.clearAll();
		sources.clear();
		Source.clearAll();
		String preset = Atom.toOneString(pstargs);
		String[] bs = preset.split("boules")[1].split(" sources");
		// System.out.println("Boules :");
		if (bs.length == 0)
		{
			clear();
			return;
		}
		String[] bbs = bs[0].split(" _b ");
		for (String s : bbs)
			if (!s.equalsIgnoreCase(""))
			{
				// System.out.println("\t\""+s+"\"");
				if (boules.size() < Boule.MAX_BOULES)
					boules.add(Boule.createBoule(s, boules.size()));
				else errmaxboules();
			}
		// System.out.println("Sources : ");
		String[] sss = bs[1].split(" _s ");
		for (String s : sss)
			if (!s.equalsIgnoreCase(""))
			{
				// System.out.println("\t\""+s+"\"");
				if (sources.size() < Source.MAX_SOURCES)
					sources.add(Source.createSource(s, sources.size()));
				else errmaxsources();
			}
	}

	// UTILITIES
	private Point2D.Float screen2world(Point2D.Float p)
	{
		if (width >= height)
			return new Point2D.Float((p.x - width / 2) / width * 2 * zoom * ratio, (height / 2 - p.y) / height * 2 * zoom);
		return new Point2D.Float((p.x - width / 2) / width * 2 * zoom, (height / 2 - p.y) / height * 2 * zoom * ratio);
	}

	private Point2D.Float world2screen(Point2D.Float p)
	{
		if (width >= height)
			return new Point2D.Float(p.x * width / (2 * zoom * ratio) + width / 2, p.y * height / (2 * zoom) + height / 2);
		return new Point2D.Float(p.x * width / (2 * zoom) + width / 2, p.y * height / (2 * zoom * ratio) + height / 2);
	}

	// OUTPUT
	// TODO HOLOBOULE reset boule si supprim�e
	private void output(Boule _b, int num)
	{
		if (!_b.isOn())
			return;
		switch (output_mode)
		{
		case OUTMODE_POSITIONS:
			outlet(0, Atom.parse("boule " + num + " " + _b.out()));
			break;
		}
	}

	private void output(Source s, int num)
	{
		if (!s.isOn())
			return;
		float value = 0;
		float mat_mult_value = 0;
		int mat_mode = 0;
		switch (output_mode)
		{
		case OUTMODE_POSITIONS:
			outlet(0, Atom.parse("source " + num + " " + s.out()));
			break;
		case OUTMODE_MATRIX:
			for (int row = 0; row < boules.size(); row++)
			{
				b = boules.get(row);
				if (b.isOn() || calc_inactiv_boules)
				{
					mat_mode = getMatrixMode(row, num - 1);
					mat_mult_value = getMatrixMult(row, num - 1);
					switch (mat_mode)
					{
					default:
					case Source.MODE_SPAT:
						outmatrix = new Atom[] { Atom.newAtom(num - 1), Atom.newAtom(row), Atom.newAtom(mat_mult_value * calcDistShapeValue(s, b)) };
						outlet(0, outmatrix);
						break;
					case Source.MODE_DIRECT:
						outmatrix = new Atom[] { Atom.newAtom(num - 1), Atom.newAtom(row), Atom.newAtom(mat_mult_value * matrix_direct_val) };
						outlet(0, outmatrix);
						break;
					case Source.MODE_EXCLUDE:
						outmatrix = new Atom[] { Atom.newAtom(num - 1), Atom.newAtom(row), Atom.newAtom(matrix_exclude_val) };
						outlet(0, outmatrix);
						break;
					case Source.MODE_IGNORE:
						break;
					}
				}
			}
			break;
		case OUTMODE_LIST:
			atomvec = new Vector<Atom>();
			atomvec.add(Atom.newAtom(num));
			for (int row = 0; row < boules.size(); row++)
			{
				b = boules.get(row);
				if (b.isOn() || calc_inactiv_boules)
				{
					mat_mode = getMatrixMode(row, num - 1);
					mat_mult_value = getMatrixMult(row, num - 1);
					switch (mat_mode)
					{
					default:
					case Source.MODE_SPAT:
						atomvec.add(Atom.newAtom(mat_mult_value * calcDistShapeValue(s, b)));
						break;
					case Source.MODE_DIRECT:
						atomvec.add(Atom.newAtom(mat_mult_value * matrix_direct_val));
						break;
					case Source.MODE_EXCLUDE:
						atomvec.add(Atom.newAtom(matrix_exclude_val));
						break;
					case Source.MODE_IGNORE:
						atomvec.add(Atom.newAtom(matrix_ignore_val));
						break;
					}
				}
				else
				{
					atomvec.add(Atom.newAtom(matrix_ignore_val));
				}
			}
			outlet(0, atomvec.toArray(new Atom[] {}));
			break;
		case OUTMODE_RECALLMULTI:
			atomvec = new Vector<Atom>();
			atomvec.add(Atom.newAtom(num));
			atomvec.add(Atom.newAtom("recallmulti"));
			for (int row = 0; row < boules.size(); row++)
			{
				b = boules.get(row);
				if (b.isOn() || calc_inactiv_boules)
				{
					mat_mode = getMatrixMode(row, num - 1);
					mat_mult_value = getMatrixMult(row, num - 1);
					switch (mat_mode)
					{
					default:
					case Source.MODE_SPAT:
						value = mat_mult_value * calcDistShapeValue(s, b);
						if (value != 0)
						{
							if (value >= 0.999)
								atomvec.add(Atom.newAtom(row + 1));
							else atomvec.add(Atom.newAtom(row + 1 + value));
						}
						break;
					case Source.MODE_DIRECT:
						atomvec.add(Atom.newAtom(row + 1 + mat_mult_value));
						break;
					case Source.MODE_EXCLUDE:
					case Source.MODE_IGNORE:
						break;
					}
				}
			}
			outlet(0, atomvec.toArray(new Atom[] {}));
			break;
		case OUTMODE_MONOMATRIX:
			for (int row = 0; row < boules.size(); row++)
			{
				b = boules.get(row);
				if (b.isOn() || calc_inactiv_boules)
				{
					mat_mode = getMatrixMode(row, num - 1);
					mat_mult_value = getMatrixMult(row, num - 1);
					switch (mat_mode)
					{
					default:
					case Source.MODE_SPAT:
						outmatrix = new Atom[] { Atom.newAtom(num), Atom.newAtom(0), Atom.newAtom(row), Atom.newAtom(mat_mult_value * calcDistShapeValue(s, b)) };
						outlet(0, outmatrix);
						break;
					case Source.MODE_DIRECT:
						outmatrix = new Atom[] { Atom.newAtom(num), Atom.newAtom(0), Atom.newAtom(row), Atom.newAtom(mat_mult_value * matrix_direct_val) };
						outlet(0, outmatrix);
						break;
					case Source.MODE_EXCLUDE:
						outmatrix = new Atom[] { Atom.newAtom(num), Atom.newAtom(0), Atom.newAtom(row), Atom.newAtom(matrix_exclude_val) };
						outlet(0, outmatrix);
						break;
					case Source.MODE_IGNORE:
						break;
					}
				}
			}
			break;
		case OUTMODE_STEREOMATRIX:
			for (int row = 0; row < boules.size(); row++)
			{
				b = boules.get(row);
				if (b.isOn() || calc_inactiv_boules)
				{
					mat_mode = getMatrixMode(row, num - 1);
					mat_mult_value = getMatrixMult(row, num - 1);
					switch (mat_mode)
					{
					default:
					case Source.MODE_SPAT:
						outmatrix = new Atom[] { Atom.newAtom(num), Atom.newAtom((num - 1) % 2), Atom.newAtom(row), Atom.newAtom(mat_mult_value * calcDistShapeValue(s, b)) };
						outlet(0, outmatrix);
						break;
					case Source.MODE_DIRECT:
						outmatrix = new Atom[] { Atom.newAtom(num), Atom.newAtom((num - 1) % 2), Atom.newAtom(row), Atom.newAtom(mat_mult_value * matrix_direct_val) };
						outlet(0, outmatrix);
						break;
					case Source.MODE_EXCLUDE:
						outmatrix = new Atom[] { Atom.newAtom(num), Atom.newAtom((num - 1) % 2), Atom.newAtom(row), Atom.newAtom(matrix_exclude_val) };
						outlet(0, outmatrix);
						break;
					case Source.MODE_IGNORE:
						break;
					}
				}
			}
			break;
		}
	}

	public void output_source(int num)
	{
		if (between(num, 1, sources.size()))
			if (sources.get(num - 1).isOn())
				output(sources.get(num - 1), num);
	}

	private float calcDistShapeValue(Source s, Boule _b)
	{
		double value = 0;
		double x2 = (s.getX() - _b.getX()) / _b.getSizex();
		double y2 = (s.getY() - _b.getY()) / _b.getSizey();
		double dist = x2 * x2 + y2 * y2;
		if (dist > 1)
			value = 0;
		else switch (_b.getShape())
		{
		case 0: // Gaussian
			value = Math.exp(-5 * dist);
			break;
		case 1: // Linear
			value = 1 - dist;
			break;
		case 2: // Exponential
			value = 1 - Math.sqrt(Math.sqrt(dist));
			break;
		case 3: // Exponential 2
			dist = Math.max(Math.sqrt(dist), MIN_DIST);
			value = MIN_DIST * MIN_DIST / (2. * dist * MIN_VALUE);
			break;
		case 4: // Threshold
			value = 1.;
			break;
		}
		return (float) value;
	}

	// READ/WRITE
	public void read()
	{
		clear();
		Atom[] res = matrix.call("read");
		mapfile = res[0].toString();
		int done = res[1].toInt();
		if (done == 1)
		{
			System.out.print("Reading " + mapfile + " ... ");
			initMatrixFromFile();
			System.out.println("done.");
		}
	}

	public void read(String fn)
	{
		fn = MaxSystem.locateFile(fn);
		if (fn != null)
		{
			clear();
			Atom[] res = matrix.call("read", fn);
			mapfile = res[0].toString();
			int done = res[1].toInt();
			if (done == 1)
			{
				System.out.print("Reading " + mapfile + " ... ");
				initMatrixFromFile();
				System.out.println("done.");
			}
		}
		else
		{
			read();
		}
	}

	private void initMatrixFromFile()
	{
		int row = 0;
		float f = getCell(row, 0, Source.PLANE_SOURCE);
		while (f != Boule.MAGICAL_FLOAT && sources.size() < Source.MAX_SOURCES)
		{
			sources.add(new Source(row, false));
			row++;
			f = getCell(row, 0, Source.PLANE_SOURCE);
		}
		row = 0;
		f = getCell(row, 0, Boule.PLANE_BOULE);
		while (f != Boule.MAGICAL_FLOAT && boules.size() < Boule.MAX_BOULES)
		{
			boules.add(new Boule(row, false));
			row++;
			f = getCell(row, 0, Boule.PLANE_BOULE);
		}
	}

	public void write()
	{
		prepareMatrixForWriting();
		Atom[] res = matrix.call("write");
		
		if(res[0].toString().equalsIgnoreCase("<none>"))
			return;
		
		mapfile = res[0].toString();
		int done = res[1].toInt();
		if (done == 1)
		{
			System.out.println("Writing " + mapfile + " done.");
		}
		else
		{
			System.err.println("Error while writing " + mapfile + ".");
		}//*/
	}

	public void writeagain()
	{
		if (mapfile != null)
		{
			prepareMatrixForWriting();
			if (!mapfile.endsWith(".jxf"))
				mapfile += ".jxf";
			Atom[] res = matrix.call("write", mapfile);
			mapfile = res[0].toString();
			int done = res[1].toInt();
			if (done == 1)
			{
				System.out.println("Writing " + mapfile + " done.");
			}
			else
			{
				System.err.println("Error while writing " + mapfile + ".");
			}
		}
		else
		{
			write();
		}
	}

	public void write(String name)
	{
		if (name != null)
		{
			prepareMatrixForWriting();
			if (!name.endsWith(".jxf"))
				name += ".jxf";
			Atom[] res = matrix.call("write", name);
			mapfile = res[0].toString();
			int done = res[1].toInt();
			if (done == 1)
			{
				System.out.println("Writing " + mapfile + " done.");
			}
			else
			{
				System.err.println("Error while writing " + mapfile + ".");
			}
		}
		else
		{
			write();
		}
	}

	private void prepareMatrixForWriting()
	{
		Boule.setEnd(boules.size());
		Source.setEnd(sources.size());
		for (int i = 0; i < sources.size(); i++)
			if (sources.get(i).getLink() == null)
				Link.clearRow(i);
	}

	public String getMapfile()
	{
		return mapfile;
	}

	public void setMapfile(String filename)
	{
		this.mapfile = filename;
		read(filename);
	}

	// UTILS
	
	protected static int clip(int i, int l, int u)
	{
		int tmp = clip(i, l);
		return clipH(tmp,u);
	}
	protected static int clip(int i, int l)
	{
		return (i < l ? l : i);
	}
	
	protected static int clipH(int i, int u)
	{
		return (i > u ? u : i);
	}
	
	protected static float clip(float i, float l, float u)
	{
		float tmp = clip(i, l);
		return clipH(tmp,u);
	}

	protected static float clip(float f, float l)
	{
		return (f < l ? l : f);
	}
	
	protected static float clipH(float f, float u)
	{
		return (f > u ? u : f);
	}
	
	protected static float dbtoa(float db)
	{
		if(db <= -96)
			return 0;
		return (float)Math.pow(10, db/20f);
	}
	
	protected static float atodb(float a)
	{
		if(a < 0.000017)
			return -96;
		return (float)(20 * Math.log10(a));
	}
	
	protected static boolean between(int i, int l, int u)
	{
		return i >= l && i <= u;
	}

	protected static boolean between(float i, float l, float u)
	{
		return i >= l && i <= u;
	}
	
	protected static int modulo(int i, int mod)
	{
		if(mod == 0)
			return i;
		while (i >= mod)
			i -= mod;
		return i;
	}

	protected static int modulo2(int i, int mod)
	{
		if(mod == 0)
			return i;
		while (i < 0) 
			i += mod;
		while (i >= mod)
			i -= mod;
		return i;
	}
	
	protected static float modulof(float f, float mod)
	{
		if(mod == 0)
			return f;
		while (f >= mod)
			f -= mod;
		return f;
	}

	protected static float modulof2(float f, float mod)
	{
		if(mod == 0)
			return f;
		while (f < 0) 
			f += mod;
		while (f >= mod)
			f -= mod;
		return f;
	}

}