package mechanics2D.shapes;

import mechanics2D.graphics.Drawable;

public interface Shape extends Drawable {
	
	void setOwner(Orientable owner);
	
	boolean colliding(Shape s, boolean computeCollisionInfo);
	
	/**
	 * @return the moment of inertia of this object, excluding the mass factor
	 */
	double moment();
	
}
