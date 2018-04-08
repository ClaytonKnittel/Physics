package mechanics.graphics.shapes;

import java.awt.Color;

import mechanics.graphics.ImageGraphics;
import mechanics.physics.CollisionInformation;
import tensor.DVector;
import tensor.Vector;

public interface Shape {

	/**
	 * @param s The shape to be comparing to this one
	 * @param thisToS the Vector going from this <code>Shape</code>'s center to the other's
	 * @return Information about the collision (relative to this <code>Shape</code>'s center), assuming they are colliding
	 * <br>
	 * The surface normal is the <code>Vector</code> normal to and pointing outward from this <code>Shape</code>'s surface.
	 */
	CollisionInformation collisionInformation(Shape s, DVector thisToOther);
	
	/**
	 * @param s The shape to be comparing to this one
	 * @param thisToS the Vector going from this <code>Shape</code>'s center to the other's
	 * @return whether or not these <code>Shape</code>s are colliding
	 * <br>
	 * The surface normal is the <code>Vector</code> normal to and pointing outward from this <code>Shape</code>'s surface.
	 */
	boolean colliding(Shape s, DVector thisToOther);
	
	/**
	 * Shapes provide a drawing method, in which a few parameters must be passed down by the
	 * Object this belongs to in order to make drawing possible. Shapes hold no information about
	 * their orientation or position in 3-D space, but rather only contain a description of
	 * what the shape looks like.
	 * <p>
	 * All parameters are with respect to the screen, not the origin.
	 * 
	 * @param g a Graphics Object provided by the Screen
	 * @param pos the position of this Object in 3-D space
	 * @param z the direction of the vertical component of this Shape
	 * @param angle the difference in angle from the described drawing of the <code>Shape</code>, according to the right-hand rule
	 * @param c the color of the object
	 */
	void draw(ImageGraphics g, Vector pos, Vector z, float angle, Color c);
	
}
