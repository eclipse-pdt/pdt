/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/

package org.eclipse.php.internal.ui.provisional.registry;

import org.eclipse.php.internal.core.documentModel.handler.PHPModelHandler;
import org.eclipse.wst.sse.core.internal.ltk.modelhandler.IDocumentTypeHandler;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.ui.internal.provisional.registry.AdapterFactoryProvider;

/**
 * Support for JavaScript editing in php source
 * 
 * @see issue #242015
 */
public class AdapterFactoryProviderForJSDT implements AdapterFactoryProvider {
	private AdapterFactoryProvider jsdtSupport;

	public AdapterFactoryProviderForJSDT() {
		try {
			jsdtSupport = new org.eclipse.wst.jsdt.web.ui.internal.registry.AdapterFactoryProviderForJSDT();
		} catch (NoClassDefFoundError e) {
			jsdtSupport = null;
		}
	}

	@Override
	public boolean isFor(IDocumentTypeHandler contentTypeDescription) {
		return jsdtSupport != null && contentTypeDescription instanceof PHPModelHandler;
	}

	@Override
	public void addAdapterFactories(IStructuredModel structuredModel) {
		if (jsdtSupport != null) {
			jsdtSupport.addAdapterFactories(structuredModel);
		}
	}

	@Override
	public void reinitializeFactories(IStructuredModel structuredModel) {
		if (jsdtSupport != null) {
			jsdtSupport.addAdapterFactories(structuredModel);
		}

	}

}
