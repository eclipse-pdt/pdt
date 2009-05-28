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

import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.swt.graphics.Image;


/**
 *
 */
public abstract class ASTAttribute {
	
	protected static final Object[] EMPTY= new Object[0];

	public abstract Object getParent();
	public abstract Object[] getChildren();
	public abstract String getLabel();
	public abstract Image getImage();
	
	public ASTNode getParentASTNode() {
		Object parent= getParent();
		while (parent instanceof ASTAttribute) {
			parent= ((ASTAttribute) parent).getParent();
		}
		if (parent instanceof ASTNode) {
			return (ASTNode) parent;
		}
		return null;
	}
	
}
