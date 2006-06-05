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
package org.eclipse.php.ui.phpCodeData;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.ui.generic.actionFilter.GenericActionFilter;
import org.eclipse.php.ui.generic.actionFilter.IActionFilterDelegator;

public class PhpCodeDataActionFilter extends GenericActionFilter {

	private static final String ADD_DESCRIPTION_ENABLEMENT_FILTER_KEY = "addDescriptionEnabled"; //$NON-NLS-1$
	private static final String ADD_DESCRIPTION_VISIBILITY_FILTER_KEY = "addDescriptionVisible"; //$NON-NLS-1$

	public PhpCodeDataActionFilter() {
		attributeName2Delegator.put(ADD_DESCRIPTION_ENABLEMENT_FILTER_KEY, new AddDescriptionEnablementFilterDelegator());
		attributeName2Delegator.put(ADD_DESCRIPTION_VISIBILITY_FILTER_KEY, new AddDescriptionVisibilityFilterDelegator());

		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.php.ui.actionFilterDelegatorForPHPCodeData");
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (element.getName().equals("ActionFilterDelegatorForPHPCodeData")) {
				DelegatorProxy delegatorProxy = new DelegatorProxy(element);
				IActionFilterDelegator delegator = delegatorProxy.getDelegator();
				attributeName2Delegator.put(element.getAttribute("key"), delegator);
			}
		}
	}

	private class DelegatorProxy {
		IConfigurationElement element;
		IActionFilterDelegator delegetor;

		public DelegatorProxy(IConfigurationElement element) {
			this.element = element;
		}

		public IActionFilterDelegator getDelegator() {
			if (delegetor == null) {
				Platform.run(new SafeRunnable("Error creation IActionFilterDelegator for extension-point org.eclipse.php.ui.actionFilterDelegatorForPHPCodeData") {
					public void run() throws Exception {
						delegetor = (IActionFilterDelegator) element.createExecutableExtension("class");
					}
				});
			}
			return delegetor;
		}
	}

}
