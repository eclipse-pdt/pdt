package org.eclipse.php.internal.ui.phar.wizard;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.core.phar.PharEntry;
import org.eclipse.php.internal.core.phar.PharFile;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.eclipse.ui.internal.wizards.datatransfer.ILeveledImportStructureProvider;

public class PharLeveledStructureProvider implements
		ILeveledImportStructureProvider {
	private PharFile tarFile;

	private PharEntry root = new PharEntry();

	private Map children;

	private Map directoryEntryCache = new HashMap();

	private int stripLevel;

	/**
	 * Creates a <code>TarFileStructureProvider</code>, which will operate on
	 * the passed tar file.
	 * 
	 * @param sourceFile
	 *            the source TarFile
	 */
	public PharLeveledStructureProvider(PharFile sourceFile) {
		super();
		tarFile = sourceFile;
		root.setName("/"); //$NON-NLS-1$
	}

	/**
	 * Creates a new container tar entry with the specified name, iff it has not
	 * already been created. If the parent of the given element does not already
	 * exist it will be recursively created as well.
	 * 
	 * @param pathname
	 *            The path representing the container
	 * @return The element represented by this pathname (it may have already
	 *         existed)
	 */
	protected PharEntry createContainer(IPath pathname) {
		PharEntry existingEntry = (PharEntry) directoryEntryCache.get(pathname);
		if (existingEntry != null) {
			return existingEntry;
		}

		PharEntry parent;
		if (pathname.segmentCount() == 1) {
			parent = root;
		} else {
			parent = createContainer(pathname.removeLastSegments(1));
		}
		PharEntry newEntry = new PharEntry();
		newEntry.setName(pathname.toString());
		// newEntry.setFileType(TarEntry.DIRECTORY);
		directoryEntryCache.put(pathname, newEntry);
		List childList = new ArrayList();
		children.put(newEntry, childList);

		List parentChildList = (List) children.get(parent);
		parentChildList.add(newEntry);
		return newEntry;
	}

	/**
	 * Creates a new tar file entry with the specified name.
	 */
	protected void createFile(PharEntry entry) {
		IPath pathname = new Path(entry.getName());
		PharEntry parent;
		if (pathname.segmentCount() == 1) {
			parent = root;
		} else {
			parent = (PharEntry) directoryEntryCache.get(pathname
					.removeLastSegments(1));
		}

		List childList = (List) children.get(parent);
		childList.add(entry);
	}

	/*
	 * (non-Javadoc) Method declared on IImportStructureProvider
	 */
	public List getChildren(Object element) {
		if (children == null) {
			initialize();
		}

		return ((List) children.get(element));
	}

	/*
	 * (non-Javadoc) Method declared on IImportStructureProvider
	 */
	public InputStream getContents(Object element) {
		try {
			return tarFile.getInputStream((PharEntry) element);
		} catch (IOException e) {
			IDEWorkbenchPlugin.log(e.getLocalizedMessage(), e);
			return null;
		}
	}

	/**
	 * Returns the resource attributes for this file.
	 * 
	 * @param element
	 * @return the attributes of the file
	 */
	// public ResourceAttributes getResourceAttributes(Object element) {
	// ResourceAttributes attributes = new ResourceAttributes();
	// PharEntry entry = (PharEntry) element;
	// // attributes.setExecutable((entry.getMode() & 0100) != 0);
	// // attributes.setReadOnly((entry.getMode() & 0200) == 0);
	// return attributes;
	// }

	/*
	 * (non-Javadoc) Method declared on IImportStructureProvider
	 */
	public String getFullPath(Object element) {
		return stripPath(((PharEntry) element).getName());
	}

	/*
	 * (non-Javadoc) Method declared on IImportStructureProvider
	 */
	public String getLabel(Object element) {
		if (element.equals(root)) {
			return ((PharEntry) element).getName();
		}

		return stripPath(new Path(((PharEntry) element).getName())
				.lastSegment());
	}

	/**
	 * Returns the entry that this importer uses as the root sentinel.
	 * 
	 * @return TarEntry entry
	 */
	public Object getRoot() {
		return root;
	}

	/**
	 * Returns the tar file that this provider provides structure for.
	 * 
	 * @return TarFile file
	 */
	public PharFile getPharFile() {
		return tarFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.internal.wizards.datatransfer.ILeveledImportStructureProvider
	 * #closeArchive()
	 */
	public boolean closeArchive() {
		try {
			getPharFile().close();
		} catch (IOException e) {
			IDEWorkbenchPlugin.log(DataTransferMessages.ZipImport_couldNotClose
					+ getPharFile().getName(), e);
			return false;
		}
		return true;
	}

	/**
	 * Initializes this object's children table based on the contents of the
	 * specified source file.
	 */
	protected void initialize() {
		children = new HashMap(1000);

		children.put(root, new ArrayList());
		Iterator<PharEntry> entries = tarFile.getPharEntryMap().values()
				.iterator();
		while (entries.hasNext()) {
			PharEntry entry = (PharEntry) entries.next();
			IPath path = new Path(entry.getName()).addTrailingSeparator();

			if (entry.isDirectory()) {
				createContainer(path);
			} else {
				// Ensure the container structure for all levels above this is
				// initialized
				// Once we hit a higher-level container that's already added we
				// need go no further
				int pathSegmentCount = path.segmentCount();
				if (pathSegmentCount > 1) {
					createContainer(path.uptoSegment(pathSegmentCount - 1));
				}
				createFile(entry);
			}
		}
	}

	/*
	 * (non-Javadoc) Method declared on IImportStructureProvider
	 */
	public boolean isFolder(Object element) {
		return ((PharEntry) element).isDirectory();
	}

	/*
	 * Strip the leading directories from the path
	 */
	private String stripPath(String path) {
		String pathOrig = new String(path);
		for (int i = 0; i < stripLevel; i++) {
			int firstSep = path.indexOf('/');
			// If the first character was a seperator we must strip to the next
			// seperator as well
			if (firstSep == 0) {
				path = path.substring(1);
				firstSep = path.indexOf('/');
			}
			// No seperator wasw present so we're in a higher directory right
			// now
			if (firstSep == -1) {
				return pathOrig;
			}
			path = path.substring(firstSep);
		}
		return path;
	}

	public void setStrip(int level) {
		stripLevel = level;
	}

	public int getStrip() {
		return stripLevel;
	}
}
