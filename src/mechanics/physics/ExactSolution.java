package mechanics.physics;

import java.awt.Color;
import java.util.LinkedList;

import mechanics.graphics.Screen;
import mechanics.utils.DynamicDrawable;
import mechanics.utils.LineSegment;
import tensor.Vector;

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
		Vector cm = main.pos().times(main.mass()).plus(other.pos().times(other.mass())).divide(main.mass() + other.mass());
		
		// x-axis
		Vector x = main.pos().minus(cm).normalized();
		// y-axis
		Vector y = x.cross(main.velocity()).cross(x).normalized();
		// angular momentum
		float l2 = main.pos().minus(cm).cross(main.velocity()).mag2();
		float d = (float) (other.mass() / PMath.Ginv / l2);
		float c = 1 / main.pos().minus(cm).mag() - d;
		
		float pi2 = (float) (Math.PI * 2);
		
		float rp = 1 / (c + d);
		float r;
		
		for (int frac = 1; frac <= numSteps; frac++) {
			r = (float) (1 / (c * Math.cos((pi2 * frac) / numSteps) + d));
			lines.add(new LineSegment(polarBasisToVector(rp, pi2 * (frac - 1) / numSteps, x, y).plus(cm), polarBasisToVector(r, pi2 * frac / numSteps, x, y).plus(cm), color));
			rp = r;
		}
		push();
	}
	
	private static Vector polarBasisToVector(float r, float theta, Vector x, Vector y) {
		return x.times((float) (r * Math.cos(theta))).plus(y.times((float) (r * Math.sin(theta))));
	}
	
	@Override
	public void give(Screen s) {
		screen = s;
	}
	
	
	
}
