
package at.xer0.Support;

import java.awt.Color;
import java.util.Random;

public enum ColorEnum {

	Magenta, Light_Gray, Gray, Dark_Gray, Black, Red, Pink, Orange, Yellow, Green, Cyan, Blue;

	
	public static Color randomColor()
	{
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		
		return new Color(r, g, b);
	}
	
	public static Color getAWTColor(ColorEnum ce)
	{

		Color returnColor = null;

		switch (ce)
		{
		case Magenta:
			returnColor = (Color.MAGENTA);
			break;
		case Light_Gray:
			returnColor = (Color.LIGHT_GRAY);
			break;
		case Gray:
			returnColor = (Color.GRAY);
			break;
		case Dark_Gray:
			returnColor = (Color.DARK_GRAY);
			break;
		case Black:
			returnColor = (Color.BLACK);
			break;
		case Red:
			returnColor = (Color.RED);
			break;
		case Pink:
			returnColor = (Color.PINK);
			break;
		case Orange:
			returnColor = (Color.ORANGE);
			break;
		case Yellow:
			returnColor = (Color.YELLOW);
			break;
		case Green:
			returnColor = (Color.GREEN);
			break;
		case Cyan:
			returnColor = (Color.CYAN);
			break;
		case Blue:
			returnColor = (Color.BLUE);
			break;
		default:
			returnColor = (Color.ORANGE);
			break;
		}

		return returnColor;
	}

}
