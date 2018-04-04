package mechanics.utils;

import java.util.Comparator;

import mechanics.graphics.Camera;

public class DrawableComparator implements Comparator<Drawable> {
	
	private Camera camera;
	
	public DrawableComparator(Camera camera) {
		this.camera = camera;
	}
	
	/**
	 * @return 1 if e1 is closer to the camera than e2, 0 if they are equidistant, and -1 if e2 is closer.
	 */
	public int compare(Drawable d1, Drawable d2) {
		float e1 = d1.distance(camera);
		float e2 = d2.distance(camera);
		return e1 < e2 ? 1 : e1 == e2 ? 0 : -1;
	}
	
}
