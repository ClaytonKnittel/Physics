package mechanics.utils;

import graphics.Color;
import graphics.entities.GLFWRenderable;
import mechanics.graphics.Camera;
import mechanics.graphics.ImageGraphics;
import mechanics.graphics.math.GMath;
import numbers.cliffordAlgebras.Quaternion;
import tensor.Matrix4;
import tensor.Vector;

public class LineSegment implements GLFWRenderable {
	
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
	public void update() {
		h = true;
		tStart = GMath.transform(start);
		tEnd = GMath.transform(end);
	}
	
	public String toString() {
		return start + "\t" + end + "\t" + color;
	}

	@Override
	public Matrix4 model() {
		return new Matrix4();
	}

	@Override
	public float[] modelData() {
		return null;
	}
	
	
}
