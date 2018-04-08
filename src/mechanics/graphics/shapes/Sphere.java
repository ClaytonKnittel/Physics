package mechanics.graphics.shapes;

import java.awt.Color;

import mechanics.graphics.ImageGraphics;
import mechanics.graphics.math.GMath;
import mechanics.physics.CollisionInformation;
import tensor.DVector;
import tensor.Vector;

public class Sphere implements Shape {
	
	private float radius;
	
	public Sphere(float radius) {
		this.radius = radius;
	}

	public void draw(ImageGraphics g, Vector pos, Vector z, float angle, Color c) {
		float d = GMath.lengthOnScreen(radius, -pos.z());
		g.setColor(c);
		g.drawCircle(GMath.screenX(pos), GMath.screenY(pos), d);
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
