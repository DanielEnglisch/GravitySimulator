
package at.xer0.Simulator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import at.xer0.Support.Obj;
import at.xer0.Support.Point;
import at.xer0.Support.Vars;

public class Render
{

	public static void renderFrame(Graphics2D g)
	{
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		//Render Origin at Center of screen:
		g.translate(Vars.mainFrame.renderPanel.getWidth() / 2, Vars.mainFrame.renderPanel.getHeight() / 2);

		renderPath(g);

		for (Obj obj : Vars.activeObjects)
		{
			g.setColor(obj.getColor());

			int x = (int) ((obj.getPosition().getX() + Vars.scaling_Delta.getX()) * Vars.scaling_ZoomFactor);
			int y = (int) ((obj.getPosition().getY() + Vars.scaling_Delta.getY()) * Vars.scaling_ZoomFactor);

			int radius = (int) (obj.getMass() * (Vars.scaling_ZoomFactor / Double.parseDouble("1E23")));

			if (Vars.forceRadius)
			{
				radius = 40;
			}

			int r_x = x - (radius / 2);
			int r_y = y - (radius / 2);

			// Render Object:
			g.fill(new Ellipse2D.Double(r_x, r_y, radius, radius));
			//

			// Render Text

			if (Vars.mainFrame.cb_showNames.isSelected())
			{
				g.setColor(Color.WHITE);
				g.drawString(obj.getName(), r_x, r_y - 10);
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
						Point p2 = obj.points.get(i /*+ 1*/);

						g.drawLine((int) (p1.getX()), (int) (p1.getY()), (int) (p2.getX()), (int) (p2.getY()));

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
