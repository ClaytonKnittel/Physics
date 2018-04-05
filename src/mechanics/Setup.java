package mechanics;

import java.awt.Color;

import mechanics.graphics.Screen;
import mechanics.physics.Body;
import mechanics.physics.ExactSolution;
import mechanics.physics.PMath;
import mechanics.physics.Planet;
import tensor.DVector;

public class Setup {
	
	
	public static final Setup ELLIPTICAL, CIRCULAR, PARABOLIC, HYPERBOLIC, POLAR;
	
	private static final Color earthColor = new Color(57, 118, 40), sunColor = new Color(253, 184, 19);
	
	static {
		Planet earth = new Planet(new DVector(30, 0, -100), new DVector(0, 0, -68), 10, 3, earthColor);
		Planet sun = new Planet(new DVector(0, 0, -100), new DVector(0, 0, .068f), 10000, 5, sunColor);
		Color[] traceE = new Color[] {Color.RED, null};
		int[] numSteps = {240, 0};
		
		ELLIPTICAL = new Setup(traceE, numSteps, earth, sun);
		
		
		earth = new Planet(new DVector(30, 0, -100), 10, 3, earthColor);
		sun = new Planet(new DVector(0, 0, -100), 100, 5, sunColor);
		traceE = new Color[] {Color.GREEN, null};
		numSteps = new int[] {200, 0};
		PMath.setupCircilarOrbit(earth, sun);
		
		CIRCULAR = new Setup(traceE, numSteps, earth, sun);
		PARABOLIC = null;
		HYPERBOLIC = null;
		POLAR = null;
	}
	
	
	private Body[] bodies;
	
	// which bodies will be traced (if opted to) (null if not traced, otherwise color of path)
	private Color[] trace;
	
	private int[] numSteps;
	
	private Setup(Color[] trace, int[] numSteps, Body...bodies) {
		this.bodies = bodies;
		this.trace = trace;
		this.numSteps = numSteps;
	}
	
	public void initialize(Screen screen, float precision) {
		for (int i = 0; i < bodies.length; i++) {
			screen.add(bodies[i]);
			if (trace[i] != null)
				bodies[i].lineTrace(screen, precision, numSteps[i], trace[i]);
		}
	}
	
	public void showExactSolution(Screen screen, int numSteps, Color color) {
		if (bodies.length < 2)
			System.err.println("Can only show solution of 2-or-more-body system");
		ExactSolution e = new ExactSolution(bodies[0], bodies[1], numSteps, color);
		screen.add(e);
		e.calculatePath();
	}
	
}
