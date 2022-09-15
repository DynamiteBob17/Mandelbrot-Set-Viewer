package hr.mlinx.viewer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;

import hr.mlinx.util.Util;

public class Frame {
	
	public static void set(MandelbrotViewer viewer) {
		JFrame frame = new JFrame();
		
		try {	
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		Util.setUIFonts();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.add(viewer);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		
		showControls();
	}
	
	public static void showControls() {
		
		JOptionPane.showMessageDialog(null, "Controls:\n\r"
				 + "MOUSE - draw rectangle to zoom in\n\r"
				 + "H - bring up this window\n\r"
				 + "R - random gradient color palette\n\r"
				 + "0 - set default color palette\n\r"
				 + "1,2,3,...,8,9 - set preset color palettes\n\r"
				 + "C - create gradient color palette\n\r"
				 + "CTRL/SHIFT - previous/next zoom\n\r"
				 + "SPACE - enter coordinates manually\n\r"
				 + "ENTER - enter image accuracy (stability/iterations threshold), recommended to increase it more and more as you zoom in\n\r"
				 + "ESC - exit the program");
		
	}
	
}
