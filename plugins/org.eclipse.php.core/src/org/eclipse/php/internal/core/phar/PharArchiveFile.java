package org.eclipse.php.internal.core.phar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.core.IArchive;
import org.eclipse.dltk.core.IArchiveEntry;
import org.eclipse.dltk.core.IArchiveProjectFragment;

public class PharArchiveFile implements IArchive {
	PharFile pharFile;

	public PharArchiveFile(IArchiveProjectFragment archiveProjectFragment,
			String fileName) throws IOException, PharException {
		this(archiveProjectFragment, new File(fileName));
	}

	public PharArchiveFile(IArchiveProjectFragment archiveProjectFragment,
			File file) throws IOException, PharException {
		PharFile oldPharFile = null;
		if (archiveProjectFragment != null
				&& archiveProjectFragment.getArchive() instanceof PharArchiveFile) {
			oldPharFile = ((PharArchiveFile) archiveProjectFragment
					.getArchive()).pharFile;
		}
		pharFile = new PharFile(oldPharFile, file);

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
		// TODO Auto-generated method stub
		return pharFile.getFileNumber();
	}

}
