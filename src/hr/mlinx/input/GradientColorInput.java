package hr.mlinx.input;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import hr.mlinx.util.Util;
import hr.mlinx.viewer.MandelbrotViewer;

public class GradientColorInput extends JPanel {
	private static final long serialVersionUID = -2252163795626712878L;
	
	private static final int GRADIENT_WIDTH = (int) (800 * Util.SCALE);
	private static final int GRADIENT_HEIGHT = (int) (80 * Util.SCALE);
	
	@SuppressWarnings("serial")
	private class Gradient extends JPanel {
		
		public Gradient() {
			super();
			
			setPreferredSize(new Dimension(GRADIENT_WIDTH, GRADIENT_HEIGHT));
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2 = (Graphics2D) g;
			Color[] gradientColors = Util.createGradientColors(colors);
			
			for (int i = 0; i < gradientColors.length; ++i) {
				g2.setColor(gradientColors[i]);
				double x = Util.map(i, 0, gradientColors.length, 0, getWidth());
				g2.fill(new Rectangle2D.Double(x, 0, x, getHeight()));
			}
		}
	}
	
	private MandelbrotViewer viewer;
	private Color[] colors;
	private JButton[] buttons;
	private Gradient gradient;

	public GradientColorInput(MandelbrotViewer viewer) {
		super();
		
		this.viewer = viewer;
		colors = viewer.getColors();
		
		setLayout(new GridLayout(0, 1));
		
		buttons = new JButton[colors.length];
		for (int i = 0; i < buttons.length; ++i) {
			add(buttons[i] = createColorChooserButton(colors[i], i));
		}
		
		JSlider colorIndexFactorSlider = new JSlider(1, 3072, viewer.getColorIndexFactor());
		colorIndexFactorSlider.addChangeListener(l -> {
			int cif = colorIndexFactorSlider.getValue();
			viewer.setColorIndexFactor(cif);
		});
		JLabel cifLabel = new JLabel("Color Index Factor:");
		cifLabel.setFont(cifLabel.getFont().deriveFont(Util.FONT_SIZE * 1.2f));
		JPanel cifPanel = new JPanel();
		cifPanel.setLayout(new BorderLayout());
		cifPanel.add(cifLabel, BorderLayout.NORTH);
		cifPanel.add(colorIndexFactorSlider, BorderLayout.CENTER);
		add(cifPanel);
		
		JSlider colorStartSlider = new JSlider(0, 2048, viewer.getColorStart());
		colorStartSlider.addChangeListener(l -> {
			int cs = colorStartSlider.getValue();
			viewer.setColorStart(cs);
		});
		JLabel csLabel = new JLabel("Color Start:");
		csLabel.setFont(csLabel.getFont().deriveFont(Util.FONT_SIZE * 1.2f));
		csLabel.setMaximumSize(new Dimension((int) (500 * Util.SCALE), (int) (50 * Util.SCALE)));
		JPanel csPanel = new JPanel();
		csPanel.setLayout(new BorderLayout());
		csPanel.add(csLabel, BorderLayout.NORTH);
		csPanel.add(colorStartSlider, BorderLayout.CENTER);
		add(csPanel);
		
		add(gradient = new Gradient());
	}
	
	private JButton createColorChooserButton(Color c, int idx) {
		JButton button = new JButton("Choose color #" + (idx + 1));
		
		colorButton(button, c);
		button.setFont(getFont().deriveFont(Util.FONT_SIZE * 2f));
		button.addActionListener(new Action(this, button.getText(), c, idx));		
		return button;
	}
	
	private class Action implements ActionListener {
		private GradientColorInput ci;
		private String s;
		private Color c;
		private int idx;
		public Action(GradientColorInput ci, String s, Color c, int idx) {
			super();
			this.ci = ci;
			this.s = s;
			this.c = c;
			this.idx = idx;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			Color chosenColor = JColorChooser.showDialog(null, s, c);
			if (chosenColor != null) {
				ci.setColor(idx, chosenColor);
				c = chosenColor;
			}
		}
	}
	
	private void colorButton(JButton btn, Color c) {
		btn.setBackground(c);
		btn.setForeground(getContrastColor(c));
	}
	
	private Color getContrastColor(Color color) {
		  double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
		  return y >= 128 ? Color.black : Color.white;
	}
	
	private void setColor(int idx, Color c) {
		colors[idx] = c;
		colorButton(buttons[idx], c);
		gradient.repaint();
		viewer.setGradientColors(colors);
	}
	
}
