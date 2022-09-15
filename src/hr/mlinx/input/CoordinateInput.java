package hr.mlinx.input;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.mlinx.viewer.MandelbrotViewer;

public class CoordinateInput extends JPanel {
	private static final long serialVersionUID = 6029735245890812958L;
	
	private JTextField x1;
	private JTextField x2;
	private JTextField y1;
	private JTextField y2;
	
	public CoordinateInput(MandelbrotViewer viewer) {
		super();
		
		x1 = new JTextField(Double.toString(viewer.getCoordinates()[0]));
		x2 = new JTextField(Double.toString(viewer.getCoordinates()[1]));
		y1 = new JTextField(Double.toString(viewer.getCoordinates()[2]));
		y2 = new JTextField(Double.toString(viewer.getCoordinates()[3]));
		
		setLayout(new GridLayout(5, 2));
		add(new JLabel("Enter coordinates:"));
		add(new JLabel(""));
		add(new JLabel("Real min:"));
		add(x1);
		add(new JLabel("Real max:"));
		add(x2);
		add(new JLabel("Imaginary min:"));
		add(y1);
		add(new JLabel("Imaginary max:"));
		add(y2);
	}

	public JTextField getX1() {
		return x1;
	}

	public JTextField getX2() {
		return x2;
	}

	public JTextField getY1() {
		return y1;
	}

	public JTextField getY2() {
		return y2;
	}
	
}
