package com.rianyusuf.terbalik.polandia.hitung;

import java.util.Map;
import java.util.TreeMap;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

public class OperatorLists {
	private Map<String, Operator> map;

	public OperatorLists() {
		map = new TreeMap<String, Operator>();

		addOperator("+", (Apfloat a, Apfloat b) -> {
			return a.add(b);
		});

		addOperator("-", (Apfloat a, Apfloat b) -> {
			return a.subtract(b);
		});

		addOperator("*", 2, (Apfloat a, Apfloat b) -> {
			return a.multiply(b);
		});

		addOperator("/", 2, (Apfloat a, Apfloat b) -> {
			return a.divide(b);
		});

		addOperator("%", 2, (Apfloat a, Apfloat b) -> {
			return a.mod(b);
		});

		addOperator("^", false, 3, (Apfloat a, Apfloat b) -> {
			return ApfloatMath.pow(a, b);
		});
	}

	public Map<String, Operator> getOperators() {
		return map;
	}

	public void addOperator(String op, boolean leftAssoc, int precedence,
			OperatorInterface cmd) {
		map.put(op, new Operator(op, precedence, leftAssoc, cmd));
	}

	public void addOperator(String op, int precedence, OperatorInterface cmd) {
		map.put(op, new Operator(op, precedence, cmd));
	}

	public void addOperator(String op, OperatorInterface cmd) {
		map.put(op, new Operator(op, cmd));
	}
}
