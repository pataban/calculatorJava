package calc;

import package_Main.Alg;

public class Calc {
	private Alg alg;
	
	public Calc(Alg alg) {
		this.alg = alg;
	}

	public String calc(String s) {
		s = new Sqrt(alg).reduce(s);
		s = new Sq(alg).reduce(s);

		s = new Sin(alg).reduce(s);
		s = new Cos(alg).reduce(s);
		s = new Tan(alg).reduce(s);
		s = new Ln(alg).reduce(s);
		s = new Log(alg).reduce(s);

		s= new Mod(alg).reduce(s);
		s = new Pow(alg).reduce(s);
		s = new MulDiv(alg).reduce(s);
		s = new AddSub(alg).reduce(s);
		return s;

	}

	public String[] calc(String st[]) {
		for (int i = 0; i < st.length; i++)
			st[i] = calc(st[i]);
		return st;
	}

}

class Sq extends Operation {

	public Sq(Alg alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String reduce(String s) {
		return reducePostfixFunction(s, "sq");
	}

	@Override
	protected double calcValue(String name, double[] dt) {
		return dt[0] * dt[0];
	}

}

class Sqrt extends Operation {

	public Sqrt(Alg alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String reduce(String s) {
		return reducePostfixFunction(s, "sqrt");
	}

	@Override
	protected double calcValue(String name, double[] dt) {
		return Math.sqrt(dt[0]);
	}

}

class Sin extends Operation {

	public Sin(Alg alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String reduce(String s) {
		return reducePrefixFunction(s, "sin");
	}

	@Override
	protected double calcValue(String name, double[] dt) {
		return Math.sin(dt[0]);
	}

}

class Cos extends Operation {

	public Cos(Alg alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String reduce(String s) {
		return reducePrefixFunction(s, "cos");
	}

	@Override
	protected double calcValue(String name, double[] dt) {
		return Math.cos(dt[0]);
	}

}

class Tan extends Operation {

	public Tan(Alg alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String reduce(String s) {
		return reducePrefixFunction(s, "tan");
	}

	@Override
	protected double calcValue(String name, double[] dt) {
		return Math.tan(dt[0]);
	}

}

class Mod extends Operation {

	public Mod(Alg alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String reduce(String s) {
		return reduceOperator(s, new String[] {"mod"});
	}

	@Override
	protected double calcValue(String name, double[] dt) {
		return dt[0]%dt[1];
	}

}

class Ln extends Operation {

	public Ln(Alg alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String reduce(String s) {
		return reducePrefixFunction(s, "ln");
	}

	@Override
	protected double calcValue(String name, double[] dt) {
		return Math.log(dt[0]);
	}

}

class Log extends Operation {

	public Log(Alg alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String reduce(String s) {
		return reducePrefixFunction(s, "log");
	}

	@Override
	protected double calcValue(String name, double[] dt) {
		return Math.log(dt[1])/Math.log(dt[0]);
	}

}

class Pow extends Operation {

	public Pow(Alg alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String reduce(String s) {
		return reduceOperator(s, new String[] { "^" });
	}

	@Override
	protected double calcValue(String name, double[] dt) {
		return Math.pow(dt[0], dt[1]);
	}

}

class MulDiv extends Operation {

	public MulDiv(Alg alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String reduce(String s) {
		return reduceOperator(s, new String[] { "*", "/" });
	}

	@Override
	protected double calcValue(String name, double[] dt) {
		return (name == "*") ? (dt[0] * dt[1]) : (dt[0] / dt[1]);
	}

}

class AddSub extends Operation {

	public AddSub(Alg alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String reduce(String s) {
		return reduceOperator(s, new String[] { "+", "-" });
	}

	@Override
	protected double calcValue(String name, double[] dt) {
		return (name == "+") ? (dt[0] + dt[1]) : (dt[0] - dt[1]);
	}

}
