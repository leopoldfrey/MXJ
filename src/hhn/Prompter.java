package hhn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
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

import javax.swing.BorderFactory;
import javax.swing.JFrame;
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

public class Prompter extends LfObject// implements Printable
{
	private static final int TYPE_TEXT = 0;
	private static final int TYPE_RTF = 1;
	private static final int TYPE_HTML = 2;
	private int type = TYPE_TEXT;
	private JFrame wind;
	private static final int DEFAULT_X = 200;
	private static final int DEFAULT_Y = 200;
	private static final int DEFAULT_W = 400;
	private static final int DEFAULT_H = 500;
	private int[] location = { DEFAULT_X, DEFAULT_Y };
	private int[] size = { DEFAULT_W, DEFAULT_H };
	private int fontsize = 10;
	private int fontstyle = 0;
	private String fontname = "Verdana";
	private static Font fo = new Font("Verdana", Font.PLAIN, 10);
	public String file = "nofile";
	public String title = "Prompter";
	public boolean titlebar = true;
	public boolean resizable = true;
	public boolean scrollvisible = true;
	public int fullscreen = 0;
	private File theFile = null;
	private JTextPane textPane;
	private JScrollBar editorScrollBar;
	private JScrollPane editorScrollPane;
	private StyledEditorKit dek;
	private RTFEditorKit rek;
	private HTMLEditorKit hek;
	private boolean titleAsFilename = true;
	public double position = 0;
	private Color brgb, frgb, bordercolor = Color.BLACK;
	private int bordersize = 0;
	private boolean ready = false;
	private static final char[] __accs = new char[] { 'A', 'X', 'C', 'V', 'S', 'Z', 'O', 'N', 'W', 'F', 'G', 'H', 'L', 'K', 'D' };

