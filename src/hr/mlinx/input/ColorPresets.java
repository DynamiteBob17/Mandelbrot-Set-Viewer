package hr.mlinx.input;

import java.awt.Color;
import java.util.List;

import hr.mlinx.util.Util;
import hr.mlinx.viewer.MandelbrotViewer.ColorPreset;

public class ColorPresets {
	
	public static void fillColorPresets(List<ColorPreset> colorPresets) {
		
		Color[] colors;
		Color[] gradientColors;
		
		colors = new Color[] {
				Color.RED,
				Color.YELLOW,
				Color.GREEN,
				Color.BLUE,
				Color.CYAN,
				Color.MAGENTA
		};
		gradientColors = Util.createGradientColors(colors);
		colorPresets.add(new ColorPreset(colors, 128, 0, gradientColors));
		
		colors = new Color[] {
				Color.BLACK,
				Color.RED,
				Color.YELLOW,
				Color.WHITE,
				Color.YELLOW,
				Color.RED
		};
		gradientColors = Util.createGradientColors(colors);
		colorPresets.add(new ColorPreset(colors, 128, 0, gradientColors));
		
		colors = new Color[] {
				new Color(209, 1, 27),
				Color.WHITE,
				Color.BLACK
		};
		gradientColors = Util.createGradientColors(colors);
		colorPresets.add(new ColorPreset(colors, 128, 0, gradientColors));
		
		colors = new Color[] {
				Color.WHITE,
				new Color(0, 102, 255),
				new Color(51, 51, 51),
				new Color(255, 0, 204)
		};
		gradientColors = Util.createGradientColors(colors);
		colorPresets.add(new ColorPreset(colors, 128, 0, gradientColors));
		
		colors = new Color[] {
				new Color(74, 7, 203),
				new Color(251, 4, 114),
				new Color(247, 172, 11),
				new Color(246, 0, 250),
				new Color(112, 24, 203)
		};
		gradientColors = Util.createGradientColors(colors);
		colorPresets.add(new ColorPreset(colors, 128, 0, gradientColors));
		
		colors = new Color[] {
				new Color(132, 94, 194),
				new Color(214, 93, 177),
				new Color(255, 111, 145),
				new Color(255, 150, 113),
				new Color(255, 199, 95),
				new Color(249, 248, 113)
		};
		gradientColors = Util.createGradientColors(colors);
		colorPresets.add(new ColorPreset(colors, 128, 1536, gradientColors));
		
		colors = new Color[] {
				new Color(21, 23, 37),
				new Color(28, 43, 77),
				new Color(218, 145, 0),
				new Color(209, 84, 33),
				new Color(153, 30, 11),
				new Color(104, 3, 0),
				new Color(218, 145, 0)
		};
		gradientColors = Util.createGradientColors(colors);
		colorPresets.add(new ColorPreset(colors, 128, 0, gradientColors));
		
		colors = new Color[] {
				new Color(255, 105, 97),
				new Color(255, 180, 128),
				new Color(248, 243, 141),
				new Color(66, 214, 165),
				new Color(8, 202, 209),
				new Color(89, 173, 246),
				new Color(157, 148, 255),
				new Color(199, 128, 232)
		};
		gradientColors = Util.createGradientColors(colors);
		colorPresets.add(new ColorPreset(colors, 128, 0, gradientColors));
		
		colors = new Color[] {
				new Color(46, 43, 40),
				new Color(59, 55, 52),
				new Color(71, 68, 64),
				new Color(84, 80, 76),
				new Color(107, 80, 107),
				new Color(171, 61, 169),
				new Color(222, 37, 218),
				new Color(235, 68, 232),
				new Color(255, 128, 255)
		};
		gradientColors = Util.createGradientColors(colors);
		colorPresets.add(new ColorPreset(colors, 128, 0, gradientColors));
		
	}
	
}
