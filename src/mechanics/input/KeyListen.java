package mechanics.input;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;

import mechanics.graphics.Camera;

public class KeyListen {
	
	private boolean[] keys = new boolean[222];
	
	private Map<Integer, mechanics.input.Action> keyActions;
	
	static {
	}
	
	public void update(Camera c) {
		for (Map.Entry<Integer, mechanics.input.Action> key : keyActions.entrySet()) {
			if (keys[key.getKey()]) {
				key.getValue().act(c);
			}
		}
	}
	
	public void init(JComponent component) {
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "ad");
		component.getActionMap().put("ad", new KeyAction(KeyEvent.VK_A, false));
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "au");
		component.getActionMap().put("au", new KeyAction(KeyEvent.VK_A, true));
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "sd");
		component.getActionMap().put("sd", new KeyAction(KeyEvent.VK_S, false));
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "su");
		component.getActionMap().put("su", new KeyAction(KeyEvent.VK_S, true));
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "dd");
		component.getActionMap().put("dd", new KeyAction(KeyEvent.VK_D, false));
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "du");
		component.getActionMap().put("du", new KeyAction(KeyEvent.VK_D, true));
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "wd");
		component.getActionMap().put("wd", new KeyAction(KeyEvent.VK_W, false));
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "wu");
		component.getActionMap().put("wu", new KeyAction(KeyEvent.VK_W, true));
		
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0, false), "md");
		component.getActionMap().put("md", new KeyAction(KeyEvent.VK_M, false));
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0, true), "mu");
		component.getActionMap().put("mu", new KeyAction(KeyEvent.VK_M, true));
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_K, 0, false), "kd");
		component.getActionMap().put("kd", new KeyAction(KeyEvent.VK_K, false));
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_K, 0, true), "ku");
		component.getActionMap().put("ku", new KeyAction(KeyEvent.VK_K, true));
		
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_J, 0, false), "jd");
		component.getActionMap().put("jd", new KeyAction(KeyEvent.VK_J, false));
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_J, 0, true), "ju");
		component.getActionMap().put("ju", new KeyAction(KeyEvent.VK_J, true));
		
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "spaced");
		component.getActionMap().put("spaced", new KeyAction(KeyEvent.VK_SPACE, false));
		component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "spaceu");
		component.getActionMap().put("spaceu", new KeyAction(KeyEvent.VK_SPACE, true));
		createMap();
	}
	
	private void createMap() {
		keyActions = new HashMap<Integer, mechanics.input.Action>(12);
		keyActions.put(KeyEvent.VK_A, (c) -> {
			if (shift())
				c.sideways(-Camera.sidewaysV);
			else c.rotate(0, Camera.turnV);
			});
		keyActions.put(KeyEvent.VK_D, (c) -> {
			if (shift())
				c.sideways(Camera.sidewaysV);
			else c.rotate(0, -Camera.turnV);
			});
		keyActions.put(KeyEvent.VK_W, (c) -> {
			if (shift())
				c.updward(Camera.upV);
			else c.forward(Camera.forwardV);
			});
		keyActions.put(KeyEvent.VK_S, (c) -> {
			if (shift())
				c.updward(-Camera.upV);
			else c.forward(-Camera.backwardV);
			});
		keyActions.put(KeyEvent.VK_M, (c) -> { c.rotate(Camera.turnV, 0); });
		keyActions.put(KeyEvent.VK_K, (c) -> { c.rotate(-Camera.turnV, 0); });
		keyActions.put(KeyEvent.VK_J, (c) -> c.flipSpeed());
	}
	
	public boolean shift() {
		return keys[KeyEvent.VK_SPACE];
	}
	
	public boolean typed(int keyCode) {
		return keys[keyCode];
	}
	
	private class KeyAction extends AbstractAction {
		
		private static final long serialVersionUID = -4319946754804169863L;
		
		private int id;
		private boolean pressed;

		public KeyAction(int id, boolean released) {
			this.id = id;
			this.pressed = !released;
		}
		
		public void actionPerformed(ActionEvent e) {
			keys[id] = pressed;
		}
		
	}
	
}
