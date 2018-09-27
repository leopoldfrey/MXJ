import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

import com.cycling74.jitter.JitterMatrix;
import com.cycling74.jitter.JitterObject;
import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;

public class FourMovieBank extends MaxObject implements FilenameFilter
{
	int vw = 720;
	int vh = 576;
	int vindex[];
	int vcount = 0;
	Vector<JitterObject> movarray = new Vector<JitterObject>();
	Vector<JitterMatrix> outmatrix = new Vector<JitterMatrix>();
	Vector<Integer> running_movies = new Vector<Integer>();
	Vector<String> filenames = new Vector<String>();
	JitterMatrix dummymatrix = new JitterMatrix(4,"char",vw,vh);
	JitterObject dummymovie = new JitterObject("jit.qt.movie");
	final static int OUT_MENU = 8;
	int current_scene[];
	int current_sync[];
	public float version;
	public boolean autoclear = false;
	public boolean debug = false;

	public FourMovieBank()
	{
		version = 0.1f;
		String build = "22/11/08";
		declareInlets(new int[] { DataTypes.ALL });
		declareOutlets(new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL});
		setInletAssist(new String[] { "incoming messages" });
		setOutletAssist(new String[] { "movie 1a out", "movie 1b out", "movie 2a out", "movie 2b out", "movie 3a out", "movie 3b out", "movie 4a out", "movie 4b out", "movie list (ubumenu)" });
		createInfoOutlet(false);
		setName(this.getClass().getName().toString());
		
		declareAttribute("autoclear");
		declareAttribute("debug");
		
