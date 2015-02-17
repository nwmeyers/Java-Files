package data_structures;

class DictionaryNode<K,V> implements Comparable<DictionaryNode<K,V>>{
	K key;
	V value;
	
public DictionaryNode(K key, V value) {
	this.key = key;
	this.value = value;
}
public int compareTo(DictionaryNode<K,V> node){
	return ((Comparable<K>)key).compareTo((K)node.key);
}
}
