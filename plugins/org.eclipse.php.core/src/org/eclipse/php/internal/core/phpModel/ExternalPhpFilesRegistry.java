package org.eclipse.php.internal.core.phpModel;

import java.util.HashMap;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.core.resources.PHPFileWrapper;

/**
 * This class wraps a simple registry of files that are opened in PHP Editor
 * and are NOT related to any project, i.e external PHP files.
 * Everytime an external PHP file is opened , it is added to this registry Singleton.
 * When the external PHP file is closed, it is removed from the registry.
 * @author yaronm
 */
public class ExternalPhpFilesRegistry {

	private static ExternalPhpFilesRegistry instance = null;
	private final HashMap externalFilesRegistry = new HashMap();
	private final ListenerList listeners = new ListenerList();

	private ExternalPhpFilesRegistry() {
	}

	public static synchronized ExternalPhpFilesRegistry getInstance() {
		if (instance == null) {
			instance = new ExternalPhpFilesRegistry();
		}
		return instance;
	}

	/**
	 * Adds a new external PHP file representation to the files registry
	 * @param iFilePath - The String representation of the IFile's path
	 * @param localPath = The String representation of the real File's path from file system
	 */
	public void addFileEntry(String iFilePath, String localPath) {
		if (externalFilesRegistry.put(iFilePath, localPath) != null) {
			notifyEntryChange(iFilePath, localPath, true);
		}
	}

	/**
	 * Removes the external PHP file representation from the files registry
	 * @param iFilePath - The String representation of the IFile's path
	 */
	public void removeFileEntry(String iFilePath) {
		Object localPath = externalFilesRegistry.remove(iFilePath);
		if (localPath != null) {
			notifyEntryChange(iFilePath, localPath.toString(), false);
		}
	}

	// Notify addition or removal events.
	private void notifyEntryChange(String iFilePath, String localPath, boolean isAddition) {
		Object[] listenersList = listeners.getListeners();
		for (int i = 0; i < listenersList.length; i++) {
			if (isAddition) {
				((ExternalPHPFilesListener)listenersList[i]).externalFileAdded(iFilePath, localPath);
			} else {
				((ExternalPHPFilesListener)listenersList[i]).externalFileRemoved(iFilePath, localPath);
			}
		}
	}

	/**
	 * Returns the String representation of the real File's path from file system
	 * This method will return null if the file does not exist in the registry
	 * @param iFilePath
	 */
	public String getEntryFromRegistry(String iFilePath) {
		String result = (String) externalFilesRegistry.get(iFilePath);
		return result;
	}

	/**
	 * Determins whether this registry contains the given file representation in registry
	 * @param iFilePath
	 * @return true/false
	 */
	public boolean isEntryExist(String iFilePath) {
		return externalFilesRegistry.containsKey(iFilePath);
	}

	/**
	 * Adds a listener that will be notified on changes made to this {@link ExternalPhpFilesRegistry}.
	 * 
	 * @param listener An {@link ExternalPHPFilesListener} to add.
	 */
	public void addListener(ExternalPHPFilesListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a listener for this {@link ExternalPhpFilesRegistry}.
	 * 
	 * @param listener An {@link ExternalPHPFilesListener} to remove.
	 */
	public void removeListener(ExternalPHPFilesListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Returns an array of IFiles that represents all the registered paths in this registry.
	 * A zero sized array will be return if the registry does not hold any record.
	 * @return 
	 * 
	 * @return An {@link IFile} array of {@link PHPFileWrapper}s.
	 */
	public IFile[] getAllAsIFiles() {
		String[] files = new String[externalFilesRegistry.size()];
		externalFilesRegistry.values().toArray(files);
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile[] iFiles = new IFile[files.length];
		for (int i = 0; i < iFiles.length; i++) {
			IPath path = Path.fromOSString(files[i]);
			IFile file = workspaceRoot.getFile(path);
			iFiles[i] = new PHPFileWrapper(file, path.getDevice());
		}
		return iFiles;
	}
}
