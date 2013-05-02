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
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.debug.core.debugger.IDebugHandler;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;

public class DebugHandlersRegistry {

	private static final String EXTENSION_POINT_NAME = "phpDebugHandlers"; //$NON-NLS-1$
	private static final String HANDLER_TAG = "handler"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String REMOTE_DEBUGGER_ATTRIBUTE = "debugger"; //$NON-NLS-1$

	/** Debug handlers stored by ID */
	private Dictionary actions = new Hashtable();

	/** Remote debuggers stored by debug handlers ID */
	private Dictionary debuggers = new Hashtable();

	/** Instance of this registry */
	private static DebugHandlersRegistry instance = null;

	private DebugHandlersRegistry() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(PHPDebugPlugin.getID(),
						EXTENSION_POINT_NAME);

		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (HANDLER_TAG.equals(element.getName())) {

				actions.put(element.getAttribute(ID_ATTRIBUTE),
						new DebugHandlerFactory(element));

				String debugger = element
						.getAttribute(REMOTE_DEBUGGER_ATTRIBUTE);
				if (debugger != null) {
					debuggers.put(element.getAttribute(ID_ATTRIBUTE), debugger);
				}
			}
		}
	}

	private static DebugHandlersRegistry getInstance() {
		if (instance == null) {
			instance = new DebugHandlersRegistry();
		}
		return instance;
	}

	private Dictionary getHandlers() {
		return actions;
	}

	/**
	 * Return debug handler according to its ID
	 * 
	 * @return handler Debug handler
	 */
	public static IDebugHandler getHandler(String id) throws Exception {
		return ((DebugHandlerFactory) getInstance().getHandlers().get(id))
				.createHandler();
	}

	/**
	 * Returns remote debugger ID by the Debug Handler ID
	 * 
	 * @param debug
	 *            handler ID.
	 * @return remote debugger ID.
	 */
	public static String getRemoteDebuggerID(String debugHandlerID) {
		return (String) getInstance().debuggers.get(debugHandlerID);
	}

	/**
	 * Factory of the debug handler object
	 */
	class DebugHandlerFactory {

		IDebugHandler handler;
		IConfigurationElement element;

		public DebugHandlerFactory(IConfigurationElement element) {
			this.element = element;
		}

		public IDebugHandler createHandler() {
			Platform
					.run(new SafeRunnable(
							"Error creation extension for extension-point org.eclipse.php.internal.debug.core.phpDebugHandlers") { //$NON-NLS-1$							
						public void run() throws Exception {
							handler = (IDebugHandler) element
									.createExecutableExtension(CLASS_ATTRIBUTE);
						}
					});
			return handler;
		}
	}
}