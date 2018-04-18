package mechanics.graphics.shapes;

import graphics.Color;
import graphics.VBOConverter;
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
	
	private Matrix4 model;
	
	private static float[] modelData;
	
	private float[] selectModelData;
	
	private Color color;
	private float reflectivity, shineDamper;
	
	public Box(float l, float w, float h, Color color) {
		this.l = l;
		this.w = w;
		this.h = h;
		this.color = color;
		updateModel();
		setModelData();
		setLightAttribs(0, 1);
	}
	
	static {
		modelData = new float[] {
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
	}
	
	private void updateModel() {
		model = Matrix4.scale(l, w, h);
	}
	
	private void setModelData() {
		selectModelData = VBOConverter.toPosNormColor(modelData, color);
	}
	
	public void setDimensions(float l, float w, float h) {
		this.l = l;
		this.w = w;
		this.h = h;
		updateModel();
	}
	
	@Override
	public Color color() {
		return color;
	}
	
	@Override
	public void setColor(Color c) {
		this.color = c;
		setModelData();
	}

	@Override
	public Matrix4 model() {
		return model;
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
		return selectModelData;
	}
	
	@Override
	public float reflectivity() {
		return reflectivity;
	}
	
	@Override
	public float shineDamper() {
		return shineDamper;
	}
	
	@Override
	public void setLightAttribs(float reflectivity, float shineDamper) {
		this.reflectivity = reflectivity;
		this.shineDamper = shineDamper;
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
	
	@Override
	public void update() {
		return;
	}
	
}
