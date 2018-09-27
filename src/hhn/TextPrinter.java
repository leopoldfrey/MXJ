package hhn;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxSystem;

import lf.LfObject;

public class TextPrinter extends LfObject
{
	private String filename;
	private TextPrinterJob tp = new TextPrinterJob("Arial", 18);
	private boolean interactive = false;
	private boolean header = false;

	public TextPrinter(Atom[] args)
	{
		version = 0.1f;
		build = "23/04/09";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		INLET_ASSIST = new String[] { "" };
		OUTLET_ASSIST = new String[] { "" };
		init();
		declareAttribute("interactive");
		declareAttribute("header");
	}
	
	public void fontsize(int i)
	{
		tp.setTypeSize(i);
	}
	
	public void fontname(String name)
	{
		tp.setTypeName(name);
	}
	
	public void padx(int i)
	{
		tp.setPadX(i);
	}
	
	public void padY(int i)
	{
		tp.setPadY(i);
	}
	
	public void print(Atom[] args)
	{
		if (args.length == 0)
		{
			filename = MaxSystem.openDialog("Read...");
		}
		else
		{
			filename = Atom.toOneString(args);
		}
		filename = MaxSystem.locateFile(filename);

		if(filename != null)
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					doPrint();
				}
			}); 
		} else {
			outlet(0, new Atom[]{Atom.newAtom("print"), Atom.newAtom("null"), Atom.newAtom(-1)});
		}
	}

	private void doPrint()
	{
		BufferedReader br;
		ArrayList<String> lines;
		lines = new ArrayList<String>();
		try
		{
			InputStreamReader isr;
			isr = new InputStreamReader(new FileInputStream(filename), "UTF-8");
			br = new BufferedReader(isr);
			String line;
			for (line = br.readLine(); line != null; line = br.readLine())
				lines.add(line);
			br.close();
			String[] headers = new String[1];
			headers[0] = header ? filename : "";//+filename;
			String[] body = new String[lines.size()];
			for (int ix = 0; ix < lines.size(); ix++)
			{
				body[ix] = lines.get(ix);
			}
			boolean didit = tp.doPrint(headers, body, interactive);
			outlet(0, new Atom[]{Atom.newAtom("print"), Atom.newAtom(filename), (didit ? Atom.newAtom(1) : Atom.newAtom(0))});
		}
		catch (Exception e)
		{
			System.err.println("Error printing: " + e);
			e.printStackTrace();
			outlet(0, new Atom[]{Atom.newAtom("print"), Atom.newAtom(filename), Atom.newAtom(-1)});
		}
	}
}
