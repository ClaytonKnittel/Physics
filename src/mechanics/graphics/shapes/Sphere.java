package mechanics.graphics.shapes;

import graphics.Color;
import graphics.VBOConverter;
import graphics.models.OBJLoader;
import mechanics.physics.CollisionInformation;
import tensor.DVector;
import tensor.Matrix4;

public class Sphere implements Shape {
	
	private static float[] modelData;
	
	private float[] selectModelData;
	
	private Color color;
	
	private Matrix4 model;
	
	private float radius;
	
	static {
		modelData = OBJLoader.loadVertexNormOBJ("/Users/claytonknittel/git/Utilities/data/sphere").getData();
	}
	
	public Sphere(float radius, Color color) {
		this.radius = radius;
		this.color = color;
		updateModel();
		setModelData();
	}
	
	private void updateModel() {
		model = Matrix4.scale(radius);
	}
	
	private void setModelData() {
		selectModelData = VBOConverter.toPosNormColor(modelData, color);
	}
	
	@Override
	public Matrix4 model() {
		return model;
	}
	
	public float[] modelData() {
		return selectModelData;
	}
	
	public void setRadius(float r) {
		this.radius = r;
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
	
	@Override
	public void update() {
		return;
	}
	
	
}
