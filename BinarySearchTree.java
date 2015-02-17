package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class BinarySearchTree<K,V> implements DictionaryADT<K,V> {
	private class Node<K,V>{
		private K key;
		private V value;
		private Node<K,V> leftChild;
		private Node<K,V> rightChild;
		
		public Node(K k, V v) {
			key = k;
			value = v;
			leftChild = rightChild = null;
		}
	}
	private Node<K,V> root;
	private int currentSize,modCounter;
	private K foundKey;
	
	public BinarySearchTree() {
		root = null;
		currentSize = 0;
		modCounter = 0;
		
	}
/*
 * used for getValue() method... uses Key and starts at the root node.
 * than it goes goes either right or left depending if the node's key is smaller or larger than 
 * the entered key.RETURNS a value
 */
	private V findValue(K key, Node<K,V> n){
		if(n==null) return null;
		if(((Comparable<K>)key).compareTo(n.key)<0)
			return findValue(key, n.leftChild);		//Go Left
		if(((Comparable<K>)key).compareTo(n.key)>0)
			return findValue(key, n.rightChild);		// Go Right
		return (V) n.value;						// Found!
	}
	
/* Find Key is used for the contains method. you enter in a key that you want to check 
 * if it is in the array, then again it compares whether the given key is less than or greater than the node's key.
 * which results in either moving down the leftCHild or the RightChild.	
 */
	
	private boolean findKey(K key, Node<K,V> n){
		if(n==null) return false;
		if(((Comparable<K>)key).compareTo(n.key)<0)
			return findKey(key, n.leftChild);		//Go Left
		if(((Comparable<K>)key).compareTo(n.key)>0)
			return findKey(key, n.rightChild);		// Go Right
		else{
			boolean helpMe;
			return  helpMe = (((Comparable<K>)key).compareTo(n.key)==0);						// Found!
	}
	}
	

	// Returns true if the dictionary has an object identified by
	// key in it, otherwise false.
	public boolean contains(K key) {
		return findKey(key,root); 
		
	}

	// Adds the given key/value pair to the dictionary.  Returns 
	// false if the dictionary is full, or if the key is a duplicate.
	// Returns true if addition succeeded.
	public boolean add(K key, V value) {
		if(contains(key))
			return false;
		if(root == null)
			root = new Node<K,V>(key,value);
		else
			insert(key,value,root,null,false);
		currentSize++;
		modCounter++;
		return true;
	}
	//created for the add, it is recursively going through the nodes to find where to insert the next 
	//key-value pair depending on whether that key is less than or greater than the node's key.
	private void insert(K k, V v, Node<K,V> n, Node<K,V> parent,boolean wasLeft){
		if(n == null) {
			if(wasLeft) parent.leftChild = new Node<K,V>(k,v);
			else parent.rightChild = new Node<K,V>(k,v);
		}
		else if(((Comparable<K>)k).compareTo((K)n.key)< 0)
			insert(k,v,n.leftChild,n,true);
		else
			insert(k,v,n.rightChild,n,false);
	}

	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	public boolean delete(K key) {
		    if (isEmpty()) return false; //Checks if is empty

		    Node<K, V> current = root;  //creating temp nodes for this method to allow for 
			Node<K,V> parent = null;	//traversing through the BST.
		    boolean wasLeft = false;

		    while (((Comparable<K>)current.key).compareTo(key) != 0) { //if the (root)node is not equal
		        parent = current;									   //while(current.key != key)
		        if (((Comparable<K>)current.key).compareTo(key) < 0) { //if the node is less than 
		            current = current.rightChild;					   //if(current.key < key)
		            wasLeft = false;
		        }else {												   // else (current.key > key)
		            current = current.leftChild;
		            wasLeft = true;
		        }
		        if (current == null) return false; //Reached bottom of tree
		    }

		    
		    if (current.leftChild == null || current.rightChild == null) { //current is to be removed 
		        
		        successorRemove(current, parent, wasLeft);//has no children or possibly one child
		    
		    
		    }else {//has two children    
		        Node <K,V> successor = current.rightChild, successorParent = current; //find the inorder successor by 
		        boolean successorLeft = false;										  //going right->left until null
		        while (successor.leftChild != null) { //right->left is not null
		            successorParent = successor;
		            successor = successor.leftChild;
		            successorLeft = true;
		        }
		        
		        successorRemove(successor, successorParent, successorLeft);//has only zero or one child

		        successor.leftChild = current.leftChild;
		        successor.rightChild = current.rightChild;

		        if (parent == null) root = successor;
		        else if (wasLeft) parent.leftChild = successor;
		        else parent.rightChild = successor;

		    }

		    modCounter++;
		    currentSize--;
		    return true;
		}

		private void successorRemove(Node<K,V> node, Node<K,V> parent, boolean wasLeft) {
		  //  Node<K,V> child = (node.leftChild != null) ? node.leftChild : node.rightChild;  //ternary operator: (if else)
		    Node<K,V> child = node.leftChild;
			if(node.leftChild != null)											//if != null then node.leftChild
		    	child =node.leftChild;
		    child = node.rightChild;
		    if (parent == null) root = child;												//if == null then node.rightChild
		    else if (wasLeft) parent.leftChild = child;
		    else parent.rightChild = child;
		}
		
				
			
			

	// Returns the value associated with the parameter key.  Returns
	// null if the key is not found or the dictionary is empty.
	public V getValue(K key) {
		return findValue(key, root);
	}

	// Returns the key associated with the parameter value.  Returns
	// null if the value is not found in the dictionary.  If more 
	// than one key exists that matches the given value, returns the
	// first one found. 
	public K getKey(V value) {
		return keyFinder(value,root);
	}

	private K keyFinder(V value, Node<K,V> n){
		if (n == null) return null;
		if(((Comparable<V>)value).compareTo(n.value)==0)
			return n.key;
		K key = keyFinder(value,n.leftChild);
		if(key != null)
			return key;
		else return keyFinder(value,n.rightChild);
	}
	
	
	
	// Returns the number of key/value pairs currently stored 
	// in the dictionary
	public int size() {
		return currentSize;
	}

	// Returns true if the dictionary is at max capacity
	public boolean isFull() {
		return false;
	}

	// Returns true if the dictionary is empty
	public boolean isEmpty() {
		return currentSize == 0;
	}

	// Returns the Dictionary object to an empty state.
	public void clear() {
		currentSize = 0;
		modCounter = 0;
		root = null;
	}

	private int inOrder(Node<K,V> n, Node<K,V> [] array, int index){
		if(n != null){
			index = inOrder(n.leftChild,array,index);	
			array[index++] = n;
			index = inOrder(n.rightChild,array,index);
		}
		return index;	
	}
	
	abstract class IteratorHelper<E> implements Iterator<E>{
		protected Node<K,V>[] nodes;
		protected int index;
		protected long modCheck;
		
		public IteratorHelper() {
			nodes = new Node[currentSize];
			index = 0;
			int j = 0;
			modCheck = modCounter;
			inOrder(root,nodes,0);	
		}
		
		public boolean hasNext(){
			if(modCheck != modCounter)
				throw new ConcurrentModificationException();
			return index < currentSize;
		}
		public abstract E next();
		
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
	class KeyIteratorHelper<K> extends IteratorHelper<K>{
		public KeyIteratorHelper(){
			super();
		}
		public K next(){
			return (K) nodes[index++].key;
		}
		
	}
	class ValueIteratorHelper<V> extends IteratorHelper<V>{
		public ValueIteratorHelper(){
			super();
		}
		public V next(){
			return (V) nodes[index++].value;
		}
	}
	@Override
	public Iterator<K> keys() {
		return new KeyIteratorHelper();
	}

	@Override
	public Iterator<V> values() {
		return new ValueIteratorHelper();
	}

	}


