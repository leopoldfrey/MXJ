

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;
import lf.gui.FloatingWindow;

public class DialogBox extends LfObject
{
	private static final int bH = 15;
	private static final int bW = 70;
	private static final int DEFAULT = 200;
	private static Font fo = new Font("Verdana", Font.PLAIN, 8);
	
	// Attributes
	private String title = "A title should be set !";
	private String message = "Post a message here !";
	private String value = "Default Value";
	private int[] location = {DEFAULT,DEFAULT};
	private int[] size = {DEFAULT,DEFAULT};
	
	// GUI
	private myFWindow fl;
	private JButton ok, cancel;
	private JTextField mes, val;
//	private JTextArea mes, val;
	private JPanel north, center, south;
	
	public DialogBox(Atom[] atoms)
	{
		version = 0.7f;
		build = "13/10/08";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.INT };
		INLET_ASSIST = new String[] { "Message controling the dialog, see usage" };
		OUTLET_ASSIST = new String[] { "User typed value out (\"cancel\" if canceled)" };
		init(0, true, atoms);
		initDialogBox();
	}

	private void initDialogBox()
	{
		declareAttribute("size", "getSize", "setSize");
		declareAttribute("location", "getLocation", "setLocation");
		declareAttribute("title", "getTitle", "setTitle");
		declareAttribute("message", "getMessage", "setMessage");
		declareAttribute("value", "getValue", "setValue");
		
		fl = new myFWindow(title, size[0], size[1], location[0], location[1], false);
		fl.setLayout(new BorderLayout());
		
//		mes = new JTextArea(message, 2, 15);
		mes = new JTextField(message);
		mes.setVisible(true);
		mes.setFont(fo);
		mes.setFocusable(false);
		mes.setEditable(false);
		mes.setSelectedTextColor(mes.getForeground());
		mes.setSelectionColor(mes.getBackground());
		mes.setOpaque(false);
//		mes.setLineWrap(true);
//		mes.setWrapStyleWord(true);
		mes.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		north = new JPanel();
		north.setLayout(new FlowLayout());
		north.add(mes);
		fl.add(north, BorderLayout.NORTH);
		
//		val = new JTextArea(value);
		val = new JTextField(value);
		val.setBorder(BorderFactory.createLoweredBevelBorder());
		val.setFont(fo);
		val.setMargin(new Insets(1, 1, 1, 1));
		val.setVisible(true);
		val.addFocusListener(new FocusAdapter()
		{
			public void focusLost(FocusEvent e)
			{
				value = val.getText();
			}
		});
		val.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					cancel();
				} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validate();
				} else if(e.getKeyCode() == KeyEvent.VK_TAB) {
					val.transferFocus();
				}
			}
		});
		val.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				val.selectAll();
			}
		});
		
		center = new JPanel();
		center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		center.setLayout(new FlowLayout());
		center.add(val);
		fl.add(center, BorderLayout.CENTER);
		
		ok = new JButton("Ok");
		ok.setFont(fo);
		ok.setFocusable(true);
		cancel = new JButton("Cancel");
		cancel.setFont(fo);
		cancel.setFocusable(true);
		//ok.setMargin(new Insets(1, 1, 1, 1));
		//cancel.setMargin(new Insets(1, 1, 1, 1));
		setAllSize(ok, bW + 2, bH + 2);
		setAllSize(cancel, bW + 2, bH + 2);
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cancel();
			}
		});
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				validate();
			}
		});
		ok.setDefaultCapable(true);
		ok.addKeyListener(new kc());
		cancel.addKeyListener(new kc());
		
		south = new JPanel();
		south.setLayout(new FlowLayout(FlowLayout.CENTER, 4, size[0] / 10));
		south.add(ok);
		south.add(cancel);
		
		fl.add(south, BorderLayout.SOUTH);
		
		setSize(size[0], size[1]);
		center();
	}

	// Fonctions accessible directement depuis Max
	public void dblclick()
	{
		open();
	}
	public void bang()
	{
		open();
	}
	public void open()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				fl.open();
			}
		});
	}
	public void close()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				fl.dispose();
			}
		});
	}
	public void cancel()
	{
		outlet(0, "canceled");
		close();
	}
	public void validate()
	{
		value = val.getText();
		outlet(0,convString(value));
		close();
	}
	public void center()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				fl.setLocationRelativeTo(null);
				location[0] = fl.getLocation().x;
				location[1] = fl.getLocation().y;
			}
		});
	}
	public void location(int i, int j)
	{
		setLocation(i, j);
	}
	public void title(Atom[] a)
	{
		setTitle(Atom.toOneString(a));
	}
	public void size(int i, int j)
	{
		setSize(i, j);
	}
	public void message(Atom[] a)
	{
		setMessage(Atom.toOneString(a));
	}
	public void value(Atom[] a)
	{
		setValue(Atom.toOneString(a));
	}
	public void inlet(int i)
	{
		if (i != 0)
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					fl.open();
				}
			});
		else
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					fl.wclose();
				}
			});
	}
	// Fonctions pour attributs
	protected void setSize(int i, int j)
	{
		size = new int[]{i,j};
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				fl.setSize(size[0], size[1]);
				setAllSize(mes, size[0] - 10, 30);
				setAllSize(north, size[0], 30);
				setAllSize(val, size[0] - 20, size[1] - 30 - 12 - bH - 16 - 20);
				setAllSize(center, size[0], (size[1] - 30 - 12 - bH - 16));
				setAllSize(south, size[0], bH + 12);
				south.setLayout(new FlowLayout(FlowLayout.CENTER, size[0] / 10, 4));
			}
		});
	}
	protected void setAllSize(JComponent c, int i, int j)
	{
		Dimension d = new Dimension(i, j);
		c.setSize(d);
		c.setPreferredSize(d);
		c.setMaximumSize(d);
		c.setMinimumSize(d);
	}
	protected int[] getSize()
	{
		return size;
	}
	protected String getTitle()
	{
		return title;
	}
	protected void setTitle(String s)
	{
		if (s != null)
		{
			title = s;
			fl.setTitle(title);
		}
	}
	protected void setMessage(String s)
	{
		if (s != null)
		{
			message = s;
			mes.setText(message);
		}
	}
	protected String getMessage()
	{
		return message;
	}
	protected void setValue(String s)
	{
		if (s != null)
		{
			value = s;
			val.setText(value);
		}
	}
	protected String getValue()
	{
		return value;
	}
	public String getValueReturn()
	{
		return val.getText();
	}
	public FloatingWindow getWindow()
	{
		return this.fl;
	}
	
	protected void setLocation(int i, int j)
	{
		location = new int[]{i,j};
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				fl.setLocation(location[0], location[1]);
			}
		});
	}
	protected int[] getLocation()
	{
		return location;
	}

	private String[] convString(String s)
	{
		StringTokenizer stk = new StringTokenizer(s);
		String[] tmp = new String[stk.countTokens()];
		int i = 0;
		while(stk.hasMoreElements())
		{
			tmp[i] = stk.nextToken();
			i++;
		}
		return tmp;
	}
	
	// Extension de Floating Window pour avoir cancel sur bouton close
	private class myFWindow extends FloatingWindow implements WindowFocusListener
	{
		public myFWindow(String title, int sizeW, int sizeH, int posiX, int posiY, boolean visi)
		{
			super(title, sizeW, sizeH, posiX, posiY, visi);
			addKeyListener(new kc());
			addWindowFocusListener(this);
		}
		public void close()
		{
			outlet(0,"canceled");
			super.dispose();
		}
		public void wclose()
		{
			super.dispose();
		}
		public void displace(MouseEvent e)
		{
			super.displace(e);
			location[0] = this.posX;
			location[1] = this.posY;
		}
		public void windowGainedFocus(WindowEvent e)
		{
		}
		public void windowLostFocus(WindowEvent e)
		{
			wclose();
		}
	}
	
	private class kc extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			int keyC = e.getKeyCode();
			if(keyC == KeyEvent.VK_ENTER)
			{
				validate();
			} else if(keyC == KeyEvent.VK_ESCAPE)
			{
				cancel();
			}
		}
	}
	
	public void info()
	{
		post(ppp+"Info:\n"
				+ppp+"   Provides a fully parameterizable Dialog Box"
			);
	}
	
	public void state()
	{
		post(
				ppp+"State:\n"
				+ppp+"   Title:"+title
				+ppp+"   Message:"+message
				+ppp+"   Value:"+value
				+ppp+"   Location:"+getLocation()[0]+" "+getLocation()[1]
   				+ppp+"   Size:"+getSize()[0]+" "+getSize()[1]
			);
	}
	public void usage()
	{
		post(
				ppp+"Usage:\n"+
				ppp+"   Attributes :"
				+"\n"+ppp+"      @title : Dialog Title (String - w. quotation marks \" \")"
				+"\n"+ppp+"      @message : Dialog Message (String - w. quotation marks \" \")"
				+"\n"+ppp+"      @defaultValue : Default Value (String - w. quotation marks \" \")"
				+"\n"+ppp+"      @size : Dialog Size (int int - default (200,200))"
				+"\n"+ppp+"      @location : Dialog Location on screen (int int - default centered)"
			);
	}
	
	public void notifyDeleted()
	{
//		close();
	}
	
	public void select()
	{
		val.selectAll();
	}
}

