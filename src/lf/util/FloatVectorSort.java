/**
 *  -----------------------------------------------------------------------------
 *  
 *  Holo-Edit, spatial sound trajectories editor, part of Holophon
 *  Copyright (C) 2006 GMEM
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *  
 *  -----------------------------------------------------------------------------
 */
package lf.util;

import java.util.Vector;

public class FloatVectorSort extends Vector<Float>
{
	public boolean changes = false;
	
	public FloatVectorSort()
	{
		super();
	}
	
	public FloatVectorSort(int cap)
	{
		super(cap);
	}
	
	public FloatVectorSort(int cap, int inc)
	{
		super(cap,inc);
	}
	
	public FloatVectorSort(Vector v)
	{
		super(v.size(),1);
		for(int i = 0;i<v.size();i++)
		{
			insertSort((Float)v.get(i));
		}
	}
	
	public Vector<Float> getVector()
	{
		Vector<Float> tmp = new Vector<Float>(capacity(),1);
		for(int i = 0;i<size();i++)
			tmp.add(get(i));
		return tmp;
	}
	
	public boolean lessThan(Float f1, Float f2)
	{
		return (f1 < f2);
	}

	public boolean lessThanOrEqual(Float f1, Float f2)
	{
		return (f1 <= f2);
	}
	

	/**
	 * rules for sorting elements 
	 * true if obj1 > obj2
	 */
	public boolean greaterThan(Float f1, Float f2)
	{
		return !lessThanOrEqual(f1,f2);
	}

	/**
	 * rules for sorting elements 
	 * true if obj1 > obj2
	 */
	public boolean greaterThanOrEqual(Float f1, Float f2)
	{
		return !lessThan(f1,f2);
	}

	/**
	 * insert an element at the right index in the vector
	 */
	public void insertSort(float element)
	{
		int i = size() - 1;
		addElement(element);
		while ((i >= 0) && (greaterThan(elementAt(i), element)))
		{
			setElementAt(elementAt(i), i + 1);
			i--;
		}
		setElementAt(element, i + 1);
	}
	
	public boolean add(float element)
	{
		if(!contains(element))
		{
			insertSort(element);
			return true;
		} 
		return false;
	}
	
	public void add(float[] array)
	{
		for(float f : array)
			add(f);
	}
	
	public boolean sort()
	{
		changes = false;
		quickSort(0, size() - 1);
		return changes;
	}
	
	private void quickSort(int left, int right)
	{
		if (right > left)
		{
			float o1 = elementAt(right);
			int i = left - 1;
			int j = right;
			while (true)
			{
				while (lessThan(elementAt(++i), o1)){}
				while (j > 0)
					if (lessThanOrEqual(elementAt(--j), o1))
						break; // out of while
				if (i >= j)
					break;
				swap(i, j);
			}
			swap(i, right);
			quickSort(left, i - 1);
			quickSort(i + 1, right);
		}
	}
	
	private void swap(int loc1, int loc2)
	{
		if(loc1 != loc2)
		{
			changes = true;
			float tmp = elementAt(loc1);
			setElementAt(elementAt(loc2), loc1);
			setElementAt(tmp, loc2);
		}
	}
	
	public Float elementAt(int pos)
	{
		return super.elementAt(pos);
	}
	
	public Float get(int pos)
	{
		return super.get(pos);
	}
	
	public String toString()
	{
		String tmp = "";
		for(int i = 0 ; i < size() ; i++)
			tmp += get(i)+(i+1 != size() ? " " : "");
		return tmp;
	}
}
