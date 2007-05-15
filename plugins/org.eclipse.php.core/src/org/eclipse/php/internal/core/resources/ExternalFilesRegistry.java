package org.eclipse.php.internal.core.resources;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
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

	private static ExternalFilesRegistry instance = null;
	private final HashMap externalFilesRegistry = new HashMap();
	private final ListenerList listeners = new ListenerList();
	private IProject externalFilesProject;

	private ExternalFilesRegistry() {
		externalFilesProject = ResourcesPlugin.getWorkspace().getRoot().getProject("external_" + System.currentTimeMillis());
	}

	public static synchronized ExternalFilesRegistry getInstance() {
		if (instance == null) {
			instance = new ExternalFilesRegistry();
		}
		return instance;
	}

	/**
	 * Adds a new external file representation to the files registry
	 * @param iFilePath - The String representation of the IFile's path
	 * @param localPath = The String representation of the real File's path from file system
	 */
	public void addFileEntry(String localPath, IFile externalFile) {
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
	public void removeFileEntry(String localPath) {
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
	 * Determins whether this registry contains the given file representation in registry
	 * @param iFilePath
	 * @return true/false
	 */
	public boolean isEntryExist(String localPath) {
		return externalFilesRegistry.containsKey(localPath);
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
	 * @return An {@link IFile} array of {@link ExternalFileDecorator}s.
	 */
	public IFile[] getAllAsIFiles() {
		Collection coll = externalFilesRegistry.values();
		IFile[] result = new ExternalFileDecorator[coll.size()];
		coll.toArray(result);
		return result;
	}

	/**
	 * Creates and returns an {@link ExternalFileDecorator} for a file that has the given pathString.
	 * Use this only to create NEW instances of the {@link ExternalFileDecorator} and
	 * the created file will not be registered.
	 * @param pathString A full path string. 
	 * @return An {@link IFile} (new instance of {@link ExternalFileDecorator}).
	 */
	public static IFile getAsIFile(String pathString) {
		IPath path = Path.fromOSString(pathString);
		return new ExternalFileDecorator(ResourcesPlugin.getWorkspace().getRoot().getFile(path), path.getDevice());
	}

	/**
	 * The default project for all the external files that are opened
	 * outside the workspace
	 */
	public IProject getExternalFilesProject() {
		return externalFilesProject;
	}
}
