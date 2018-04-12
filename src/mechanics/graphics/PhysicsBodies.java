package mechanics.graphics;

import mechanics.utils.Entity;
import tensor.DVector;

public class PhysicsBodies {
	
	private EntityList entities;
	
	public PhysicsBodies() {
		this.entities = new EntityList();
	}
	
	public void init() {
		this.entities.init();
	}
	
	public int size() {
		return entities.size();
	}
	
	public float energy() {
		return entities.energy();
	}
	
	public DVector momentum() {
		return entities.momentum();
	}
	
	public void add(Entity... entities) {
		this.entities.add(entities);
	}
	
	public void remove(Entity d) {
		if (d == null)
			return;
		entities.remove(d);
	}
	
	public void physUpdate() {
		entities.interact();
		for (Entity e : entities)
			e.physUpdate();
	}
	
	public String toString() {
		return entities.toString();
	}
	
}
