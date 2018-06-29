/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction.proposals;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.corext.util.QualifiedTypeNameHistory;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.ui.text.correction.SimilarElementsRequestor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;

public class AddImportCorrectionProposal extends ASTRewriteCorrectionProposal {

	private final String fTypeName;
	private final String fQualifierName;

	public AddImportCorrectionProposal(String name, ISourceModule cu, int relevance, Image image, String qualifierName,
			String typeName, Identifier node) {
		super(name, cu, ASTRewrite.create(node.getAST()), relevance, image);
		fTypeName = typeName;
		fQualifierName = qualifierName;
	}

	public String getQualifiedTypeName() {
		return fQualifierName + SimilarElementsRequestor.ENCLOSING_TYPE_SEPARATOR + fTypeName;
	}

	@Override
	protected void performChange(IEditorPart activeEditor, IDocument document) throws CoreException {
		super.performChange(activeEditor, document);
		rememberSelection();
	}

	private void rememberSelection() {
		QualifiedTypeNameHistory.remember(getQualifiedTypeName());
	}

}
