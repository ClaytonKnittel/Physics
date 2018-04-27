package mechanics2D.shapes;

import tensor.DVector2;

public class CollisionInformation {
	
	private DVector2 dir, loc;
	
	public CollisionInformation(DVector2 dir, DVector2 loc) {
		this.dir = dir;
		this.loc = loc;
	}
	
	public DVector2 dir() {
		return dir;
	}
	
	public DVector2 loc() {
		return loc;
	}
	
}
