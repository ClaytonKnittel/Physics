package mechanics;

import graphics.Color;
import graphics.entities.LightSource;
import mechanics.graphics.Camera;
import mechanics.graphics.Screen;
import mechanics.graphics.shapes.Axes;
import mechanics.graphics.shapes.Guitar;
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
import tensor.DMatrix;
import tensor.Vector;

public class Main {
	
	public static void main(String args[]) {
		Screen s = new Screen(800, 600, 72, "starfield");
//		s.setCamera(new Vector(0, 0, 0), 0, 0, 0);
		
		s.setCamera(new Vector(36.8f, 23.8f, 8.84f), .398f, 5.975f, 0);
//		Setup.ELLIPTICAL.initialize(s, 1);
//		Setup.ELLIPTICAL.showExactSolution(s, 300, "red");
		
//		Setup.PARABOLIC.initialize(s, 1);
//		Setup.PARABOLIC.showExactSolution(s, 300, "red");
		
//		Setup.HYPERBOLIC.initialize(s, 1);
//		Setup.HYPERBOLIC.showExactSolution(s, 1200, "red");
		
//		Setup.CIRCULAR.initialize(s, 1);
//		Setup.CIRCULAR.showExactSolution(s, 300, "red");
		
//		Setup.POLAR.initialize(s, 1);
//		Setup.POLAR.showExactSolution(s, 300, "red");

//		s.setCamera(new Vector(300, 0, 3000));
//		s.setCamera(new Vector(1000, -40, 100));
//		Setup.EARTH_MOON.initialize(s, 1);
//		Setup.EARTH_MOON.showExactSolution(s, 300, "red");
		
		
		s.setCamera(new Vector(321.154f, 292.68f, 808.924f));
		Setup.INNER_SYSTEM.initialize(s, 1);
//		Setup.INNER_SYSTEM.showExactSolutions(s, 300, new String[] {"red", "red", "red", "red"}, 0, new int[] {1, 2, 3, 4});
		
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
				return new Color(255, 255, 255);
			}
			public float brightness() {
				return 0;
			}
		});
		
//		Rectangle r = new Rectangle(new DVector(10, 0, -40), 10, 10, 20, 30, "blue");
//		r.setAngularVelocity(new DVector(0.6, 0.0, .5));
//		r.setAngles(0, 0, 0);
//		r.setLightAttribs(.8f, 12);
//		s.add(r);
		
//		Guitar g = new Guitar(1f, Color.matte_blue);
//		s.add(g);
		
//		Axes a = new Axes(Vector.ZERO, 10, "red", "green", "blue");
//		s.add(a);
		
		
		Planet p = new Planet(new DVector(10, 20, 0), 900, 6, "red");
		p.setLightAttribs(.7f, 14);
//		s.add(p);
		
		Planet p2 = new Planet(new DVector(100, 20, 0), 200, 5, "blue");
		p2.setLightAttribs(.2f, 4);
//		s.add(p2);
		
		Planet p3 = new Planet(new DVector(100, 70, 0), 200, 5, "blue");
		p3.setLightAttribs(.3f, 5);
//		s.add(p3);
		
		//System.out.println(PMath.dW(0, 0, 0, 10, 20, 30, new DVector(1, 0, 0), new DVector(0, 0, 0)));

//		LineSegment l = new LineSegment(r.pos().toVector(), new Vector(10, 20, 30), 1, "cyan");		
//		LineSegment l2 = new LineSegment(r.pos().toVector(), new Vector(10, 20, 30), 1, Color.yellow);
//		s.add(l);
		
//		s.add(l2);
		
		s.init();
		s.enter();
		
		
		ThreadMaster graphics = new ThreadMaster(() -> {
			s.draw();
		}, Screen.dt, true, "graphics");
		ThreadMaster physics = new ThreadMaster(() -> {
			s.physUpdate();
			PMath.next();
//			l.setDir(r.angularVelocity().toVector(), 40);
//			l.setEnd(r.angularVelocity().toVector().times(80).plus(l.start()));
//			l2.setEnd(r.angularMomentum().toVector().times(1.4f).plus(l.start()));
//			l.setEnd(Matrixd.toSpaceFrame(r.phi(), r.theta(), r.psi()).multiply(new DVector(40, 40, 0)).toVector().plus(l.start()));
		}, PMath.dt, false, "physics");
		
		ThreadMaster info = new ThreadMaster(() -> {
			System.out.println(s.getInfo());
			System.out.println(graphics);
			System.out.println(physics);
//			System.out.println(r.phi() + " " + r.theta() + " " + r.psi());
//			System.out.println(r.angularVelocity().mag());
		}, 1, false, "info");

		physics.start();
		info.start();
		
		// beacuse GLFW needs to be run in main thread
		graphics.run();
	}
	
}
