/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.organizeIncludes;

import org.eclipse.php.internal.core.util.collections.BucketMap;

/**
 * @author seva
 */
public class DoubleBucketMap<K, V1, V2> {
	private BucketMap<K, V1> first;

	private BucketMap<K, V2> second;

	public DoubleBucketMap(BucketMap<K, V1> first, BucketMap<K, V2> second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * @return the first
	 */
	public BucketMap<K, V1> getFirst() {
		return first;
	}

	/**
	 * @return the second
	 */
	public BucketMap<K, V2> getSecond() {
		return second;
	}

}
