package mechanics.graphics.shapes;

import graphics.VBOConverter;
import mechanics.physics.CollisionInformation;
import tensor.DVector;
import tensor.Matrix4;
import tensor.Vector;

public class Axes implements Shape {
	
	private static final float[][] modelData;
	
	private Vector pos;
	private float length;
	private final String x, y, z;
	private float reflectivity, shineDamper;
	
	private Matrix4 model;
	
	private static final float factor = 15;
	
	private final float[] selectModelData;
	
	static {
		float[] line = new LineSegment().rawModelData();
		modelData = new float[3][line.length];
		for (int i = 0; i < line.length; i++) {
			modelData[0][i] = line[i];				// x
			modelData[1][i] = line[i / 3 * 3 + (i + 2) % 3];	// y
			modelData[2][i] = line[i / 3 * 3 + (i + 1) % 3];	// z
		}
		for (int j = 0; j < modelData.length; j++) {
			for (int i = j; i < modelData[j].length; i += 3)
				modelData[j][i] = (modelData[j][i] + .5f) * factor;
		}
	}
	
	public Axes(Vector pos, float length, String x, String y, String z) {
		this.pos = pos;
		this.length = length;
		this.x = x;
		this.y = y;
		this.z = z;
		selectModelData = setModelData();
		updateModel();
		setLightAttribs(0, 1);
	}
	
	private float[] setModelData() {
		float[] ret = new float[modelData[0].length * 3 * 3 / 2];
		float[] x = VBOConverter.toPosNormTexture(modelData[0]);
		float[] y = VBOConverter.toPosNormTexture(modelData[1]);
		float[] z = VBOConverter.toPosNormTexture(modelData[2]);
		
		int j = 0;
		for (int i = 0; i < x.length; i++)
			ret[j++] = x[i];
		for (int i = 0; i < y.length; i++)
			ret[j++] = y[i];
		for (int i = 0; i < z.length; i++)
			ret[j++] = z[i];
		
		return ret;
	}
	
	private void updateModel() {
		model = Matrix4.translate(pos).multiply(Matrix4.scale(length / factor));
	}
	
	public void setPos(Vector pos) {
		this.pos = pos;
		updateModel();
	}
	
	public void setSize(float size) {
		this.length = size;
		updateModel();
	}
	
	@Override
	public String texture() {
		return x;
	}
	
	@Override
	public void setTexture(String t) {
		return;
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
	public Matrix4 model() {
		return model;
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

	@Override
	public void update() {
		return;
	}
	
	
	
}
