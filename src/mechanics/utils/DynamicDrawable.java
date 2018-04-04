package mechanics.utils;

import mechanics.graphics.Screen;

/**
 * @author claytonknittel
 * 
 * DynamicDrawables are able to freely add and remove Drawable obejcts to and from the Screen. They themselves are not Drawable, but
 * they are in control of adding their components to the Screen to be rendered.
 *
 */
public interface DynamicDrawable {
	
	/**
	 * Gives the DynamicDrawable access to the Screen that it will be adding and removing its elements from
	 * 
	 * @param s the Screen that will be drawing the parts of this DynamicDrawable
	 */
	void give(Screen s);
}
