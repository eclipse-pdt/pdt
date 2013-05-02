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

import java.util.*;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.internal.core.util.collections.IntHashtable;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;

public class DebugMessagesRegistry {

	private static final String EXTENSION_POINT_NAME = "phpDebugMessages"; //$NON-NLS-1$
	private static final String MESSAGE_TAG = "message"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String HANDLER_ATTRIBUTE = "handler"; //$NON-NLS-1$
	private static final String OVERRIDES_ATTRIBUTE = "overridesId"; //$NON-NLS-1$

	/** This hash storing debug messagesHash by their type */
	private IntHashtable messagesHash = new IntHashtable(50);

	/** Messages types stored by message ID */
	private Dictionary<String, Integer> messagesTypes = new Hashtable<String, Integer>();

	/** Message handlers stored by message type */
	private IntHashtable handlers = new IntHashtable();

	/** Instance of this registry */
	private static DebugMessagesRegistry instance = null;

	private DebugMessagesRegistry() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(PHPDebugPlugin.getID(),
						EXTENSION_POINT_NAME);

		Map<String, IConfigurationElement> configElementsMap = new HashMap<String, IConfigurationElement>();
		for (final IConfigurationElement element : elements) {
			if (MESSAGE_TAG.equals(element.getName())) {
				String messageId = element.getAttribute(ID_ATTRIBUTE);
				if (!configElementsMap.containsKey(messageId)) {
					configElementsMap.put(messageId, element);
				}
				String overridesId = element.getAttribute(OVERRIDES_ATTRIBUTE);
				if (overridesId != null) {
					configElementsMap.put(overridesId, null);
				}
			}
		}

		Collection<IConfigurationElement> configElements = configElementsMap
				.values();
		while (configElements.remove(null))
			; // remove null elements

		for (final IConfigurationElement element : configElements) {
			final IDebugMessage messages[] = new IDebugMessage[1];

			SafeRunnable
					.run(new SafeRunnable(
							"Error creation extension for extension-point org.eclipse.php.internal.debug.core.phpDebugMessages") { //$NON-NLS-1$
						public void run() throws Exception {
							messages[0] = (IDebugMessage) element
									.createExecutableExtension(CLASS_ATTRIBUTE);
						}
					});

			if (messages[0] != null
					&& !this.messagesHash.containsKey(messages[0].getType())) {
				messagesHash.put(messages[0].getType(), messages[0]);
				messagesTypes.put(element.getAttribute(ID_ATTRIBUTE),
						new Integer(messages[0].getType()));

				String handlerClass = element.getAttribute(HANDLER_ATTRIBUTE);
				if (handlerClass != null
						&& !handlers.containsKey(messages[0].getType())) {
					handlers.put(messages[0].getType(),
							new DebugMessageHandlerFactory(element));
				}
			}
		}
	}

	private IntHashtable getMessages() {
		return messagesHash;
	}

	private Dictionary<String, Integer> getMessagesTypes() {
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
	 * 
	 * @throws Exception
	 */
	public static IDebugMessage getMessage(int type) throws Exception {
		IntHashtable messages = getInstance().getMessages();
		if (messages.containsKey(type)) {
			return (IDebugMessage) messages.get(type).getClass().newInstance();
		} else {
			throw new Exception("Can't find message for ID = " + type //$NON-NLS-1$
					+ " in Debug messages registry!");//$NON-NLS-1$
		}
	}

	/**
	 * Return message according to its ID
	 */
	public static IDebugMessage getMessage(String id) throws Exception {
		return (IDebugMessage) getInstance().getMessages().get(
				(getInstance().getMessagesTypes().get(id)).intValue())
				.getClass().newInstance();
	}

	/**
	 * Return handler according to the message
	 */
	public static IDebugMessageHandler getHandler(IDebugMessage message) {
		DebugMessageHandlerFactory debugMessageHandlerFactory = ((DebugMessageHandlerFactory) getInstance()
				.getHandlers().get(message.getType()));
		if (debugMessageHandlerFactory != null) {
			return debugMessageHandlerFactory.createHandler();
		}
		return null;
	}

	/**
	 * Instantiation proxy of the handler object
	 */
	class DebugMessageHandlerFactory {

		IConfigurationElement element;

		public DebugMessageHandlerFactory(IConfigurationElement element) {
			this.element = element;
		}

		public IDebugMessageHandler createHandler() {
			final IDebugMessageHandler[] handler = new IDebugMessageHandler[1];
			SafeRunnable
					.run(new SafeRunnable(
							"Error creation handler for extension-point org.eclipse.php.internal.debug.core.phpDebugMessages") { //$NON-NLS-1$
						public void run() throws Exception {
							handler[0] = (IDebugMessageHandler) element
									.createExecutableExtension(HANDLER_ATTRIBUTE);
						}
					});
			return handler[0];
		}
	}
}