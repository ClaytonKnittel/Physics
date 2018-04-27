package mechanics2D;

import java.awt.Color;

import mechanics.physics.bodies.PMath;
import mechanics.utils.ThreadMaster;
import mechanics2D.graphics.Screen;
import mechanics2D.physics.Ball;

public class Main {
	
	public static void main(String args[]) {
		Screen s = new Screen(600, 500);
		
		Ball b = new Ball(300, 200, 0, -60, 20, 20, Color.RED);
		s.add(b);
		
		Ball b2 = new Ball(100, 200, 0, 60, 20, 20, Color.GREEN);
		s.add(b2);
		
		ThreadMaster t1 = new ThreadMaster(() -> {
			s.render();
		}, Screen.dt, false, "graphics");
		ThreadMaster t2 = new ThreadMaster(() -> {
			s.physUpdate();
		}, PMath.dt, false, "physics");
		
		t1.start();
		t2.start();
	}
	
	
	
}
