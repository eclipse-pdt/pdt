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
package org.eclipse.php.internal.ui.search;

import org.eclipse.dltk.internal.ui.search.DLTKElementLine;
import org.eclipse.search.ui.text.Match;

public class OccurrenceMatch extends Match {

	private final int fFlags;

	public OccurrenceMatch(DLTKElementLine element, int offset, int length,
			int flags) {
		super(element, offset, length);
		fFlags = flags;
	}

	public int getFlags() {
		return fFlags;
	}

}
