package mechanics2D;

import java.awt.Color;

import mechanics2D.physics.PMath;
import mechanics.utils.ThreadMaster;
import mechanics2D.graphics.Screen;
import mechanics2D.physics.Ball;
import mechanics2D.physics.Box;
import mechanics2D.tests.ConditionalDrawer;
import tensor.DVector2;

public class Main {
	
	public static void main(String args[]) {
		Screen s = new Screen(600, 500);
		
//		Ball b = new Ball(300, 200, 0, -60, 20, 20, Color.RED);
//		s.add(b);
//		
//		Ball b2 = new Ball(100, 200, 0, 60, 20, 20, Color.GREEN);
//		s.add(b2);
		
		
		Box box = new Box(200, 250, 0, 50, 20, 100, 40, Color.ORANGE);
		box.setW(0.1);
		
		Box box2 = new Box(400, 250, 0, -50, 20, 30, 100, Color.BLUE);
		box2.setW(0);
		
		ConditionalDrawer d = new ConditionalDrawer(v -> {
			DVector2 e = box.toBodyFrame(v);
			if (Math.abs(e.x()) < box.width() / 2 && Math.abs(e.y()) < box.height() / 2)
				return true;
			e = box2.toBodyFrame(v);
			if (Math.abs(e.x()) < box2.width() / 2 && Math.abs(e.y()) < box2.height() / 2)
				return true;
			return false;
		}, 4, 600, 500, Color.BLACK);

		ConditionalDrawer d2 = new ConditionalDrawer(v -> {
			box2.setPos(v);
			return box.shape().colliding(box2.shape(), false);
		}, 4, 600, 500, Color.BLACK);
		
		s.add(box);
		s.add(box2);
		//s.add(d2);
		
		ThreadMaster t1 = new ThreadMaster(() -> {
			s.render();
		}, Screen.dt, false, "graphics");
		ThreadMaster t2 = new ThreadMaster(() -> {
			s.physUpdate();
		}, PMath.dt, false, "physics");
		ThreadMaster t3 = new ThreadMaster(() -> {
			System.out.println(box.energy() + box2.energy() + PMath.gPotential(box, box2));
		}, 1, false, "info");
		
		t2.setSpeed(1f);
		
		t1.start();
		t2.start();
		t3.start();
	}
	
	
	
}
