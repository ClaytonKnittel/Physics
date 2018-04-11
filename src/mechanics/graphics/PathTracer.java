package mechanics.graphics;

import graphics.Color;
import mechanics.utils.DynamicDrawable;
import mechanics.utils.FiniteModularList;
import mechanics.utils.LineSegment;
import tensor.Vector;

public class PathTracer implements DynamicDrawable {
	
	private FiniteModularList<LineSegment> lines;
	private float precision;
	private Color color;
	private Screen screen;
	
	public PathTracer(float precision, int size, Color color) {
		this.precision = precision * precision;
		this.color = color;
		lines = new FiniteModularList<LineSegment>(LineSegment.class, size);
	}
	
	public void update() {
		for (LineSegment l : lines)
			l.update();
	}
	
	public void give(Screen s) {
		screen = s;
	}
	
	public void recordLocation(Vector pos) {
		if (lines.isEmpty())
			lines.add(new LineSegment(pos, pos, color));
		else if (shouldAppend(pos)) {
			screen.remove(lines.add(new LineSegment(lines.getLast().end(), pos, color)));
			screen.add(lines.getLast());
		} else
			return;
		// ensure that last Line added will have its most recent transformed position saved
		lines.getLast().update();
	}
	
	private boolean shouldAppend(Vector pos) {
		return lines.getLast().end().minus(pos).mag2() >= precision;
	}
	
}
