package at.xer0.lib.Simulators.GravitySimulator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JFrame implements Runnable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public View(ArrayList<SObject> objs)
	{
		
		setTitle("View");
		
		setBounds(0, 0, 700, 500);
		setLocationRelativeTo(null);
		JPanel p = new RenderPanel(objs);
		add(p);
	}

	@Override
	public void run() {
		setVisible(true);
	}

}

class RenderPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<SObject> objects = null;
	public Vec2D delta = new Vec2D(0,0);
	public double zoom = 0;
	public int radius = 40;
	
	public RenderPanel(ArrayList<SObject> s)
	{
		this.objects = s;
		this.setBackground(Color.BLACK);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for(int i = 0; i < objects.size(); i++)
		{
			SObject o = objects.get(i);
			g.setColor(o.color);
			g.fillOval((int)o.getPosition().getX(),(int) o.getPosition().getY(), radius, radius);
		}
	}
}
