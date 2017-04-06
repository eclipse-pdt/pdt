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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.debug.core.pathmapper.PathEntry.Type;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping;
import org.eclipse.php.internal.debug.core.pathmapper.PathMapper.Mapping.MappingSource;
import org.eclipse.php.internal.debug.core.pathmapper.VirtualPath;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.server.core.builtin.xml.Factory;
import org.eclipse.php.internal.server.core.builtin.xml.PathMapping;
import org.eclipse.php.internal.server.core.builtin.xml.Port;
import org.eclipse.php.internal.server.core.builtin.xml.Server;
import org.eclipse.php.internal.server.core.builtin.xml.ServerInstance;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.ServerPort;

@SuppressWarnings("restriction")
public class DefaultPHPServerConfiguration extends PHPServerConfiguration {

	private final static String DEFAULT_SERVER_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Server>\n\t<Port name=\"HTTP/1.1\" protocol=\"HTTP\">80</Port>\n</Server>"; //$NON-NLS-1$

	protected String fPhpIniFile;
	protected Server server;
	protected ServerInstance serverInstance;
	protected Factory serverFactory;
	protected boolean isServerDirty;

	public DefaultPHPServerConfiguration(IFolder path) {
		super(path);
	}

	@Override
	public List<ServerPort> getServerPorts() {
		List<ServerPort> ports = new ArrayList<ServerPort>();
		try {
			int size = server.getPortCount();
			for (int i = 0; i < size; i++) {
				Port port = server.getPort(i);
				String name = port.getName();
				String protocol = port.getProtocol();
				int portValue = port.getPort();
				ports.add(new ServerPort(Integer.toString(i), name, portValue, protocol));
			}
		} catch (Exception e) {
			Trace.trace(Trace.SEVERE, "Error getting server ports", e); //$NON-NLS-1$
		}
		return ports;
	}

	@Override
	public void modifyServerPort(String id, int port) {
		int connNum = Integer.parseInt(id);
		Port p = serverInstance.getPort(connNum);
		if (p != null) {
			p.setPort(port);
			isServerDirty = true;
			firePropertyChangeEvent(MODIFY_PORT_PROPERTY, id, new Integer(port));
		}
	}

	@Override
	public IStatus localizeConfiguration(IPath baseDir, IPath deployDir, PHPServer server, IProgressMonitor monitor) {
		return null;
	}

	@Override
	public ServerPort getMainPort() {
		Iterator<ServerPort> iterator = getServerPorts().iterator();
		while (iterator.hasNext()) {
			ServerPort port = (ServerPort) iterator.next();
			// Return only an HTTP port from the selected Service
			if (port.getProtocol().toLowerCase().equals("http") && port.getId().indexOf('/') < 0) //$NON-NLS-1$
				return port;
		}
		return null;
	}

	@Override
	public void save(IFolder folder, IProgressMonitor monitor) throws CoreException {
		try {
			monitor = ProgressUtil.getMonitorFor(monitor);
			monitor.beginTask(Messages.savingTask, 1200);

			// save server.xml
			byte[] data = serverFactory.getContents();
			InputStream in = new ByteArrayInputStream(data);
			IFile file = folder.getFile(SERVER_XML_FILENAME); // $NON-NLS-1$
			if (file.exists()) {
				if (isServerDirty)
					file.setContents(in, true, true, ProgressUtil.getSubMonitorFor(monitor, 200));
				else
					monitor.worked(200);
			} else
				file.create(in, true, ProgressUtil.getSubMonitorFor(monitor, 200));
			isServerDirty = false;

			// save php.ini
			if (fPhpIniFile == null) {
				fPhpIniFile = ""; //$NON-NLS-1$
			}
			in = new ByteArrayInputStream(fPhpIniFile.getBytes());
			file = folder.getFile(PHP_INI_FILENAME); // $NON-NLS-1$
			if (file.exists())
				monitor.worked(200);
			else
				file.create(in, true, ProgressUtil.getSubMonitorFor(monitor, 200));

			if (monitor.isCanceled())
				return;
			monitor.done();
		} catch (Exception e) {
			Trace.trace(Trace.SEVERE, "Could not save PHP 7.0 Built-in Server configuration to " + folder.toString(), //$NON-NLS-1$
					e);
			throw new CoreException(new Status(IStatus.ERROR, PHPServerPlugin.PLUGIN_ID, 0,
					NLS.bind(Messages.errorCouldNotSaveConfiguration, new String[] { e.getLocalizedMessage() }), e));
		}
	}

