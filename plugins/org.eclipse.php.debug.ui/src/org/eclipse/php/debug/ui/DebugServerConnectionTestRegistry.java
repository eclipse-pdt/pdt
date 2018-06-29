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
package org.eclipse.php.debug.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.ui.Logger;

/**
 * Debuge server connection tests registry.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DebugServerConnectionTestRegistry {

	private static final String EXTENSION_ID = "org.eclipse.php.debug.ui.debugServerConnectionTest"; //$NON-NLS-1$
	private static final String PROP_TEST = "debugServerTest"; //$NON-NLS-1$
	private static final String PROP_DEBUGGER_TYPE = "debuggerTypeName"; //$NON-NLS-1$
	private static final String PROP_OVERRIDES_ID = "overridesId"; //$NON-NLS-1$
	private static final String PROP_ID = "id"; //$NON-NLS-1$

	private DebugServerConnectionTestRegistry() {
		// Registry reader - private constructor
	}

	/**
	 * Reads and returns server connection tests for given debugger type.
	 * 
	 * @param debuggerId
	 * @return server connection tests
	 */
	public static IDebugServerConnectionTest[] getTests(final String debuggerId) {
		Map<String, IDebugServerConnectionTest> filtersMap = new HashMap<>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (PROP_TEST.equals(element.getName())) {
				String debuggerTypeName = elements[i].getAttribute(PROP_DEBUGGER_TYPE);
				String overridesIds = elements[i].getAttribute(PROP_OVERRIDES_ID);
				if (debuggerTypeName.equals(PHPDebuggersRegistry.getDebuggerName(debuggerId))) {
					String id = element.getAttribute(PROP_ID);
					if (!filtersMap.containsKey(id)) {
						if (overridesIds != null) {
							StringTokenizer st = new StringTokenizer(overridesIds, ", "); //$NON-NLS-1$
							while (st.hasMoreTokens()) {
								filtersMap.put(st.nextToken(), null);
							}
						}
						try {
							filtersMap.put(id, (IDebugServerConnectionTest) element.createExecutableExtension("class")); //$NON-NLS-1$
						} catch (CoreException e) {
							Logger.logException(e);
						}
					}

				}
			}
		}
		Collection<IDebugServerConnectionTest> testers = filtersMap.values();
		while (testers.remove(null)) {
			; // remove null elements
		}
		IDebugServerConnectionTest[] debugTests = testers.toArray(new IDebugServerConnectionTest[testers.size()]);
		return debugTests;
	}

}
