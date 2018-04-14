package mechanics.graphics;

import java.util.ArrayList;
import java.util.Iterator;

import mechanics.physics.bodies.Body;
import mechanics.physics.bodies.PMath;
import mechanics.utils.Entity;
import tensor.DVector;

public class EntityList extends ArrayList<Entity> {
	
	private static final long serialVersionUID = -6475954776663998651L;
	
	public EntityList() {
		super();
	}
	
	public void add(Entity...entities) {
		for (Entity e : entities)
			add(e);
	}
	
	public synchronized Entity getEl(int i) {
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
		Entity e;
		for (int i = 0; i < size(); i++) {
			e = getEl(i);
			if (Body.class.isAssignableFrom(e.getClass())) {
				Body b = (Body) e;
				energy += PMath.kineticEnergy(b);
				for (int j = i + 1; j < size(); j++) {
					energy += PMath.potentialEnergy((Body) getEl(j), b);
				}
			}
		}
		return energy;
	}
	
	public DVector momentum() {
		DVector p = new DVector(0, 0, 0);
		Entity e;
		for (int i = 0; i < size(); i++) {
			e = getEl(i);
			if (Body.class.isAssignableFrom(e.getClass())) {
				Body b = (Body) e;
				p.add(b.momentum());
			}
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
