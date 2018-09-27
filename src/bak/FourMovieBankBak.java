package bak;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

import com.cycling74.jitter.JitterMatrix;
import com.cycling74.jitter.JitterObject;
import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;


public class FourMovieBankBak extends MaxObject implements FilenameFilter
{
	int vw = 720;
	int vh = 576;
	int vindex[];
	int vcount = 0;
	Vector<JitterObject> movarray = new Vector<JitterObject>();
	Vector<JitterMatrix> outmatrix = new Vector<JitterMatrix>();
	JitterMatrix dummymatrix = new JitterMatrix(4,"char",vw,vh);
	JitterObject dummymovie = new JitterObject("jit.qt.movie");
	Vector<String> filenames = new Vector<String>();
	final static int OUT_MENU = 8;
	int current_scene[];
	int fade[];
	protected int[] INLET_TYPES = {};
	protected int[] OUTLET_TYPES = {};
	protected String[] INLET_ASSIST = {};
	protected String[] OUTLET_ASSIST = {};
	public float version;

	public FourMovieBankBak()
	{
		version = 0.1f;
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL};
		INLET_ASSIST = new String[] { "incoming messages" };
		OUTLET_ASSIST = new String[] { "movie 1a out", "movie 1b out", "movie 2a out", "movie 2b out", "movie 3a out", "movie 3b out", "movie 4a out", "movie 4b out", "movie list (ubumenu)" };
	
		init();
		
