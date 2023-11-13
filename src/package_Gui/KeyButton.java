package package_Gui;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class KeyButton extends JButton {
	private static final long serialVersionUID = 1L;

	private String key;

	public KeyButton(String key) {
		super(key);
		this.key = key;
	}
	
	public KeyButton(String key, ActionListener al) {
		super(key);
		this.key = key;
		addActionListener(al);
	}

	public String getKey() {
		return key;
	}

}
