package mechanics2D.graphics;

import java.awt.Graphics;

import mechanics2D.shapes.Shape;

public interface Drawable {
	
	default void draw(Graphics g) {
		shape().draw(g);
	}
	
	Shape shape();
	
}
