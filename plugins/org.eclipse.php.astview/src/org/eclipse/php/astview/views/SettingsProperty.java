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

import org.eclipse.php.core.ast.nodes.AST;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.swt.graphics.Image;

public class SettingsProperty extends ASTAttribute {
	
	private final Program fRoot;

	public SettingsProperty(Program root) {
		fRoot= root;
	}

	public Object getParent() {
		return fRoot;
	}

	public Object[] getChildren() {
		AST ast= fRoot.getAST();
		Object[] res= {
				new GeneralAttribute(this, "apiLevel", String.valueOf(ast.apiLevel())),
				new GeneralAttribute(this, "hasResolvedBindings", String.valueOf(ast.hasResolvedBindings())),
		};
		return res;
	}

	public String getLabel() {
		return "> AST settings";  //$NON-NLS-1$
	}

	public Image getImage() {
		return null;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !obj.getClass().equals(getClass())) {
			return false;
		}
		return true;
	}
	
	public int hashCode() {
		return 19;
	}
}
