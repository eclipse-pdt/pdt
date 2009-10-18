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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IntMap {

	/**
	 * Returns the number of key-value mappings in this map. If the map contains
	 * more than <tt>Integer.MAX_VALUE</tt> elements, returns
	 * <tt>Integer.MAX_VALUE</tt>.
	 * 
	 * @return the number of key-value mappings in this map.
	 */
	int size();

	/**
	 * Returns <tt>true</tt> if this map contains no key-value mappings.
	 * 
	 * @return <tt>true</tt> if this map contains no key-value mappings.
	 */
	boolean isEmpty();

	/**
	 * Returns <tt>true</tt> if this map contains a mapping for the specified
	 * key.
	 * 
	 * @param key
	 *            key whose presence in this map is to be tested.
	 * @return <tt>true</tt> if this map contains a mapping for the specified
	 *         key.
	 * 
	 * @throws ClassCastException
	 *             if the key is of an inappropriate type for this map.
	 * @throws NullPointerException
	 *             if the key is <tt>null</tt> and this map does not not permit
	 *             <tt>null</tt> keys.
	 */
	boolean containsKey(int key);

	/**
	 * Returns <tt>true</tt> if this map maps one or more keys to the specified
	 * value. More formally, returns <tt>true</tt> if and only if this map
	 * contains at least one mapping to a value <tt>v</tt> such that
	 * <tt>(value==null ? v==null : value.equals(v))</tt>. This operation will
	 * probably require time linear in the map size for most implementations of
	 * the <tt>Map</tt> interface.
	 * 
	 * @param value
	 *            value whose presence in this map is to be tested.
	 * @return <tt>true</tt> if this map maps one or more keys to the specified
	 *         value.
	 */
	boolean containsValue(Object value);

	/**
	 * Returns the value to which this map maps the specified key. Returns
	 * <tt>null</tt> if the map contains no mapping for this key. A return value
	 * of <tt>null</tt> does not <i>necessarily</i> indicate that the map
	 * contains no mapping for the key; it's also possible that the map
	 * explicitly maps the key to <tt>null</tt>. The <tt>containsKey</tt>
	 * operation may be used to distinguish these two cases.
	 * 
	 * @param key
	 *            key whose associated value is to be returned.
	 * @return the value to which this map maps the specified key, or
	 *         <tt>null</tt> if the map contains no mapping for this key.
	 * 
	 * @throws ClassCastException
	 *             if the key is of an inappropriate type for this map.
	 * @throws NullPointerException
	 *             key is <tt>null</tt> and this map does not not permit
	 *             <tt>null</tt> keys.
	 * 
	 * @see #containsKey(Object)
	 */
	Object get(int key);

	// Modification Operations

	/**
	 * Associates the specified value with the specified key in this map
	 * (optional operation). If the map previously contained a mapping for this
	 * key, the old value is replaced.
	 * 
	 * @param key
	 *            key with which the specified value is to be associated.
	 * @param value
	 *            value to be associated with the specified key.
	 * @return previous value associated with specified key, or <tt>null</tt> if
	 *         there was no mapping for key. A <tt>null</tt> return can also
	 *         indicate that the map previously associated <tt>null</tt> with
	 *         the specified key, if the implementation supports <tt>null</tt>
	 *         values.
	 * 
	 * @throws UnsupportedOperationException
	 *             if the <tt>put</tt> operation is not supported by this map.
	 * @throws ClassCastException
	 *             if the class of the specified key or value prevents it from
	 *             being stored in this map.
	 * @throws IllegalArgumentException
	 *             if some aspect of this key or value prevents it from being
	 *             stored in this map.
	 * @throws NullPointerException
	 *             this map does not permit <tt>null</tt> keys or values, and
	 *             the specified key or value is <tt>null</tt>.
	 */
	Object put(int key, Object value);

	/**
	 * Removes the mapping for this key from this map if present (optional
	 * operation).
	 * 
	 * @param key
	 *            key whose mapping is to be removed from the map.
	 * @return previous value associated with specified key, or <tt>null</tt> if
	 *         there was no mapping for key. A <tt>null</tt> return can also
	 *         indicate that the map previously associated <tt>null</tt> with
	 *         the specified key, if the implementation supports <tt>null</tt>
	 *         values.
	 * @throws UnsupportedOperationException
	 *             if the <tt>remove</tt> method is not supported by this map.
	 */
	Object remove(int key);

	// Bulk Operations

	/**
	 * Copies all of the mappings from the specified map to this map (optional
	 * operation). These mappings will replace any mappings that this map had
	 * for any of the keys currently in the specified map.
	 * 
	 * @param t
	 *            Mappings to be stored in this map.
	 * 
	 * @throws UnsupportedOperationException
	 *             if the <tt>putAll</tt> method is not supported by this map.
	 * 
	 * @throws ClassCastException
	 *             if the class of a key or value in the specified map prevents
	 *             it from being stored in this map.
	 * 
	 * @throws IllegalArgumentException
	 *             some aspect of a key or value in the specified map prevents
	 *             it from being stored in this map.
	 * 
	 * @throws NullPointerException
	 *             this map does not permit <tt>null</tt> keys or values, and
	 *             the specified key or value is <tt>null</tt>.
	 */
	void putAll(IntMap t);

	/**
	 * Removes all mappings from this map (optional operation).
	 * 
	 * @throws UnsupportedOperationException
	 *             clear is not supported by this map.
	 */
	void clear();

	// Views

	/**
	 * Returns a collection view of the values contained in this map. The
	 * collection is backed by the map, so changes to the map are reflected in
	 * the collection, and vice-versa. If the map is modified while an iteration
	 * over the collection is in progress, the results of the iteration are
	 * undefined. The collection supports element removal, which removes the
	 * corresponding mapping from the map, via the <tt>Iterator.remove</tt>,
	 * <tt>Collection.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
	 * <tt>clear</tt> operations. It does not support the add or <tt>addAll</tt>
	 * operations.
	 * 
	 * @return a collection view of the values contained in this map.
	 */
	public Collection values();

	/**
	 * Returns a set view of the mappings contained in this map. Each element in
	 * the returned set is a <tt>Map.Entry</tt>. The set is backed by the map,
	 * so changes to the map are reflected in the set, and vice-versa. If the
	 * map is modified while an iteration over the set is in progress, the
	 * results of the iteration are undefined. The set supports element removal,
	 * which removes the corresponding mapping from the map, via the
	 * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>, <tt>removeAll</tt>,
	 * <tt>retainAll</tt> and <tt>clear</tt> operations. It does not support the
	 * <tt>add</tt> or <tt>addAll</tt> operations.
	 * 
	 * @return a set view of the mappings contained in this map.
	 */
	public Set entrySet();

	/**
	 * A map entry (key-value pair). The <tt>Map.entrySet</tt> method returns a
	 * collection-view of the map, whose elements are of this class. The
	 * <i>only</i> way to obtain a reference to a map entry is from the iterator
	 * of this collection-view. These <tt>Map.Entry</tt> objects are valid
	 * <i>only</i> for the duration of the iteration; more formally, the
	 * behavior of a map entry is undefined if the backing map has been modified
	 * after the entry was returned by the iterator, except through the
	 * iterator's own <tt>remove</tt> operation, or through the
	 * <tt>setValue</tt> operation on a map entry returned by the iterator.
	 * 
	 * @see Map#entrySet()
	 * @since 1.2
	 */
	public interface Entry {
		/**
		 * Returns the key corresponding to this entry.
		 * 
		 * @return the key corresponding to this entry.
		 */
		int getKey();

		/**
		 * Returns the value corresponding to this entry. If the mapping has
		 * been removed from the backing map (by the iterator's <tt>remove</tt>
		 * operation), the results of this call are undefined.
		 * 
		 * @return the value corresponding to this entry.
		 */
		Object getValue();

		/**
		 * Replaces the value corresponding to this entry with the specified
		 * value (optional operation). (Writes through to the map.) The behavior
		 * of this call is undefined if the mapping has already been removed
		 * from the map (by the iterator's <tt>remove</tt> operation).
		 * 
		 * @param value
		 *            new value to be stored in this entry.
		 * @return old value corresponding to the entry.
		 * 
		 * @throws UnsupportedOperationException
		 *             if the <tt>put</tt> operation is not supported by the
		 *             backing map.
		 * @throws ClassCastException
		 *             if the class of the specified value prevents it from
		 *             being stored in the backing map.
		 * @throws IllegalArgumentException
		 *             if some aspect of this value prevents it from being
		 *             stored in the backing map.
		 * @throws NullPointerException
		 *             the backing map does not permit <tt>null</tt> values, and
		 *             the specified value is <tt>null</tt>.
		 */
		Object setValue(Object value);

		/**
		 * Compares the specified object with this entry for equality. Returns
		 * <tt>true</tt> if the given object is also a map entry and the two
		 * entries represent the same mapping. More formally, two entries
		 * <tt>e1</tt> and <tt>e2</tt> represent the same mapping if
		 * 
		 * <pre>
		 * (e1.getKey() == null ? e2.getKey() == null : e1.getKey().equals(e2.getKey()))
		 * 		&amp;&amp; (e1.getValue() == null ? e2.getValue() == null : e1.getValue()
		 * 				.equals(e2.getValue()))
		 * </pre>
		 * 
		 * This ensures that the <tt>equals</tt> method works properly across
		 * different implementations of the <tt>Map.Entry</tt> interface.
		 * 
		 * @param o
		 *            object to be compared for equality with this map entry.
		 * @return <tt>true</tt> if the specified object is equal to this map
		 *         entry.
		 */
		boolean equals(Object o);

		/**
		 * Returns the hash code value for this map entry. The hash code of a
		 * map entry <tt>e</tt> is defined to be:
		 * 
		 * <pre>
		 * (e.getKey() == null ? 0 : e.getKey().hashCode())
		 * 		&circ; (e.getValue() == null ? 0 : e.getValue().hashCode())
		 * </pre>
		 * 
		 * This ensures that <tt>e1.equals(e2)</tt> implies that
		 * <tt>e1.hashCode()==e2.hashCode()</tt> for any two Entries <tt>e1</tt>
		 * and <tt>e2</tt>, as required by the general contract of
		 * <tt>Object.hashCode</tt>.
		 * 
		 * @return the hash code value for this map entry.
		 * @see Object#hashCode()
		 * @see Object#equals(Object)
		 * @see #equals(Object)
		 */
		int hashCode();
	}

	// Comparison and hashing

	/**
	 * Compares the specified object with this map for equality. Returns
	 * <tt>true</tt> if the given object is also a map and the two Maps
	 * represent the same mappings. More formally, two maps <tt>t1</tt> and
	 * <tt>t2</tt> represent the same mappings if
	 * <tt>t1.entrySet().equals(t2.entrySet())</tt>. This ensures that the
	 * <tt>equals</tt> method works properly across different implementations of
	 * the <tt>Map</tt> interface.
	 * 
	 * @param o
	 *            object to be compared for equality with this map.
	 * @return <tt>true</tt> if the specified object is equal to this map.
	 */
	boolean equals(Object o);

	/**
	 * Returns the hash code value for this map. The hash code of a map is
	 * defined to be the sum of the hashCodes of each entry in the map's
	 * entrySet view. This ensures that <tt>t1.equals(t2)</tt> implies that
	 * <tt>t1.hashCode()==t2.hashCode()</tt> for any two maps <tt>t1</tt> and
	 * <tt>t2</tt>, as required by the general contract of Object.hashCode.
	 * 
	 * @return the hash code value for this map.
	 * @see Map.Entry#hashCode()
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 * @see #equals(Object)
	 */
	int hashCode();
}
