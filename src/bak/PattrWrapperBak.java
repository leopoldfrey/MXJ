package bak;
//import DialogBox;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

//import javax.swing.SwingUtilities;

import lf.LfObject;

public class PattrWrapperBak extends LfObject
{
	private static final int ACTION_NONE = 0;
	private static final int ACTION_NEW = 1;
	private static final int ACTION_UPDATE = 2;
	private static final int ACTION_NEW2 = 3;
	private static final int IN_DEFAULT = 0;
	private static final int IN_PATTR = 1;
	private static final int IN_MENUINDEX = 2;
	private static final int IN_MENUEVAL = 3;
	private static final int OUT_PATTR = 0;
	private static final int OUT_MENU = 1;
	private static final int OUT_STORE = 2;

	private boolean enable_fill_menu = false;
	private boolean first_fill = false;
	
	private int current_preset = 0;
	private String current_lib_name = "default";
	private String current_preset_name = "";
	
	private boolean autowrite = false;
	private boolean prestore = false;
	private int prestore_action = ACTION_NONE;
	
	public PattrWrapperBak(Atom[] atoms)
	{
		version = 0.1f;
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.INT, DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL };
		INLET_ASSIST = new String[] { "messages to wrapper", "from pattrstorage", "(int) ubumenu item index", "(anything) evaluated ubumenu item" };
		OUTLET_ASSIST = new String[] { "to pattrstorage" , "to ubumenu" , "bang before storing" };
	
		init();
		
		declareAttribute("autowrite");
		declareAttribute("prestore");
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
		}
	}

	// PATTR MESSAGES IN
	
	private void pattranything(String message, Atom[] args)
	{
		if(message.equalsIgnoreCase("slotname"))
		{
			if(args.length == 1 && args[0].toString().equalsIgnoreCase("done"))
			{
				enable_fill_menu = false;
				outlet(OUT_MENU, Atom.parse("set "+current_preset));
			}
			else if(enable_fill_menu)
				if(first_fill)
				{
					outlet(OUT_MENU, Atom.parse("append ("+current_lib_name+")"));
					first_fill = false;
				}
				else if(args[1].toString().equalsIgnoreCase("(undefined)"))
				{
					outlet(OUT_MENU, Atom.parse("append ("+args[0].toString()+" - "+args[1].toString()+")"));
				}
				else if(args[1].toString().equalsIgnoreCase("<(unnamed)>"))
				{
					outlet(OUT_MENU, Atom.parse("append "+args[0].toString()+" - (unnamed)"));
				} else {
					outlet(OUT_MENU, Atom.parse("append "+args[0].toString()+" - "+args[1].toString()));
				}
		}
		else if(message.equalsIgnoreCase("current"))
		{
			current_preset = args[0].toInt();
			outlet(OUT_MENU, Atom.parse("set "+current_preset));
			outlet(OUT_MENU, "clearchecks");
			outlet(OUT_MENU, Atom.parse("checkitem "+current_preset+" 1"));
		}
		else if(message.equalsIgnoreCase("read") || message.equalsIgnoreCase("write"))
		{
			if(args[args.length-1].toInt() == 1)
			{
				current_lib_name = args[0].toString();
				update();
				outlet(OUT_PATTR,"getcurrent");
			}
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
		outlet(OUT_MENU, Atom.parse("setcheck 165"));
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
			case ACTION_NONE:
				return;
			case ACTION_NEW:
				new1();
				prestore_action = ACTION_NONE;
				return;
			case ACTION_UPDATE:
				update1();
				prestore_action = ACTION_NONE;
				return;
			case ACTION_NEW2:
				new2();
				prestore_action = ACTION_NONE;
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
			prestore_action = ACTION_NEW2;
			outletBang(OUT_STORE);
			return;
		}
		new2();
	}
	
	private void newPreset()
	{
		if(prestore)
		{
			prestore_action = ACTION_NEW;
			outletBang(OUT_STORE);
			return;
		}
		new1();
	}

	private void new1() {
//		SwingUtilities.invokeLater(new Runnable(){
//			public void run()
//			{
//				DialogBox d = new DialogBox(new Atom[]{}){
//					public void validate()
//					{
//						close();
//						current_preset_name = getValueReturn();
//						new2();
//					}
//				};
//				d.setSize(180, 120);
//				d.setMessage("Enter a name for this new preset");
//				d.setTitle("New Preset");
//				d.setValue("preset name");
//				d.center();
//				d.select();
//				d.open();
//			}
//		});
	}
	
	private void new2()
	{
		outlet(OUT_PATTR,concat(Atom.parse("store 200"),current_preset_name));
		outlet(OUT_PATTR,"renumber");
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
			prestore_action = ACTION_UPDATE;
			outletBang(OUT_STORE);
			return;
		}
		update1();
	}

	private void update1() {
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
//		SwingUtilities.invokeLater(new Runnable(){
//			public void run()
//			{
//				DialogBox d = new DialogBox(new Atom[]{}){
//					public void validate()
//					{
//						close();
//						current_preset_name = getValueReturn();
//						renameGo();
//					}
//				};
//				d.setSize(180, 120);
//				d.setTitle("Rename Preset");
//				d.setMessage("Enter a new name for preset n�"+current_preset);
//				d.setValue(current_preset_name);
//				d.center();
//				d.select();
//				d.open();
//			}
//		});
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
		if(current_preset < 1)
			return;
		outlet(OUT_PATTR,Atom.parse("remove "+current_preset));
		current_preset_name = "";
		current_preset = 0;
		update();
		outlet(OUT_MENU,Atom.parse("set 0"));
		if(autowrite)
			outlet(OUT_PATTR,"writeagain");
	}
	
	// preset orders functions

	private void presetUp()
	{
		if(current_preset <= 1)
			return;
		outlet(OUT_PATTR,Atom.parse("remove "+current_preset));
		current_preset--;
		outlet(OUT_PATTR,Atom.parse("insert "+current_preset));
		outlet(OUT_PATTR,concat(Atom.parse("slotname "+current_preset),current_preset_name));
		update();
		if(autowrite)
			outlet(OUT_PATTR,"writeagain");
		outlet(OUT_PATTR,"getcurrent");
	}
	
	private void presetDown()
	{
		outlet(OUT_PATTR,Atom.parse("remove "+current_preset));
		current_preset++;
		outlet(OUT_PATTR,Atom.parse("insert "+current_preset));
		outlet(OUT_PATTR,concat(Atom.parse("slotname "+current_preset),current_preset_name));
		update();
		if(autowrite)
			outlet(OUT_PATTR,"writeagain");
		outlet(OUT_PATTR,"getcurrent");
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
			outlet(OUT_MENU,"clear");
			update();
		}
		outlet(OUT_PATTR,"getcurrent");
	}
}
