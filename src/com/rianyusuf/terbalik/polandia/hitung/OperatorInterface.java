package com.rianyusuf.terbalik.polandia.hitung;

import org.apfloat.Apfloat;

@FunctionalInterface
public interface OperatorInterface {
	public Apfloat eval(Apfloat left, Apfloat right)
			throws RPNException;
}
