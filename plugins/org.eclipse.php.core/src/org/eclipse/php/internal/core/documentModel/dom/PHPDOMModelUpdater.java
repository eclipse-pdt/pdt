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
package org.eclipse.php.internal.core.documentModel.dom;

import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.wst.xml.core.internal.document.DOMModelImpl;
import org.eclipse.wst.xml.core.internal.document.XMLModelUpdater;

public class PHPDOMModelUpdater extends XMLModelUpdater {
	public PHPDOMModelUpdater(DOMModelImpl model) {
		super(model);
	}

	protected boolean isNestedTagClose(String regionType) {
		return regionType == PHPRegionContext.PHP_CLOSE;
	}
}
