package lf;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;

public class pattrwrap2 extends MaxObject
{
	private static final int PRESTORE_ACTION_NONE = 0;
	private static final int PRESORE_ACTION_NEW = 1;
	private static final int PRESTORE_ACTION_UPDATE = 2;
	private static final int PRESTORE_ACTION_NEW2 = 3;

	private static final int IN_DEFAULT = 0;
	private static final int IN_PATTR = 1;
//	private static final int IN_MENUINDEX = 2;
	private static final int IN_MENUEVAL = 2;
	private static final int OUT_PATTR = 0;
	private static final int OUT_MENU = 1;
	private static final int OUT_STORE = 2;

	private static final int CHECKITEM_MAX4 = 165;
	private static final int CHECKITEM_MAX5 = 8226;
	private int setcheck = CHECKITEM_MAX5;
	
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
	private boolean disp_num = false;
	private int prestore_action = PRESTORE_ACTION_NONE;
		
	public Vector<String> pstname = new Vector<String>();
	private int current_max_preset = 0;
	
	public float version = 0.4f;
	protected String build = "12/04/2011";
	protected int[] INLET_TYPES = { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL };
	protected int[] OUTLET_TYPES = { DataTypes.ALL, DataTypes.ALL, DataTypes.ALL };
	protected String[] INLET_ASSIST = { "messages to wrapper", "from pattrstorage", "(anything) evaluated ubumenu item" };
	protected String[] OUTLET_ASSIST = { "to pattrstorage" , "to ubumenu" , "bang before storing" };
	
	public pattrwrap2(Atom[] atoms)
	{	
		declareInlets(INLET_TYPES);
		declareOutlets(OUTLET_TYPES);
		setInletAssist(INLET_ASSIST);
		setOutletAssist(OUTLET_ASSIST);
		createInfoOutlet(false);
				
		declareAttribute("autowrite");
		declareAttribute("prestore");
	}
	
