/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.selectionactions;

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ISourceReference;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.corext.dom.SelectionAnalyzer;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;

public class StructureSelectEnclosingAction extends StructureSelectionAction {

	public StructureSelectEnclosingAction(PHPStructuredEditor editor,
			SelectionHistory history) {
		super(Messages.StructureSelectEnclosingAction_3, editor, history);
		setToolTipText(Messages.StructureSelectEnclosingAction_4);
		setDescription(Messages.StructureSelectEnclosingAction_5);
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
		// IJavaHelpContextIds.STRUCTURED_SELECT_ENCLOSING_ACTION);
	}

	/*
	 * This constructor is for testing purpose only.
	 */
	public StructureSelectEnclosingAction() {
	}

	@Override
	ISourceRange internalGetNewSelectionRange(ISourceRange oldSourceRange,
			ISourceReference sr, SelectionAnalyzer selAnalyzer)
			throws ModelException {
		ASTNode first = selAnalyzer.getFirstSelectedNode();
		if (first == null || first.getParent() == null)
			return getLastCoveringNodeRange(oldSourceRange, sr, selAnalyzer);

		return getSelectedNodeSourceRange(sr, first.getParent());
	}
}
