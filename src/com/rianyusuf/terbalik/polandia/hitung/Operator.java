package com.rianyusuf.terbalik.polandia.hitung;

import org.apfloat.Apfloat;

public class Operator {
	private String op;

	private int precedence;
	private boolean leftAssoc;
	private OperatorInterface operatorEval;
	
	public Operator(String op, int precedence, boolean leftAssoc, OperatorInterface operatorEval) {
		this.op = op;
		this.precedence = precedence;
		this.leftAssoc = leftAssoc;
		this.operatorEval = operatorEval;
	}
	public Operator(String op, int precedence, OperatorInterface operatorEval) {

		this(op, precedence, true, operatorEval);
	}

	public Operator(String op, OperatorInterface operatorEval) {
		this(op, 1, true, operatorEval);
	}

	public String getOperator() {
		return op;
	}
	
	public int getPrecedence() {
		return precedence;
	}
	
	public boolean isLeftAssoc() {
		return leftAssoc;
	}

	public Apfloat eval(Apfloat a, Apfloat b) throws RPNException {
		return operatorEval.eval(a, b);
	}
}