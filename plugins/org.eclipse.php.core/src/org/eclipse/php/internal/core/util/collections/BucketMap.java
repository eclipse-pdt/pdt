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

import java.lang.reflect.Method;
import java.util.*;

import org.eclipse.php.internal.core.PHPCorePlugin;

public class BucketMap<K, V> {
	private Map<K, Set<V>> map;

	private Set<V> defaultSet = new HashSet<V>(1);

	public BucketMap() {
		map = new HashMap<K, Set<V>>();
	}

	public BucketMap(Set<V> set) {
		this();
		defaultSet = set;
	}

	public BucketMap(int size) {
		map = new HashMap<K, Set<V>>(size);
	}

	public void add(K key, V value) {
		Set<V> values = map.get(key);
		if (values == null) {
			values = createSet();
			map.put(key, values);
		}
		values.add(value);
	}

	/**
	 * @return
	 */
	private Set<V> createSet() {
		if (defaultSet instanceof Cloneable) {
			try {
				Method method = defaultSet.getClass().getMethod(
						"clone", (Class<?>) null); //$NON-NLS-1$
				return (Set<V>) method.invoke(defaultSet, new Object[] {});
			} catch (Exception e) {
			}
		}
		try {
			return defaultSet.getClass().newInstance();
		} catch (Exception e) {
			PHPCorePlugin.log(e);
			return new HashSet<V>(1);
		}
	}

	public void addAll(K key, Collection<V> newValues) {
		Set<V> values = map.get(key);
		if (values == null) {
			values = createSet();
			map.put(key, values);
		}
		values.addAll(newValues);
	}

	public void merge(BucketMap<K, V> bucketMap) {
		for (K key : bucketMap.getKeys()) {
			addAll(key, bucketMap.getSet(key));
		}
	}

	public boolean contains(K key, V value) {
		Set<V> values = map.get(key);
		if (values == null)
			return false;
		return values.contains(value);
	}

	public boolean containsKey(/* K */Object key) {
		return map.containsKey(key);
	}

	public Set<V> get(K key) {
		Set<V> values = getSet(key);
		if (values == null)
			return createSet();
		return values;
	}

	public Set<V> getSet(Object key) {
		return map.get(key);
	}

	public Set<V> getAll() {
		Set<V> valuesSet = createSet();
		for (Set<V> values : map.values()) {
			for (V v : values)
				valuesSet.add(v);
		}
		return valuesSet;
	}

	public boolean remove(K key, V value) {
		Set<V> values = map.get(key);
		if (values == null)
			return false;
		boolean result = values.remove(value);
		if (values.size() == 0) {
			map.remove(key);
		}
		return result;
	}

	public Collection<V> removeAll(K key) {
		if (map.remove(key) != null)
			return map.remove(key);
		return createSet();
	}

	public void clear() {
		map.clear();
	}

	public Set<K> getKeys() {
		return map.keySet();
	}
}