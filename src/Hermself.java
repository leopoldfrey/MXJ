import java.awt.geom.Point2D;
import java.util.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;

public class Hermself extends MaxObject
{
	//
	// P0 ------------------------- P1
	// | \ T3 / |
	// | \ / |
	// | P4 ---------- P5 |
	// | | | |
	// | T1 | T0 | T2 |
	// | | | |
	// | | | |
	// | P6 ---------- P7 |
	// | / \ |
	// | / T4 \ |
	// P2 ------------------------- P3
	//
	//
	int Z = 0;
	float Zface = 0.01f;
	float grid = 10;
	int gridI = 10;
	//float randTex = 0;
	//float randPos = 0;
	String prim = "quads";
	boolean autocalc = false;
	Texture textures[];
	Plane planes[];
	Point2D.Float points[];
	Vector<String> glcmd = new Vector<String>();
	public float version;
	protected int[] INLET_TYPES = {};
	protected int[] OUTLET_TYPES = {};
	protected String[] INLET_ASSIST = {};
	protected String[] OUTLET_ASSIST = {};
	protected String build = "";

	//
	Vector<Atom> preset;// = new Vector<Atom>();
	Atom[] pst;// = new Atom[preset.size()];

	public Hermself(Atom[] args)
	{
		version = 0.1f;
		build = "29/03/09";
		INLET_ASSIST = new String[] { "Commands in", "preset in" };
		OUTLET_ASSIST = new String[] { "to jit.gl.sketch", "preset out" };
		INLET_TYPES = new int[] { DataTypes.ALL, DataTypes.LIST };
		OUTLET_TYPES = new int[] { DataTypes.ALL, DataTypes.ALL };
		declareAttribute("grid", null, "setGrid");
		declareAttribute("prim", null, "setPrim");
		declareAttribute("autocalc");
//		declareAttribute("randPos");
//		declareAttribute("randTex");
		declareInlets(INLET_TYPES);
		declareOutlets(OUTLET_TYPES);
		setInletAssist(INLET_ASSIST);
		setOutletAssist(OUTLET_ASSIST);
		createInfoOutlet(false);
		textures = new Texture[6];
		textures[0] = new Texture("tex1");
		textures[1] = new Texture("tex2");
		textures[2] = new Texture("tex3");
		textures[3] = new Texture("tex4");
		textures[4] = new Texture("tex5");
		textures[5] = new Texture("tex6");
		points = new Point2D.Float[8];
		points[0] = new Point2D.Float(-1.1f, 0.8f);
		points[1] = new Point2D.Float(1.1f, 0.8f);
		points[2] = new Point2D.Float(-1.1f, -0.8f);
		points[3] = new Point2D.Float(1.1f, -0.8f);
		points[4] = new Point2D.Float(-0.2f, 0.2f);
		points[5] = new Point2D.Float(0.2f, 0.2f);
		points[6] = new Point2D.Float(-0.2f, -0.2f);
		points[7] = new Point2D.Float(0.2f, -0.2f);
		planes = new Plane[6];
		for (int i = 0; i < 6; i++)
			planes[i] = new Plane((int) grid);
		update();
	}
	
	public void list(Atom[] args)
	{
		if(getInlet() == 1)
		{
			int argv = 0;
			grid = args[argv].toInt();
			argv++;
			prim = args[argv].toString();
			argv++;
			for(int i = 0 ; i < 8 ; i++)
			{
				points[i] = new Point2D.Float(args[argv].toFloat(),args[argv+1].toFloat());
				argv += 2;
			}
			Texture t;
			for(int i = 0 ; i < 6 ; i++)
			{
				t = textures[i];
				t.name = args[argv].toString();
				argv++;
				for(int j = 0 ; j < 4 ; j++)
				{
					t.texcoords[j] = new Point2D.Float(args[argv].toFloat(), args[argv+1].toFloat());
					argv+=2;
				}
			}
			update();
		}
	}
	
