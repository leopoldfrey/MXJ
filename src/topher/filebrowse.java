package topher;

import java.awt.Point;
import java.io.File;
import java.util.HashMap;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;

// TODO BROWSER file filter ?
// TODO BROWSER focus & keys up/down/right/left
public class filebrowse extends MaxObject
{
	private static final String[] INLET_ASSIST = new String[] { "lcd in / message in" };
	private static final String[] OUTLET_ASSIST = new String[] { "connect to lcd", "info out" };
	private int _w = 0;
	private int _h = 0;
	private int _offscreen_w = 0;
	private int _offscreen_h = 0;
	private int _fs = 0;
	private File _root = null;
	private LCDWrapper _lcd = null;
	private TreeNode rootnode = null;
	private boolean minizable = true;
	private boolean prep_dragged_file = false;
	private boolean dragged_file = false;
	private int[] BGCOLOR = new int[] { 255, 255, 255 };
	private int[] FGCOLOR = new int[] { 0, 0, 0 };
	private int[] DRAG_FILE_COLOR = new int[] { 100, 100, 100 };
	private int[] SELFGCOLOR = new int[] { 255, 255, 155 };
	private int[] SCROLLBARCOLOR = new int[] { 255, 255, 255 };
	private int[] SCROLLBGCOLOR = new int[] { 200, 200, 200 };
	private int[] TRIANGLECOLOR = new int[] { 0, 0, 0 };
	private int[] FOLDERCOLOR = new int[] { 255, 255, 55 };
	private static final int ORIGIN_OFFSET_X = 10;
	private static final int ORIGIN_OFFSET_Y = 10;
	private static final int CELL_HEIGHT = 20;
	private static final int FONT_SIZE = 10;
	private int _content_pane_x_offset = 0;
	private int _content_pane_y_offset = 0;
	private boolean _minimized = false;
	private TreeNode selected_node = null;
	private String sel_name = "";
	private String sel_filename = "";
	private Point mousepos = new Point();

	public filebrowse(String root, int width, int height, int minizable)
	{
		declareInlets(new int[] { DataTypes.ALL });
		declareOutlets(new int[] { DataTypes.ALL, DataTypes.ALL });
		setInletAssist(INLET_ASSIST);
		setOutletAssist(OUTLET_ASSIST);
		createInfoOutlet(false);
		String s_root = MaxSystem.maxPathToNativePath(root);
		_root = new File(s_root);
		if (!_root.exists() || !_root.isDirectory())
			bail("(mxj filebrowse) Invalid root directory argument" + s_root);
		_lcd = new LCDWrapper(this);
		_w = width;
		_h = height;
		this.minizable = minizable != 0;
	}

	// bang to initialize
	public void initialize()
	{
		rootnode = new TreeNode(_root);
		_lcd.local(false);
		_lcd.enable_sprites(true);
		_lcd.setSize(_w, _h);
		_lcd.clear();
		rootnode.open = true;
		draw();
	}

	private void draw()
	{
		_lcd.setBRGB(BGCOLOR);
		_lcd.clear();
		_lcd.beginSprite("contentpane");
		_lcd.setFont("Geneva", FONT_SIZE, null);
		_offscreen_h = rendertree(rootnode, ORIGIN_OFFSET_X, ORIGIN_OFFSET_Y);
		_lcd.endSprite();
		_lcd.drawSprite("contentpane", _content_pane_x_offset, _content_pane_y_offset);
		if (_h - _content_pane_y_offset > _offscreen_h && _content_pane_y_offset != 0)
			_offscreen_h = _h - _content_pane_y_offset;
		if (_offscreen_h > _h)
		{
			draw_v_scrollbar();
			v_scrollbar_is_showing = true;
		}
		else
		{
			v_scrollbar_is_showing = false;
			_lcd.hideSprite("vscrollbar");
		}
		if (dragged_file && selected_node != null)
			draw_dragged();
		else _lcd.hideSprite("dragged_file");
		if (!_minimized && minizable)
		{
			draw_minimize();
		}
	}

