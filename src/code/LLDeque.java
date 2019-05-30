package code;

/* 
 * ASSIGNMENT 2
 * AUTHOR:  <Insert Student Name>
 * Class : LLDeque
 *
 * You are not allowed to use Java containers!
 * You must implement the linked list yourself
 * Note that it should be a doubly linked list
 *
 * MODIFY 
 * 
 * */

import given.iDeque;
import java.util.Iterator;


//If you have been following the class, it should be obvious by now how to implement a Deque wth a doubly linked list
public class LLDeque<E> implements iDeque<E> {

	//Use sentinel nodes. See slides if needed
	private Node<E> header;
	private Node<E> trailer;
	private int size = 0;

	/*
	 * ADD FIELDS IF NEEDED
	 */

	// The nested node class, provided for your convenience. Feel free to modify
	private class Node<T> {
		private T element;
		private Node<T> next;
		private Node<T> prev;
		/*
		 * ADD FIELDS IF NEEDED
		 */

		Node(T d, Node<T> n, Node<T> p) {
			element = d;
			next = n;
			prev = p;
		} 

		/*
		 * ADD METHODS IF NEEDED
		 */
	}

	public LLDeque() {
		//Remember how we initialized the sentinel nodes
		header  = new Node<E>(null, null, header);
		trailer = new Node<E>(null, trailer, header);
		header.next = trailer;

		/*
		 * ADD CODE IF NEEDED
		 */
	}

	public String toString() {
		if(isEmpty())
			return "";
		StringBuilder sb = new StringBuilder(1000);
		sb.append("[");
		Node<E> tmp = header.next;
		while(tmp.next != trailer) {
			sb.append(tmp.element.toString());
			sb.append(", ");
			tmp = tmp.next;
		}
		sb.append(tmp.element.toString());
		sb.append("]");
		return sb.toString();
	}

	/*
	 * ADD METHODS IF NEEDED
	 */

	/*
	 * Below are the interface methods to be overriden
	 */

	@Override
	public int size() {

		return size;
	}

	@Override
	public boolean isEmpty() {

		return size == 0 ? true : false;
	}

	@Override
	public void addFront(E o) {
		Node<E> frontier = header.next;
		Node<E> newFirst = new Node<E>(o, frontier, header);
		frontier.prev = newFirst;
		header.next = newFirst;
		size++;
	}

	@Override
	public E removeFront() {
		
		if(isEmpty()) return null;
		Node<E> tmp = header.next;
		Node<E> newFirst = tmp.next;
		newFirst.prev = header;
		header.next = newFirst;
		size--;
		return tmp.element;
	}

	@Override
	public E front() {
		
		return isEmpty() ? null : header.next.element;
	}

	@Override
	public void addBehind(E o) {
		Node<E> backer = trailer.prev;
		Node<E> newLast = new Node<E>(o, trailer, backer);
		backer.next = newLast;
		trailer.prev = newLast;
		size++;
	}

	@Override
	public E removeBehind() {
		
		if(isEmpty()) return null;
		Node<E> tmp = trailer.prev;
		Node<E> newLast = tmp.prev;
		newLast.next = trailer;
		trailer.prev = newLast;
		size--;
		
		return tmp.element;
	}

	@Override
	public E behind() {
		
		return isEmpty() ? null : trailer.prev.element;
	}

	@Override
	public void clear() {
		header.next = trailer;
		trailer.prev = header;
		size = 0;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		//Hint: Fill in the LLDequeIterator given below and return a new instance of it
		return new LLDequeIterator();
	}

	private final class LLDequeIterator implements Iterator<E> {
		
		private Node<E> cur;
		/*
		 * 
		 * ADD A CONSTRUCTOR IF NEEDED
		 * Note that you can freely access everything about the outer class!
		 * 
		 */
		public LLDequeIterator() {
			cur = header;
		}

		@Override
		public boolean hasNext() {
			if(cur.next == trailer) return false;
			return true;
		}

		@Override
		public E next() {
			cur = cur.next;
			return cur.element;
		}        
	}

}
