package calc;

import package_Main.Alg;

public abstract class Operation {
	private Alg alg;
	
	public Operation(Alg alg) {
		this.alg = alg;
	}

	public abstract String reduce(String s);

	protected abstract double calcValue(String name, double[] dt);

	protected final String reducePostfixFunction(String s, String name) {
		int ipf = s.indexOf(name);
		while (ipf != -1) {
			int ib, brec = 0;
			if (s.charAt(ipf - 1) == ')') {
				brec = 1;
				ib = ipf - brec - 1;
				int c = brec;
				while (c > 0) {
					if (s.charAt(ib) == '(')
						c--;
					else if (s.charAt(ib) == ')')
						c++;
					ib--;
				}
				ib++;
			} else {
				ib = alg.getDoubleBeginIndex(s, ipf - 1);
			}

			String[] args = s.substring(ib + brec, ipf - brec).split(",");
			s = s.substring(0, ib) + alg.writeValue(calcValue(name, alg.solveToDouble(args)))
					+ s.substring(ipf + name.length(), s.length());
			ipf = s.indexOf(name);
		}
		return s;
	}

	protected final String reducePrefixFunction(String s, String name) {
		int ib = s.indexOf(name), ie, brecr = 0;
		while (ib != -1) {
			if (s.charAt(ib + name.length()) == '(') {
				brecr = 1;
				ie = ib + name.length() + brecr;
				int c = brecr;
				while (c > 0) {
					if (s.charAt(ie) == '(')
						c++;
					else if (s.charAt(ie) == ')')
						c--;
					ie++;
				}
			} else {
				ie = alg.getDoubleEndIndex(s, ib + name.length());
			}

			String[] args = s.substring(ib + name.length() + brecr, ie - brecr).split(",");
			s = s.substring(0, ib) + alg.writeValue(calcValue(name, alg.solveToDouble(args))) + s.substring(ie, s.length());
			ib = s.indexOf(name);
		}
		return s;
	}

	protected final String reduceOperator(String s, String[] namet) {
		String name = getFirst(s, namet);
		while (name != null) {
			int io = s.indexOf(name), ilb, ire, brecl = 0, brecr = 0;
			if ((name == "-") && ((io == 0) || (s.charAt(io - 1) == '(') || (s.charAt(io - 1) == 'E'))) {
				s = alg.replace(s, '-', '_');
				name = getFirst(s, namet);
				continue;
			}
			if (s.charAt(io - 1) == ')') {
				brecl = 1;
				ilb = io - brecl - 1;
				int c = brecl;
				while (c > 0) {
					if (s.charAt(ilb) == '(')
						c--;
					else if (s.charAt(ilb) == ')')
						c++;
					ilb--;
				}
				ilb++;

			} else {
				ilb = alg.getDoubleBeginIndex(s, io - 1);
			}

			if (s.charAt(io + name.length()) == '(') {
				brecr = 1;
				ire = io + name.length() + brecr;
				int c = brecr;
				while (c > 0) {
					if (s.charAt(ire) == '(')
						c++;
					else if (s.charAt(ire) == ')')
						c--;
					ire++;
				}
			} else {
				ire = alg.getDoubleEndIndex(s, io + name.length());
			}

			String[] argsl = s.substring(ilb + brecl, io - brecl).split(",");
			String[] argsr = s.substring(io + name.length() + brecr, ire - brecr).split(",");
			s = s.substring(0, ilb) + alg.writeValue(calcValue(name, alg.solveToDouble(alg.joinT(argsl, argsr))))
					+ s.substring(ire, s.length());
			name = getFirst(s, namet);
		}
		s = alg.replaceAll(s, '_', '-');
		return s;
	}

	private final String getFirst(String s, String[] namet) {
		String r = null;
		int min = s.length() + 3, tmp;
		for (String name : namet)
			if (((tmp = s.indexOf(name)) < min) && (tmp != -1)) {
				min = tmp;
				r = name;
			}
		return r;
	}

}
