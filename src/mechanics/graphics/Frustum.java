package mechanics.graphics;

import tensor.Vector;

public class Frustum extends Camera {
	
	private float angleX, depth;
	private float width, height, halfWidth, halfHeight;
	
	public Frustum(float width, float height, float viewAngle, Vector pos, float phi, float theta, float psi) {
		super(pos, phi, theta, psi);
		this.width = width;
		this.height = height;
		this.halfWidth = width / 2;
		this.halfHeight = height / 2;
		this.angleX = (float) Math.toRadians(viewAngle) / 2f;
		this.depth = width / (float) Math.tan(this.angleX);
	}
	
	public Frustum(float width, float height, float viewAngle, Vector pos) {
		this(width, height, viewAngle, pos, 0, (float) Math.PI / 2, 0);
	}
	
	public float width() {
		return width;
	}
	
	public float height() {
		return height;
	}
	
	public float halfWidth() {
		return halfWidth;
	}
	
	public float halfHeight() {
		return halfHeight;
	}
	
	public float depth() {
		return depth;
	}
	
	public boolean visible(Vector v) {
		return true;
	}
	
}
