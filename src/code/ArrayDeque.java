package code;

/* 
 * ASSIGNMENT 2
 * AUTHOR:  Eren Deniz Sanlıer
 * Class : ArrayDeque
 *
 * You are not allowed to use Java containers!
 * You must implement the Array Deque yourself
 *
 * MODIFY 
 * 
 * */

import given.iDeque;
import java.util.Arrays;
import java.util.Iterator;



@SuppressWarnings("unused")
public class ArrayDeque<E> implements iDeque<E> {

	private E[] A; //Do not change this name!
	private int size;
	private int front;
	private int rear;
	/*
	 * ADD FIELDS IF NEEDED
	 */

	public ArrayDeque() {
		this(1000);
		/*
		 * ADD CODE IF NEEDED
		 */
	}

	public ArrayDeque(int initialCapacity) {
		if(initialCapacity < 1)
			throw new IllegalArgumentException();
		A = createNewArrayWithSize(initialCapacity);
		
		size = 0;
		front = 0;
		rear = 1;
		
		/*
		 * ADD CODE IF NEEDED
		 */
	}

	// This is given to you for your convenience since creating arrays of generics is not straightforward in Java
	@SuppressWarnings({"unchecked" })
	private E[] createNewArrayWithSize(int size) {
		return (E[]) new Object[size];
	}

	//Bonus: Modify this such that the dequeue prints from front to back!
	//Hint, after you implement the iterator, use that!
	public String toString() {
		
		if(isEmpty()) return "";
		
		StringBuilder sb = new StringBuilder(1000);

		sb.append("[");
		
		Iterator<E> iter = iterator();
		
		while (iter.hasNext()) {
			
			E e = iter.next();
			sb.append(e);
			
			if (!iter.hasNext()) sb.append("]");
			else sb.append(", ");
		}
		
		return sb.toString();
	}
	
	private void resizeArray() {
		
		E[] tmp = createNewArrayWithSize(A.length * 2);
		
	    int j = front;

	    for(int i = 0; i < A.length; i++, j++) {
	    	tmp[i] = A[j % A.length];
	    }

	    front = 0;
	    rear = A.length;
	    A = tmp;

	
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
		if(size() == 0) return true;
		return false;
	}

	@Override
	public void addFront(E o) {

		if(size == A.length)
			resizeArray();
		
		if(isEmpty()) {
			front = 0;
			A[front] = o;
			rear = 1;
		}

		else {
			front = (front - 1 + A.length) % A.length;
			A[front] = o;
		}

		size++;
	}

	@Override
	public E removeFront() {
		
		if(isEmpty()) {
			return null;
		}
			
		E tmp = A[front];
		A[front] = null;
		front = (front + 1) % A.length;
		
		size--;
		
		return tmp;
	}

	@Override
	public E front() {
		return isEmpty() ? null : A[front];
	}

	@Override
	public void addBehind(E o) {
		
		if(size == A.length)
			resizeArray();
		
		if(isEmpty()) {
			front = 0;
			A[front] = o;
			rear = 1;
		}
		
		else {
			A[rear] = o;
			rear = (rear + 1) % A.length;
		}
		
		
		size++;
	}

	@Override
	public E removeBehind() {
		
		if(isEmpty()) {
			return null;
		}
		
		rear = (rear - 1 + A.length) % A.length;
		E tmp = A[rear];
		A[rear] = null;
		size--;
		
		return tmp;
	}

	@Override
	public E behind() {
		return isEmpty() ? null : A[(rear - 1 + A.length) % A.length];
	}

	@Override
	public void clear() {
		
		A = createNewArrayWithSize(A.length);
		size = 0;
		front = 0;
		rear = 1;
	}

	//Must print from front to back
	@Override
	public Iterator<E> iterator() {
		return new ArrayDequeIterator();
	}

	private final class ArrayDequeIterator implements Iterator<E> {

		/*
		 * 
		 * ADD A CONSTRUCTOR IF NEEDED Note that you can freely access everything about
		 * the outer class!
		 * 
		 */
		
		private int counter;
		private int curInd;
		

		public ArrayDequeIterator() {
			counter = 0;
		}

		@Override
		public boolean hasNext() {
			curInd = (front + counter) % A.length;
			if ((A[curInd] != null) && (curInd != front || counter == 0)) return true;
			else return false;
		}

		@Override
		public E next() {
			curInd = (front + counter) % A.length;
			E tmp = A[curInd];
			counter++;
			return tmp;
		}
	}
}




