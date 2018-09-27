package lf.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitleBar extends JPanel
{
	private static final long serialVersionUID = 1L;
	private int tbSize = 16;
	private int cbSize = 9;
	private Color tbColor = new Color(100,100,100);
	private JLabel ti;
	private Color tiColor = new Color(255,255,255);
	private JButton cb;
	private Font fo = new Font("Verdana",0, 10);
	private String title;
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
		ti.setText(this.title);
	}

	public TitleBar(String titleStr, int sizH)
	{
		super();
		title = titleStr;
		
		setLocation(0,0);
		setMaximumSize(new Dimension(sizH,tbSize));
		setLayout(new BorderLayout());
		setVisible(true);
		setBackground(tbColor);
		setFocusable(false);
		
		cb = new JButton();
		cb.setPreferredSize(new Dimension(cbSize,cbSize));
		cb.setMinimumSize(new Dimension(cbSize,cbSize));
		cb.setFocusable(false);
		
		ti = new JLabel(title);
		ti.setFont(fo);
		ti.setVisible(true);
		ti.setForeground(tiColor);
		ti.setFocusable(false);
		
		JPanel tmp = new JPanel();
		tmp.setOpaque(false);
		tmp.add(cb);
		tmp.setAlignmentX(0);
		tmp.setAlignmentY(0);
		tmp.setFocusable(false);
		
		add(tmp, BorderLayout.WEST);
		add(ti, BorderLayout.CENTER);
	}
	
	public void addActionListener(ActionListener al)
	{
		cb.addActionListener(al);
	}
	
	public void setWidth(int width)
	{
		setMaximumSize(new Dimension(width,tbSize));
	}
	
	public boolean isStarred()
	{
		return title.endsWith("*");
	}
	
	public void setStarred(boolean b)
	{
		String tmp = title;
		if(b)
		{
			if(!tmp.endsWith("*"))
				tmp += "*";
		} else {
			if(tmp.endsWith("*"))
				tmp = tmp.substring(0,tmp.length() - 1);
		}
		setTitle(tmp);
	}
}