import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class SystemClipboard extends LfObject implements ClipboardOwner
{
	static Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();

	SystemClipboard(Atom[] atoms)
	{
		version = 0.1f;
		build = "06/09/06";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "copy something to system clipboard" };
		OUTLET_ASSIST = new String[] { "paste from clipboard" };
		init(0, false, atoms);
	}

	public void usage()
	{
		post(ppp+"Usage:\n"
				+ppp+"   Messages :\n"
				+ppp+"   copy ... : put a message in the clipboard\n"
				+ppp+"   paste : get the text message residing on the clipboard");
	}

	public void info()
	{
		post(ppp+"Info:\n"
				+ppp+"   Put text messages on the system clipboard\n"
				+ppp+"   to get them in another application\n"
				+ppp+"   or get the text message residing on the clipboard in max world");
	}

	public void state()
	{
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		//odd: the Object param of getContents is not currently used
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText)
		{
			try
			{
				result = (String) contents.getTransferData(DataFlavor.stringFlavor);
			}
			catch (UnsupportedFlavorException ex)
			{
				//highly unlikely since we are using a standard DataFlavor
				System.out.println(ex);
				ex.printStackTrace();
			}
			catch (IOException ex)
			{
				System.out.println(ex);
				ex.printStackTrace();
			}
		}
		post(ppp+"State:\n"
				+ppp+"   Clipboard content : "+result);
	}

	public void lostOwnership(Clipboard clipboard, Transferable contents)
	{
	}

	public void paste()
	{
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		//odd: the Object param of getContents is not currently used
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText)
		{
			try
			{
				result = (String) contents.getTransferData(DataFlavor.stringFlavor);
			}
			catch (UnsupportedFlavorException ex)
			{
				//highly unlikely since we are using a standard DataFlavor
				System.out.println(ex);
				ex.printStackTrace();
			}
			catch (IOException ex)
			{
				System.out.println(ex);
				ex.printStackTrace();
			}
		}
		char[] ccc = result.toCharArray();
		String tmp = new String();
		int i = 0;
		while(i < ccc.length)
		{
			while(i < ccc.length && (byte)ccc[i] != 13)
			{
				tmp = tmp + ccc[i];
				i++;
			}
			outlet(0, Atom.parse(tmp));
			tmp = new String();
			i++;
		}
	}

	public void copy(Atom[] args)
	{
		clip.setContents(new StringSelection(Atom.toOneString(args)), this);
	}
}
