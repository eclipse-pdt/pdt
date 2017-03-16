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

import java.util.List;

import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.swt.graphics.Image;

/**
 *
 */
public class CommentsProperty extends ASTAttribute {
	
	private final Program fRoot;

	public CommentsProperty(Program root) {
		fRoot= root;
	}

	public Object getParent() {
		return fRoot;
	}

	public Object[] getChildren() {
		List commentList= fRoot.comments();
		return (commentList == null ? EMPTY : commentList.toArray());
	}

	public String getLabel() {
		List commentList= fRoot.comments();
		return "> comments (" +  (commentList == null ? 0 : commentList.size()) + ")";  //$NON-NLS-1$//$NON-NLS-2$
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
		return 17;
	}
}
