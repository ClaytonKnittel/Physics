package mechanics.graphics;

import java.awt.Graphics;

import mechanics.utils.Drawable;
import mechanics.utils.Entity;
import tensor.DVector;

public class Drawer {
	
	private EntityList entities;
	private DrawableList objects;
	
	public Drawer(Camera camera) {
		this.entities = new EntityList();
		objects = new DrawableList(camera);
	}
	
	public void init() {
		this.entities.init();
	}
	
	public int size() {
		return objects.size();
	}
	
	public float energy() {
		return entities.energy();
	}
	
	public DVector momentum() {
		return entities.momentum();
	}
	
	public void add(Drawable d) {
		if (isEntity(d))
			entities.add((Entity) d);
		objects.add(d);
	}
	
	private static boolean isEntity(Object o) {
		return Entity.class.isAssignableFrom(o.getClass());
	}
	
	public void remove(Drawable d) {
		if (d == null)
			return;
		if (isEntity(d))
			entities.remove((Entity) d);
		objects.remove(d);
	}
	
	public void update() {
		for (Drawable d : objects)
			d.update();
	}
	
	public void physUpdate() {
		entities.interact();
		for (Entity e : entities)
			e.physUpdate();
	}
	
	public void draw(Graphics g) {
		objects.draw(g);
	}
	
}