	public void pst(String message, Atom[] args)
	{
		if(message.equalsIgnoreCase("all"))
		{
			list(args);		
		} else if(message.equalsIgnoreCase("tunnel")) {
			int argv = 0;
			grid = args[argv].toInt();
			argv++;
			prim = args[argv].toString();
			argv++;
			for(int i = 0 ; i < 8 ; i++)
			{
				points[i] = new Point2D.Float(args[argv].toFloat(),args[argv+1].toFloat());
				argv += 2;
			}
			update();			
		} else if(message.equalsIgnoreCase("texture")) {
			int argv = 0;
			Texture t;
			for(int i = 0 ; i < 6 ; i++)
			{
				t = textures[i];
				t.name = args[argv].toString();
				argv++;
				for(int j = 0 ; j < 4 ; j++)
				{
					t.texcoords[j] = new Point2D.Float(args[argv].toFloat(), args[argv+1].toFloat());
					argv+=2;
				}
			}
			update();
		}
	}
	
	public void getpst()
	{
		preset = new Vector<Atom>();
		preset.add(Atom.newAtom("pst"));
		preset.add(Atom.newAtom("all"));
		preset.add(Atom.newAtom(grid));
		preset.add(Atom.newAtom(prim));
		for(Point2D.Float p : points)
		{
			preset.add(Atom.newAtom(p.x));
			preset.add(Atom.newAtom(p.y));
		}
		for(Texture t : textures)
		{
			preset.add(Atom.newAtom(t.name));
			for(Point2D.Float p : t.texcoords)
			{
				preset.add(Atom.newAtom(p.x));
				preset.add(Atom.newAtom(p.y));
			}
		}
		pst = new Atom[preset.size()];
		outlet(1, preset.toArray(pst));
	}
	
	public void getpst(String message)
	{
		preset = new Vector<Atom>();
		if(message.equalsIgnoreCase("tunnel"))
		{
			preset.add(Atom.newAtom("pst"));
			preset.add(Atom.newAtom("tunnel"));
			preset.add(Atom.newAtom(grid));
			preset.add(Atom.newAtom(prim));
			for(Point2D.Float p : points)
			{
				preset.add(Atom.newAtom(p.x));
				preset.add(Atom.newAtom(p.y));
			}
			pst = new Atom[preset.size()];
			outlet(1, preset.toArray(pst));
		} else if(message.equalsIgnoreCase("texture")) {
			preset.add(Atom.newAtom("pst"));
			preset.add(Atom.newAtom("texture"));
			for(Texture t : textures)
			{
				preset.add(Atom.newAtom(t.name));
				for(Point2D.Float p : t.texcoords)
				{
					preset.add(Atom.newAtom(p.x));
					preset.add(Atom.newAtom(p.y));
				}
			}
			pst = new Atom[preset.size()];
			outlet(1, preset.toArray(pst));
		}
	}

	public void setGrid(int i)
	{
		this.grid = i;
		this.gridI = i;
		for (int k = 0; k < 5; k++)
			planes[k] = new Plane((int) grid);
		if (autocalc)
			update();
	}

	public void setPrim(String p)
	{
		this.prim = p;
		if (autocalc)
			bang();
	}

	public void update()
	{
		calcQuadAll();
		bang();
	}

	public void bang()
	{
		glcmd.clear();
		glcmd.add("reset");
		glcmd.add("glenable texture");
		addQuad(textures[0], planes[0], 4, 5, 6, 7);
		addQuad(textures[1], planes[1], 0, 4, 2, 6);
		addQuad(textures[2], planes[2], 5, 1, 7, 3);
		addQuad(textures[3], planes[3], 0, 1, 4, 5);
		addQuad(textures[4], planes[4], 6, 7, 2, 3);
		addQuadZ(textures[5], planes[5], 0, 1, 2, 3);
		glcmd.add("gldisable texture");
		for (String s : glcmd)
			outlet(0, Atom.parse(s));
	}

	public void calcQuadAll()
	{
		calculateQuad(textures[0], planes[0], 4, 5, 6, 7);
		calculateQuad(textures[1], planes[1], 0, 4, 2, 6);
		calculateQuad(textures[2], planes[2], 5, 1, 7, 3);
		calculateQuad(textures[3], planes[3], 0, 1, 4, 5);
		calculateQuad(textures[4], planes[4], 6, 7, 2, 3);
		calculateQuad(textures[5], planes[5], 0, 1, 2 , 3);
	}

