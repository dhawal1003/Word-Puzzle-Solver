package puzzle;

//******************PUBLIC OPERATIONS*********************

//void insert( x )       --> Insert x
//bool remove( x )       --> Remove x
//bool contains( x )     --> Return true if x is present
//void makeEmpty( )      --> Remove all items

public class MyHashTable {

	private static final int DEFAULT_TABLE_SIZE = 101001;
	private String[] array;
	private int currentSize;

	public MyHashTable() {

		array = new String[nextPrime(DEFAULT_TABLE_SIZE)];
		currentSize = 0;
	}

	private void clear() {
		for (int i = 0; i < array.length; i++)
			array[i] = null;
		currentSize = 0;
	}

	public void makeEmpty() {
		clear();
	}

	public void insert(String x) {

		int idx = myHash(x);

		array[idx] = x;
		currentSize++;

		if (currentSize >= array.length / 2) {
			rehash();
		}

	}

	public boolean contains(String x) {

		if (array[myHash(x)] != null) {
			return true;
		} else {
			return false;
		}

	}

	public boolean remove(String x) {
		if (contains(x)) {
			array[myHash(x)] = null;
			currentSize--;
			return true;
		}
		return false;

	}

	private void rehash() {

		String oldarray[] = array;

		array = new String[nextPrime(array.length * 2)];
		clear();

		for (String s : oldarray) {
			if (s != null)
				insert(s);
		}

	}

	private int myHash(String x) {

		int hashValue = x.hashCode();

		hashValue %= array.length;

		if (hashValue < 0) {
			hashValue += array.length;
		}

		int currentidx = hashValue;
		int i = 1;

		while (array[hashValue] != null && !array[hashValue].equals(x)) {
			hashValue = currentidx + i * i * i * i;
			hashValue %= array.length;
			i++;
		}

		return hashValue;
	}

	private static int nextPrime(int n) {
		if (n % 2 == 0)
			n++;

		for (; !isPrime(n); n += 2)
			;

		return n;
	}

	private static boolean isPrime(int n) {
		if (n == 2 || n == 3)
			return true;

		if (n == 1 || n % 2 == 0)
			return false;

		for (int i = 3; i * i <= n; i += 2)
			if (n % i == 0)
				return false;

		return true;
	}

}
