/**
 * Copyright (c) 2007 Zend Technologies
 */
package org.eclipse.php.internal.core.util.collections;

import java.lang.reflect.Method;
import java.util.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.internal.core.PHPCorePlugin;

import sun.reflect.Reflection;
import sun.reflect.ReflectionFactory;

public class BucketMap/* <K,V> */{
	private Map/* <K, Set<V>> */map;

	private Set setDefault = new HashSet(1);

	public BucketMap() {
		map = new HashMap();
	}

	public BucketMap(Set set) {
		this();
		setDefault = set;
	}

	public BucketMap(int size) {
		map = new HashMap(size);
	}

	public void add(/* K */Object key, /* V */Object value) {
		Set/* <V> */values = (Set) map.get(key);
		if (values == null) {
			values = createSet();
			map.put(key, values);
		}
		values.add(value);
	}

	/**
	 * @return
	 */
	private Set createSet() {
		if (setDefault instanceof Cloneable) {
			try {
				Method method = setDefault.getClass().getMethod("clone", null); //$NON-NLS-1$
				return (Set) method.invoke(setDefault, new Object[] {});
			} catch (Exception e) {}
		}
		try {
			return (Set) setDefault.getClass().newInstance();
		} catch (Exception e) {
			PHPCorePlugin.log(e);
			return new HashSet();
		}
	}

	public void addAll(/* K */Object key, Collection/* <V> */newValues) {
		Set/* <V> */values = (Set) map.get(key);
		if (values == null) {
			values = createSet();
			map.put(key, values);
		}
		values.addAll(newValues);
	}

	public void merge(BucketMap bucketMap) {
		for (Iterator i = bucketMap.getKeys().iterator(); i.hasNext();) {
			Object/* <K> */key = /* <K> */i.next();
			addAll(key, bucketMap.getSet(key));
		}
	}

	public boolean contains(/* K */Object key, /* V */Object value) {
		Set/* <V> */values = (Set) map.get(key);
		if (values == null) return false;
		return values.contains(value);
	}

	public boolean containsKey(/* K */Object key) {
		return map.containsKey(key);
	}

	public Set/* <V> */get(/* K */Object key) {
		Set/* <V> */values = getSet(key);
		if (values == null) return new HashSet(0);
		return values;
	}

	public Set/* <V> */getSet(Object key) {
		return (Set) map.get(key);
	}

	public Set/* <V> */getAll() {
		Set valuesSet = createSet();
		for (Iterator i = map.values().iterator(); i.hasNext();) {
			Set values = (Set) i.next();
			for (Iterator j = values.iterator(); j.hasNext();)
				valuesSet.add(j.next());
		}
		return valuesSet;
	}

	public boolean remove(/* K */Object key, /* V */Object value) {
		Set/* <K> */values = (Set) map.get(key);
		if (values == null) return false;
		boolean result = values.remove(value);
		if (values.size() == 0) {
			map.remove(key);
		}
		return result;
	}

	public/* V */Object[] removeAll(Object/* K */key) {
		Set/* <V> */removedStrings = (Set) map.remove(key);
		if (removedStrings != null) return removedStrings.toArray();
		return new Object[0];
	}

	public void clear() {
		map.clear();
	}

	public Set getKeys() {
		return map.keySet();
	}
}