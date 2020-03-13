package ru.itmo.java;

import static java.lang.Math.abs;

public class HashTable {
	private class Pair {
		private final Object key;
		private final Object value;
		private boolean deleted = false;

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
	private int realSize = 0;


	private int getHash(Object key) {
		return abs(key.hashCode() % capacity);
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

	private int find(Object key, boolean findDeleted) {
		int hc = getHash(key);

		while (true) {
			if (table[hc] == null) {
				break;
			} else if (table[hc].key.equals(key) && (!table[hc].deleted || findDeleted)) {
				break;
			}

			hc = (hc + 1) % this.capacity;
		}

		return hc;
	}


	private void resize() {
		Pair[] oldTable = table;

		//System.out.println(realSize + " " + capacity + " " + threshold);

		capacity *= 2;
		threshold = (int) (capacity * loadFactor);
		size = 0;
		realSize = 0;

		table = new Pair[capacity];

		for (Pair pair : oldTable) {
			if (pair != null && !pair.deleted) {
				put(pair.key, pair.value);
			}
		}

		//System.out.println("New size:" + size +" "+ realSize);
	}

	Object put(Object key, Object value) {
		//System.out.println("put "+ key);

		int hc = find(key, true);

		if (table[hc] == null) {
			table[hc] = new Pair(key, value);
			size++;
			realSize++;

			if (realSize == threshold) {
				resize();
			}

			return null;
		} else if (table[hc].deleted) {
			table[hc] = new Pair(key, value);
			size++;
			return null;
		} else {
			Object oldVal = table[hc].value;
			table[hc] = new Pair(key, value);

			return oldVal;
		}
	}

	Object get(Object key) {
		//System.out.println("get " + key);
		int hc = find(key, false);

		return (table[hc] == null) ? null : table[hc].value;
	}

	Object remove(Object key) {
		//System.out.println("remove " + key);
		int hc = find(key, false);

		if (table[hc] != null) {
			table[hc].deleted = true;
			size--;
			return table[hc].value;
		}

		return null;
	}

	int size() {
		return size;
	}

}
