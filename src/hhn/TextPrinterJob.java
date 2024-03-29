package hhn;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Utility class to print some lines of text to the default printer. Uses some default font settings, and gets the page size from the PrinterJob object.
 * 
 * Note: this little example class does not handle pagination. All the text must fit on a single page.
 * 
 * This class can also be used as a standalone utility. If the main method is invoked, it reads lines of text from System.in, and prints them to the default printer.
 */
public class TextPrinterJob implements Printable
{
	/**
	 * Default font size, 12 point
	 */
	public static final int DEFAULT_FONT_SIZE = 12;
	/**
	 * Default type name, Serif
	 */
	public static final String DEFAULT_FONT_NAME = "Serif";
	private PrinterJob job;
	private String typeName;
	private int typeSize;
	private Font typeFont;
	private Font typeFontBold;
	private String[] header;
	private String[] body;
	private int padX = 5;
	private int padY = 5;

	/**
	 * Create a TextPrinter object with the default type font and size.
	 */
	public TextPrinterJob()
	{
		this(DEFAULT_FONT_NAME, DEFAULT_FONT_SIZE);
	}

	/**
	 * Create a TextPrinter object ready to print text with a given font and type size.
	 */
	public TextPrinterJob(String name, int size)
	{
		if (size < 3 || size > 127)
		{
			throw new IllegalArgumentException("Type size out of range");
		}
		typeName = name;
		typeSize = size;
		typeFont = new Font(typeName, Font.PLAIN, typeSize);
		typeFontBold = new Font(typeName, Font.BOLD, typeSize);
		job = null;
	}

	/**
	 * Initialize the printer job.
	 */
	protected void init()
	{
		job = PrinterJob.getPrinterJob();
		//PrintService[] s = PrinterJob.lookupPrintServices();
		//for(PrintService ps : s)
		//	System.out.println("Printer Service : "+ps.getName());
	}

	/**
	 * Initialize the print job, and return the base number of characters per line with the established font size and font. This is really just a guess, because we can't get the font metrics yet.
	 */
	public int getCharsPerLine()
	{
		if (job == null)
		{
			init();
		}
		PageFormat pf;
		pf = job.defaultPage();
		double width = pf.getImageableWidth(); // in 72nd of a pt
		double ptsize = typeFont.getSize();
		double ptwid = ptsize / 2;
		double cnt = (width / ptwid);
		return (int) (Math.round(cnt));
	}

	/**
	 * Print some text. Headers are printed first, in bold, followed by the body text, in plain style. If the boolean argument interactive is set to true, then the printer dialog gets shown.
	 * 
	 * Either array may be null, in which case they are treated as empty.
	 * 
	 * This method returns true if printing was initiated, or false if the user cancelled printer. This method may throw PrinterException if printing could not be started.
	 */
	public boolean doPrint(String[] _h, String[] _b, boolean interactive) throws PrinterException
	{
		if (job == null)
		{
			init();
		}
		if (interactive)
			try
			{
				if (job.printDialog())
				{
					// we are going to print
				}
				else
				{
					// we are not going to print
					return false;
				}
			}
			catch (Exception pe)
			{
				System.err.println("Could not pop up print dialog");
				// assume user wants to print anyway...
			}
		job.setPrintable(this);
		this.header = _h;
		this.body = _b;
		job.print();
		job = null; // we are no longer initialized
		return true;
	}

	/**
	 * Perform printing according to the Java printing model. NEVER CALL THIS DIRECTLY! It will be called by the PrinterJob as necessary. This method always returns Printable.NO_SUCH_PAGE for any page number greater than 0.
	 */
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
	{
		if (pageIndex != 0)
		{
			return NO_SUCH_PAGE;
		}
		FontMetrics fm;
		graphics.setFont(typeFont);
		graphics.setColor(Color.black);
		fm = graphics.getFontMetrics();
		// fill in geometric and rendering guts here
		int i;
		double x, y;
		x = pageFormat.getImageableX();
		y = pageFormat.getImageableY() + fm.getMaxAscent();
		// do the headings
		if (header != null)
		{
			graphics.setFont(typeFontBold);
			for (i = 0; i < header.length; i++)
			{
				graphics.drawString(header[i], (int) x + padX, (int) y + padY);
				y += fm.getHeight();
			}
		}
		String line, cutline, rest;
		int maxlen = getCharsPerLine();
		int cutat;
		// do the body
		if (body != null)
		{
			graphics.setFont(typeFont);
			for (i = 0; i < body.length; i++)
			{
				line = body[i];
				while (line.length() > getCharsPerLine())
				{
					cutline = line.substring(0, maxlen);
					//System.out.println("line def : "+cutline);
					cutat = cutline.lastIndexOf(" ");
					cutline = cutline.substring(0, cutat);
					//System.out.println("line case : "+cutline);
					graphics.drawString(cutline, (int) x + padX, (int) y + padY);
					y += fm.getHeight();
					//
					line = line.substring(cutat + 1);
					//System.out.println("rest case : "+line);
				}
				graphics.drawString(line, (int) x + padX, (int) y + padY);
				y += fm.getHeight();
			}
		}
		return PAGE_EXISTS;
	}

	/**
	 * Main method for testing. This main method sets up a header of "PRINTER TEST" and reads System.in to get body text.
	 */
	public static void main(String[] args)
	{
		BufferedReader br;
		ArrayList<String> lines;
		String filename = args[0];
		System.out.println(filename);
		TextPrinterJob tp;
		tp = new TextPrinterJob("Arial", 18);
		lines = new ArrayList<String>();
		try
		{
			InputStreamReader isr;
			isr = new InputStreamReader(new FileInputStream(filename), "UTF-8");
			br = new BufferedReader(isr);
			String line;
			for (line = br.readLine(); line != null; line = br.readLine())
			{
				lines.add(line);
			}
			br.close();
			System.out.println("chars per line: " + tp.getCharsPerLine());
			System.out.println("attempting to print...");
			String[] headers = new String[1];
			headers[0] = filename;
			String[] body = new String[lines.size()];
			for (int ix = 0; ix < lines.size(); ix++)
			{
				body[ix] = lines.get(ix);
			}
			boolean didit = tp.doPrint(headers, body, true);
			System.out.println("doPrint returns " + didit);
		}
		catch (Exception e)
		{
			System.err.println("Error printing: " + e);
			e.printStackTrace();
			System.exit(1);
		}
		System.exit(0);
	}

	public String getTypeName()
	{
		return typeName;
	}

	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
	}

	public int getTypeSize()
	{
		return typeSize;
	}

	public void setTypeSize(int typeSize)
	{
		this.typeSize = typeSize;
	}

	public Font getTypeFont()
	{
		return typeFont;
	}

	public void setTypeFont(Font typeFont)
	{
		this.typeFont = typeFont;
	}

	public Font getTypeFontBold()
	{
		return typeFontBold;
	}

	public void setTypeFontBold(Font typeFontBold)
	{
		this.typeFontBold = typeFontBold;
	}

	public String[] getHeader()
	{
		return header;
	}

	public void setHeader(String[] header)
	{
		this.header = header;
	}

	public int getPadX()
	{
		return padX;
	}

	public void setPadX(int padX)
	{
		this.padX = padX;
	}

	public int getPadY()
	{
		return padY;
	}

	public void setPadY(int padY)
	{
		this.padY = padY;
	}
}