/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.debug.daemon.communication;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.debug.daemon.DaemonPlugin;

/**
 * Registry class for the ICommunicationDaemon extentions.
 * 
 * @author Shalom Gibly
 */
public class CommunicationDaemonRegistry {

	private static final String EXTENSION_POINT_NAME = "debugCommunicationDaemon"; //$NON-NLS-1$
	private static final String DAEMON_TAG = "daemon"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$
	
	private static final String DEFAULT_DEBUG_DAEMONS_NAMESPACE = "org.eclipse.php.debug.core";

	/** Actions stored by ID */
	private Dictionary daemons = new Hashtable();

	/** Instance of this registry */
	private static CommunicationDaemonRegistry instance = null;

	private CommunicationDaemonRegistry() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(DaemonPlugin.getID(), EXTENSION_POINT_NAME);

		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (DAEMON_TAG.equals(element.getName())) {
				daemons.put(element.getAttribute(ID_ATTRIBUTE), new CommunicationDaemonFactory(element));
			}
		}
	}

	private static CommunicationDaemonRegistry getInstance() {
		if (instance == null) {
			instance = new CommunicationDaemonRegistry();
		}
		return instance;
	}

	private Dictionary getDaemons() {
		return daemons;
	}

	/**
	 * Returns a daemon according to its ID
	 */
	public static ICommunicationDaemon getDaemon(String id) throws Exception {
		return (ICommunicationDaemon) getInstance().getDaemons().get(id);
	}

	/**
	 * Return best matching ICommunicationDaemon.
	 * The returned ICommunicationDaemon is always a new instance.
	 * In case of an error, null is returned.
	 * 
	 * @return A new instance of a best match ICommunicationDaemon
	 */
	public static ICommunicationDaemon getBestMatchCommunicationDaemon() {
		try {
			ICommunicationDaemon defaultDaemon = null;
			Dictionary factories = getInstance().getDaemons();
			Enumeration e = factories.elements();
			while (e.hasMoreElements()) {
				CommunicationDaemonFactory initializerFactory = (CommunicationDaemonFactory) e.nextElement();
				ICommunicationDaemon initializerDaemon = initializerFactory.createDaemon();
				if (DEFAULT_DEBUG_DAEMONS_NAMESPACE.equals(initializerFactory.element.getNamespaceIdentifier())) {
					if (initializerDaemon.isEnabled()) {
						defaultDaemon = initializerDaemon;
					}
				} else {
					if (initializerDaemon.isEnabled()) {
						return initializerDaemon;
					}
				}
			}
			if (defaultDaemon != null) {
				return defaultDaemon;
			}
		} catch (Exception e) {
			DaemonPlugin.log(e);
		}
		return null;
	}

	/**
	 * Instantiation factory for the daemon object.
	 */
	class CommunicationDaemonFactory {

		ICommunicationDaemon daemon;
		IConfigurationElement element;

		public CommunicationDaemonFactory(IConfigurationElement element) {
			this.element = element;
		}

		public ICommunicationDaemon createDaemon() {
			if (daemon == null) {
				SafeRunner.run(new SafeRunnable("Error creation extension for extension-point org.eclipse.php.debug.daemon.communication") {
					public void run() throws Exception {
						daemon = (ICommunicationDaemon) element.createExecutableExtension(CLASS_ATTRIBUTE);
					}
				});
			}
			return daemon;
		}
	}
}