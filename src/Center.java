import java.awt.Dimension;

import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxWindow;

import lf.LfObject;

public class Center extends LfObject {
	public Center() {
		version = 0.1f;
		build = "15/10/07";
		INLET_TYPES = new int[] { DataTypes.ALL };
		OUTLET_TYPES = new int[] {};
		INLET_ASSIST = new String[] { "Center" };
		OUTLET_ASSIST = new String[] {};
		init();
	}

	public void bang()
	{
		center();
	}
	
	public void center() {
		MaxWindow mw = this.getParentPatcher().getWindow();
		int[] siz = mw.getSize();

		Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		int x = (d.width - siz[0]) / 2;
		int y = (d.height - siz[1]) / 2;
		mw.setLocation(x, y, x + siz[0], y + siz[1]);
	}
}