	public void calcQuadAllTextures()
	{
		calculateQuadTex(textures[0], planes[0], 4, 5, 6, 7);
		calculateQuadTex(textures[1], planes[1], 0, 4, 2, 6);
		calculateQuadTex(textures[2], planes[2], 5, 1, 7, 3);
		calculateQuadTex(textures[3], planes[3], 0, 1, 4, 5);
		calculateQuadTex(textures[4], planes[4], 6, 7, 2, 3);
		calculateQuadTex(textures[5], planes[5], 0, 1, 2, 3);
	}

	public void calcQuadTexture(int num)
	{
		switch (num)
		{
		case 0:
			calculateQuadTex(textures[0], planes[0], 4, 5, 6, 7);
			break;
		case 1:
			calculateQuadTex(textures[1], planes[1], 0, 4, 2, 6);
			break;
		case 2:
			calculateQuadTex(textures[2], planes[2], 5, 1, 7, 3);
			break;
		case 3:
			calculateQuadTex(textures[3], planes[3], 0, 1, 4, 5);
			break;
		case 4:
			calculateQuadTex(textures[4], planes[4], 6, 7, 2, 3);
			break;
		case 5:
			calculateQuadTex(textures[5], planes[5], 0, 1, 2, 3);
			break;
		}
	}

	public void calcQuadPoints()
	{
		calculateQuadPoints(planes[0], 4, 5, 6, 7);
		calculateQuadPoints(planes[1], 0, 4, 2, 6);
		calculateQuadPoints(planes[2], 5, 1, 7, 3);
		calculateQuadPoints(planes[3], 0, 1, 4, 5);
		calculateQuadPoints(planes[4], 6, 7, 2, 3);
		calculateQuadPoints(planes[5], 0, 1, 2 ,3);
	}

	public void calcQuadPoint(int num)
	{
		switch (num)
		{
		case 0:
			calculateQuadPoints(planes[1], 0, 4, 2, 6);
			calculateQuadPoints(planes[3], 0, 1, 4, 5);
			calculateQuadPoints(planes[5], 0, 1, 2 ,3);
			break;
		case 1:
			calculateQuadPoints(planes[2], 5, 1, 7, 3);
			calculateQuadPoints(planes[3], 0, 1, 4, 5);
			calculateQuadPoints(planes[5], 0, 1, 2 ,3);
			break;
		case 2:
			calculateQuadPoints(planes[1], 0, 4, 2, 6);
			calculateQuadPoints(planes[4], 6, 7, 2, 3);
			calculateQuadPoints(planes[5], 0, 1, 2 ,3);
			break;
		case 3:
			calculateQuadPoints(planes[2], 5, 1, 7, 3);
			calculateQuadPoints(planes[4], 6, 7, 2, 3);
			calculateQuadPoints(planes[5], 0, 1, 2 ,3);
			break;
		case 4:
			calculateQuadPoints(planes[0], 4, 5, 6, 7);
			calculateQuadPoints(planes[1], 0, 4, 2, 6);
			calculateQuadPoints(planes[3], 0, 1, 4, 5);
			break;
		case 5:
			calculateQuadPoints(planes[0], 4, 5, 6, 7);
			calculateQuadPoints(planes[2], 5, 1, 7, 3);
			calculateQuadPoints(planes[3], 0, 1, 4, 5);
			break;
		case 6:
			calculateQuadPoints(planes[0], 4, 5, 6, 7);
			calculateQuadPoints(planes[1], 0, 4, 2, 6);
			calculateQuadPoints(planes[4], 6, 7, 2, 3);
			break;
		case 7:
			calculateQuadPoints(planes[0], 4, 5, 6, 7);
			calculateQuadPoints(planes[2], 5, 1, 7, 3);
			calculateQuadPoints(planes[4], 6, 7, 2, 3);
			break;
		}
		/*
		 * calculateQuadPoints(planes[0], 4, 5, 6, 7); calculateQuadPoints(planes[1], 0, 4, 2, 6); calculateQuadPoints(planes[2], 5, 1, 7, 3); calculateQuadPoints(planes[3], 0, 1, 4, 5); calculateQuadPoints(planes[4], 6, 7, 2, 3); //
		 */
	}

