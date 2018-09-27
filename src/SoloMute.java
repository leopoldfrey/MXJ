

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public class SoloMute extends MaxObject
{
	// @attributes
	private int tracksNb;
	private int specialKey;
	private int solo[];
	private int mute[];
	private int gates[];
	private int monitor[];
	public SoloMute(Atom[] args)
	{
		if (args.length == 0)
		{
			// arg check
//			error();
			bail("Missing Argument : (int) Tracks Number");
		} else
		{
			// init
			if (args[0].isInt())
			{
				tracksNb = args[0].toInt();
				specialKey = 0;
				solo = new int[tracksNb];
				mute = new int[tracksNb];
				gates = new int[tracksNb];
				monitor = new int[tracksNb];
				init();
				for (int i = 0; i < tracksNb; i++)
				{
					monitor[i] = 0;
				}
				String inlets = "";
				String outlets = "";
				for (int i = 0; i < tracksNb; i++)
				{
					inlets += "iii";
				}
				outlets = inlets + "l";
				inlets += "i";
				declareTypedIO(inlets, outlets);
				for (int i = 0; i < tracksNb; i++)
				{
					setInletAssist(3 * i, "Solo " + (i + 1) + " in");
					setInletAssist(3 * i + 1, "Mute " + (i + 1) + " in");
					setInletAssist(3 * i + 2, "Monitor " + (i + 1) + " in");
					setOutletAssist(3 * i, "Solo " + (i + 1) + " out");
					setOutletAssist(3 * i + 1, "Mute " + (i + 1) + " out");
					setOutletAssist(3 * i + 2, "Monitor " + (i + 1) + " out");
				}
				setInletAssist(tracksNb * 3, "Special Key");
				setOutletAssist(tracksNb * 3, "Gates list out");
				createInfoOutlet(false);
				post("Solomute v0.1 : " + tracksNb + " tracks");
			} else
			{
				error("First argument must be an int");
			}
		}
	}
	private void init()
	{
		for (int i = 0; i < tracksNb; i++)
		{
			solo[i] = 0;
			mute[i] = 0;
			gates[i] = 1;
		}
	}
	public void inlet(int v)
	{
		if (getInlet() == 3 * tracksNb)
		{
			if (specialKey != v)
			{
				specialKey = v;
			}
		} else
		{
			int trackNb = getInlet() / 3;
			boolean soloB = (getInlet() - (trackNb * 3) == 0 ? true : false);
			// solo
			if (soloB)
			{
				// post("soloB");
				// touche alt enfonc�e
				if (specialKey == 1)
				{
					if (v == 1)
					{
						for (int i = 0; i < tracksNb; i++)
						{
							if (i != trackNb)
							{
								solo[i] = 0;
								mute[i] = 1;
								gates[i] = 0;
							} else
							{
								solo[i] = 1;
								mute[i] = 0;
								gates[i] = 1;
							}
						}
					} else
					{
						init();
					}
					// touche alt non enfonc�e
				} else
				{
					solo[trackNb] = v;
					if (v == 1)
					{
						// on met celle-ci en solo
						gates[trackNb] = 1;
						mute[trackNb] = 0;
						// on mute les pistes dont solo est off
						for (int k = 0; k < tracksNb; k++)
						{
							if (solo[k] == 0 && k != trackNb)
							{
								gates[k] = 0;
								mute[k] = 1;
							}
						}
					} else
					{
						int soloSum = 0;
						for (int k = 0; k < tracksNb; k++)
						{
							soloSum += solo[k];
						}
						// si pas d'autre solo > toutes tracks unmute, sinon on
						// mute celle-ci
						if (soloSum == 0)
						{
							init();
						} else
						{
							mute[trackNb] = 1;
							gates[trackNb] = 0;
						}
					}
				}
			} else
			{
				boolean muteB = (getInlet() - (trackNb * 3) == 1 ? true : false);
				// mute
				if (muteB)
				{
					// post("muteB");
					// touche alt enfonc�e
					if (specialKey == 1)
					{
						muteAll(v);
					} else
					{
						if (v == 1)
						{
							mute[trackNb] = 1;
							solo[trackNb] = 0;
							gates[trackNb] = 0;
						} else
						{
							mute[trackNb] = 0;
							gates[trackNb] = 1;
							int soloSum = 0;
							for (int k = 0; k < tracksNb; k++)
							{
								soloSum += solo[k];
							}
							// si d'autre solo > celle-ci aussi
							if (soloSum > 0)
							{
								solo[trackNb] = 1;
							}
						}
					}
					// monitor
				} else
				{
					// post("monitorB");
					if (specialKey == 1)
					{
						for (int i = 0; i < tracksNb; i++)
						{
							if (i == trackNb)
							{
								monitor[i] = v;
							} else
							{
								monitor[i] = 1 - v;
							}
						}
					} else
					{
						monitor[trackNb] = v;
					}
				}
			}
			outAll();
		}
	}
	private void outAll()
	{
		for (int i = 0; i < tracksNb; i++)
		{
			outlet(3 * i, solo[i]);
			outlet(3 * i + 1, mute[i]);
			outlet(3 * i + 2, monitor[i]);
		}
		outlet(3 * tracksNb, gates);
	}
	private void muteAll(int k)
	{
		for (int i = 0; i < tracksNb; i++)
		{
			mute[i] = k;
			solo[i] = 0;
			gates[i] = 1 - k;
		}
	}
}
