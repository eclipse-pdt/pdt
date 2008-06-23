/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.internal.ui.PHPUIMessages;

/**
 * @author guy.g
 */
public class ElementCreationProxy {
	IConfigurationElement element;
	String extensionPointName;
	Object elementObject;
	
	public ElementCreationProxy(IConfigurationElement element, String extensionPointName) {
		this.element = element;
		this.extensionPointName = extensionPointName;
	}
	
	public Object getObject() {
		if (elementObject == null) {
			SafeRunner.run(new SafeRunnable(PHPUIMessages.getString("ElementCreationProxy.0") + element.getName() + PHPUIMessages.getString("ElementCreationProxy.1") + extensionPointName) { //$NON-NLS-1$ //$NON-NLS-2$
				public void run() throws Exception {
					elementObject = element.createExecutableExtension("class"); //$NON-NLS-1$
				}
			});
		}
		return elementObject;
	}
}