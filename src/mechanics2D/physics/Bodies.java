package mechanics2D.physics;

import mechanics2D.graphics.Drawable;
import structures.Web;

public class Bodies {
	
	private Web<Body> bodies;
	
	public Bodies() {
		bodies = new Web<>();
	}
	
	public void add(Body...b) {
		for (Body bo : b)
			bodies.add(bo);
	}
	
	public void attemptAdd(Drawable... drawables) {
		for (Drawable d : drawables) {
			if (Body.class.isAssignableFrom(d.getClass()))
				bodies.add((Body) d);
		}
	}
	
	public void update() {
		bodies.actPairs((b1, b2) -> b1.interact(b2));
		bodies.act(b -> b.update());
	}
	
}
