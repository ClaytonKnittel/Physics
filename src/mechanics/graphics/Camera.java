package mechanics.graphics;

import graphics.input.Locatable;
import mechanics.graphics.math.GMath;
import numbers.cliffordAlgebras.Quaternion;
import tensor.Vector;

public class Camera extends Vector implements Locatable {
	
	private float phi, theta, psi;
	
	private boolean boost;
	private long lastBoost;
	private final long wait = 400;
	
	// in spatial units / second
	public static float upV, forwardV, backwardV, sidewaysV, turnV, factor;
	
	static {
		upV = 33;
		forwardV = 50;
		backwardV = 40;
		sidewaysV = 34;
		turnV = .4f;
		factor = 30;
	}
	
	public Camera(Vector pos, float phi, float theta, float psi) {
		super(pos);
		this.phi = phi;
		this.theta = theta;
		this.psi = psi;
		this.boost = false;
		this.lastBoost = 0;
	}
	
	public Camera(Vector pos) {
		this(pos, 0, 0, 0);
	}
	
	@Override
	public Vector pos() {
		return this;
	}
	
	@Override
	public float phi() {
		return phi;
	}
	
	@Override
	public float theta() {
		return theta;
	}
	
	@Override
	public float psi() {
		return psi;
	}
	
	public void rotate(float vPhi, float vTheta, float vPsi) {
		phi += vPhi * GMath.dt;
		theta += vTheta * GMath.dt;
		psi += vPsi * GMath.dt;
	}
	
	public void updward(float velocity) {
		if (boost)
			velocity *= factor;
		this.add(new Vector(0, velocity * GMath.dt, 0));
	}
	
	public void forward(float velocity) {
		if (boost)
			velocity *= factor;
		this.add(new Vector((float) -Math.sin(phi), 0, (float) -Math.cos(phi)).times(velocity * GMath.dt));
	}
	
	/**
	 * @param velocity the velocity at which to travel in the rightward direction
	 */
	public void sideways(float velocity) {
		if (boost)
			velocity *= factor;
		this.add(new Vector((float) Math.cos(phi), 0, (float) -Math.sin(phi)).times(velocity * GMath.dt));
	}
	
	public void flipSpeed() {
		if (lastBoost < System.currentTimeMillis()) {
			boost = !boost;
			this.lastBoost = System.currentTimeMillis() + wait;
		}
	}
	
	public Quaternion getTransformation() {
		return Quaternion.euler(Vector.X, theta).times(Quaternion.euler(Vector.Y, -phi));
	}
	
	public String toString() {
		return super.toString() + "  \tphi: " + phi + "  \ttheta: " + theta + "  \tpsi: " + psi;
	}

}