		clear();
		dummymovie.call("dim",new int[]{vw,vh});
		dummymovie.call("colormode","uyvy");
		dummymovie.matrixcalc(dummymatrix, dummymatrix);
	}
	
	public void init()
	{
		vindex = new int[]{-1,-1,-1,-1,-1,-1,-1,-1};
		current_scene = new int[]{1,1,1,1};
		current_sync = new int[]{0,0,0,0};
		for(int i : running_movies)
			movarray.get(i).call("stop");
		running_movies.clear();
		for(int i = 0 ; i < 8 ; i+=2)
		{
			outlet(i, Atom.parse("fade 1 0"));
			outlet(i, Atom.parse("sync 0"));
			outlet(i+1, Atom.parse("sync 0"));
		}
	}
	
	public void dblclick()
	{
		version();
	}
	
	public void version()
	{
		System.out.println("FourMovieBank - leopold.frey@free.fr - 06.84.39.91.46 - "+version);
	}

	public void clear()
	{
		vcount = 0;
		init();
		for(JitterObject jo : movarray)
			jo.freePeer();
		for(JitterMatrix jm : outmatrix)
			jm.freePeer();
		movarray.clear();
		outmatrix.clear();
		filenames.clear();
		outlet(OUT_MENU,"clear");
		outlet(OUT_MENU,Atom.parse("append ignore"));
		outlet(OUT_MENU,Atom.parse("append black"));
	}

	public void bang()
	{ 
		if(debug)
		{
			System.out.print("\tindex per bus : ");
			for(int i = 0 ; i < 8 ; i++)
				System.out.print(vindex[i]+" ");
			System.out.println();
			System.out.print("\trunning movies : ");
			for(int i : running_movies)
				System.out.print(i+" ");
			System.out.println();
		}
		if (vcount != 0) {
			for(int k : running_movies)
				movarray.get(k).matrixcalc(outmatrix.get(k), outmatrix.get(k));
			JitterMatrix jm;
			for(int i = 0; i < 8; i++)
			{
				if(vindex[i] == -1)
				{
					outlet(i,"jit_matrix",dummymatrix.getName());	
				} else {
					outlet(i,"jit_matrix",outmatrix.get(vindex[i]).getName());	
				}
			}
		} else {
			for(int i = 0; i < 8; i++)
				outlet(i,"jit_matrix",dummymatrix.getName());	
		}
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
		foldername = convert(foldername);
		String[] list = fold.list(this);
		if(list.length != 0)
		{
			if(autoclear)
				clear();
			// load new movies
			JitterMatrix jm;
			JitterObject jo;
			for (String moviename : list) {
				if(!filenames.contains(moviename))
				{
					jo = new JitterObject("jit.qt.movie");
					jo.call("autostart", 0);
					jo.call("vol", 0);
					jo.call("dim", new int[] { vw, vh });
					jo.call("colormode", "uyvy");
					rv = jo.call("read", foldername + moviename);
					if (rv[1].toInt() == 1)
					{ // success, read returns an array [filename,success]
						System.out.println("Adding : " + vcount + " " + foldername + moviename);
						filenames.add(moviename);
						movarray.add(jo);
						jm = new JitterMatrix(4, "char", vw, vh);
						jm.setName(moviename);
						outmatrix.add(jm);
						jo.matrixcalc(jm, jm);
						outlet(OUT_MENU, "append", moviename);
						vcount++;
					} else
					{
						error("can't find/read : " + foldername + moviename);
					}
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
		for (JitterMatrix jm : outmatrix)
			jm.setDim(new int[]{vw,vh});
		for(JitterObject jo : movarray)
			jo.call("dim",new int[]{vw,vh});
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
			if(debug)
				System.out.println("GO : "+Atom.toOneString(args)+" / "+args.length);
			for(int i = 0 ; i < 4 ; i++)
			{
				int i2 = i*2;
				int i3 = i*3;
				boolean b = current_scene[i] == 0;
				int curscene = i2 + (b ? 0 : 1);
				int nextscene = i2 + (b ? 1 : 0);
				int curmov = vindex[i2 + current_scene[i]];
				int cursync = current_sync[i];
				int nextmov = args[i3].toInt()-1;
				int nextfade = args[i3+1].toInt();
				int nextsync = args[i3+2].toInt();
				if(nextmov >= -1 || nextsync > 0)
				{
					if(nextsync > 0 && i != 0)
					{
						b = current_scene[0] == 1;
						int onext = nextscene;
						nextscene = i2 + (b ? 1 : 0);
						if(onext != nextscene)
						{
							System.out.println("impossible ! t'es tomb� sur un os mec !");
							int oi = vindex[curscene];
							if(oi != -1)
							{
								vindex[curscene] = -1;
								boolean stop = true;
								for (int k : vindex)
									if (k == oi)
										stop = false;
								if (stop)
								{
									movarray.get(oi).call("stop");
									running_movies.removeElement(oi);
								}
							}
						}
						if(debug)
							System.out.println("\t" + i + " nextsync " + nextsync);
						outlet(nextscene, Atom.parse("sync "+ nextsync));
						outlet(i2, b ? Atom.parse("fade 1 " + nextfade) : Atom.parse("fade 0 " + nextfade));
						current_sync[i] = nextsync;
						current_scene[i] = b ? 1 : 0;
					} else if(nextmov != curmov) {
						int oi = vindex[nextscene];
						if(oi != -1)
						{
							vindex[nextscene] = -1;
							boolean stop = true;
							for (int k : vindex)
								if (k == oi)
									stop = false;
							if (stop)
							{
								movarray.get(oi).call("stop");
								running_movies.removeElement(oi);
							}
						}
						vindex[nextscene] = nextmov;
						if (vindex[nextscene] != -1)
						{
							if (!running_movies.contains(vindex[nextscene]))
							{
								running_movies.add(vindex[nextscene]);
								movarray.get(vindex[nextscene]).call("time", 0);
								movarray.get(vindex[nextscene]).call("start");
							}
						}
						if(debug)
							System.out.println("\t" + i + " nextscene " + nextscene +" nextmov "+nextmov);
						outlet(nextscene, Atom.parse("sync "+ nextsync));
						outlet(i2, b ? Atom.parse("fade 1 " + nextfade) : Atom.parse("fade 0 " + nextfade));
						current_sync[i] = nextsync;
						current_scene[i] = b ? 1 : 0;
					} else {
						if(debug)
							System.out.println("\t"+i+" nochanges");
					}
				} else {
					if(debug)
						System.out.println("\t"+i+" ignore");
				}
			}
		} else if(s.equalsIgnoreCase("endxfade")) {
			if(debug)
				System.out.println("FADE END : "+Atom.toOneString(args));
			int endfade = args[0].toInt()-1;
			endfade = endfade*2+(current_scene[endfade] == 0 ? 1 : 0);
			int oi = vindex[endfade];
			if(oi != -1)
			{
				vindex[endfade] = -1;
				boolean stop = true;
				for (int i : vindex)
					if (i == oi)
						stop = false;
				if (stop)
				{
					movarray.get(oi).call("stop");
					running_movies.removeElement(oi);
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



