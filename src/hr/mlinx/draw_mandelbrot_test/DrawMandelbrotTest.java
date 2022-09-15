package hr.mlinx.draw_mandelbrot_test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import hr.mlinx.plotting.Complex;
import hr.mlinx.util.Util;

public class DrawMandelbrotTest extends JPanel implements MouseMotionListener {
	private static final long serialVersionUID = 7353270855615987580L;
	
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 900;
	private static final int RECT_WIDTH = 40;
	private static final int RECT_HEIGHT = 40;
	private static final Color RECT_COLOR = Color.GREEN;
	private static final int STABILITY_THRESHOLD = 100;
	
	private BufferedImage image;
	private int[] pixels;
	private Rectangle rect;
	
	public DrawMandelbrotTest() {
		super();
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		rect = new Rectangle(0, 0, 0, 0);
		addMouseMotionListener(this);
		
		JFrame frame = new JFrame("Draw Mandelbrot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		double x, y;
		Complex z, c;
		int iterations = 0;
		
		for (int i = rect.x; i < rect.x + rect.width; ++i) {
			for (int j = rect.y; j < rect.y + rect.height; ++j) {
				x = i;
				y = j;
				
				x = Util.map(x, 0, WIDTH, -2, 2);
				y = Util.map(y, 0, HEIGHT, -2, 2);
				
				c = new Complex(x, y);
				z = new Complex(0, 0);
				iterations = 0;
				
				while (z.mod() < 2 && iterations < STABILITY_THRESHOLD) {
					z.squared();
					z.add(c);
					++iterations;
				}
				
				if (iterations == STABILITY_THRESHOLD)
					pixels[j * WIDTH + i] = RECT_COLOR.getRGB();
			}
		}
		
		g.drawImage(image, 0, 0, null);
		g.setColor(RECT_COLOR);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(3f));
		g2.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
		g2.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DrawMandelbrotTest();
		});
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX() - RECT_WIDTH / 2;
		int y = e.getY() - RECT_HEIGHT / 2;
		
		if (new Rectangle(0, 0, WIDTH, HEIGHT).contains(new Rectangle(x, y, RECT_WIDTH, RECT_HEIGHT))) {
			rect.setRect(x, y, RECT_WIDTH, RECT_HEIGHT);
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX() - RECT_WIDTH / 2;
		int y = e.getY() - RECT_HEIGHT / 2;
		
		if (new Rectangle(0, 0, WIDTH, HEIGHT).contains(new Rectangle(x, y, RECT_WIDTH, RECT_HEIGHT))) {
			rect.setRect(x, y, RECT_WIDTH, RECT_HEIGHT);
			repaint();
		}
	}
	
}
