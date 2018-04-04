package mechanics.physics;

import tensor.Vector;

public class CollisionInformation {
	
	private Vector location, norm;
	
	public CollisionInformation(Vector location, Vector norm) {
		this.location = location;
		this.norm = norm;
	}
	
	public Vector location() {
		return location;
	}
	
	public Vector norm() {
		return norm;
	}
	
}
