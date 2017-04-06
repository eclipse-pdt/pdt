/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.ServerPort;

/**
 * PHP server configuration.
 */
@SuppressWarnings("restriction")
public abstract class PHPServerConfiguration implements IPHPServerConfiguration, IPHPServerConfigurationWorkingCopy {
	public static final String NAME_PROPERTY = "name"; //$NON-NLS-1$
	public static final String PORT_PROPERTY = "port"; //$NON-NLS-1$
	public static final String MODIFY_PORT_PROPERTY = "modifyPort"; //$NON-NLS-1$
	public final static String SERVER_XML_FILENAME = "server.xml"; //$NON-NLS-1$
	public final static String PHP_INI_FILENAME = "php.ini"; //$NON-NLS-1$

	protected IFolder configPath;

	// property change listeners
	private transient List<PropertyChangeListener> propertyListeners;

	/**
	 * PHPServerConfiguration constructor.
	 * 
	 * @param path
	 *            a path
	 */
	public PHPServerConfiguration(IFolder path) {
		super();
		this.configPath = path;
	}

	protected IFolder getFolder() {
		return configPath;
	}

	/**
	 * Copies all files from the given directory in the workbench to the given
	 * location. Can be overridden by version specific class to modify or
	 * enhance what publish does.
	 * 
	 * @param phpServerDir
	 *            Destination PHP Server root directory.
	 * @param doBackup
	 *            Backup existing configuration files (true if not test mode).
	 * @param monitor
	 *            Progress monitor to use
	 * @return result of operation
	 */
	protected IStatus backupAndPublish(IPath phpServerDir, boolean doBackup, IProgressMonitor monitor) {
		MultiStatus ms = new MultiStatus(PHPServerPlugin.PLUGIN_ID, 0, Messages.publishConfigurationTask, null);
		if (Trace.isTraceEnabled())
			Trace.trace(Trace.FINER, "Backup and publish"); //$NON-NLS-1$
		monitor = ProgressUtil.getMonitorFor(monitor);

		try {
			IPath backup = null;
			if (doBackup) {
				// create backup directory
				backup = phpServerDir.append("backup"); //$NON-NLS-1$
				if (!backup.toFile().exists())
					backup.toFile().mkdir();
			}
			backupFolder(getFolder(), phpServerDir, backup, ms, monitor);
		} catch (Exception e) {
			Trace.trace(Trace.SEVERE, "backupAndPublish() error", e); //$NON-NLS-1$
			IStatus s = new Status(IStatus.ERROR, PHPServerPlugin.PLUGIN_ID, 0,
					NLS.bind(Messages.errorPublishConfiguration, new String[] { e.getLocalizedMessage() }), e);
			ms.add(s);
		}

		monitor.done();
		return ms;
	}

	protected void backupFolder(IFolder folder, IPath confDir, IPath backup, MultiStatus ms, IProgressMonitor monitor)
			throws CoreException {
		IResource[] children = folder.members();
		if (children == null)
			return;

		int size = children.length;
		monitor.beginTask(Messages.publishConfigurationTask, size * 100);
		for (int i = 0; i < size; i++) {
			if (children[i] instanceof IFile) {
				try {
					IFile file = (IFile) children[i];
					String name = file.getName();
					monitor.subTask(NLS.bind(Messages.publisherPublishTask, new String[] { name }));
					if (Trace.isTraceEnabled())
						Trace.trace(Trace.FINEST, "Publishing " + name); //$NON-NLS-1$

					// backup and copy file
					boolean copy = true;
					if (backup != null && !(backup.append(name).toFile().exists())) {
						IStatus status = FileUtil.copyFile(confDir.append(name).toOSString(),
								backup + File.separator + name);
						ms.add(status);
						if (!status.isOK())
							copy = false;
					}

					if (copy) {
						String destPath = confDir.append(name).toOSString();
						String destContents = null;
						String srcContents = null;
						File dest = new File(destPath);
						if (dest.exists()) {
							InputStream fis = new FileInputStream(destPath);
							destContents = PHPServerHelper.getFileContents(fis);
							if (destContents != null) {
								fis = file.getContents();
								srcContents = PHPServerHelper.getFileContents(fis);
							}
						}
						if (destContents == null || srcContents == null || !srcContents.equals(destContents)) {
							InputStream in = file.getContents();
							ms.add(FileUtil.copyFile(in, destPath));
						}
					}
				} catch (Exception e) {
					Trace.trace(Trace.SEVERE, "backupAndPublish() error", e); //$NON-NLS-1$
					ms.add(new Status(IStatus.ERROR, PHPServerPlugin.PLUGIN_ID, 0,
							NLS.bind(Messages.errorPublishConfiguration, new String[] { e.getLocalizedMessage() }), e));
				}
			}
			monitor.worked(100);
		}
	}

	protected void backupPath(IPath path, IPath confDir, IPath backup, MultiStatus ms, IProgressMonitor monitor) {
		File[] files = path.toFile().listFiles();
		if (files == null)
			return;

		int size = files.length;
		monitor.beginTask(Messages.publishConfigurationTask, size * 100);
		for (int i = 0; i < size; i++) {
			try {
				File file = files[i];
				String name = file.getName();
				monitor.subTask(NLS.bind(Messages.publisherPublishTask, new String[] { name }));
				if (Trace.isTraceEnabled())
					Trace.trace(Trace.FINEST, "Publishing " + name); //$NON-NLS-1$

				// backup and copy file
				boolean copy = true;
				if (backup != null && !(backup.append(name).toFile().exists())) {
					IStatus status = FileUtil.copyFile(confDir.append(name).toOSString(),
							backup + File.separator + name);
					ms.add(status);
					if (!status.isOK())
						copy = false;
				}

				if (copy)
					ms.add(FileUtil.copyFile(file.getAbsolutePath(), confDir.append(name).toOSString()));
			} catch (Exception e) {
				Trace.trace(Trace.SEVERE, "backupAndPublish() error", e); //$NON-NLS-1$
				ms.add(new Status(IStatus.ERROR, PHPServerPlugin.PLUGIN_ID, 0,
						NLS.bind(Messages.errorPublishConfiguration, new String[] { e.getLocalizedMessage() }), e));
			}
			monitor.worked(100);
		}
	}

