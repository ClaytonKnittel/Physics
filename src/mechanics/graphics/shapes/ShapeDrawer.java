package mechanics.graphics.shapes;

import mechanics.graphics.ImageGraphics;

public class ShapeDrawer {
	
	private static final float threshold = .1f;
	
	private static int abs(int a) {
		return a < 0 ? -a : a;
	}
	
	private static float abs(float a) {
		return a < 0 ? -a : a;
	}
	
	public static void drawLine(float x1, float y1, float x2, float y2, int color, ImageGraphics g) {
		if (!shouldDrawLine(x1, y1, x2, y2, g.width() / 2, g.height() / 2))
			return;
		
		float dx = x2 - x1;
		float dy = y2 - y1;
		
		if (Math.abs(dx) < threshold && Math.abs(dy) < threshold)
			return;
		
		int len;
		
		if (Math.abs(dx) > Math.abs(dy)) {
			len = abs((int) x2 - (int) x1) + 1;
			dy /= abs(dx);
			dx = dx > 0 ? 1 : -1;
		} else {
			len = abs((int) y2 - (int) y1) + 1;
			dx /= abs(dy);
			dy = dy > 0 ? 1 : -1;
		}
		
		int i = 0;
		for (; i < len; i++) {
			g.set((int) (x1 + i * dx), (int) (y1 + i * dy), color);
		}
	}
	
	private static boolean shouldDrawLine(float x1, float y1, float x2, float y2, float hWidth, float hHeight) {
		if (Math.abs(x1 - hWidth) >= hWidth && Math.abs(x2 - hWidth) >= hWidth)
			return false;
		if (Math.abs(y1 - hHeight) >= hHeight && Math.abs(y2 - hHeight) >= hHeight)
			return false;
		return true;
	}
	
	public static void drawCircle(float x, float y, float radius, int color, ImageGraphics g) {
		float r = radius * radius;
		for (float dx = x - radius; dx <= x + radius; dx++) {
			for (float dy = y - radius; dy <= y + radius; dy++) {
				if (sq(dx - x) + sq(dy - y) <= r)
					g.set((int) dx, (int) dy, color);
			}
		}
	}
	
	private static float sq(float f) {
		return f * f;
	}
	
}
