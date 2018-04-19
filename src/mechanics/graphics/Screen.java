package mechanics.graphics;

import java.util.HashMap;

import graphics.GLFWWindow;
import graphics.entities.GLFWRenderable;
import graphics.entities.LightSource;
import graphics.input.K;
import graphics.input.KeyAction;
import graphics.input.Locatable;
import mechanics.utils.DynamicDrawable;
import mechanics.utils.Entity;
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
	
	private Camera camera;
	
	public static float dt = .02f;
		
	private PhysicsBodies bodies;
	
	
	/**
	 * @param width width of the screen (in pixels)
	 * @param height height of the screen (in pixels)
	 * @param viewAngle the minor angle between the two bounding view planes
	 * in the width dimension (in degrees)
	 */
	public Screen(int width, int height, float viewAngle) {
		this.width = width;
		this.height = height;
		this.camera = new Camera(new Vector(Vector.ZERO));
		
		HashMap<Integer, KeyAction> pressed = new HashMap<Integer, KeyAction>();
		HashMap<Integer, KeyAction> released = new HashMap<Integer, KeyAction>();
		setKeybindings(pressed, released);
		
		window = new GLFWWindow(width, height, "Planetary Motion", pressed, released);
		setInputs("pos, norm, texcoord", new int[] {3, 3, 2});
		
		window.setQuitAction(() -> ThreadMaster.quit());
		
		this.bodies = new PhysicsBodies();
		
		add(camera);
	}
	
	private boolean shift;
	
	private void setKeybindings(HashMap<Integer, KeyAction> p, HashMap<Integer, KeyAction> r) {
		shift = false;
		p.put(K.ONE, () -> window.quit());
		
		p.put(K.SPACE, () -> shift = true);
		p.put(K.J, () -> camera.toggleBoost());
		
		p.put(K.W, () -> {
			if (shift)
				camera.setDY(Camera.upV);
			else
				camera.setDZ(-Camera.forwardV);
		});
		p.put(K.S, () -> {
			if (shift)
				camera.setDY(-Camera.upV);
			else
				camera.setDZ(Camera.backwardV);
		});
		
		p.put(K.A, () -> {
			if (shift)
				camera.setDX(-Camera.sidewaysV);
			else
				camera.setDPhi(Camera.turnV);
		});
		p.put(K.D, () -> {
			if (shift)
				camera.setDX(Camera.sidewaysV);
			else
				camera.setDPhi(-Camera.turnV);
		});
		
		p.put(K.K, () -> {
			camera.setDTheta(Camera.turnV);
		});
		p.put(K.M, () -> {
			camera.setDTheta(-Camera.turnV);
		});
		
		p.put(K.Q, () -> {
			camera.setDPsi(Camera.turnV);
		});
		p.put(K.E, () -> {
			camera.setDPsi(-Camera.turnV);
		});
		
		r.put(K.SPACE, () -> shift = false);
		
		r.put(K.W, () -> {
			camera.setDZ(0);
			camera.setDY(0);
		});
		r.put(K.S, () -> {
			camera.setDZ(0);
			camera.setDY(0);
		});
		
		r.put(K.A, () -> {
			camera.setDPhi(0);
			camera.setDX(0);
		});
		r.put(K.D, () -> {
			camera.setDPhi(0);
			camera.setDX(0);
		});
		
		r.put(K.K, () -> {
			camera.setDTheta(0);
		});
		r.put(K.M, () -> {
			camera.setDTheta(0);
		});
		
		r.put(K.Q, () -> {
			camera.setDPsi(0);
		});
		r.put(K.E, () -> {
			camera.setDPsi(0);
		});
	}
	
	private void setInputs(String names, int[] sizes) {
		window.configureInputs(names, sizes);
	}
	
	public void enter() {
		window.enter();
	}
	
	public void setCamera(Vector loc) {
		camera.add(loc.minus(camera));
	}
	
	public void setCamera(Vector loc, float phi, float theta, float psi) {
		camera.add(loc.minus(camera));
		camera.setAngles(phi, theta, psi);
	}
	
	/**
	 * must be called prior to execution and after all <code>Entities</code> have been added
	 */
	public void init() {
		bodies.init();
	}
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void add(GLFWRenderable...states) {
		window.add(states);
	}
	
	public void add(Entity...entities) {
		bodies.add(entities);
		for (Entity e : entities)
			add((GLFWRenderable) e);
	}
	
	public void add(LightSource light) {
		window.add(light);
	}
	
	public void add(Locatable camera) {
		window.add(camera);
	}
	
	public void add(DynamicDrawable d) {
		d.give(this);
	}
	
	public void remove(GLFWRenderable...states) {
		window.remove(states);
	}
	
	public void physUpdate() {
		bodies.physUpdate();
	}
	
	public void draw() {
		if (window.shouldClose()) {
			window.destroy();
		}
		window.render();
	}
	
	public String getInfo() {
		return camera.toString() + "\nNumber of bodies: " + bodies.size() + "\nEnergy: " + bodies.energy() + "\nMomentum: " + bodies.momentum();
	}
	
	public String getBodies() {
		return bodies.toString();
	}
	
}
