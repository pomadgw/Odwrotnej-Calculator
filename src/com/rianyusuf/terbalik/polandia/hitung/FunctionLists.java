package com.rianyusuf.terbalik.polandia.hitung;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

public class FunctionLists {
	private Map<String, Function> map;
	private long precision;
	public FunctionLists(long prec) {
	    this.precision = prec;
		map = new TreeMap<String, Function>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.toLowerCase().compareTo(o2.toLowerCase());
			}

		});

		addFunction("pi", 0, (List<Apfloat> params) -> {
			return ApfloatMath.pi(precision);
		});

		addFunction("e", 0, (List<Apfloat> params) -> {
			return ApfloatMath.exp(new Apfloat(1, precision));
		});

		addFunction("sin", 1, (List<Apfloat> params) -> {
			return ApfloatMath.sin(params.get(0));
		});

		addFunction("cos", 1, (List<Apfloat> params) -> {
			return ApfloatMath.cos(params.get(0));
		});

		addFunction("tan", 1, (List<Apfloat> params) -> {
			return ApfloatMath.tan(params.get(0));
		});

		addFunction("asin", 1, (List<Apfloat> params) -> {
			return ApfloatMath.asin(params.get(0));
		});

		addFunction("acos", 1, (List<Apfloat> params) -> {
			return ApfloatMath.acos(params.get(0));
		});

		addFunction("atan", 1, (List<Apfloat> params) -> {
			return ApfloatMath.atan(params.get(0));
		});

		addFunction("sinh", 1, (List<Apfloat> params) -> {
			return ApfloatMath.sinh(params.get(0));
		});

		addFunction("cosh", 1, (List<Apfloat> params) -> {
			return ApfloatMath.cosh(params.get(0));
		});

		addFunction("tanh", 1, (List<Apfloat> params) -> {
			return ApfloatMath.tanh(params.get(0));
		});

		addFunction("asinh", 1, (List<Apfloat> params) -> {
			return ApfloatMath.asinh(params.get(0));
		});

		addFunction("acosh", 1, (List<Apfloat> params) -> {
			return ApfloatMath.acosh(params.get(0));
		});

		addFunction("atanh", 1, (List<Apfloat> params) -> {
			return ApfloatMath.atanh(params.get(0));
		});

		addFunction("log", 1, (List<Apfloat> params) -> {
			return ApfloatMath.log(params.get(0));
		});

		addFunction(
				"logn",
				2,
				(List<Apfloat> params) -> {
					return ApfloatMath.log(params.get(0)).divide(
							ApfloatMath.log(params.get(1)));
				});

		addFunction("exp", 1, (List<Apfloat> params) -> {
			return ApfloatMath.exp(params.get(0));
		});

		addFunction("inv", 1, (List<Apfloat> params) -> {
			return new Apfloat(1, precision).divide(params.get(0));
		});

		addFunction("abs", 1, (List<Apfloat> params) -> {
			return ApfloatMath.abs(params.get(0));
		});

		addFunction("sqrt", 1, (List<Apfloat> params) -> {
			return ApfloatMath.sqrt(params.get(0));
		});

		addFunction("fac", 1, (List<Apfloat> params) -> {
			return fac(params.get(0), precision);
		});
	}
	
	public FunctionLists()
    {
        this(50);
    }

	private Apfloat fac(Apfloat apfloat, long precision) {
		if (apfloat.compareTo(Apfloat.ONE) <= 0) {
			return new Apfloat(1);
		} else {
			return apfloat.multiply(fac(apfloat.subtract(new Apfloat(1, precision)), precision));
		}
	}

	public Map<String, Function> getFunctions() {
		return map;
	}

	public void addFunction(String name, int numParams, FuncInterface cmd) {
		map.put(name, new Function(name, numParams, cmd));
	}

    /**
     * @return the precision
     */
    public long getPrecision()
    {
        return precision;
    }

    /**
     * @param precision the precision to set
     */
    public void setPrecision(long precision)
    {
        this.precision = precision;
    }

}
