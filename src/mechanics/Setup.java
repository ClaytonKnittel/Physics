package mechanics;

import graphics.Color;
import mechanics.graphics.Screen;
import mechanics.physics.Body;
import mechanics.physics.ExactSolution;
import mechanics.physics.PMath;
import mechanics.physics.Planet;
import mechanics.physics.SphericalBodies;
import mechanics.utils.Entity;
import tensor.DVector;

public class Setup {
	
	
	public static final Setup ELLIPTICAL, CIRCULAR, PARABOLIC, HYPERBOLIC, POLAR, EARTH_MOON, INNER_SYSTEM;
	
	private static final Color earthColor = new Color(57, 118, 40), sunColor = new Color(253, 184, 19), moonColor = new Color(254, 252, 215),
			mercuryColor = new Color(177, 173, 173), venusColor = new Color(227, 158, 28), marsColor = new Color(193, 68, 14);
	
	static {
		Planet earth = new Planet(new DVector(30, 0, -100), new DVector(0, 0, -68), 10, 3, earthColor);
		Planet sun = new Planet(new DVector(0, 0, -100), new DVector(0, 0, .068f), 10000, 5, sunColor);
		Color[] traceE = new Color[] {Color.red, null};
		int[] numSteps = {240, 0};
		
		ELLIPTICAL = new Setup(traceE, numSteps, earth, sun);
		
		
		earth = new Planet(new DVector(30, 0, -100), 10, 3, earthColor);
		sun = new Planet(new DVector(0, 0, -100), 100, 5, sunColor);
		traceE = new Color[] {Color.green, null};
		numSteps = new int[] {200, 0};
		PMath.setupCircilarOrbit(earth, sun);
		
		CIRCULAR = new Setup(traceE, numSteps, earth, sun);
		
		earth = new Planet(new DVector(30, 0, -100), 10, 3, earthColor);
		sun = new Planet(new DVector(-.3f, 0, -100), 1000, 5, sunColor);
		traceE = new Color[] {Color.green, null};
		numSteps = new int[] {1000, 0};
		PMath.setupParabolicOrbit(earth, sun);
		System.out.println(earth + "\n\n" + sun);
		
		PARABOLIC = new Setup(traceE, numSteps, earth, sun);
		
		earth = new Planet(new DVector(30, 0, -100), new DVector(0, 0, -100), 10, 3, earthColor);
		sun = new Planet(new DVector(0, 0, -100), new DVector(0, 0, .1f), 10000, 5, sunColor);
		traceE = new Color[] {Color.red, null};
		numSteps = new int[] {500, 0};
		
		HYPERBOLIC = new Setup(traceE, numSteps, earth, sun);
		
		
		
		SphericalBodies earthSun = new SphericalBodies(new DVector(30, Math.PI / 2, 0), new DVector(0, .16, -.12),
													   10, 100, 3, 5, earthColor, sunColor);
		traceE = new Color[] {Color.red, null};
		numSteps = new int[] {200, 0};
		
		POLAR = new Setup(traceE, numSteps, earthSun);
		
		
		
		earth = new Planet(new DVector(1000, 0, -100), 10, 1, earthColor);
		sun = new Planet(new DVector(0, 0, -100), 1000, 30, sunColor);
		Planet moon = new Planet(new DVector(1000, 0, -102.56956), .01f, 0.1f, moonColor);
		PMath.setupCircilarOrbit(earth, sun);
		PMath.setupOrbitAroundPlanet(earth, moon, new DVector(1, 1, 1).normalized());
		
		traceE = new Color[] {Color.red, null, Color.gray};
		numSteps = new int[] {200, 0, 200};
		
		EARTH_MOON = new Setup(traceE, numSteps, earth, sun, moon);
		
		
		
		earth = new Planet(new DVector(1000, 0, -100), 10, 1, earthColor);
		sun = new Planet(new DVector(0, 0, -100), 1000, 30, sunColor);
		Planet mercury = new Planet(new DVector(86.7832, 380.222, 0), 0.552735261, 1, mercuryColor);
		Planet venus = new Planet(new DVector(-551.552, -462.807, 0), 8.14997513, 1, venusColor);
		Planet mars = new Planet(new DVector(-1229.71, 893.434, 0), 1.07446849, 1, marsColor);
		PMath.setupCircilarOrbit(mercury, sun);
		PMath.setupCircilarOrbit(venus, sun);
		PMath.setupCircilarOrbit(earth, sun);
		PMath.setupCircilarOrbit(mars, sun);
		
		traceE = new Color[] {null, Color.yellow, Color.orange, Color.cyan, Color.red};
		numSteps = new int[] {0, 300, 300, 300, 300};
		
		INNER_SYSTEM = new Setup(traceE, numSteps, sun, mercury, venus, earth, mars);
	}
	
	
	private Entity[] bodies;
	
	// which bodies will be traced (if opted to) (null if not traced, otherwise color of path)
	private Color[] trace;
	
	private int[] numSteps;
	
	private Setup(Color[] trace, int[] numSteps, Entity...bodies) {
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
		Body a, b;
		if (bodies[0] instanceof SphericalBodies) {
			SphericalBodies sp = (SphericalBodies) bodies[0];
			a = sp.body1();
			b = sp.body2();
		} else {
			a = (Body) bodies[0];
			b = (Body) bodies[1];
		}
		ExactSolution e = new ExactSolution(a, b, numSteps, color);
		screen.add(e);
		e.calculatePath();
	}
	
	public void showExactSolutions(Screen screen, int numSteps, Color[] colors, int sunLoc, int[] exacts) {
		Body sun = (Body) bodies[sunLoc];
		int colorLoc = 0;
		for (int i : exacts) {
			ExactSolution e = new ExactSolution((Body) bodies[i], sun, numSteps, colors[colorLoc++]);
			screen.add(e);
			e.calculatePath();
		}
	}
	
}
