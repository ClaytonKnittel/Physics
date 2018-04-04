package mechanics;

import java.awt.Color;

import mechanics.graphics.Screen;
import mechanics.graphics.math.GMath;
import mechanics.physics.ExactSolution;
import mechanics.physics.PMath;
import mechanics.physics.Planet;
import mechanics.utils.ThreadMaster;
import tensor.Vector;

public class Main {
	
	public static void main(String args[]) {
		Screen s = new Screen(800, 600, 72);
		
		//Planet p = new Planet(new Vector(10, 0, -8000), new Vector(0, 0, 1000), 10, 3, new Color(0, 210, 50));
		Planet p = new Planet(new Vector(30, 0, -100), new Vector(0, 0, -80), 10, 3, new Color(0, 210, 50));
		p.lineTrace(s, 1, Color.RED);
		
		Planet q = new Planet(new Vector(0, 0, -100), new Vector(0, 0, .08f), 10000, 5, new Color(100, 200, 230));
		
		s.add(q);
		//s.add(new Planet(new Vector(8.1f, 0, -100), new Vector(0, 0, 0), 10, 3, new Color(220, 200, 110)));
		s.add(p);
//		s.add(new Planet(new Vector(-33, 0, -80), new Vector(0, 15, 0), 10, 3, new Color(220, 0, 110)));
		
		ExactSolution e = new ExactSolution(p, q, 200, Color.YELLOW);
		s.add(e);
		e.calculatePath();
		
//		int i = 20;
//		for (int x = -i; x <= i; x += 60) {
//			for (int y = -i; y <= i; y += 60) {
//				for (int z = -i; z <= i; z += 60) {}
//					//s.add(new Planet(new Vector(x, y, z - 100), 60, 1, new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256))));
//			}
//		}
		
		s.init();
		
		ThreadMaster graphics = new ThreadMaster(() -> {
			s.draw();
			GMath.next();
		}, GMath.dt, "graphics");
		
		ThreadMaster physics = new ThreadMaster(() -> {
			s.physUpdate();
			PMath.next();
		}, PMath.dt, "physics");
		
		ThreadMaster info = new ThreadMaster(() -> {
			System.out.println(s.getInfo());
			System.out.println(graphics);
			System.out.println(physics);
		}, 1);
		
		graphics.start();
		physics.start();
		info.start();
	}
	
}
