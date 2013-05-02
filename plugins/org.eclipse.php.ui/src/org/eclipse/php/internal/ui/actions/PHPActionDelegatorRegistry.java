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
package org.eclipse.php.internal.ui.actions;

import java.util.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.ui.actions.IRenamePHPElementActionFactory;
import org.eclipse.ui.IActionDelegate;

public class PHPActionDelegatorRegistry {

	private static final String EXTENSION_POINT = "org.eclipse.php.ui.phpActionDelegator"; //$NON-NLS-1$
	private static final String ACTION_ELEMENT = "action"; //$NON-NLS-1$
	private static final String ACTION_ID_ATTRIBUTE = "actionId"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String PRIORITY_ATTRIBUTE = "priority"; //$NON-NLS-1$

	private Map actionDelegators = new HashMap();
	private static PHPActionDelegatorRegistry instance = new PHPActionDelegatorRegistry();

	private PHPActionDelegatorRegistry() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT); 
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (ACTION_ELEMENT.equals(element.getName())) {
				String actionId = element.getAttribute(ACTION_ID_ATTRIBUTE);
				List elementsList = (List) actionDelegators.get(actionId);
				if (elementsList == null) {
					elementsList = new LinkedList();
				}
				elementsList.add(element);
				actionDelegators.put(actionId, elementsList);
			}
		}
	}

	/**
	 * Returns the action delegator with the highest priority contributed with
	 * specified ID through extension point
	 * <code>org.eclipse.php.ui.phpActionImplementor</code>.
	 * 
	 * @param id
	 *            action id
	 * @return The action delegator {@link IActionDelegate} with the highest
	 *         priority, or <code>null</code> if no pages where contributed.
	 */
	public static IPHPActionDelegator getActionDelegator(String id) {
		final List elementsList = (List) instance.actionDelegators.get(id);
		IPHPActionDelegator action = null;
		if (elementsList != null) {
			int topPriority = 0;
			Iterator i = elementsList.iterator();
			while (i.hasNext()) {
				IConfigurationElement element = (IConfigurationElement) i
						.next();
				int currentPriority = Integer.valueOf(
						element.getAttribute(PRIORITY_ATTRIBUTE)).intValue();
				// the final action should be the one with the highest priority
				if (currentPriority > topPriority) {
					try {
						action = (IPHPActionDelegator) element
								.createExecutableExtension(CLASS_ATTRIBUTE);
						topPriority = currentPriority;
					} catch (CoreException e) {
						Logger.logException(
								PHPUIMessages.PHPActionDelegatorRegistry_0
										+ element.getAttribute(CLASS_ATTRIBUTE),
								e); 
					}
				}
			}
		}
		return action;
	}

	/**
	 * Returns the action delegator factory with the highest priority
	 * contributed with specified ID through extension point
	 * <code>org.eclipse.php.ui.phpActionImplementor</code>.
	 * 
	 * @param id
	 *            action id
	 * @return The action delegator factory
	 *         {@link IRenamePHPElementActionFactory} with the highest priority,
	 *         or <code>null</code> if no pages where contributed.
	 */
	public static IRenamePHPElementActionFactory getActionDelegatorFactory(
			String id) {
		final List elementsList = (List) instance.actionDelegators.get(id);
		IRenamePHPElementActionFactory action = null;
		if (elementsList != null) {
			int topPriority = 0;
			Iterator i = elementsList.iterator();
			while (i.hasNext()) {
				IConfigurationElement element = (IConfigurationElement) i
						.next();
				int currentPriority = Integer.valueOf(
						element.getAttribute(PRIORITY_ATTRIBUTE)).intValue();
				// the final action should be the one with the highest priority
				if (currentPriority > topPriority) {
					try {
						action = (IRenamePHPElementActionFactory) element
								.createExecutableExtension(CLASS_ATTRIBUTE);
						topPriority = currentPriority;
					} catch (CoreException e) {
						Logger.logException(
								PHPUIMessages.PHPActionDelegatorRegistry_1
										+ element.getAttribute(CLASS_ATTRIBUTE),
								e); 
					}
				}
			}
		}
		return action;
	}

}
