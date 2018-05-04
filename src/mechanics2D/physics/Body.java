package mechanics2D.physics;

import mechanics2D.graphics.Drawable;
import mechanics2D.shapes.CollisionInformation;
import mechanics2D.shapes.Orientable;
import mechanics2D.shapes.Shape;
import tensor.DVector2;

import static mechanics2D.shapes.AbstractShape.popCollisionInfo;

import java.util.LinkedList;

import static mechanics2D.shapes.AbstractShape.areCollisions;

public abstract class Body implements Drawable, Orientable {
	
	private DVector2 pos, vel;
	
	// angle + angular velocity + moment of inertia
	private double phi, w, I;
	
	private double mass;
	
	private LinkedList<Force> netForce, netImpulse;
	
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
		netForce = new LinkedList<>();
		netImpulse = new LinkedList<>();
		resetForces();
	}
	
	public void interact(Body b) {
		PMath.gForce(this, b);
		if (b.shape().colliding(shape(), true)) {
			while (areCollisions()) {
				CollisionInformation c = popCollisionInfo();
				PMath.collisionForce(this, b, c);
			}
		}
	}
	
	@Override
	public DVector2 pos() {
		return pos;
	}
	
	public DVector2 vel() {
		return vel;
	}
	
	@Override
	public void move(DVector2 move) {
		pos.add(move);
	}
	
	@Override
	public double angle() {
		return phi;
	}
	
	public double w() {
		return w;
	}
	
	public void setW(double w) {
		this.w = w;
	}
	
	@Override
	public void rotate(double angle) {
		phi += angle;
	}
	
	public double moment() {
		return I;
	}
	
	@Override
	public Shape shape() {
		return shape;
	}
	
	public double mass() {
		return mass;
	}
	
	public void addForce(Force force) {
		netForce.add(force);
	}
	
	public void addImpulse(Force force) {
		netImpulse.add(force);
	}
	
	private void resetForces() {
		netForce.clear();
		netImpulse.clear();
	}
	
	public void update() {
		for (Force f : netForce) {
			vel.add(f.force().times(PMath.dt / mass));
			
			w += f.torque() * PMath.dt / I;
		}
		for (Force f : netImpulse) {
			vel.add(f.force().divide(mass));
			
			w += f.torque() / I;
		}
		pos.add(vel.times(PMath.dt));
		phi += w * PMath.dt;
		resetForces();
	}
	
	public double energy() {
		return mass * vel.mag2() / 2 + I * w * w / 2;
	}
	
}
