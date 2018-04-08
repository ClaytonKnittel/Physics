package mechanics.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import mechanics.graphics.shapes.ShapeDrawer;

public class ImageGraphics {
	
	private BufferStrategy b;
	
	private BufferedImage img;
	private int[] pixels;
	private final int width, height;
	
	private int bgColor;
	
	private int currentColor;
	
	public ImageGraphics(JFrame parent) {
		parent.createBufferStrategy(2);
		this.b = parent.getBufferStrategy();
		this.width = parent.getWidth();
		this.height = parent.getHeight();
		
		this.bgColor = Color.WHITE.getRGB();
		this.currentColor = Color.BLACK.getRGB();
		
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}
	
	public void setBGColor(Color c) {
		bgColor = c.getRGB();
	}
	
	public void setColor(Color c) {
		currentColor = c.getRGB();
	}
	
	public void set(int x, int y, int val) {
		if (!contains(x, y))
			return;
		pixels[y * width + x] = val;
	}
	
	public boolean contains(int x, int y) {
		return !(x < 0 || x >= width || y < 0 || y >= height);
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = bgColor;
	}
	
	protected void draw() {
		Graphics g = b.getDrawGraphics();
		
		g.drawImage(img, 0, 0, null);
		
		g.dispose();
		b.show();
	}
	
	
	public void drawLine(float x1, float y1, float x2, float y2) {
		ShapeDrawer.drawLine(x1, y1, x2, y2, currentColor, this);
	}
	
	public void drawCircle(float x, float y, float radius) {
		ShapeDrawer.drawCircle(x, y, radius, currentColor, this);
	}
	
}
