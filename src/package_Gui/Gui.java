package package_Gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import package_Main.Alg;

public class Gui {
	static final int gridHSiz = 6;
	static final int gridWSiz = 6;
	static final int gridWGap = 1;
	static final int gridHGap = 1;
	static final int frameHSiz = 250;
	static final int frameWSiz = 400;
	static final int frameHMax = 700;
	static final int frameWMax = 1300;
	static final String[] digits = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	static final String[] operators = new String[] { "/", "*", "-", "+", "^","mod" };
	static final String[] prefixFunc = new String[] { "sin", "cos", "tan","ln","log" };
	static final String[] postfixFunc = new String[] { "sq", "sqrt" };
	static final String[] signs = new String[] { "(", ")", ".","," };////////////
	private Alg alg;
	private JFrame frame;
	private JTextField screen;
	private double mem = 0;

	public Gui(Alg alg) {
		this.alg = alg;
		frame = new JFrame("kalkulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Box mainBox = new Box(BoxLayout.Y_AXIS);
		frame.getContentPane().add(mainBox);
		screen = new JTextField();
		screen.setEditable(false);
		mainBox.add(screen);
		JPanel buttonPanel = new JPanel(new GridLayout(gridHSiz, gridWSiz, gridWGap, gridHGap));
		mainBox.add(buttonPanel);

		DigitListener nl = new DigitListener();
		OperatorListener ol = new OperatorListener();
		EqualsListener el = new EqualsListener();
		DotListener dl = new DotListener();
		CommaListener cml = new CommaListener();
		OpenBracketListener obl = new OpenBracketListener();
		CloseBracketListener cbl = new CloseBracketListener();
		QuickFunctionListener qfl = new QuickFunctionListener();
		SpecialFunctionListener sfl = new SpecialFunctionListener();
		MemoryModListener mml = new MemoryModListener();
		MemoryPrtListener mpl = new MemoryPrtListener();
		ClearListener cl = new ClearListener();
		BackspaceListener bl = new BackspaceListener();

		buttonPanel.add(new KeyButton("sin", sfl));
		buttonPanel.add(new KeyButton("cos", sfl));
		buttonPanel.add(new KeyButton("tan", sfl));
		buttonPanel.add(new KeyButton("mod", ol));
		buttonPanel.add(new KeyButton("ln", sfl));
		buttonPanel.add(new KeyButton("log",sfl));
		buttonPanel.add(new KeyButton("(", obl));
		buttonPanel.add(new KeyButton(")", cbl));
		buttonPanel.add(new KeyButton("+/-", qfl));
		buttonPanel.add(new KeyButton("^", ol));
		buttonPanel.add(new KeyButton("1/x", qfl));
		buttonPanel.add(new KeyButton("m-", mml));
		buttonPanel.add(new KeyButton("7", nl));
		buttonPanel.add(new KeyButton("8", nl));
		buttonPanel.add(new KeyButton("9", nl));
		buttonPanel.add(new KeyButton("/", ol));
		buttonPanel.add(new KeyButton("sq", qfl));
		buttonPanel.add(new KeyButton("m+", mml));
		buttonPanel.add(new KeyButton("4", nl));
		buttonPanel.add(new KeyButton("5", nl));
		buttonPanel.add(new KeyButton("6", nl));
		buttonPanel.add(new KeyButton("*", ol));
		buttonPanel.add(new KeyButton("sqrt", qfl));
		buttonPanel.add(new KeyButton("mrc", mpl));
		buttonPanel.add(new KeyButton("1", nl));
		buttonPanel.add(new KeyButton("2", nl));
		buttonPanel.add(new KeyButton("3", nl));
		buttonPanel.add(new KeyButton("-", ol));
		buttonPanel.add(new KeyButton(""));
		buttonPanel.add(new KeyButton("<--", bl));
		buttonPanel.add(new KeyButton("0", nl));
		buttonPanel.add(new KeyButton(".", dl));
		buttonPanel.add(new KeyButton("=", el));
		buttonPanel.add(new KeyButton("+", ol));
		buttonPanel.add(new KeyButton(",",cml));
		buttonPanel.add(new KeyButton("c", cl));
		
		screen.addKeyListener(new KeyboardListener());

		frame.setBounds(800, 200, frameWSiz, frameHSiz);
		frame.setVisible(true);

	}

	private void addText(String s) {
		screen.setText(screen.getText() + s);
		screen.requestFocus();
	}

	private boolean chkEnd(String s, String[] sfx) {
		for (String suffix : sfx)
			if (s.endsWith(suffix))
				return true;
		return false;
	}

	private String getEnd(String s) {
		for (String sfx : operators)
			if (s.endsWith(sfx))
				return sfx;
		for (String sfx : prefixFunc)
			if (s.endsWith(sfx))
				return sfx;
		for (String sfx : postfixFunc)
			if (s.endsWith(sfx))
				return sfx;
		for (String sfx : signs)
			if (s.endsWith(sfx))
				return sfx;
		for (String sfx : digits)
			if (s.endsWith(sfx))
				return sfx;
		return null;
	}

	private class DigitListener implements ActionListener {
		private final String[] sfx = alg.joinT(new String[] { "(", ".","," }, digits, operators, prefixFunc);

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = screen.getText();
			if ((s.length() == 0) || chkEnd(s, sfx))
				addText(((KeyButton) e.getSource()).getKey());
		}
	}

