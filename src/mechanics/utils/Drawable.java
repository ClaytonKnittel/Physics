package mechanics.utils;

import mechanics.graphics.Camera;
import mechanics.graphics.ImageGraphics;

public interface Drawable {
	
	/**
	 * Used to determine the order in which to draw obejcts.
	 * 
	 * @return the distance away from the Screen this Object is
	 */
	float distance(Camera c);
	
	
	void draw(ImageGraphics g);
	
	/**
	 * update this <code>Entity</code>'s graphics parameters
	 */
	void update();
}
