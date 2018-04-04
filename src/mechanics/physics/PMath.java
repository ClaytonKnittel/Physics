package mechanics.physics;

import mechanics.physics.utils.Attribute;
import tensor.Vector;

public final class PMath {
	
	public static final float dt = .001f;
	public static final double Ginv = .1d; //14983338527.5574;
	
	private static long frame = 0;
	
	/**
	 * Called after each physics frame has passed
	 */
	public static void next() {
		frame++;
	}
	
	public static long frame() {
		return frame;
	}
	
	public static Vector interactionForce(Body a, Body b) {
		Vector netAonB = new Vector(0, 0, 0);
		if (b.is(Attribute.Physical))
			netAonB.add(PMath.collide(b, a));
		
		if (a.is(Attribute.Massive) && b.is(Attribute.Massive))
			netAonB.add(PMath.gForce(b, a));
		return netAonB;
	}
	
	/**
	 * @param a <code>Body</code> a in interaction
	 * @param b <code>Body</code> b in interaction
	 * @return the force of a on b
	 */
	public static Vector gForce(Body a, Body b) {
		if (!a.shouldInteract(b))
			return Vector.ZERO;
		Vector dr = a.pos().minus(b.pos());
		float s = (float) (a.mass() * b.mass() / Ginv / dr.mag2());
		dr.normalize();
		return dr.times(s);
	}
	
	/**
	 * Fixates two Bodies together that are touching (after a collision).
	 * This prevents Bodies from continually colliding with one another and causing strange.
	 * <p>
	 * Sums their momenta and assigns each the same velocity so that it is conserved.
	 * @param b the Body that is touching this one
	 */
	public static void fixate(Body a, Body b) {
		Vector p = a.momentum().plus(b.momentum());
		p.div(a.mass() + b.mass());
		a.setVelocity(new Vector(p));
		b.setVelocity(new Vector(p));
	}
	
	
	/**
	 * @param a <code>Body</code> a
	 * @param b <code>Body</code> b
	 * 
	 * Handes the collision of two rigid Bodies.
	 * 
	 */
	public static Vector collide(Body a, Body b) {
		if (!a.shouldCollide(b))
			return Vector.ZERO;
		
		Vector thisToOther = b.pos().minus(a.pos());
		if (!a.shape().colliding(b.shape(), thisToOther))
			return Vector.ZERO;
		CollisionInformation c = a.shape().collisionInformation(b.shape(), thisToOther);
		
		float force = PMath.collisionForce(a, b, c.norm()) / PMath.dt;
		
		a.appendCollision(b);
		return c.norm().times(force);
	}
	
	/**
	 * Calculates the force of a collision between two objects
	 * 
	 * @param b1 Body 1
	 * @param b2 Body 2
	 * @param norm surface normal away from surface 1
	 * @return the magnitude of the force of the collision between the two objects
	 */
	private static float collisionForce(Body b1, Body b2, Vector norm) {
		return 2 * b1.mass() * b2.mass() / (b1.mass() + b2.mass()) * norm.dot(b1.velocity().minus(b2.velocity()));
	}
	
	public static float kineticEnergy(Body b) {
		return b.mass() / 2 * b.velocity().mag2();
	}
	
	public static float potentialEnergy(Body b1, Body b2) {
		return (float) (-b1.mass() * b2.mass() / Ginv / (b1.pos().minus(b2.pos())).mag());
	}
	
}
