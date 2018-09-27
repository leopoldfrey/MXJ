import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxSystem;

import lf.LfObject;

public class PattrWrapper extends LfObject
{
	private static final int PRESTORE_ACTION_NONE = 0;
	private static final int PRESORE_ACTION_NEW = 1;
	private static final int PRESTORE_ACTION_UPDATE = 2;
	private static final int PRESTORE_ACTION_NEW2 = 3;

	private static final int IN_DEFAULT = 0;
	private static final int IN_PATTR = 1;
	private static final int IN_MENUINDEX = 2;
	private static final int IN_MENUEVAL = 3;
	private static final int IN_BOULES = 4;
	private static final int OUT_PATTR = 0;
	private static final int OUT_MENU = 1;
	private static final int OUT_STORE = 2;
	private static final int OUT_BOULES = 3;

	private static final int CHECKITEM_MAX4 = 165;
	private static final int CHECKITEM_MAX5 = 8226;
	private int setcheck = 8226;
	
	private boolean enable_fill_menu = false;
	private boolean first_fill = false;
	
	private boolean recallsym = false;
	private int recallsym_index = -1;
	private String recallsym_name = "";
	
	private int current_preset = 0;
	private String current_lib_name = "default";
	private String current_preset_name = "";
	
	private boolean autowrite = false;
	private boolean prestore = false;
	private boolean map = false;
	private int prestore_action = PRESTORE_ACTION_NONE;
	
	// boules
	private float posx = 0, posy = 0;
	private boolean flag_newboule = false;
	private boolean flag_updboule = false;
	
	public Vector<String> pstname = new Vector<String>();
	// Maxbug with renumber fix
	private int current_max_preset = 0;
	
	public static Vector<PattrWrapper> pattrwrapper_system = new Vector<PattrWrapper>();
	
	public PattrWrapper(Atom[] atoms)
	{
		version = 0.6f;
		build="12/04/2012";
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.INT, DataTypes.ALL , DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL };
		INLET_ASSIST = new String[] { "messages to wrapper", "from pattrstorage", "(int) ubumenu item index", "(anything) evaluated ubumenu item", "from boules" };
		OUTLET_ASSIST = new String[] { "to pattrstorage" , "to ubumenu" , "bang before storing", "to boules" };
	
		init();
		
		setcheck = /*maxversion[0] == 4 ? CHECKITEM_MAX4 : //*/CHECKITEM_MAX5;
		
		declareAttribute("autowrite");
		declareAttribute("prestore");
		declareAttribute("map");
		
