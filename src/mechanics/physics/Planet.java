package mechanics.physics;

import graphics.Color;
import mechanics.graphics.shapes.Sphere;
import tensor.DVector;

public class Planet extends Body {
	
	public Planet(DVector pos, double mass, float radius, Color color) {
		super(pos, mass, new Sphere(radius), color);
	}
	
	public Planet(DVector pos, DVector vi, double mass, float radius, Color color) {
		super(pos, vi, mass, new Sphere(radius), color);
	}
	
}
