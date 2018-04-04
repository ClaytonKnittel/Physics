package mechanics.graphics;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import mechanics.utils.Drawable;
import mechanics.utils.DrawableComparator;

public class DrawableList extends ArrayList<Drawable> {
	private static final long serialVersionUID = -3007148116116931686L;
	
	private DrawableComparator ec;
	
	public DrawableList(Camera c) {
		super();
		ec = new DrawableComparator(c);
	}
	
	private synchronized Drawable getEl(int i) {
		return super.get(i);
	}
	
	public void draw(Graphics g) {
		this.sort(ec);
		for (int i = 0; i < this.size(); i++)
			getEl(i).draw(g);
	}

	
	@Override
	public Iterator<Drawable> iterator() {
		return new Iter();
	}
	
	private class Iter implements Iterator<Drawable> {

		int loc;
		
		public Iter() {
			loc = 0;
		}
		
		@Override
		public boolean hasNext() {
			return loc < size();
		}

		@Override
		public Drawable next() {
			return getEl(loc++);
		}
		
	}
	
}
