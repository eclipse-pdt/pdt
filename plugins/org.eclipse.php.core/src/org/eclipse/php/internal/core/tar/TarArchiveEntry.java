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
package org.eclipse.php.internal.core.tar;

import org.eclipse.dltk.core.IArchiveEntry;

public class TarArchiveEntry implements IArchiveEntry {
	TarEntry tarEntry;

	public TarArchiveEntry(TarEntry tarEntry) {
		this.tarEntry = tarEntry;
	}

	public TarEntry getTarEntry() {
		return tarEntry;
	}

	public void setTarEntry(TarEntry tarEntry) {
		this.tarEntry = tarEntry;
	}

	@Override
	public String getName() {
		return tarEntry.getName();
	}

	@Override
	public boolean isDirectory() {
		return tarEntry.getFileType() == TarEntry.DIRECTORY;
	}

	@Override
	public long getSize() {
		// TODO Auto-generated method stub
		return tarEntry.getSize();
	}

}
