package mechanics.utils;

import mechanics.physics.utils.Attribute;
import numbers.cliffordAlgebras.Quaternion;

public interface Entity extends Drawable {
	
	Quaternion pos();
	
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
	
	
	void interact(Entity e);
	
	boolean is(Attribute a);
}
