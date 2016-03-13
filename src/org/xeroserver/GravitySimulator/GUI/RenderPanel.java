package org.xeroserver.GravitySimulator.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.xeroserver.GravitySimulator.Simulator.Render;

public class RenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public RenderPanel() {

		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
		setLayout(null);
		setIgnoreRepaint(true);
		setBounds(218, 11, 846, 648);
		setFocusable(false);
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D gn = (Graphics2D) g;

		super.paintComponent(gn);

		Render.renderFrame(gn);

	}

}
