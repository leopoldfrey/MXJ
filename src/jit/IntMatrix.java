package jit;

import com.cycling74.jitter.JitterMatrix;

public class IntMatrix
{
	public int[] dim;
	public int[] array;
	public int w = 0;
	public int h = 0;
	public int pl = 0;

	public IntMatrix(JitterMatrix j)
	{
		fromMatrix(j);
	}

	public IntMatrix(int width, int height, int plane)
	{
		w = width;
		h = height;
		pl = plane;
		array = new int[w * h * pl];
	}

	public void fromMatrix(JitterMatrix j)
	{
		dim = j.getDim();
		w = dim[0];
		h = dim[1];
		pl = j.getPlanecount();
		if(array != null && array.length != w * h * pl);
			array = new int[w * h * pl];
		j.copyMatrixToArray(array);
	}

	public void toMatrix(JitterMatrix j)
	{
		j.copyArrayToMatrix(array);
	}

	public int get(int i, int j, int p)
	{
		return array[i * pl + j * w * pl + p];
	}

	public int get(int[] pos, int p)
	{
		return get(pos[0], pos[1], p);
	}

	public int get(int[] pos)
	{
		return get(pos, 0);
	}

	public int get(int i, int j)
	{
		return get(i, j, 0);
	}

	public void set(int i, int j, int p, int v)
	{
		array[i * pl + j * w * pl + p] = v;
	}

	public void set(int i, int j, int p, double v)
	{
		array[i * pl + j * w * pl + p] = (int)Math.max(0, Math.min(v,255));
	}
	
	public void set(int i, int j, int v)
	{
		array[i + j * w] = v;
	}
	
	public void set(int i, int j, double v)
	{
		array[i + j * w] = (int)Math.max(0, Math.min(v,255));
	}

	public void set(int[] pos, int p, int v)
	{
		set(pos[0], pos[1], p, v);
	}

	public void set(int[] pos, int v)
	{
		set(pos, 0, v);
	}
	
	public void setAll(int v)
	{
		for(int i = 0 ; i < array.length ; i++)
			array[i] = v;
	}
}