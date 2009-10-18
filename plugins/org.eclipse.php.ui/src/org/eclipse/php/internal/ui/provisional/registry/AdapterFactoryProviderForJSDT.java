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

package org.eclipse.php.internal.ui.provisional.registry;

import org.eclipse.php.internal.core.documentModel.handler.PHPModelHandler;
import org.eclipse.wst.sse.core.internal.ltk.modelhandler.IDocumentTypeHandler;

/**
 * Support for JavaScript editing in php source
 * 
 * @see issue #242015
 */
public class AdapterFactoryProviderForJSDT
		extends
		org.eclipse.wst.jsdt.web.ui.internal.registry.AdapterFactoryProviderForJSDT {

	@Override
	public boolean isFor(IDocumentTypeHandler contentTypeDescription) {
		return contentTypeDescription instanceof PHPModelHandler;
	}

}
