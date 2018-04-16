package mechanics;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import graphics.Color;
import graphics.entities.LightSource;
import mechanics.graphics.Camera;
import mechanics.graphics.Screen;
import mechanics.graphics.shapes.Axes;
import mechanics.graphics.shapes.LineSegment;
import mechanics.graphics.shapes.Sphere;
import mechanics.physics.ExactSolution;
import mechanics.physics.bodies.PMath;
import mechanics.physics.bodies.Planet;
import mechanics.physics.bodies.Rectangle;
import mechanics.physics.utils.Attribute;
import mechanics.utils.ThreadMaster;
import tensor.DVector;
import tensor.Matrix;
import tensor.Matrixd;
import tensor.Vector;

public class Main {
	
	public static void main(String args[]) {
		
		Screen s = new Screen(800, 600, 72);
//		s.setCamera(new Vector(0, 0, 0), 0, 0, 0);
		
		s.setCamera(new Vector(36.8f, 23.8f, 8.84f), .398f, 5.975f, 0);
//		Setup.ELLIPTICAL.initialize(s, 1);
		//Setup.ELLIPTICAL.showExactSolution(s, 300, Color.cyan);
		
//		Setup.PARABOLIC.initialize(s, 1);
//		Setup.PARABOLIC.showExactSolution(s, 300, Color.cyan);
		
//		Setup.HYPERBOLIC.initialize(s, 1);
//		Setup.HYPERBOLIC.showExactSolution(s, 1200, Color.CYAN);
		
//		Setup.CIRCULAR.initialize(s, 1);
//		Setup.CIRCULAR.showExactSolution(s, 300, Color.CYAN);
		
//		Setup.POLAR.initialize(s, 1);
//		Setup.POLAR.showExactSolution(s, 300, Color.CYAN);

//		s.setCamera(new Vector(300, 0, 3000));
//		s.setCamera(new Vector(1000, -40, 100));
//		Setup.EARTH_MOON.initialize(s, 1);
//		Setup.EARTH_MOON.showExactSolution(s, 300, Color.CYAN);
		
		
//		s.setCamera(new Vector(0, 0, 1000));
//		Setup.INNER_SYSTEM.initialize(s, 1);
//		Setup.INNER_SYSTEM.showExactSolutions(s, 300, new Color[] {Color.BLUE, Color.GRAY, Color.GREEN, Color.DARK_GRAY}, 0, new int[] {1, 2, 3, 4});
		
//		int i = 20;
//		for (int x = -i; x <= i; x += 60) {
//			for (int y = -i; y <= i; y += 60) {
//				for (int z = -i; z <= i; z += 60) {}
//					//s.add(new Planet(new Vector(x, y, z - 100), 60, 1, new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256))));
//			}
//		}
		
		s.add(new LightSource() {
			public Vector pos() {
				return new Vector(50, 0, 50);
			}
			public Color color() {
				return Color.white;
			}
			public float brightness() {
				return 0;
			}
		});
		
		Rectangle r = new Rectangle(new DVector(10, 0, -40), 1, 10, 20, 30, Color.blue);
		r.setAngularVelocity(new DVector(0.8, 0, 0));
		r.setAngles(0, 0, 0);
		s.add(r);
		
		Axes a = new Axes(Vector.ZERO, 10, Color.red, Color.green, Color.blue);
		s.add(a);
		
		
//		Planet p = new Planet(new DVector(10, 20, 0), 3, 6, Color.red);
//		p.setAttribute(Attribute.Massive, false);
//		p.setAttribute(Attribute.Physical, false);
//		s.add(p);
		
		LineSegment l = new LineSegment(r.pos().toVector(), new Vector(10, 20, 30), 1, Color.cyan);
		s.add(l);
		
		s.init();
		s.enter();
		
		System.out.println(Matrixd.toSpaceFrame(.3, .4, .1).multiply(Matrixd.toSpaceFrame(.3, .4, .1)).roundString());
		
		ThreadMaster graphics = new ThreadMaster(() -> {
			s.draw();
		}, Screen.dt, true, "graphics");
		ThreadMaster physics = new ThreadMaster(() -> {
			s.physUpdate();
			PMath.next();
			//l.setDir(r.angularVelocity().toVector(), 40);
			l.setEnd(Matrixd.toRotatingFrame(r.phi(), r.theta(), r.psi()).multiply(new DVector(40, 40, 0)).toVector().plus(l.start()));
		}, PMath.dt, false, "physics");
		
		ThreadMaster info = new ThreadMaster(() -> {
			System.out.println(s.getInfo());
			System.out.println(graphics);
			System.out.println(physics);
			System.out.println(r.phi() + " " + r.theta() + " " + r.psi());
		}, 1, false, "info");

		physics.start();
		info.start();
		
		// beacuse GLFW needs to be run in main thread
		graphics.run();
	}
	
}