	public Prompter(Atom[] atoms)
	{
		version = 0.6f;
		build = "20/10/08";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "commands (open,close,read,write...)" };
		OUTLET_ASSIST = new String[] { "prompt position" };
		init();
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
		declareAttribute("titlebar", "isTitlebar", "setTitlebar");
		declareAttribute("resizable", "isResizable", "setResizable");
		declareAttribute("scrollvisible", "isScrollvisible", "setScrollvisible");
		declareAttribute("fullscreen", "getFullscreen", "setFullscreen");
		// MaxSystem.registerCommandAccelerators(__accs);
		wind = new JFrame(title);
		wind.setSize(size[0], size[1]);
		wind.setLocation(location[0], location[1]);
		// wind = new FloatingWindow(title, size[0], size[1], location[0], location[1], true);
		wind.setResizable(resizable);
		wind.setUndecorated(!titlebar);
		wind.setTitle(title);
		dek = new StyledEditorKit();
		rek = new RTFEditorKit();
		hek = new HTMLEditorKit();
		textPane = new JTextPane();
		textPane.setMargin(new Insets(20, 20, 20, 20));
		updateType();
		textPane.setEditable(false);
		editorScrollPane = new JScrollPane(textPane);
		editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setBorder(null);
		editorScrollBar = editorScrollPane.getVerticalScrollBar();
		editorScrollBar.setBorder(null);
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
		// editorTextArea.setEditable(false);
		// editorTextArea.setEnabled(false);
		// editorTextArea.setRequestFocusEnabled(false);
		// for(KeyListener kl : editorTextArea.getKeyListeners())
		// editorTextArea.removeKeyListener(kl);
		// for(KeyListener kl : wind.getKeyListeners())
		// wind.removeKeyListener(kl);
		// for(KeyListener kl : editorScrollBar.getKeyListeners())
		// editorScrollBar.removeKeyListener(kl);
		// for(KeyListener kl : editorScrollPane.getKeyListeners())
		// editorScrollPane.removeKeyListener(kl);
		// Init Style
		// doc = textPane.getStyledDocument();
		// Style def = StyleContext.getDefaultStyleContext().
		// getStyle(StyleContext.DEFAULT_STYLE);
		//
		// Style regular = doc.addStyle("regular", def);
		// StyleConstants.setFontFamily(def, "SansSerif");
		//
		// Style s = doc.addStyle("italic", regular);
		// StyleConstants.setItalic(s, true);
		//
		// s = doc.addStyle("bold", regular);
		// StyleConstants.setBold(s, true);
		//
		// s = doc.addStyle("small", regular);
		// StyleConstants.setFontSize(s, 10);
		//
		// s = doc.addStyle("large", regular);
		// StyleConstants.setFontSize(s, 16);
		/*
		 * textPane.addPropertyChangeListener(new PropertyChangeListener() { public void propertyChange(PropertyChangeEvent evt) { System.out.println("Prop : "+evt.getPropertyName()+" "+evt.getOldValue()+" "+evt.getOldValue()); } });//
		 */
		ready = true;
	}

	private void updateType()
	{
		switch (type)
		{
		case TYPE_TEXT:
			textPane.setEditorKit(dek);
			break;
		case TYPE_RTF:
			textPane.setEditorKit(rek);
			break;
		case TYPE_HTML:
			textPane.setEditorKit(hek);
			break;
		}
		textPane.getEditorKit().createDefaultDocument();
	}

	public void bang()
	{
		position = ((double) editorScrollBar.getValue()) / (double) (editorScrollBar.getMaximum() - editorScrollBar.getVisibleAmount());
		position = position * 100.;
		if(Double.isNaN(position))
			position = 0;
		outlet(0, new Atom[] { Atom.newAtom("position"), Atom.newAtom(position) });
	}

	public void open()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				// wind.pack();
				wind.setVisible(true);
			}
		});
		outlet(0, new Atom[] { Atom.newAtom("open"), Atom.newAtom(1) });
	}

	public void close()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				wind.setVisible(false);
				wind.dispose();
			}
		});
		outlet(0, new Atom[] { Atom.newAtom("open"), Atom.newAtom(0) });
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
//		System.out.println("Reading : "+file);
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
			if (file.endsWith(".rtf") || file.endsWith(".rtfd"))
				type = TYPE_RTF;
			else if (file.endsWith(".html") || file.endsWith(".htm"))
				type = TYPE_HTML;
			else type = TYPE_TEXT;
			file = MaxSystem.locateFile(file);
