package hr.mlinx.viewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import hr.mlinx.input.ColorPresets;
import hr.mlinx.input.KeyInput;
import hr.mlinx.plotting.MandelbrotPlotter;
import hr.mlinx.util.Util;

/*
 * This program has limited zoom because coordinates
 * are stored as double values and they are limited to
 * around 16 decimal places of precision. The best solution
 * to this problem is to use the Java arbitrary precision 
 * BigDecimal class combined with perturbation theory which I might or might not use
 * in a different version of this program some time in the future.
 */

public class MandelbrotViewer extends JPanel {
	private static final long serialVersionUID = 4054652191688122802L;
	
	private static final int NUM_OF_THREADS = 256;
	
	public record Coords(double realStart, double realEnd, double imagStart, double imagEnd) {}
	public record Entry(Coords coords, BufferedImage mandelbrotSet, double[] smoothed, int stabilityThreshold) {}
	public record ColorPreset(Color[] colors, int colorIndexFactor, int colorStart, Color[] gradientColors) {}
	
	private Point zoomStart;
	private Point zoomEnd;
	
	private int stabilityThreshold;
	private List<Entry> history;
	private int currentEntryIdx;
	private Color[] colors;
	private int colorIndexFactor;
	private int colorStart;
	private Color[] gradientColors;
	private List<ColorPreset> colorPresets;
	private MandelbrotPlotter plotter;
	
	public MandelbrotViewer() {
		super();
		
		zoomStart = zoomEnd = null;
		addZoomListeners();
		
		stabilityThreshold = 1000;
		Coords coords = new Coords(-2, 1, -1, 1);
		BufferedImage mandelbrotSet = new BufferedImage(Util.RES.width, Util.RES.height,
											 BufferedImage.TYPE_INT_RGB);
		history = new LinkedList<>();
		history.add(new Entry(coords, mandelbrotSet, new double[Util.RES.width * Util.RES.height], stabilityThreshold));
		colors = new Color[] {
				new Color(0, 7, 100),
				new Color(32, 107, 203),
				new Color(237, 255, 255),
				new Color(255, 170, 0),
				new Color(165, 42, 42),
				new Color(0, 2, 0)
		};
		currentEntryIdx = 0;
		colorIndexFactor = 128;
		colorStart = 0;
		gradientColors = Util.createGradientColors(colors);
		colorPresets = new ArrayList<>();
		colorPresets.add(new ColorPreset(Arrays.copyOf(colors, colors.length), colorIndexFactor, colorStart,
										 Arrays.copyOf(gradientColors, gradientColors.length)));
		ColorPresets.fillColorPresets(colorPresets);
		drawMandelbrot();
		
		Frame.set(this);
		addKeyListener(new KeyInput(this));
		requestFocus();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(history.get(currentEntryIdx).mandelbrotSet(), 0, 0, null);
		drawZoomRect(g2);
		
	}
	
	private void drawMandelbrot() {
		
		plotter = new MandelbrotPlotter(this, history.get(currentEntryIdx), colorIndexFactor, gradientColors, NUM_OF_THREADS);

		plotter.plot();
		
	}
	
	private void drawZoomRect(Graphics2D g2) {
		
		if (zoomStart != null && zoomEnd != null 
			&& !zoomStart.equals(zoomEnd)) {
			
			double x1 = zoomStart.getX();
			double y1 = zoomStart.getY();
			double x2 = zoomEnd.getX();
			double y2 = zoomEnd.getY();
			double width = Math.abs(x2 - x1);
			double height = Math.abs(y2 - y1);
			double x;
			double y;
			
			x = x2 > x1 ? x1 : x2;
			
			y = y2 > y1 ? y1 : y2;
			
			g2.setColor(Color.WHITE);
			g2.draw(new Rectangle2D.Double(x, y, width, height));
			
		}
		
	}
	
