package mechanics.physics;

import java.awt.Color;

import mechanics.graphics.Sphere;
import tensor.Vector;

public class Planet extends Body {
	
	public Planet(Vector pos, float mass, float radius, Color color) {
		super(pos, mass, new Sphere(radius), color);
	}
	
	public Planet(Vector pos, Vector vi, float mass, float radius, Color color) {
		super(pos, vi, mass, new Sphere(radius), color);
	}
	
}
