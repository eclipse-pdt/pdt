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
package org.eclipse.php.debug.core.debugger;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.debug.core.Logger;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.debugger.actions.IDebugAction;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;

public class DebugActionsRegistry {

	private static final String EXTENSION_POINT_NAME = "phpDebugActions"; //$NON-NLS-1$
	private static final String ACTION_TAG = "action"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String MESSAGE_ATTRIBUTE = "message"; //$NON-NLS-1$

	/** Actions stored by ID */
	private Dictionary actions = new Hashtable();
	
	/** Instance of this registry */
	private static DebugActionsRegistry instance = null;
	
	private DebugActionsRegistry() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(PHPDebugPlugin.getID(), EXTENSION_POINT_NAME);
		
		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (ACTION_TAG.equals(element.getName())) {
				actions.put (element.getAttribute(ID_ATTRIBUTE), new DebugActionProxy(element));
			}
		}
	}
	
	private static DebugActionsRegistry getInstance() {
		if (instance == null) {
			instance = new DebugActionsRegistry();
		}
		return instance;
	}
	
	private Dictionary getActions() {
		return actions;
	}

	/**
	 * Return action according to its ID
	 */
	public static IDebugAction getAction (String id) throws Exception {
		return (IDebugAction) getInstance().getActions().get(id);
	}
	
	/**
	 * Instantiation proxy of the handler object
	 */
	class DebugActionProxy implements IDebugAction {

		IDebugAction action;
		IConfigurationElement element;
		
		public DebugActionProxy (IConfigurationElement element) {
			this.element = element;
		}
		
		public IDebugAction createAction() {
			if (action == null) {
				Platform.run(new SafeRunnable("Error creation extension for extension-point org.eclipse.php.debug.core.phpDebugActions") {
					public void run() throws Exception {
						action = (IDebugAction) element.createExecutableExtension(CLASS_ATTRIBUTE);
					}
				});
			}
			return action;
		}

		public IDebugMessage getMessage() {
			IDebugMessage message = createAction().getMessage();
			if (message == null) {
				try {
					message = DebugMessagesRegistry.getMessage(element.getAttribute(MESSAGE_ATTRIBUTE));
				} catch (Exception e) {
					Logger.logException("Error retrieving request message for debug action", e);
				}
			}
			return message;
		}
	}
}