	private void draw_dir(String name, int x, int y)
	{
		_lcd.setFRGB(TRIANGLECOLOR);
		// draw trinangle
		_lcd.lineSegment(x - 4, y + 1, x, y + 5);
		_lcd.lineSegment(x, y + 5, x - 4, y + 9);
		_lcd.lineSegment(x - 4, y + 9, x - 4, y + 1);
		// draw folder
		// _lcd.setFRGB(FOLDERCOLOR);
		_lcd.setFRGB(FOLDERCOLOR[0] - 45, FOLDERCOLOR[1] - 35, FOLDERCOLOR[2] - 35);
		_lcd.paintRoundRect(x + 4, y, 10, 5, 5, 5);
		_lcd.setFRGB(FGCOLOR);
		_lcd.frameRoundRect(x + 4, y, 10, 5, 5, 5);
		_lcd.setFRGB(FOLDERCOLOR);
		_lcd.paintRoundRect(x + 4, y + 2, 15, 10, 5, 5);
		_lcd.setFRGB(FGCOLOR);
		_lcd.frameRoundRect(x + 4, y + 2, 15, 10, 5, 5);
		// draw folder line
		_lcd.lineSegment(x + 4, y + 2, x + 17, y + 2);
		_lcd.setFRGB(FGCOLOR);
		_lcd.drawString(name, x + 5 + 20, y + 10);
	}

	private final int[] open_folder_vertices = new int[10];

	private void draw_open_dir(String name, int x, int y)
	{
		_lcd.setFRGB(TRIANGLECOLOR);
		// draw trinangle
		_lcd.lineSegment(x - 8, y + 4, x, y + 4);
		_lcd.lineSegment(x, y + 4, x - 4, y + 8);
		_lcd.lineSegment(x - 4, y + 8, x - 8, y + 4);
		// draw folder
		_lcd.setFRGB(FOLDERCOLOR);
		_lcd.paintRoundRect(x + 4, y, 10, 5, 5, 5);
		_lcd.setFRGB(FGCOLOR);
		_lcd.frameRoundRect(x + 4, y, 10, 5, 5, 5);
		_lcd.setFRGB(FOLDERCOLOR);
		_lcd.paintRoundRect(x + 4, y + 2, 15, 10, 5, 5);
		_lcd.setFRGB(FGCOLOR);
		_lcd.frameRoundRect(x + 4, y + 2, 15, 10, 5, 5);
		_lcd.setFRGB(FOLDERCOLOR);
		_lcd.paintRect(x + 5, y + 2, 9, 2);
		// draw folder line starting at bottom left
		open_folder_vertices[0] = x + 4;
		open_folder_vertices[1] = y + 12;
		open_folder_vertices[2] = x + 6;
		open_folder_vertices[3] = y + 4;
		open_folder_vertices[4] = x + 4 + 17;
		open_folder_vertices[5] = y + 4;
		open_folder_vertices[6] = x + 4 + 15;
		open_folder_vertices[7] = y + 12;
		open_folder_vertices[8] = x + 4;
		open_folder_vertices[9] = y + 12;
		_lcd.setFRGB(FOLDERCOLOR[0] - 35, FOLDERCOLOR[1] - 25, FOLDERCOLOR[2] - 25);
		_lcd.paintPoly(open_folder_vertices);
		_lcd.setFRGB(FGCOLOR);
		_lcd.framePoly(open_folder_vertices);
		_lcd.setFRGB(FGCOLOR);
		_lcd.drawString(name, x + 5 + 20, y + 10);
	}

	private void draw_file(String name, int x, int y)
	{
		// draw file
		_lcd.setFRGB(255, 255, 255);
		_lcd.paintRect(x, y, 10, 14);
		// draw black outline
		_lcd.setFRGB(0, 0, 0);
		_lcd.frameRect(x, y, 11, 15);
		_lcd.setFRGB(FGCOLOR);
		_lcd.drawString(name, x + 14, y + 12);
	}

	private void draw_dragged()
	{
		// draw file
		// draw black outline
		int l = selected_node.f.getName().length();
		_lcd.beginSprite("dragged_file");
		_lcd.setFRGB(DRAG_FILE_COLOR);
		_lcd.frameRect(-6, -2, l * FONT_SIZE / 2 + 35, 20);
		_lcd.frameRect(0, 0, 11, 15);
		_lcd.drawString(selected_node.f.getName(), 14, 12);
		_lcd.endSprite();
		_lcd.drawSprite("dragged_file", mousepos.x - 5, mousepos.y - 5);
	}

	private String[] _text_attributes_minimized = new String[] { "bold" };