	@Override
	public void load(IPath path, PHPexeItem phpExeItem, IProgressMonitor monitor) throws CoreException {
		try {
			monitor = ProgressUtil.getMonitorFor(monitor);
			monitor.beginTask(Messages.loadingTask, 7);

			serverFactory = new Factory();
			serverFactory.setPackageName("org.eclipse.php.internal.server.core.builtin.xml"); //$NON-NLS-1$
			server = (Server) serverFactory.loadDocument(DEFAULT_SERVER_XML);
			serverInstance = new ServerInstance(server);
			monitor.worked(1);

			// load php.ini file
			File file = phpExeItem.getINILocation();
			if (file != null && file.exists())
				fPhpIniFile = PHPServerHelper.getFileContents(new FileInputStream(file));
			else
				fPhpIniFile = null;
			monitor.worked(1);

			if (monitor.isCanceled())
				return;
			monitor.done();
		} catch (Exception e) {
			Trace.trace(Trace.WARNING, "Could not load PHP ini from " + path.toOSString() + ": " + e.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
			throw new CoreException(new Status(IStatus.ERROR, PHPServerPlugin.PLUGIN_ID, 0,
					NLS.bind(Messages.errorCouldNotLoadConfiguration, path.toOSString()), e));
		}
	}

	@Override
	public void load(IFolder folder, IProgressMonitor monitor) throws CoreException {
		try {
			monitor = ProgressUtil.getMonitorFor(monitor);
			monitor.beginTask(Messages.loadingTask, 1200);

			// load server.xml
			IFile file = folder.getFile(SERVER_XML_FILENAME);
			InputStream in = file.getContents();
			serverFactory = new Factory();
			serverFactory.setPackageName("org.eclipse.php.internal.server.core.builtin.xml"); //$NON-NLS-1$
			server = (Server) serverFactory.loadDocument(in);
			serverInstance = new ServerInstance(server);
			monitor.worked(200);

			// load php.ini
			file = folder.getFile(PHP_INI_FILENAME);
			if (file.exists()) {
				in = file.getContents();
				fPhpIniFile = PHPServerHelper.getFileContents(in);
			} else
				fPhpIniFile = null;
			monitor.worked(200);

			if (monitor.isCanceled())
				throw new Exception("Cancelled"); //$NON-NLS-1$
			monitor.done();
		} catch (Exception e) {
			Trace.trace(Trace.WARNING, "Could not load PHP ini from " + folder.getFullPath() + ": " + e.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
			throw new CoreException(new Status(IStatus.ERROR, PHPServerPlugin.PLUGIN_ID, 0,
					NLS.bind(Messages.errorCouldNotLoadConfiguration, folder.getFullPath().toOSString()), e));
		}
	}

	@Override
	public IPath getServerWorkDirectory(IPath basePath) {
		return null;
	}

	@Override
	public IPath getContextWorkDirectory(IPath basePath, IModule module) {
		return null;
	}

	@Override
	public List<WebModule> getWebModules() {
		return null;
	}

	@Override
	public void addWebModule(int index, IPHPWebModule module) {
	}

	@Override
	public void removeWebModule(int index) {

	}

	@Override
	public Mapping[] getPathMappings(String moduleName) {
		PathMapping[] pathMappings = serverInstance.getPathMapping();
		List<Mapping> list = new ArrayList<>();
		for (PathMapping pathMapping : pathMappings) {
			if (moduleName.equals(pathMapping.getModule())) {
				VirtualPath local = new VirtualPath(pathMapping.getLocalPath());
				VirtualPath remote = new VirtualPath(pathMapping.getRemotePath());
				Mapping mapping = new Mapping(local, remote, Type.WORKSPACE, MappingSource.ENVIRONMENT);
				list.add(mapping);
			}
		}
		return list.toArray(new Mapping[list.size()]);
	}

	@Override
	public void setPathMapping(String moduleName, Mapping[] mappings) {
		isServerDirty = true;
		PathMapping[] pathMappings = serverInstance.getPathMapping();
		for (int i = 0; i < pathMappings.length; i++) {
			PathMapping mapping = pathMappings[i];
			if (mapping.getModule().equals(moduleName)) {
				serverInstance.removePathMapping(i);
			}
		}
		for (int i = 0; i < mappings.length; i++) {
			PathMapping pathMapping = serverInstance.createPathMapping();
			pathMapping.setLocalPath(mappings[i].localPath.toString());
			pathMapping.setRemotePath(mappings[i].remotePath.toString());
			pathMapping.setModule(moduleName);
		}
	}

	@Override
	public Mapping[] getPathMappings() {
		PathMapping[] pathMappings = serverInstance.getPathMapping();
		List<Mapping> list = new ArrayList<>();
		for (PathMapping pathMapping : pathMappings) {
			VirtualPath local = new VirtualPath(pathMapping.getLocalPath());
			VirtualPath remote = new VirtualPath(pathMapping.getRemotePath());
			Mapping mapping = new Mapping(local, remote, Type.WORKSPACE, MappingSource.ENVIRONMENT);
			list.add(mapping);
		}
		return list.toArray(new Mapping[list.size()]);
	}

}
