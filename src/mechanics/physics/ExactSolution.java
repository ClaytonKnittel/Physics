package mechanics.physics;

import java.util.LinkedList;

import graphics.Color;
import mechanics.graphics.Screen;
import mechanics.utils.DynamicDrawable;
import mechanics.utils.LineSegment;
import tensor.DVector;

public class ExactSolution implements DynamicDrawable {
	
	private Screen screen;
	private Body main, other;
	private int numSteps;
	private Color color;
	
	private LinkedList<LineSegment> lines;
	
	public ExactSolution(Body main, Body other, int numSteps, Color color) {
		this.main = main;
		this.other = other;
		this.numSteps = numSteps;
		this.color = color;
		lines = new LinkedList<LineSegment>();
	}
	
	private void reset() {
		for (LineSegment l : lines)
			screen.remove(l);
		lines.clear();
	}
	
	private void push() {
		for (LineSegment l : lines)
			screen.add(l);
	}
	
	public void calculatePath() {
		reset();
		DVector cm = PMath.centerOfMass(main, other);
		
		// x-axis
		DVector x = main.pos().minus(cm).normalized();
		// y-axis
		DVector y = x.cross(main.velocity()).cross(x).normalized();
		// angular momentum
		double l2 = main.pos().minus(cm).cross(main.velocity()).mag2();
		double factor = PMath.square(other.mass() / (other.mass() + main.mass()));
		double d = factor * other.mass() / (PMath.Ginv * l2);
		double c = 1 / main.pos().minus(cm).mag() - d;
		
		
		double pi2 = Math.PI * 2;
		
		double rp = 1 / (c + d);
		double r;
		
		//System.out.println(d + " " + c + "  " + rp);
		
		for (int frac = 1; frac <= numSteps; frac++) {
			r = 1 / (c * Math.cos((pi2 * frac) / numSteps) + d);
			lines.add(new LineSegment(polarBasisToVector(rp, pi2 * (frac - 1) / numSteps, x, y).plus(cm).toVector(), polarBasisToVector(r, pi2 * frac / numSteps, x, y).plus(cm).toVector(), color));
			rp = r;
		}
		push();
	}
	
	private static DVector polarBasisToVector(double r, double theta, DVector x, DVector y) {
		return x.times((r * Math.cos(theta))).plus(y.times((r * Math.sin(theta))));
	}
	
	@Override
	public void give(Screen s) {
		screen = s;
	}
	
	
	
}
