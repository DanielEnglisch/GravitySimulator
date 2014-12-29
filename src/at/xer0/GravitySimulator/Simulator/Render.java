
package at.xer0.GravitySimulator.Simulator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import at.xer0.GravitySimulator.Objects.Obj;
import at.xer0.GravitySimulator.Objects.Point;
import at.xer0.GravitySimulator.Support.Vars;

public class Render
{

	public static void renderFrame(Graphics2D g)
	{

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		// Render Origin at Center of screen:
		g.translate(Vars.mainFrame.renderPanel.getWidth() / 2, Vars.mainFrame.renderPanel.getHeight() / 2);

		renderPath(g);

		for (Obj obj : Vars.activeObjects)
		{
			g.setColor(obj.getColor());

			int x = (int) ((obj.getPosition().getX() + Vars.scaling_Delta.getX()) * Vars.scaling_ZoomFactor);
			int y = (int) ((obj.getPosition().getY() + Vars.scaling_Delta.getY()) * Vars.scaling_ZoomFactor);

			//Fixed Radius:
			int radius = 25;
			

			int r_x = x - (radius / 2);
			int r_y = y - (radius / 2);

			// Render Object:
			g.fill(new Ellipse2D.Double(r_x, r_y, radius, radius));
			//

			// Render Text

			if (Vars.mainFrame.cb_showNames.isSelected())
			{
				g.setColor(Color.WHITE);
				g.drawString(obj.getName(), r_x - obj.getName().length(), r_y - 10);
			}

		}

	}

	private static void renderPath(Graphics2D g)
	{

		for (Obj obj : Vars.activeObjects)
		{

			if (Vars.mainFrame.cb_drawPath.isSelected())
			{
				g.setColor(obj.getColor());

				// LinePath:
				for (int i = 0; i < obj.points.size(); i++)
				{
					try
					{
						Point p1 = obj.points.get(i);
						Point p2 = obj.points.get(i /* + 1 */);

						g.drawLine((p1.getX()), (p1.getY()), (p2.getX()), (p2.getY()));

					} catch (Exception exx)
					{
					}

				}
			} else
			{
				obj.clearPoints();
			}

		}
	}

}
