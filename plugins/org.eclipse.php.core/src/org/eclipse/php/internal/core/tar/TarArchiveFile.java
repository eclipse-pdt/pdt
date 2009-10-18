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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.core.IArchive;
import org.eclipse.dltk.core.IArchiveEntry;

public class TarArchiveFile implements IArchive {
	private TarFile tarFile;
	private boolean mapInited = false;
	private Map<String, TarArchiveEntry> map = new HashMap<String, TarArchiveEntry>();

	public TarArchiveFile(String filename) throws TarException, IOException {
		tarFile = new TarFile(filename);
	}

	public TarArchiveFile(File file) throws TarException, IOException {
		tarFile = new TarFile(file);
	}

	public Enumeration<? extends IArchiveEntry> getArchiveEntries() {
		final Enumeration e = tarFile.entries();

		return new Enumeration<IArchiveEntry>() {

			public boolean hasMoreElements() {
				return e.hasMoreElements();
			}

			public IArchiveEntry nextElement() {
				TarEntry tarEntry = (TarEntry) e.nextElement();
				if (map.containsKey(tarEntry.getName())) {
					return map.get(tarEntry.getName());
				} else {
					TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(
							tarEntry);
					map.put(tarArchiveEntry.getName(), tarArchiveEntry);
					return tarArchiveEntry;
				}

			}

		};
	}

	public IArchiveEntry getArchiveEntry(String name) {
		// if (map.containsKey(name)) {
		// return map.get(name);
		// } else {
		initMap();
		return map.get(name);
		// }
	}

	private void initMap() {
		// TODO Auto-generated method stub

		if (mapInited)
			return;
		Enumeration<? extends IArchiveEntry> e = getArchiveEntries();
		while (e.hasMoreElements()) {
			TarArchiveEntry tarArchiveEntry = (TarArchiveEntry) e.nextElement();
			// e.nextElement();
			map.put(tarArchiveEntry.getName(), tarArchiveEntry);
		}
		mapInited = true;
	}

	public InputStream getInputStream(IArchiveEntry entry) throws IOException {
		if (entry instanceof TarArchiveEntry) {
			TarArchiveEntry tarArchiveEntry = (TarArchiveEntry) entry;
			try {
				return tarFile.getInputStream(tarArchiveEntry.getTarEntry());
			} catch (TarException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void close() throws IOException {
		tarFile.close();
	}

	public String getName() {
		return tarFile.getName();
	}

	public int fileSize() {
		// TODO Auto-generated method stub
		initMap();
		return map.size();
	}

}
