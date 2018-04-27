package mechanics2D.shapes;

import java.awt.Graphics;

import tensor.Vector2;

public interface Shape {
	
	void draw(Graphics g);
	
	void setOwner(Orientable owner);
	
	/**
	 * @return the moment of inertia of this object, excluding the mass factor
	 */
	double moment();
	
}
