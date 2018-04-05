package mechanics.utils;

import java.awt.Color;
import java.awt.Graphics;

import mechanics.graphics.Camera;
import mechanics.graphics.math.GMath;
import numbers.cliffordAlgebras.Quaternion;
import tensor.Vector;

public class LineSegment implements Drawable {
	
	private Quaternion start, end;
	private Quaternion tStart, tEnd;
	private Color color;
	
	private boolean h;
	
	public LineSegment(Vector start, Vector end, Color color) {
		this.start = new Quaternion(start);
		this.end = new Quaternion(end);
		this.color = color;
		h = false;
	}
	
	public Vector start() {
		return start;
	}
	
	public Vector end() {
		return end;
	}
	
	@Override
	public void draw(Graphics g) {
		if (tStart.z() > 0 || tEnd.z() > 0)
			return;
		g.setColor(color);
		g.drawLine(GMath.screenX(tStart), GMath.screenY(tStart), GMath.screenX(tEnd), GMath.screenY(tEnd));
	}

	@Override
	public void update() {
		h = true;
		tStart = GMath.transform(start);
		tEnd = GMath.transform(end);
	}
	
	public String toString() {
		return start + "\t" + end + "\t" + color;
	}

	@Override
	public float distance(Camera c) {
		if (!h)
			System.out.println(this);
		return Math.abs((tStart.z() + tEnd.z()) / 2);
	}
	
	
}
