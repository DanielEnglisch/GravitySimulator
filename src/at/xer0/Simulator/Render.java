package at.xer0.Simulator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import at.xer0.Support.Obj;
import at.xer0.Support.Point;
import at.xer0.Support.Vars;


public class Render
{
	public static void renderFrame(Graphics gn)
	{
		// Actual Render Logic

		Graphics2D g = (Graphics2D) gn;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		
		for (Obj obj : Vars.activeObjects)
		{
			g.setColor(obj.getColor());

			int x = (int) obj.getPosition().getX();
			int y = (int) obj.getPosition().getY();

			int radius = obj.getRadius();

			int r_x = x - (radius / 2);
			int r_y = y - (radius / 2);
			int r_xVel = ((int) obj.getVelocity().getX());
			int r_yVel = ((int) obj.getVelocity().getY());

			// Render Object:
			g.fillOval(r_x, r_y, radius, radius);
			//

			// Render Path:
			if (Vars.mainFrame.cb_drawPath.isSelected())
			{
				g.setColor(obj.getColor());

				for (int i = 0; i < obj.points.size(); i++)
				{
					try
					{
						Point p1 = obj.points.get(i);
						Point p2 = obj.points.get(i + 1);

						g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());

					} catch (Exception exx)
					{
					}

				}
			}

			//

			// Velocity Vector:
			if (Vars.mainFrame.cb_speedVec.isSelected())
			{
				g.setColor(Color.BLACK);
				g.drawLine(x, y, x + r_xVel, y + r_yVel);
			}
			//
		}
	}
}
