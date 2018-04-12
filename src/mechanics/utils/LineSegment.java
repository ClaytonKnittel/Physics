package mechanics.utils;

import graphics.Color;
import graphics.entities.GLFWRenderable;
import numbers.cliffordAlgebras.Quaternion;
import tensor.Matrix4;
import tensor.Vector;

public class LineSegment implements GLFWRenderable {
	
	private Vector start, end;
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
	public void update() {
		
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
