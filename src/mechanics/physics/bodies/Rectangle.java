package mechanics.physics.bodies;

import mechanics.graphics.shapes.Box;
import tensor.DVector;

public class Rectangle extends Body {
	
	public Rectangle(DVector pos, double mass, float l, float w, float h, String texture) {
		super(pos, mass, new Box(l, w, h, texture));
	}
	
	public Rectangle(DVector pos, DVector vi, double mass, float l, float w, float h, String texture) {
		super(pos, vi, mass, new Box(l, w, h, texture));
	}
}