	public void version()
	{
		post("lf.pattrwrap © 2011 Leopold Frey / version:" + version + " / build: " + build);
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
//		case IN_MENUINDEX :
//			menuint(i);
//			break;
		}
	}
	
	public void display_number(int v)
	{
		disp_num = v > 0;
		update();
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
				} else {
					pstname.add(args[1].toString());
					current_max_preset = args[0].toInt();
					if(disp_num)
					{
						outlet(OUT_MENU, new Atom[]{ Atom.newAtom("append"), args[1], Atom.newAtom("("+current_max_preset+")")});
					} else {
						outlet(OUT_MENU, new Atom[]{ Atom.newAtom("append"), args[1]});
					}
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
			} else if(current_preset != -1){
				outlet(OUT_PATTR, Atom.newAtom(current_preset));
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
			if(args.length == 3)
			{
				current_preset = args[2].toFloat() >= 0.5 ? args[1].toInt() : args[0].toInt();
				updateCurrentMenuItem(current_preset);
			} else if(current_preset != args[0].toInt())
			{
				current_preset = args[0].toInt();
				updateCurrentMenuItem(current_preset);
			}
		}
	}
	
	// UBUMENU MESSAGES IN
	
	private void menueval(Atom args[])
	{
		if(args.length >= 1)
		{
			String tmpstr = args[0].toString();//Atom.toOneString(args);
			int tmp = pstname.indexOf(tmpstr);
			if(tmp != -1)
			{
				current_preset = tmp;
				current_preset_name = tmpstr;
				recallsym(current_preset_name);		
			}
		}
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
                        "Enter a new name for preset n¡"+current_preset,
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
	
	public void swap(int i1, int i2)
	{
		if(i1 > 0 && i2 > 0 && i1 < pstname.size() && i2 < pstname.size())
		{
			String pst1 = pstname.get(i1);
			String pst2 = pstname.get(i2);
			//post("swap : "+pst1+" "+pst2);
			outlet(OUT_PATTR,new Atom[]{Atom.newAtom("copy"), Atom.newAtom(i1), Atom.newAtom(20000)});
			outlet(OUT_PATTR,new Atom[]{Atom.newAtom("copy"), Atom.newAtom(i2), Atom.newAtom(i1)});
			outlet(OUT_PATTR,new Atom[]{Atom.newAtom("copy"), Atom.newAtom(20000), Atom.newAtom(i2)});
			outlet(OUT_PATTR,new Atom[]{Atom.newAtom("slotname"), Atom.newAtom(i1), Atom.newAtom(pst2)});
			if(disp_num)
			{
				outlet(OUT_MENU,new Atom[]{Atom.newAtom("setitem"), Atom.newAtom(i1), Atom.newAtom(pst2), Atom.newAtom("("+i1+")")});
			} else {
				outlet(OUT_MENU,new Atom[]{Atom.newAtom("setitem"), Atom.newAtom(i1), Atom.newAtom(pst2)});
			}
			pstname.set(i1, pst2);
			outlet(OUT_PATTR,new Atom[]{Atom.newAtom("slotname"), Atom.newAtom(i2), Atom.newAtom(pst1)});
			if(disp_num)
			{
				outlet(OUT_MENU,new Atom[]{Atom.newAtom("setitem"), Atom.newAtom(i2), Atom.newAtom(pst1), Atom.newAtom("("+i2+")")});
			} else {
				outlet(OUT_MENU,new Atom[]{Atom.newAtom("setitem"), Atom.newAtom(i2), Atom.newAtom(pst1)});		
			}
			outlet(OUT_PATTR,new Atom[]{Atom.newAtom("delete"), Atom.newAtom(20000)});
			pstname.set(i2, pst1);
			
			if(current_preset > 0)
			{		
				if(current_preset == i1)
				{
					current_preset = i2;
					current_preset_name = pst1;
					outlet(OUT_PATTR,i2);
					updateCurrentMenuItem(i2);
				}
				else if(current_preset == i2)
				{
					current_preset = i1;
					current_preset_name = pst2;
					outlet(OUT_PATTR,i1);
					updateCurrentMenuItem(i1);
				}
				else
				{
					outlet(OUT_PATTR,current_preset);
					updateCurrentMenuItem(current_preset);
				}
			}

			if(autowrite)
				outlet(OUT_PATTR,"writeagain");
		}		
	}
	
	public void swap(String n1, String n2)
	{
		int i1 = pstname.indexOf(n1);
		int i2 = pstname.indexOf(n2);
		if(i1 > 0 && i2 > 0)
			swap(i1,i2);
		else if (i1 <= 0) {
			error("PattrWrapper : preset \""+n1+"\" doesn't exist.");
		} else {
			error("PattrWrapper : preset \""+n2+"\" doesn't exist.");
		}
	}

	// preset symbol recall
	
	public void recallsym(String pstna)
	{
		// OLD
		//recallsym = true;
		//recallsym_name = pstna;
		//recallsym_index = -1;
		//outlet(OUT_PATTR,Atom.parse("getslotnamelist 1"));

		recallsym_name = pstna;
		recallsym_index = pstname.indexOf(pstna);
		if(recallsym_index > 0)
		{
			outlet(OUT_PATTR,Atom.parse("recall "+recallsym_index));
			updateCurrentMenuItem(recallsym_index);
		}
		else
			error("PattrWrapper : preset \""+recallsym_name+"\" doesn't exist.");
	}
	
	public void recallsym(String pst1, String pst2, float v)
	{
		int p1 = pstname.indexOf(pst1);
		int p2 = pstname.indexOf(pst2);
		if(p1 == -1)
		{
			error("PattrWrapper : preset \""+pst1+"\" doesn't exist.");
			return;
		}
		if(p2 == -1)
		{
			error("PattrWrapper : preset \""+pst2+"\" doesn't exist.");
			return;
		}
		outlet(OUT_PATTR,new Atom[]{Atom.newAtom("recall"), Atom.newAtom(p1), Atom.newAtom(p2), Atom.newAtom(v)});	

		current_preset = (v > 0.5f ? p2 : p1);
		outlet(OUT_MENU,Atom.parse("set "+current_preset));
		outlet(OUT_MENU,"clearchecks");
		outlet(OUT_MENU,Atom.parse("checkitem "+current_preset+" 1"));
	}
	
	public void recallmultisym(Atom[] args)
	{
		if(args.length % 2 != 0)
		{
			error("PattrWrapper : recallmultisym waits for a list a pair preset_name or preset_number + weight");
			return;
		}
		Atom[] out = new Atom[args.length/2];
		Atom aname, aval;
		int c = 0;
		int ai;
		for(int i = 0 ; i < args.length-1 ; i+= 2)
		{
			aname = args[i];
			aval = args[i+1];
			//post(i + " "+ a.toString());
			if(aname.isString())
			{
				ai = pstname.indexOf(aname.toString());
				if(ai == -1)
				{
					error("PattrWrapper : preset \""+recallsym_name+"\" doesn't exist.");
					return;
				}

			} else {
				ai = aname.toInt();
			}
			out[c] = Atom.newAtom(ai+aval.toFloat());

			c++;
		}
		outlet(OUT_PATTR, concat("recallmulti", out));
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
	
	protected static Atom[] concat(Atom[] a, Atom[] b)
	{
		Atom[] tmp = new Atom[a.length + b.length];
		System.arraycopy(a, 0, tmp, 0, a.length);
		System.arraycopy(b, 0, tmp, a.length, b.length);
		return tmp;
	}
	
	protected static Atom[] concat(String s, Atom[] args)
	{
		return concat(new Atom[]{Atom.newAtom(s)},args);
	}
	
	protected static Atom[] concat(Atom[] args, String s)
	{		
		return concat(args,new Atom[]{Atom.newAtom(s)});
	}
}
