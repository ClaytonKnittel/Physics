package mechanics.graphics;

import graphics.input.Locatable;
import tensor.Matrix4;
import tensor.Vector;

public class Camera extends Vector implements Locatable {
	
	public static float pi2 = (float) Math.PI * 2;
	
	private float phi, theta, psi;
	
	private Vector v;
	private float dPhi, dTheta, dPsi;
	
	private boolean boost;
	
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
		freeze();
	}
	
	public Camera(Vector pos) {
		this(pos, 0, (float) Math.PI / 2, 0);
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
		this.add(Matrix4.phiRotate(phi).multiply(v).times(Screen.dt));
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
	
	public void setAngles(float phi, float theta, float psi) {
		this.phi = phi;
		this.theta = theta;
		this.psi = psi;
	}
	
	public void setDPhi(float dPhi) {
		this.dPhi = dPhi;
	}
	
	public void setDTheta(float dTheta) {
		this.dTheta = dTheta;
	}
	
	public void setDPsi(float dPsi) {
		this.dPsi = dPsi;
	}
	
	private static float mod2Pi(float angle) {
		if (angle < 0)
			return mod2Pi(angle + pi2);
		if (angle >= pi2)
			return mod2Pi(angle - pi2);
		return angle;
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
	
	public void toggleBoost() {
		boost = !boost;
	}
	
	public void rotate(float dPhi, float dTheta, float dPsi) {
		phi = mod2Pi(phi + dPhi);
		theta = mod2Pi(theta + dTheta);
		psi = mod2Pi(psi + dPsi);
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
	
	public String toString() {
		return super.toString() + "  \tphi: " + phi + "  \ttheta: " + theta + "  \tpsi: " + psi;
	}

}
