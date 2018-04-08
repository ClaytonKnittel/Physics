package mechanics.physics;

import java.awt.Color;

import mechanics.graphics.Camera;
import mechanics.graphics.ImageGraphics;
import mechanics.graphics.PathTracer;
import mechanics.graphics.Screen;
import mechanics.graphics.math.GMath;
import mechanics.graphics.shapes.Shape;
import mechanics.physics.utils.Attribute;
import mechanics.physics.utils.Attributes;
import mechanics.physics.utils.BodyCollisionList;
import mechanics.utils.Entity;
import numbers.cliffordAlgebras.DQuaternion;
import tensor.DVector;
import tensor.Vector;

public abstract class Body implements Entity {
	
	/**
	 * @serialField pos the position of the center of mass
	 */
	private DQuaternion pos;
	
	/**
	 * @serialField transPos the transformed position, with respect to the screen
	 */
	private Vector transPos;
	
	private DVector velocity;
	
	private DVector netForce;
	
	
	
	private double mass;
	
	/**
	 * @serialField a description of the shape of this Body
	 */
	private Shape shape;
	
	private Color color;
	
	private PathTracer pathTracer;
	
	
	private BodyCollisionList collided;
	
	/**
	 * By default, Bodies are Physical and Massive
	 */
	private Attributes attributes;
	
	public Body(DVector pos, DVector vi, double mass, Shape shape, Color color, Attribute...attributes) {
		this.pos = new DQuaternion(pos);
		this.velocity = vi;
		this.mass = mass;
		this.shape = shape;
		this.color = color;
		this.collided = new BodyCollisionList(this);
		this.attributes = new Attributes(attributes);
		reset();
	}
	
	public Body(DVector pos, DVector vi, double mass, Shape shape, Color color) {
		this(pos, vi, mass, shape, color, Attribute.Physical, Attribute.Massive);
	}
	
	public Body(DVector pos, double mass, Shape shape, Color color) {
		this(pos, new DVector(DVector.ZERO), mass, shape, color);
	}
	
	/**
	 * Calculates v(dt / 2) and sets that as the true initial velocity.
	 */
	public void init() {
		velocity.add(netForce.divide(mass).times(PMath.dt / 2));
		// reset the forces (they were only calculated to find v(dt / 2) and must be reset before the first physics iteration runs
		reset();
	}
	
	public void lineTrace(Screen screen, float precision, int numSteps, Color color) {
		pathTracer = new PathTracer(precision, numSteps, color);
		screen.add(pathTracer);
	}
	
	public boolean tracing() {
		return pathTracer != null;
	}
	
	public DQuaternion pos() {
		return pos;
	}
	
	public DVector velocity() {
		return velocity;
	}
	
	protected void setVelocity(DVector v) {
		this.velocity = v;
	}
	
	protected void addVelocity(DVector v) {
		this.velocity.add(v);
	}
	
	public DVector momentum() {
		return velocity.times(mass);
	}
	
	public double mass() {
		return mass;
	}
	
	public Color color() {
		return color;
	}
	
	public Shape shape() {
		return shape;
	}
	
	public String toString() {
		return "Position: " + (DVector) pos + "\nVelocity: " + velocity + "\nMass: " + mass + "\nColor: " + color;
	}
	
	public boolean is(Attribute a) {
		return attributes.is(a);
	}
	
	public void draw(ImageGraphics g) {
		shape.draw(g, transPos, Vector.Z, 0, color);
	}

	public float distance(Camera c) {
		return Math.abs(transPos.z());
	}
	
	protected void addForce(DVector force) {
		netForce.add(force);
	}
	
	public void update() {
		transPos = GMath.transform(pos.toQuaternion());
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
		reset();
	}
	
	private void reset() {
		netForce = new DVector(DVector.ZERO);
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
	
}
