/*******************************************************************************
 * Copyright (c) 2009 Zhao and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zhao - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phar;

import org.eclipse.dltk.core.IArchiveEntry;

public class PharArchiveEntry implements IArchiveEntry {
	PharEntry pharEntry;

	public PharArchiveEntry(PharEntry pharEntry) {
		this.pharEntry = pharEntry;
	}

	public PharEntry getPharEntry() {
		return pharEntry;
	}

	public void setPharEntry(PharEntry pharEntry) {
		this.pharEntry = pharEntry;
	}

	public String getName() {
		return pharEntry.getName();
	}

	public boolean isDirectory() {
		return pharEntry.isDirectory();
	}

	public long getSize() {
		return pharEntry.getSize();
	}

}