	private void draw_minimize()
	{
		if (!_minimized)
		{
			_lcd.beginSprite("minimize");
			_lcd.setFRGB(SCROLLBARCOLOR);
			_lcd.frameRect(0, 0, 10, 10);
			// draw minus
			_lcd.setFRGB(FGCOLOR);
			_lcd.lineSegment(2, 4, 7, 4);
			_lcd.endSprite();
			_lcd.drawSprite("minimize", 2, 2);
		}
		else
		{// draw maximize icon
			_lcd.beginSprite("minimize");
			_lcd.setFRGB(BGCOLOR);
			_lcd.paintRect(0, 0, _w, 16);
			// draw nub
			_lcd.setFRGB(SCROLLBARCOLOR);
			_lcd.frameRect(2, 2, 10, 10);
			// draw plus
			_lcd.setFRGB(FGCOLOR);
			_lcd.lineSegment(4, 6, 8, 6);
			_lcd.lineSegment(6, 8, 6, 4);
			// draw root dirname
			_lcd.setFont("Geneva", 10, null);
			_lcd.drawString("Dir: " + rootnode.f.getAbsolutePath(), 14, 10);
			_lcd.endSprite();
			_lcd.drawSprite("minimize", 0, 0);
		}
	}

	private void do_minimize()
	{
		_minimized = true;
		_lcd.hideSprite("contentpane");
		if (v_scrollbar_is_showing)
			_lcd.hideSprite("vscrollbar");
		_lcd.setSize(_w, 14);
		draw_minimize();
	}

	private void do_maximize()
	{
		_minimized = false;
		_lcd.setSize(_w, _h);
		draw();
	}

	private TreeNode get_node_from_click(int x, int y)
	{
		y -= ORIGIN_OFFSET_Y + _content_pane_y_offset;
		while (y % (CELL_HEIGHT) != 0)
			y--;
		// y = ((int)(y / 20)) * 20;
		// post("scaling y from "+y+" to "+(y+10));
		TreeNode t = _nodemap.get(new Integer(y + ORIGIN_OFFSET_Y));
		/*
		 * if(t != null) post("t is "+t.f.getName()); else post("t is null");
		 */
		return t;
	}

	private HashMap<Integer,TreeNode> _nodemap = new HashMap<Integer,TreeNode>();

	// return if we are rendering things out of bounds
	private int rendertree(TreeNode node, int x, int y)
	{
		if (node != null && node.f.isDirectory())
		{
			if (node == selected_node)
			{
				_lcd.setFRGB(SELFGCOLOR);
				_lcd.paintRect(-300, y - 4, 600, 20);
			}
			if (node.open)
				draw_open_dir(node.f.getName(), x, y);
			else draw_dir(node.f.getName(), x, y);
			// need more efficient data structure here!!
			_nodemap.put(new Integer(y), node);
			// post("putting "+node.f.getName()+" at "+y);
			x += 10;
			// this doesn't take into account the size of the string following.
			// we need to figure this out if we want to have horizontal scrolling
			_offscreen_w = x;
			// if(node == rootnode)
			y += CELL_HEIGHT;
			if (node.open)
			{
				if (!node.calculated)
					expand_node(node);
				for (int i = 0; i < node.children.length; i++)
				{
					y = rendertree(node.children[i], x, y);
					// y+=CELL_HEIGHT;
				}
			}
			return y;
		}
		if (node == selected_node)
		{
			_lcd.setFRGB(SELFGCOLOR);
			_lcd.paintRect(-300, y - 2, 600, 20);
		}
		draw_file(node.f.getName(), x, y);
		_nodemap.put(new Integer(y), node);
		// post("putting "+node.f.getName()+" at "+y);
		return y + CELL_HEIGHT;
	}

	private static final int SCROLLBAR_WIDTH = 12;
	private int v_scrollbar_nub_y = 0;
	private int v_scrollbar_nub_h = 0;
	private boolean v_scrollbar_is_showing = false;

	private void draw_v_scrollbar()
	{
		_lcd.beginSprite("vscrollbar");
		// clear rect;
		_lcd.setFRGB(SCROLLBGCOLOR);
		_lcd.paintRect(0, 0, SCROLLBAR_WIDTH, _h);
		// draw bar area
		_lcd.setFRGB(FGCOLOR);
		_lcd.frameRect(0, 0, SCROLLBAR_WIDTH, _h);
		// draw nub
		// _lcd.setFRGB(0,255,0);
		double nub_percent = (float) _h / (float) _offscreen_h;
		v_scrollbar_nub_h = (int) (_h * nub_percent);
		_lcd.frameRoundRect(0, v_scrollbar_nub_y, SCROLLBAR_WIDTH, v_scrollbar_nub_h, 8, 8);
		_lcd.setFRGB(SCROLLBARCOLOR);
		_lcd.paintRoundRect(1, v_scrollbar_nub_y + 2, SCROLLBAR_WIDTH - 2, v_scrollbar_nub_h - 4, 8, 8);
		_lcd.endSprite();
		_lcd.drawSprite("vscrollbar", _w - SCROLLBAR_WIDTH, 0);
	}

