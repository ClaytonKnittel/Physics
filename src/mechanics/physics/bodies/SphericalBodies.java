package mechanics.physics.bodies;

import mechanics.graphics.PathTracer;
import mechanics.graphics.Screen;
import mechanics.graphics.shapes.Shape;
import mechanics.graphics.shapes.Sphere;
import mechanics.physics.utils.Attribute;
import mechanics.utils.Entity;
import numbers.cliffordAlgebras.DQuaternion;
import numbers.cliffordAlgebras.Quaternion;
import tensor.DVector;
import tensor.Matrix4;

public class SphericalBodies implements Entity {
	
	/**
	 * Vectors holding the radius, polar and azimuthal angle of each planet
	 * <p>
	 * Of the form (r, theta, phi)
	 */
	private DQuaternion b1, b2;
	private DVector v1, v2;
	
	private Shape s1, s2;
	private float reflectivity, shineDamper;
	
	private double m1, m2;
	
	private double l1, l2;
	
	// traces body 1
	private PathTracer pathTracer;
	
	public SphericalBodies(DVector p1, DVector v1, double m1, double m2, float r1, float r2, String t1, String t2) {
		this.b1 = new DQuaternion(p1);
		this.b2 = new DQuaternion(0, p1.x() * m1 / m2, Math.PI - p1.y(), p1.z() + Math.PI);
		this.v1 = v1;
		this.v2 = new DVector(v1.x() * m1 / m2, v1.y(), v1.z());
		this.s1 = new Sphere(r1, t1);
		this.s2 = new Sphere(r2, t2);
		this.m1 = m1;
		this.m2 = m2;
		init();
	}
	
	@Override
	public String texture() {
		return "red";
	}
	
	@Override
	public float[] modelData() {
		return null;
	}
	
	@Override
	public float reflectivity() {
		return reflectivity;
	}
	
	@Override
	public float shineDamper() {
		return shineDamper;
	}
	
	@Override
	public void setLightAttribs(float reflectivity, float shineDamper) {
		this.reflectivity = reflectivity;
		this.shineDamper = shineDamper;
	}
	
	public DVector p1() {
		return b1;
	}
	
	public DVector p2() {
		return b2;
	}
	
	public double r1() {
		return b1.x();
	}
	
	public double r2() {
		return b2.x();
	}
	
	public double theta1() {
		return b1.y();
	}
	
	public double theta2() {
		return b2.y();
	}
	
	public double phi1() {
		return b1.z();
	}
	
	public double phi2() {
		return b2.z();
	}
	
	public double dr1() {
		return v1.x();
	}
	
	public double dr2() {
		return v2.x();
	}
	
	public double dtheta1() {
		return v1.y();
	}
	
	public double dtheta2() {
		return v2.y();
	}
	
	public double dphi1() {
		return v1.z();
	}
	
	public double dphi2() {
		return v2.z();
	}
	
	public double m1() {
		return m1;
	}

	public double m2() {
		return m2;
	}
	
	public double l1() {
		return l1;
	}
	
	public double l2() {
		return l2;
	}
	
	public double ra1() {
		return r1() * (PMath.square(dtheta1()) + PMath.square(dphi1() * Math.sin(theta1())));
	}
	
	public double ra2() {
		return r2() * (PMath.square(dtheta2()) + PMath.square(dphi2() * Math.sin(theta2())));
	}
	
	public double thetaa1() {
		return PMath.square(dphi1()) * Math.sin(theta1()) * Math.cos(theta1()) - 2 * dr1() * dtheta1() / r1();
	}
	
	public double thetaa2() {
		return PMath.square(dphi2()) * Math.sin(theta2()) * Math.cos(theta2()) - 2 * dr2() * dtheta2() / r2();
	}
	
	//		double dphi1 = b.l1() / (b.m1() * square(b.r1() * Math.sin(b.theta1())));
	
	
	//Cos[phi[t]] Sin[theta[t]] Derivative[1][r][t] + 
	// r[t] (-Sin[phi[t]] Sin[theta[t]] Derivative[1][phi][t] + 
	//		    Cos[phi[t]] Cos[theta[t]] Derivative[1][theta][t])
	
	/*
	 * Sin[phi[t]] Sin[theta[t]] Derivative[1][r][t] + 
 r[t] (Cos[phi[t]] Sin[theta[t]] Derivative[1][phi][t] + 
    Cos[theta[t]] Sin[phi[t]] Derivative[1][theta][t])
    
    Cos[theta[t]] Derivative[1][r][t] - 
 r[t] Sin[theta[t]] Derivative[1][theta][t]
	 */
	
