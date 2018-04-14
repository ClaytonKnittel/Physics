package mechanics.graphics.shapes;

import mechanics.physics.CollisionInformation;
import tensor.DVector;
import tensor.Matrix4;

public class Box implements Shape {
	
	/**
	 * Length, width and height of box, respectively
	 * <p>
	 * Length is along the x-axis, width is along y, and height is along z.
	 */
	private float l, w, h;
	
	private static float[] modelData;
	
	public Box(float l, float w, float h) {
		this.l = l;
		this.w = w;
		this.h = h;
	}
	
	static {
		modelData = new float[] {
			-.5f, -.5f, .5f,	0, 0, 1,
			.5f, -.5f, .5f,	0, 0, 1,
			-.5f, .5f, .5f,	0, 0, 1,

			.5f, -.5f, .5f,	0, 0, 1,
			.5f, .5f, .5f,	0, 0, 1,
			-.5f, .5f, .5f,	0, 0, 1,
			
			
			-.5f, -.5f, -.5f,	0, 0, -1,
			-.5f, .5f, -.5f,	0, 0, -1,
			.5f, -.5f, -.5f,	0, 0, -1,

			.5f, -.5f, -.5f,	0, 0, -1,
			-.5f, .5f, -.5f,	0, 0, -1,
			.5f, .5f, -.5f,	0, 0, -1,
			
			
			.5f, -.5f, .5f,	1, 0, 0,
			.5f, -.5f, -.5f,	1, 0, 0,
			.5f, .5f, .5f,	1, 0, 0,

			.5f, -.5f, -.5f,	1, 0, 0,
			.5f, .5f, -.5f,	1, 0, 0,
			.5f, .5f, .5f,	1, 0, 0,
			
			
			-.5f, -.5f, .5f,	-1, 0, 0,
			-.5f, .5f, .5f,	-1, 0, 0,
			-.5f, -.5f, -.5f,	-1, 0, 0,

			-.5f, -.5f, -.5f,	-1, 0, 0,
			-.5f, .5f, .5f,	-1, 0, 0,
			-.5f, .5f, -.5f,	-1, 0, 0,
			
			
			-.5f, .5f, .5f,	0, 1, 0,
			.5f, .5f, .5f,	0, 1, 0,
			-.5f, .5f, -.5f,	0, 1, 0,

			.5f, .5f, .5f,	0, 1, 0,
			.5f, .5f, -.5f,	0, 1, 0,
			-.5f, .5f, -.5f,	0, 1, 0,
			
			
			-.5f, -.5f, .5f,	0, -1, 0,
			-.5f, -.5f, -.5f,	0, -1, 0,
			.5f, -.5f, .5f,	0, -1, 0,

			.5f, -.5f, .5f,	0, -1, 0,
			-.5f, -.5f, -.5f,	0, -1, 0,
			.5f, -.5f, -.5f,	0, -1, 0,
		};
	}

	@Override
	public Matrix4 model() {
		return Matrix4.scale(l, w, h);
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
	public float[] modelData() {
		return modelData;
	}
	
	@Override
	public double l1() {
		return (w * w + h * h) / 12;
	}
	
	@Override
	public double l2() {
		return (l * l + h * h) / 12;
	}
	
	@Override
	public double l3() {
		return (l * l + w * w) / 12;
	}
	
}
