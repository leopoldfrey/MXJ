package test;
import java.io.File;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;


public class FileNameEx extends MaxObject
{
	public FileNameEx()
	{
		createInfoOutlet(false);
		declareOutlets(new int[] { DataTypes.ALL, DataTypes.ALL });
	}
	
	public void bang()
	{
		outlet(1,correctPath(MaxSystem.openDialog()));
//		outlet(1,MaxSystem.nameConform(MaxSystem.openDialog(),MaxSystem.PATH_STYLE_MAX,MaxSystem.PATH_TYPE_ABSOLUTE));
	}
	
	public void root(String dir)
	{
		File root = new File(MaxSystem.maxPathToNativePath(dir));
		if (!root.exists() || !root.isDirectory())
		{
			error("(mxj filebrowse) Invalid root directory argument");
			return;
		}
		dump(new TreeNode2(root));
	}
	
	private void dump(TreeNode2 t)
	{
		outlet(0,Atom.newAtom("clear"));
		outlet(0,Atom.parse("append "+t.toString()));
		for(TreeNode2 c : t.children)
			outlet(0,Atom.parse("append \t"+c.toString()));
	}
	
	public static String correctPath(String path)
	{
		if(path.indexOf("/Volumes/") != -1)
		{
			path = path.substring(path.indexOf("Volumes")+8);
			int ind = path.indexOf("/");
			path = path.substring(0,ind)+":"+path.substring(ind);
		}
		return path;
	}
}

class TreeNode2
{
	File f;
	TreeNode2[] children;

	// x and y will be set when we render the tree
	TreeNode2(File file)
	{
		this.f = file;
		expand();
	}
	
	public TreeNode2 expand()
	{
		if (!f.isDirectory())
			return this;
		File[] files = f.listFiles();
		if (files != null && files.length > 0)
		{
			// get all but hidden files && . && ..
			int real_length = 0;
			for (int i = 0; i < files.length; i++)
				if (!files[i].getName().startsWith(".") && !files[i].isHidden())
					real_length++;
			children = new TreeNode2[real_length];
			int c = 0;
			for (int i = 0; i < files.length; i++)
				if (!files[i].getName().startsWith(".") && !files[i].isHidden())
				{
					children[c] = new TreeNode2(files[i]);
					c++;
				}
		} else {
			children = new TreeNode2[0];
		}
		return this;
	}
	
	public String toString()
	{
		return FileNameEx.correctPath(f.getAbsolutePath());
//		return MaxSystem.nameConform(f.getAbsolutePath(),MaxSystem.PATH_STYLE_MAX,MaxSystem.PATH_TYPE_ABSOLUTE);
	}
}
