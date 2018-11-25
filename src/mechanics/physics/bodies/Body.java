package mechanics.physics.bodies;

import mechanics.graphics.PathTracer;
import mechanics.graphics.Screen;
import mechanics.graphics.shapes.Shape;
import mechanics.physics.utils.Attribute;
import mechanics.physics.utils.Attributes;
import mechanics.physics.utils.BodyCollisionList;
import mechanics.utils.Entity;
import numbers.cliffordAlgebras.DQuaternion;
import tensor.DVector;
import tensor.Matrix4;
import tensor.DMatrix;

public abstract class Body implements Entity {
	
	/**
	 * @serialField pos the position of the center of mass
	 */
	private DVector pos;
	
	
	private DVector velocity;
	
	private DVector netForce;
	
	// for graphics
	private Matrix4 model;
	
	
	private double mass;
	
	// angular velocity
	private DVector w;
	
	// orientation of the body. at 0, 0, 0, axes align with space frame axes
	private double phi, theta, psi;
	
	private DVector torque;
	
	/**
	 * @serialField a description of the shape of this Body
	 */
	private Shape shape;
	
	private float reflectivity, shineDamper;
	
	private PathTracer pathTracer;
	
	
	private BodyCollisionList collided;
	
	/**
	 * By default, Bodies are Physical and Massive
	 */
	private Attributes attributes;
	
	public Body(DVector pos, DVector vi, double mass, Shape shape, Attribute...attributes) {
		this.pos = new DQuaternion(pos);
		this.velocity = vi;
		this.mass = mass;
		this.shape = shape;
		this.collided = new BodyCollisionList(this);
		this.attributes = new Attributes(attributes);
		this.model = new Matrix4();
		this.phi = 0;
		this.theta = 0;
		this.psi = 0;
		this.w = new DVector(0, 0, 0);
		reset();
		setLightAttribs(0, 1);
	}
	
	public Body(DVector pos, DVector vi, double mass, Shape shape) {
		this(pos, vi, mass, shape, Attribute.Physical, Attribute.Massive);
	}
	
	public Body(DVector pos, double mass, Shape shape) {
		this(pos, new DVector(DVector.ZERO), mass, shape);
	}
	
	/**
	 * Calculates v(dt / 2) and sets that as the true initial velocity.
	 */
	public void init() {
		velocity.add(netForce.divide(mass).times(PMath.dt / 2));
		// reset the forces (they were only calculated to find v(dt / 2) and must be reset before the first physics iteration runs
		reset();
	}
	
	public void setLightAttribs(float reflectivity, float shineDamper) {
		this.reflectivity = reflectivity;
		this.shineDamper = shineDamper;
	}
	
	@Override
	public float reflectivity() {
		return reflectivity;
	}
	
	@Override
	public float shineDamper() {
		return shineDamper;
	}
	
	public void setAngularVelocity(DVector d) {
		this.w = d;
	}
	
	public void setPos(DVector pos) {
		this.pos = pos;
	}
	
	public void setAngles(double phi, double theta, double psi) {
		this.phi = phi;
		this.theta = theta;
		this.psi = psi;
	}
	
	@Override
	public String texture() {
		return shape.texture();
	}
	
	@Override
	public Matrix4 model() {
		return model;
	}
	
	@Override
	public float[] modelData() {
		return shape.modelData();
	}
	
	public void lineTrace(Screen screen, float precision, int numSteps, String color) {
		pathTracer = new PathTracer(precision, numSteps, color);
		screen.add(pathTracer);
	}
	
	public boolean tracing() {
		return pathTracer != null;
	}
	
	public DVector pos() {
		return pos;
	}
	
	public DVector velocity() {
		return velocity;
	}
	
	public double phi() {
		return phi;
	}
	
	public double theta() {
		return theta;
	}
	
	public double psi() {
		return psi;
	}
	
	protected void setVelocity(DVector v) {
		this.velocity = v;
	}
	
	public void addVelocity(DVector v) {
		this.velocity.add(v);
	}
	
	public DVector momentum() {
		return velocity.times(mass);
	}
	
	public double mass() {
		return mass;
	}
	
	public DVector angularVelocity() {
		return w;
	}
	
	public DVector angularMomentum() {
		return DMatrix.toSpaceFrame(phi, theta, psi).multiply(new DVector(shape.l1(), shape.l2(), shape.l3()).prod(DMatrix.toRotatingFrame(phi, theta, psi).multiply(this.w)));
	}
	
	public Shape shape() {
		return shape;
	}
	
	public String toString() {
		return "Position: " + (DVector) pos + "\nVelocity: " + velocity + "\nAngles: phi: " + phi + " \tTheta: " + theta + " \tPsi: " + psi + "\nW: " + w + "\nMass: " + mass;
	}
	
	public boolean is(Attribute a) {
		return attributes.is(a);
	}
	
	protected void addForce(DVector force) {
		netForce.add(force);
	}
	
	public void update() {
		model = Matrix4.model(pos.toVector(), (float) phi, (float) theta, (float) psi).multiply(shape.model());
		if (tracing())
			pathTracer.recordLocation(pos.toVector());
	}
	
	public void physUpdate() {
		// go through all the recently collided Bodies and
		// see if any have expired their waiting time
		collided.update();
		// do the physics calculations for this Body
		step();
	}
	
	private void step() {
		velocity.add(netForce.divide(mass).times(PMath.dt));
		pos.add(velocity.times(PMath.dt));
		angularStep();
		reset();
	}
	
	private void angularStep() {
		double l1 = mass * shape.l1();
		double l2 = mass * shape.l2();
		double l3 = mass * shape.l3();
		w.add(PMath.dW(phi, theta, psi, l1, l2, l3, w, torque));
		DVector dAngles = PMath.dAngles(phi, theta, psi, w);
		phi += dAngles.x();
		theta += dAngles.y();
		psi += dAngles.z();
		normalizeAngles();
	}
	
	private void normalizeAngles() {
		phi = mod2Pi(phi);
		theta = mod2Pi(theta);
		psi = mod2Pi(psi);
	}
	
	private double mod2Pi(double d) {
		if (d < 0)
			return mod2Pi(d + Math.PI * 2);
		if (d >= Math.PI * 2)
			return mod2Pi(d - Math.PI * 2);
		return d;
	}
	
	private void reset() {
		netForce = new DVector(DVector.ZERO);
		torque = new DVector(DVector.ZERO);
	}
	
	/**
	 * handles interaction between this body and another <code>Entity</code>. Will account for the affect of itself on the <code>Entity</code> and
	 * vice versa.
	 * @param e the entity this <code>Body</code> is interacting with
	 */
	public void interact(Entity e) {
		// only interacts with other physics bodies
		if (!Body.class.isAssignableFrom(e.getClass()))
			return;
		Body b = (Body) e;
		DVector net = PMath.interactionForce(this, b);
		this.addForce(net);
		b.addForce(net.times(-1));
	}
	
	public void appendCollision(Body b) {
		collided.add(b);
	}
	
	public boolean shouldCollide(Body b) {
		return collided.shouldCollide(b);
	}
	
	public boolean shouldInteract(Body b) {
		return collided.shouldInteract(b);
	}
	
	public boolean colliding(Body b) {
		return shape.colliding(b.shape, b.pos.minus(pos));
	}
	
	public void setAttribute(Attribute a) {
		attributes.set(a, true);
	}
	
	public void unsetAttribute(Attribute a) {
		attributes.set(a, false);
	}
	
}
