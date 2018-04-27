package mechanics2D.shapes;

import java.awt.Graphics;

public interface Shape {
	
	void draw(Graphics g);
	
	void setOwner(Orientable owner);
	
	boolean colliding(Shape s);
	
	CollisionInformation getCollisionInfo(Shape s);
	
	/**
	 * @return the moment of inertia of this object, excluding the mass factor
	 */
	double moment();
	
}
