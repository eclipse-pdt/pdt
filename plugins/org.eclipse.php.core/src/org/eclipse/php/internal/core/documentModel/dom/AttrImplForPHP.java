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
import org.eclipse.wst.xml.core.internal.document.AttrImpl;
import org.w3c.dom.Document;

/**
 * Represents attributes implementation in php dom model
 * 
 * @author Roy, 2007
 */
public class AttrImplForPHP extends AttrImpl {

	protected boolean isNestedLanguageOpening(String regionType) {
		return regionType == PHPRegionContext.PHP_OPEN;
	}

	protected void setOwnerDocument(Document ownerDocument) {
		super.setOwnerDocument(ownerDocument);
	}

	protected void setName(String name) {
		super.setName(name);
	}

}
