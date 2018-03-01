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
package org.eclipse.php.internal.ui.text.correction;

import java.util.Collection;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.core.ast.rewrite.ListRewrite;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.text.correction.proposals.ASTRewriteCorrectionProposal;
import org.eclipse.php.internal.ui.text.correction.proposals.UnimplementedMethodsCorrectionProposal;
import org.eclipse.php.ui.text.correction.IInvocationContext;
import org.eclipse.php.ui.text.correction.IProblemLocation;
import org.eclipse.swt.graphics.Image;

public class LocalCorrectionsSubProcessor {

	public static void addUnimplementedMethodsProposals(IInvocationContext context, IProblemLocation problem,
			Collection<IScriptCompletionProposal> proposals) {
		ISourceModule cu = context.getCompilationUnit();
		ASTNode selectedNode = problem.getCoveringNode(context.getASTRoot());
		if (selectedNode == null) {
			return;
		}
		ASTNode typeNode = null;
		boolean hasProposal = false;
		if (selectedNode.getType() == ASTNode.CLASS_INSTANCE_CREATION) {
			if (((ClassInstanceCreation) selectedNode).getAnonymousClassDeclaration() != null) {
				typeNode = ((ClassInstanceCreation) selectedNode).getAnonymousClassDeclaration();
				hasProposal = true;
			}
		} else if (selectedNode.getType() == ASTNode.IDENTIFIER) {
			typeNode = selectedNode;
			while ((typeNode = typeNode.getParent()) != null) {
				if (typeNode instanceof ClassDeclaration) {
					if (((ClassDeclaration) typeNode).resolveTypeBinding() != null) {
						hasProposal = true;
					}
					break;
				}
			}
		}
		if (hasProposal) {
			UnimplementedMethodsCorrectionProposal proposal = new UnimplementedMethodsCorrectionProposal(cu, typeNode,
					IProposalRelevance.ADD_UNIMPLEMENTED_METHODS);
			proposals.add(proposal);
		}
		if (typeNode instanceof ClassDeclaration) {
			ClassDeclaration typeDeclaration = (ClassDeclaration) typeNode;
			ModifierCorrectionSubProcessor.addMakeTypeAbstractProposal(context, typeDeclaration, proposals, cu);
		}
	}

	public static void getInterfaceExtendsClassProposals(IInvocationContext context, IProblemLocation problem,
			Collection<IScriptCompletionProposal> proposals) {
		ASTNode selectedNode = problem.getCoveringNode(context.getASTRoot());
		if (selectedNode == null) {
			return;
		}
		while (selectedNode.getParent() instanceof NamespaceName) {
			selectedNode = selectedNode.getParent();
		}

		boolean isType = false;
		if (selectedNode instanceof Identifier) {
			ITypeBinding typeBinding = ((Identifier) selectedNode).resolveTypeBinding();
			if (typeBinding != null && typeBinding.getPHPElement() instanceof IType) {
				try {
					int flags = ((IType) typeBinding.getPHPElement()).getFlags();
					if (PHPFlags.isClass(flags) || PHPFlags.isInterface(flags)) {
						isType = true;
					}
				} catch (ModelException e) {
					PHPUiPlugin.log(e);
				}
			}
		}
		if (!isType) {
			return;
		}

		StructuralPropertyDescriptor locationInParent = selectedNode.getLocationInParent();
		if (locationInParent != ClassDeclaration.SUPER_CLASS_PROPERTY
				&& locationInParent != AnonymousClassDeclaration.SUPER_CLASS_PROPERTY) {
			return;
		}

		ASTNode typeDecl = selectedNode.getParent();
		{
			ChildListPropertyDescriptor property = null;
			if (locationInParent == ClassDeclaration.SUPER_CLASS_PROPERTY) {
				property = ClassDeclaration.INTERFACES_PROPERTY;
			} else {
				property = AnonymousClassDeclaration.INTERFACES_PROPERTY;
			}

			ASTRewrite rewrite = ASTRewrite.create(context.getASTRoot().getAST());
			ASTNode placeHolder = rewrite.createMoveTarget(selectedNode);
			ListRewrite interfaces = rewrite.getListRewrite(typeDecl, property);
			interfaces.insertFirst(placeHolder, null);

			String label = CorrectionMessages.LocalCorrectionsSubProcessor_extendstoimplements_description;
			Image image = DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_CHANGE);
			ASTRewriteCorrectionProposal proposal = new ASTRewriteCorrectionProposal(label,
					context.getCompilationUnit(), rewrite, IProposalRelevance.CHANGE_EXTENDS_TO_IMPLEMENTS, image);
			proposals.add(proposal);
		}
		{
			// ASTRewrite rewrite =
			// ASTRewrite.create(context.getASTRoot().getAST());
			//
			// rewrite.set(typeDecl, ClassDeclaration.INTERFACES_PROPERTY,
			// Boolean.TRUE, null);
			//
			// String typeName = typeDecl.getName().getName();
			// String label =
			// Messages.format(CorrectionMessages.LocalCorrectionsSubProcessor_classtointerface_description,
			// BasicElementLabels.getJavaElementName(typeName));
			// Image image =
			// DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_CHANGE);
			// ASTRewriteCorrectionProposal proposal = new
			// ASTRewriteCorrectionProposal(label,
			// context.getCompilationUnit(), rewrite,
			// IProposalRelevance.CHANGE_CLASS_TO_INTERFACE, image);
			// proposals.add(proposal);
		}
	}
}
