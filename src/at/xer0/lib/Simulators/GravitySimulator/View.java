package at.xer0.lib.Simulators.GravitySimulator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RenderPanel p = null;
	private GravitySimulator gs = null;

	public View(ArrayList<SObject> objs, GravitySimulator g) {
		gs = g;
		setTitle("View");

		WindowListener exitListener = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				terminate();

			}
		};
		addWindowListener(exitListener);

		setBounds(0, 0, 700, 500);
		setLocationRelativeTo(null);
		p = new RenderPanel(objs);
		add(p);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

	}

	public void terminate() {
		gs.view = null;
		dispose();
		Thread.currentThread().interrupt();
	}

	@Override
	public void run() {
		setVisible(true);
	}

	public void setScale(int scale) {
		p.zoom = (double) (1.0 / scale);
	}

	public int getScale() {
		return (int) (1 / p.zoom);
	}

	public Vec2D getDelta() {
		return p.delta;
	}

	public void setDelta(Vec2D d) {
		p.delta = d;
	}

}

class RenderPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<SObject> objects = null;
	public Vec2D delta = new Vec2D(0, 0);
	public double zoom = 1;
	public int radius = 25;
	public ArrayList<PathPoint> points = new ArrayList<PathPoint>();

	public RenderPanel(ArrayList<SObject> s) {
		this.objects = s;
		this.setBackground(Color.BLACK);
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D gg = (Graphics2D) g;

		super.paintComponent(gg);

		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		// Render Origin at Center of screen:
		gg.translate(getWidth() / 2, getHeight() / 2);

		for (int v = 0; v < points.size(); v++) {
			PathPoint p = points.get(v);
			gg.setColor(Color.WHITE);
			gg.drawLine(p.getX(), p.getY(), p.getX(), p.getY());
		}

		for (int i = 0; i < objects.size(); i++) {
			SObject o = objects.get(i);
			gg.setColor(o.color);

			int x = (int) ((o.getPosition().getX() + delta.getX()) * zoom);
			int y = (int) ((o.getPosition().getY() + delta.getY()) * zoom);

			gg.fillOval(x, y, radius, radius);
			gg.setColor(Color.WHITE);
			gg.drawString(o.getName(), x, y - 10);
		}
		gg.setColor(Color.WHITE);
		gg.drawString("Scale 1:" + (int) (1 / zoom), -(getWidth() / 2) + 10,
				-(getHeight() / 2) + 20);
	}
}
