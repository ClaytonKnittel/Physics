package mechanics2D.physics;

import mechanics2D.shapes.CollisionInformation;
import tensor.DVector2;

public class PMath {
	
	public static double dt = 0.0009765625;
	
	public static double G = 100000;
	
	/**
	 * 
	 * @param b1 Body 1
	 * @param b2 Body 2
	 * @return force of b2 on b1 due to gravity
	 */
	public static void gForce(Body b1, Body b2) {
		DVector2 r = b2.pos().minus(b1.pos());
		double d = r.mag2();
		r.normalize();
		r.scale(G * b1.mass() * b2.mass() / d);
		Force f = new Force(r);
		b1.addForce(f);
		b2.addForce(f.opposite());
	}
	
	public static double gPotential(Body b1, Body b2) {
		double d = b2.pos().minus(b1.pos()).mag();
		return -G * b1.mass() * b2.mass() / d;
	}
	
	public static void collisionForce(Body b1, Body b2, CollisionInformation c) {
		double e = 1;
		
		DVector2 r1 = c.loc().minus(b1.pos());
		DVector2 r2 = c.loc().minus(b2.pos());
		
		double p1 = r1.cross(c.dir());
		double p2 = r2.cross(c.dir());
		
		System.out.println("FE");
		double f = (1 + e) * (b1.vel().minus(b2.vel()).dot(c.dir()) + b1.w() * p1 + b2.w() * p2) / (1 / b1.mass() + 1 / b2.mass() + square(p1) / b1.moment() + square(p2) / b2.moment());
		
//		double num = b1.vel().minus(b2.vel()).plus(b1.pos().crossPerpendicular(b1.angle())).minus(b2.pos().crossPerpendicular(b2.angle())).dot(c.dir());
//		double denom = 1 / b1.mass() + 1 / b2.mass() + square(r1.cross(c.dir())) / b1.moment() + square(r2.cross(c.dir())) / b2.moment();
//		
//		double f = (1 + e) * num / denom;
		
		Force f1 = new Force(r1, c.dir().times(-f));
		Force f2 = new Force(r2, c.dir().times(f));
		b1.addImpulse(f1);
		b2.addImpulse(f2);
	}
	
	public static double square(double d) {
		return d * d;
	}
	
}
