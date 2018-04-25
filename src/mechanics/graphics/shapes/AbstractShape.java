package mechanics.graphics.shapes;

import tensor.Matrix4;

public abstract class AbstractShape implements Shape {
	
	private Matrix4 model;
	
	private float[] selectModelData;
	
	private String texture;
	private float reflectivity, shineDamper;
	
	public AbstractShape(String texture) {
		this.texture = texture;
		setLightAttribs(0, 1);
		updateModelData();
	}
	
	protected abstract float[] rawModelData();
	
	private void updateModelData() {
		selectModelData = rawModelData();
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
	public String texture() {
		return texture;
	}

	@Override
	public void setTexture(String texture) {
		this.texture = texture;
		updateModelData();
	}
	
	@Override
	public void update() {
		return;
	}

}
