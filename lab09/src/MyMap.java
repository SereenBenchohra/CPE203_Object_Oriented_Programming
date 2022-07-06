import java.util.List;
import java.util.ArrayList;

// You may not use any library classes other than List and ArrayList,
// above, to implement your map.  If the string "java." or "javax."
// occurs in this file after this, your submission will be rejected.


// DO NOT MODIFY ANYTHING IN THIS FILE UNTIL THIS LINE!
// Fill in you class after this.  Your submission will be 
// rejected by checkgit and by the autograder
// if you modify anything before this comment block.

/**
 * This class implements a map from any key type K to any value
 * type V.  The K type must have a valid equals() and hashCode() 
 * implementations.  MyMap<K, V> supports a public constructor that
 * takes a single int argument, giving the number of buckets for the
 * internal hashtable.
 * <p>
 * MyMap<K, V> supports a get() operation that takes
 * a key value, and delivers null if the key is not found, or a value
 * of type V (or a descendant of V) if the key is found.  The return type
 * of get() is V.
 * <p>
 * MyMap<K, V> further supports a put() operation that takes a key and a value,
 * in that order.  The value is associated with that key value, replacing any
 * other value that might have been stored at that key.
 * <p>
 * MyMap<K, V> also supports a method called getEntries() that takes no
 * arguments, and returns a List<MyMapEntry<K, V>> containing all of the
 * entries currently in the map.  MyMapEntry<K, V> has public final fields 
 * called "key" and "value".  
 * <p>
 * Finally, MyMap<K, V> supports a debugging method called getBuckets().
 * It delivers a List<List<MyMapEntry<K, V>>>, with one entry for each 
 * bucket in the internal hash table.  Because this is just a debugging
 * method, it's OK to return internal data structures; MyMap<K, V> needen't
 * make a defensive copy.  (A defensive copy is when you make a copy of
 * a data structure and return the copy, so a caller can't modify your
 * internal data structures).
 * <p>
 * All of the above methods are public.
 */


class MyMap<K, V> {

    public int numberBuckets;
    public List<MyMapEntry<K, V>> entriesList;
    public List<List<MyMapEntry<K, V>>> bucketsList;
    private K key;
    private V value;

    public MyMap(int numberBuckets){
        this.numberBuckets = numberBuckets;
        this.entriesList = new ArrayList<MyMapEntry<K, V>>();
        this.bucketsList = new ArrayList<List<MyMapEntry<K, V>>>(numberBuckets);
        for (int i = 0; i < numberBuckets; i++){
            List<MyMapEntry<K, V>> withinBucket = new ArrayList<MyMapEntry<K, V>>();
            withinBucket.add(null);
            bucketsList.add(i, withinBucket);

        }

    }

    public int hash(K key){
        int index = key.hashCode() % (numberBuckets);
        return index;
    }

    public V get(K key) {

        int hash = hash(key);
        if (bucketsList.get(hash) == null) {
            return null;
        } else {
            List<MyMapEntry<K, V>> tempList = bucketsList.get(hash);
            MyMapEntry<K, V> temp = tempList.get(0);
            while (temp != null) {
                if (temp.key.equals(key))
                    return temp.value;
                temp = temp.next;

            }
        }
        return null;
    }


    public void put(K key, V value) {

        if(key == null)
            return;

        int index = hash(key);

        MyMapEntry<K, V> entry = new MyMapEntry<K,V>(key, value);
        List<MyMapEntry<K, V>> withinBucket = bucketsList.get(index);

        if (withinBucket.get(0) == null) {
            withinBucket.set(0, entry);
            entriesList.add(entry);
            bucketsList.set(index, withinBucket);
        }
        else{
            MyMapEntry<K, V> currentEntry = withinBucket.get(0);
            MyMapEntry<K, V> previous = null;
            while (currentEntry != null) {
                if (currentEntry.key.equals(key)) {
                    if (previous == null) {
                        entry.setNext(currentEntry.next);
                        withinBucket.set(0, entry);
                        entriesList.remove(currentEntry);
                        entriesList.add(entry);
                        return;
                    }
                    else {
                        entry.setNext(currentEntry.next);
                        previous.next = entry;
                        int spot = withinBucket.indexOf(currentEntry);
                        withinBucket.set(spot, entry);
                        entriesList.remove(currentEntry);
                        entriesList.add(entry);
                        return;
                    }
                }
                previous = currentEntry;
                currentEntry = currentEntry.next;
            }
            previous.setNext(entry);
            withinBucket.add(entry);
            entriesList.add(entry);
        }
    }


    public List<MyMapEntry<K, V>> getEntries(){
        return entriesList;
    }

    public List<List<MyMapEntry<K, V>>> getBuckets(){
        return bucketsList;
    }

}