	private void move_v_scrollbar_nub(int y)
	{
		if (v_scrollbar_is_showing)
		{
			if (y < 0)
			{
				y = 0;
			}
			if (y > _h - v_scrollbar_nub_h)
			{
				y = _h - v_scrollbar_nub_h;
			}
			v_scrollbar_nub_y = y;
			double percent_offset = (double) v_scrollbar_nub_y / (double) _h;
			_content_pane_y_offset = (int) -(_offscreen_h * percent_offset);
			draw();
		}
	}

	private void expand_node(TreeNode node)
	{
		if (!node.f.isDirectory())
			return;
		TreeNode[] children;
		File[] files = node.f.listFiles();
		if (files != null && files.length > 0)
		{
			// children = new treenode[files.length];
			// for (int i = 0; i < children.length; i++)
			// children[i] = new treenode(files[i]);
			// node.children = children;
			int real_length = 0;
			for (int i = 0; i < files.length; i++)
				if (!files[i].getName().startsWith(".") && !files[i].isHidden())
					real_length++;
			children = new TreeNode[real_length];
			int c = 0;
			for (int i = 0; i < files.length; i++)
				if (!files[i].getName().startsWith(".") && !files[i].isHidden())
				{
					children[c] = new TreeNode(files[i]);
					c++;
				}
			node.children = children;
		}
		else
		{
			node.children = new TreeNode[0];
		}
		node.calculated = true;
	}

	private void dumptree(TreeNode node, int indent)
	{
		for (int i = 0; i < indent; i++)
			System.out.print(" ");
		System.out.print(node.f.getName());
		System.out.println();
		if (node.children != null)
		{
			for (int i = 0; i < node.children.length; i++)
			{
				dumptree(node.children[i], indent + 5);
			}
		}
	}

	class TreeNode
	{
		File f;
		boolean open;
		int x;
		int y;
		TreeNode[] children;
		boolean calculated;

		// x and y will be set when we render the tree
		TreeNode(File file)
		{
			this.f = file;
			this.open = false;
			this.calculated = false;
		}
	}

	private boolean _mousedown = false;
	private boolean _dragging = false;
	private int mousedown_x = -1;
	private int mousedown_y = -1;
	private boolean first_mousedown = true;
	private int _v_nub_off_y = 0;
	private boolean _drag_nub = false;
	private boolean no_drag = false;

	public void mouse(int x, int y)
	{
		if (!_mousedown && x == mousedown_x && y == mousedown_y)
		{
		}
		else if (first_mousedown)
		{
			mouseClicked(x, y);
			mousedown_x = x;
			mousedown_y = y;
			first_mousedown = false;
			if (v_scrollbar_is_showing)
			{
				if (x >= _w - SCROLLBAR_WIDTH && y >= v_scrollbar_nub_y && y <= v_scrollbar_nub_y + v_scrollbar_nub_h)
				{
					// click was in nub
					_v_nub_off_y = y - v_scrollbar_nub_y;
					_drag_nub = true;
				}
			}
		}
		else
		{
			mouseDragged(x, y);
		}
	}

	public void mousedown(boolean b)
	{
		no_drag = true;
		_mousedown = b;
		if (b)
			first_mousedown = true;
		else
		{
			_drag_nub = false;
			prep_dragged_file = false;
			if (dragged_file)
			{
				dragged_file = false;
				outlet(1, Atom.parse("released " + (mousepos.x - _w) + " " + mousepos.y));
				draw();
			}
		}
	}

