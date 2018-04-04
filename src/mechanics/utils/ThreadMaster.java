package mechanics.utils;

public class ThreadMaster extends Thread {
	
	private Updatable u;
	private long delay;
	private long delta;
	private int frames;
	
	public ThreadMaster(Updatable u, float frequency) {
		this.u = u;
		this.delay = (long) Math.round(frequency * 1000);
		this.delta = System.currentTimeMillis();
		this.frames = 0;
	}
	
	public ThreadMaster(Updatable u, float frequency, String name) {
		this(u, frequency);
		this.setName(name);
	}
	
	private int reset() {
		int ret = frames;
		frames = 0;
		return ret;
	}
	
	public int getFrames() {
		return reset();
	}
	
	@Override
	public void run() {
		while (true) {
			if (delta > System.currentTimeMillis()) {
				yield();
				continue;
			}
			delta = System.currentTimeMillis() + delay;
			frames++;
			u.update();
		}
	}
	
	public String toString() {
		return this.getName() + " " + getFrames();
	}
	
}
