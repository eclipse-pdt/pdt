/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.server.ui.types;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.server.core.types.ServerType;
import org.eclipse.php.internal.server.ui.Activator;
import org.eclipse.php.internal.server.ui.types.ServerTypeDescriptor;
import org.eclipse.php.server.core.types.IServerType;

/**
 * Registry providing server type descriptors.
 */
public class ServerTypesDescriptorRegistry {

	private static Map<String, IServerTypeDescriptor> descriptors = null;
	protected static final String PROP_ID = "descriptor"; //$NON-NLS-1$
	public static final String EXTENSION_POINT_ID = Activator.getDefault().getBundle().getSymbolicName()
			+ ".serverTypeDescriptor"; //$NON-NLS-1$

	private static ServerTypesDescriptorRegistry instance;

	/**
	 * Returns server type descriptor that corresponds to provided server type.
	 * 
	 * @param serverType
	 * @return server type descriptor
	 */
	public static synchronized final IServerTypeDescriptor getDescriptor(IServerType serverType) {
		Map<String, IServerTypeDescriptor> descriptors = getDescriptors();
		if (serverType == null || serverType.getId() == null) {
			return descriptors.get(ServerType.GENERIC_PHP_SERVER_ID);
		}
		return descriptors.get(serverType.getId());
	}

	private static final Map<String, IServerTypeDescriptor> getDescriptors() {
		if (descriptors == null) {
			descriptors = getDefault().readFromExtensionPoint();
		}
		return descriptors;
	}

	private static ServerTypesDescriptorRegistry getDefault() {
		if (instance == null) {
			instance = new ServerTypesDescriptorRegistry();
		}
		return instance;
	}

	private Map<String, IServerTypeDescriptor> readFromExtensionPoint() {
		final Map<String, IServerTypeDescriptor> descriptors = new HashMap<>();
		IConfigurationElement[] configurationElements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID);
		for (final IConfigurationElement element : configurationElements) {
			IServerTypeDescriptor descriptor = new ServerTypeDescriptor(element);
			descriptors.put(descriptor.getServerTypeId(), descriptor);
		}
		return descriptors;
	}

}
