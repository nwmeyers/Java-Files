/*  The PriorityQueue ADT may store objects in any order.  However,
    removal of objects from the PQ must follow specific criteria.
    The object of highest priority that has been in the PQ longest
    must be the object returned by the remove() method.  FIFO return
    order must be preserved for objects of identical priority.
   
    Ranking of objects by priority is determined by the Comparable<E>
    interface.  All objects inserted into the PQ must implement this
    interface.
*/   

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;



public class UnorderedList<E> implements Iterable<E>{
	private int currentSize;
    private E [] storage;
    private long modCounter;
	
	private class Node<E>{
    	E data;
    	Node<E> next;
        
    public Node (E data) {
    	this.data = data;
    	next = null;
    }
    }
    private Node<E> head, tail; 
    
    public UnorderedList() {
    	head = tail = null;
       	currentSize = 0;
    	modCounter = 0;
    }

    //  Inserts a new object into the priority queue.  Returns true if
    //  the insertion is successful.  If the PQ is full, the insertion
    //  is aborted, and the method returns false.
    public boolean insertLast(E object){
    		 Node newNode = new Node(object);
    	        if(head == null) { // test so we can use current.next in the while loop
    	            head = newNode;
    	            tail = newNode; //added
    	            modCounter++;
    	            currentSize++;
    	            return true;
    	        } 
    	                Node current = head;
    	        while(current.next != null)
    	            current = current.next; 
    	        current.next = newNode;
    	        tail = current.next; //added
    	    	
            currentSize++;
            modCounter++;
            return true;
    } 
             
    public boolean insertFront(E object){
		Node newNode = new Node(object);
        newNode.next = head;
        head = newNode;
        currentSize++;
        modCounter++;
        return true;
} 
    				
       
    //  Removes the object of highest priority that has been in the
    //  PQ the longest, and returns it.  Returns null if the PQ is empty.
    public E remove() {
    	if(head == null)
            return null;
    	E obj;
    	Node<E> previous = null, current = head, bestPrevious = null, bestCurrent = head;
    	while(current != null){
    		if(((Comparable<E>)current.data).compareTo(bestCurrent.data)<0){
    			bestPrevious = previous;
    			bestCurrent = current;
    		}
    	previous = current;
    	current = current.next;
    	}
    	obj = bestCurrent.data;
    	
    	if(currentSize > 1 && bestPrevious == null){
    		head = head.next;
    	if(head == null)
    		tail = null;
    	currentSize--;
    	modCounter++;
    	return obj;
    	}
    	
    	if(currentSize == 1 && bestPrevious == null){
    		head = head.next;
    	if(head == null)
    		tail = null;
    	}
    	else if(bestCurrent == tail){
    		bestPrevious.next = null;
    		tail = bestPrevious;
    	}
    	else{
    		bestPrevious.next = bestCurrent.next;
    		currentSize--;
    		modCounter++;
    		return obj;
    	}
    	currentSize--;
    	modCounter++;
		return obj;
    }
    
    public E removeMin() {
    	if(head == null)
            return null;
    	E obj;
    	Node<E> previous = null, current = head, bestPrevious = null, bestCurrent = head;
    	while(current != null){
    		if(((Comparable<E>)current.data).compareTo(bestCurrent.data)>0){
    			bestPrevious = previous;
    			bestCurrent = current;
    		}
    	previous = current;
    	current = current.next;
    	}
    	obj = bestCurrent.data;
    	
    	if(currentSize > 1 && bestPrevious == null){
    		head = head.next;
    	if(head == null)
    		tail = null;
    	currentSize--;
    	modCounter++;
    	return obj;
    	}
    	
    	if(currentSize == 1 && bestPrevious == null){
    		head = head.next;
    	if(head == null)
    		tail = null;
    	}
    	else if(bestCurrent == tail){
    		bestPrevious.next = null;
    		tail = bestPrevious;
    	}
    	else{
    		bestPrevious.next = bestCurrent.next;
    		currentSize--;
    		modCounter++;
    		return obj;
    	}
    	currentSize--;
    	modCounter++;
		return obj;
    }
    
    public E remove(E object) {
            Node<E> previous = null, current = head;
            while(current != null){
            if(((Comparable<E>)current.data).compareTo(object) == 0){
            if (current == head)
            head = current.next;
            else
            previous.next = current.next;

            if (current == tail) // Move the tail back one
            tail = previous;
            
            currentSize--;
            modCounter++;
            return current.data;

            }
            previous = current;
            current = current.next;
            }
            return null;	

            }
   
    //  Returns the object of highest priority that has been in the
    //  PQ the longest, but does NOT remove it. 
    //  Returns null if the PQ is empty.
    public E peek(E value){
    	if(head == null)
            return null;
    	Node<E> previous = null, current = head;
    	while(current != null){
    		if(((Comparable<E>)current.data).compareTo(value) == 0){
    			return current.data;
    		}
    		current = current.next;
    	}
    	return null;	
    }
    
    //  Returns true if the priority queue contains the specified element
    public boolean contains(E obj){
    	Node<E> previous = null, current = head;
    	while(current != null){
    		if(((Comparable<E>)current.data).compareTo(obj) == 0){
    			return true;
    		}
    		current = current.next;
    	}
    	return false;	
    }
   
    //  Returns the number of objects currently in the PQ.
    public int size(){
    	return currentSize;
    }
      
    //  Returns the PQ to an empty state.
    public void clear(){
    	head = null;
    	modCounter++;
    }
   
    //  Returns true if the PQ is empty, otherwise false
    public boolean isEmpty(){
    	if(head == null)
    		return true;
    	return false;
    	
    }
   
    //  Returns true if the PQ is full, otherwise false.  List based
    //  implementations should always return false.
    public boolean isFull(){
    	return false;
    }
    
    public void reverseList(){

        Node tmp = null, previous = null, current = head;
        while(current != null) {
           tmp = current.next;
           current.next = previous;
           previous = current;
           current = tmp;
            }
       head = previous;
}
    
    //  Returns an iterator of the objects in the PQ, in no particular
    //  order.
    public Iterator<E> iterator(){
    	return new IteratorHelper();
    }
    class IteratorHelper implements Iterator<E>{
    	long modificationCounter;
    	Node<E> iterNode;
		
    	public IteratorHelper(){
    		
			modificationCounter = modCounter;
    		iterNode = head;
    	}
    	
		public boolean hasNext() {
			if(modCounter != modificationCounter)
				throw new ConcurrentModificationException();
			return iterNode != null;
		}
		
		public E next() {
			if(!hasNext())
				throw new NoSuchElementException();
			E tmp = iterNode.data;
			iterNode = iterNode.next;
			return tmp;
		}

		
		public void remove() {
			throw new UnsupportedOperationException();
			
		}
    }
} 