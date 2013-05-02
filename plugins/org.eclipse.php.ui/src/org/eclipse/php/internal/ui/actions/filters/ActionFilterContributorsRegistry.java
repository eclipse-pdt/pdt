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
package org.eclipse.php.internal.ui.actions.filters;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.ui.actions.filters.IActionFilterContributor;

class ActionFilterContributorsRegistry {

	private static final String EXTENSION_POINT = "actionFilterContributors"; //$NON-NLS-1$
	private static final String CONTRIBUTOR_ELEMENT = "contributor"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$

	private static ActionFilterContributorsRegistry instance;
	private Map contributorElements = new HashMap();

	private ActionFilterContributorsRegistry() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(PHPUiPlugin.ID, EXTENSION_POINT);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (element.getName().equals(CONTRIBUTOR_ELEMENT)) {
				contributorElements.put(element.getAttribute(ID_ATTRIBUTE),
						element);
			}
		}
	}

	public synchronized static ActionFilterContributorsRegistry getInstance() {
		if (instance == null) {
			instance = new ActionFilterContributorsRegistry();
		}
		return instance;
	}

	/**
	 * Returns filter contributor by the specified ID
	 * 
	 * @param id
	 *            ID of the action filter contributor
	 * @return action filter contributor if it was found, otherwise
	 *         <code>null</code>
	 */
	public IActionFilterContributor getContributor(String id) {
		final IConfigurationElement element = (IConfigurationElement) contributorElements
				.get(id);
		if (element != null) {
			final IActionFilterContributor contributor[] = new IActionFilterContributor[1];
			SafeRunner.run(new SafeRunnable(
					PHPUIMessages.ActionFilterContributorsRegistry_0
							+ EXTENSION_POINT) { 
						public void run() throws Exception {
							contributor[0] = (IActionFilterContributor) element
									.createExecutableExtension(CLASS_ATTRIBUTE);
						}
					});
			return contributor[0];
		}
		return null;
	}
}