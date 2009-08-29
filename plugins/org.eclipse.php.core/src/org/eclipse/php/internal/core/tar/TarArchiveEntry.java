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

	public String getName() {
		return tarEntry.getName();
	}

	public boolean isDirectory() {
		return tarEntry.getFileType() == TarEntry.DIRECTORY;
	}

	public long getSize() {
		// TODO Auto-generated method stub
		return tarEntry.getSize();
	}

}