	public void addQuad(Texture tex, Plane plane, int p1, int p2, int p3, int p4)
	{
		glcmd.add("glbindtexture " + tex.name);
		glcmd.add("glbegin " + prim);
		for (int j = 1; j <= gridI; j++)
		{
			for (int i = 1; i <= gridI; i++)
			{
				glcmd.add(point2texcoord(plane.tmpTex[i - 1][j - 1]));
				glcmd.add(point2vertex(plane.tmpPoints[i - 1][j - 1]));
				glcmd.add(point2texcoord(plane.tmpTex[i][j - 1]));
				glcmd.add(point2vertex(plane.tmpPoints[i][j - 1]));
				glcmd.add(point2texcoord(plane.tmpTex[i][j]));
				glcmd.add(point2vertex(plane.tmpPoints[i][j]));
				glcmd.add(point2texcoord(plane.tmpTex[i - 1][j]));
				glcmd.add(point2vertex(plane.tmpPoints[i - 1][j]));
			}
		}
		glcmd.add("glend");
	}
	
	public void addQuadZ(Texture tex, Plane plane, int p1, int p2, int p3, int p4)
	{
		glcmd.add("glbindtexture " + tex.name);
		glcmd.add("glbegin " + prim);
		for (int j = 1; j <= gridI; j++)
		{
			for (int i = 1; i <= gridI; i++)
			{
				glcmd.add(point2texcoord(plane.tmpTex[i - 1][j - 1]));
				glcmd.add(point2vertexZ(plane.tmpPoints[i - 1][j - 1]));
				glcmd.add(point2texcoord(plane.tmpTex[i][j - 1]));
				glcmd.add(point2vertexZ(plane.tmpPoints[i][j - 1]));
				glcmd.add(point2texcoord(plane.tmpTex[i][j]));
				glcmd.add(point2vertexZ(plane.tmpPoints[i][j]));
				glcmd.add(point2texcoord(plane.tmpTex[i - 1][j]));
				glcmd.add(point2vertexZ(plane.tmpPoints[i - 1][j]));
			}
		}
		glcmd.add("glend");
	}

	private void calculateQuad(Texture tex, Plane plane, int p1, int p2, int p3, int p4)
	{
		Point2D.Float tmpY1, tmpY2, tmpTexY1, tmpTexY2;
		float stepJ, stepI;
		for (int j = 0; j <= gridI; j++)
		{
			stepJ = j / grid;
			tmpY1 = interp(points[p1], points[p3], stepJ);
			tmpY2 = interp(points[p2], points[p4], stepJ);
			tmpTexY1 = interp(tex.texcoords[0], tex.texcoords[2], stepJ);
			tmpTexY2 = interp(tex.texcoords[1], tex.texcoords[3], stepJ);
			for (int i = 0; i <= gridI; i++)
			{
				stepI = i / grid;
				plane.tmpPoints[i][j] = interp(tmpY1, tmpY2, stepI);
				plane.tmpTex[i][j] = interp(tmpTexY1, tmpTexY2, stepI);
			}
		}
	}

	private void calculateQuadTex(Texture tex, Plane plane, int p1, int p2, int p3, int p4)
	{
		Point2D.Float tmpTexY1, tmpTexY2;
		float stepJ, stepI;
		for (int j = 0; j <= gridI; j++)
		{
			stepJ = j / grid;
			tmpTexY1 = interp(tex.texcoords[0], tex.texcoords[2], stepJ);
			tmpTexY2 = interp(tex.texcoords[1], tex.texcoords[3], stepJ);
			for (int i = 0; i <= gridI; i++)
			{
				stepI = i / grid;
				plane.tmpTex[i][j] = interp(tmpTexY1, tmpTexY2, stepI);
			}
		}
	}

