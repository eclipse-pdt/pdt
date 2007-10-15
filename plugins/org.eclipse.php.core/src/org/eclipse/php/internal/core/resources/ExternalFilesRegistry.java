package org.eclipse.php.internal.core.resources;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Path;

/**
 * This class wraps a simple registry of files that are opened in the Editor
 * and are NOT related to any project, i.e external files.
 * Everytime an external PHP file is opened , it is added to this registry Singleton.
 * When the external file is closed, it is removed from the registry.
 * @author yaronm
 */
public class ExternalFilesRegistry {

	private static final ExternalFilesRegistry instance = new ExternalFilesRegistry();
	private final HashMap<String, IFile> externalFilesRegistry = new HashMap<String, IFile>();
	private final ListenerList listeners = new ListenerList();
	private IProject externalFilesProject;

	private ExternalFilesRegistry() {
		externalFilesProject = ResourcesPlugin.getWorkspace().getRoot().getProject("external_" + System.currentTimeMillis()); //$NON-NLS-1$
	}

	public static ExternalFilesRegistry getInstance() {
		return instance;
	}

	/**
	 * Adds a new external file representation to the files registry
	 * @param iFilePath - The String representation of the IFile's path
	 * @param localPath = The String representation of the real File's path from file system
	 */
	public synchronized void addFileEntry(String localPath, IFile externalFile) {
		if (!externalFilesRegistry.containsKey(localPath)) {
			externalFilesRegistry.put(localPath, externalFile);
			notifyEntryChange(localPath, true);
		}
	}

	/**
	 * Retrieves the IFile value from the registry for the given path
	 * @param localPath
	 * @return null if does not exist in the registry
	 */
	public IFile getFileEntry(String localPath) {
		return (IFile) externalFilesRegistry.get(localPath);
	}

	/**
	 * Removes the external file representation from the files registry
	 * @param localPath - The String representation of the local path
	 */
	public synchronized void removeFileEntry(String localPath) {
		if (externalFilesRegistry.remove(localPath) != null) {
			notifyEntryChange(localPath, false);
		}
	}

	// Notify addition or removal events.
	private void notifyEntryChange(String localPath, boolean isAddition) {
		Object[] listenersList = listeners.getListeners();
		for (int i = 0; i < listenersList.length; i++) {
			if (isAddition) {
				((ExternalFilesRegistryListener) listenersList[i]).externalFileAdded(localPath);
			} else {
				((ExternalFilesRegistryListener) listenersList[i]).externalFileRemoved(localPath);
			}
		}
	}

	/**
	 * Determines whether this registry contains the given file representation in registry
	 * Device must not be null
	 * @param localPath
	 * @return true/false
	 */
	public boolean isEntryExist(String localPath) {
		if (externalFilesRegistry.containsKey(localPath)) {
			return true;
		}
		Collection<IFile> coll = externalFilesRegistry.values();
		for (Iterator<IFile> iterator = coll.iterator(); iterator.hasNext();) {
			IFile iFile = iterator.next();
			if (iFile.getFullPath().equals(new Path(localPath))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines whether this registry contains the given file representation in registry
	 * @param localPath
	 * @return true/false
	 */
	public boolean isEntryExist(IFile file) {
		Collection<IFile> coll = externalFilesRegistry.values();
		for (Iterator<IFile> iterator = coll.iterator(); iterator.hasNext();) {
			IFile iFile = iterator.next();
			if (iFile.getFullPath().equals(file.getFullPath())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds a listener that will be notified on changes made to this {@link ExternalFilesRegistry}.
	 * 
	 * @param listener An {@link ExternalFilesRegistryListener} to add.
	 */
	public void addListener(ExternalFilesRegistryListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a listener for this {@link ExternalFilesRegistry}.
	 * 
	 * @param listener An {@link ExternalFilesRegistryListener} to remove.
	 */
	public void removeListener(ExternalFilesRegistryListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Returns an array of IFiles that represents all the registered paths in this registry.
	 * A zero sized array will be return if the registry does not hold any record.
	 * @return 
	 * 
	 * @return An {@link IFile} array of {@link ExternalFileWrapper}s.
	 */
	public IFile[] getAllAsIFiles() {
		Collection coll = externalFilesRegistry.values();
		IFile[] result = new ExternalFileWrapper[coll.size()];
		coll.toArray(result);
		return result;
	}

	/**
	 * The default project for all the external files that are opened
	 * outside the workspace
	 */
	public IProject getExternalFilesProject() {
		return externalFilesProject;
	}
}
