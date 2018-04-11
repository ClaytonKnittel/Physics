package mechanics;

import arrays.AO;
import graphics.Color;
import graphics.RigidState;
import graphics.models.OBJLoader;
import mechanics.graphics.Screen;
import mechanics.graphics.math.GMath;
import mechanics.physics.PMath;
import mechanics.physics.Planet;
import mechanics.utils.ThreadMaster;
import tensor.DVector;
import tensor.Vector;

public class Main {
	
	public static void main(String args[]) {
		
		Screen s = new Screen(800, 600, 72);

		
		//s.setCamera(new Vector(0, 100, 100), .4f, 0);
//		Setup.ELLIPTICAL.initialize(s, 1);
//		Setup.ELLIPTICAL.showExactSolution(s, 300, Color.CYAN);
		
//		Setup.PARABOLIC.initialize(s, 1);
//		Setup.PARABOLIC.showExactSolution(s, 300, Color.CYAN);
		
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
		
		
//		s.setCamera(new Vector(0, 0, 10000));
//		Setup.INNER_SYSTEM.initialize(s, 1);
//		Setup.INNER_SYSTEM.showExactSolutions(s, 300, new Color[] {Color.BLUE, Color.GRAY, Color.GREEN, Color.DARK_GRAY}, 0, new int[] {1, 2, 3, 4});
		
//		int i = 20;
//		for (int x = -i; x <= i; x += 60) {
//			for (int y = -i; y <= i; y += 60) {
//				for (int z = -i; z <= i; z += 60) {}
//					//s.add(new Planet(new Vector(x, y, z - 100), 60, 1, new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256))));
//			}
//		}
		
		float[] f = new float[] {-.6f, -.4f, -1.3f, 1, 1, 1,
								 .6f, -.4f, -1.3f, 0, 0, 1,
								 0, .5f, -1.3f, 0, 1, 0};
		float[] ff = new float[] {-1f, -.4f, -1, 0, 0, 0,
				 .4f, -.4f, -1, 0, 1, 0,
				 -.2f, .5f, -1, 1, 0, 0};
		
		s.add(new Planet(new DVector(0, 0, 0), 1, 1, Color.cyan));
		
		s.init();
		s.enter();
		
		
		ThreadMaster graphics = new ThreadMaster(() -> {
			s.draw();
			GMath.next();
		}, GMath.dt, true, "graphics");
		
		ThreadMaster physics = new ThreadMaster(() -> {
			s.physUpdate();
			PMath.next();
		}, PMath.dt, false, "physics");
		
		ThreadMaster info = new ThreadMaster(() -> {
			System.out.println(s.getInfo());
			System.out.println(graphics);
			System.out.println(physics);
		}, 1, false);
		
		physics.start();
		info.start();
		
		// beacuse GLFW needs to be run in main thread
		graphics.run();
	}
	
}
