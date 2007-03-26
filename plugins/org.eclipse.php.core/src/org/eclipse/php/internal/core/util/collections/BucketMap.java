/**
 * Copyright (c) 2007 Zend Technologies
 */
package org.eclipse.php.internal.core.util.collections;

import java.util.*;

public class BucketMap/* <K,V> */{
	private Map/* <K, Set<V>> */map;

	public BucketMap() {
		map = new HashMap();
	}

	public BucketMap(int size) {
		map = new HashMap(size);
	}

	public void add(/* K */Object key, /* V */Object value) {
		Set/* <V> */values = (Set) map.get(key);
		if (values == null) {
			values = new HashSet(1);
			map.put(key, values);
		}
		values.add(value);
	}

	public void addAll(/* K */Object key, Collection/* <V> */newValues) {
		Set/* <V> */values = (Set) map.get(key);
		if (values == null) {
			values = new HashSet();
			map.put(key, values);
		}
		values.addAll(newValues);
	}

	public boolean contains(/* K */Object key, /* V */Object value) {
		Set/* <V> */values = (Set) map.get(key);
		if (values == null)
			return false;
		return values.contains(value);
	}

	public Set/* <V> */get(/* K */Object key) {
		Set/* <V> */values = getSet(key);
		if (values == null)
			return new HashSet(0);
		return values;
	}

	public Set/* <V> */getSet(Object key) {
		return (Set) map.get(key);
	}

	public Set/* <V> */getAll() {
		Set valuesSet = new HashSet();
		for (Iterator i = map.values().iterator(); i.hasNext();) {
			Set values = (Set) i.next();
			for (Iterator j = values.iterator(); j.hasNext();)
				valuesSet.add(j.next());
		}
		return valuesSet;
	}

	public boolean remove(/* K */Object key, /* V */Object value) {
		Set/* <K> */values = (Set) map.get(key);
		if (values == null)
			return false;
		boolean result = values.remove(value);
		if (values.size() == 0) {
			map.remove(key);
		}
		return result;
	}

	public/* V */Object[] removeAll(Object/* K */key) {
		Set/* <V> */removedStrings = (Set) map.remove(key);
		if (removedStrings != null)
			return removedStrings.toArray();
		return new Object[0];
	}

	public void clear() {
		map.clear();
	}

	public Set getKeys() {
		return map.keySet();
	}
}