package mechanics2D.shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Circle implements Shape {
	
	private float radius;
	private Orientable owner;
	private Color color;
	
	public Circle(float radius, Orientable owner, Color color) {
		this.radius = radius;
		this.owner = owner;
		this.color = color;
	}
	
	public Circle(float radius, Color color) {
		this(radius, null, color);
	}
	
	@Override
	public void setOwner(Orientable owner) {
		this.owner = owner;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		int r2 = (int) (2 * radius);
		g.fillOval((int) (owner.x() - radius), (int) (owner.y() - radius), r2, r2);
	}
	
	@Override
	public double moment() {
		return 2d * radius * radius / 5d;
	}
	
}
