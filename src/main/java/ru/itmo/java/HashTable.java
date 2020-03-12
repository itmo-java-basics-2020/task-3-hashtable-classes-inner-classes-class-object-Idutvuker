package ru.itmo.java;

public class HashTable
{
    private class Pair {
        public final Object key;
        public final Object value;

        private Pair(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    private Pair[] table;
    private float loadFactor = 0.5f;
    private int capacity;
    private int threshold;
    private int size = 0;

    private int getHash(Object key) {
        return key.hashCode() % capacity;
    }

    HashTable(int initialCapacity) {
        this.capacity = initialCapacity;
        threshold = (int) (capacity * loadFactor);
        table = new Pair[capacity];
    }

    HashTable(int initialCapacity, float loadFactor) {
        this.loadFactor = loadFactor;
        this.capacity = initialCapacity;
        threshold = (int) (capacity * loadFactor);
        table = new Pair[capacity];
    }

    Object put(Object key, Object value) {
        int hc = getHash(key);
        while (table[hc] != null && !table[hc].key.equals(key)) {
            hc = (hc + 1) % this.capacity;
        }

        table[hc] = value;
        size++;
    }

    Object get(Object key) {
        throw new UnsupportedOperationException();
    }

    Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    int size() {
        return size;
    }

}
