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
/*
 * WeakCollection.java
 *
 * Created on September 14, 2000, 11:08 AM
 */

package org.eclipse.php.internal.core.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * @author erez
 * @version
 */
public class CollectionDecorator implements Collection {

	private Collection innerCollection;

	public CollectionDecorator(Collection collection) {
		innerCollection = collection;
	}

	public int size() {
		return innerCollection.size();
	}

	public boolean isEmpty() {
		return innerCollection.isEmpty();
	}

	public boolean contains(Object o) {
		return innerCollection.contains(o);
	}

	public Iterator iterator() {
		return innerCollection.iterator();
	}

	public Object[] toArray() {
		return innerCollection.toArray();
	}

	public Object[] toArray(Object[] arg0) {
		return innerCollection.toArray(arg0);
	}

	public boolean add(Object arg0) {
		return innerCollection.add(arg0);
	}

	public boolean remove(Object o) {
		return innerCollection.remove(o);
	}

	public boolean containsAll(Collection arg0) {
		return innerCollection.containsAll(arg0);
	}

	public boolean addAll(Collection arg0) {
		return innerCollection.addAll(arg0);
	}

	public boolean removeAll(Collection arg0) {
		return innerCollection.removeAll(arg0);
	}

	public boolean retainAll(Collection arg0) {
		return innerCollection.retainAll(arg0);
	}

	public void clear() {
		innerCollection.clear();
	}

	protected Collection getInnerCollection() {
		return innerCollection;
	}

}