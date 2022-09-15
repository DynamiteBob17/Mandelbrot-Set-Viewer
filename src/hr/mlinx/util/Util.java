package hr.mlinx.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.UIManager;

public class Util {
	
	public static final Dimension RES = Toolkit.getDefaultToolkit().getScreenSize();
	public static final double SCALE = RES.getWidth() / 1920.0;
	public static final Random R = new Random();
	
	public static final float FONT_SIZE = 15f * (float) SCALE;
	
	public static void setUIFonts() {
		
		Font messageFont = new Font("Verdana", Font.PLAIN, (int) FONT_SIZE);
		Font componentFont = new Font("Segoe", Font.PLAIN, (int) FONT_SIZE);
		
		UIManager.put("OptionPane.messageFont", messageFont);
		UIManager.put("OptionPane.buttonFont", messageFont);
		UIManager.put("Label.font", componentFont);
		UIManager.put("TextField.font", componentFont);
		
	}
	
	public static double map(double val, double valLow, double valHigh, double returnValLow, double returnValHigh) {
		
		double ratio = (val - valLow) / (valHigh - valLow);
		
		return ratio * (returnValHigh - returnValLow) + returnValLow;
		
	}
	
	public static double log2(double n) {
		return Math.log(n) / Math.log(2);
	}
	
	public static Color[] createGradientColors(Color ... colors) {
		
		float div = 1f / colors.length;
		int len = 2048;
		Color[] colorArray = new Color[len];
		
		for (int i = 0; i < colors.length; ++i) {
			
			twoColorGradient((int) (i * div * len), (int) ((i + 1) * div * len),
							  colors[i], i == colors.length - 1 ? colors[0] : colors[i + 1],
							  colorArray);
			
		}
		
		return colorArray;
		
	}
	
	private static void twoColorGradient(int point1, int point2, Color c1, Color c2, Color[] colors) {
		
		int steps = point2 - point1;
		for (int i = 0; i < steps; ++i) {
			
			float ratio = (float) i / steps;
			int r = (int) (c2.getRed() * ratio + c1.getRed() * (1f - ratio));
			int g = (int) (c2.getGreen() * ratio + c1.getGreen() * (1f - ratio));
			int b = (int) (c2.getBlue() * ratio + c1.getBlue() * (1f - ratio));
			colors[i + point1] = new Color(r, g, b);
			
		}
		
	}
	
}
