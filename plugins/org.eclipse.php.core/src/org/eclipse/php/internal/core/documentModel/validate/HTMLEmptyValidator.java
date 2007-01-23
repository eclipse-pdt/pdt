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
package org.eclipse.php.internal.core.documentModel.validate;

import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.xml.core.internal.validate.ValidationComponent;

public class HTMLEmptyValidator extends ValidationComponent {

	/**
	 * HTMLSimpleValidator constructor comment.
	 */
	public HTMLEmptyValidator() {
		super();

	}

	/**
	 * Allowing the INodeAdapter to compare itself against the type
	 * allows it to return true in more than one case.
	 */
	public boolean isAdapterForType(Object type) {
		return ((type == HTMLEmptyValidator.class) || super.isAdapterForType(type));
	}

	public void validate(IndexedRegion node) {
		//		System.out.println ("in validate");

	}

}