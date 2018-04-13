package mechanics.graphics;

import graphics.input.Locatable;
import tensor.Matrix4;
import tensor.Vector;

public class Camera extends Vector implements Locatable {
	
	private float phi, theta, psi;
	
	private Vector v;
	private float dPhi, dTheta, dPsi;
	
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
		turnV = 2f;
		factor = 30;
	}
	
	public Camera(Vector pos, float phi, float theta, float psi) {
		super(pos);
		this.phi = phi;
		this.theta = theta;
		this.psi = psi;
		this.boost = false;
		this.lastBoost = 0;
		freeze();
	}
	
	public Camera(Vector pos) {
		this(pos, 0, 0, 0);
	}
	
	private void freeze() {
		this.v = new Vector(0, 0, 0);
		this.dPhi = 0;
		this.dTheta = 0;
		this.dPsi = 0;
	}
	
	@Override
	public void update() {
		Vector v;
		if (boost)
			v = this.v.times(factor);
		else
			v = this.v;
		this.add(Matrix4.phiRotate(-phi).multiply(v).times(Screen.dt));
		this.rotate(dPhi * Screen.dt, dTheta * Screen.dt, dPsi * Screen.dt);
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
	
	public void setDPhi(float dPhi) {
		this.dPhi = dPhi;
	}
	
	public void setDTheta(float dTheta) {
		this.dTheta = dTheta;
	}
	
	public void setDX(float dx) {
		v.x(dx);
	}
	
	public void setDY(float dy) {
		v.y(dy);
	}
	
	public void setDZ(float dz) {
		v.z(dz);
	}
	
	public void setDPsi(float dPsi) {
		this.dPsi = dPsi;
	}
	
	public void toggleBoost() {
		boost = !boost;
	}
	
	public void rotate(float dPhi, float dTheta, float dPsi) {
		phi += dPhi;
		theta += dTheta;
		psi += dPsi;
	}
	
	public void updward(float velocity) {
		if (boost)
			velocity *= factor;
		this.add(new Vector(0, velocity * Screen.dt, 0));
	}
	
	public void forward(float velocity) {
		if (boost)
			velocity *= factor;
		this.add(new Vector((float) -Math.sin(phi), 0, (float) -Math.cos(phi)).times(velocity * Screen.dt));
	}
	
	/**
	 * @param velocity the velocity at which to travel in the rightward direction
	 */
	public void sideways(float velocity) {
		if (boost)
			velocity *= factor;
		this.add(new Vector((float) Math.cos(phi), 0, (float) -Math.sin(phi)).times(velocity * Screen.dt));
	}
	
	public void flipSpeed() {
		if (lastBoost < System.currentTimeMillis()) {
			boost = !boost;
			this.lastBoost = System.currentTimeMillis() + wait;
		}
	}
	
	public String toString() {
		return super.toString() + "  \tphi: " + phi + "  \ttheta: " + theta + "  \tpsi: " + psi;
	}

}
