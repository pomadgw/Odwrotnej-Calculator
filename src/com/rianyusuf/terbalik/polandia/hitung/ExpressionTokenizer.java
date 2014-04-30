package com.rianyusuf.terbalik.polandia.hitung;

import java.util.Iterator;
import java.util.Set;

public class ExpressionTokenizer implements Iterator<String> {
	private int pos;
	private String input;
	private String prevExpression;
	private char decimalSeparator;
	private Set<String> operators;

	public ExpressionTokenizer(String expression, Set<String> operators) {
		this.input = expression;
		this.operators = operators;
		decimalSeparator = '.';
	}

	@Override
	public boolean hasNext() {
		return (pos < input.length());
	}

	// 123+45*sin(pi)

	@Override
	public String next() {
		StringBuilder token = new StringBuilder();

		if (pos >= input.length())
			return prevExpression = null;

		char ch = input.charAt(pos);

		while (Character.isWhitespace(ch) && pos < input.length()) {
			ch = input.charAt(++pos);
		}

		if (Character.isDigit(ch)) {
			while (Character.isDigit(ch)
					|| ch == decimalSeparator || ch == 'e' || ch == 'E' && pos < input.length()) {
				token.append(input.charAt(pos++));
				ch = pos == input.length() ? 0 : input.charAt(pos);
			}
		} else if (ch == '-'
				&& Character.isDigit(peekNextChar())
				&& (operators.contains(prevExpression)
						|| "(".equals(prevExpression)
						|| ",".equals(prevExpression) || prevExpression == null)) {
			token.append('-');
			pos++;
			token.append(next());
		} else if (Character.isLetter(ch)) {
			while (Character.isLetter(ch) || Character.isDigit(ch)
					|| (ch == '_') && pos < input.length()) {
				token.append(input.charAt(pos++));
				ch = pos == input.length() ? 0 : input.charAt(pos);
			}
		} else if (ch == '(' || ch == ')' || ch == ',') {
			token.append(ch);
			pos++;
		} else {
			boolean quit = false;
			while (!Character.isLetter(ch) && !Character.isDigit(ch)
					&& !Character.isWhitespace(ch) && ch != '(' && ch != ')'
					&& ch != ',' && pos < input.length() & !quit) {
				token.append(input.charAt(pos));
				pos++;
				ch = pos == input.length() ? 0 : input.charAt(pos);
				if (ch == '-') {
					quit = true;
				}
			}
			if (!operators.contains(token.toString())) {
				throw new RPNException("Operator not defined: " + token
						+ " at position " + (pos - token.length() + 1));
			}
		}

		return prevExpression = token.toString();
	}

	@Override
	public void remove() {
		throw new RPNException("Oops! You can't remove a token of expression");
	}

	private char peekNextChar() {
		if (pos + 1 < input.length())
			return input.charAt(pos + 1);
		else
			return 0;
	}
}