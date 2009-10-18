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
package org.eclipse.php.internal.debug.core.pathmapper;

import java.util.Comparator;

/**
 * This comparator compares between two path entries in determines which of them
 * best matches the given path in terms of number of matching last segments.
 * 
 * @author michael
 */
public class BestMatchPathComparator implements Comparator<PathEntry> {

	private String[] segments;

	/**
	 * Constructs new best match comparator
	 * 
	 * @param path
	 *            Abstract path of the file model
	 */
	public BestMatchPathComparator(VirtualPath path) {
		this.segments = path.getSegments();
	}

	public int compare(PathEntry e1, PathEntry e2) {
		String[] s1 = e1.getAbstractPath().getSegments();
		String[] s2 = e2.getAbstractPath().getSegments();
		int ns1 = 0;
		int ns2 = 0;
		boolean found = true;
		for (int i = 0, j = 0, k = 0; found && i < segments.length
				&& (j < s1.length || k < s2.length); ++i, ++j, ++k) {
			found = false;
			if (j < s1.length && s1[j].equals(segments[i])) {
				ns1 = 0;
				found = true;
			}
			if (k < s2.length && s2[k].equals(segments[i])) {
				ns2 = 0;
				found = true;
			}
		}
		return Integer.signum(ns2 - ns1);
	}
}