import com.cycling74.jitter.JitterMatrix;
import com.cycling74.jitter.JitterObject;
import com.cycling74.max.MaxObject;

import jit.IntMatrix;

public class WaveformScope extends MaxObject
{
	public JitterMatrix foo, scope, scope2;
	public JitterObject rgb2luma;
	public int[] cell;
	public int[] pos = new int[2];
	public int accum = 25;
	public int mode = 0;
	public IntMatrix in, out;

	public WaveformScope()
	{
		scope = new JitterMatrix();
		scope2 = new JitterMatrix();
		rgb2luma = new JitterObject("jit.rgb2luma");
		declareAttribute("accum");
		declareAttribute("mode");
	}

	public void jit_matrix(String inname)
	{
		foo = new JitterMatrix(inname);
		switch (mode)
		{
		case 3:
			scope2.setPlanecount(foo.getPlanecount());
			scope2.setType(foo.getType());
			scope2.setDim(new int[] { 256, foo.getDim()[1] });
			scope2.setall(0);
			in = new IntMatrix(foo);
			out = new IntMatrix(256, in.h, foo.getPlanecount());
			for (int j = 0; j < foo.getDim()[1]; j++)
			{
				pos[1] = j;
				for (int i = 0; i < foo.getDim()[0]; i++)
				{
					for (int k = 0; k < foo.getPlanecount(); k++)
					{
						pos[0] = in.get(i, j, k);
						out.set(pos, k, accum + out.get(pos, k));
					}
				}
			}
			break;
		case 2:
			scope2.setPlanecount(foo.getPlanecount());
			scope2.setType(foo.getType());
			scope2.setDim(new int[] { foo.getDim()[0], 256 });
			scope2.setall(0);
			in = new IntMatrix(foo);
			out = new IntMatrix(in.w, 256, foo.getPlanecount());
			for (int i = 0; i < foo.getDim()[0]; i++)
			{
				pos[0] = i;
				for (int j = 0; j < foo.getDim()[1]; j++)
				{
					for (int k = 0; k < foo.getPlanecount(); k++)
					{
						pos[1] = 255 - in.get(i, j, k);
						out.set(pos, k, accum + out.get(pos, k));
					}
				}
			}
			break;
		case 1:
			scope.setDim(foo.getDim());
			scope.setPlanecount(1);
			scope.setType(foo.getType());
			scope2.setPlanecount(1);
			scope2.setType(foo.getType());
			scope2.setDim(new int[] { 256, foo.getDim()[1] });
			scope2.setall(0);
			rgb2luma.matrixcalc(inname, scope);
			in = new IntMatrix(scope);
			out = new IntMatrix(256, in.h, 1);
			for (int j = 0; j < scope.getDim()[1]; j++)
			{
				pos[1] = j;
				for (int i = 0; i < scope.getDim()[0]; i++)
				{
					pos[0] = in.get(i, j);
					out.set(pos, accum + out.get(pos));
				}
			}
			break;
		default:
		case 0:
			scope.setDim(foo.getDim());
			scope.setPlanecount(1);
			scope.setType(foo.getType());
			scope2.setPlanecount(1);
			scope2.setType(foo.getType());
			scope2.setDim(new int[] { foo.getDim()[0], 256 });
			scope2.setall(0);
			rgb2luma.matrixcalc(inname, scope);
			in = new IntMatrix(scope);
			out = new IntMatrix(in.w, 256, 1);
			for (int i = 0; i < scope.getDim()[0]; i++)
			{
				pos[0] = i;
				for (int j = 0; j < scope.getDim()[1]; j++)
				{
					pos[1] = 255 - in.get(i, j);
					out.set(pos, accum + out.get(pos));
				}
			}
			break;
		}
		out.toMatrix(scope2);
		outlet(0, "jit_matrix", scope2.getAttr("name"));
	}
}