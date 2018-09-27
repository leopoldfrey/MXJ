import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.SwingUtilities;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxClock;
import com.cycling74.max.MaxSystem;

import lf.LfObject;

class CsvFileFilter implements FilenameFilter
{
	public String extension = ".csv";
	
	public String getDescription()
	{
		return "MAIN Csv Record (*.csv)";
	}

	public boolean accept(File fd, String fn)
	{
		if (fn != null && fd != null)
		{
			String fileExt = getExtension(fn);
			if (fileExt != null)
			{
				if (fileExt.equalsIgnoreCase(extension))
				{
					return true;
				}
			}
		}
		return false;
	}

	public String getExtension(String filename)
	{
		if (filename != null)
		{
			int i = filename.lastIndexOf('.');
			if (i != -1)
				return filename.substring(i).toLowerCase();
		}
		return null;
	}

}

public class MainRecorder extends LfObject implements Executable {

	public static final String FILE_SEP = System.getProperty("file.separator");
	public static final String PATH_SEP = FILE_SEP;
	public static final String LINE_SEP = System.getProperty("line.separator");
	
	File f;
	String filename = "";
	boolean rec = false;
	boolean play = false;
	BufferedWriter bw;
	BufferedReader br;
	MaxClock clock = null;
	long prevtime = -1;
	   
    public MainRecorder(Atom[] args)
	{
		version = 0.1f;
		build = "23/02/16";
		INLET_ASSIST = new String[] { "" };
		OUTLET_ASSIST = new String[] { "" };
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.INT, DataTypes.INT };

		init();

		openfile(System.getProperty("user.home")+System.getProperty("file.separator")+"mainrecord.csv");
		clock = new MaxClock(this);
	}
    
    public void notifyDeleted()
    {
    	if(play)
    	{
    		clock.unset();
    		try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public void open()
    {
    	SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				FileDialog chooser = new FileDialog(new Frame(), "Csv File", FileDialog.SAVE);
				chooser.setFilenameFilter(new CsvFileFilter());
				chooser.setDirectory("");
				chooser.setVisible(true);
				if (chooser.getFile() != null)
				{
					String fn = chooser.getFile();
					String fd = chooser.getDirectory();
					openfile(fd + fn);
				}
				else openfile(System.getProperty("user.home")+System.getProperty("file.separator")+"mainrecord.csv");;
			}
		});
	}
	
	public void openfile(String ofn)
	{
		filename = ofn;
		if (!filename.equalsIgnoreCase(""))
		{
			if (!filename.endsWith(".csv"))
				filename = filename.concat(".csv");
			if (filename.indexOf(":") != -1)
				filename = MaxSystem.maxPathToNativePath(filename);
			post("MainRecorder : Writing to " + filename);
			f = new File(filename);
			if(!f.exists())
				clear();
		} else {
			open();
		}
	}
	
	public void preset(float ff)
	{
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true), "ISO-8859-1"));
			bw.append("PRESET "+ff);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void anything(String message, Atom atoms[])
	{
		if(message.equalsIgnoreCase("data"))
		{
			if(rec)
			{
				try {
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true), "ISO-8859-1"));
					for(int i = 0 ; i < atoms.length ; i++)
					{
						bw.append(atoms[i].toString());
						if(i < atoms.length - 1)
							bw.append("; ");
					}
					bw.newLine();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void record(int i)
	{
		rec = i > 0;
		outlet(1, rec == true ? 1 : 0);
	}
	
	public void play(int i)
	{
		play = i > 0;
		outlet(2, play == true ? 1 : 0);
		
		if(rec)
		{
			rec = false;
			outlet(1, rec == true ? 1 : 0);
		}
		
		if(play)
		{
			prevtime = -1;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "ISO-8859-1"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//post("__ Start of file __");
			clock.delay(0);
		}
		else
		{
			clock.unset();
			try {
				if(br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void clear()
	{
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "ISO-8859-1"));
			bw.write("time; "
					
					+ "left_posx; left_posy; left_posz; "
					+ "left_dirx; left_diry; left_dirz; "
					
					+ "lfing1_posx; lfing1_posy; lfing1_posz; "
					+ "lfing1_dirx; lfing1_diry; lfing1_dirz; "
					
					+ "lfing2_posx; lfing2_posy; lfing2_posz; "
					+ "lfing2_dirx; lfing2_diry; lfing2_dirz; "
					
					+ "lfing3_posx; lfing3_posy; lfing3_posz; "
					+ "lfing3_dirx; lfing3_diry; lfing3_dirz; "
					
					+ "lfing4_posx; lfing4_posy; lfing4_posz; "
					+ "lfing4_dirx; lfing4_diry; lfing4_dirz; "
					
					+ "lfing5_posx; lfing5_posy; lfing5_posz; "
					+ "lfing5_dirx; lfing5_diry; lfing5_dirz; "
					
					+ "right_posx; right_posy; right_posz; "
					+ "right_dirx; right_diry; right_dirz; "
					
					+ "rfing1_posx; rfing1_posy; rfing1_posz; "
					+ "rfing1_dirx; rfing1_diry; rfing1_dirz; "
					
					+ "rfing2_posx; rfing2_posy; rfing2_posz; "
					+ "rfing2_dirx; rfing2_diry; rfing2_dirz; "
					
					+ "rfing3_posx; rfing3_posy; rfing3_posz; "
					+ "rfing3_dirx; rfing3_diry; rfing3_dirz; "
					
					+ "rfing4_posx; rfing4_posy; rfing4_posz; "
					+ "rfing4_dirx; rfing4_diry; rfing4_dirz; "
					
					+ "rfing5_posx; rfing5_posy; rfing5_posz; "
					+ "rfing5_dirx; rfing5_diry; rfing5_dirz"
					
					);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void execute()
	{
		try {
			String line = br.readLine();
			if(line == null)
			{
				play = false;
				//post("__ End of file __");
				outlet(2, play == true ? 1 : 0);
				return;
			}
			if(line.startsWith("time; "))
				line = br.readLine();
			if(line.startsWith("PRESET"))
				line = br.readLine();
			long time;
			if(line != null)
			{
				
				line = line.replace("; ", " ");
				time = new Long(line.substring(0, line.indexOf(' '))).longValue();
				line = line.substring(line.indexOf(' '));
				if(prevtime == -1)
				{
					prevtime = time;
				}
				outlet(0,line.replace("; ", " "));
				//post("delay "+(time-prevtime));
				clock.delay(time-prevtime);
				prevtime = time;
			}
			else
			{
				play = false;
				//post("__ End of file __");
				outlet(2, play == true ? 1 : 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
