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
	
	public LineSegment(Vector start, Vector end, Color color) {
		this.start = new Quaternion(start);
		this.end = new Quaternion(end);
		this.color = color;
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
		tStart = GMath.transform(start);
		tEnd = GMath.transform(end);
	}

	@Override
	public float distance(Camera c) {
		return Math.abs((tStart.z() + tEnd.z()) / 2);
	}
	
	
}
