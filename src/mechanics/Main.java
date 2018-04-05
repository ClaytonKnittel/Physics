package mechanics;

import java.awt.Color;

import mechanics.graphics.Screen;
import mechanics.graphics.math.GMath;
import mechanics.physics.PMath;
import mechanics.utils.ThreadMaster;

public class Main {
	
	public static void main(String args[]) {
		Screen s = new Screen(800, 600, 72);
		
		Setup.CIRCULAR.initialize(s, 1);
		Setup.CIRCULAR.showExactSolution(s, 300, Color.ORANGE);
		
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