	protected IStatus publishContextConfig(IPath baseDir, IPath deployDir, IProgressMonitor monitor) {
		// Default implementation assumes nothing to do
		return Status.OK_STATUS;
	}

	protected IStatus updateContextsToServeDirectly(IPath baseDir, String phpVersion, String loader,
			IProgressMonitor monitor) {
		// Default implementation assumes nothing to do
		return Status.OK_STATUS;
	}

	protected IStatus cleanupServer(IPath confDir, IPath installDir, boolean removeKeptContextFiles,
			IProgressMonitor monitor) {
		// Default implementation assumes nothing to clean
		return Status.OK_STATUS;
	}

	/**
	 * Make any local changes to the server configuration at the specified
	 * runtime base directory needed to complete publishing the server.
	 * 
	 * @param baseDir
	 *            runtime base directory for the server
	 * @param deployDir
	 *            deployment directory for the server
	 * @param server
	 *            server being localized
	 * @param monitor
	 *            a progress monitor
	 * @return result of operation
	 */
	public abstract IStatus localizeConfiguration(IPath baseDir, IPath deployDir, PHPServer server,
			IProgressMonitor monitor);

	/**
	 * Returns the main server port.
	 * 
	 * @return ServerPort
	 */
	public abstract ServerPort getMainPort();

	public abstract Mapping[] getPathMappings(String moduleName);

	public abstract Mapping[] getPathMappings();

	public abstract void setPathMapping(String moduleName, Mapping[] mappings);

	/**
	 * Returns the prefix that is used in front of the web module path property.
	 * (e.g. "webapps")
	 *
	 * @return java.lang.String
	 */
	public String getDocBasePrefix() {
		return ""; //$NON-NLS-1$
	}

	/**
	 * Returns the partial URL applicable to this module.
	 * 
	 * @param webModule
	 *            a web module
	 * @return the partial URL
	 */
	protected String getWebModuleURL(IModule webModule) {
		WebModule module = getWebModule(webModule);
		if (module != null)
			return module.getPath();

		return null;
	}

	/**
	 * Returns the given module from the config.
	 *
	 * @param module
	 *            a web module
	 * @return a web module
	 */
	public WebModule getWebModule(IModule module) {
		if (module == null)
			return null;

		String memento = module.getId();

		List<WebModule> modules = getWebModules();
		int size = modules.size();
		for (int i = 0; i < size; i++) {
			WebModule webModule = (WebModule) modules.get(i);
			if (memento.equals(webModule.getMemento())) {
				return webModule;
			}
		}
		return null;
	}

	protected abstract void save(IFolder folder, IProgressMonitor monitor) throws CoreException;

	protected void firePropertyChangeEvent(String propertyName, Object oldValue, Object newValue) {
		if (propertyListeners == null)
			return;

		PropertyChangeEvent event = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
		try {
			Iterator<PropertyChangeListener> iterator = propertyListeners.iterator();
			while (iterator.hasNext()) {
				try {
					PropertyChangeListener listener = (PropertyChangeListener) iterator.next();
					listener.propertyChange(event);
				} catch (Exception e) {
					Trace.trace(Trace.SEVERE, "Error firing property change event", e); //$NON-NLS-1$
				}
			}
		} catch (Exception e) {
			Trace.trace(Trace.SEVERE, "Error in property event", e); //$NON-NLS-1$
		}
	}

	/**
	 * Adds a property change listener to this server.
	 *
	 * @param listener
	 *            java.beans.PropertyChangeListener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (propertyListeners == null)
			propertyListeners = new ArrayList<PropertyChangeListener>();
		propertyListeners.add(listener);
	}

	/**
	 * Removes a property change listener from this server.
	 *
	 * @param listener
	 *            java.beans.PropertyChangeListener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (propertyListeners != null)
			propertyListeners.remove(listener);
	}

	public void importFromPath(IPath path, PHPexeItem phpExeItem, IProgressMonitor monitor) throws CoreException {
		load(path, phpExeItem, monitor);
	}

	protected abstract void load(IPath path, PHPexeItem phpExeItem, IProgressMonitor monitor) throws CoreException;

	protected abstract void load(IFolder folder, IProgressMonitor monitor) throws CoreException;

	public abstract void addWebModule(int index, IPHPWebModule module);

	public abstract void removeWebModule(int index);

	/**
	 * Gets the work directory for the server.
	 * 
	 * @param basePath
	 *            path to server runtime directory
	 * @return path for the server's work directory
	 */
	public abstract IPath getServerWorkDirectory(IPath basePath);

	/**
	 * Gets the work directory for the specified module on the server.
	 * 
	 * @param basePath
	 *            path to server runtime directory
	 * @param module
	 *            a PHP web module
	 * @return path for the module's work directory on the server
	 */
	public abstract IPath getContextWorkDirectory(IPath basePath, IModule module);

	/**
	 * Return a string representation of this object.
	 * 
	 * @return java.lang.String
	 */
	@Override
	public String toString() {
		return "PHPServerConfiguration[" + getFolder() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
