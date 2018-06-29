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
import org.eclipse.wst.xml.core.internal.document.AttrImpl;
import org.w3c.dom.Document;

/**
 * Represents attributes implementation in php dom model
 * 
 * @author Roy, 2007
 */
public class AttrImplForPHP extends AttrImpl {

	@Override
	protected boolean isNestedLanguageOpening(String regionType) {
		return regionType == PHPRegionContext.PHP_OPEN;
	}

	@Override
	protected void setOwnerDocument(Document ownerDocument) {
		super.setOwnerDocument(ownerDocument);
	}

	@Override
	protected void setName(String name) {
		super.setName(name);
	}

}
