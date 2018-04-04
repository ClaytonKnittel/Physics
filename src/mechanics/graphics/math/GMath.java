package mechanics.graphics.math;

import mechanics.graphics.Screen;
import numbers.cliffordAlgebras.Quaternion;
import tensor.Vector;

public class GMath {
	
	// frequency of frames (seconds / frame)
	public static final float dt = .01f;
	
	private static Screen screen;
	private static Quaternion transform;
	
	private static long frame = 0;
	
	/**
	 * Called after each graphics frame has passed
	 */
	public static void next() {
		frame++;
	}
	
	public static long frame() {
		return frame;
	}
	
	public static void init(Screen screen) {
		GMath.screen = screen;
	}
	
	public static void reset() {
		transform = new Quaternion(1, 0, 0, 0);
	}
	
	public static void appendRotation(Quaternion rotation) {
		transform = rotation.times(transform);
	}
	
	
	public static float lengthOnScreen(float length, float distance) {
		return length * screen.depth() / (distance);
	}
	
	/**
	 * @param v a transformed <code>Vector</code>, with respect to the screen
	 * @return the <code>Vector</code>'s screen x-coordinate
	 */
	public static int screenX(Vector v) {
		return screen.width() / 2 - (int) (v.x() * screen.depth() / (v.z()));
	}
	
	/**
	 * @param v a transformed <code>Vector</code>, with respect to the screen
	 * @return the <code>Vector</code>'s screen y-coordinate
	 */
	public static int screenY(Vector v) {
		return screen.height() / 2 + (int) (v.y() * screen.depth() / (v.z()));
	}
	
	
	public static Quaternion transform(Quaternion q) {
		return transform.rotate(q.minus(screen.getCamera()));
	}
	
	
	
	
	
}
