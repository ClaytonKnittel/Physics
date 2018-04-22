package mechanics.graphics.shapes;

import graphics.VBOConverter;
import mechanics.physics.CollisionInformation;
import tensor.DVector;
import tensor.Matrix4;

public class Box extends AbstractShape {
	
	private static final float[] modelData;
	
	/**
	 * Length, width and height of box, respectively
	 * <p>
	 * Length is along the x-axis, width is along y, and height is along z.
	 */
	private float l, w, h;
	
	public Box(float l, float w, float h, String texture) {
		super(texture);
		this.l = l;
		this.w = w;
		this.h = h;
		updateModel();
		setLightAttribs(0, 1);
	}
	
	static {
		float[] rawModelData = new float[] {
			-.5f, -.5f, .5f,	0, 0, 1,
			.5f, -.5f, .5f,		0, 0, 1,
			-.5f, .5f, .5f,		0, 0, 1,

			.5f, -.5f, .5f,		0, 0, 1,
			.5f, .5f, .5f,		0, 0, 1,
			-.5f, .5f, .5f,		0, 0, 1,
			
			
			-.5f, -.5f, -.5f,	0, 0, -1,
			-.5f, .5f, -.5f,	0, 0, -1,
			.5f, -.5f, -.5f,	0, 0, -1,

			.5f, -.5f, -.5f,	0, 0, -1,
			-.5f, .5f, -.5f,	0, 0, -1,
			.5f, .5f, -.5f,		0, 0, -1,
			
			
			.5f, -.5f, .5f,		1, 0, 0,
			.5f, -.5f, -.5f,	1, 0, 0,
			.5f, .5f, .5f,		1, 0, 0,

			.5f, -.5f, -.5f,	1, 0, 0,
			.5f, .5f, -.5f,		1, 0, 0,
			.5f, .5f, .5f,		1, 0, 0,
			
			
			-.5f, -.5f, .5f,	-1, 0, 0,
			-.5f, .5f, .5f,		-1, 0, 0,
			-.5f, -.5f, -.5f,	-1, 0, 0,

			-.5f, -.5f, -.5f,	-1, 0, 0,
			-.5f, .5f, .5f,		-1, 0, 0,
			-.5f, .5f, -.5f,	-1, 0, 0,
			
			
			-.5f, .5f, .5f,		0, 1, 0,
			.5f, .5f, .5f,		0, 1, 0,
			-.5f, .5f, -.5f,	0, 1, 0,

			.5f, .5f, .5f,		0, 1, 0,
			.5f, .5f, -.5f,		0, 1, 0,
			-.5f, .5f, -.5f,	0, 1, 0,
			
			
			-.5f, -.5f, .5f,	0, -1, 0,
			-.5f, -.5f, -.5f,	0, -1, 0,
			.5f, -.5f, .5f,		0, -1, 0,

			.5f, -.5f, .5f,		0, -1, 0,
			-.5f, -.5f, -.5f,	0, -1, 0,
			.5f, -.5f, -.5f,	0, -1, 0,
		};
		modelData = VBOConverter.toPosNormColor(rawModelData);
	}
	
	protected float[] rawModelData() {
		return modelData;
	}
	
	private void updateModel() {
		updateModel(Matrix4.scale(l, w, h));
	}
	
	public void setDimensions(float l, float w, float h) {
		this.l = l;
		this.w = w;
		this.h = h;
		updateModel();
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
