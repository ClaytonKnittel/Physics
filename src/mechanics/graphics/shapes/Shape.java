package mechanics.graphics.shapes;

import mechanics.physics.CollisionInformation;
import tensor.DVector;
import tensor.Matrix4;

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
	 * @return the model data for this shape, in compliance with whatever rendering method is being used
	 */
	float[] modelData();
	
	/**
	 * @return themodel matrix. Will be the first operator to be performed on the data to transform it into the right shape / size
	 */
	Matrix4 model();
	
	/**
	 * Moment of inertia factor, excluding the mass factor
	 * @return moment of inertia about x- (principal) axis
	 */
	double l1();
	/**
	 * Moment of inertia factor, excluding the mass factor
	 * @return moment of inertia about y- (principal) axis
	 */
	double l2();
	/**
	 * Moment of inertia factor, excluding the mass factor
	 * @return moment of inertia about z- (principal) axis
	 */
	double l3();
	
}
