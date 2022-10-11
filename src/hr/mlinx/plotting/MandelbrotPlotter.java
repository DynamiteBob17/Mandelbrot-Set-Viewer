package hr.mlinx.plotting;

import java.awt.Color;
import java.awt.image.DataBufferInt;
import java.util.LinkedList;
import java.util.List;

import hr.mlinx.util.Util;
import hr.mlinx.viewer.MandelbrotViewer;
import hr.mlinx.viewer.MandelbrotViewer.Entry;

public class MandelbrotPlotter {
	
	private List<PlotPartly> plotters;
	
	public MandelbrotPlotter(MandelbrotViewer viewer, Entry entry,
							 int colorIndexFactor, Color[] gradientColors, int numOfThreads) {
		super();
		
		plotters = new LinkedList<>();
		int partition = ((DataBufferInt) entry.mandelbrotSet().getRaster().getDataBuffer()).getData().length / numOfThreads;
		
		for (int i = 0; i < numOfThreads; ++i) {
			
			plotters.add(new PlotPartly(viewer, entry,
										colorIndexFactor, gradientColors, i * partition, i * partition + partition));
			
		}
	}
	
	public void plot() {
		
		plotters.forEach(p -> {
			
			p.start();
			
		});
		
	}
	
	public boolean isPlotting() {
		
		for (PlotPartly p : plotters) {
			
			if (p.isAlive())
				return true;
			
		}
		
		return false;
	}
	
	private class PlotPartly extends Thread {
		
		private MandelbrotViewer viewer;
		private Entry entry;
		private int colorIndexFactor;
		private Color[] gradientColors;
		private int start, end;
		
		public PlotPartly(MandelbrotViewer viewer, Entry entry,
						  int colorIndexFactor, Color[] gradientColors, int start, int end) {
			super();
			
			this.viewer = viewer;
			this.entry = entry;
			this.colorIndexFactor = colorIndexFactor;
			this.gradientColors = gradientColors;
			this.start = start;
			this.end = end;
		}
		
		@Override
		public void run() {
			
			int[] pixels = ((DataBufferInt) entry.mandelbrotSet().getRaster().getDataBuffer()).getData();
			int w = entry.mandelbrotSet().getWidth();
			int h = entry.mandelbrotSet().getHeight();
			int x;
			int y;
			double x1 = entry.coords().realStart();
			double x2 = entry.coords().realEnd();
			double y1 = entry.coords().imagStart();
			double y2 = entry.coords().imagEnd();
			int stabilityThreshold = entry.stabilityThreshold();
			int iterations;
			Complex z;
			Complex c;
			double smoothed;
			int colorIdx;
			
			for (int i = start; i < end; ++i) {
				
				x = i % w;
				y = i / w % h;
				
				iterations = 0;
				z = new Complex(0, 0);
				c = new Complex(Util.map(x, 0, w, x1, x2),
						 		Util.map(y, 0, h, y1, y2));
				
				while (z.norm() < 256 && iterations < stabilityThreshold) {
					
					z.squared();
					z.add(c);
					++iterations;
					
				}
				
				if (iterations == stabilityThreshold) {
					
					pixels[i] = 0;
					entry.smoothed()[i] = -1;
					continue;
					
				}
				
				smoothed = iterations - Util.log2(Util.log2(z.norm()));
				if (smoothed < 0.0)
					smoothed = 0;
				entry.smoothed()[i] = smoothed;
				
				colorIdx = ((int) (Math.sqrt(smoothed) * colorIndexFactor) + viewer.getColorStart()) % gradientColors.length;
				pixels[i] = gradientColors[colorIdx].getRGB();
				
			}
			
			viewer.repaint();
			
		}
		
	}
	
}
