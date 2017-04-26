/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction.proposals;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.viewsupport.BasicElementLabels;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.ui.text.correction.CorrectionMessages;
import org.eclipse.php.internal.ui.text.correction.LinkedNodeFinder;
import org.eclipse.php.internal.ui.util.Messages;
import org.eclipse.php.ui.text.correction.IInvocationContext;

/**
 * Renames the primary type to be compatible with the name of the compilation
 * unit. All constructors and local references to the type are renamed as well.
 */
public class CorrectMainTypeNameProposal extends ASTRewriteCorrectionProposal {

	private final Identifier fOldName;
	private final Identifier fNewName;
	private final IInvocationContext fContext;

	/**
	 * Constructor for CorrectTypeNameProposal.
	 * 
	 * @param cu
	 *            the compilation unit
	 * @param context
	 *            the invocation contect
	 * @param oldTypeName
	 *            the old type name
	 * @param newTypeName
	 *            the new type name
	 * @param relevance
	 *            the relevance
	 */
	public CorrectMainTypeNameProposal(ISourceModule cu, IInvocationContext context, Identifier oldTypeName,
			Identifier newTypeName, int relevance) {
		super("", cu, null, relevance, null); //$NON-NLS-1$
		fContext = context;

		setDisplayName(Messages.format(CorrectionMessages.ReorgCorrectionsSubProcessor_renametype_description,
				BasicElementLabels.getJavaElementName(newTypeName.getName())));
		setImage(DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_CHANGE));
		fOldName = oldTypeName;
		fNewName = newTypeName;
	}

	@Override
	protected ASTRewrite getRewrite() throws CoreException {
		Program astRoot = fContext.getASTRoot();

		AST ast = astRoot.getAST();
		ASTRewrite rewrite = ASTRewrite.create(ast);

		TypeDeclaration decl = findTypeDeclaration(astRoot.statements(), fOldName);
		if (decl != null) {
			ASTNode[] sameNodes = LinkedNodeFinder.findByNode(astRoot, decl.getName());
			for (int i = 0; i < sameNodes.length; i++) {
				rewrite.replace(sameNodes[i], fNewName, null);
			}
			if (sameNodes.length == 0) {
				rewrite.replace(fOldName, fNewName, null);
			}
		}
		return rewrite;
	}

	private TypeDeclaration findTypeDeclaration(List<Statement> types, Identifier name) {
		if (types == null || types.size() == 0) {
			return null;
		} else if (types.get(0) instanceof NamespaceDeclaration) {
			types = ((NamespaceDeclaration) types.get(0)).getBody().statements();
		}
		for (Iterator<Statement> iter = types.iterator(); iter.hasNext();) {
			Statement decl = iter.next();
			if (decl instanceof TypeDeclaration) {
				if (name.equals(((TypeDeclaration) decl).getName())) {
					return (TypeDeclaration) decl;
				}
			}
		}
		return null;
	}
}