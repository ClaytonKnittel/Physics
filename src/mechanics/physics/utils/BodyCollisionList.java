package mechanics.physics.utils;

import mechanics.physics.bodies.Body;
import mechanics.physics.bodies.PMath;

public class BodyCollisionList extends NodeIterable {
	
	private Body self;
	
	
	public BodyCollisionList(Body self) {
		super();
		this.self = self;
	}
	
	public void update() {
		for (Node b : this) {
			if (b.el.shouldDestroy()) {
				if (b.el.body().colliding(self)) {
					b.el.reset();
					b.el.fixate();
					PMath.fixate(self, b.el.body());
					return;
				}
				remove(b);
			}
		}
	}
	
	public boolean shouldCollide(Body b) {
		for (Node bct : this) {
			if (bct.el.body() == b)
				return false;
		}
		return true;
	}
	
	public boolean shouldInteract(Body b) {
		for (Node bct : this) {
			if (bct.el.body() == b) {
				if (bct.el.fixated())
					return false;
				break;
			}
		}
		return true;
	}
	
	public String toString() {
		String ret = "";
		for (Node n : this)
			ret += n.el.body() + "\n";
		return ret;
	}
	
}