	private void addZoomListeners() {
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				
					if (plotter.isPlotting()) {
					
					e.consume();
					return;
					
				}
				
				zoomStart = zoomEnd = e.getPoint();
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
				if (plotter.isPlotting()) {
					
					e.consume();
					return;
					
				}
				
				if (zoomStart != null && zoomEnd != null && !zoomStart.equals(zoomEnd)) {
					
					double x1, x2, y1, y2;
					
					if (zoomStart.getX() < zoomEnd.getX()) {
						
						x1 = zoomStart.getX();
						x2 = zoomEnd.getX();
						
					} else {
						
						x1 = zoomEnd.getX();
						x2 = zoomStart.getX();
						
					}
					
					if (zoomStart.getY() < zoomEnd.getY()) {
						
						y1 = zoomStart.getY();
						y2 = zoomEnd.getY();
						
					} else {
						
						y1 = zoomEnd.getY();
						y2 = zoomStart.getY();
						
					}
					
					Coords coords = history.get(currentEntryIdx).coords();
					
					x1 = Util.map(x1, 0, getWidth(), coords.realStart(), coords.realEnd());
					x2 = Util.map(x2, 0, getWidth(), coords.realStart(), coords.realEnd());
					y1 = Util.map(y1, 0, getHeight(), coords.imagStart(), coords.imagEnd());
					y2 = Util.map(y2, 0, getHeight(), coords.imagStart(), coords.imagEnd());
					
					history.add(++currentEntryIdx, new Entry(new Coords(x1, x2, y1, y2), 
												 new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB),
												 new double[getWidth() * getHeight()],
												 stabilityThreshold));
					
					removeSubsequentEntries();
					
					drawMandelbrot();
					
				}
				
