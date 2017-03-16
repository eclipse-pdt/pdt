/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.actions;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;

public class RenamePHPElementActionDelegateProxy extends RenamePHPElementActionDelegate {

	@Override
	protected ASTNode getSelectedNode(Program program, int offset, int length) {
		// TODO Auto-generated method stub
		return super.getSelectedNode(program, offset, length);
	}

	@Override
	protected int getSourceOffset(IModelElement element) throws ModelException {
		// TODO Auto-generated method stub
		return super.getSourceOffset(element);
	}

	@Override
	protected boolean isModelElement(Object object) {
		// TODO Auto-generated method stub
		return super.isModelElement(object);
	}

	@Override
	protected boolean isScriptContainer(Object object) {
		// TODO Auto-generated method stub
		return super.isScriptContainer(object);
	}

	@Override
	protected boolean isSourceReference(Object object) {
		// TODO Auto-generated method stub
		return super.isSourceReference(object);
	}

}
