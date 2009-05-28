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
package org.eclipse.php.astview.views;

import org.eclipse.php.internal.core.ast.nodes.AST;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.swt.graphics.Image;

public class WellKnownTypesProperty extends ASTAttribute {
	
	public static final String[] WELL_KNOWN_TYPES = {
		"boolean",
		"byte",
		"char",
		"double",
		"float",
		"int",
		"long",
		"short",
		"void",
		"java.lang.Boolean",
		"java.lang.Byte",
		"java.lang.Character",
		"java.lang.Class",
		"java.lang.Cloneable",
		"java.lang.Double",
		"java.lang.Error",
		"java.lang.Exception",
		"java.lang.Float",
		"java.lang.Integer",
		"java.lang.Long",
		"java.lang.Object",
		"java.lang.RuntimeException",
		"java.lang.Short",
		"java.lang.String",
		"java.lang.StringBuffer",
		"java.lang.Throwable",
		"java.lang.Void",
		"java.io.Serializable",

		"_.$UnknownType$"
	};
	
	private final Program fRoot;

	public WellKnownTypesProperty(Program root) {
		fRoot= root;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.astview.views.ASTAttribute#getParent()
	 */
	public Object getParent() {
		return fRoot;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.astview.views.ASTAttribute#getChildren()
	 */
	public Object[] getChildren() {
		AST ast= fRoot.getAST();
		
		Binding[] res= new Binding[WELL_KNOWN_TYPES.length];
		for (int i= 0; i < WELL_KNOWN_TYPES.length; i++) {
			String type= WELL_KNOWN_TYPES[i];
			res[i]= new Binding(this, type, ast.resolveWellKnownType(type), true);
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.astview.views.ASTAttribute#getLabel()
	 */
	public String getLabel() {
		return "> RESOLVE_WELL_KNOWN_TYPES";  //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.astview.views.ASTAttribute#getImage()
	 */
	public Image getImage() {
		return null;
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !obj.getClass().equals(getClass())) {
			return false;
		}
		return true;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return 57;
	}
}
