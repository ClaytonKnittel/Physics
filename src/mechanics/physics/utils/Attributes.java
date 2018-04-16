package mechanics.physics.utils;

public class Attributes {
	
	private boolean[] attributes;
	
	public Attributes(Attribute... attributes) {
		this.attributes = new boolean[Attribute.values().length];
		for (Attribute a : attributes)
			this.attributes[a.ordinal()] = true;
	}
	
	public boolean is(Attribute a) {
		return attributes[a.ordinal()];
	}
	
	public void set(Attribute a, boolean condition) {
		attributes[a.ordinal()] = condition;
	}
	
}
