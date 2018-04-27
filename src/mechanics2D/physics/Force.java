package mechanics2D.physics;

import mechanics2D.shapes.CollisionInformation;
import tensor.DVector2;

public class Force extends CollisionInformation {
	
	public Force(DVector2 force, DVector2 loc) {
		super(force, loc);
	}
	
	public Force opposite() {
		return new Force(dir().times(-1), loc());
	}
	
}
