package mechanics.physics.bodies;

import mechanics.graphics.shapes.Sphere;
import tensor.DVector;

public class Planet extends Body {
	
	
	public Planet(DVector pos, double mass, float radius, String texture) {
		super(pos, mass, new Sphere(radius, texture));
	}
	
	public Planet(DVector pos, DVector vi, double mass, float radius, String texture) {
		super(pos, vi, mass, new Sphere(radius, texture));
	}
	
}
