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
package org.eclipse.php.internal.ui.autoEdit;

import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.format.IIndentationStrategyExtension1;

public class IndentationExtensionRegistry {

	private static IndentationExtensionRegistry instance;

	private SortedMap<Integer, IIndentationStrategyExtension1> extensions = new TreeMap<>();
	private static final String EXTENSION_ID = "org.eclipse.php.core.indentationStrategy";

	private IndentationExtensionRegistry() {

		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);

		// first registered extension wins...
		for (IConfigurationElement element : elements) {

			try {
				IIndentationStrategyExtension1 extension = (IIndentationStrategyExtension1) element
						.createExecutableExtension("class");
				Integer priority = Integer.parseInt(element.getAttribute("priority"));
				extensions.put(priority, extension);
			} catch (CoreException e) {
				PHPCorePlugin.log(e);
			}
		}
	}

	public static IndentationExtensionRegistry getInstance() {
		if (instance == null) {
			instance = new IndentationExtensionRegistry();
		}

		return instance;
	}

	public SortedMap<Integer, IIndentationStrategyExtension1> getExtensions() {
		return extensions;
	}

	public boolean hasExtensions() {
		return extensions.size() > 0;
	}
}
