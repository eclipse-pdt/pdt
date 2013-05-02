/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.daemon.communication;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.debug.daemon.communication.ICommunicationDaemon;
import org.eclipse.php.internal.debug.daemon.DaemonPlugin;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

/**
 * Registry class for the ICommunicationDaemon extensions.
 * 
 * @author Shalom Gibly
 */
public class CommunicationDaemonRegistry {

	private static final String EXTENSION_POINT_NAME = "debugCommunicationDaemon"; //$NON-NLS-1$
	private static final String DAEMON_TAG = "daemon"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$

	private static final String DEFAULT_DEBUG_DAEMONS_NAMESPACE = "org.eclipse.php.debug.core"; //$NON-NLS-1$

	/** Actions stored by ID */
	private Dictionary<String, CommunicationDaemonFactory> daemons = new Hashtable<String, CommunicationDaemonFactory>();

	/** Instance of this registry */
	private static CommunicationDaemonRegistry instance = null;

	private CommunicationDaemonRegistry() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(DaemonPlugin.getID(),
						EXTENSION_POINT_NAME);

		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (DAEMON_TAG.equals(element.getName())) {
				daemons.put(element.getAttribute(ID_ATTRIBUTE),
						new CommunicationDaemonFactory(element));
			}
		}
	}

	private static CommunicationDaemonRegistry getInstance() {
		if (instance == null) {
			instance = new CommunicationDaemonRegistry();
		}
		return instance;
	}

	private Dictionary<String, CommunicationDaemonFactory> getDaemons() {
		return daemons;
	}

	/**
	 * Returns a daemon according to its ID
	 */
	public static ICommunicationDaemon getDaemon(String id) throws Exception {
		return (ICommunicationDaemon) getInstance().getDaemons().get(id);
	}

	/**
	 * Return best matching ICommunicationDaemons array. The returned
	 * ICommunicationDaemons are always new instances. In case of an error, null
	 * is returned. The basic PDT returned array of communication daemons
	 * contains all the daemons that were registered in the
	 * org.eclipse.php.debug.core plug-in name-space. In case that a different
	 * plug-in added another daemon, we add the new daemon to the list and in
	 * case that the daemon extends the PDT daemon class, the PDT daemon will be
	 * removed from the list. This way we can make sure that enhanced daemons
	 * will be loaded on top of the daemons they enhance.
	 * 
	 * Note that this method simply returns all the registered valid debug
	 * daemons. It does NOT take into consideration the debugger type that is
	 * currently used by the PDT, and this should be handled by the calling
	 * function by initializing the relevant daemons from the returned list.
	 * 
	 * @return New instances of a best match ICommunicationDaemons
	 */
	public static ICommunicationDaemon[] getBestMatchCommunicationDaemons() {
		try {
			Dictionary<String, CommunicationDaemonFactory> factories = getInstance()
					.getDaemons();
			Enumeration<CommunicationDaemonFactory> e = factories.elements();
			ArrayList<ICommunicationDaemon> pdtDaemons = new ArrayList<ICommunicationDaemon>(
					5);
			ArrayList<ICommunicationDaemon> additionalDaemons = new ArrayList<ICommunicationDaemon>(
					5);
			while (e.hasMoreElements()) {
				final CommunicationDaemonFactory initializerFactory = e
						.nextElement();
				final ICommunicationDaemon initializerDaemon = initializerFactory
						.createDaemon();
				boolean filter = WorkbenchActivityHelper
						.filterItem(new IPluginContribution() {
							public String getLocalId() {
								return initializerDaemon.getDebuggerID();
							}

							public String getPluginId() {
								return initializerFactory.element
										.getNamespaceIdentifier();
							}
						});
				if (filter) {
					continue;
				}
				if (DEFAULT_DEBUG_DAEMONS_NAMESPACE
						.equals(initializerFactory.element
								.getNamespaceIdentifier())) {
					if (initializerDaemon.isEnabled()) {
						pdtDaemons.add(initializerDaemon);
					}
				} else {
					if (initializerDaemon.isEnabled()) {
						additionalDaemons.add(initializerDaemon);
					}
				}
			}
			// Create the final daemons list.
			// Check if any of the additional daemons enhance one of the basic
			// PDT daemons. In
			// this case, we do not use the PDT daemon and remove it from the
			// returned list.
			ArrayList<ICommunicationDaemon> daemons = new ArrayList<ICommunicationDaemon>(
					pdtDaemons.size() + 4);
			daemons.addAll(pdtDaemons);
			for (ICommunicationDaemon addedDaemon : additionalDaemons) {
				daemons.add(addedDaemon);
				for (ICommunicationDaemon pdtDaemon : pdtDaemons) {
					if (pdtDaemon.getClass().isAssignableFrom(
							addedDaemon.getClass())) {
						// in this case, remove the pdt daemon because it's a
						// superclass of the
						// enhanced plugin.
						daemons.remove(pdtDaemon);
					}
				}
			}
			ICommunicationDaemon[] daemonsLoaded = new ICommunicationDaemon[daemons
					.size()];
			daemons.toArray(daemonsLoaded);
			return daemonsLoaded;
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
				SafeRunner
						.run(new SafeRunnable(
								"Error creation extension for extension-point org.eclipse.php.internal.debug.daemon.communication") { //$NON-NLS-1$
							public void run() throws Exception {
								daemon = (ICommunicationDaemon) element
										.createExecutableExtension(CLASS_ATTRIBUTE);
							}
						});
			}
			return daemon;
		}
	}
}