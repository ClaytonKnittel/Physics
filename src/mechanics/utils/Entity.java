package mechanics.utils;

import java.awt.Color;

import mechanics.graphics.Screen;
import mechanics.physics.utils.Attribute;
import numbers.cliffordAlgebras.DQuaternion;

public interface Entity extends Drawable {
	
	DQuaternion pos();
	
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
	
	void lineTrace(Screen screen, float precision, int numSteps, Color color);
	
	
	void interact(Entity e);
	
	boolean is(Attribute a);
}