		vindex = new int[]{-1,-1,-1,-1,-1,-1,-1,-1};
		current_scene = new int[]{0,0,0,0};
		fade = new int[]{0,0,0,0};
//		dummymovie.call("unique",1);
		dummymovie.call("dim",new int[]{vw,vh});
		dummymovie.call("colormode","uyvy");
		dummymovie.matrixcalc(dummymatrix, dummymatrix);
	}
	
	protected void init()
	{
		declareInlets(INLET_TYPES);
		declareOutlets(OUTLET_TYPES);
		setInletAssist(INLET_ASSIST);
		setOutletAssist(OUTLET_ASSIST);
		createInfoOutlet(false);
		setName(this.getClass().getName().toString());
	}

	public void bang()
	{ 
		if (vcount != 0) {
//			Vector<Integer> running_movies = new Vector<Integer>();
			for(int i = 0; i < 8; i++)
			{
				if(vindex[i] == -1)
				{
					outlet(i,"jit_matrix",dummymatrix.getName());	
				} else {
//					running_movies.add(vindex[i]);
					movarray.get(vindex[i]).matrixcalc(outmatrix.get(vindex[i]),outmatrix.get(vindex[i]));
					outlet(i,"jit_matrix",outmatrix.get(vindex[i]).getName());	
				}
			}
//			JitterObject mov;
//			for(int i = 0 ; i < movarray.size() ; i++)
//			{
//				if(!running_movies.contains(i))
//				{
//					mov = movarray.get(i);
//					mov.call("stop");
//				}
//			}
		} else {
			for(int i = 0; i < 8; i++)
				outlet(i,"jit_matrix",dummymatrix.getName());	
		}
	}

	public void startAll()
	{
		if(vcount != 0)
		{
			JitterObject qtm;
			for(int i = 0 ; i < vcount ; i++)
			{
				qtm = movarray.get(i);
				qtm.call("time",0);
				qtm.call("start");
			}
		}
	}
	
	public void stopAll()
	{
		if(vcount != 0)
			for(int i = 0 ; i < vcount ; i++)
				movarray.get(i).call("stop");
	}
	
	public void preloadAll()
	{
		if(vcount != 0)
		{
			JitterObject qtm;
			JitterMatrix jtm;
			for(int i = 0 ; i < vcount ; i++)
			{
				qtm = movarray.get(i);
				jtm = outmatrix.get(i);
				qtm.matrixcalc(jtm, jtm);
			}
		}
	}
	
	public void index(int i, int j)
	{
		if(i < 0 || i >= 8)
			return;
		if (j>=vcount)
			j = vcount-1;
		if (j<0)
			j = 0;

//		movarray.get(vindex[i]).call("stop");
		vindex[i] = j;
		movarray.get(vindex[i]).call("time",0);
		movarray.get(vindex[i]).call("start");
	}

	public void readfolder(String foldername)
	{
		int i;
		Atom[] rv;	

		foldername = MaxSystem.locateFile(convert(foldername));
		File fold = new File(foldername);
		if(!fold.exists())
		{
			error("Can't find folder : "+foldername);
			return;
		}
		String[] list = fold.list(this);
		if(list.length != 0)
		{
			// reset old movies
			for (JitterObject jo : movarray) {
				jo.freePeer();
			}
			for (JitterMatrix jm : outmatrix) {
				jm.freePeer();
			}
			// build filename array
			vcount = 0;
			filenames.clear();
			movarray.clear();
			outmatrix.clear();
			outlet(OUT_MENU,"clear");
			outlet(OUT_MENU,Atom.parse("append ignore"));
			outlet(OUT_MENU,Atom.parse("append black"));
			// load new movies
			for (String moviename : list) {
				filenames.add(foldername+moviename);// = fold.pathname + "/" + fold.filename; 
				System.out.println("Adding : "+foldername+moviename);
				JitterObject jo = new JitterObject("jit.qt.movie");
				jo.call("autostart",0);
				jo.call("vol",0);
//				jo.call("adapt",1);
				jo.call("dim",new int[]{vw,vh});
//				jo.call("unique",1);
				jo.call("colormode","uyvy");
//				jo.call("preroll",1);
				rv = jo.call("read",foldername+moviename);
				movarray.add(jo);
				if (rv[1].toInt()==1) { // success, read returns an array [filename,success]
					outmatrix.add(new JitterMatrix(4,"char",vw,vh));
//					jo.call("time",0);
//					jo.call("start");
					jo.matrixcalc(outmatrix.get(vcount),outmatrix.get(vcount));
//					jo.call("stop");
					outlet(OUT_MENU,"append",moviename);
					vcount++;
				}
			}
			for(int k = 0; k < 8 ; k++)
			{
				if (vindex[k]>=vcount) 
					vindex[k] = -1;
				if(vindex[k] != -1)
					movarray.get(vindex[k]).matrixcalc(outmatrix.get(vindex[k]),outmatrix.get(vindex[k]));
			}
		}
	}

	public void dim(int width, int height)
	{
		vw = width;
		vh = height;
		for (int i = 0; i < vcount; i++)
			outmatrix.get(i).setDim(new int[]{vw,vh});
		dummymatrix.setDim(new int[]{vw,vh});
	}

	public void list(Atom[] args)
	{
		if(!args[0].isInt())
			return;
		int k = args[0].toInt();
		if(k < 0 || k >= 8)
			return;
		movarray.get(vindex[k]).send(Atom.toOneString(Atom.removeFirst(args)));
	}
	
	public void anything(String s, Atom[] args)
	{
		if(s.equalsIgnoreCase("go"))
		{
			for(int i = 0 ; i < 4 ; i++)
			{
				int i2 = i*2;
				boolean b = current_scene[i] == 0;
				if(args[i2].toInt() >= 0)
				{
					int next_scene = i2 + (b ? 1 : 0);
					vindex[next_scene] = args[i2].toInt()-1;
					if(vindex[next_scene] != -1)
					{
						movarray.get(vindex[next_scene]).call("time",0);
						movarray.get(vindex[next_scene]).call("start");
					}
					outlet(i2,b ? Atom.parse("fade 1 "+args[i2+1].toInt()) : Atom.parse("fade 0 "+args[i2+1].toInt()));
					current_scene[i] = b ? 1 : 0;
				}
			}
		} else if(s.equalsIgnoreCase("all")) {
			for(JitterObject jo : movarray)
				jo.send(args[0].toString(), Atom.removeFirst(args));
		} else {
			for(int k = 0; k < 8; k++)
				if(vindex[k] >= 0)
					movarray.get(vindex[k]).call(s, args);
		}
	}
	
	public void notifyDeleted()
	{
		for(JitterObject jo : movarray)
			jo.freePeer();
		for(JitterMatrix jm : outmatrix)
			jm.freePeer();
	}
	
	public boolean accept(File f, String s)
	{
		return s.endsWith(".mov")
		|| s.endsWith(".mpeg") 
		|| s.endsWith(".mpg") 
		|| s.endsWith(".avi")
		|| s.endsWith(".mp4")
		|| s.endsWith(".dv")
		|| s.endsWith(".jpg")
		|| s.endsWith(".jpeg")
		|| s.endsWith(".gif")
		|| s.endsWith(".pict")
		|| s.endsWith(".pct")
		|| s.endsWith(".tiff")
		|| s.endsWith(".tif")
		|| s.endsWith(".bmp")
		|| s.endsWith(".psd")
		|| s.endsWith(".png")
		|| s.endsWith(".MOV")
		|| s.endsWith(".MPEG") 
		|| s.endsWith(".MPG") 
		|| s.endsWith(".AVI")
		|| s.endsWith(".MP4")
		|| s.endsWith(".DV")
		|| s.endsWith(".JPG")
		|| s.endsWith(".JPEG")
		|| s.endsWith(".GIF")
		|| s.endsWith(".PICT")
		|| s.endsWith(".PCT")
		|| s.endsWith(".TIFF")
		|| s.endsWith(".TIF")
		|| s.endsWith(".BMP")
		|| s.endsWith(".PSD")
		|| s.endsWith(".PNG")
		;
	}
	
	public boolean fixed(String s)
	{
		return s.endsWith(".jpg")
		|| s.endsWith(".jpeg")
		|| s.endsWith(".gif")
		|| s.endsWith(".pict")
		|| s.endsWith(".pct")
		|| s.endsWith(".tiff")
		|| s.endsWith(".tif")
		|| s.endsWith(".bmp")
		|| s.endsWith(".psd")
		|| s.endsWith(".png")
		|| s.endsWith(".JPG")
		|| s.endsWith(".JPEG")
		|| s.endsWith(".GIF")
		|| s.endsWith(".PICT")
		|| s.endsWith(".PCT")
		|| s.endsWith(".TIFF")
		|| s.endsWith(".TIF")
		|| s.endsWith(".BMP")
		|| s.endsWith(".PSD")
		|| s.endsWith(".PNG")
		;
	}
	
	private String convert(String s)
	{
		if (s.indexOf("/Volumes/") != -1)
		{
			s = s.substring(s.indexOf("Volumes") + 8);
			int ind = s.indexOf("/");
			s = s.substring(0, ind) + ":" + s.substring(ind);
		}
		return s;
	}

	private String invert(String s)
	{
		if(s.indexOf(":") == -1)
			return s;
		String[] tmp = s.split(":");
		return "/Volumes/"+tmp[0]+tmp[1];
	}
}
