package mechanics2D.shapes;

import java.awt.Graphics;

import tensor.DMatrix2;
import tensor.DVector2;

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
		g.setColor(color());
		g.fillPolygon(new int[] {(int) ll.x(), (int) lr.x(), (int) ur.x(), (int) ul.x()}, 
				new int[] {(int) ll.y(), (int) lr.y(), (int) ur.y(), (int) ul.y()}, 4);
	}
	
	@Override
	public boolean colliding(Shape s) {
		return false;
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
