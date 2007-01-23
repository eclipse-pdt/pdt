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
package org.eclipse.php.internal.core.documentModel.parser.regions;

import org.eclipse.wst.xml.core.internal.parser.regions.GenericTemplateRegion;

public class PHPContentRegion extends GenericTemplateRegion {

	/**
	 * The lexical token type of the region
	 */
	private final String tokenType;
	
	public PHPContentRegion(int start, int textLength, int length) {
		this(start, textLength, length, PHPRegionTypes.PHP_CONTENT);
	}

	public PHPContentRegion(int start, int textLength, int length, String type) {
		super(start, textLength, length);
		this.tokenType = type;
	}

	public final String getType() {
		return this.tokenType;
	}
}
