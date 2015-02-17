/*  DictionaryADT.java
    Dictionary interface.
    CS310, Fall 2014
    Alan Riggins
 */    

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import data_structures.HashTable.DictionaryEntry;



public class OrderedArrayDictionary<K,V> implements DictionaryADT<K,V> {
	private int currentSize,maxSize;
	private DictionaryEntry<K,V> [] storage;
	private long modCounter;

	public OrderedArrayDictionary(int DICTIONARY_SIZE) {
		currentSize = 0;
		modCounter = 0;
		maxSize = DICTIONARY_SIZE;
		storage = new DictionaryEntry[maxSize];

	}
	
	public class DictionaryEntry<K,V> implements Comparable<DictionaryEntry<K,V>> {
        private K key;
        private V value;
		
        
        public DictionaryEntry(K key, V value) {
            this.key = key;
            this.value = value;
            }
        
        public K getKey() {
            return key;
            }
            
        public V getValue() {
            return value;
        }
            
            public int compareTo(DictionaryEntry<K,V> node){
            	return ((Comparable<K>)key).compareTo((K)node.key);
            }
            
        }

	private int findIt(K key,int lo, int hi) {
		if (hi<lo)
			return lo;
		int mid = (lo+hi) / 2; //mid
		if( ((Comparable<K>)key).compareTo(storage[mid].getKey()) < 0)
			return findIt(key,lo,mid-1);
		return findIt(key,mid+1,hi); }
	
    private int binSearch(K key,int lo, int hi) {
    	if (hi<lo)
    		return -1;
    	int mid = (lo+hi) / 2; //mid
    	int comp = ((Comparable<K>)key).compareTo(storage[mid].getKey());
    	if (comp == 0) 
    		return mid; //found
    	if (comp < 0)
    		return binSearch(key,lo,mid-1);
    	return binSearch(key,mid+1,hi); }

	// Returns true if the dictionary has an object identified by
	// key in it, otherwise false.
	public boolean contains(K key) {
	    	if (binSearch(key,0,currentSize-1) == -1)
	    		return false;
	    	return true;
		}

	// Adds the given key/value pair to the dictionary.  Returns 
	// false if the dictionary is full, or if the key is a duplicate.
	// Returns true if addition succeeded.
	public boolean add(K key, V value) {
		if(currentSize == maxSize)
			return false; 


		int where = findIt(key,0,currentSize-1);

		for(int i = currentSize-1;  i >= where  ; i--)
			storage[i+1]=storage[i];
		storage[where] = new DictionaryEntry(key,value);
		currentSize++;
		modCounter++;
		return true;
	}

	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	public boolean delete(K key) {
		int where = binSearch(key,0,currentSize-1);							//finds the location of the obj
   	 	if(where == -1)
   	 		return false;
   	 	
    	for(int i = where; i < currentSize-1; i++)					//count up from i (not down)
    			storage[i]=storage[i+1];									//move one cell to the left
    		currentSize--;
    		modCounter++;
    		return true;
	}

	// Returns the value associated with the parameter key.  Returns
	// null if the key is not found or the dictionary is empty.
	public V getValue(K key) {
		if(currentSize == 0){
			return null;
		}
		int where = binSearch(key,0,currentSize-1);
		if(where == -1) return null;
		return (V) storage[where].getValue();

	}

	// Returns the key associated with the parameter value.  Returns
	// null if the value is not found in the dictionary.  If more 
	// than one key exists that matches the given value, returns the
	// first one found. 
	public K getKey(V value) {
		if(currentSize == 0)
			return null;

		DictionaryEntry tmp = storage[0];
		while(tmp != null)
			for(int i = 0; i < currentSize;i++)
				if(((Comparable) value).compareTo(storage[i].getValue()) == 0){
					return storage[i].getKey();
				}
		return null;
	}

	// Returns the number of key/value pairs currently stored 
	// in the dictionary
	public int size() {
		return currentSize;
	}

	// Returns true if the dictionary is at max capacity
	public boolean isFull() {
		return currentSize == maxSize;
	}

	// Returns true if the dictionary is empty
	public boolean isEmpty() {
		return currentSize == 0;
	}

	// Returns the Dictionary object to an empty state.
	public void clear() {
		currentSize = 0;
		modCounter = 0;
	}


	// Returns an Iterator of the keys in the dictionary, in ascending
	// sorted order.  The iterator must be fail-fast.
	public Iterator<K> keys() {
		return (Iterator<K>) new KeyIteratorHelper();
	}

	class KeyIteratorHelper implements Iterator<K> {
		int iterIndex;
		long checkCheck;

		public KeyIteratorHelper() {
			iterIndex = 0;
			checkCheck = modCounter;
		}

		public boolean hasNext() {
			if(checkCheck != modCounter)
				throw new ConcurrentModificationException();
			return iterIndex < currentSize;
		}

		public K next() {
			if(!hasNext())
				throw new NoSuchElementException();
			return (K) storage[iterIndex++].getKey();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	// Returns an Iterator of the values in the dictionary.  The
	// order of the values must match the order of the keys. 
	// The iterator must be fail-fast. 
	public Iterator<V> values() {
		return (Iterator<V>) new ValueIteratorHelper();
	}

	class ValueIteratorHelper implements Iterator<V> {
		int iterIndex;
		long checkCheck;

		public ValueIteratorHelper() {
			iterIndex = 0;
			checkCheck = modCounter;
		}

		public boolean hasNext() {
			if(checkCheck != modCounter)
				throw new ConcurrentModificationException();
			return iterIndex < currentSize;
		}

		public V next() {
			if(!hasNext())
				throw new NoSuchElementException();
			return (V) storage[iterIndex++].getValue();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}


}