	private class OperatorListener implements ActionListener {
		private final String[] sfx = alg.joinT(new String[] { ")" }, digits, postfixFunc);

		@Override
		public void actionPerformed(ActionEvent e) {
			if (chkEnd(screen.getText(), sfx))
				addText(((KeyButton) e.getSource()).getKey());
			else if((screen.getText().endsWith("(")&&(((KeyButton) e.getSource()).getKey()=="-")))
				addText("-");
		}
	}

	private class DotListener implements ActionListener {
		private final String[] sfx = digits;

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = screen.getText();
			if (chkEnd(screen.getText(), sfx)) {
				double d = alg.readValue(s.substring(alg.getDoubleBeginIndex(s, s.length() - 1), s.length()));
				if (((double) ((int) d)) == d)
					addText(".");
			}
		}
	}
	
	private class CommaListener implements ActionListener {//////////////////////////////////////////////
		private final String[] sfx = digits;

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = screen.getText();
			if (chkEnd(screen.getText(), sfx)) {
				double d = alg.readValue(s.substring(alg.getDoubleBeginIndex(s, s.length() - 1), s.length()));
				if (((double) ((int) d)) == d)
					addText(".");
			}
		}
	}	

	private class OpenBracketListener implements ActionListener {
		private final String[] sfx = alg.joinT(new String[] { "(","," }, prefixFunc, operators);

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = screen.getText();
			if ((s.length() == 0) || chkEnd(s, sfx))
				addText("(");
		}
	}

	private class CloseBracketListener implements ActionListener {
		private final String[] sfx = alg.joinT(new String[] { ")" }, digits, postfixFunc);

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = screen.getText();
			if ((alg.count(s, '(') > alg.count(s, ')')) && (chkEnd(s, sfx)))
				addText(")");
		}
	}

	private class SpecialFunctionListener implements ActionListener {
		private final String[] sfx = alg.joinT(new String[] { "(","," }, operators);

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = screen.getText();
			if ((s.length() == 0) || chkEnd(s, sfx))
				addText(((KeyButton) e.getSource()).getKey());
		}
	}

	private class MemoryPrtListener implements ActionListener {
		private final String[] sfx = alg.joinT(new String[] { "(","," }, operators,prefixFunc);

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = screen.getText();
			if ((s.length() == 0) || chkEnd(s, sfx))
				addText("" + mem);
			else if (s.equals("" + mem)) {
				mem = 0;
				screen.setText("");
				screen.requestFocus();
			}
		}
	}

	private class EqualsListener implements ActionListener {
		public final String[] sfx = alg.joinT(new String[] { ")" }, digits, postfixFunc);

		public boolean chk(String s) {
			return ((alg.count(s, '(') == alg.count(s, ')')) && (chkEnd(s, sfx)));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = screen.getText();
			if (chk(s)) {
				screen.setText(alg.solveToString(s));
				screen.requestFocus();
			}
		}
	}

	private class QuickFunctionListener implements ActionListener {
		EqualsListener el = new EqualsListener();

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = screen.getText();
			if (el.chk(s)) {
				el.actionPerformed(e);
				s=screen.getText();
				String key=((KeyButton) e.getSource()).getKey();
				switch(key) {
				case "+/-":	key="*(-1)";	break;
				case "1/x":	key="^(-1)";	break;
				}
				addText(key);
				el.actionPerformed(e);
			}
		}
	}

	private class MemoryModListener implements ActionListener {
		EqualsListener el = new EqualsListener();

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = screen.getText();
			if (el.chk(s)) {
				el.actionPerformed(e);
				switch (((KeyButton) e.getSource()).getKey()) {
				case "m+":
					mem += alg.solveToDouble(s);
					break;
				case "m-":
					mem -= alg.solveToDouble(s);
					break;
				}
			}
			screen.requestFocus();
		}
	}

	private class ClearListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			screen.setText("");
			screen.requestFocus();
		}
	}

	private class BackspaceListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = screen.getText();
			if (s.length() > 0) {
				screen.setText(s.substring(0, s.length() - getEnd(s).length()));
				screen.requestFocus();
			}
		}
	}

	private class KeyboardListener implements KeyListener {
		private boolean ctrl = false;

		@Override
		public void keyTyped(KeyEvent e) {
			char c=e.getKeyChar();
			if((c>=48)&&(c<=58))	new DigitListener().actionPerformed(new ActionEvent(new KeyButton(""+c), ActionEvent.ACTION_PERFORMED, ""));
			else {
				switch(c) {
				case '+':		new OperatorListener()			.actionPerformed(new ActionEvent(new KeyButton("+"), ActionEvent.ACTION_PERFORMED, ""));	break;
				case '-':		new OperatorListener()			.actionPerformed(new ActionEvent(new KeyButton("-"), ActionEvent.ACTION_PERFORMED, ""));	break;
				case '*':		new OperatorListener()			.actionPerformed(new ActionEvent(new KeyButton("*"), ActionEvent.ACTION_PERFORMED, ""));	break;
				case '/':		new OperatorListener()			.actionPerformed(new ActionEvent(new KeyButton("/"), ActionEvent.ACTION_PERFORMED, ""));	break;
				case '^':		new OperatorListener()			.actionPerformed(new ActionEvent(new KeyButton("^"), ActionEvent.ACTION_PERFORMED, ""));	break;
				case '=':		new EqualsListener()			.actionPerformed(new ActionEvent(new KeyButton("="), ActionEvent.ACTION_PERFORMED, ""));	break;
				case 's':		new SpecialFunctionListener()	.actionPerformed(new ActionEvent(new KeyButton("sin"), ActionEvent.ACTION_PERFORMED, ""));	break;
				case 'c':		new SpecialFunctionListener()	.actionPerformed(new ActionEvent(new KeyButton("cos"), ActionEvent.ACTION_PERFORMED, ""));	break;
				case 't':		new SpecialFunctionListener()	.actionPerformed(new ActionEvent(new KeyButton("tan"), ActionEvent.ACTION_PERFORMED, ""));	break;
				case 'l':		new SpecialFunctionListener()	.actionPerformed(new ActionEvent(new KeyButton("ln"), ActionEvent.ACTION_PERFORMED, ""));	break;
				case '.':		new DotListener()				.actionPerformed(new ActionEvent(new KeyButton("."), ActionEvent.ACTION_PERFORMED, ""));	break;
				case '(':		new OpenBracketListener()		.actionPerformed(new ActionEvent(new KeyButton("("), ActionEvent.ACTION_PERFORMED, ""));	break;
				case ')':		new CloseBracketListener()		.actionPerformed(new ActionEvent(new KeyButton(")"), ActionEvent.ACTION_PERFORMED, ""));	break;
				case 'M':		new MemoryModListener()			.actionPerformed(new ActionEvent(new KeyButton("m+"), ActionEvent.ACTION_PERFORMED, ""));	break;	
				case 'm':		new MemoryPrtListener()			.actionPerformed(new ActionEvent(new KeyButton("mrc"), ActionEvent.ACTION_PERFORMED, ""));	break;
				}
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_CONTROL)
				ctrl = true;
			else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (ctrl)
					new ClearListener().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
				else
					new EqualsListener().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
			}
			else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				new BackspaceListener().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_CONTROL)
				ctrl = false;
		}

	}

}
