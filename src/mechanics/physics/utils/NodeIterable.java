package mechanics.physics.utils;

import java.util.Iterator;

import mechanics.physics.bodies.Body;
import mechanics.physics.utils.NodeIterable.Node;

public abstract class NodeIterable implements Iterable<Node> {

	private int size;
	
	private Node first, last;

	
	public NodeIterable() {
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public void add(Body b) {
		if (size == 0) {
			first = new Node(b);
			last = first;
		}
		else
			last = last.add(new BodyCollisionTimer(b));
		size++;
	}
	
	public void removeLast() {
		remove(last);
		size--;
	}
	
	protected void remove(Node n) {
		size--;
		if (n.prev == null) {
			if (n.next == null) {
				first = null;
				last = null;
				return;
			}
			first = n.next;
			first.prev = null;
			return;
		}
		if (n.next == null) {
			last = n.prev;
			last.next = null;
		}
		else
			n.prev.next = n.next;
		return;
	}

	@Override
	public Iterator<Node> iterator() {
		return new Iter();
	}
	
	protected class Node {
		BodyCollisionTimer el;
		Node prev;
		Node next;
		
		Node(BodyCollisionTimer b) {
			this.el = b;
		}
		
		Node(Body b) {
			this(new BodyCollisionTimer(b));
		}
		
		private Node(BodyCollisionTimer b, Node previous) {
			this(b);
			prev = previous;
		}
		
		Node add(BodyCollisionTimer b) {
			Node n = new Node(b, this);
			return next = n;
		}
	}
	
	private class Iter implements Iterator<Node> {

		int loc;
		Node current;
		
		public Iter() {
			loc = 0;
		}
		
		@Override
		public boolean hasNext() {
			return loc < size;
		}

		@Override
		public Node next() {
			loc++;
			if (current == null)
				return current = first;
			return current = current.next;
		}
		
	}
}
