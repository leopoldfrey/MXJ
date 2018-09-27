package jit;

import com.cycling74.jitter.JitterMatrix;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;

public class Degenerate extends MaxObject
{
	JitterMatrix out;
	IntMatrix input, chemical;
	// private double D2C[];
	private double cA = 0.05;
	private double cB = 20;
	private double cC = 0.5;
	private double cD = 2;
	private double cE = 1;
	private double cF = 1;
	private double cG = 1;
	private double cH = 2;
	private double cI = 3;
	private double cJ = 0;
	private double cK = 250;
	private double cL = 50;
	private double cM = 1;
	int w = 320;
	int h = 240;
	int pl = 1;
	private double A[] = new double[w];
	private double I[] = new double[w];
	private double D2A[] = new double[w];
	private double D2I[] = new double[w];

	public Degenerate()
	{
		declareInlets(new int[] { DataTypes.ALL });
		declareOutlets(new int[] { DataTypes.ALL });
		declareAttribute("cA");
		declareAttribute("cB");
		declareAttribute("cC");
		declareAttribute("cD");
		declareAttribute("cE");
		declareAttribute("cF");
		declareAttribute("cG");
		declareAttribute("cH");
		declareAttribute("cI");
		declareAttribute("cJ");
		declareAttribute("cK");
		declareAttribute("cL");
		declareAttribute("cM");
		out = new JitterMatrix(pl, "char", w, h);
		size(w, h);
		bang();
	}

	public void jit_matrix(String inname)
	{
		JitterMatrix inmat = new JitterMatrix(inname);
		if(inmat.getType().equalsIgnoreCase("char") || inmat.getPlanecount() != 1)
		{
			if (inmat.getDim()[0] != chemical.w || inmat.getDim()[1] != chemical.h || inmat.getPlanecount() != chemical.pl)
				resize(inmat);
			input.fromMatrix(inmat);
			bang();
		} else {
			System.out.println("jit.Degenerate only supports 4 plane char matrix");
		}
	}

	public void size(int _w, int _h)
	{
		w = _w;
		h = _h;
		update();
	}

	public void resize(JitterMatrix inmat)
	{
		w = inmat.getDim()[0];
		h = inmat.getDim()[1];
		pl = inmat.getPlanecount();
//		System.out.println("jit.Degenerate resize(w:"+w+",h:"+h+",pl:"+pl+")");
		update();
	}

	private void update()
	{
		A = new double[w * pl];
		I = new double[w * pl];
		D2A = new double[w * pl];
		D2I = new double[w * pl];
		chemical = new IntMatrix(w, h, pl);
		input = new IntMatrix(w, h, pl);
		input.setAll(255);
		out.setDim(new int[] { w, h });
		out.setPlanecount(pl);
	}

	public void reset()
	{
		input.setAll(255);
	}

	public void bang()
	{
		for (int i = 0; i < w; i++)
		{
			A[i] = Math.random();
			I[i] = Math.random() + cB * i / w;
			chemical.set(i, 0, cB * A[i]);
		}
		for (int t = 0; t < h; t++)
		{
			// Laplacian (for diffusion)
			for (int i = 1; i < w - 1; i++)
			{
				D2A[i] = A[i - 1] + A[i + 1] - 2 * A[i];
				D2I[i] = I[i - 1] + I[i + 1] - 2 * I[i];
			}
			D2A[0] = A[1] - A[0];
			D2I[0] = I[1] - I[0];
			int c = 0;
			for (int i = 0; i < w; i++)
			{
				A[i] = A[i] + cA * (cB * i / w * A[i] * A[i] * A[i] / (I[i]) + cC - cD * A[i] + cE * D2A[i] + cF * (Math.random() * 2 * cM - cM));
				I[i] = I[i] + cA * (cG * A[i] * A[i] * A[i] - cH * I[i] + cI * D2I[i] + cJ * (Math.random() * 2 * cM - cM));
				chemical.set(i, t, (cK - cL * A[i]) / 255. * input.get(i, t));
			}
		}
		chemical.toMatrix(out);
		outlet(0, "jit_matrix", out.getAttr("name"));
	}

	public static double random(double a, double b)
	{
		return (Math.random() * (b - a) + a);
	}
}
