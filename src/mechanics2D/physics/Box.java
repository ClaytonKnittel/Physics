package mechanics2D.physics;

import java.awt.Color;

import mechanics2D.shapes.Rectangle;

public class Box extends Body {
	
	public Box(double x, double y, double vx, double vy, double mass, double length, double width, Color color) {
		super(x, y, vx, vy, mass, new Rectangle(length, width, null, color));
		super.shape().setOwner(this);
	}
	
	public Box(double x, double y, double mass, double length, double width, Color color) {
		this(x, y, 0, 0, mass, length, width, color);
	}
	
}
