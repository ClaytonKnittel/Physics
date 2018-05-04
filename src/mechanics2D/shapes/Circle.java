package mechanics2D.shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends AbstractShape {
	
	private double radius;
	
	public Circle(double radius, Orientable owner, Color color) {
		super(owner, color);
		this.radius = radius;
	}
	
	public Circle(float radius, Color color) {
		this(radius, null, color);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(color());
		int r2 = (int) (2 * radius);
		g.fillOval((int) (owner().pos().x() - radius), (int) (owner().pos().y() - radius), r2, r2);
	}
	
	@Override
	public double moment() {
		return 2d * radius * radius / 5d;
	}
	
	@Override
	public boolean colliding(Shape s, boolean computeCollisionInfo) {
		if (s instanceof Circle) {
			Circle c = (Circle) s;
			double di = radius + c.radius;
			di *= di;
			return di >= owner().pos().minus(c.owner().pos()).mag2();
		}
		return false;
	}
	
}
