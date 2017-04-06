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
package org.eclipse.php.internal.server.core.builtin.xml;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * This class represents an instance of a PHP Server, server as defined by a
 * specific Service, Engine, and Host defined in a server.xml configuration
 * file.
 */
public class ServerInstance {

	protected Server server;

	protected IStatus status = Status.OK_STATUS;

	/**
	 * Constructs a ServerInstance using the specified Server configuration. The
	 * ServerInstance provides access to a selected Service, Engine, and Host as
	 * determined by the supplied service and host name or their defaults.
	 * 
	 * @param server
	 *            Server configuration on which to base this instance.
	 * @param serviceName
	 *            Name of the service the instance should use. Defaults to
	 *            &quot;Catalina&quot; if <b>null</b> or any empty string is
	 *            specified.
	 * @param hostName
	 *            Name of the host the instance should use. Defaults to the
	 *            defaultHost setting on the Engine element found under the
	 *            service. If the defaultHost is not set, defaults to
	 *            &quot;localhost&quot;.
	 */
	public ServerInstance(Server server) {
		if (server == null)
			throw new IllegalArgumentException("Server argument may not be null."); //$NON-NLS-1$
		this.server = server;
	}

	/**
	 * This method is used to get the problem status following a method call
	 * that returned <b>null</b> due to an error.
	 * 
	 * @return Status of last method call.
	 */
	public IStatus getStatus() {
		return status;
	}

	/**
	 * Gets the array of Ports found in the Server configuration of this
	 * ServerInstance.
	 * 
	 * @return Array of Ports found in the Server configuration.
	 */
	public Port[] getPorts() {
		status = Status.OK_STATUS;
		int size = server.getPortCount();
		Port[] ports = new Port[size];
		for (int i = 0; i < size; i++) {
			ports[i] = server.getPort(i);
		}
		return ports;
	}

	/**
	 * Gets the port at the specified index. If a Port does not exist at that
	 * index a new Port is appended and returned.
	 * 
	 * @param index
	 *            Index of the Port to return.
	 * @return Returns the Port at the specified index or a new Port if one at
	 *         that index doesn't exist.
	 */
	public Port getPort(int index) {
		status = Status.OK_STATUS;
		return server.getPort(index);
	}

	/**
	 * Gets the array of Ports found in the Server configuration of this
	 * ServerInstance.
	 * 
	 * @return Array of Ports found in the Server configuration.
	 */
	public PathMapping[] getPathMapping() {
		status = Status.OK_STATUS;
		int size = server.getPathMappingCount();
		PathMapping[] mappings = new PathMapping[size];
		for (int i = 0; i < size; i++) {
			mappings[i] = server.getPathMapping(i);
		}
		return mappings;
	}

	/**
	 * Gets the port at the specified index. If a Port does not exist at that
	 * index a new Port is appended and returned.
	 * 
	 * @param index
	 *            Index of the Port to return.
	 * @return Returns the Port at the specified index or a new Port if one at
	 *         that index doesn't exist.
	 */
	public PathMapping getPathMapping(int index) {
		status = Status.OK_STATUS;
		return server.getPathMapping(index);
	}

	public PathMapping createPathMapping() {
		status = Status.OK_STATUS;
		return (PathMapping) server.createElement("PathMapping"); //$NON-NLS-1$
	}

	public boolean removePathMapping(int index) {
		status = Status.OK_STATUS;
		return server.removeElement("PathMapping", index); //$NON-NLS-1$
	}

}
