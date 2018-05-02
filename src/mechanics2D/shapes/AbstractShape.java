package mechanics2D.shapes;

import java.awt.Color;

import mechanics2D.math.Transformable;
import tensor.DMatrix2;
import tensor.DVector2;

public abstract class AbstractShape implements Shape, Transformable {
	
	private Orientable owner;
	private Color color;
	
	public AbstractShape(Orientable owner, Color color) {
		this.owner = owner;
		this.color = color;
	}
	
	public Color color() {
		return color;
	}
	
	public Orientable owner() {
		return owner;
	}
	
	@Override
	public void setOwner(Orientable owner) {
		this.owner = owner;
	}
	
	@Override
	public Orientable transform(DVector2 pos, double angle) {
		return new Orientable() {
			public DVector2 pos() {
				return DMatrix2.rotate(-angle).multiply(owner.pos().minus(pos));
			}

			public void move(DVector2 dPos) {
				return;
			}

			public double angle() {
				return owner.angle() - angle;
			}

			public void rotate(double dAngle) {
				return;
			}
		};
	}
	
}
