package topher;

import java.util.HashMap;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;
public class LCDWrapper
{

	private static final String[] INLET_ASSIST = new String[]{
		"inlet 1 help"
	};
	private static final String[] OUTLET_ASSIST = new String[]{
		"outlet 1 help"
	};

	public MaxObject _peer = null;
	int _po = 0;//peer outlet
	public LCDWrapper(MaxObject peer)
	{
		_peer = peer;		
		_po   = 0;//default to first outlet
	}

	public LCDWrapper(MaxObject peer, int peer_outlet)
	{
		_peer = peer;		
		_po = peer_outlet;
	}


	//turn off mouse drawing on LCD surface
	public void local(boolean b)
	{
		do_outlet(new Atom[]{Atom.newAtom("local"),b ? Atom.newAtom(1) : Atom.newAtom(0)});
	}

	//turn off mouse drawing on LCD surface
	public void onscreen(boolean b)
	{
		do_outlet(new Atom[]{Atom.newAtom("onscreen"),b ? Atom.newAtom(1) : Atom.newAtom(0)});
	}
	
	public void border(boolean b)
	{
		do_outlet(new Atom[]{Atom.newAtom("border"),b ? Atom.newAtom(1) : Atom.newAtom(0)});
	}

	public void enable_sprites(boolean b)
	{
		do_outlet(new Atom[]{Atom.newAtom("enablesprites"),b ? Atom.newAtom(1) : Atom.newAtom(0)});		
	}

	public void idle(boolean b)
	{
		do_outlet(new Atom[]{Atom.newAtom("idle"),b ? Atom.newAtom(1) : Atom.newAtom(0)});		
	}
	
	
	private Atom[] _mess_getsize = new Atom[]{Atom.newAtom("getsize")};
	public void getsize()
	{
		do_outlet(_mess_getsize);		
	}
	
	private Atom[] _mess_beginsprite = new Atom[]{Atom.newAtom("recordsprite")};
	private String _current_sprite = null;
	public void beginSprite(String name)
	{
		_current_sprite = name;
		do_outlet(_mess_beginsprite);
	}

	private Atom[] _mess_endsprite = new Atom[]{Atom.newAtom("closesprite"),null};
	public void endSprite()
	{
		_mess_endsprite[1] = Atom.newAtom(_current_sprite);
		_current_sprite = null;
		do_outlet(_mess_endsprite);
	}

	private Atom[] _mess_drawsprite = new Atom[]{Atom.newAtom("drawsprite"),null,null,null};
	public void drawSprite(String spritename,int x, int y)
	{
		_mess_drawsprite[1] = Atom.newAtom(spritename);
		_mess_drawsprite[2] = Atom.newAtom(x);
		_mess_drawsprite[3] = Atom.newAtom(y);
		do_outlet(_mess_drawsprite);
	}

	private Atom[] _mess_hidesprite = new Atom[]{Atom.newAtom("hidesprite"),null};
	public void hideSprite(String spritename)
	{
		_mess_hidesprite[1] = Atom.newAtom(spritename);
		do_outlet(_mess_hidesprite);
	}

	private Atom[] _mess_deletesprite = new Atom[]{Atom.newAtom("deletesprite"),null};
	public void deleteSprite(String spritename)
	{
		_mess_deletesprite[1] = Atom.newAtom(spritename);
		do_outlet(_mess_deletesprite);
	}

	private Atom[] _mess_spritetoback = new Atom[]{Atom.newAtom("backsprite"),null};
	public void spriteToBack(String spritename)
	{
		_mess_spritetoback[1] = Atom.newAtom(spritename);
		do_outlet(_mess_spritetoback);
	}


	private Atom[] _mess_spritetofront = new Atom[]{Atom.newAtom("frontsprite"),null};
	public void spriteToFront(String spritename)
	{
		_mess_spritetofront[1] = Atom.newAtom(spritename);
		do_outlet(_mess_spritetofront);
	}

	private Atom[] _mess_clearsprites = new Atom[]{Atom.newAtom("clearsprites")};
	public void clearSprites()
	{
		do_outlet(_mess_clearsprites);
	}

    private Atom[] _mess_setsize = new Atom[]{Atom.newAtom("size"),null,null};
	public void setSize(int width, int height)
	{
		_mess_setsize[1] = Atom.newAtom(width);
		_mess_setsize[2] = Atom.newAtom(height);
		do_outlet(_mess_setsize);
	}


