import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxSystem;

import lf.LfObject;
import lf.gui.FloatingWindow;

public class Prompter extends LfObject
{
	private static final int TYPE_TEXT = 0;
	private static final int TYPE_RTF = 1;
	private static final int TYPE_HTML = 2;
	private int type = TYPE_TEXT;
	private FloatingWindow wind;
	private static final int DEFAULT_X = 200;
	private static final int DEFAULT_Y = 200;
	private static final int DEFAULT_W = 400;
	private static final int DEFAULT_H = 500;
	private int[] location = { DEFAULT_X, DEFAULT_Y };
	private int[] size = { DEFAULT_W, DEFAULT_H };
	private int fontsize = 10;
	private int	fontstyle = 0;
	private String fontname = "Verdana";
	private static Font fo = new Font("Verdana", Font.PLAIN, 10);
	public String file = "nofile";
	public String title = "Prompter";
	private File theFile = null;
	private JTextPane textPane;
	private JScrollBar editorScrollBar;
	private StyledEditorKit dek;
	private RTFEditorKit rek;
	private HTMLEditorKit hek;
	private boolean titleAsFilename = true;
	public double position = 0;
	private Color brgb, frgb;
	private static final char[] __accs = new char[] { 'A', 'X', 'C', 'V', 'S', 'Z', 'O', 'N', 'W', 'F', 'G', 'H', 'L', 'K', 'D' };

	public Prompter(Atom[] atoms)
	{
		version = 0.6f;
		build = "20/10/08";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "commands (open,close,read,write...)" };
		OUTLET_ASSIST = new String[] { "prompt position" };
		init(0, true, atoms);
		declareAttribute("fontname", "getFontname", "setFontname");
		declareAttribute("fontsize", "getFontsize", "setFontsize");
		declareAttribute("fontstyle", "getFontstyle", "setFontstyle");
		declareAttribute("position", "getPosition", "setPosition");
		declareAttribute("file", "getFile", "setFile");
		declareAttribute("location", "getLocation", "setLocation");
		declareAttribute("size", "getSize", "setSize");
		declareAttribute("title", "getTitle", "setTitle");
		declareAttribute("editable", "getEditable", "setEditable");
		declareAttribute("type", "getType", "setType");
//		MaxSystem.registerCommandAccelerators(__accs);
		wind = new FloatingWindow(title, size[0], size[1], location[0], location[1], true);
		wind.setResizable(true);
		wind.setUndecorated(false);
		wind.setTitleBar(false);
		wind.setTitle(title);
		dek = new StyledEditorKit();
		rek = new RTFEditorKit();
		hek = new HTMLEditorKit();
		textPane = new JTextPane();
		textPane.setMargin(new Insets(20, 20, 20, 20));			
		updateType();
		JScrollPane editorScrollPane = new JScrollPane(textPane);
		editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		editorScrollBar = editorScrollPane.getVerticalScrollBar();
		editorScrollBar.addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent arg0)
			{
				bang();
			}
		});
		wind.setLayout(new BorderLayout());
		wind.add(editorScrollPane, BorderLayout.CENTER);
		wind.addWindowListener(new WindowListener()
		{
			public void windowActivated(WindowEvent e)
			{
			}

			public void windowClosed(WindowEvent e)
			{
			}

			public void windowClosing(WindowEvent e)
			{
				outlet(0, new Atom[] { Atom.newAtom("open"), Atom.newAtom(0) });
			}

			public void windowDeactivated(WindowEvent e)
			{
			}

			public void windowDeiconified(WindowEvent e)
			{
			}

			public void windowIconified(WindowEvent e)
			{
			}

			public void windowOpened(WindowEvent e)
			{
				outlet(0, new Atom[] { Atom.newAtom("open"), Atom.newAtom(1) });
			}
		});
		wind.addComponentListener(new ComponentListener()
		{
			public void componentHidden(ComponentEvent e)
			{
			}

			public void componentMoved(ComponentEvent e)
			{
				outlet(0, new Atom[] { Atom.newAtom("location"), Atom.newAtom(wind.getX()), Atom.newAtom(wind.getY()) });
			}

			public void componentResized(ComponentEvent e)
			{
				outlet(0, new Atom[] { Atom.newAtom("size"), Atom.newAtom(wind.getWidth()), Atom.newAtom(wind.getHeight()) });
			}

			public void componentShown(ComponentEvent e)
			{
			}
		});
		center();
//		editorTextArea.setEditable(false);
//		editorTextArea.setEnabled(false);
//		editorTextArea.setRequestFocusEnabled(false);
//		for(KeyListener kl : editorTextArea.getKeyListeners())
//			editorTextArea.removeKeyListener(kl);
//		for(KeyListener kl : wind.getKeyListeners())
//			wind.removeKeyListener(kl);
//		for(KeyListener kl : editorScrollBar.getKeyListeners())
//			editorScrollBar.removeKeyListener(kl);
//		for(KeyListener kl : editorScrollPane.getKeyListeners())
//			editorScrollPane.removeKeyListener(kl);
		
		// Init Style

