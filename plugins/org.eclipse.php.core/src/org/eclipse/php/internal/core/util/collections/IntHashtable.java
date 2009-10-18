/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.util.collections;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class IntHashtable implements IntMap, Cloneable, Serializable {

	static final long serialVersionUID = -3094290553755493591L;
	/**
	 * The hash table data.
	 */
	private transient Entry table[];

	/**
	 * The total number of entries in the hash table.
	 */
	private transient int count;

	/**
	 * The table is rehashed when its size exceeds this threshold. (The value of
	 * this field is (int)(capacity * loadFactor).)
	 * 
	 * @serial
	 */
	private int threshold;

	/**
	 * The load factor for the hashtable.
	 * 
	 * @serial
	 */
	private float loadFactor;

	/**
	 * The number of times this Hashtable has been structurally modified
	 * Structural modifications are those that change the number of entries in
	 * the Hashtable or otherwise modify its internal structure (e.g., rehash).
	 * This field is used to make iterators on Collection-views of the Hashtable
	 * fail-fast. (See ConcurrentModificationException).
	 */
	private transient int modCount = 0;

	/**
	 * Constructs a new, empty hashtable with the specified initial capacity and
	 * the specified load factor.
	 * 
	 * @param initialCapacity
	 *            the initial capacity of the hashtable.
	 * @param loadFactor
	 *            the load factor of the hashtable.
	 * @exception IllegalArgumentException
	 *                if the initial capacity is less than zero, or if the load
	 *                factor is nonpositive.
	 */
	public IntHashtable(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException(
					"Illegal Capacity: " + initialCapacity); //$NON-NLS-1$
		if (loadFactor <= 0 || Float.isNaN(loadFactor))
			throw new IllegalArgumentException("Illegal Load: " + loadFactor); //$NON-NLS-1$

		if (initialCapacity == 0)
			initialCapacity = 1;
		this.loadFactor = loadFactor;
		table = new Entry[initialCapacity];
		threshold = (int) (initialCapacity * loadFactor);
	}

	/**
	 * Constructs a new, empty hashtable with the specified initial capacity and
	 * default load factor, which is <tt>0.75</tt>.
	 * 
	 * @param initialCapacity
	 *            the initial capacity of the hashtable.
	 * @exception IllegalArgumentException
	 *                if the initial capacity is less than zero.
	 */
	public IntHashtable(int initialCapacity) {
		this(initialCapacity, 0.75f);
	}

	/**
	 * Constructs a new, empty hashtable with a default capacity and load
	 * factor, which is <tt>0.75</tt>.
	 */
	public IntHashtable() {
		this(11, 0.75f);
	}

	/**
	 * Constructs a new hashtable with the same mappings as the given Map. The
	 * hashtable is created with a capacity of twice the number of entries in
	 * the given Map or 11 (whichever is greater), and a default load factor,
	 * which is <tt>0.75</tt>.
	 * 
	 * @param t
	 *            the map whose mappings are to be placed in this map.
	 * @since 1.2
	 */
	public IntHashtable(IntMap t) {
		this(Math.max(2 * t.size(), 11), 0.75f);
		putAll(t);
	}

	/**
	 * Returns the number of keys in this hashtable.
	 * 
	 * @return the number of keys in this hashtable.
	 */
	public int size() {
		return count;
	}

	/**
	 * Tests if this hashtable maps no keys to values.
	 * 
	 * @return <code>true</code> if this hashtable maps no keys to values;
	 *         <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return count == 0;
	}

	/**
	 * Returns an enumeration of the keys in this hashtable.
	 * 
	 * @return an enumeration of the keys in this hashtable.
	 * @see Enumeration
	 * @see #elements()
	 * @see #keySet()
	 * @see Map
	 * 
	 *      public synchronized Enumeration keys() { return
	 *      getEnumeration(KEYS); }
	 */

	/**
	 * Returns an enumeration of the values in this hashtable. Use the
	 * Enumeration methods on the returned object to fetch the elements
	 * sequentially.
	 * 
	 * @return an enumeration of the values in this hashtable.
	 * @see java.util.Enumeration
	 * @see #values()
	 * @see Map
	 */
	public synchronized Enumeration elements() {
		return getEnumeration(VALUES);
	}

	/**
	 * Tests if some key maps into the specified value in this hashtable. This
	 * operation is more expensive than the <code>containsKey</code> method.
	 * <p>
	 * 
	 * Note that this method is identical in functionality to containsValue,
	 * (which is part of the Map interface in the collections framework).
	 * 
	 * @param value
	 *            a value to search for.
	 * @return <code>true</code> if and only if some key maps to the
	 *         <code>value</code> argument in this hashtable as determined by
	 *         the <tt>equals</tt> method; <code>false</code> otherwise.
	 * @exception NullPointerException
	 *                if the value is <code>null</code>.
	 * @see #containsValue(Object)
	 * @see Map
	 */
	public synchronized boolean contains(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}

		Entry tab[] = table;
		for (int i = tab.length; i-- > 0;) {
			for (Entry e = tab[i]; e != null; e = e.next) {
				if (e.value.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns true if this Hashtable maps one or more keys to this value.
	 * <p>
	 * 
	 * Note that this method is identical in functionality to contains (which
	 * predates the Map interface).
	 * 
	 * @param value
	 *            value whose presence in this Hashtable is to be tested.
	 * @return <tt>true</tt> if this map maps one or more keys to the specified
	 *         value.
	 * @see Map
	 * @since 1.2
	 */
	public boolean containsValue(Object value) {
		return contains(value);
	}

	/**
	 * Tests if the specified object is a key in this hashtable.
	 * 
	 * @param key
	 *            possible key.
	 * @return <code>true</code> if and only if the specified object is a key in
	 *         this hashtable, as determined by the <tt>equals</tt> method;
	 *         <code>false</code> otherwise.
	 * @see #contains(Object)
	 */
	public synchronized boolean containsKey(int key) {
		Entry tab[] = table;
		int hash = key;// .hashCode();
		int index = (hash & 0x7FFFFFFF) % tab.length;
		for (Entry e = tab[index]; e != null; e = e.next) {
			if ((e.hash == hash) && e.key/* .equals( */== key/* ) */) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the value to which the specified key is mapped in this hashtable.
	 * 
	 * @param key
	 *            a key in the hashtable.
	 * @return the value to which the key is mapped in this hashtable;
	 *         <code>null</code> if the key is not mapped to any value in this
	 *         hashtable.
	 */
	public synchronized Object get(int key) {
		Entry tab[] = table;
		int hash = key;// .hashCode();
		int index = (hash & 0x7FFFFFFF) % tab.length;
		for (Entry e = tab[index]; e != null; e = e.next) {
			if ((e.hash == hash) && e.key/* .equals( */== key/* ) */) {
				return e.value;
			}
		}
		return null;
	}

	/**
	 * Increases the capacity of and internally reorganizes this hashtable, in
	 * order to accommodate and access its entries more efficiently. This method
	 * is called automatically when the number of keys in the hashtable exceeds
	 * this hashtable's capacity and load factor.
	 */
	protected void rehash() {
		int oldCapacity = table.length;
		Entry oldMap[] = table;

		int newCapacity = oldCapacity * 2 + 1;
		Entry newMap[] = new Entry[newCapacity];

		modCount++;
		threshold = (int) (newCapacity * loadFactor);
		table = newMap;

		for (int i = oldCapacity; i-- > 0;) {
			for (Entry old = oldMap[i]; old != null;) {
				Entry e = old;
				old = old.next;

				int index = (e.hash & 0x7FFFFFFF) % newCapacity;
				e.next = newMap[index];
				newMap[index] = e;
			}
		}
	}

	/**
	 * Maps the specified <code>key</code> to the specified <code>value</code>
	 * in this hashtable. Neither the key nor the value can be <code>null</code>
	 * .
	 * <p>
	 * 
	 * The value can be retrieved by calling the <code>get</code> method with a
	 * key that is equal to the original key.
	 * 
	 * @param key
	 *            the hashtable key.
	 * @param value
	 *            the value.
	 * @return the previous value of the specified key in this hashtable, or
	 *         <code>null</code> if it did not have one.
	 * @exception NullPointerException
	 *                if the key or value is <code>null</code>.
	 * @see Object#equals(Object)
	 */
	public synchronized Object put(/* Object */int key, Object value) {
		// Make sure the value is not null
		if (value == null) {
			throw new NullPointerException();
		}

		// Makes sure the key is not already in the hashtable.
		Entry tab[] = table;
		int hash = key/* .hashCode() */;
		int index = (hash & 0x7FFFFFFF) % tab.length;
		for (Entry e = tab[index]; e != null; e = e.next) {
			if ((e.hash == hash) && e.key == /* .equals( */key/* ) */) {
				Object old = e.value;
				e.value = value;
				return old;
			}
		}

		modCount++;
		if (count >= threshold) {
			// Rehash the table if the threshold is exceeded
			rehash();

			tab = table;
			index = (hash & 0x7FFFFFFF) % tab.length;
		}

		// Creates the new entry.
		Entry e = new Entry(hash, key, value, tab[index]);
		tab[index] = e;
		count++;
		return null;
	}

	/**
	 * Removes the key (and its corresponding value) from this hashtable. This
	 * method does nothing if the key is not in the hashtable.
	 * 
	 * @param key
	 *            the key that needs to be removed.
	 * @return the value to which the key had been mapped in this hashtable, or
	 *         <code>null</code> if the key did not have a mapping.
	 */
	public synchronized Object remove(/* Object */int key) {
		Entry tab[] = table;
		int hash = key;// .hashCode();
		int index = (hash & 0x7FFFFFFF) % tab.length;
		for (Entry e = tab[index], prev = null; e != null; prev = e, e = e.next) {
			if ((e.hash == hash) && e.key == /* .equals( */key/* ) */) {
				modCount++;
				if (prev != null) {
					prev.next = e.next;
				} else {
					tab[index] = e.next;
				}
				count--;
				Object oldValue = e.value;
				e.value = null;
				return oldValue;
			}
		}
		return null;
	}

	/**
	 * Copies all of the mappings from the specified Map to this Hashtable These
	 * mappings will replace any mappings that this Hashtable had for any of the
	 * keys currently in the specified Map.
	 * 
	 * @param t
	 *            Mappings to be stored in this map.
	 * @since 1.2
	 */
	public synchronized void putAll(IntMap t) {
		Iterator i = t.entrySet().iterator();
		while (i.hasNext()) {
			IntMap.Entry e = (IntMap.Entry) i.next();
			put(e.getKey(), e.getValue());
		}
	}

	/**
	 * Clears this hashtable so that it contains no keys.
	 */
	public synchronized void clear() {
		Entry tab[] = table;
		modCount++;
		for (int index = tab.length; --index >= 0;)
			tab[index] = null;
		count = 0;
	}

	/**
	 * Creates a shallow copy of this hashtable. All the structure of the
	 * hashtable itself is copied, but the keys and values are not cloned. This
	 * is a relatively expensive operation.
	 * 
	 * @return a clone of the hashtable.
	 */
	public synchronized Object clone() {
		try {
			IntHashtable t = (IntHashtable) super.clone();
			t.table = new Entry[table.length];
			for (int i = table.length; i-- > 0;) {
				t.table[i] = (table[i] != null) ? (Entry) table[i].clone()
						: null;
			}
			// t.keySet = null;
			t.entrySet = null;
			t.values = null;
			t.modCount = 0;
			return t;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}

	/**
	 * Returns a string representation of this <tt>Hashtable</tt> object in the
	 * form of a set of entries, enclosed in braces and separated by the ASCII
	 * characters "<tt>,&nbsp;</tt>" (comma and space). Each entry is rendered
	 * as the key, an equals sign <tt>=</tt>, and the associated element, where
	 * the <tt>toString</tt> method is used to convert the key and element to
	 * strings.
	 * <p>
	 * Overrides to <tt>toString</tt> method of <tt>Object</tt>.
	 * 
	 * @return a string representation of this hashtable.
	 */
	public synchronized String toString() {
		int max = size() - 1;
		StringBuffer buf = new StringBuffer();
		Iterator it = entrySet().iterator();

		buf.append("{"); //$NON-NLS-1$
		for (int i = 0; i <= max; i++) {
			Map.Entry e = (Map.Entry) (it.next());
			buf.append(e.getKey() + "=" + e.getValue()); //$NON-NLS-1$
			if (i < max)
				buf.append(", "); //$NON-NLS-1$
		}
		buf.append("}"); //$NON-NLS-1$
		return buf.toString();
	}

	private Enumeration getEnumeration(int type) {
		if (count == 0) {
			return emptyEnumerator;
		}
		return new Enumerator(type, false);
	}

	private Iterator getIterator(int type) {
		if (count == 0) {
			return emptyIterator;
		}
		return new Enumerator(type, true);
	}

	// Views

	// private transient Set keySet = null;
	private transient Set entrySet = null;
	private transient Collection values = null;

	/**
	 * Returns a Set view of the keys contained in this Hashtable. The Set is
	 * backed by the Hashtable, so changes to the Hashtable are reflected in the
	 * Set, and vice-versa. The Set supports element removal (which removes the
	 * corresponding entry from the Hashtable), but not element addition.
	 * 
	 * @return a set view of the keys contained in this map.
	 * @since 1.2
	 * 
	 *        public Set keySet() { if (keySet == null) keySet =
	 *        Collections.synchronizedSet(new KeySet(), this); return keySet; }
	 * 
	 *        private class KeySet extends AbstractSet { public Iterator
	 *        iterator() { return getIterator(KEYS); } public int size() {
	 *        return count; } public boolean contains(/*Object* o) { return
	 *        containsKey(o); } public boolean remove(Object o) { return
	 *        Hashtable.this.remove(o) != null; } public void clear() {
	 *        Hashtable.this.clear(); } }
	 */

	/**
	 * Returns a Set view of the entries contained in this Hashtable. Each
	 * element in this collection is a Map.Entry. The Set is backed by the
	 * Hashtable, so changes to the Hashtable are reflected in the Set, and
	 * vice-versa. The Set supports element removal (which removes the
	 * corresponding entry from the Hashtable), but not element addition.
	 * 
	 * @return a set view of the mappings contained in this map.
	 * @see Map.Entry
	 * @since 1.2
	 */
	public Set entrySet() {
		if (entrySet == null)
			entrySet = Collections.synchronizedSet(new EntrySet());
		return entrySet;
	}

	private class EntrySet extends AbstractSet {
		public Iterator iterator() {
			return getIterator(ENTRIES);
		}

		public boolean contains(Object o) {
			if (!(o instanceof IntMap.Entry))
				return false;
			IntMap.Entry entry = (IntMap.Entry) o;
			/* Object */int key = entry.getKey();
			Entry tab[] = table;
			int hash = key;// .hashCode();
			int index = (hash & 0x7FFFFFFF) % tab.length;

			for (Entry e = tab[index]; e != null; e = e.next)
				if (e.hash == hash && e.equals(entry))
					return true;
			return false;
		}

		public boolean remove(Object o) {
			if (!(o instanceof IntMap.Entry))
				return false;
			IntMap.Entry entry = (IntMap.Entry) o;
			/* Object */int key = entry.getKey();
			Entry tab[] = table;
			int hash = key;// .hashCode();
			int index = (hash & 0x7FFFFFFF) % tab.length;

			for (Entry e = tab[index], prev = null; e != null; prev = e, e = e.next) {
				if (e.hash == hash && e.equals(entry)) {
					modCount++;
					if (prev != null)
						prev.next = e.next;
					else
						tab[index] = e.next;

					count--;
					e.value = null;
					return true;
				}
			}
			return false;
		}

		public int size() {
			return count;
		}

		public void clear() {
			IntHashtable.this.clear();
		}
	}

	/**
	 * Returns a Collection view of the values contained in this Hashtable. The
	 * Collection is backed by the Hashtable, so changes to the Hashtable are
	 * reflected in the Collection, and vice-versa. The Collection supports
	 * element removal (which removes the corresponding entry from the
	 * Hashtable), but not element addition.
	 * 
	 * @return a collection view of the values contained in this map.
	 * @since 1.2
	 */
	public Collection values() {
		if (values == null)
			values = Collections.synchronizedCollection(new ValueCollection());
		return values;
	}

	private class ValueCollection extends AbstractCollection {
		public Iterator iterator() {
			return getIterator(VALUES);
		}

		public int size() {
			return count;
		}

		public boolean contains(Object o) {
			return containsValue(o);
		}

		public void clear() {
			IntHashtable.this.clear();
		}
	}

	// Comparison and hashing

	/**
	 * Compares the specified Object with this Map for equality, as per the
	 * definition in the Map interface.
	 * 
	 * @return true if the specified Object is equal to this Map.
	 * @see Map#equals(Object)
	 * @since 1.2
	 */
	public synchronized boolean equals(Object o) {
		if (o == this)
			return true;

		if (!(o instanceof IntMap))
			return false;
		IntMap t = (IntMap) o;
		if (t.size() != size())
			return false;

		Iterator i = entrySet().iterator();
		while (i.hasNext()) {
			IntMap.Entry e = (IntMap.Entry) i.next();
			/* Object */int key = e.getKey();
			Object value = e.getValue();
			if (value == null) {
				if (!(t.get(key) == null && t.containsKey(key)))
					return false;
			} else {
				if (!value.equals(t.get(key)))
					return false;
			}
		}
		return true;
	}

	/**
	 * Returns the hash code value for this Map as per the definition in the Map
	 * interface.
	 * 
	 * @see Map#hashCode()
	 * @since 1.2
	 */
	public synchronized int hashCode() {
		int h = 0;
		Iterator i = entrySet().iterator();
		while (i.hasNext())
			h += i.next().hashCode();
		return h;
	}

	/**
	 * Save the state of the Hashtable to a stream (i.e., serialize it).
	 * 
	 * @serialData The <i>capacity</i> of the Hashtable (the length of the
	 *             bucket array) is emitted (int), followed by the <i>size</i>
	 *             of the Hashtable (the number of key-value mappings), followed
	 *             by the key (Object) and value (Object) for each key-value
	 *             mapping represented by the Hashtable The key-value mappings
	 *             are emitted in no particular order.
	 */
	private synchronized void writeObject(java.io.ObjectOutputStream s)
			throws IOException {
		// Write out the length, threshold, loadfactor
		s.defaultWriteObject();

		// Write out length, count of elements and then the key/value objects
		s.writeInt(table.length);
		s.writeInt(count);
		for (int index = table.length - 1; index >= 0; index--) {
			Entry entry = table[index];

			while (entry != null) {
				s.write(entry.key);
				s.writeObject(entry.value);
				entry = entry.next;
			}
		}
	}

	/**
	 * Reconstitute the Hashtable from a stream (i.e., deserialize it).
	 */
	private synchronized void readObject(java.io.ObjectInputStream s)
			throws IOException, ClassNotFoundException {
		// Read in the length, threshold, and loadfactor
		s.defaultReadObject();

		// Read the original length of the array and number of elements
		int origlength = s.readInt();
		int elements = s.readInt();

		// Compute new size with a bit of room 5% to grow but
		// No larger than the original size. Make the length
		// odd if it's large enough, this helps distribute the entries.
		// Guard against the length ending up zero, that's not valid.
		int length = (int) (elements * loadFactor) + (elements / 20) + 3;
		if (length > elements && (length & 1) == 0)
			length--;
		if (origlength > 0 && length > origlength)
			length = origlength;

		table = new Entry[length];
		count = 0;

		// Read the number of elements and then all the key/value objects
		for (; elements > 0; elements--) {
			/* Object */int key = s.readInt();
			Object value = s.readObject();
			put(key, value);
		}
	}

	/**
	 * Hashtable collision list.
	 */
	private static class Entry implements IntMap.Entry {
		int hash;
		int key;
		Object value;
		Entry next;

		protected Entry(int hash, int key, Object value, Entry next) {
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
		}

		protected Object clone() {
			return new Entry(hash, key, value, (next == null ? null
					: (Entry) next.clone()));
		}

		// Map.Entry Ops

		public int getKey() {
			return key;
		}

		public Object getValue() {
			return value;
		}

		public Object setValue(Object value) {
			if (value == null)
				throw new NullPointerException();

			Object oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public boolean equals(Object o) {
			if (!(o instanceof IntMap.Entry))
				return false;
			IntMap.Entry e = (IntMap.Entry) o;

			return (key == 0 ? e.getKey() == 0 : key/* .equals( */== e.getKey()/* ) */)
					&& (value == null ? e.getValue() == null : value.equals(e
							.getValue()));
		}

		public int hashCode() {
			return hash ^ (value == null ? 0 : value.hashCode());
		}

		public String toString() {
			return key/* .toString() */+ "=" + value.toString(); //$NON-NLS-1$
		}
	}

	// Types of Enumerations/Iterations
	// private static final int KEYS = 0;
	private static final int VALUES = 1;
	private static final int ENTRIES = 2;

	/**
	 * A hashtable enumerator class. This class implements both the Enumeration
	 * and Iterator interfaces, but individual instances can be created with the
	 * Iterator methods disabled. This is necessary to avoid unintentionally
	 * increasing the capabilities granted a user by passing an Enumeration.
	 */
	private class Enumerator implements Enumeration, Iterator {
		Entry[] table = IntHashtable.this.table;
		int index = table.length;
		Entry entry = null;
		Entry lastReturned = null;
		int type;

		/**
		 * Indicates whether this Enumerator is serving as an Iterator or an
		 * Enumeration. (true -> Iterator).
		 */
		boolean iterator;

		/**
		 * The modCount value that the iterator believes that the backing List
		 * should have. If this expectation is violated, the iterator has
		 * detected concurrent modification.
		 */
		protected int expectedModCount = modCount;

		Enumerator(int type, boolean iterator) {
			this.type = type;
			this.iterator = iterator;
		}

		public boolean hasMoreElements() {
			Entry e = entry;
			int i = index;
			Entry t[] = table;
			/* Use locals for faster loop iteration */
			while (e == null && i > 0) {
				e = t[--i];
			}
			entry = e;
			index = i;
			return e != null;
		}

		public Object nextElement() {
			Entry et = entry;
			int i = index;
			Entry t[] = table;
			/* Use locals for faster loop iteration */
			while (et == null && i > 0) {
				et = t[--i];
			}
			entry = et;
			index = i;
			if (et != null) {
				Entry e = lastReturned = entry;
				entry = e.next;
				// return type == KEYS ? e.key : (type == VALUES ? e.value : e);
				return type == VALUES ? e.value : e;
			}
			throw new NoSuchElementException("Hashtable Enumerator"); //$NON-NLS-1$
		}

		// Iterator methods
		public boolean hasNext() {
			return hasMoreElements();
		}

		public Object next() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			return nextElement();
		}

		public void remove() {
			if (!iterator)
				throw new UnsupportedOperationException();
			if (lastReturned == null)
				throw new IllegalStateException("Hashtable Enumerator"); //$NON-NLS-1$
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();

			synchronized (IntHashtable.this) {
				Entry[] tab = IntHashtable.this.table;
				int index = (lastReturned.hash & 0x7FFFFFFF) % tab.length;

				for (Entry e = tab[index], prev = null; e != null; prev = e, e = e.next) {
					if (e == lastReturned) {
						modCount++;
						expectedModCount++;
						if (prev == null)
							tab[index] = e.next;
						else
							prev.next = e.next;
						count--;
						lastReturned = null;
						return;
					}
				}
				throw new ConcurrentModificationException();
			}
		}
	}

	private static EmptyEnumerator emptyEnumerator = new EmptyEnumerator();
	private static EmptyIterator emptyIterator = new EmptyIterator();

	/**
	 * A hashtable enumerator class for empty hash tables, specializes the
	 * general Enumerator
	 */
	private static class EmptyEnumerator implements Enumeration {

		EmptyEnumerator() {
		}

		public boolean hasMoreElements() {
			return false;
		}

		public Object nextElement() {
			throw new NoSuchElementException("Hashtable Enumerator"); //$NON-NLS-1$
		}
	}

	/**
	 * A hashtable iterator class for empty hash tables
	 */
	private static class EmptyIterator implements Iterator {

		EmptyIterator() {
		}

		public boolean hasNext() {
			return false;
		}

		public Object next() {
			throw new NoSuchElementException("Hashtable Iterator"); //$NON-NLS-1$
		}

		public void remove() {
			throw new IllegalStateException("Hashtable Iterator"); //$NON-NLS-1$
		}

	}

}
