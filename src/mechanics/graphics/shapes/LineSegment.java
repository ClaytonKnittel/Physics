package mechanics.graphics.shapes;

import mechanics.physics.CollisionInformation;
import tensor.DVector;
import tensor.Matrix4;
import tensor.Vector;
import tensor.Vector2;

public class LineSegment extends AbstractShape {
	
	private static final float[] modelData;
	
	private Vector start, end;
	
	private Vector center;
	
	private float radius, length;
	
	// tells which direction this line is pointing
	private float phi, theta;
	
	static {
		int numPoints = 15;
		
		float pi2 = 2 * (float) Math.PI;
		float d = pi2 / numPoints;
		float l = .5f;
		float w = .5f;
		
		modelData = new float[36 * numPoints];
		
		for (int i = 0; i < numPoints; i++) {
			modelData[36 * i] = -l;									// x1
			modelData[36 * i + 1] = yPosInCircle(w, (i + 1) * d);	// y1
			modelData[36 * i + 2] = zPosInCircle(w, (i + 1) * d);	// z1
			modelData[36 * i + 3] = 0;								// n1x
			modelData[36 * i + 4] = yPosInCircle(1, (i + 1) * d);	// n1y
			modelData[36 * i + 5] = zPosInCircle(1, (i + 1) * d);	// n1z
			modelData[36 * i + 6] = -l;								// x2
			modelData[36 * i + 7] = yPosInCircle(w, i * d);			// y2
			modelData[36 * i + 8] = zPosInCircle(w, i * d);			// z2
			modelData[36 * i + 9] = 0;								// n2x
			modelData[36 * i + 10] = yPosInCircle(1, i * d);		// n2y
			modelData[36 * i + 11] = zPosInCircle(1, i * d);		// n2z
			modelData[36 * i + 12] = l;								// x3
			modelData[36 * i + 13] = yPosInCircle(w, (i + 1) * d);	// y3
			modelData[36 * i + 14] = zPosInCircle(w, (i + 1) * d);	// z3
			modelData[36 * i + 15] = 0;								// n3x
			modelData[36 * i + 16] = yPosInCircle(1, (i + 1) * d);	// n3y
			modelData[36 * i + 17] = zPosInCircle(1, (i + 1) * d);	// n3z
			
			modelData[36 * i + 18] = -l;							// x4
			modelData[36 * i + 19] = yPosInCircle(w, i * d);		// y4
			modelData[36 * i + 20] = zPosInCircle(w, i * d);		// z4
			modelData[36 * i + 21] = 0;								// n4x
			modelData[36 * i + 22] = yPosInCircle(1, i * d);		// n4y
			modelData[36 * i + 23] = zPosInCircle(1, i * d);		// n4z
			modelData[36 * i + 24] = l;								// x5
			modelData[36 * i + 25] = yPosInCircle(w, i * d);		// y5
			modelData[36 * i + 26] = zPosInCircle(w, i * d);		// z5
			modelData[36 * i + 27] = 0;								// n5x
			modelData[36 * i + 28] = yPosInCircle(1, i * d);		// n5y
			modelData[36 * i + 29] = zPosInCircle(1, i * d);		// n5z
			modelData[36 * i + 30] = l;								// x6
			modelData[36 * i + 31] = yPosInCircle(w, (i + 1) * d);	// y6
			modelData[36 * i + 32] = zPosInCircle(w, (i + 1) * d);	// z6
			modelData[36 * i + 33] = 0;								// n6x
			modelData[36 * i + 34] = yPosInCircle(1, (i + 1) * d);	// n6y
			modelData[36 * i + 35] = zPosInCircle(1, (i + 1) * d);	// n6z
		}
	}
	
	public LineSegment(Vector start, Vector end, float width, String color) {
		super(color);
		this.start = start;
		this.end = end;
		
		this.radius = width / 2;
		
		set();
		setLightAttribs(0, 1);
	}
	
	protected LineSegment() {
		super("black");
	}
	
	private void set() {
		this.center = start.plus(end).divide(2);
		
		Vector len = end.minus(start);
		this.length = len.mag();
		Vector2 projXZ = new Vector2(len.x(), len.z());
		if (projXZ.x() == 0 && projXZ.y() == 0)
			this.phi = 0;
		else
			this.phi = (float) Math.acos(projXZ.x() / projXZ.mag()) * (projXZ.y() < 0 ? 1 : -1);
		this.theta = (float) Math.asin(len.y() / length);
		updateModel();
	}
	
	protected float[] rawModelData() {
		return modelData;
	}
	
	private void updateModel() {
		updateModel(Matrix4.translate(center).multiply(Matrix4.yRotate(phi).multiply(Matrix4.zRotate(theta)).multiply(Matrix4.scale(length, radius, radius))));
	}
	
	public void setStart(Vector start) {
		this.start = start;
		set();
	}
	
	public void setEnd(Vector end) {
		this.end = end;
		set();
	}
	
	public void setDir(Vector dir, float length) {
		setEnd(start.plus(dir.normalized().times(length)));
	}
	
	/**
	 * 
	 * @param radius radius of this line
	 * @param angle the angle above 0 (pointing in the z-direction)
	 * @return z-coordinate
	 */
	private static float zPosInCircle(float radius, float angle) {
		return radius * (float) Math.cos(angle);
	}
	/**
	 * 
	 * @param radius radius of this line
	 * @param angle the angle above 0 (pointing in the z-direction)
	 * @return z-coordinate
	 */
	private static float yPosInCircle(float radius, float angle) {
		return radius * (float) Math.sin(angle);
	}
	
	public Vector start() {
		return start;
	}
	
	public Vector end() {
		return end;
	}
	
	public String toString() {
		return "Center: " + center + "\tLength: " + length + "\t" + texture();
	}

	@Override
	public CollisionInformation collisionInformation(Shape s, DVector thisToOther) {
		return null;
	}

	@Override
	public boolean colliding(Shape s, DVector thisToOther) {
		return false;
	}

	@Override
	public double l1() {
		return 0;
	}

	@Override
	public double l2() {
		return 0;
	}

	@Override
	public double l3() {
		return 0;
	}
	
	
}
