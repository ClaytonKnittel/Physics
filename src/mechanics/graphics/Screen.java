package mechanics.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mechanics.graphics.math.GMath;
import mechanics.input.KeyListen;
import mechanics.utils.Drawable;
import mechanics.utils.DynamicDrawable;
import tensor.Vector;


/**
 * 
 * @author claytonknittel
 * 
 * The Screen is drawn with a right-handed coordinate system
 *
 */

public class Screen extends JFrame {
	
	private static final long serialVersionUID = -8477179036183785300L;

	private int width, height;
	private float viewAngle, depth;
	
	private JPanel panel;
	
	private BufferStrategy b;
	
	private Camera camera;
	
	private Drawer drawer;
	
	private KeyListen keyListener;
	
	private Color bg = new Color(16, 16, 16);
	
	/**
	 * @param width width of the screen (in pixels)
	 * @param height height of the screen (in pixels)
	 * @param viewAngle the minor angle between the two bounding view planes
	 * in the width dimension (in degrees)
	 */
	public Screen(int width, int height, float viewAngle) {
		this.width = width;
		this.height = height;
		this.viewAngle = (float) Math.toRadians(viewAngle) / 2f;
		this.depth = width / (float) Math.tan(this.viewAngle);
		
		this.setVisible(true);
		this.setFocusable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setResizable(false);
		this.setTitle("Planetary Motion");
		
		this.createBufferStrategy(2);
		this.b = this.getBufferStrategy();
		
		panel = new JPanel();
		this.add(panel);
		panel.setFocusable(true);
		panel.requestFocus();
		
		keyListener = new KeyListen();
		keyListener.init(panel);
		
		GMath.init(this);
		
//		sun.java2d.SunGraphics2D gf;
//		sun.java2d.pipe.PixelDrawPipe p;
		
		this.camera = new Camera(new Vector(Vector.ZERO));
		
		this.drawer = new Drawer(camera);
	}
	
	public void setCamera(Vector loc) {
		camera.add(loc.minus(camera));
	}
	
	public void setCamera(Vector loc, float theta, float phi) {
		camera.add(loc.minus(camera));
		camera.rotate((theta - camera.polar()) / GMath.dt, (phi - camera.azimuthal()) / GMath.dt);
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
	
	public float viewAngle() {
		return viewAngle;
	}
	
	public float depth() {
		return depth;
	}
	
	public Camera getCamera() {
		return camera;
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
		keyListener.update(camera);
		GMath.reset();
		GMath.appendRotation(camera.getTransformation());
		drawer.update();
	}
	
	public void physUpdate() {
		drawer.physUpdate();
	}
	
	public void draw() {
		update();
		Graphics g = b.getDrawGraphics();
		
		g.setColor(bg);
		g.fillRect(0, 0, width, height);
		
		drawer.draw(g);
		
		g.dispose();
		b.show();
	}
	
	public String getInfo() {
		return camera.toString() + "\nNumber of bodies: " + drawer.size() + "\nEnergy: " + drawer.energy() + "\nMomentum: " + drawer.momentum();
	}
	
}
