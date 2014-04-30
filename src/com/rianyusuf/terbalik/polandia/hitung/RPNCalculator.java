package com.rianyusuf.terbalik.polandia.hitung;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apfloat.Apfloat;

import com.rianyusuf.ds.Stack;

public class RPNCalculator {
	private Stack<Apfloat> stack;
	private Map<String, Operator> mapOp;
	private Map<String, Function> mapFunc;

	private boolean isRPN = true;

	public static void main(String[] args) {
		RPNCalculator npt = new RPNCalculator();
		Scanner in = new Scanner(System.in);
		String str;

		System.out
				.println("Selamat datang di Kalkulator dengan\nNotasi Polandia Terbalik!");
		System.out
				.println("Masukkan perhitungan atau ketik q + ENTER untuk keluar.");
		/*
		 * Selama bukan [Qq], maka coba olah data yang dimasukkan.
		 */
		do {
			str = in.nextLine();
			if (str.equalsIgnoreCase("changes mode")) {
				npt.setIsRPN(!npt.isRPN());
				System.out.println("in mode RPN: " + npt.isRPN());
			} else if (str.equalsIgnoreCase("about")) {
				System.out.println("RPC version 1.0.0\nby Rahadian Yusuf");
			} else if (str.equalsIgnoreCase("help")) {
				System.out.println("Operators: ");
				for (String s : npt.getOperators().keySet()) {
					System.out.println(s);
				}
				System.out.println();

				System.out.println("Functions: ");
				for (String s : npt.getFunctions().keySet()) {
					System.out.print(s + "(");
					Function func = npt.getFunctions().get(s);
					for (int i = 1; i <= func.getNumParams(); i++) {
						System.out.print((char) (96 + i));
						if (i != func.getNumParams())
							System.out.print(", ");
					}
					System.out.println(")");
				}
				System.out.println();
			} else if (!str.equalsIgnoreCase("q"))
				npt.parse(str);
		} while (!str.equalsIgnoreCase("q"));

		in.close();
	}

	public boolean isRPN() {
		return isRPN;
	}

	public void setIsRPN(boolean b) {
		isRPN = b;
	}

	public boolean parse(String str) {
		if (isRPN) {
			return parseRPN(str);
		} else {
			return parseInfix(str);
		}
	}

	public RPNCalculator() {
		stack = new Stack<Apfloat>();
		mapOp = new OperatorLists().getOperators();
		mapFunc = new FunctionLists().getFunctions();
		;
	}

	public boolean parseInfix(String syntax) {
		return parseRPN(toRPN(syntax));
	}

	public boolean parseRPN(String syntax) {
		Apfloat ret = new Apfloat("0");

		boolean error = false;
		StringTokenizer sc = new StringTokenizer(syntax);

		while (sc.hasMoreTokens()) {
			String in = sc.nextToken();

			try {
				// Asumsikan inputnya angka berwujud String
				// Angka itu harus dibebaskan dari String dan dimasukkan ke
				// dalam rumah Stack

				Apfloat x = new Apfloat(in, 50);
				stack.push(x);
			} catch (NumberFormatException e) {
				// Wah, ternyata bukan angka. Mungkin operator yang ingin
				// mengolah para angka. Dicoba jalankan operatornya
				try {
					eval(in);
				} catch (RPNException e1) {
					System.err.println("ERROR: " + e1.getMessage());
					error = true;
				} catch (ArithmeticException e2) {
					System.err.println("ERROR: arithmetic error: "
							+ e2.getMessage());
					error = true;
				}
			} catch (EmptyStackException e) {
				// Stacknya kosong, apa yang mau diolah?
				if (!error) {
					System.err.println("ERROR: stack is empty");
					error = true;
				}
			}
		}

		// Hasil akhir dikeluarkan dari stack
		if (stack.size() > 0)
			ret = stack.pop();
		else if (!error) {
			System.err.println("ERROR: stack is empty");
			error = true;
		}

		if (stack.size() > 0) {
			stack.makeEmpty();
		}

		if (!error)
			System.out.println(ret.toString(true));

		return error;
	}

	public void eval(String cmd) throws RPNException {
		if (mapOp.containsKey(cmd)) {
			Operator run = mapOp.get(cmd);
			if (stack.size() < 2)
				throw new RPNException("Parameter is not sufficient");

			Apfloat a = stack.pop();
			Apfloat b = stack.pop();
			stack.push(run.eval(b, a));

		} else if (mapFunc.containsKey(cmd)) {
			Function run = mapFunc.get(cmd);
			ArrayList<Apfloat> params = new ArrayList<Apfloat>();

			if (stack.size() < run.getNumParams())
				throw new RPNException("More parameter(s) need");
			else if (stack.size() > run.getNumParams()
					&& run.getNumParams() > 0)
				throw new RPNException("Parameters are too much");

			for (int i = 0; i < run.getNumParams(); i++) {
				params.add(stack.pop());
			}
			stack.push(run.eval(params));
		} else {
			throw new RPNException("Operator or function not defined: " + cmd);
		}
	}

	private boolean isNumber(String str) {

		if (str.equals("-"))
			return false;

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (!Character.isDigit(ch) && ch != '-' && ch != '.' && (ch != 'e'
					&& ch != 'E' && i == 0))
				return false;
		}

		return true;
	}

	public String toRPN(String input) {
		ExpressionTokenizer tokenizer = new ExpressionTokenizer(input,
				mapOp.keySet());

		Stack<String> opStack = new Stack<String>();
		Queue<String> result = new LinkedList<String>();

		while (tokenizer.hasNext()) {
			String token = tokenizer.next();

			if (isNumber(token)) {
				result.add(token);
			} else if (mapFunc.containsKey(token)) {
				opStack.push(token);
			} else if (token.equals(",")) {
				while (!opStack.isEmpty() && !opStack.peek().equals("(")) {
					result.add(opStack.pop());
				}

				if (opStack.isEmpty()) {
					throw new RPNException(
							"Either the separator was misplaced or parentheses were mismatched");
				}
			} else if (mapOp.containsKey(token)) {
				Operator op1 = mapOp.get(token);
				String token2 = opStack.isEmpty() ? null : opStack.peek();

				while (token2 != null
						&& mapOp.containsKey(token2)
						&& (op1.isLeftAssoc()
								&& op1.getPrecedence() <= mapOp.get(token2)
										.getPrecedence() || op1.getPrecedence() < mapOp
								.get(token2).getPrecedence())) {
					result.add(opStack.pop());
					token2 = opStack.isEmpty() ? null : opStack.peek();
				}

				opStack.push(token);
			} else if (token.equals("(")) {
				opStack.push(token);
			} else if (token.equals(")")) {
				while (!opStack.isEmpty() && !opStack.peek().equals("(")) {
					result.add(opStack.pop());
				}

				if (opStack.isEmpty()) {
					throw new RPNException("Parentheses were mismatched");
				}

				opStack.pop(); // (

				if (!opStack.isEmpty() && mapFunc.containsKey(opStack.peek())) {
					result.add(opStack.pop());
				}
			}
		}

		if (!opStack.isEmpty()) {
			while (!opStack.isEmpty()) {
				if (opStack.peek().equals("(")) {
					throw new RPNException("Parentheses were mismatched");
				}
				result.add(opStack.pop());
			}
		}

		String ret = "";

		while (!result.isEmpty()) {
			ret += result.poll();
			ret += (result.peek() != null) ? " " : "";
		}

		return ret;
	}

	public final Map<String, Operator> getOperators() {
		return mapOp;
	}

	public final Map<String, Function> getFunctions() {
		return mapFunc;
	}
}