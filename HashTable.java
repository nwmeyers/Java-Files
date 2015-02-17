package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;


public class HashTable<K,V> implements DictionaryADT<K,V> {
	private int currentSize,tableSize,maxSize;
	private long modCounter;
    private UnorderedList <DictionaryEntry<K,V>> [] list;
    
	public HashTable(int wholeSize){
	this.currentSize = 0;
	this.maxSize = wholeSize;
	this.tableSize = (int)(1.33 * wholeSize);
	this.modCounter = 0;	
	list = new UnorderedList[tableSize];
	for(int i = 0; i < tableSize;i++)
		list[i] = new UnorderedList<DictionaryEntry<K,V>>();
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

	 public  int getIndex(K key){
		int index;
		return  index = (key.hashCode() & 0x7FFFFFFF ) % tableSize;
	 }
	 
	// Returns true if the dictionary has an object identified by
	// key in it, otherwise false.
	public boolean contains(K key) {
		if(currentSize == 0)
			return false;
		int where = getIndex(key);
		return list[where].contains(new DictionaryEntry<K,V>(key,null));
	
	}

	// Adds the given key/value pair to the dictionary.  Returns 
	// false if the dictionary is full, or if the key is a duplicate.
	// Returns true if addition succeeded.
	public boolean add(K key, V value) {
		if(currentSize == maxSize) 
			return false;
		if(contains(key))
			return false;
		int where = getIndex(key);
		list[where].insertFront(new DictionaryEntry<K, V>(key,value));
		currentSize++;
		modCounter++;
		return true;
	}

	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	public boolean delete(K key) {
		if(currentSize == 0)
		return false;
		if(!contains(key))
			return false;
		int where = getIndex(key);
		list[where].remove(new DictionaryEntry<K,V>(key,null));
		currentSize--;
		modCounter++;
		return true;	
		
	}

	// Returns the value associated with the parameter key.  Returns
	// null if the key is not found or the dictionary is empty.
	public V getValue(K key) {
		if(currentSize == 0)
			return null;
		int where = getIndex(key);
		DictionaryEntry<K,V> entry = list[where].peek(new DictionaryEntry<K,V>(key,null));
		if(entry == null)
			return null;
		return entry.getValue();
		
	}

	// Returns the key associated with the parameter value.  Returns
	// null if the value is not found in the dictionary.  If more 
	// than one key exists that matches the given value, returns the
	// first one found. 
	public K getKey(V value) {
		if(currentSize == 0)
			return null;
		for(int i = 0; i < tableSize; i++)
			for(DictionaryEntry<K,V> n : list[i])
				if(((Comparable<V>)value).compareTo(n.getValue()) == 0)
					return n.getKey();
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
		for(int i = 0; i < tableSize; i++)
			list[i].clear();
		currentSize = 0;
		modCounter = 0;
	}
	
private DictionaryEntry<K,V>[] shellSort(DictionaryEntry<K, V>[] nodes){
	DictionaryEntry<K, V>[] n = nodes;
	int in, out, h=1;
	DictionaryEntry<K, V> temp;
	int size = n.length;
	
	while(h <= size/3) //calculate gaps
		h = h*3+1;
	while(h > 0){
		for(out = h; out < size; out++){
			temp = n[out];
			in = out;
			while(in > h - 1 && n[in - h].compareTo(temp) >= 0){
				n[in] = n[in-h];
				in -= h;
			}
			n[in] = temp;
			}
		h = (h-1)/3;
		}
	return n;
	}
		
		
		
	
	abstract class IteratorHelper<E> implements Iterator<E>{
		protected DictionaryEntry<K,V> [] nodes;
		protected int index;
		protected long modCheck;
		
		public IteratorHelper() {
			nodes = new DictionaryEntry[currentSize];
			index = 0;
			int j = 0;
			modCheck = modCounter;
			for(int i=0; i< tableSize; i++)
				for(DictionaryEntry n : list[i])
					nodes[j++] = n;
			nodes = (DictionaryEntry<K,V>[]) shellSort(nodes);			
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
			return (K) nodes[index++].getKey();
		}
		
	}
	class ValueIteratorHelper<V> extends IteratorHelper<V>{
		public ValueIteratorHelper(){
			super();
		}
		public V next(){
			return (V) nodes[index++].getValue();
		}
	}
	@Override
	public Iterator<K> keys() {
		return new KeyIteratorHelper<K>();
	}

	@Override
	public Iterator<V> values() {
		return new ValueIteratorHelper<V>();
	}
	}

	



