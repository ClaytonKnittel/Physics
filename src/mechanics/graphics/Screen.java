package mechanics.graphics;

import java.util.HashMap;

import graphics.GLFWWindow;
import graphics.entities.GLFWRenderable;
import graphics.entities.LightSource;
import graphics.input.K;
import graphics.input.KeyAction;
import graphics.input.Locatable;
import mechanics.graphics.math.GMath;
import mechanics.utils.DynamicDrawable;
import mechanics.utils.ThreadMaster;
import tensor.Vector;


/**
 * 
 * @author claytonknittel
 * 
 * The Screen is drawn with a right-handed coordinate system
 *
 */

public class Screen {
	
	//private static final long serialVersionUID = -8477179036183785300L;
	
	
	private GLFWWindow window;
	
	private int width, height;
	
	private Frustum frustum;
	
		
	private Drawer drawer;
	
	
	/**
	 * @param width width of the screen (in pixels)
	 * @param height height of the screen (in pixels)
	 * @param viewAngle the minor angle between the two bounding view planes
	 * in the width dimension (in degrees)
	 */
	public Screen(int width, int height, float viewAngle) {
		this.width = width;
		this.height = height;
		this.frustum = new Frustum(width, height, viewAngle, new Vector(Vector.ZERO));
		
		HashMap<Integer, KeyAction> p = new HashMap<Integer, KeyAction>();
		HashMap<Integer, KeyAction> r = new HashMap<Integer, KeyAction>();
		p.put(K.Q, () -> window.quit());
		
		window = new GLFWWindow(width, height, "Planetary Motion", p, r);
		setInputs("pos, norm, color", new int[] {3, 3, 3});
		
		window.setQuitAction(() -> ThreadMaster.quit());
		
		GMath.init(this);
		
		this.drawer = new Drawer(frustum);
	}
	
	private void setInputs(String names, int[] sizes) {
		window.configureInputs(names, sizes);
	}
	
	public void enter() {
		window.enter();
	}
	
	public void setCamera(Vector loc) {
		frustum.add(loc.minus(frustum));
	}
	
	public void setCamera(Vector loc, float phi, float theta, float psi) {
		frustum.add(loc.minus(frustum));
		frustum.rotate((theta - frustum.theta()) / GMath.dt, (phi - frustum.phi()) / GMath.dt, (psi - frustum.psi()) / GMath.dt);
	}
	
	/**
	 * must be called prior to execution and after all <code>Entities</code> have been added
	 */
	public void init() {
		drawer.init();
	}
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}
	
	public Frustum getFustrum() {
		return frustum;
	}
	
	public void add(GLFWRenderable...states) {
		window.add(states);
	}
	
	public void add(LightSource light) {
		window.add(light);
	}
	
	public void add(Locatable camera) {
		window.add(camera);
	}
	
//	public void add(Drawable d) {
//		drawer.add(d);
//	}
	
	public void add(DynamicDrawable d) {
		d.give(this);
	}
	
//	public void remove(Drawable d) {
//		drawer.remove(d);
//	}
	
	public void remove(GLFWRenderable...states) {
		window.remove(states);
	}
	
	public void update() {
		GMath.reset();
		GMath.appendRotation(frustum.getTransformation());
		drawer.update();
	}
	
	public void physUpdate() {
		drawer.physUpdate();
	}
	
	public void draw() {
		update();
		if (window.shouldClose()) {
			window.destroy();
		}
		window.render();
		
//		img.clear();
//		drawer.draw(img);
//		img.draw();
	}
	
	public String getInfo() {
		return frustum.toString() + "\nNumber of bodies: " + drawer.size() + "\nEnergy: " + drawer.energy() + "\nMomentum: " + drawer.momentum();
	}
	
}
