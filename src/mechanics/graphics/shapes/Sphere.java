package mechanics.graphics.shapes;

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
	
	public Sphere(float radius, String texture) {
		super(texture);
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
		if (Sphere.class.isAssignableFrom(s.getClass()))
			return sphereCollision((Sphere) s, thisToS);
		return null;
	}
	
	private CollisionInformation sphereCollision(Sphere s, DVector thisToS) {
		double mdist = radius + s.radius;
		mdist *= mdist;
		if (thisToS.mag2() > mdist)
			return null;
		return new CollisionInformation(thisToS.divide(2), thisToS.normalized());
	}
	
	public boolean colliding(Shape s, DVector thisToS) { 
		if (Sphere.class.isAssignableFrom(s.getClass())) {
			double mdist = radius + ((Sphere) s).radius;
			return thisToS.mag2() <= mdist * mdist;
		}
		return false;
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
