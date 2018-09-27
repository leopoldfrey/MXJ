import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.SwingUtilities;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxBox;
import com.cycling74.max.MaxPatcher;

import lf.LfObject;
import lf.db.DataBase;

public class Foule extends LfObject
{
	private static final int OUT_CELLBLOCK = 0;
	private static final int OUT_GRANUL = 1;
	private static final int OUT_UMENU = 2;
	private static final int OUT_DONE = 3;
	private static final int OUT_NUM = 4;
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
	
	public Foule(Atom[] args)
	{
		version = 0.2f;
		build = "11/06/07";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL };
		INLET_ASSIST = new String[] { "Command in" };
		OUTLET_ASSIST = new String[] { "To jit.cellblock", "To bufgranul~", "To ubumenu", "Query done bang", "Number of results" };
		
		init();
		
		db = new DataBase("localhost",(short)3306,"lab100","lab100","lab100");
	}
	
	public void notifyDeleted()
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
				done();
				
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
		if(db.connect())
		{
			System.out.print("Clearing \'memory\'...");
			db.request(DataBase.TTRUNCATE, "truncate table memory");
			System.out.println("...done");
		}
	}
	
	private void done()
	{
		outletBang(OUT_DONE);
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
		outlet(OUT_UMENU,Atom.parse("clear"));
		outlet(OUT_CELLBLOCK,Atom.parse("clear all"));
		ResultSet rs;
		if(pfilter == null)
		{
			int cpt = 0;
			try
			{
				rs = (ResultSet)db.request(DataBase.TSELECT, "select * from memory where intens >= ? and intens <= ?", limits[0], limits[1]);
				while(rs.next())
				{
					treatResultSet(cpt, rs);
					cpt++;
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			outlet(OUT_NUM,cpt);
			done();
		} else {
			int cpt = 0;
			for(String curfil : pfilter)
			{
				if(!curfil.equalsIgnoreCase("_"))
				{
					try
					{
						rs = (ResultSet)db.request(DataBase.TSELECT, "select * from memory where pfrom like ? and intens >= ? and intens <= ?",curfil, limits[0], limits[1]);
						while(rs.next())
						{
							treatResultSet(cpt, rs);
							cpt++;
						}
					}
					catch (SQLException e)
					{
						e.printStackTrace();
					}
				}
			}
			outlet(OUT_NUM,cpt);
			done();
		}
	}	
	
	private void treatResultSet(int cpt, ResultSet rs) throws SQLException
	{
		int indice = rs.getInt("indice");
		String filename = rs.getString("filename");
		String phrase = rs.getString("phrase");
		String from = rs.getString("pfrom");
		String to = rs.getString("pto");
		int intens = rs.getInt("intens");
		outlet(OUT_GRANUL, Atom.parse("set "+cpt+" "+filename));
		outlet(OUT_UMENU, Atom.parse("append "+filename));
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
			done();
		}
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