	private void calculateQuadPoints(Plane plane, int p1, int p2, int p3, int p4)
	{
		Point2D.Float tmpY1, tmpY2;
		float stepJ, stepI;
		for (int j = 0; j <= gridI; j++)
		{
			stepJ = j / grid;
			tmpY1 = interp(points[p1], points[p3], stepJ);
			tmpY2 = interp(points[p2], points[p4], stepJ);
			for (int i = 0; i <= gridI; i++)
			{
				stepI = i / grid;
				plane.tmpPoints[i][j] = interp(tmpY1, tmpY2, stepI);
			}
		}
	}

	public Point2D.Float interp(Point2D.Float p1, Point2D.Float p2, float step)
	{
		return new Point2D.Float(interpol(p1.x, p2.x, step), interpol(p1.y, p2.y, step));
	}

	public static float interpol(float f1, float f2, float step)
	{
		return f1 + step * (f2 - f1);
	}

	public void point(int num, float x, float y)
	{
		if (num < points.length && num >= 0)
		{
			points[num] = new Point2D.Float(x, y);
			if (autocalc)
			{
				calcQuadPoint(num);
				bang();
			}
		}
	}

	public void points(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, float x5, float y5, float x6, float y6, float x7, float y7)
	{
		points[0] = new Point2D.Float(x0, y0);
		points[1] = new Point2D.Float(x1, y1);
		points[2] = new Point2D.Float(x2, y2);
		points[3] = new Point2D.Float(x3, y3);
		points[4] = new Point2D.Float(x4, y4);
		points[5] = new Point2D.Float(x5, y5);
		points[6] = new Point2D.Float(x6, y6);
		points[7] = new Point2D.Float(x7, y7);
		if (autocalc)
		{
			calcQuadPoints();
			bang();
		}
	}

	public void texture(int num, String name, float minX, float maxX, float minY, float maxY)
	{
		if (num < textures.length && num >= 0)
		{
			textures[num] = new Texture(name, minX, maxX, minY, maxY);
			if (autocalc)
			{
				calcQuadTexture(num);
				bang();
			}
		}
	}

	public void texture(int num, String name, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
	{
		if (num < textures.length && num >= 0)
		{
			textures[num] = new Texture(name, x1, y1, x2, y2, x3, y3, x4, y4);
			if (autocalc)
			{
				calcQuadTexture(num);
				bang();
			}
		}
	}

	public void texture(int num, String name)
	{
		if (num < textures.length && num >= 0)
		{
			textures[num].name = name;
			if (autocalc)
			{
				bang();
			}
		}
	}

	public void texture(String name)
	{
		for (Texture t : textures)
			t.name = name;
		if (autocalc)
		{
			bang();
		}
	}

	public void texture(String name, float minX, float maxX, float minY, float maxY)
	{
		textures = new Texture[5];
		textures[0] = new Texture(name, minX, maxX, minY, maxY);
		textures[1] = new Texture(name, minX, maxX, minY, maxY);
		textures[2] = new Texture(name, minX, maxX, minY, maxY);
		textures[3] = new Texture(name, minX, maxX, minY, maxY);
		textures[4] = new Texture(name, minX, maxX, minY, maxY);
		if (autocalc)
		{
			calcQuadAllTextures();
			bang();
		}
	}

	public void texture(float minX, float maxX, float minY, float maxY)
	{
		for(Texture t : textures)
			t.apply(minX, maxX, minY, maxY);
		if (autocalc)
		{
			calcQuadAllTextures();
			bang();
		}
	}
	
	public void texture(String name, float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, float x5, float y5, float x6, float y6, float x7, float y7)
	{
		textures = new Texture[5];
		textures[0] = new Texture(name, x4, y4, x5, y5, x6, y6, x7, y7);
		textures[1] = new Texture(name, x0, y0, x4, y4, x2, y2, x6, y6);
		textures[2] = new Texture(name, x5, y5, x1, y1, x7, y7, x3, y3);
		textures[3] = new Texture(name, x0, y0, x1, y1, x4, y4, x5, y5);
		textures[4] = new Texture(name, x6, y6, x7, y7, x2, y2, x3, y3);
		for (Texture t : textures)
			t.name = name;
		if (autocalc)
		{
			calcQuadAllTextures();
			bang();
		}
	}

	public void texture(String name, float minX1, float maxX1, float minY1, float maxY1, float minX2, float maxX2, float minY2, float maxY2)
	{
		textures = new Texture[5];
		textures[0] = new Texture(name, minX2, minY2, maxX2, minY2, minX2, maxY2, maxX2, maxY2);
		textures[1] = new Texture(name, minX1, minY1, minX2, minY2, minX1, maxY1, minX2, maxY2);
		textures[2] = new Texture(name, maxX2, minY2, maxX1, minY1, maxX2, maxY2, maxX1, maxY1);
		textures[3] = new Texture(name, minX1, minY1, maxX1, minY1, minX2, minY2, maxX2, minY2);
		textures[4] = new Texture(name, minX2, maxY2, maxX2, maxY2, minX1, maxY1, maxX1, maxY1);
		for (Texture t : textures)
			t.name = name;
		if (autocalc)
		{
			calcQuadAllTextures();
			bang();
		}
	}

	private String point2vertex(Point2D.Float p)
	{
		return "glvertex " + p.x + " " + p.y + " " + Z;
		//return "glvertex "+(p.x+randPos())+" "+(p.y+randPos())+" "+(Z+randPos());
	}

	private String point2vertexZ(Point2D.Float p)
	{
		return "glvertex " + p.x + " " + p.y + " " + Zface;
		//return "glvertex "+(p.x+randPos())+" "+(p.y+randPos())+" "+(Z+randPos());
	}
	
	private String point2texcoord(Point2D.Float p)
	{
		return "gltexcoord " + p.x + " " + p.y;
		//return "glvertex "+(p.x+randPos())+" "+(p.y+randPos())+" "+(Z+randPos());
	}

	/*
	private float randTex()
	{
		return (float) (Math.random() * 2 - 1) * randTex;
	}

	private float randPos()
	{
		return (float) (Math.random() * 2 - 1) * randPos;
	}//*/
}

class Texture
{
	public String name;
	public Point2D.Float texcoords[];

