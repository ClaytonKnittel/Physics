package mechanics.graphics.shapes;

import mechanics.physics.CollisionInformation;
import tensor.DVector;

public class Box implements Shape {
	
	/**
	 * Length, width and height of box, respectively
	 * <p>
	 * Length is along the x-axis, width is along y, and height is along z.
	 */
	private float l, w, h;
	
	private float[] modelData;
	
	public Box(float l, float w, float h) {
		this.l = l;
		this.w = w;
		this.h = h;
		createModel();
	}
	
	private void createModel() {
		float l = this.l / 2;
		float w = this.w / 2;
		float h = this.h / 2;
		this.modelData = new float[] {
			-l, -w, h,	0, 0, 1,
			l, -w, h,	0, 0, 1,
			-l, w, h,	0, 0, 1,

			l, -w, h,	0, 0, 1,
			l, w, h,	0, 0, 1,
			-l, w, h,	0, 0, 1,
			
			
			-l, -w, -h,	0, 0, -1,
			-l, w, -h,	0, 0, -1,
			l, -w, -h,	0, 0, -1,

			l, -w, -h,	0, 0, -1,
			-l, w, -h,	0, 0, -1,
			l, w, -h,	0, 0, -1,
			
			
			l, -w, h,	1, 0, 0,
			l, -w, -h,	1, 0, 0,
			l, w, h,	1, 0, 0,

			l, -w, -h,	1, 0, 0,
			l, w, -h,	1, 0, 0,
			l, w, h,	1, 0, 0,
			
			
			-l, -w, h,	-1, 0, 0,
			-l, w, h,	-1, 0, 0,
			-l, -w, -h,	-1, 0, 0,

			-l, -w, -h,	-1, 0, 0,
			-l, w, h,	-1, 0, 0,
			-l, w, -h,	-1, 0, 0,
			
			
			-l, w, h,	0, 1, 0,
			l, w, h,	0, 1, 0,
			-l, w, -h,	0, 1, 0,

			l, w, h,	0, 1, 0,
			l, w, -h,	0, 1, 0,
			-l, w, -h,	0, 1, 0,
			
			
			-l, -w, h,	0, -1, 0,
			-l, -w, -h,	0, -1, 0,
			l, -w, h,	0, -1, 0,

			l, -w, h,	0, -1, 0,
			-l, -w, -h,	0, -1, 0,
			l, -w, -h,	0, -1, 0,
		};
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
	
}
