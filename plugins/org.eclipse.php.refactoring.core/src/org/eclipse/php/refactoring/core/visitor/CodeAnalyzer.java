/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies - adapt for PHP refactoring
 *******************************************************************************/
package org.eclipse.php.refactoring.core.visitor;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.ast.nodes.SingleFieldDeclaration;
import org.eclipse.php.internal.core.corext.dom.Selection;
import org.eclipse.php.refactoring.core.SourceModuleSourceContext;

public class CodeAnalyzer extends StatementAnalyzer {

	private ISourceModule sourceModule;

	public CodeAnalyzer(Program cunit, ISourceModule sourceModule,
			IDocument document, Selection selection,
			boolean traverseSelectedNode) throws CoreException, IOException {
		super(cunit, sourceModule, document, selection, traverseSelectedNode);
		this.sourceModule = sourceModule;
	}

	protected final void checkSelectedNodes() {
		super.checkSelectedNodes();
		RefactoringStatus status = getStatus();
		if (status.hasFatalError())
			return;
		ASTNode node = getFirstSelectedNode();
		// TODO - check if this needs to be here at all
		if (node instanceof SingleFieldDeclaration) {
			status.addFatalError(
					"Operation not applicable to the current selection.", //$NON-NLS-1$
					new SourceModuleSourceContext(sourceModule,
							new org.eclipse.dltk.corext.SourceRange(node
									.getStart(), node.getLength())));
		}
	}
}