		pattrwrapper_system.add(this);
	}
	
	public void notifyDeleted()
	{
		pattrwrapper_system.remove(this);
	}
	
	public void inlet(int i)
	{
		switch(getInlet())
		{
		case IN_DEFAULT :
		case IN_PATTR :
			outlet(OUT_PATTR,i);
			updateCurrentMenuItem(i);
			break;
		case IN_MENUINDEX :
			menuint(i);
			break;
		}
	}
	
	public void inlet(float f)
	{
		switch(getInlet())
		{
		case IN_DEFAULT :
		case IN_PATTR :
			outlet(OUT_PATTR,f);
			updateCurrentMenuItem(f);
			break;
		}
	}
	
	private void updateCurrentMenuItem(float f)
	{
		if(f < 1)
			return;
		int i = (int)f;
		f -= i;
		current_preset = (f > 0.5f ? i+1 : i);
		outlet(OUT_MENU,Atom.parse("set "+current_preset));
		outlet(OUT_MENU,"clearchecks");
		outlet(OUT_MENU,Atom.parse("checkitem "+current_preset+" 1"));
	}
	
	private void updateCurrentMenuItem(int i)
	{
		if(i < 1)
			return;
		current_preset = i;
		outlet(OUT_MENU,Atom.parse("set "+current_preset));
		outlet(OUT_MENU,"clearchecks");
		outlet(OUT_MENU,Atom.parse("checkitem "+current_preset+" 1"));
	}
	
	public void list(Atom[] list)
	{
		switch(getInlet())
		{
		case IN_DEFAULT :
		case IN_PATTR :
			outlet(OUT_PATTR,list);
			break;
		case IN_MENUEVAL :
			menueval(list);
			break;
		case IN_BOULES :
			posx = list[0].toFloat();
			posy = list[1].toFloat();
			break;
		}
	}
	
	public void anything(String message, Atom[] args)
	{
		switch(getInlet())
		{
		case IN_DEFAULT :
			outlet(OUT_PATTR,message,args);
			break;
		case IN_PATTR :
			pattranything(message, args);
			break;
		case IN_MENUEVAL :
			menueval(concat(message,args));
			break;
		case IN_BOULES :
			bouleanything(message,args);
			break;
		}
	}

	// BOULES MESSAGES IN
	
	private void bouleanything(String message, Atom[] args)
	{
		if(!map)
			return;
		if(message.equalsIgnoreCase("init"))
		{
			outlet(OUT_BOULES, Atom.newAtom("clear"));
			outlet(OUT_BOULES, Atom.parse("source 1 0. 0."));
			flag_updboule = true;
			outlet(OUT_PATTR,Atom.parse("getslotnamelist 1"));
		}
	}
	
	// PATTR MESSAGES IN
	
	private void pattranything(String message, Atom[] args)
	{
		if(message.equalsIgnoreCase("slotname"))
		{
			if(flag_updboule)
			{
				if(args[0].toString().equalsIgnoreCase("done"))
					flag_updboule = false;
				else 
					outlet(OUT_BOULES,Atom.parse("boule "+args[0].toInt()+" "+(Math.random()*2.-1.)+" "+(Math.random()*2.-1.)+" 0.5 0.5 3 "+args[1].toString()));
			}
			if(recallsym)
			{
				if(args[0].toString().equalsIgnoreCase("done"))
				{
					recallsym = false;
					if(recallsym_index == -1)
						recallsym_index = 0;
					dorecallsym();
				}
				else if(args[1].toString().equalsIgnoreCase(recallsym_name))
					recallsym_index = args[0].toInt();
			}
			else if(args.length == 1 && args[0].toString().equalsIgnoreCase("done"))
			{
				enable_fill_menu = false;
				outlet(OUT_MENU, Atom.parse("set "+current_preset));
			}
			else if(enable_fill_menu)
				if(first_fill)
				{
					pstname.add("("+current_lib_name+")");
					outlet(OUT_MENU, Atom.parse("append ("+current_lib_name+")"));
					first_fill = false;
					current_max_preset = 0;
				}
				else if(args[1].toString().equalsIgnoreCase("(undefined)"))
				{
					pstname.add(args[1].toString());
					current_max_preset = args[0].toInt();
					outlet(OUT_MENU, Atom.parse("append ("+args[0].toString()+" - "+args[1].toString()+")"));
				}
				else if(args[1].toString().equalsIgnoreCase("<(unnamed)>"))
				{
					pstname.add("<(unnamed)>");
					current_max_preset = args[0].toInt();
					outlet(OUT_MENU, Atom.parse("append "+args[0].toString()+" - (unnamed)"));
				} else {
					pstname.add(args[1].toString());
					current_max_preset = args[0].toInt();
					outlet(OUT_MENU, Atom.parse("append "+args[0].toString()+" - "+args[1].toString()));
				}
		}
		else if(message.equalsIgnoreCase("current"))
		{
			int tmp_curpst = args[0].toInt();
			if(tmp_curpst != -1)
			{
				current_preset = tmp_curpst;
				outlet(OUT_MENU, Atom.parse("set " + current_preset));
				outlet(OUT_MENU, "clearchecks");
				outlet(OUT_MENU, Atom.parse("checkitem " + current_preset + " 1"));
				if (flag_newboule)
				{
					outlet(OUT_BOULES, Atom.parse("boule " + current_preset + " " + posx + " " + posy + " 0.5 0.5 3 " + current_preset_name));
					flag_newboule = false;
				}
			} else if(current_preset != -1){
				//post("HERE I AM RECALL "+current_preset);
				//outlet(OUT_PATTR, Atom.newAtom(current_preset));
			}
		}
		else if(message.equalsIgnoreCase("read"))
		{
			if(args[args.length-1].toInt() == 1)
			{
				current_lib_name = args[0].toString();
				update();
				outlet(OUT_PATTR,"getcurrent");
			}
		}
		else if(message.equalsIgnoreCase("write"))
		{
			if(args[args.length-1].toInt() == 1)
				current_lib_name = args[0].toString();
		}
		else if(message.equalsIgnoreCase("recall"))
		{
			current_preset = args[0].toInt();
		}
	}
	
	// UBUMENU MESSAGES IN

	private void menuint(int i)
	{
		current_preset = i;
		outlet(OUT_PATTR, current_preset);
		outlet(OUT_MENU,"clearchecks");
		outlet(OUT_MENU,Atom.parse("checkitem "+current_preset+" 1"));
	}
	
	private void menueval(Atom args[])
	{
		if(args.length >= 3 && !args[0].toString().equalsIgnoreCase("disabled"))
			current_preset_name = Atom.toOneString(Atom.removeFirst(args,2));
	}
	
	// UPDATE UBUMENU FROM PATTRSTORAGE
	
	public void update()
	{
		outlet(OUT_MENU, "clearchecks");
		outlet(OUT_MENU, Atom.parse("setcheck "+setcheck));
		pstname.clear();
		outlet(OUT_MENU, "clear");
		first_fill = true;
		enable_fill_menu = true;
		outlet(OUT_PATTR, "getslotnamelist");
	}
	
	// WAIT FOR GO IN PRESTORE MODE
	
	public void go()
	{
		if(prestore)
		{
			switch(prestore_action)
			{
			case PRESTORE_ACTION_NONE:
				return;
			case PRESORE_ACTION_NEW:
				newGo();
				prestore_action = PRESTORE_ACTION_NONE;
				return;
			case PRESTORE_ACTION_UPDATE:
				updateGo();
				prestore_action = PRESTORE_ACTION_NONE;
				return;
			case PRESTORE_ACTION_NEW2:
				newGoThreaded();
				prestore_action = PRESTORE_ACTION_NONE;
				return;
			}
		}
	}
	
	// PRESET FUNCTIONS

	public void preset(String s)
	{
		if(s.equalsIgnoreCase("new")) {
			newPreset();
		} else if(s.equalsIgnoreCase("rename")) {
			renamePreset();
		} else if(s.equalsIgnoreCase("update")) {
			updatePreset();
		} else if(s.equalsIgnoreCase("delete")) {
			deletePreset();
		} else if(s.equalsIgnoreCase("up")) {
			presetUp();
		} else if(s.equalsIgnoreCase("down")) {
			presetDown();
		}
	}
	
	public void preset(Atom[] args)
	{
		if(args.length == 1)
		{
			preset(args[0].toString());
			return;
		}
		
		if(args[0].toString().equalsIgnoreCase("new"))
		{
			newPreset(Atom.toOneString(Atom.removeFirst(args)));
		}
	}
	
	// new preset

	private void newPreset(String name)
	{
		current_preset_name = name;
		if(prestore)
		{
			prestore_action = PRESTORE_ACTION_NEW2;
			outletBang(OUT_STORE);
			return;
		}
		newGoThreaded();
	}
	
	private void newPreset()
	{
		if(prestore)
		{
			prestore_action = PRESORE_ACTION_NEW;
			outletBang(OUT_STORE);
			return;
		}
		newGo();
	}

	private void newGo() {
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				MaxSystem.nextWindowIsModal();
				String tmp = (String)JOptionPane.showInputDialog(
						null,
						"Enter a name for this new preset",
                        "New Preset",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        "preset name");
				if(tmp != null && !tmp.equalsIgnoreCase(""))
				{
					current_preset_name = tmp;
					newGoThreaded();
				}
			}
		});
	}
	
	private void newGoThreaded()
	{
		flag_newboule = true;
		current_max_preset++;
		pstname.add(current_preset_name);
		current_preset = current_max_preset;
		// Maxbug fix
		outlet(OUT_PATTR,concat(Atom.parse("store "+current_max_preset),current_preset_name));
		/*
		outlet(OUT_PATTR,concat(Atom.parse("store 200"),current_preset_name));
		outlet(OUT_PATTR,"renumber");//*/
		update();
		if(autowrite)
			outlet(OUT_PATTR,"writeagain");
		outlet(OUT_PATTR,"getcurrent");
	}
	
	// update preset

	private void updatePreset()
	{
		if(prestore)
		{
			prestore_action = PRESTORE_ACTION_UPDATE;
			outletBang(OUT_STORE);
			return;
		}
		updateGo();
	}

	private void updateGo() {
		outlet(OUT_PATTR,Atom.parse("store "+current_preset));
		if(autowrite)
		{
			outlet(OUT_PATTR,"writeagain");
			outlet(OUT_PATTR,"getcurrent");
		}
	}
	
	// rename preset

	private void renamePreset()
	{
		if(current_preset < 1)
			return;
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				MaxSystem.nextWindowIsModal();
				String tmp = (String)JOptionPane.showInputDialog(
						null,
                        "Enter a new name for preset n�"+current_preset,
                        "Rename Preset",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        current_preset_name);
				if(tmp != null && !tmp.equalsIgnoreCase(""))
				{
					current_preset_name = tmp;
					renameGo();
				}
			}
		});
	}
	
	private void renameGo()
	{
		outlet(OUT_PATTR,concat(Atom.parse("slotname "+current_preset),current_preset_name));
		update();
		outlet(OUT_MENU,"clearchecks");
		outlet(OUT_MENU,Atom.parse("checkitem "+current_preset+" 1"));
		if(autowrite)
		{
			outlet(OUT_PATTR,"writeagain");
			outlet(OUT_PATTR,"getcurrent");
		}
	}

	// delete preset

	private void deletePreset()
	{
//		System.err.println("Preset Delete is disabled due to a bug in Max 5.0.7");
	
		if(current_preset < 1)
			return;
		outlet(OUT_PATTR,Atom.parse("remove "+current_preset));
		current_preset_name = "";
		current_preset = 0;
		update();
		outlet(OUT_MENU,Atom.parse("set 0"));
		if(autowrite)
			outlet(OUT_PATTR,"writeagain");//*/
	}
	
	// preset orders functions

	private void presetUp()
	{
//		System.err.println("Preset Up is disabled due to a bug in Max 5.0.7");
		
		if(current_preset <= 1)
			return;
		outlet(OUT_PATTR,Atom.parse("remove "+current_preset));
		current_preset--;
		outlet(OUT_PATTR,Atom.parse("insert "+current_preset));
		outlet(OUT_PATTR,concat(Atom.parse("slotname "+current_preset),current_preset_name));
		update();
		if(autowrite)
			outlet(OUT_PATTR,"writeagain");
		outlet(OUT_PATTR,"getcurrent");//*/
	}
	
	private void presetDown()
	{
//		System.err.println("Preset Down is disabled due to a bug in Max 5.0.7");
		
		outlet(OUT_PATTR,Atom.parse("remove "+current_preset));
		current_preset++;
		outlet(OUT_PATTR,Atom.parse("insert "+current_preset));
		outlet(OUT_PATTR,concat(Atom.parse("slotname "+current_preset),current_preset_name));
		update();
		if(autowrite)
			outlet(OUT_PATTR,"writeagain");
		outlet(OUT_PATTR,"getcurrent");//*/
	}
	
	// preset symbol recall
	
	public void recallsym(String pstna)
	{
		recallsym = true;
		recallsym_name = pstna;
		recallsym_index = -1;
		outlet(OUT_PATTR,Atom.parse("getslotnamelist 1"));
	}
	
	private void dorecallsym()
	{
		if(recallsym_index > 0)
		{
			outlet(OUT_PATTR,Atom.parse("recall "+recallsym_index));
			updateCurrentMenuItem(recallsym_index);
		}
		else
			error("PattrWrapper : preset \""+recallsym_name+"\" doesn't exist.");
	}
  	
	// UNDO / REDO
	
	public void temporary(String s)
	{
		if(s.equalsIgnoreCase("recall"))
			outlet(OUT_PATTR, Atom.parse("recall 0"));
		else if(s.equalsIgnoreCase("store"))
			outlet(OUT_PATTR, Atom.parse("store 0"));
	}
	
	// LIBRARY FUNCTIONS
	
	public void library(String s)
	{
		if(s.equalsIgnoreCase("open")) {
			outlet(OUT_PATTR,"read");
		} else if(s.equalsIgnoreCase("reopen")) {
			outlet(OUT_PATTR,"readagain");
		} else if(s.equalsIgnoreCase("save")) {
			outlet(OUT_PATTR,"writeagain");
		} else if(s.equalsIgnoreCase("saveas")) {
			outlet(OUT_PATTR,"write");
		} else if(s.equalsIgnoreCase("clear")) {
			current_preset_name = "";
			current_preset = 0;
			outlet(OUT_PATTR,"clear");
			pstname.clear();
			outlet(OUT_MENU,"clear");
			update();
		}
		outlet(OUT_PATTR,"getcurrent");
	}
}
