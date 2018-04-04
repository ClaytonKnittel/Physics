package mechanics.graphics;

import java.util.ArrayList;
import java.util.Iterator;

import mechanics.physics.Body;
import mechanics.physics.PMath;
import mechanics.utils.Entity;
import tensor.Vector;

public class EntityList extends ArrayList<Entity> {
	
	private static final long serialVersionUID = -6475954776663998651L;
	
	public EntityList() {
		super();
	}
	
	private synchronized Entity getEl(int i) {
		return super.get(i);
	}
	
	public void init() {
		interact();
		for (int i = 0; i < this.size(); i++)
			getEl(i).init();
	}
	
	public void interact() {
		for (int i = 0; i < size() - 1; i++) {
			for (int j = i + 1; j < size(); j++)
				getEl(i).interact(getEl(j));
		}
	}
	
	public float energy() {
		float energy = 0;
		for (int i = 0; i < size(); i++) {
			Body b = (Body) getEl(i);
			energy += PMath.kineticEnergy(b);
			for (int j = i + 1; j < size(); j++) {
				energy += PMath.potentialEnergy((Body) getEl(j), b);
			}
		}
		return energy;
	}
	
	public Vector momentum() {
		Vector p = new Vector(0, 0, 0);
		for (int i = 0; i < size(); i++) {
			p.add(((Body) getEl(i)).momentum());
		}
		return p;
	}
	
	@Override
	public Iterator<Entity> iterator() {
		return new Iter();
	}
	
	private class Iter implements Iterator<Entity> {

		int loc;
		
		public Iter() {
			loc = 0;
		}
		
		@Override
		public boolean hasNext() {
			return loc < size();
		}

		@Override
		public Entity next() {
			return getEl(loc++);
		}
		
	}
	
}
