package mechanics.graphics.shapes;

import graphics.Color;
import graphics.models.OBJLoader;
import mechanics.physics.CollisionInformation;
import tensor.DVector;
import tensor.Matrix4;

public class Guitar extends AbstractShape {
	
	private float scale;
	
	private static final float[] modelData;
	
	static {
		modelData = OBJLoader.loadVertexNormOBJ("/users/claytonknittel/downloads/ccrrd08t8vsw-e/eg/obj/Electric Guitar.obj").getData();
	}
	
	public Guitar(float scale, Color color) {
		super(color);
		setScale(scale);
		updateModel();
		setLightAttribs(0, 1);
	}
	
	@Override
	protected float[] rawModelData() {
		return modelData;
	}
	
	private void updateModel() {
		this.updateModel(Matrix4.scale(scale));
	}
	
	public void setScale(float scale) {
		this.scale = scale;
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
		return 1;
	}

	@Override
	public double l2() {
		return 1;
	}

	@Override
	public double l3() {
		return 1;
	}
	
	
	
}
