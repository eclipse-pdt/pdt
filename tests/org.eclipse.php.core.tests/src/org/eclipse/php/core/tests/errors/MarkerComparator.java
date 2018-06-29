/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.tests.errors;

import java.util.Comparator;

import org.eclipse.core.resources.IMarker;

public class MarkerComparator implements Comparator<IMarker> {

	@Override
	public int compare(IMarker o1, IMarker o2) {
		int res = compareInt(o1.getAttribute(IMarker.LINE_NUMBER, -1), o2.getAttribute(IMarker.LINE_NUMBER, -1));
		if (res == 0) {
			res = compareInt(o1.getAttribute(IMarker.CHAR_START, -1), o2.getAttribute(IMarker.CHAR_START, -1));
			if (res == 0) {
				res = compareInt(o1.getAttribute(IMarker.CHAR_END, -1), o2.getAttribute(IMarker.CHAR_END, -1));
				if (res == 0) {
					res = compareString(o1.getAttribute(IMarker.MESSAGE, ""), o2.getAttribute(IMarker.MESSAGE, ""));
				}
			}
		}

		return res;
	}

	private int compareInt(int l, int r) {
		return l < r ? -1 : (l == r ? 0 : 1);
	}

	private int compareString(String l, String r) {
		return l.compareTo(r);
	}

}
