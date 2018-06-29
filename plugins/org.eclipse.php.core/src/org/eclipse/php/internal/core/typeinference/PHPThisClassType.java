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
