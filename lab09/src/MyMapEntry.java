class MyMapEntry<K, V> {
    public final K key;
    public final V value;
    public MyMapEntry<K, V> next = null;

    public MyMapEntry(K key, V value){
        this.key = key;
        this.value = value;
    }

    public void setNext(MyMapEntry<K, V> next){
        this.next = next;
    }

    public MyMapEntry getNext(){
        return this.next;
    }

    @Override
    public String toString() {
        return "MyMapEntry" + "(" + this.key + ", " + this.value + ")";
    }
}