package mechanics.graphics;

import java.awt.Graphics;

import mechanics.graphics.math.GMath;
import mechanics.utils.Entity;
import numbers.cliffordAlgebras.Quaternion;
import tensor.Vector;

public class Camera extends Vector {
	
	private float polar, azimuthal;
	
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
	
	public Camera(Vector pos, float polar, float azimuthal) {
		super(pos);
		this.polar = polar;
		this.azimuthal = azimuthal;
		this.boost = false;
		this.lastBoost = 0;
	}
	
	public Camera(Vector pos) {
		this(pos, 0, 0);
	}
	
	public float polar() {
		return polar;
	}
	
	public float azimuthal() {
		return azimuthal;
	}
	
	public void rotate(float vPolar, float vAzimuthal) {
		polar += vPolar * GMath.dt;
		azimuthal += vAzimuthal * GMath.dt;
	}
	
	public void updward(float velocity) {
		if (boost)
			velocity *= factor;
		this.add(new Vector(0, velocity * GMath.dt, 0));
	}
	
	public void forward(float velocity) {
		if (boost)
			velocity *= factor;
		this.add(new Vector((float) -Math.sin(azimuthal), 0, (float) -Math.cos(azimuthal)).times(velocity * GMath.dt));
	}
	
	/**
	 * @param velocity the velocity at which to travel in the rightward direction
	 */
	public void sideways(float velocity) {
		if (boost)
			velocity *= factor;
		this.add(new Vector((float) Math.cos(azimuthal), 0, (float) -Math.sin(azimuthal)).times(velocity * GMath.dt));
	}
	
	public void flipSpeed() {
		if (lastBoost < System.currentTimeMillis()) {
			boost = !boost;
			this.lastBoost = System.currentTimeMillis() + wait;
		}
	}
	
	public Quaternion getTransformation() {
		return Quaternion.euler(Vector.X, polar).times(Quaternion.euler(Vector.Y, -azimuthal));
	}
	
	public float distance(Entity e) {
		return 0;
	}

	public void draw(Graphics g) {
	}
	
	public String toString() {
		return super.toString() + "  \t" + polar + "    " + azimuthal;
	}

}
