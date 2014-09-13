
package at.xer0.GUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import at.xer0.Simulator.Render;

public class RenderPanel extends JPanel
{

	private static final long serialVersionUID = 1L;

	public RenderPanel()
	{
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		setBounds(218, 11, 600, 400);
		setLayout(null);
	}

	public void paintComponent(Graphics g)
	{

		super.paintComponent(g);
		Render.renderFrame(g);

	}

}
