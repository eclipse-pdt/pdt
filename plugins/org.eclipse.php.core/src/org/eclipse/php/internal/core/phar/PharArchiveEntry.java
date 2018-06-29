/*******************************************************************************
 * Copyright (c) 2009 Zhao and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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

	@Override
	public String getName() {
		return pharEntry.getName();
	}

	@Override
	public boolean isDirectory() {
		return pharEntry.isDirectory();
	}

	@Override
	public long getSize() {
		return pharEntry.getSize();
	}

}
