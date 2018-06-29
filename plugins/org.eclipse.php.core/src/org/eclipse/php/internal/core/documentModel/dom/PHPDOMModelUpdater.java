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
package org.eclipse.php.internal.core.documentModel.dom;

import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.wst.xml.core.internal.document.DOMModelImpl;
import org.eclipse.wst.xml.core.internal.document.XMLModelUpdater;

public class PHPDOMModelUpdater extends XMLModelUpdater {
	public PHPDOMModelUpdater(DOMModelImpl model) {
		super(model);
	}

	@Override
	protected boolean isNestedTagClose(String regionType) {
		return regionType == PHPRegionContext.PHP_CLOSE;
	}
}
