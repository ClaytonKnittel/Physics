package mechanics2D.shapes;

import java.awt.Graphics;

import tensor.DMatrix2;
import tensor.DVector2;

import mechanics2D.physics.Body;

public class Rectangle extends AbstractShape {
	
	private double length, height;
	
	private DVector2 ll, lr, ul, ur;
	
	public Rectangle(double length, double height, Orientable owner, java.awt.Color color) {
		super(owner, color);
		this.length = length;
		this.height = height;
	}
	
	private void setCorners() {
		ll = new DVector2(owner().pos().x() - length / 2, owner().pos().y() + height / 2);
		lr = new DVector2(owner().pos().x() + length / 2, owner().pos().y() + height / 2);
		ul = new DVector2(owner().pos().x() - length / 2, owner().pos().y() - height / 2);
		ur = new DVector2(owner().pos().x() + length / 2, owner().pos().y() - height / 2);
	}
	
	public double length() {
		return length;
	}
	
	public double height() {
		return height;
	}

	@Override
	public void draw(Graphics g) {
		DVector2 ll, lr, ul, ur;
		DMatrix2 rot = DMatrix2.rotate(owner().angle());
		
		setCorners();
		ll = rot.multiply(this.ll.minus(owner().pos())).plus(owner().pos());
		lr = rot.multiply(this.lr.minus(owner().pos())).plus(owner().pos());
		ul = rot.multiply(this.ul.minus(owner().pos())).plus(owner().pos());
		ur = rot.multiply(this.ur.minus(owner().pos())).plus(owner().pos());
		
		if (((Body) owner()).colliding) {
			g.setColor(Body.collideColor);
		} else
			g.setColor(color());
		
		g.fillPolygon(new int[] {(int) ll.x(), (int) lr.x(), (int) ur.x(), (int) ul.x()}, 
				new int[] {(int) ll.y(), (int) lr.y(), (int) ur.y(), (int) ul.y()}, 4);
	}
	
	@Override
	public boolean colliding(Shape s) {
		if (s instanceof Rectangle) {
			if (collidingRects((Rectangle) s, true))
				return true;
		}
		return false;
	}
	
	/**
	 * This is a special case of the separating axis theorem (SAT)
	 * @param r the rectangle to be compared to this
	 * @param first true when called outside this method, false when called within
	 * @return whether or not these rectangles collide
	 */
	private boolean collidingRects(Rectangle r, boolean first) {
		Orientable rPos = r.transform(super.owner().pos(), super.owner().angle());
		
		double hlength = r.length / 2;
		double hheight = r.height / 2;
		
		double theta = rPos.angle();
		DVector2 rpos = rPos.pos();
		double x = rpos.x();
		double y = rpos.y();
		
		double cos = Math.cos(theta);
		double sin = Math.sin(theta);
		
		// first test projection onto x-axis (will need to compare to length / 2)
		double dif = hlength * Math.abs(cos) + hheight * Math.abs(sin);
		if (notIntersecting(x - dif, x + dif, -this.length / 2, this.length / 2))
			return false;
		
		// then test projection onto y-axis (will need to compare to height / 2)
		dif = hlength * Math.abs(sin) + hheight * Math.abs(cos);
		if (notIntersecting(y - dif, y + dif, -this.height / 2, this.height / 2))
			return false;
		
		if (!first)
			return true;
		
		if (r.collidingRects(this, false))
			return true;
		return false;
	}
	
	private boolean notIntersecting(double min1, double max1, double min2, double max2) {
		return min2 > max1 || min1 > max2;
	}

	@Override
	public CollisionInformation getCollisionInfo(Shape s) {
		return null;
	}

	@Override
	public double moment() {
		return (length * length + height * height) * length * height / 12;
	}

}
