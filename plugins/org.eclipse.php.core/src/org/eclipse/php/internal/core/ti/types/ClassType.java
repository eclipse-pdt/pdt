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

package org.eclipse.php.internal.core.ti.types;

import org.eclipse.php.internal.core.ast.nodes.BodyDeclaration;
import org.eclipse.php.internal.core.ast.nodes.TypeDeclaration;

/**
 * This class represents PHP element class type
 */
public class ClassType implements IClassType {
	
	private TypeDeclaration typeDecl;
	private BodyDeclaration bodyDecl;
	
	public ClassType(TypeDeclaration typeDecl, BodyDeclaration bodyDecl) {
		if (typeDecl == null || bodyDecl == null) {
			throw new NullPointerException();
		}
		this.typeDecl = typeDecl;
		this.bodyDecl = bodyDecl;
	}

	public boolean isSubtypeOf(IClassType classType) {
		return false;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof ClassType)) {
			return false;
		}
		ClassType other = (ClassType)obj;
		return other.typeDecl == typeDecl && other.bodyDecl == bodyDecl;
	}

	public int hashCode() {
		return typeDecl.hashCode() + 127 * bodyDecl.hashCode();
	}

	public String toString() {
		return "Class " + typeDecl.getName().getName();
	}
}
