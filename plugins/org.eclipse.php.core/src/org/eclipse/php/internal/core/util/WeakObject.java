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
package org.eclipse.php.internal.core.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 
 * @author eran
 * @version
 */
public class WeakObject extends WeakReference {
	/*
	 * Hashcode of key, stored here since the key may be tossed by the GC
	 */
	private int hash;

	public WeakObject(Object k) {
		super(k);
		hash = k.hashCode();
	}

	public WeakObject(Object k, ReferenceQueue q) {
		super(k, q);
		hash = k.hashCode();
	}

	/*
	 * A WeakKey is equal to another WeakKey iff they both refer to objects that
	 * are, in turn, equal according to their own equals methods
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		Object t = this.get();
		Object u = o instanceof WeakObject ? ((WeakObject) o).get() : o;
		if ((t == null) || (u == null)) {
			return false;
		}
		if (t == u) {
			return true;
		}
		return t.equals(u);
	}

	public int hashCode() {
		return hash;
	}

}