	public Texture(String name)
	{
		this.name = name;
		texcoords = new Point2D.Float[4];
		texcoords[0] = new Point2D.Float(0, 1);
		texcoords[1] = new Point2D.Float(1, 1);
		texcoords[2] = new Point2D.Float(0, 0);
		texcoords[3] = new Point2D.Float(1, 0);
	}

	public Texture(String name, float minX, float maxX, float minY, float maxY)
	{
		this.name = name;
		texcoords = new Point2D.Float[4];
		texcoords[0] = new Point2D.Float(minX, minY);
		texcoords[1] = new Point2D.Float(maxX, minY);
		texcoords[2] = new Point2D.Float(minX, maxY);
		texcoords[3] = new Point2D.Float(maxX, maxY);
	}

	public Texture(String name, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
	{
		this.name = name;
		texcoords = new Point2D.Float[4];
		texcoords[0] = new Point2D.Float(x1, y1);
		texcoords[1] = new Point2D.Float(x2, y2);
		texcoords[2] = new Point2D.Float(x3, y3);
		texcoords[3] = new Point2D.Float(x4, y4);
	}

	public void apply(float minX, float maxX, float minY, float maxY)
	{
		texcoords = new Point2D.Float[4];
		texcoords[0] = new Point2D.Float(minX, minY);
		texcoords[1] = new Point2D.Float(maxX, minY);
		texcoords[2] = new Point2D.Float(minX, maxY);
		texcoords[3] = new Point2D.Float(maxX, maxY);
	}
	
	public String toString()
	{
		return name;
	}
}

class Plane
{
	public Texture tex;
	Point2D.Float[][] tmpPoints;
	Point2D.Float[][] tmpTex;

	Plane(int grid)
	{
		tmpPoints = new Point2D.Float[grid + 1][grid + 1];
		tmpTex = new Point2D.Float[grid + 1][grid + 1];
		for (int i = 0; i < grid + 1; i++)
			for (int j = 0; j < grid + 1; j++)
			{
				tmpPoints[i][j] = new Point2D.Float(0, 0);
				tmpTex[i][j] = new Point2D.Float(0, 0);
			}
	}
}
