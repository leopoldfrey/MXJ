import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class Log extends LfObject{

	File logFile;
	FileWriter logFW;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss" );
	
	public Log(Atom[] atoms) 
	{
		version = 0.1f;
		build = "7/12/12";
		INLET_ASSIST = new String[] { "String to be logged" };
		OUTLET_ASSIST = new String[] { "Info out" };
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };
		init(0, false, atoms);
		
		logFile = new File(System.getProperty("user.home")+System.getProperty("file.separator")+"log.txt");
		Date date = new Date();
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    try {
			logFW = new FileWriter(logFile,true);
			logFW.append("______________________________________________\n");
			logFW.append("__ "+sdf.format(date)+" __ Log Started __\n");
			logFW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void file(Atom[] atoms)
	{
		logFile = new File(Atom.toOneString(atoms));
		if(!logFile.exists())
			try {
				logFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
		Date date = new Date();
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    try {
			logFW = new FileWriter(logFile,true);
			logFW.append("______________________________________________\n");
			logFW.append("__ "+sdf.format(date)+" __ Log Started __\n");
			logFW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}//*/
	}
	
	public void log(Atom[] atoms)
	{
		try {
			Date date = new Date();
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(date);
			logFW = new FileWriter(logFile,true);
			logFW.append("__ "+sdf.format(date)+" __ "+Atom.toOneString(atoms)+"\n");
			logFW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		try {
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			logFW = new FileWriter(logFile,true);
			logFW.append("__ "+sdf.format(date)+" __ Log Stopped __\n");
			logFW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}