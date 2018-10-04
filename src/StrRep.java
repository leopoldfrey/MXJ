import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

import lf.LfObject;

public class StrRep extends LfObject {

	public StrRep(Atom[] atoms)
	{
		version = 0.1f;
		build = "14/01/18";
		INLET_ASSIST = new String[] { "String to be changed." };
		OUTLET_ASSIST = new String[] { "Result String." };
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] { DataTypes.ALL };

		init(0,false,atoms);
	}

	public void anything(String message, Atom[] atoms)
	{
		String result = "";
		if (message != null && !message.equalsIgnoreCase(""))
		{
			if (atoms != null && !message.equalsIgnoreCase(""))
			{
				result = message.concat(" " + Atom.toOneString(atoms));
				outlet(0, result.toLowerCase().replaceAll("t'en ", "tan ").replaceAll("n'en as", "nan. na").replaceAll("’", "").replaceAll("ô", "o").replaceAll("î", "i").replaceAll("ù", "u").replaceAll("'", "").replaceAll(",", "").replaceAll("-", " ").replaceAll("\"", "").replaceAll("cest a", "cètt a").replaceAll("cest e", "cètt e").replaceAll("cest o", "cètt o").replaceAll("cest i", "cètt i").replaceAll("cest u", "cètt u").replaceAll("cest ", "cè ").replaceAll("quest ", "qè ").replaceAll("nest ", "nè ").replaceAll("disons ", "disons, ").replaceAll("mennuie", "ment nuit").replaceAll("tennuie", "tant nuit"));
			}
			
		}
	}
}