	private Atom[] _mess_clear = new Atom[]{Atom.newAtom("clear")};
	public void clear()
	{
		do_outlet(_mess_clear);
	}

	private Atom[] _mess_reset = new Atom[]{Atom.newAtom("reset")};
	public void reset()
	{
		do_outlet(_mess_reset);
	}

	private Atom[] _mess_frgb = new Atom[]{Atom.newAtom("frgb"),null,null,null};
	public void setFRGB(int r, int g, int b)
	{
		_mess_frgb[1] = Atom.newAtom(r);	
		_mess_frgb[2] = Atom.newAtom(g);
		_mess_frgb[3] = Atom.newAtom(b);
		do_outlet(_mess_frgb);
	}
	public void setFRGB(int[] rgb)
	{
		_mess_frgb[1] = Atom.newAtom(rgb[0]);	
		_mess_frgb[2] = Atom.newAtom(rgb[1]);
		_mess_frgb[3] = Atom.newAtom(rgb[2]);
		do_outlet(_mess_frgb);
	}
	private Atom[] _mess_brgb = new Atom[]{Atom.newAtom("brgb"),null,null,null};
	public void setBRGB(int r, int g, int b)
	{
		_mess_brgb[1] = Atom.newAtom(r);		
		_mess_brgb[2] = Atom.newAtom(g);
		_mess_brgb[3] = Atom.newAtom(b);
		do_outlet(_mess_brgb);
	}
	public void setBRGB(int[] rgb)
	{
		_mess_brgb[1] = Atom.newAtom(rgb[0]);		
		_mess_brgb[2] = Atom.newAtom(rgb[1]);
		_mess_brgb[3] = Atom.newAtom(rgb[2]);
		do_outlet(_mess_brgb);
	}


	// TEXT faces //////////////////////////////
	//normal
	//bold
	//itlaic
	//underline
	//outline
	//shadow
	//condense
	//extend
	/////////////////////////////////////////////
	private String[] DEFAULTTEXTFACE = new String[]{"textface","normal"};
	private Atom[] _mess_font = new Atom[]{Atom.newAtom("font"),null,null};
	public void setFont(String fontname,int fontsize,String[] textface)
	{

		_mess_font[1] = Atom.newAtom(fontname);		
		_mess_font[2] = Atom.newAtom(fontsize);
		do_outlet(_mess_font);
		
	
		if(textface == null)
		{
			textface = DEFAULTTEXTFACE;
		}
		else
		{
			//HACK HACK HACK!!
			if(textface.length == 1)
			{
				DEFAULTTEXTFACE[1] = textface[0];
				do_outlet(DEFAULTTEXTFACE);
				DEFAULTTEXTFACE[1] = "normal";
			}
		}	
		//need to make this support other than default	
		do_outlet(textface);

	}

	private Atom[] _mess_moveto = new Atom[]{Atom.newAtom("moveto"),null,null};
	public void move_to(int x, int y)
	{
		_mess_moveto[1] = Atom.newAtom(x);
		_mess_moveto[2] = Atom.newAtom(y);
		do_outlet(_mess_moveto);					
	}

	private Atom _atom_ascii = Atom.newAtom("ascii");
	public void drawString(String str, int x, int y)
	{
		
		if(str == null)
		{
			str = "null";
		}
		byte[] b = str.getBytes();
		Atom[] out = new Atom[b.length+1];
		out[0] = _atom_ascii;
		for(int i = 1; i < out.length;i++)
		{
			out[i] = Atom.newAtom(b[i-1]);
		}

		move_to(x,y);
		do_outlet(out); 	
	}

	private Atom[] _mess_frameoval = new Atom[]{Atom.newAtom("frameoval"),null,null,null,null};
	public void frameOval(int x, int y, int width, int height)
	{
		_mess_frameoval[1] = Atom.newAtom(x);	
		_mess_frameoval[2] = Atom.newAtom(y);	
		_mess_frameoval[3] = Atom.newAtom(x+width);	
		_mess_frameoval[4] = Atom.newAtom(y+height);	
		do_outlet(_mess_frameoval);
	}

