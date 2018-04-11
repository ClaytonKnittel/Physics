package mechanics.graphics.shapes;

import graphics.models.OBJLoader;
import mechanics.physics.CollisionInformation;
import tensor.DVector;

public class Sphere implements Shape {
	
	private float radius;
	
	private static final float[] modelData;
	
	static {
//		modelData = new float[] {
//				0, -1, 0,	0, -1, 0,
//				1, 0, 1,	1, 0, 1,
//				-1, 0, 1,	-1, 0, 1,
//				
//				-1, 0, 1,	-1, 0, 1,
//				1, 0, 1,	1, 0, 1,
//				0, 1, 0,	0, 1, 0,
//				
//				0, -1, 0,	0, -1, 0,
//				1, 0, -1,	1, 0, -1,
//				1, 0, 1,	1, 0, 1,
//				
//				1, 0, 1,	1, 0, 1,
//				1, 0, -1,	1, 0, -1,
//				0, 1, 0,	0, 1, 0,
//				
//				0, -1, 0,	0, -1, 0,
//				-1, 0, -1,	-1, 0, -1,
//				1, 0, -1,	1, 0, -1,
//				
//				1, 0, -1,	1, 0, -1,
//				-1, 0, -1,	-1, 0, -1,
//				0, 1, 0,	0, 1, 0,
//				
//				0, -1, 0,	0, -1, 0,
//				-1, 0, 1,	-1, 0, 1,
//				-1, 0, -1,	-1, 0, -1,
//				
//				-1, 0, -1,	-1, 0, -1,
//				-1, 0, 1,	-1, 0, 1,
//				0, 1, 0,	0, 1, 1
//		};
		modelData = OBJLoader.loadVertexNormOBJ("/Users/claytonknittel/git/Utilities/data/sphere").getData();
	}
	
	public Sphere(float radius) {
		this.radius = radius;
	}
	
	public float[] modelData() {
		return modelData;
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
	
	
}
