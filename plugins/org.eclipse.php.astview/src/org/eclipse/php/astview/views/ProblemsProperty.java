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

import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.swt.graphics.Image;

/**
 *
 */
public class ProblemsProperty extends ASTAttribute {
	
	private final ISourceModule fRoot;

	public ProblemsProperty(ISourceModule root) {
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
		// TODO : how to get the IProblems
		IProblem[] problems= null ; // fRoot.getProblems();
		return null;
/*		Object[] res= new Object[problems.length];
		for (int i= 0; i < res.length; i++) {
			res[i]= new ProblemNode(this, problems[i]);
		}
		return res;
*/	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.astview.views.ASTAttribute#getLabel()
	 */
	public String getLabel() {
		return "> compiler problems (" /*+  fRoot.getProblems().length*/ + ")";  //$NON-NLS-1$//$NON-NLS-2$
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
		return 18;
	}
}
