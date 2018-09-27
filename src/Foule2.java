import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import javax.swing.SwingUtilities;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxBox;
import com.cycling74.max.MaxPatcher;
import com.cycling74.msp.MSPBuffer;

import lf.LfObject;
import lf.db.DataBase;

public class Foule2 extends LfObject
{
	private static final int OUT_DONE = 0;
	private static final int OUT_GRANUL = 1;
	private static final int OUT_CELLBLOCK = 2;
	private static final int OUT_COLL = 3;
	private static final int OUT_LIST = 4;
	private static final int OUT_NUM = 5;
	private static final String FILE_SEP = System.getProperty("file.separator");
	private static final String PATH_SEP = System.getProperty("path.separator");
	private DataBase db;
	private FilenameFilter sndFilter = new FilenameFilter()
	{
		public boolean accept(File dir, String name)
		{
			return (new File(dir+FILE_SEP+name)).isDirectory() || name.endsWith(".aif") || name.endsWith(".wav");
		}						
	};
	private String[] pfilter;
	private int[] limits = new int[]{0,10};
	private Vector<Integer> selBuf;
	public boolean enablecellblock = false;
	
	public Foule2(Atom[] args)
	{
		version = 0.2f;
		build = "11/06/07";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL };
		INLET_ASSIST = new String[] { "Command in" };
		OUTLET_ASSIST = new String[] { "Query done bang", "To bufgranul~", "To jit.cellblock", "To coll", "List of valid index", "Number of results" };
		
		init();
		declareAttribute("enablecellblock");
		
