/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.entities;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractJsonArray<V> extends JsonEntity implements JsonCollection, Iterable<V> {
	
	protected List<V> values = new LinkedList<V>();
	
	private transient PropertyChangeListener propListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent e) {
			int index = values.indexOf(e.getSource());
			firePropertyChange("#" + index + "." + e.getPropertyName(), e.getOldValue(), e.getNewValue());
		}
	};
	
	@SuppressWarnings("unchecked")
	protected void doParse(Object obj) {
		clear();
		if (obj instanceof List) {
			for (Object item : (List<Object>)obj) {
				add((V)item);
			}
		}
	}

	@Override
	protected Object buildJson() {
		LinkedList<Object> out = new LinkedList<Object>();
		for (V val : values) {
			if (val == null) {
				continue;
			}
			out.add(getJsonValue(val));
		}
		return out;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.composer.api.entities.JsonCollection#size()
	 */
	public int size() {
		return values.size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.composer.api.entities.JsonCollection#clear()
	 */
	public void clear() {
		while (values.size() > 0) {
			remove(get(0));
		}
	}
	
	/**
	 * Checks whether the value is present.
	 * 
	 * @param value the value to check
	 * @return true if present, false if not
	 */
	public boolean has(V value) {
		return values.contains(value);
	}
	
	/**
	 * Returns the passed object
	 * 
	 * @param packageName
	 * @return the dependency
	 */
	public V get(int index) {
		return values.get(index);
	}
	
	public int indexOf(V value) {
		return values.indexOf(value);
	}
	
	/**
	 * Adds a value to the receiver's collection
	 * 
	 * @param value the new value
	 */
	public void add(V value) {
		values.add(value);
		
		if (value instanceof JsonEntity) {
			((JsonEntity)value).addPropertyChangeListener(propListener);
		}
		
		firePropertyChange("#" + (values.size() - 1), null, value);
	}
	
	/**
	 * Removes a value from the receiver's collection
	 * 
	 * @param value the value to remove
	 */
	public void remove(V value) {
		int index = values.indexOf(value);
		values.remove(value);
		
		if (value instanceof JsonEntity) {
			((JsonEntity)value).removePropertyChangeListener(propListener);
		}
		
		firePropertyChange("#" + index, value, null);
	}
	
	/**
	 * If oldValue exists, replaces with newValue
	 * 
	 * @param oldValue
	 * @param newValue
	 */
	public void replace(V oldValue, V newValue) {
		if (values.contains(oldValue)) {
			int index = values.indexOf(oldValue);
			values.remove(oldValue);
			values.add(index, newValue);
			
			if (oldValue instanceof JsonEntity) {
				((JsonEntity)oldValue).removePropertyChangeListener(propListener);
			}
			
			if (newValue instanceof JsonEntity) {
				((JsonEntity)newValue).removePropertyChangeListener(propListener);
			}
			
			firePropertyChange("#" + index, oldValue, newValue);
		}
	}

	public Object[] toArray() {
		return values.toArray();
	}
	
	public <T> T[] toArray(T[] a) {
		return values.toArray(a);
	}
	
	public List<V> toList() {
		return values;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AbstractJsonArray) {
			return values.equals(((AbstractJsonArray<V>)obj).toList());
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<V> iterator() {
		return values.iterator();
	}
}
