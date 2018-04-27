package mechanics2D.physics;

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
		b1.addForce(r);
		b2.addForce(r.times(-1));
	}
	
}
