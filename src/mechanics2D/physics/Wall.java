package mechanics2D.physics;

import java.awt.Color;

import mechanics2D.shapes.Rectangle;

public class Wall extends PassiveBody {

	public Wall(double x, double y, double mass, double length, double width, Color color) {
		super(x, y, mass, new Rectangle(length, width, null, color));
		super.shape().setOwner(this);
	}

}
