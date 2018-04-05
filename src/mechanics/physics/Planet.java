package mechanics.physics;

import java.awt.Color;

import mechanics.graphics.Sphere;
import tensor.DVector;

public class Planet extends Body {
	
	public Planet(DVector pos, float mass, float radius, Color color) {
		super(pos, mass, new Sphere(radius), color);
	}
	
	public Planet(DVector pos, DVector vi, float mass, float radius, Color color) {
		super(pos, vi, mass, new Sphere(radius), color);
	}
	
}
