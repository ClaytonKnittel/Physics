package mechanics.physics;

import mechanics.physics.utils.Attribute;
import tensor.DVector;

public final class PMath {
	
	public static final double dt = .001;
	public static final double Ginv = .1; //14983338527.5574;
	
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
	
	public static DVector interactionForce(Body a, Body b) {
		DVector netAonB = new DVector(0, 0, 0);
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
	public static DVector gForce(Body a, Body b) {
		if (!a.shouldInteract(b))
			return DVector.ZERO;
		DVector dr = a.pos().minus(b.pos());
		double s = (a.mass() * b.mass() / Ginv / dr.mag2());
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
		DVector p = a.momentum().plus(b.momentum());
		p.div(a.mass() + b.mass());
		a.setVelocity(new DVector(p));
		b.setVelocity(new DVector(p));
	}
	
	
	/**
	 * @param a <code>Body</code> a
	 * @param b <code>Body</code> b
	 * 
	 * Handes the collision of two rigid Bodies.
	 * 
	 */
	public static DVector collide(Body a, Body b) {
		if (!a.shouldCollide(b))
			return DVector.ZERO;
		
		DVector thisToOther = b.pos().minus(a.pos());
		if (!a.shape().colliding(b.shape(), thisToOther))
			return DVector.ZERO;
		CollisionInformation c = a.shape().collisionInformation(b.shape(), thisToOther);
		
		double force = PMath.collisionForce(a, b, c.norm()) / PMath.dt;
		
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
	private static double collisionForce(Body b1, Body b2, DVector norm) {
		return 2 * b1.mass() * b2.mass() / (b1.mass() + b2.mass()) * norm.dot(b1.velocity().minus(b2.velocity()));
	}
	
	public static double kineticEnergy(Body b) {
		return b.mass() / 2 * b.velocity().mag2();
	}
	
	public static double potentialEnergy(Body b1, Body b2) {
		return -b1.mass() * b2.mass() / Ginv / (b1.pos().minus(b2.pos())).mag();
	}
	
	
	public static void setupCircilarOrbit(Body b1, Body b2) {
		DVector r = b2.pos().minus(b1.pos());
		DVector p;
		
		// p will be direction of motion of b1
		p = r.cross(DVector.Y);
		if (p.mag2() < .0001)
			p = r.cross(DVector.X);
		p.normalize();
		double factor = 1 / Math.sqrt(PMath.Ginv * r.mag() * (b1.mass() + b2.mass()));
		double v1i = b2.mass() * factor;
		double v2i = b1.mass() * factor;
		b1.setVelocity(p.times(v1i));
		b2.setVelocity(p.times(-v2i));
	}
	
}
