package lf.gui;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class BarreProgression extends FloatingWindow
{
	// the bar
	JProgressBar barre;
	// last value
	int opVal = 0;
	// current value
	int pVal = 0;
	// font
	Font f = new Font("courrier", Font.PLAIN, 10);
	//
	private int counter = 0;

	//
	public BarreProgression()
	{
		super("Loading...", 100, 20, 0, 0, true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLayout(new FlowLayout());
		barre = new JProgressBar(0, 16);
		barre.setValue(0);
		barre.setFont(f);
		barre.setStringPainted(true);
		barre.setVisible(true);
		add(barre);
		setLocationRelativeTo(null);
		counter = 0;
	}

	//
	public BarreProgression(String Title)
	{
		super(Title, 100, 20, 0, 0, true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLayout(new FlowLayout());
		barre = new JProgressBar(0, 16);
		counter = 0;
		barre.setValue(0);
		barre.setFont(f);
		barre.setStringPainted(true);
		barre.setVisible(true);
		add(barre);
		setLocationRelativeTo(null);
		counter = 0;
	}

	// Updates the value
	public void setValue(int value)
	{
		counter = value;
		barre.setValue(value);
		repaint();
	}
	// gets the value
	public int getValue()
	{
		return counter;
	}

	public void repaint()
	{
		pVal = (int) (barre.getPercentComplete() * 100);
		if (pVal != opVal)
		{
			opVal = pVal;
			super.repaint();
		}
	}

	// Sets the maximum
	public void setMaximum(int max)
	{
		barre.setMaximum(max);
	}

	// Gets the maximum
	public int getMaximum()
	{
		return barre.getMaximum();
	}

	public void inc()
	{
		counter++;
		barre.setValue(counter);
		repaint();
	}

	public void dec()
	{
		counter--;
		barre.setValue(counter);
		repaint();
	}

	public void inc(int i)
	{
		counter += i;
		barre.setValue(counter);
		repaint();
	}
	
	public void dec(int i)
	{
		counter -= i;
		barre.setValue(counter);
		repaint();
	}
}