package com.rianyusuf.terbalik.polandia.hitung;

import java.util.List;
import org.apfloat.Apfloat;


@FunctionalInterface
public interface FuncInterface {
	public Apfloat eval(List<Apfloat> params) throws RPNException;
}
