package mechanics.graphics.shapes;

import graphics.Color;
import graphics.VBOConverter;
import tensor.Matrix4;

public abstract class AbstractShape implements Shape {
	
	private Matrix4 model;
	
	private float[] selectModelData;
	
	private Color color;
	private float reflectivity, shineDamper;
	
	public AbstractShape(Color c) {
		this.color = c;
		setLightAttribs(0, 1);
		updateModelData();
	}
	
	protected abstract float[] rawModelData();
	
	private void updateModelData() {
		selectModelData = VBOConverter.toPosNormColor(rawModelData(), color);
	}

	@Override
	public Matrix4 model() {
		return model;
	}
	
	
	public void updateModel(Matrix4 model) {
		this.model = model;
	}
	
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
	public Color color() {
		return color;
	}

	@Override
	public void setColor(Color c) {
		this.color = c;
		updateModelData();
	}
	
	@Override
	public void update() {
		return;
	}

}
