package hr.mlinx.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import hr.mlinx.viewer.Frame;
import hr.mlinx.viewer.MandelbrotViewer;

public class KeyInput extends KeyAdapter {
	
	private MandelbrotViewer viewer;
	
	public KeyInput(MandelbrotViewer viewer) {
		super();
		
		this.viewer = viewer;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (viewer.getPlotter().isPlotting()) {
			
			e.consume();
			return;
			
		}
		
		int key = e.getKeyCode();
		
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			
			System.exit(0);
			
		} else if (e.getKeyCode() == KeyEvent.VK_H) {
			
			Frame.showControls();
			
		} else if (key == KeyEvent.VK_SPACE) {
			
			CoordinateInput ci = new CoordinateInput(viewer);
			
			int option = JOptionPane.showConfirmDialog(viewer,
													   ci,
													   "Input",
													   JOptionPane.OK_CANCEL_OPTION);
			
			if (option == JOptionPane.OK_OPTION) {
				
				try {
					
					double x1 = Double.parseDouble(ci.getX1().getText());
					double x2 = Double.parseDouble(ci.getX2().getText());
					double y1 = Double.parseDouble(ci.getY1().getText());
					double y2 = Double.parseDouble(ci.getY2().getText());
					
					viewer.setCoordinates(x1, x2, y1, y2);
					
				} catch (NumberFormatException ex) {
				}
				
			}
			
		} else if (key == KeyEvent.VK_ENTER) {
			
			String input = JOptionPane.showInputDialog("Enter image accuracy (stability/iterations threshold) [> 0]:",
													   viewer.getStabThresh());
			
			if (input != null && !input.isBlank()) {
				
				try {
					
					int stabilityThreshold = Integer.parseInt(input);
					
					if (stabilityThreshold > 0) {
						
						viewer.setStabThresh(stabilityThreshold);
						
					}
					
				} catch (NumberFormatException ex) {
				}
				
			}
			
		} else if (key == KeyEvent.VK_DOWN) {
			
			viewer.previousZoom();
			
		} else if (key == KeyEvent.VK_UP) {
			
			viewer.nextZoom();
			
		} else if (key == KeyEvent.VK_R) {
			
			viewer.randomGradientColors();
			
		} else if (key == KeyEvent.VK_C) {
			
			GradientColorInput ci = new GradientColorInput(viewer);
			
			JOptionPane.showConfirmDialog(viewer,
									      ci,
								          "Create Gradient",
										  JOptionPane.DEFAULT_OPTION,
										  JOptionPane.PLAIN_MESSAGE);
			
		} else if (key == KeyEvent.VK_0) {
			
			viewer.setColorPreset(0);
			
		} else if (key == KeyEvent.VK_1) {
			
			viewer.setColorPreset(1);
			
		} else if (key == KeyEvent.VK_2) {
			
			viewer.setColorPreset(2);
			
		} else if (key == KeyEvent.VK_3) {
			
			viewer.setColorPreset(3);
			
		} else if (key == KeyEvent.VK_4) {
			
			viewer.setColorPreset(4);
			
		} else if (key == KeyEvent.VK_5) {
			
			viewer.setColorPreset(5);
			
		} else if (key == KeyEvent.VK_6) {
			
			viewer.setColorPreset(6);
			
		} else if (key == KeyEvent.VK_7) {
			
			viewer.setColorPreset(7);
			
		} else if (key == KeyEvent.VK_8) {
			
			viewer.setColorPreset(8);
			
		} else if (key == KeyEvent.VK_9) {
			
			viewer.setColorPreset(9);
			
		}
		
	}
	
}