//			System.out.println("Located file : "+file);
			theFile = new File(file);
			updateType();
			switch (type)
			{
			case TYPE_TEXT:
				textPane.read(new InputStreamReader(new FileInputStream(theFile), "UTF-8"), null);
				break;
			case TYPE_RTF:
				textPane.read(new InputStreamReader(new FileInputStream(theFile), "UTF-8"), null);
				break;
			case TYPE_HTML:
				URL url = new URL("file", "localhost", file);
				textPane.setPage(url);
				break;
			}
			if (titleAsFilename)
				wind.setTitle(file);
			outlet(0, new Atom[] { Atom.newAtom("read"), Atom.newAtom(file), Atom.newAtom(1) });
		}
		catch (FileNotFoundException e)
		{
			error("Prompter : File \"" + file + "\" not found.");
			if (titleAsFilename)
				wind.setTitle(file);
			debug(e);
			outlet(0, new Atom[] { Atom.newAtom("read"), Atom.newAtom(file), Atom.newAtom(0) });
		}
		catch (NullPointerException e)
		{
			error("Prompter : File \"" + file + "\" not found.");
			if (titleAsFilename)
				wind.setTitle(file);
			debug(e);
			outlet(0, new Atom[] { Atom.newAtom("read"), Atom.newAtom(file), Atom.newAtom(0) });
		}
		catch (UnsupportedEncodingException e)
		{
			error("Prompter : Unsupported encoding.");
			if (titleAsFilename)
				wind.setTitle(file);
			debug(e);
			outlet(0, new Atom[] { Atom.newAtom("read"), Atom.newAtom(file), Atom.newAtom(0) });
		}
		catch (Exception e)
		{
			error("Exception " + e.toString());
			if (titleAsFilename)
				wind.setTitle(file);
			debug(e);
			outlet(0, new Atom[] { Atom.newAtom("read"), Atom.newAtom(file), Atom.newAtom(0) });
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
			switch (type)
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
		if (ready)
		{
			try
			{
				editorScrollBar.setValue((int) clip((editorScrollBar.getMaximum() - editorScrollBar.getVisibleAmount()) * (position / 100), 0f, editorScrollBar.getMaximum()));
			}
			catch (Exception e)
			{
				// System.err.println(e.toString());
				e.printStackTrace();
			}
			// } else {
			// System.err.println("!ready");
		}
	}

	public void getScrollInfos()
	{
		outlet(0, new Atom[] { Atom.newAtom("maximum"), Atom.newAtom(editorScrollBar.getMaximum()) });
		outlet(0, new Atom[] { Atom.newAtom("visamount"), Atom.newAtom(editorScrollBar.getVisibleAmount()) });
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
	
	public void bordercolor(Atom[] c)
	{
		if (c.length >= 3)
		{
			if (c[0].isInt() && c[1].isInt() && c[2].isInt())
				bordercolor = new Color(c[0].toInt(), c[1].toInt(), c[2].toInt());
		}
		editorScrollPane.setBorder(BorderFactory.createLineBorder(bordercolor, bordersize));
	}
	
	public void bordersize(int i)
	{
		bordersize = i;
		editorScrollPane.setBorder(BorderFactory.createLineBorder(bordercolor, bordersize));
	}

	public void usage()
	{
		post("Usage:");
		post("   Attributes :");
		post("      @file : file to display.");
		post("      @fontsize : font size (int).");
		post("      @position : position in the file (%).");
		post("      @wrap i1 : set line wrap (0-1).");
		post("      @size i1 i2 : set size.");
		post("      @location i1 i2 : set location.");
		post("      @title s1 : set the title of the window (s1 = \'filename\' sets the filename as window title).");
		post("   Messages :\n");
		post("      open : open the window.");
		post("      close : close the window.");
		post("      center : center the window in the screen.");
		post("      read (s1) : read a file (optional argument filename).");
		post("      write (s1) : write a file (optional argument filename).");
		post("      writeagain : replace the currently read file.");
		post("      save : save the file (called when patcher is closed).");
		post("      frgb i1 i2 i3 : font color.");
		post("      brgb i1 i2 i3 : background color.");
		post("      fontsize i1 : font size.");
		post("      position f1 : sets the prompter position (%).");
		post("   Output :\n");
		post("      f1 : returns the viewed position in the text (%).");
	}

	public void info()
	{
		post(ppp + "Info:\n" + ppp + "   Displays a fully parameterizable prompter, and simple text editor.");
	}

	public void state()
	{
		post(ppp + "State:\n" + ppp + "   Filename:" + this.file + ppp + "   Fontsize:" + this.fontsize + ppp + "   Position:" + this.position + ppp + /* " Wrap:" + getWrap() + ppp + */"   Size:" + getSize()[0] + " " + getSize()[1] + ppp + "   Location:" + getLocation()[0] + " " + getLocation()[1]
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
		ready = false;
		size = new int[] { i, j };
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				wind.setSize(size[0], size[1]);
				ready = true;
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
		ready = false;
		location = new int[] { i, j };
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				wind.setLocation(location[0], location[1]);
				ready = true;
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
		this.type = clip(_type, 0, 2);
	}

	public int getFontstyle()
	{
		return fontstyle;
	}

	public void setFontstyle(int fontstyle)
	{
		ready = false;
		this.fontstyle = fontstyle;
		updateFont();
	}

	public int getFontsize()
	{
		return fontsize;
	}

	public void setFontsize(int fontsize)
	{
		ready = false;
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
		ready = true;
	}

	public boolean isTitlebar()
	{
		return titlebar;
	}

	public void setTitlebar(boolean _t)
	{
		titlebar = _t;
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				wind.setUndecorated(!titlebar);
			}
		});
	}

	public boolean isResizable()
	{
		return resizable;
	}

	public void setResizable(boolean _r)
	{
		resizable = _r;
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				wind.setResizable(resizable);
			}
		});
	}

	public boolean isScrollvisible()
	{
		return scrollvisible;
	}

	public void setScrollvisible(boolean scrollvisible)
	{
		this.scrollvisible = scrollvisible;
		editorScrollBar.setVisible(false);
		if (this.scrollvisible)
		{
			editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		}
		else
		{
			editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
	}

	public int getFullscreen()
	{
		return fullscreen;
	}

	public void setFullscreen(int fullscreen)
	{
		ready = false;
		this.fullscreen = fullscreen;
		if (this.fullscreen > 0)
		{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gs = ge.getScreenDevices()[this.fullscreen - 1];
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			final Rectangle bounds = gc.getBounds();
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					wind.setVisible(false);
					wind.dispose();
					wind.setUndecorated(true);
					editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
					editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					wind.setLocation(bounds.x, bounds.y);
					wind.setSize(bounds.width, bounds.height);
					// wind.pack();
					wind.setVisible(true);
					ready = true;
				}
			});
		}
		else
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					wind.setVisible(false);
					wind.dispose();
					editorScrollBar.setVisible(scrollvisible);
					if (scrollvisible)
					{
						editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
						editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					}
					else
					{
						editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
						editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					}
					wind.setUndecorated(!titlebar);
					wind.setLocation(location[0], location[1]);
					wind.setSize(size[0], size[1]);
					// wind.pack();
					wind.setVisible(true);
					ready = true;
				}
			});
		}
	}
	
	/*
	public void devices()
	{
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		for (int j = 0; j < gs.length; j++)
		{
			GraphicsDevice gd = gs[j];
			GraphicsConfiguration gc = gd.getDefaultConfiguration();
			Rectangle bounds = gc.getBounds();
			outlet(0, new Atom[] { Atom.newAtom("screen"), Atom.newAtom(j + 1), Atom.newAtom(bounds.x), Atom.newAtom(bounds.y), Atom.newAtom(bounds.width), Atom.newAtom(bounds.height) });
		}
	}

	public void print()
	{
		try
		{
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(this);
			job.print();
			outlet(0, new Atom[] { Atom.newAtom("print"), Atom.newAtom(1) });
		}
		catch (PrinterException e)
		{
			e.printStackTrace();
			outlet(0, new Atom[] { Atom.newAtom("print"), Atom.newAtom(-1) });
		}
	}

	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
	{
		if (pageIndex > 0)
		{ // We have only one page, and 'page' is zero-based
			return NO_SUCH_PAGE;
		}
		
		// User (0,0) is typically outside the imageable area, so we must translate by the X and Y values in the PageFormat to avoid clipping
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		// Now we perform our rendering 
		
		try
		{
//			String s = "";
//			FileReader fr = new FileReader(file);
//			char c = (char)fr.read();
//			while(c != -1)
//			{
//				s += s + c;
//				c = (char)fr.read();
//			}
			System.out.println("Read : "+textPane.getText());
			System.out.println("Bounds : "+g2d.getClipBounds().width);
			String txt = textPane.getText();
			//int len = txt.length();
			//int w = g2d.getClipBounds().width / fontsize;
			//int cpt = 0;
			//while(cpt + w < len)
			//{
			//	graphics.drawString(txt.substring(cpt,cpt+w), 0, );
			//}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		graphics.drawString("A", 0, 0);
		graphics.drawString("B", g2d.getClipBounds().width, g2d.getClipBounds().height);
		//tell the caller that this page is part of the printed document
		return PAGE_EXISTS;
	}//*/

	
}
