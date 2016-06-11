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
package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.core.IType;

public class PHPThisClassType extends PHPClassType {

	private IType type;

	public PHPThisClassType(String namespace, String typeName, IType type) {
		super(namespace, typeName);
		this.type = type;
	}

	public PHPThisClassType(String typeName, IType type) {
		super(typeName);
		this.type = type;
	}

	public IType getType() {
		return type;
	}
}
