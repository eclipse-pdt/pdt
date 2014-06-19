/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
		int res = Integer.compare(o1.getAttribute(IMarker.LINE_NUMBER, -1),
				o2.getAttribute(IMarker.LINE_NUMBER, -1));
		if (res == 0) {
			res = Integer.compare(o1.getAttribute(IMarker.CHAR_START, -1),
					o2.getAttribute(IMarker.CHAR_START, -1));
			if (res == 0) {
				res = Integer.compare(o1.getAttribute(IMarker.CHAR_END, -1),
						o2.getAttribute(IMarker.CHAR_END, -1));
			}
		}

		return res;
	}

}