	public Body body1() {
		return new Planet(new DQuaternion(cartesian1()), polarToCartesianVelocity1(), (float) m1, 1, s1.texture());
	}
	
	public Body body2() {
		return new Planet(new DQuaternion(cartesian2()), polarToCartesianVelocity2(), (float) m2, 1, s2.texture());
	}
	
	private DVector polarToCartesianVelocity1() {
		double dx = Math.cos(phi1()) * Math.sin(theta1()) * dr1() + r1() * (-Math.sin(phi1()) * Math.sin(theta1()) * dphi1() + Math.cos(phi1()) * Math.cos(theta1()) * dtheta1());
		double dz = -Math.sin(phi1()) * Math.sin(theta1()) * dr1() - r1() * (Math.cos(phi1()) * Math.sin(theta1()) * dphi1() + Math.sin(phi1()) * Math.cos(theta1()) * dtheta1());
		double dy = Math.cos(theta1()) * dr1() - r1() * Math.sin(theta1()) * dtheta1();
		return new DVector(dx, dy, dz);
	}
	
	private DVector polarToCartesianVelocity2() {
		double dx = Math.cos(phi2()) * Math.sin(theta2()) * dr2() + r2() * (-Math.sin(phi2()) * Math.sin(theta2()) * dphi2() + Math.cos(phi2()) * Math.cos(theta2()) * dtheta2());
		double dz = -Math.sin(phi2()) * Math.sin(theta2()) * dr2() - r2() * (Math.cos(phi2()) * Math.sin(theta2()) * dphi2() + Math.sin(phi2()) * Math.cos(theta2()) * dtheta2());
		double dy = Math.cos(theta2()) * dr2() - r2() * Math.sin(theta2()) * dtheta2();
		return new DVector(dx, dy, dz);
	}
	
	
	public void lineTrace(Screen screen, float precision, int numSteps, String color) {
		pathTracer = new PathTracer(precision, numSteps, color);
		screen.add(pathTracer);
	}
	
	public boolean tracing() {
		return pathTracer != null;
	}
	
	/**
	 * Used only for graphics purposes
	 * @return b1 in cartesian coordinates
	 */
	public Quaternion cartesian1() {
		return toCartesian(b1);
	}
	
	/**
	 * Used only for graphics purposes
	 * @return b2 in cartesian coordinates
	 */
	public Quaternion cartesian2() {
		return toCartesian(b2);
	}
	
	/**
	 * Transforms spherical coordinates to cartesian, placing (r=1, theta=0, phi=0) parallel to the y-axis (up)
	 * 
	 * @param v Spherical DVector (r, theta, phi) to be transformed to cartesian
	 * @return v in cartesian coordinates
	 */
	private static Quaternion toCartesian(DVector v) {
		return new Quaternion(0, (float) (v.x() * Math.sin(v.y()) * Math.cos(v.z())), (float) (v.x() * Math.cos(v.y())), -(float) (v.x() * Math.sin(v.y()) * Math.sin(v.z())));
	}

	@Override
	public void update() {
		if (tracing())
			pathTracer.recordLocation(cartesian1());
	}
	
	@Override
	public Matrix4 model() {
		return new Matrix4();
	}

	public void init() {
		l1 = m1() * dphi1() * PMath.square(r1() * Math.sin(theta1()));
		l2 = m2() * dphi2() * PMath.square(r2() * Math.sin(theta2()));
		
		DVector[] accels = PMath.gForceSpherical(this);
		v1.add(accels[0].divide(m1).times(PMath.dt / 2));
		v2.add(accels[1].divide(m2).times(PMath.dt / 2));
	}

	@Override
	public void physUpdate() {
		DVector[] accels = PMath.gForceSpherical(this);
		v1.add(accels[0].times(PMath.dt));
		v2.add(accels[1].times(PMath.dt));
		
		updatedPhi();
		
		b1.add(v1.times(PMath.dt));
		b2.add(v2.times(PMath.dt));
	}
	
	private void updatedPhi() {
		v1.add(new DVector(0, 0, l1 / (m1 * PMath.square(r1() * Math.sin(theta1()))) - v1.z()));
		v2.add(new DVector(0, 0, l2 / (m2 * PMath.square(r2() * Math.sin(theta2()))) - v2.z()));
	}
	
	@Override
	public void interact(Entity e) {
		return;
	}

	@Override
	public boolean is(Attribute a) {
		return false;
	}

	@Override
	public DQuaternion pos() {
		return new DQuaternion(Math.sin(theta1()) * r1(), r1(), Math.toDegrees(theta1()), Math.toDegrees(phi1()));
	}

	@Override
	public Shape shape() {
		return null;
	}
	
	
	
}