//		doc = textPane.getStyledDocument();
//        Style def = StyleContext.getDefaultStyleContext().
//        getStyle(StyleContext.DEFAULT_STYLE);
//
//        Style regular = doc.addStyle("regular", def);
//        StyleConstants.setFontFamily(def, "SansSerif");
//
//        Style s = doc.addStyle("italic", regular);
//        StyleConstants.setItalic(s, true);
//
//        s = doc.addStyle("bold", regular);
//        StyleConstants.setBold(s, true);
//
//        s = doc.addStyle("small", regular);
//        StyleConstants.setFontSize(s, 10);
//
//        s = doc.addStyle("large", regular);
//        StyleConstants.setFontSize(s, 16);

	}
	
	private void updateType()
	{
		switch(type)
		{
		case TYPE_TEXT :
			textPane.setEditorKit(dek);
			break;
		case TYPE_RTF :
			textPane.setEditorKit(rek);
			break;
		case TYPE_HTML :
			textPane.setEditorKit(hek);
			break;
		}
		textPane.getEditorKit().createDefaultDocument();
	}

	public void bang()
	{
		position = ((double) editorScrollBar.getValue()) / (double) (editorScrollBar.getMaximum() - editorScrollBar.getVisibleAmount());
		position = position * 100.;
		outlet(0, new Atom[] { Atom.newAtom("position"), Atom.newAtom(position) });
	}

	public void open()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				wind.setVisible(true);
				outlet(0, new Atom[] { Atom.newAtom("open"), Atom.newAtom(1) });
			}
		});
	}

	public void close()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				wind.setVisible(false);
				outlet(0, new Atom[] { Atom.newAtom("open"), Atom.newAtom(0) });
			}
		});
	}

	public void read(Atom[] list)
	{
		if (list.length == 0)
		{
			file = MaxSystem.openDialog("Read...");
		}
		else
		{
			file = Atom.toOneString(list);
		}
		read();
	}

	private void read()
	{
		try
		{
			if (file.indexOf("/Volumes/") != -1)
			{
				file = file.substring(file.indexOf("Volumes") + 8);
				int ind = file.indexOf("/");
				file = file.substring(0, ind) + ":" + file.substring(ind);
			}
			if(file.endsWith(".rtf") || file.endsWith(".rtfd"))
				type = TYPE_RTF;
			else if(file.endsWith(".html") || file.endsWith(".htm"))
				type = TYPE_HTML;
			else
				type = TYPE_TEXT;
			file = MaxSystem.locateFile(file);
			theFile = new File(file);
			updateType();
			switch(type)
			{
			case TYPE_TEXT :
				textPane.read(new InputStreamReader(new FileInputStream(theFile),"UTF-8"), null);
				break;
			case TYPE_RTF :
				textPane.read(new InputStreamReader(new FileInputStream(theFile),"UTF-8"), null);
				break;
			case TYPE_HTML :
				URL url = new URL("file","localhost",file);
				textPane.setPage(url);
				break;
			}
			if (titleAsFilename)
				wind.setTitle(file);
		}
		catch (FileNotFoundException e)
		{
			error("Prompter : File \""+file+"\" not found.");
			if (titleAsFilename)
				wind.setTitle(file);
			debug(e);
		}
		catch (NullPointerException e)
		{
			error("Prompter : File \""+file+"\" not found.");
			if (titleAsFilename)
				wind.setTitle(file);
			debug(e);
		}
		catch (UnsupportedEncodingException e)
		{
			error("Prompter : Unsupported encoding.");
			if (titleAsFilename)
				wind.setTitle(file);
			debug(e);
		}
		catch (Exception e)
		{
			error("Exception "+e.toString());
			if (titleAsFilename)
				wind.setTitle(file);
			debug(e);
		}
	}

	public void save()
	{
		// writeagain();
		// post("prompter -> save disabled");
	}

	public void writeagain()
	{
		write();
	}

	public void write(Atom[] list)
	{
		if (list.length == 0)
		{
			if (theFile != null)
				file = MaxSystem.saveAsDialog("Save as...", theFile.getName());
			else file = MaxSystem.saveAsDialog("Save as...", file);
		}
		else
		{
			file = Atom.toOneString(list);
		}
		write();
	}

	private void write()
	{
		try
		{
			try
			{
				theFile = new File(file);
			}
			catch (NullPointerException e)
			{
				file = MaxSystem.locateFile(file);
				theFile = new File(file);
			}
			switch(type)
			{
			case TYPE_TEXT:
				textPane.write(new FileWriter(theFile));
				if (titleAsFilename)
					wind.setTitle(file);
				break;
			case TYPE_RTF:
			case TYPE_HTML:
				error("Prompter : Sorry, RTF and HTML documents can not be written.");
				break;
			}
		}
		catch (Exception e)
		{
			error("Problem while writing the file : " + file);
			debug(e);
		}
	}

	public void dblclick()
	{
		open();
	}

	public void clear()
	{
		textPane.setText("");
	}

	public double getPosition()
	{
		return position;
	}

	public void setPosition(float position)
	{
		this.position = position;
		editorScrollBar.setValue((int) ((editorScrollBar.getMaximum() - editorScrollBar.getVisibleAmount()) * (position / 100)));
	}

	public String getFile()
	{
		return file;
	}

	public void setFile(String file)
	{
		this.file = file;
		read();
	}

	public void frgb(Atom[] c)
	{
		if (c.length >= 3)
		{
			if (c[0].isInt() && c[1].isInt() && c[2].isInt())
				frgb = new Color(c[0].toInt(), c[1].toInt(), c[2].toInt());
		}
		textPane.setForeground(frgb);
	}

	public void brgb(Atom[] c)
	{
		if (c.length >= 3)
		{
			if (c[0].isInt() && c[1].isInt() && c[2].isInt())
				brgb = new Color(c[0].toInt(), c[1].toInt(), c[2].toInt());
		}
		textPane.setBackground(brgb);
	}

	public void usage()
	{
		post(ppp + "Usage:\n" + ppp + "   Attributes :\n" + ppp + "      @file : file to display." + ppp + "      @fontsize : font size (int)." + ppp + "      @position : position in the file (%)." + ppp + "      @wrap i1 : set line wrap (0-1)." + ppp + "      @size i1 i2 : set size." + ppp
				+ "      @location i1 i2 : set location." + ppp + "      @title s1 : set the title of the window (s1 = \'filename\' sets the filename as window title)." + ppp + "   Messages :\n" + ppp + "      open : open the window." + ppp + "      close : close the window." + ppp
				+ "      center : center the window in the screen." + ppp + "      read (s1) : read a file (optional argument filename)." + ppp + "      write (s1) : write a file (optional argument filename)." + ppp + "      writeagain : replace the currently read file." + ppp
				+ "      save : save the file (called when patcher is closed)." + ppp + "      frgb i1 i2 i3 : font color." + ppp + "      brgb i1 i2 i3 : background color." + ppp + "      fontsize i1 : font size." + ppp + "      position f1 : sets the prompter position (%)." + ppp
				+ "   Output :\n" + ppp + "      f1 : returns the viewed position in the text (%).");
	}

	public void info()
	{
		post(ppp + "Info:\n" + ppp + "   Displays a fully parameterizable prompter, and simple text editor.");
	}

	public void state()
	{
		post(ppp + "State:\n" + ppp + "   Filename:" + this.file + ppp + "   Fontsize:" + this.fontsize + ppp + "   Position:" + this.position + ppp + /*"   Wrap:" + getWrap() + ppp + */"   Size:" + getSize()[0] + " " + getSize()[1] + ppp + "   Location:" + getLocation()[0] + " " + getLocation()[1]
				+ ppp + "   Title:" + getTitle());
	}

	public void notifyDeleted()
	{
		// post("deleting...");
		close();
		// SwingUtilities.invokeLater(new Runnable()
		// {
		// public void run()
		// {
		// wind.dispose();
		// }
		// });
	}

	public void center()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				wind.setLocationRelativeTo(null);
				location[0] = wind.getLocation().x;
				location[1] = wind.getLocation().y;
			}
		});
	}

	public void size(int i, int j)
	{
		setSize(i, j);
	}

	protected int[] getSize()
	{
		return new int[] { wind.getSize().width, wind.getSize().height };
	}

	protected void setSize(int i, int j)
	{
		size = new int[] { i, j };
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				wind.setSize(size[0], size[1]);
			}
		});
	}

	public void title(Atom[] a)
	{
		setTitle(Atom.toOneString(a));
	}

	protected String getTitle()
	{
		return wind.getTitle();
	}

	protected void setTitle(String s)
	{
		if (s != null)
		{
			if (s.equalsIgnoreCase("filename"))
			{
				titleAsFilename = true;
				wind.setTitle(file);
			}
			else
			{
				titleAsFilename = false;
				wind.setTitle(s);
			}
		}
	}

	public void location(int i, int j)
	{
		setLocation(i, j);
	}

	protected int[] getLocation()
	{
		return new int[] { wind.getLocation().x, wind.getLocation().y };
	}

	protected void setLocation(int i, int j)
	{
		location = new int[] { i, j };
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				wind.setLocation(location[0], location[1]);
			}
		});
	}
	
	protected boolean getEditable()
	{
		return textPane.isEditable();
	}

	protected void setEditable(boolean b)
	{
		textPane.setEditable(b);
	}

	public int getType()
	{
		return type;
	}

	public void setType(int _type)
	{
		this.type = clip(_type,0,2);
	}

	public int getFontstyle()
	{
		return fontstyle;
	}

	public void setFontstyle(int fontstyle)
	{
		this.fontstyle = fontstyle;
		updateFont();
	}

	public int getFontsize()
	{
		return fontsize;
	}

	public void setFontsize(int fontsize)
	{
		this.fontsize = fontsize;
		updateFont();
	}
	
	public String getFontname()
	{
		return fontname;
	}

	public void setFontname(String fontname)
	{
		this.fontname = fontname;
		updateFont();
	}
	
	private void updateFont()
	{
		fo = new Font(fontname, fontstyle, fontsize);
		textPane.setFont(fo);
	}
}
