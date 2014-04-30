package com.rianyusuf.terbalik.polandia.hitung;

import java.util.List;

import org.apfloat.Apfloat;

/**
 * @author Rahadian
 * 
 */
public class Function {
	private int numParams;
	private String name;
	private FuncInterface function;

	public Function(String name, int numParams, FuncInterface function) {
		this.name = name;
		this.numParams = numParams;
		this.function = function;
	}

	public int getNumParams() {
		return numParams;
	}

	public String getName() {
		return name;
	}

	public Apfloat eval(List<Apfloat> params) throws RPNException {
		return function.eval(params);
	}
}