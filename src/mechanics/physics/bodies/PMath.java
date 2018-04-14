package mechanics.physics.bodies;

import mechanics.physics.CollisionInformation;
import mechanics.physics.utils.Attribute;
import tensor.DVector;

public final class PMath {
	
	public static final double dt = 0.0009765625;
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
	
	
	public static DVector[] gForceSpherical(SphericalBodies b) {
		double mu = b.m1() * b.m2() / (b.m1() + b.m2());
		
		double m = b.m1() * (b.m1() + b.m2()) / b.m2();
		double f1 = -mu * b.m2() / (Ginv * square(b.r1()));
		double dr1 = b.ra1() + f1 / m;
		double dtheta1 = b.thetaa1();
		
		m = b.m2() * (b.m1() + b.m2()) / b.m1();
		double f2 = -mu * b.m1() / (Ginv * square(b.r2()));
		double dr2 = b.ra2() + f2 / m;
		double dtheta2 = b.thetaa2();
		
//		System.out.println(dr1 + "\t" + dtheta1 + "\t" + dphi1);
//		System.out.println(dr2 + "\t" + dtheta2 + "\t" + dphi2);
//		System.out.println();
				
		return new DVector[] {new DVector(dr1, dtheta1, 0), new DVector(dr2, dtheta2, 0)};
	}
	
	public static double square(double d) {
		return d * d;
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
	 * Handles the collision of two rigid Bodies.
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
	
	
	public static void setupOrbitAroundPlanet(Body planet, Body satellite) {
		setupOrbitAroundPlanet(planet, satellite, DVector.Z);
	}
	
	public static void setupOrbitAroundPlanet(Body planet, Body satellite, DVector norm) {
		DVector r = satellite.pos().minus(centerOfMass(planet, satellite));
		
		// p will be direction of motion of b1
		DVector p = getNormalizedPerpendicular(r, norm);
		double factor = 1 / Math.sqrt(PMath.Ginv * r.mag() * (planet.mass() + satellite.mass()));
		satellite.addVelocity(p.times(planet.mass() * factor).plus(planet.velocity()));
	}
	
	public static void setupCircilarOrbit(Body b1, Body b2) {
		setupCircularOrbit(b1, b2, DVector.Z);
	}
	
	public static void setupCircularOrbit(Body b1, Body b2, DVector norm) {
		DVector r = b1.pos().minus(centerOfMass(b1, b2));
		
		// p will be direction of motion of b1
		DVector p = getNormalizedPerpendicular(r, norm);
		
		double factor = 1 / Math.sqrt(PMath.Ginv * r.mag() * (b1.mass() + b2.mass()));
		b1.addVelocity(p.times(b2.mass() * factor));
		b2.addVelocity(p.times(-b1.mass() * factor));
	}
	
	public static void setupParabolicOrbit(Body b1, Body b2) {
		setupParabolicOrbit(b1, b2, DVector.Z);
	}
	
	public static void setupParabolicOrbit(Body b1, Body b2, DVector norm) {
		DVector r = b1.pos().minus(centerOfMass(b1, b2));
		DVector p = getNormalizedPerpendicular(r, norm);
		
		double factor = Math.sqrt(2 * b2.mass() / (Ginv * r.mag())) / (b1.mass() + b2.mass());
		b1.addVelocity(p.times(b2.mass() * factor));
		b2.addVelocity(p.times(-b1.mass() * factor));
	}
	
	public static DVector centerOfMass(Body b1, Body b2) {
		return b1.pos().times(b1.mass()).plus(b2.pos().times(b2.mass())).divide(b1.mass() + b2.mass());
	}
	
	private static DVector getNormalizedPerpendicular(DVector r, DVector norm) {
		DVector p = r.cross(norm);
		if (p.mag2() < .0001)
			p = r.cross(DVector.X);
		return p.normalized();
	}
	
	
	
	public static double dTheta(double phi, double theta, double psi, double l1, double l2, double l3, double L) {
		return (sq(Math.sin(psi)) / l2 + sq(Math.cos(psi)) / l1) * L;
	}
	
	public static double dPhi(double phi, double theta, double psi, double l1, double l2, double l3, double L) {
		return (1 / l2 - 1 / l1) * L * Math.sin(theta) * Math.sin(psi) / 2;
	}
	
	public static double dPsi(double phi, double theta, double psi, double l1, double l2, double l3, double L) {
		return (1 / l3 - sq(Math.sin(psi)) / l2 - sq(Math.cos(psi)) / l1) * L * Math.cos(theta);
	}
	
	private static double sq(double d) {
		return d * d;
	}
	
}