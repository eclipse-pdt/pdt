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
import org.eclipse.php.core.util.collections.IntHashtable;
import org.eclipse.php.debug.core.PHPDebugPlugin;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;

public class DebugMessagesRegistry {

	private static final String EXTENSION_POINT_NAME = "phpDebugMessages"; //$NON-NLS-1$
	private static final String MESSAGE_TAG = "message"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String HANDLER_ATTRIBUTE = "handler"; //$NON-NLS-1$

	/** This hash storing debug messagesHash by their type */ 
	private IntHashtable messagesHash = new IntHashtable(50);
	
	/** Messages types stored by message ID */
	private Dictionary messagesTypes = new Hashtable();
	
	/** Message handlers stored by message type */
	private IntHashtable handlers = new IntHashtable();
	
	/** Instance of this registry */
	private static DebugMessagesRegistry instance = null;
	
	private DebugMessagesRegistry() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(PHPDebugPlugin.getID(), EXTENSION_POINT_NAME);
		
		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];

			if (MESSAGE_TAG.equals(element.getName())) {
				final IDebugMessage messages[] = new IDebugMessage[1];
			
				Platform.run(new SafeRunnable("Error creation extension for extension-point org.eclipse.php.debug.core.phpDebugMessages") {
					public void run() throws Exception {
						messages[0] = (IDebugMessage) element.createExecutableExtension(CLASS_ATTRIBUTE);
					}
				});
				
				if (messages[0] != null && !this.messagesHash.containsKey(messages[0].getType())) {	
					this.messagesHash.put (messages[0].getType(), messages[0]);
					messagesTypes.put (element.getAttribute(ID_ATTRIBUTE), new Integer(messages[0].getType()));
					
					String handlerClass = element.getAttribute(HANDLER_ATTRIBUTE);
					if (handlerClass != null && !handlers.containsKey(messages[0].getType())) {
						handlers.put(messages[0].getType(), new DebugMessageHandlerFactory(element));
					}
				}
			}
		}
	}
	
	private IntHashtable getMessages() {
		return messagesHash;
	}

	private Dictionary getMessagesTypes() {
		return messagesTypes;
	}

	private IntHashtable getHandlers() {
		return handlers;
	}

	private static DebugMessagesRegistry getInstance() {
		if (instance == null) {
			instance = new DebugMessagesRegistry();
		}
		return instance;
	}

	/**
	 * Return message according to its type
	 * @throws Exception 
	 */
	public static IDebugMessage getMessage(int type) throws Exception {
		IntHashtable messages = getInstance().getMessages();
		if (messages.containsKey(type)) {
			return (IDebugMessage) messages.get(type).getClass().newInstance();
		} else {
			throw new Exception("Can't find message for type" + type +" in Debug messages registry!");
		}
	}
	
	/**
	 * Return message according to its ID
	 */
	public static IDebugMessage getMessage(String id) throws Exception {
		return (IDebugMessage) getInstance().getMessages().get(((Integer)getInstance().getMessagesTypes().get(id)).intValue()).getClass().newInstance();
	}
	
	/**
	 * Return handler according to the message
	 */
	public static IDebugMessageHandler getHandler (IDebugMessage message) {
		return ((DebugMessageHandlerFactory)getInstance().getHandlers().get(message.getType())).createHandler();
	}
		
	/**
	 * Instantiation proxy of the handler object
	 */
	class DebugMessageHandlerFactory {

		IDebugMessageHandler handler;
		IConfigurationElement element;
		
		public DebugMessageHandlerFactory (IConfigurationElement element) {
			this.element = element;
		}
		
		public IDebugMessageHandler createHandler() {
			if (handler == null) {
				Platform.run(new SafeRunnable("Error creation handler for extension-point org.eclipse.php.debug.core.phpDebugMessages") {
					public void run() throws Exception {
						handler = (IDebugMessageHandler) element.createExecutableExtension(HANDLER_ATTRIBUTE);
					}
				});
			}
			return handler;
		}
	}
}