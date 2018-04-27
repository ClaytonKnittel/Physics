package mechanics2D.physics;

import mechanics2D.graphics.Drawable;
import mechanics2D.shapes.CollisionInformation;
import mechanics2D.shapes.Orientable;
import mechanics2D.shapes.Shape;
import tensor.DVector2;

public abstract class Body implements Drawable, Orientable {
	
	private DVector2 pos, vel;
	
	// angle + angular velocity + moment of inertia
	private double phi, w, I;
	
	private double mass;
	
	private DVector2 netForce;
	private double netTorque;
	
	private Shape shape;
	
	public Body(double x, double y, double vx, double vy, double mass, Shape shape) {
		pos = new DVector2(x, y);
		vel = new DVector2(vx, vy);
		phi = 0;
		w = 0;
		this.mass = mass;
		this.shape = shape;
		I = shape.moment() * mass;
		shape.setOwner(this);
		resetForces();
	}
	
	public void interact(Body b) {
		PMath.gForce(this, b);
		if (b.shape().colliding(shape())) {
			CollisionInformation c = b.shape().getCollisionInfo(shape());
			
		}
	}
	
	@Override
	public DVector2 pos() {
		return pos;
	}
	
	@Override
	public void move(DVector2 move) {
		pos.add(move);
	}
	
	@Override
	public double angle() {
		return phi;
	}
	
	@Override
	public void rotate(double angle) {
		phi += angle;
	}
	
	@Override
	public Shape shape() {
		return shape;
	}
	
	public double mass() {
		return mass;
	}
	
	public void addForce(DVector2 force) {
		netForce.add(force);
	}
	
	private void resetForces() {
		netForce = new DVector2(0, 0);
		netTorque = 0;
	}
	
	public void update() {
		vel.add(netForce.times(PMath.dt / mass));
		pos.add(vel.times(PMath.dt));
		
		w += netTorque * PMath.dt / I;
		phi += w * PMath.dt;
		
		resetForces();
	}
	
}
