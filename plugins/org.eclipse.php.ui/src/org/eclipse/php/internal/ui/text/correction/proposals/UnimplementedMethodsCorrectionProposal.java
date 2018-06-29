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
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.ltk.core.refactoring.CategorizedTextEditGroup;
import org.eclipse.ltk.core.refactoring.GroupCategory;
import org.eclipse.ltk.core.refactoring.GroupCategorySet;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.core.ast.rewrite.ImportRewrite;
import org.eclipse.php.internal.core.ast.rewrite.ListRewrite;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.corext.codemanipulation.StubUtility;
import org.eclipse.php.internal.ui.text.correction.CorrectionMessages;
import org.eclipse.text.edits.TextEditGroup;

public class UnimplementedMethodsCorrectionProposal extends ASTRewriteCorrectionProposal {

	private ASTNode fTypeNode;

	public UnimplementedMethodsCorrectionProposal(ISourceModule cu, ASTNode typeNode, int relevance) {
		super("", cu, null, relevance, null); //$NON-NLS-1$
		setDisplayName(CorrectionMessages.UnimplementedMethodsCorrectionProposal_description);
		setImage(DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_CHANGE));

		fTypeNode = typeNode;
	}

	@Override
	protected ASTRewrite getRewrite() throws CoreException {
		AST ast = fTypeNode.getAST();

		ASTRewrite rewrite = ASTRewrite.create(ast);
		IMethod[] methods = null;
		boolean isInterface = false;
		ListRewrite listRewrite = null;
		if (fTypeNode instanceof ClassDeclaration) {
			ClassDeclaration decl = (ClassDeclaration) fTypeNode;
			ITypeBinding binding = decl.resolveTypeBinding();
			listRewrite = rewrite.getListRewrite(decl.getBody(), Block.STATEMENTS_PROPERTY);
			methods = PHPModelUtils.getUnimplementedMethods((IType) binding.getPHPElement(), null);
			isInterface = binding.isInterface();
		} else if (fTypeNode instanceof AnonymousClassDeclaration) {
			AnonymousClassDeclaration decl = (AnonymousClassDeclaration) fTypeNode;
			listRewrite = rewrite.getListRewrite(decl.getBody(), Block.STATEMENTS_PROPERTY);
			IModelElement element = this.getCompilationUnit().getElementAt(fTypeNode.getStart());
			methods = PHPModelUtils.getUnimplementedMethods((IType) element, null);
		} else {
			return null;
		}
		Program program = (Program) fTypeNode.getRoot();
		ImportRewrite imports = createImportRewrite(program);
		NamespaceDeclaration namespace = program.getNamespaceDeclaration(fTypeNode.getStart());
		for (int i = 0; i < methods.length; i++) {
			MethodDeclaration newMethodDecl = StubUtility.createImplementationStub(getCompilationUnit(), namespace,
					rewrite, imports, methods[i], isInterface);
			listRewrite.insertLast(newMethodDecl,
					createTextEditGroup(CorrectionMessages.AddUnimplementedMethodsOperation_AddMissingMethod_group));
		}
		return rewrite;
	}

	protected TextEditGroup createTextEditGroup(String label) {
		if (label.length() > 0) {
			return new CategorizedTextEditGroup(label, new GroupCategorySet(new GroupCategory(label, label, label)));
		} else {
			return new TextEditGroup(label);
		}
	}

}
