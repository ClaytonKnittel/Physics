package mechanics.graphics;

import graphics.GLFWWindow;

//import java.awt.Color;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;

import mechanics.graphics.math.GMath;
// import mechanics.input.KeyListen;
import mechanics.utils.Drawable;
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
	
	private Fustrum fustrum;
	
	//private JPanel panel;
		
	private Drawer drawer;
	
	//private ImageGraphics img;
	
	//private KeyListen keyListener;
	
	/**
	 * @param width width of the screen (in pixels)
	 * @param height height of the screen (in pixels)
	 * @param viewAngle the minor angle between the two bounding view planes
	 * in the width dimension (in degrees)
	 */
	public Screen(int width, int height, float viewAngle) {
		this.width = width;
		this.height = height;
		this.fustrum = new Fustrum(width, height, viewAngle, new Vector(Vector.ZERO));
		
		window = new GLFWWindow();
		window.loadShaders("/Users/claytonknittel/git/Physics/src/mechanics/graphics/opengl/vertexShader",
				"/Users/claytonknittel/git/Physics/src/mechanics/graphics/opengl/fragmentShader");
		window.enter();
		window.setQuitAction(() -> ThreadMaster.quit());
		
		GMath.init(this);
				
		this.drawer = new Drawer(fustrum);
	}
	
	public void setCamera(Vector loc) {
		fustrum.add(loc.minus(fustrum));
	}
	
	public void setCamera(Vector loc, float theta, float phi) {
		fustrum.add(loc.minus(fustrum));
		fustrum.rotate((theta - fustrum.polar()) / GMath.dt, (phi - fustrum.azimuthal()) / GMath.dt);
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
	
	public Fustrum getFustrum() {
		return fustrum;
	}
	
	public void add(Drawable d) {
		drawer.add(d);
	}
	
	public void add(DynamicDrawable d) {
		d.give(this);
	}
	
	public void remove(Drawable d) {
		drawer.remove(d);
	}
	
	public void update() {
		//keyListener.update(fustrum);
		GMath.reset();
		GMath.appendRotation(fustrum.getTransformation());
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
		return fustrum.toString() + "\nNumber of bodies: " + drawer.size() + "\nEnergy: " + drawer.energy() + "\nMomentum: " + drawer.momentum();
	}
	
}
