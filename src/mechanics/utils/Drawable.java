package mechanics.utils;

import java.awt.Graphics;

import mechanics.graphics.Camera;

public interface Drawable {
	
	/**
	 * Used to determine the order in which to draw obejcts.
	 * 
	 * @return the distance away from the Screen this Object is
	 */
	float distance(Camera c);
	
	
	void draw(Graphics g);
	
	/**
	 * update this <code>Entity</code>'s graphics parameters
	 */
	void update();
}
