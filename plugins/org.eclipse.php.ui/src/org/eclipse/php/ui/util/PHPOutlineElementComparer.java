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

package org.eclipse.php.ui.util;

import java.util.ArrayList;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.IElementComparer;

/**
 * 
 * @author guy.g
 *
 */
public class PHPOutlineElementComparer implements IElementComparer {

	private IPHPOutlineElementComparer[] comparers;

	public PHPOutlineElementComparer() {
		ArrayList comparers = new ArrayList();
		comparers.add(new PHPElementComparer());
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.php.ui.phpOutlineElementComparers");
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (element.getName().equals("comparer")) {
				ComparerProxy modelManagerProxy = new ComparerProxy(element);
				IPHPOutlineElementComparer comparer = modelManagerProxy.getComparer();
				comparers.add(comparer);
			}
		}
		this.comparers = new IPHPOutlineElementComparer[comparers.size()];
		comparers.toArray(this.comparers);
	}

	public boolean equals(Object a, Object b) {
		if (a == b)
			return true;
		if (a == null || b == null)
			return false;
		if (a.getClass() != b.getClass())
			return false;
		if (a.equals(b))
			return true;
		for (int i = 0; i < comparers.length; i++) {
			IPHPOutlineElementComparer comparer = comparers[i];
			if (comparer.supports(a)) {
				return comparer.equals(a, b);
			}
		}
		return false;
	}

	public int hashCode(Object element) {
		for (int i = 0; i < comparers.length; i++) {
			IPHPOutlineElementComparer comparer = comparers[i];
			if (comparer.supports(element)) {
				return comparer.hashCode(element);
			}
		}
		return element.hashCode();
	}

	private class ComparerProxy {
		IConfigurationElement element;
		IPHPOutlineElementComparer comparer;

		public ComparerProxy(IConfigurationElement element) {
			this.element = element;
		}

		public IPHPOutlineElementComparer getComparer() {
			if (comparer == null) {
				SafeRunner.run(new SafeRunnable("Error creation comparer for extension-point org.eclipse.php.ui.phpOutlineElementComparers") {
					public void run() throws Exception {
						comparer = (IPHPOutlineElementComparer) element.createExecutableExtension("class");
					}
				});
			}
			return comparer;
		}
	}
}
