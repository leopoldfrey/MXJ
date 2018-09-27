import java.io.File;

import com.cycling74.max.Executable;
import com.cycling74.max.MaxClock;
import com.cycling74.max.MaxObject;


public class FileWatcher extends MaxObject implements Executable
{
	private static final int WATCH_DELAY = 250;
	private MaxClock watch;
	private String file;
	private File f;
	private long timeStamp;
	
	public FileWatcher()
	{
		createInfoOutlet(false);
		watch = new MaxClock(this);
		declareAttribute("file", "getFile", "setFile");
	}

	public void notifyDeleted()
	{
		watch.unset();
		watch.release();
	}
	
	public String getFile()
	{
		return file;
	}

	public void setFile(String file)
	{
		this.file = file;
		updateFile();
			
	}

	private void updateFile()
	{
		if(this.file != null)
		{
			f = new File(file);
			if(f.exists())
			{
				timeStamp = f.lastModified();
//				System.out.println("Start Watching : "+f.getAbsolutePath()+" "+timeStamp);
				watch.delay(WATCH_DELAY);
			}
			else
			{
//				System.out.println("Not found : "+file);
				file = null;
				f = null;
				watch.unset();
			}
		}
	}
	
	public void execute()
	{
		if(f != null)
		{
			long oTimeStamp = timeStamp;
			timeStamp = f.lastModified();
//			System.out.println("Watching : "+f.getAbsolutePath()+" "+timeStamp);
			if(timeStamp != oTimeStamp)
				outletBang(0);
			watch.delay(WATCH_DELAY);
		} else 
		{
			watch.unset();
		}
	}
}