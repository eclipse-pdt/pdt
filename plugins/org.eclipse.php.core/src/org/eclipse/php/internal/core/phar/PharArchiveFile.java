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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.core.IArchive;
import org.eclipse.dltk.core.IArchiveEntry;

public class PharArchiveFile implements IArchive {

	private PharFile pharFile;

	public PharArchiveFile(String fileName) throws IOException, PharException {
		this(new File(fileName));
	}

	public PharArchiveFile(File file) throws IOException, PharException {
		pharFile = new PharFile(file);
	}

	public void close() throws IOException {
		pharFile.close();
	}

	public Enumeration<? extends IArchiveEntry> getArchiveEntries() {
		List<PharEntry> pharEntryList = pharFile.getPharEntryList();
		final Iterator<PharEntry> it = pharEntryList.iterator();

		return new Enumeration<IArchiveEntry>() {

			public boolean hasMoreElements() {
				return it.hasNext();
			}

			public IArchiveEntry nextElement() {
				return new PharArchiveEntry(it.next());
			}

		};
	}

	public IArchiveEntry getArchiveEntry(String name) {
		return new PharArchiveEntry(pharFile.getEntry(name));
	}

	public InputStream getInputStream(IArchiveEntry entry) throws IOException {
		if (entry instanceof PharArchiveEntry) {
			PharArchiveEntry pharArchiveEntry = (PharArchiveEntry) entry;
			return pharFile.getInputStream(pharArchiveEntry.getPharEntry());
		}
		return null;
	}

	public String getName() {
		return pharFile.getName();
	}

	public int fileSize() {
		return pharFile.getFileNumber();
	}

}
