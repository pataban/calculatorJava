package package_Main;

import calc.Calc;
import package_Gui.Gui;

@SuppressWarnings({ "unused" })
public class Alg {
	private Gui gui;
	private Calc calc;

	public Alg() {
		gui = new Gui(this);
		calc = new Calc(this);
	}

//	controller
//	_____________________________________________________________

	public String solveToString(String s) {
		return calc.calc(s);
	}

	public String[] solveToString(String[] st) {
		for (int i = 0; i < st.length; i++)
			st[i] = solveToString(st[i]);
		return st;
	}

	public double solveToDouble(String s) {
		return readValue(solveToString(s));
	}

	public double[] solveToDouble(String[] st) {
		double[] dt = new double[st.length];
		for (int i = 0; i < st.length; i++)
			dt[i] = solveToDouble(st[i]);
		return dt;
	}

	public String writeValue(double d) {
		return (d >= 0) ? "" + d : "(" + d + ")";
	}

	public String[] writeValue(double[] dt) {
		String[] st = new String[dt.length];
		for (int i = 0; i < dt.length; i++)
			st[i] = writeValue(dt[i]);
		return st;
	}

	public double readValue(String s) {
		return Double.parseDouble(remBrackets(s));
	}

	public double[] readValue(String[] st) {
		double[] dt = new double[st.length];
		for (int i = 0; i < st.length; i++)
			dt[i] = readValue(st[i]);
		return dt;
	}

	public String remBrackets(String s) {
		while (s.startsWith("(") && (s.lastIndexOf(')') == (s.length() - 1))) {
			s = s.substring(1, s.length() - 1);
		}
		return s;
	}

//	___________________________________________________________________________________________
//	String extensions

	public int getDoubleBeginIndex(String s, int index) {
		while ((index >= 0) && (((s.charAt(index) >= '0') && (s.charAt(index) <= '9')) || (s.charAt(index) == '.') || (s.charAt(index) == 'E')))
			index--;
		if ((index == 0) && (s.charAt(index) == '-'))
			return index;
		if ((index > 0) && (s.charAt(index) == '-') && (s.charAt(index - 1) == '('))
			return index;
		if ((index > 0) && (s.charAt(index) == '-') && (s.charAt(index - 1) == 'E'))
			return getDoubleBeginIndex(s, index - 2);
		return index + 1;
	}

	public int getDoubleEndIndex(String s, int index) {
		if ((index < s.length()) && (s.charAt(index) == '-'))
			index++;
		while ((index < s.length()) && (((s.charAt(index) >= '0') && (s.charAt(index) <= '9')) || (s.charAt(index) == '.')))
			index++;
		if ((index < s.length()) && (s.charAt(index) == 'E'))
			return getDoubleEndIndex(s, index + 1);
		return index;
	}

	public int count(String s, char c) {
		int count = 0;
		for (int i = s.length() - 1; i >= 0; i--)
			if (s.charAt(i) == c)
				count++;
		return count;
	}

	public String replace(String s, int i, char c) {
		return s.substring(0, i) + c + s.substring(i + 1, s.length());
	}

	public String replace(String s, char cb, char ce) {
		int i = s.indexOf(cb);
		return replace(s, i, ce);
	}

	public String replaceAll(String s, char cb, char ce) {
		int i = s.indexOf(cb);
		while (i != -1) {
			s = replace(s, i, ce);
			i = s.indexOf(cb);
		}
		return s;
	}

	public String[] joinT(String[] a, String[] b) {
		String[] str = new String[a.length + b.length];
		for (int i = 0; i < a.length; i++)
			str[i] = a[i];
		for (int i = 0; i < b.length; i++)
			str[a.length + i] = b[i];
		return str;
	}

	public String[] joinT(String[] a, String[] b, String[] c) {
		String[] str = new String[a.length + b.length + c.length];
		for (int i = 0; i < a.length; i++)
			str[i] = a[i];
		for (int i = 0; i < b.length; i++)
			str[a.length + i] = b[i];
		for (int i = 0; i < c.length; i++)
			str[a.length + b.length + i] = c[i];
		return str;
	}

	public String[] joinT(String[] a, String[] b, String[] c, String[] d) {
		String[] str = new String[a.length + b.length + c.length + d.length];
		for (int i = 0; i < a.length; i++)
			str[i] = a[i];
		for (int i = 0; i < b.length; i++)
			str[a.length + i] = b[i];
		for (int i = 0; i < c.length; i++)
			str[a.length + b.length + i] = c[i];
		for (int i = 0; i < d.length; i++)
			str[a.length + b.length + c.length + i] = d[i];
		return str;
	}

//	________________________________________________________________________________
//	shortcuts

	public void prt(String s) {
		System.out.println(s);
	}

	public void puti(int a) {
		System.out.println(a);
	}

	public void putd(double a) {
		System.out.println(a);
	}

	public void ret() {
		System.out.println();
	}

}