		db = new DataBase("localhost",(short)3306,"lab100","lab100","lab100");
	}
	
	public void notifyDeleted()
	{
		db.close();
	}
	
	public void close()
	{
		db.close();
	}
	
	public void initDb()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				String prop = "apple.awt.fileDialogForDirectories";
				Properties props = System.getProperties();
				Object oldValue = null;
				if (prop != null)
				{
					oldValue = props.get(prop);
					props.put(prop, "true");
				}
				
				FileDialog qd = new FileDialog(new Frame(),"Root Folder", FileDialog.LOAD);
				qd.setFilenameFilter(new FilenameFilter()
				{
					public boolean accept(File dir, String name)
					{
						if((new File(dir+name)).isDirectory())
							return true;
						return false;
					}
				});
				qd.setVisible(true);
				String tmp = qd.getDirectory()+qd.getFile();
				
				int indice = 0;
				
				if(tmp != null)
				{
					String from;
					String filename;
					int defintens = 5;
					String defto = "NOONE";
					
					File root = new File(tmp);
					File[] folders = root.listFiles(sndFilter);
					for(File fold : folders)
					{
						if(fold.isDirectory())
						{
							from = fold.getName();
							File[] files = fold.listFiles(sndFilter);
							for(File fil : files)
							{
								filename = fil.getName();
								String[] split = filename.split("_");
//								db.request(DataBase.TINSERT, "insert into memory (indice,filename,phrase,pfrom,pto,intens) values(?,?,?,?,?,?)", indice, filename, split[1], from, defto, defintens);
								db.request(DataBase.TINSERT, "insert into memory (filename,phrase,pfrom,pto,intens) values(?,?,?,?,?)", filename, split[1], from, defto, defintens);
								indice++;
							}
						}
					}
				}

				outlet(OUT_NUM, indice-1);
				done("Init Database");
				
				// Reset the system property
				if (prop != null)
				{
					if (oldValue == null)
						props.remove(prop);
					else
						props.put(prop, oldValue);
				}
			}

		});
	}
	
	public void clearDb()
	{
		System.out.print("Clearing \'memory\'...");
		db.request(DataBase.TTRUNCATE, "truncate table memory");
		System.out.println("...done");
		done("Clear Database");
	}
	
	private void done(String action)
	{
		outlet(OUT_DONE, Atom.newAtom(action));
		//outletBang(OUT_DONE);
	}

	public void filter(Atom[] args)
	{
		pfilter = new String[args.length];
		int i = 0;
		for(Atom a : args)
		{
			pfilter[i] = a.toString();
			i++;
		}
	}
	
	public void limits(Atom[] args)
	{
		limits = new int[2];
		switch(args.length)
		{
		case 1 :
			limits[0] = args[0].toInt();
			limits[1] = args[0].toInt();
			break;
		case 2 :
			limits[0] = args[0].toInt();
			limits[1] = args[1].toInt();
			break;
		}
	}

	public void bang()
	{
		try
		{
			selBuf = new Vector<Integer>();
			if(enablecellblock)
				outlet(OUT_CELLBLOCK,Atom.parse("clear all"));
			ResultSet rs;
			int cpt = 0;
			if(pfilter == null)
			{
				rs = (ResultSet)db.request(DataBase.TSELECT, "select * from memory where intens >= ? and intens <= ?", limits[0], limits[1]);
				while(rs.next())
				{
					treatResultSet(cpt, rs);
					cpt++;
				}
			} else {
				// FORMAT REQUEST
				String request = "select * from memory where intens >= ? and intens <= ?";
				String curfil;
				int len = 0;
				boolean first = true;
				for(int i = 0 ; i < pfilter.length ; i++)
				{
					curfil = pfilter[i];
					if(!curfil.equalsIgnoreCase("_"))
					{
						if(first)
						{
							first = false;
							request += " and (";
						}
						request += "pfrom like ? or ";
						len++;
					}
				}
				if(len != 0)
					request = request.substring(0, request.length()-4);
				if(!first)
					request += ")";
				len += 2;
				
				// FILL REQUEST ARGS ARRAY
				Object[] args = new Object[len];
				args[0] = limits[0];
				args[1] = limits[1];
				int cc = 2;
				for(int i = 0 ; i < pfilter.length ; i++)
				{
					curfil = pfilter[i];
					if(!curfil.equalsIgnoreCase("_"))
					{
						args[cc] = curfil;
						cc++;
					}
				}

				// EXECUTE REQUEST
				rs = (ResultSet)db.request2(DataBase.TSELECT, request, args);
				while(rs.next())
				{
					treatResultSet(cpt, rs);
					cpt++;
				}
			}
			outlet(OUT_NUM,cpt);
			outList();
			done("Filter Request");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}	
	
	private void treatResultSet(int cpt, ResultSet rs) throws SQLException
	{
		int indice = rs.getInt("indice");
		selBuf.add(indice);
		
		if(!enablecellblock)
			return;
		String filename = rs.getString("filename");
		String phrase = rs.getString("phrase");
		String from = rs.getString("pfrom");
		String to = rs.getString("pto");
		int intens = rs.getInt("intens");
		outlet(OUT_CELLBLOCK, Atom.parse("set 0 "+cpt+" "+indice));
		outlet(OUT_CELLBLOCK, Atom.parse("set 1 "+cpt+" "+filename));
		outlet(OUT_CELLBLOCK, Atom.parse("set 2 "+cpt+" "+phrase));
		outlet(OUT_CELLBLOCK, Atom.parse("set 3 "+cpt+" "+from));
		outlet(OUT_CELLBLOCK, Atom.parse("set 4 "+cpt+" "+to));
		outlet(OUT_CELLBLOCK, Atom.parse("set 5 "+cpt+" "+intens));
	}
	
	public void createBuffers()
	{
		MaxBox b = this.getParentPatcher().getNamedBox("buf");
		if(b != null && b.isPatcher())
		{
			MaxPatcher p = b.getSubPatcher();
			int x = 5;
			int y = 5;
			String filename;
			ResultSet rs;
			int cpt = 0;
			try
			{
				rs = (ResultSet)db.request(DataBase.TSELECT, "select filename from memory");
				while(rs.next())
				{
					filename = rs.getString("filename");
					p.newDefault(x, y, "buffer~", Atom.parse(filename+" "+filename));
					y += 20;
					cpt++;
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			outlet(OUT_NUM,cpt);
			done("Create Buffers");
		}
	}	
	
	public void initBufGran()
	{
		String filename;
		ResultSet rs;
		int cpt = 0;
		try
		{
			rs = (ResultSet)db.request(DataBase.TSELECT, "select filename from memory");
			while(rs.next())
			{
				filename = rs.getString("filename");
				outlet(OUT_GRANUL, Atom.parse("set "+cpt+" "+filename));
				cpt++;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		outlet(OUT_NUM,cpt);
		done("Init BufGranul~");
	}
	
	public void initColl()
	{
		outlet(OUT_COLL,Atom.parse("clear"));
		String filename;
		int indice;
		ResultSet rs;
		int cpt = 0;
		try
		{
			rs = (ResultSet)db.request(DataBase.TSELECT, "select * from memory");
			while(rs.next())
			{
				filename = rs.getString("filename");
				indice = rs.getInt("indice");
				outlet(OUT_COLL, new Atom[]{Atom.newAtom("store"),Atom.newAtom(indice),Atom.newAtom(MSPBuffer.getLength(filename))});
				cpt++;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		outlet(OUT_NUM,cpt);
		done("Init Coll");
		
	}

	private void outList()
	{
		int[] list = new int[selBuf.size()];
		for(int i = 0 ; i < selBuf.size() ; i++)
			list[i] = selBuf.get(i);
		outlet(OUT_LIST, Atom.newAtom(list));
	}

	public void update(Atom[] args)
	{
		if(args.length == 3)
		{
			String req = "update memory set "+args[0].toString()+"=(?) where indice = \'"+args[2].toInt()+"\'";
			db.request(DataBase.TUPDATE, req, args[1].toString());
		}
	}
}
