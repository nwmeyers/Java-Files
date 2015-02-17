package data_structures;
import java.util.Iterator;

import data_structures.*;

public class LatinDictionary {
    private static DictionaryADT<String,String> dictionary;
    private int maxSize = 10000;
    
    

    // constructor takes no arguments.  Size depends on the datafile.
    // creates an instance of the DictionaryADT. Use your HashTable 
    // implementation in this class (though all four should work).
    // Methods that make modifications to the dictionary modify the
    // DictionaryADT object, not the datafile.
    public LatinDictionary() {
//    	dictionary = new OrderedArrayDictionary(maxSize);
//    	dictionary = new HashTable<String,String>(maxSize);
//    	dictionary = new BinarySearchTree();
    	dictionary = new RedBlackTree();
	}

    public static int size(){
    	return dictionary.size();
    }
    public static void clear(){
    	dictionary.clear();
    }
    // reads the key=value pairs from the datafile and builds a dictionary structure 
    public void loadDictionary(String fileName) {    
    	DictionaryEntry [] entries = DictionaryReader.getDictionaryArray(fileName);  
    	for(DictionaryEntry n : entries)
    		dictionary.add(n.getKey(),n.getValue());
        }
    
    // inserts a new Latin word and its definition
    public boolean insertWord(String word, String definition) {
			return dictionary.add(word, definition);
        }

    // removes the key value pair that is identified by the key from the dictionary
    public boolean deleteWord(String word) {
		return dictionary.delete(word);
        }

    // looks up the definition of the Latin word
    public String getDefinition(String word) {
		return dictionary.getValue(word);
        }

    // returns true if the Latin word is already in the dictionary
    public boolean containsWord(String word) {
		return dictionary.contains(word);
        }
    
    // returns all of the keys in the dictionary within the range start .. finish
    // inclusive, in sorted order. Neither value 'start' or 'finish' need be in the
    // dictionary.  Returns null if there are no keys in the range specified.    
    public String[] getRange(String start, String finish) {
		Iterator<String> i = dictionary.keys();
		int counter = 0;
		while(i.hasNext()){
			String n = i.next();
			if(n.compareTo(start) >= 0 && n.compareTo(finish) <= 0) //counting the amount of start to finish elements there are.
				counter++;	
		}
		String [] array = new String [counter];
		int index = 0;
		i = dictionary.keys();
		while(i.hasNext()){
			String n = i.next();
			if(n.compareTo(start) >= 0 && n.compareTo(finish) <= 0)//using the counter, placing the elements into array
				array[index++] = n;
		}
		return array;    
    }
		
    	
            
    // returns an Iterator of the latin words (the keys) in the dictionary,
    // in sorted order.
    public Iterator<String> words() {
		return dictionary.keys();
        }

    // returns the definitions in the dictionary, in exactly the same order
    // as the words() Iterator
    public Iterator<String> definitions() {
		return dictionary.values();
        }
}