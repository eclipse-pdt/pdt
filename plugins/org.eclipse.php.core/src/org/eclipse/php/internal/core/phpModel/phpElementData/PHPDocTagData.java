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
package org.eclipse.php.internal.core.phpModel.phpElementData;


import org.eclipse.php.internal.core.util.Visitor;

public class PHPDocTagData extends AbstractCodeData {

	private String description = null;
	
	public PHPDocTagData(String name, String description) {
		super(name);
		this.description =description; 
	}

	public void accept(Visitor v) {
		((PHPProjectModelVisitor) v).visit(this);
	}

	public String getDescription() {
		return description == null ? getName() : description;
	}
}
