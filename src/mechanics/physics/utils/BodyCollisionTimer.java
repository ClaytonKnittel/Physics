package mechanics.physics.utils;

import mechanics.physics.bodies.Body;
import mechanics.physics.bodies.PMath;

/**
 * 
 * @author claytonknittel
 * 
 * <p>
 * 
 * A class that retains information about which Bodies have recently collided with the
 * one holding this Object, and it also keeps track of how long it needs to wait before
 * it can collide with this Body again.
 * <br>
 * Meant to prevent collision bouncing (colliding multiple times when only one collision should happen).
 *
 */
public class BodyCollisionTimer {
	
	private Body b;
	private long destructTime;
	
	/**
	 * True if these Objects are fixated on each other, meaning they should not physically interact
	 */
	private boolean fixated;
	
	// this value is the number of physics frames that must pass before this Body can be collided with again
	private static final long WAIT = 6l;
	
	public BodyCollisionTimer(Body b) {
		this.b = b;
		reset();
	}
	
	public void reset() {
		destructTime = PMath.frame() + WAIT;
	}
	
	boolean shouldDestroy() {
		return PMath.frame() > destructTime;
	}
	
	boolean fixated() {
		return fixated;
	}
	
	void fixate() {
		fixated = true;
	}
	
	Body body() {
		return b;
	}
	
}
