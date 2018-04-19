package mechanics.utils;

import graphics.entities.GLFWRenderable;
import mechanics.graphics.Screen;
import mechanics.graphics.shapes.Shape;
import mechanics.physics.utils.Attribute;
import tensor.DVector;

public interface Entity extends GLFWRenderable {
	
	DVector pos();
	
	Shape shape();
	
	/**
	 * Sets up the Entity in whatever ways necessary (does initial calculations, prepares
	 * the Object for physics calculations, etc.)
	 */
	void init();
	
	/**
	 * update this <code>Entity</code>'s physics parameters
	 */
	void physUpdate();
	
	void lineTrace(Screen screen, float precision, int numSteps, String color);
	
	
	void interact(Entity e);
	
	boolean is(Attribute a);
}