	private Atom[] _mess_paintoval = new Atom[]{Atom.newAtom("paintoval"),null,null,null,null};
	public void paintOval(int x, int y, int width, int height)
	{
		_mess_paintoval[1] = Atom.newAtom(x);	
		_mess_paintoval[2] = Atom.newAtom(y);	
		_mess_paintoval[3] = Atom.newAtom(x+width);	
		_mess_paintoval[4] = Atom.newAtom(y+height);	
		do_outlet(_mess_paintoval);
	}

	private Atom[] _mess_framerect = new Atom[]{Atom.newAtom("framerect"),null,null,null,null};
	public void frameRect(int x, int y, int width, int height)
	{
		_mess_framerect[1] = Atom.newAtom(x);	
		_mess_framerect[2] = Atom.newAtom(y);	
		_mess_framerect[3] = Atom.newAtom(x+width);	
		_mess_framerect[4] = Atom.newAtom(y+height);	
		do_outlet(_mess_framerect);
	}

	private Atom[] _mess_paintrect = new Atom[]{Atom.newAtom("paintrect"),null,null,null,null};
	public void paintRect(int x, int y, int width, int height)
	{
		_mess_paintrect[1] = Atom.newAtom(x);	
		_mess_paintrect[2] = Atom.newAtom(y);	
		_mess_paintrect[3] = Atom.newAtom(x+width);	
		_mess_paintrect[4] = Atom.newAtom(y+height);	
		do_outlet(_mess_paintrect);
	}


	private Atom[] _mess_linesegment = new Atom[]{Atom.newAtom("linesegment"),null,null,null,null};
	public void lineSegment(int x, int y, int x2, int y2)
	{
		_mess_linesegment[1] = Atom.newAtom(x);	
		_mess_linesegment[2] = Atom.newAtom(y);	
		_mess_linesegment[3] = Atom.newAtom(x2);	
		_mess_linesegment[4] = Atom.newAtom(y2);	
		do_outlet(_mess_linesegment);
	}
	private Atom[] _mess_paintarc = new Atom[]{Atom.newAtom("paintarc"),null,null,null,null,null,null};
	public void paintArc(int x, int y, int width, int height,int startdegrees,int enddegrees)
	{
		_mess_paintarc[1] = Atom.newAtom(x);	
		_mess_paintarc[2] = Atom.newAtom(y);	
		_mess_paintarc[3] = Atom.newAtom(x+width);	
		_mess_paintarc[4] = Atom.newAtom(y+height);	
		_mess_paintarc[5] = Atom.newAtom(startdegrees);	
		_mess_paintarc[6] = Atom.newAtom(enddegrees);	
		do_outlet(_mess_paintarc);
	}

	private Atom[] _mess_framearc = new Atom[]{Atom.newAtom("framearc"),null,null,null,null,null,null};
	public void frameArc(int x, int y, int width, int height,int startdegrees,int enddegrees)
	{
		_mess_framearc[1] = Atom.newAtom(x);	
		_mess_framearc[2] = Atom.newAtom(y);	
		_mess_framearc[3] = Atom.newAtom(x+width);	
		_mess_framearc[4] = Atom.newAtom(y+height);	
		_mess_framearc[5] = Atom.newAtom(startdegrees);	
		_mess_framearc[6] = Atom.newAtom(enddegrees);	
		do_outlet(_mess_framearc);
	}

	private Atom[] _mess_frameroundrect = new Atom[]{Atom.newAtom("frameroundrect"),null,null,null,null,null,null};
	public void frameRoundRect(int x, int y, int width, int height,int roundx,int roundy)
	{
		_mess_frameroundrect[1] = Atom.newAtom(x);	
		_mess_frameroundrect[2] = Atom.newAtom(y);	
		_mess_frameroundrect[3] = Atom.newAtom(x+width);	
		_mess_frameroundrect[4] = Atom.newAtom(y+height);	
		_mess_frameroundrect[5] = Atom.newAtom(roundx);	
		_mess_frameroundrect[6] = Atom.newAtom(roundy);	
		do_outlet(_mess_frameroundrect);
	}

	private Atom[] _mess_paintroundrect = new Atom[]{Atom.newAtom("paintroundrect"),null,null,null,null,null,null};
	public void paintRoundRect(int x, int y, int width, int height,int roundx,int roundy)
	{
		_mess_paintroundrect[1] = Atom.newAtom(x);	
		_mess_paintroundrect[2] = Atom.newAtom(y);	
		_mess_paintroundrect[3] = Atom.newAtom(x+width);	
		_mess_paintroundrect[4] = Atom.newAtom(y+height);	
		_mess_paintroundrect[5] = Atom.newAtom(roundx);	
		_mess_paintroundrect[6] = Atom.newAtom(roundy);	
		do_outlet(_mess_paintroundrect);
	}