				zoomStart = zoomEnd = null;
				
			}
			
		});
		
		addMouseMotionListener(new MouseAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
				if (plotter.isPlotting()) {
					
					e.consume();
					return;
					
				}
				
				
				if (zoomStart != null && zoomEnd != null) {
					
					if (e.getPoint().getX() == zoomStart.getX() 
						|| e.getPoint().getY() == zoomStart.getY()) {
							
						zoomEnd = zoomStart;
							
					} else {
							
						zoomEnd = keepAspectRatio(zoomStart, e.getPoint());
							
					}
						
					repaint();
					
				}
				
			}
			
		});
		
	}
	
	private Point keepAspectRatio(Point start, Point end) {
		
		double desiredRatio = (double) getWidth() / getHeight();
		double w = end.getX() - start.getX();
		double h = end.getY() - start.getY();
		double currentRatio = Math.abs(w / h);
		double diff;
		
		if (currentRatio > desiredRatio) {
			
			diff = Math.abs(h) * (currentRatio / desiredRatio - 1.0);
			end.setLocation(end.getX(), end.getY() + diff * Math.signum(h));
			
		} else if (currentRatio < desiredRatio) {
			
			diff = Math.abs(w) * (desiredRatio / currentRatio - 1.0);
			end.setLocation(end.getX() + diff * Math.signum(w), end.getY());
			
		}
		
		return end;
		
	}
	
	public void previousZoom() {
		
		if (currentEntryIdx > 0) {
			
			--currentEntryIdx;
			
			colorIdx(currentEntryIdx);
			
		}
		
	}
	
	public void nextZoom() {
		
		if (currentEntryIdx < history.size() - 1) {
			
			++currentEntryIdx;
			
			colorIdx(currentEntryIdx);
			
		}
		
	}
	
	private void colorIdx(int idx) {
		
		if (stabilityThreshold != history.get(idx).stabilityThreshold()) {
			
			Coords coords = history.get(idx).coords();
			
			history.remove(idx);
			history.add(idx, new Entry(coords, new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB),
									   new double[getWidth() * getHeight()], stabilityThreshold));
			
			drawMandelbrot();
			
		} else {
			
			colorPixels();
			
		}
		
	}
	
	private void removeSubsequentEntries() {
		
		for (int i = currentEntryIdx + 1; i < history.size(); ++i) {
			
			history.remove(i--);
			
		}
		
	}
	
	public void setCoordinates(double realStart, double realEnd, double imagStart, double imagEnd) {
		
		Coords coordsNew = new Coords(realStart, realEnd, imagStart, imagEnd);
		
		if (!history.get(currentEntryIdx).coords().equals(coordsNew)) {
			
			history.add(++currentEntryIdx, new Entry(coordsNew, new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB),
										 new double[getWidth() * getHeight()], stabilityThreshold));
			
			removeSubsequentEntries();
			
			drawMandelbrot();
			 
		}
		
	}
	
	public double[] getCoordinates() {
		
		Coords coords = history.get(currentEntryIdx).coords();
		
		return new double[] {
				coords.realStart(),
				coords.realEnd(),
				coords.imagStart(),
				coords.imagEnd()
		};
		
	}
	
	public void setStabThresh(int stabilityThreshold) {
		
		if (this.stabilityThreshold != stabilityThreshold) {
			
			this.stabilityThreshold = stabilityThreshold;
			
			Coords coords = history.get(currentEntryIdx).coords();
			history.remove(currentEntryIdx);
			history.add(currentEntryIdx, new Entry(coords, new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB),
									   new double[getWidth() * getHeight()], stabilityThreshold));
			
			drawMandelbrot();
			
		} else {
			
			colorPixels();
			
		}
		
	}
	
	public int getStabThresh() {
		
		return stabilityThreshold;
		
	}
	
	public Color[] getGradientColors() {
		
		return gradientColors;
		
	}
	
	public void setGradientColors(Color ... colors) {
		
		this.colors = colors;
		gradientColors = Util.createGradientColors(colors);
		
		colorPixels();
		
	}
	
	private void colorPixels() {
		
		int[] pixels = ((DataBufferInt) history.get(currentEntryIdx).mandelbrotSet().getRaster().getDataBuffer()).getData();
		double[] smoothed = history.get(currentEntryIdx).smoothed();
		double smooth;
		
		for (int i = 0; i < pixels.length; ++i) {
			
			smooth = smoothed[i];
			
			if (smooth < 0.0)
				pixels[i] = 0;
			else
				pixels[i] = gradientColors[((int) (smooth * colorIndexFactor) + colorStart) % gradientColors.length].getRGB();
			
		}
		
		repaint();
		
	}
	
	public void randomGradientColors() {
		
		for (int i = 0; i < colors.length; ++i) {
			
			colors[i] = randomColor();
			
		}
		
		setGradientColors(colors);
		
	}
	
	private Color randomColor() {
		return new Color(Util.R.nextInt(256), Util.R.nextInt(256), Util.R.nextInt(256));
	}
	
	public Color[] getColors() {
		return colors;
	}
	
	public MandelbrotPlotter getPlotter() {
		return plotter;
	}
	
	public int getColorIndexFactor() {
		return colorIndexFactor;
	}
	
	public void setColorIndexFactor(int colorIndexFactor) {
		
		if (this.colorIndexFactor != colorIndexFactor) {
			
			this.colorIndexFactor = colorIndexFactor;
			
			colorPixels();
			
		}
		
	}
	
	public int getColorStart() {
		return colorStart;
	}
	
	public void setColorStart(int colorStart) {
		
		if (this.colorStart != colorStart) {
			
			this.colorStart = colorStart;
			
			colorPixels();
			
		}
		
	}
	
	public void setColorPreset(int idx) {
		
		if (idx < colorPresets.size() && idx >= 0) {
			
			ColorPreset colorPreset = colorPresets.get(idx);
			colors = Arrays.copyOf(colorPreset.colors(), colorPreset.colors().length);
			colorIndexFactor = colorPreset.colorIndexFactor();
			colorStart = colorPreset.colorStart();
			gradientColors = Arrays.copyOf(colorPreset.gradientColors(), colorPreset.gradientColors().length);
			
			colorPixels();
			
		}
		
	}
	
	public List<Entry> getHistory() {
		return history;
	}

	public static void main(String[] args) {
		new MandelbrotViewer();
	}
	
}
