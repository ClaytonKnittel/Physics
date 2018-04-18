package mechanics.graphics.shapes;

import graphics.Color;
import graphics.models.OBJLoader;
import mechanics.physics.CollisionInformation;
import tensor.DVector;
import tensor.Matrix4;

public class Sphere extends AbstractShape {
	
	private float radius;
	
	private static final float[] modelData;
	
	static {
		modelData = OBJLoader.loadVertexNormOBJ("/Users/claytonknittel/git/Utilities/data/sphere").getData();
	}
	
	public Sphere(float radius, Color color) {
		super(color);
		this.radius = radius;
		updateModel();
		setLightAttribs(0, 1);
	}
	
	private void updateModel() {
		updateModel(Matrix4.scale(radius));
	}
	
	protected float[] rawModelData() {
		return modelData;
	}
	
	public void setRadius(float r) {
		this.radius = r;
		updateModel();
	}

	public CollisionInformation collisionInformation(Shape s, DVector thisToS) {
		if (!colliding(s, thisToS))
			return null;
		return new CollisionInformation(thisToS.divide(2), thisToS.normalized());
	}
	
	public boolean colliding(Shape s, DVector thisToS) {
		if (!Sphere.class.isAssignableFrom(s.getClass()))
			return false;
		Sphere p = (Sphere) s;
		double mdist = radius + p.radius;
		mdist *= mdist;
		if (thisToS.mag2() > mdist)
			return false;
		return true;
	}
	
	@Override
	public double l1() {
		return radius * radius * 2.0 / 5.0;
	}
	
	@Override
	public double l2() {
		return radius * radius * 2.0 / 5.0;
	}
	
	@Override
	public double l3() {
		return radius * radius * 2.0 / 5.0;
	}
	
	
}
