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

public class IntegerVectorSort extends Vector<Integer>
{
	public boolean changes = false;
	
	public IntegerVectorSort()
	{
		super();
	}
	
	public IntegerVectorSort(int cap)
	{
		super(cap);
	}
	
	public IntegerVectorSort(int cap, int inc)
	{
		super(cap,inc);
	}
	
	public IntegerVectorSort(Vector v)
	{
		super(v.size(),1);
		for(int i = 0;i<v.size();i++)
		{
			insertSort((Integer)v.get(i));
		}
	}
	
	public Vector getVector()
	{
		Vector<Integer> tmp = new Vector<Integer>(capacity(),1);
		for(int i = 0;i<size();i++)
		{
			tmp.add(get(i));
		}
		return tmp;
	}
	
	public boolean lessThan(Integer i1, Integer i2)
	{
		return (i1 < i2);
	}

	public boolean lessThanOrEqual(Integer i1, Integer i2)
	{
		return (i1 <= i2);
	}
	

	/**
	 * rules for sorting elements 
	 * true if obj1 > obj2
	 */
	public boolean greaterThan(int i1, int i2)
	{
		return !lessThanOrEqual(i1,i2);
	}

	/**
	 * rules for sorting elements 
	 * true if obj1 > obj2
	 */
	public boolean greaterThanOrEqual(int i1, int i2)
	{
		return !lessThan(i1,i2);
	}

	/**
	 * insert an element at the right index in the vector
	 */
	public void insertSort(int element)
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
	
	public boolean add(int element)
	{
		if(!contains(element))
		{
			insertSort(element);
			return true;
		} 
		return false;
	}
	
	public void add(int[] array)
	{
		for(int i : array)
			add(i);
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
			int o1 = elementAt(right);
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
			int tmp = elementAt(loc1);
			setElementAt(elementAt(loc2), loc1);
			setElementAt(tmp, loc2);
		}
	}
	
	public Integer elementAt(int pos)
	{
		return super.elementAt(pos);
	}
	
	public Integer get(int pos)
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
