package mechanics.physics;

import tensor.DVector;

public class CollisionInformation {
	
	private DVector location, norm;
	
	public CollisionInformation(DVector location, DVector norm) {
		this.location = location;
		this.norm = norm;
	}
	
	public DVector location() {
		return location;
	}
	
	public DVector norm() {
		return norm;
	}
	
}