	public void mouseClicked(int x, int y)
	{
		if (!_minimized)
		{
			if (x <= 12 && y <= 12 && minizable)
			{
				do_minimize();
				return;
			}
			if (v_scrollbar_is_showing)
			{
				if (x >= _w - SCROLLBAR_WIDTH)
				{
					// click was in scrollbar area but not on nub
					if ((y < v_scrollbar_nub_y) || (y > v_scrollbar_nub_y + v_scrollbar_nub_h))
					{
						// move it by a nub size
						if (y > v_scrollbar_nub_y)
							move_v_scrollbar_nub(v_scrollbar_nub_y + v_scrollbar_nub_h);
						else move_v_scrollbar_nub(v_scrollbar_nub_y - v_scrollbar_nub_h);
						draw();
					}
					return;
				}
			}
			selected_node = get_node_from_click(x, y);
			if (selected_node != null)
			{
				sel_name = selected_node.f.getName();
				String abs_path = selected_node.f.getAbsolutePath();
				if(abs_path.indexOf("/Volumes/") != -1)
				{
					abs_path = abs_path.substring(abs_path.indexOf("Volumes")+8);
					int ind = abs_path.indexOf("/");
					abs_path = abs_path.substring(0,ind)+":"+abs_path.substring(ind);
				}
				sel_filename = abs_path;//MaxSystem.nameConform(selected_node.f.getAbsolutePath(), MaxSystem.PATH_STYLE_MAX, MaxSystem.PATH_TYPE_IGNORE);
				outlet(1, Atom.parse("directory " + (selected_node.f.isDirectory() ? 1 : 0)));
				outlet(1, Atom.parse("name " + sel_name));
				outlet(1, Atom.parse("filename " + sel_filename));
				selected_node.open = !selected_node.open;
				if (!selected_node.f.isDirectory())
				{
					mousepos.setLocation(x, y);
					prep_dragged_file = true;
				}
				draw();
			}
		}
		else
		{
			do_maximize();
		}
	}

	public void mouseDragged(int x, int y)
	{
		if(no_drag)
		{
			no_drag = false;
			return;
		}
		if(prep_dragged_file)
		{
			dragged_file = true;
			outlet(1, Atom.parse("draggedover " + (x - _w) + " " + y + " " + sel_name + "@" + sel_filename));
		}
		if (_drag_nub)
		{
			move_v_scrollbar_nub(y - _v_nub_off_y);
		}
		else if (dragged_file)
		{
			outlet(1, Atom.parse("draggedover " + (x - _w) + " " + y + " " + sel_name + "@" + sel_filename));
			mousepos.setLocation(x, y);
			draw();
		}
	}

	public void bang()
	{
		initialize();
	}

	public void root(String dir)
	{
//		dir = MaxSystem.maxPathToNativePath(dir);
		_root = new File(dir);
		if (!_root.exists() || !_root.isDirectory())
		{
			error("(mxj filebrowse) Invalid root directory argument");
			return;
		}
		rootnode = new TreeNode(_root);
		rootnode.open = true;
		_nodemap.clear();
		move_v_scrollbar_nub(0);
		redraw();
	}

	public void size(int width, int height)
	{
		_w = width;
		_h = height;
		_lcd.setSize(width, height);
		redraw();
	}

	public void brgb(int r, int g, int b)
	{
		BGCOLOR[0] = r;
		BGCOLOR[1] = g;
		BGCOLOR[2] = b;
		redraw();
	}

	public void frgb(int r, int g, int b)
	{
		FGCOLOR[0] = r;
		FGCOLOR[1] = g;
		FGCOLOR[2] = b;
		redraw();
	}

	public void scrollbarrgb(int r, int g, int b)
	{
		SCROLLBARCOLOR[0] = r;
		SCROLLBARCOLOR[1] = g;
		SCROLLBARCOLOR[2] = b;
		redraw();
	}

	public void trianglergb(int r, int g, int b)
	{
		TRIANGLECOLOR[0] = r;
		TRIANGLECOLOR[1] = g;
		TRIANGLECOLOR[2] = b;
		redraw();
	}

	public void folderrgb(int r, int g, int b)
	{
		FOLDERCOLOR[0] = r;
		FOLDERCOLOR[1] = g;
		FOLDERCOLOR[2] = b;
		redraw();
	}

	public void save()
	{
		// uncomment this is you want colors and size saved with the patch
		/*
		 * Atom[] abrgb = Atom.newAtom(BGCOLOR); Atom[] afrgb = Atom.newAtom(FGCOLOR); Atom[] ascrollbarrgb = Atom.newAtom(SCROLLBARCOLOR); Atom[] atrianglergb = Atom.newAtom(TRIANGLECOLOR); Atom[] afolderrgb = Atom.newAtom(FOLDERCOLOR); Atom[] asize = new Atom[]{Atom.newAtom(_w),Atom.newAtom(_h)};
		 * embedMessage("brgb", abrgb); embedMessage("frgb", afrgb); embedMessage("scrollbarrgb", ascrollbarrgb); embedMessage("trianglergb", atrianglergb); embedMessage("folderrgb", afolderrgb); embedMessage("size", asize);
		 */
	}

	public void redraw()
	{
		if (rootnode == null)
		{
			rootnode = new TreeNode(_root);
			rootnode.open = true;
		}
		if (_minimized)
		{
			_lcd.reset();
			draw_minimize();
		}
		else
		{
			_lcd.reset();
			draw();
		}
	}
}
