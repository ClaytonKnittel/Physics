package mechanics.physics;

import graphics.Color;
import mechanics.graphics.shapes.Box;
import tensor.DVector;

public class Rectangle extends Body {
	
	public Rectangle(DVector pos, double mass, float l, float w, float h, Color color) {
		super(pos, mass, new Box(l, w, h), color);
	}
	
	public Rectangle(DVector pos, DVector vi, double mass, float l, float w, float h, Color color) {
		super(pos, vi, mass, new Box(l, w, h), color);
	}
}
