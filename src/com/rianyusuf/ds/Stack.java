package com.rianyusuf.ds;

import java.util.EmptyStackException;

public class Stack<E> {
	class Node {
		E data;
		Node next;

		public Node(E data, Node next) {
			this.data = data;
			this.next = next;
		}

		public Node(E data) {
			this(data, null);
		}

		public Node() {
			this(null, null);
		}
	}

	private Node top;
	private int size;

	public Stack() {
		size = 0;
	}

	public void push(E data) {
		top = new Node(data, top);
		size++;
	}

	public boolean isEmpty() {
		return top == null;
	}

	public void makeEmpty() {
		top = null;
		size = 0;
	}

	public E peek() {
		if (isEmpty())
			throw new EmptyStackException();

		return top.data;
	}

	public E pop() {
		E data = peek();

		top = top.next;
		size--;
		
		return data;
	}

	public int size() {
		return size;
	}
}