	public void paintPoly(int[] vertices)
	{
		Atom[] out = new Atom[vertices.length + 1];
		out[0] = Atom.newAtom("paintpoly");
		for(int i = 1; i < out.length;i++)
		{
			out[i] = Atom.newAtom(vertices[i-1]);
		}
		do_outlet(out);
	}

	public void framePoly(int[] vertices)
	{
		Atom[] out = new Atom[vertices.length + 1];
		out[0] = Atom.newAtom("framepoly");
		for(int i = 1; i < out.length;i++)
		{
			out[i] = Atom.newAtom(vertices[i-1]);
		}
		do_outlet(out);
	}
	
	private Atom[] _mess_scrollrect = new Atom[]{Atom.newAtom("scrollrect"),null,null,null,null,null,null};
	public void scrollRect(int x, int y, int width, int height,int scrollx,int scrolly)
	{
		_mess_scrollrect[1] = Atom.newAtom(x);	
		_mess_scrollrect[2] = Atom.newAtom(y);	
		_mess_scrollrect[3] = Atom.newAtom(x+width);	
		_mess_scrollrect[4] = Atom.newAtom(y+height);	
		_mess_scrollrect[5] = Atom.newAtom(scrollx);	
		_mess_scrollrect[6] = Atom.newAtom(scrolly);	
		do_outlet(_mess_scrollrect);
	}

	private Atom[] _mess_cliprect = new Atom[]{Atom.newAtom("cliprect"),null,null,null,null};
	public void clipRect(int x1, int y1, int x2, int y2)
	{
		_mess_cliprect[1] = Atom.newAtom(x1);	
		_mess_cliprect[2] = Atom.newAtom(y1);	
		_mess_cliprect[3] = Atom.newAtom(x2);	
		_mess_cliprect[4] = Atom.newAtom(y2);	
		do_outlet(_mess_cliprect);
	}
	
	public void noclip()
	{
		do_outlet(new Atom[]{Atom.newAtom("noclip")});
	}
	
	private Atom[] _mess_readpict = new Atom[]{Atom.newAtom("readpict"),null,null};
	public void readPict(String name, String filename)
	{
		_mess_readpict[1] = Atom.newAtom(name);
		_mess_readpict[2] = Atom.newAtom(filename);
		do_outlet(_mess_readpict);
	}
	
	private Atom[] _mess_drawpict = new Atom[]{Atom.newAtom("drawpict"),null,null,null};
	public void drawPict(String name, int x, int y)
	{
		_mess_drawpict[1] = Atom.newAtom(name);
		_mess_drawpict[2] = Atom.newAtom(x);
		_mess_drawpict[3] = Atom.newAtom(y);
		do_outlet(_mess_drawpict);
	}
	
	private HashMap<String, LCDImage> _image_hashmap = new HashMap<String, LCDImage>();

	public LCDImage loadImage(String filename)
	{
		LCDImage i = new LCDImage(filename);	
		_image_hashmap.put(filename,i);
		do_outlet(new Atom[]{Atom.newAtom("readpict"),Atom.newAtom(filename)});
		return i;
	}

	public void pict(String name,String error)
	{
		System.err.println("(LCDWrapper) error loading "+name);
		_image_hashmap.remove(name);
	}

	public void pict(String name,int width,int height)
	{
		LCDImage i = _image_hashmap.get(name);
		if(i != null)
		{
			i.setWidth(width);
			i.setHeight(height);
		}
	}

	public class LCDImage
	{
		String name;
		int w;
		int h;
		LCDImage(String name)
		{
			this.name = name;
			w = 0;
			h = 0;	
		}		
		
		void setWidth(int width)
		{
			w = width;
		}
	
		void setHeight(int height)
		{
			h = height;
		}	

		public int getWidth()
		{
			return w;
		}

		public int getHeight()
		{
			return h;
		}
	}

	private void do_outlet(Atom[] cmd)
	{
		_peer.outlet(_po,cmd);
	}

	private void do_outlet(String[] cmd)
	{
		_peer.outlet(_po,cmd);
	}
}








