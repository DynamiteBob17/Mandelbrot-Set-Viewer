package hr.mlinx.plotting;

public class Complex {
	
	private double x;
	private double y;
	
	public Complex(double x, double y) {
		super();
		
		this.x = x;
		this.y = y;
	}
	
	public void squared() {
		double xMult = x * x - y * y;
		double yMult = 2 * x * y;
		
		this.x = xMult;
		this.y = yMult;
	}
	
	public void add(Complex addend) {
		this.x += addend.x;
		this.y += addend.y;
	}
	
	public double mod() {
		if (x != 0 || y != 0)
            return Math.sqrt(x * x + y * y);
        else 
        	return 0.0;
	}

	@Override
	public String toString() {
		return "Z = " + x + " + " + y + "i";
	}
	
}
