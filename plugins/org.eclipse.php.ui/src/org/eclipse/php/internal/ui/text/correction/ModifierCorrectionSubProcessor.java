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

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.core.Flags;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.core.compiler.ast.parser.PhpProblemIdentifier;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.text.correction.proposals.ASTRewriteCorrectionProposal;
import org.eclipse.php.internal.ui.text.correction.proposals.LinkedCorrectionProposal;
import org.eclipse.php.internal.ui.util.Messages;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.php.ui.text.correction.IInvocationContext;
import org.eclipse.php.ui.text.correction.IProblemLocation;
import org.eclipse.swt.graphics.Image;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ModifierCorrectionSubProcessor {

	public static final int TO_STATIC = 1;
	public static final int TO_VISIBLE = 2;
	public static final int TO_NON_PRIVATE = 3;
	public static final int TO_NON_STATIC = 4;
	public static final int TO_NON_FINAL = 5;

	public static void addNonAccessibleReferenceProposal(IInvocationContext context, IProblemLocation problem,
			Collection proposals, int kind, int relevance) throws CoreException {
		ASTNode selectedNode = problem.getCoveringNode(context.getASTRoot());
		if (selectedNode == null) {
			return;
		}

		IBinding binding = null;
		switch (selectedNode.getType()) {
		case ASTNode.IDENTIFIER:
			binding = ((Identifier) selectedNode).resolveBinding();
			break;
		case ASTNode.NAMESPACE_NAME:
			List<Identifier> segments = ((NamespaceName) selectedNode).segments();
			binding = segments.get(segments.size() - 1).resolveBinding();
			break;
		default:
			return;
		}
		ITypeBinding typeBinding = null;
		String name;

		if (binding instanceof ITypeBinding) {
			typeBinding = (ITypeBinding) binding;
			name = PHPModelUtils.extractElementName(binding.getName());
		} else {
			return;
		}
		if (typeBinding != null) {
			int excludedModifiers = 0;
			String label;
			switch (kind) {
			case TO_NON_FINAL:
				label = Messages.format(
						CorrectionMessages.ModifierCorrectionSubProcessor_changemodifiertononfinal_description, name);
				excludedModifiers = ClassDeclaration.MODIFIER_FINAL;
				break;
			default:
				throw new IllegalArgumentException("not supported"); //$NON-NLS-1$
			}
			IType type = (IType) typeBinding.getPHPElement();
			ISourceModule targetCU = type.getSourceModule();
			if (targetCU != null) {
				try {
					Image image = DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_CHANGE);
					Program root = SharedASTProvider.getAST(targetCU, SharedASTProvider.WAIT_YES, null);
					if (root != null) {
						AST ast = root.getAST();
						ASTRewrite rewrite = ASTRewrite.create(ast);
						ClassDeclaration decl = (ClassDeclaration) root.findDeclaringNode(typeBinding);
						rewrite.set(decl, ClassDeclaration.MODIFIER_PROPERTY, decl.getModifier() & ~excludedModifiers,
								null);
						ASTRewriteCorrectionProposal proposal = new ASTRewriteCorrectionProposal(label, targetCU,
								rewrite, 6, image);
						proposals.add(proposal);
					}
				} catch (IOException e) {
					PHPUiPlugin.log(e);
				}
			}
		}
	}

	public static void addMethodRequiresBodyProposals(IInvocationContext context, IProblemLocation problem,
			Collection proposals) {
		ISourceModule cu = context.getCompilationUnit();
		AST ast = context.getASTRoot().getAST();

		ASTNode selectedNode = problem.getCoveringNode(context.getASTRoot());
		if (!(selectedNode.getParent() instanceof FunctionDeclaration)) {
			return;
		}
		selectedNode = selectedNode.getParent();
		FunctionDeclaration decl = (FunctionDeclaration) selectedNode;
		MethodDeclaration mdecl = (MethodDeclaration) decl.getParent();
		{
			ASTRewrite rewrite = ASTRewrite.create(ast);

			Block body = ast.newBlock();
			rewrite.set(decl, FunctionDeclaration.BODY_PROPERTY, body, null);

			String label = CorrectionMessages.ModifierCorrectionSubProcessor_addmissingbody_description;
			Image image = DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_CHANGE);
			ASTRewriteCorrectionProposal proposal = new ASTRewriteCorrectionProposal(label, cu, rewrite, 9, image);

			proposals.add(proposal);
		}
		if (!Flags.isAbstract(mdecl.getModifier())) {
			ASTRewrite rewrite = ASTRewrite.create(ast);
			rewrite.set(mdecl, MethodDeclaration.MODIFIER_PROPERTY, mdecl.getModifier() | Modifiers.AccAbstract, null);

			String label = CorrectionMessages.ModifierCorrectionSubProcessor_setmethodabstract_description;
			Image image = DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_CHANGE);
			LinkedCorrectionProposal proposal = new LinkedCorrectionProposal(label, cu, rewrite, 8, image);
			proposals.add(proposal);
		}

	}

	public static void addAbstractMethodProposals(IInvocationContext context, IProblemLocation problem,
			Collection proposals) {
		ISourceModule cu = context.getCompilationUnit();

		Program astRoot = context.getASTRoot();

		ASTNode selectedNode = problem.getCoveringNode(astRoot);
		if (selectedNode == null) {
			return;
		}
		FunctionDeclaration funcDecl = null;
		while ((selectedNode = selectedNode.getParent()) != null) {
			if (selectedNode instanceof FunctionDeclaration) {
				funcDecl = (FunctionDeclaration) selectedNode;
				break;
			}
		}
		if (funcDecl == null) {
			return;
		}

		MethodDeclaration mdecl = (MethodDeclaration) funcDecl.getParent();
		ASTNode parent = funcDecl;
		ClassDeclaration parentTypeDecl = null;
		boolean parentIsAbstractClass = false;
		while ((parent = parent.getParent()) != null) {
			if (parent instanceof ClassDeclaration) {
				parentTypeDecl = (ClassDeclaration) parent;
				if (Flags.isAbstract(parentTypeDecl.getModifier())) {
					parentIsAbstractClass = true;
				}
				break;
			} else if (parent instanceof AnonymousClassDeclaration) {
				parentIsAbstractClass = false;
				break;
			}
		}

		boolean hasNoBody = funcDecl.getBody() == null;

		IProblemIdentifier id = problem.getProblemIdentifier();
		if (id == PhpProblemIdentifier.AbstractMethodInAbstractClass || parentIsAbstractClass) {
			AST ast = astRoot.getAST();
			ASTRewrite rewrite = ASTRewrite.create(ast);

			rewrite.set(mdecl, MethodDeclaration.MODIFIER_PROPERTY, mdecl.getModifier() & ~Modifiers.AccAbstract, null);

			if (hasNoBody) {
				Block body = ast.newBlock();
				rewrite.set(funcDecl, FunctionDeclaration.BODY_PROPERTY, body, null);
			}

			String label = CorrectionMessages.ModifierCorrectionSubProcessor_removeabstract_description;
			Image image = DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_CHANGE);
			ASTRewriteCorrectionProposal proposal = new ASTRewriteCorrectionProposal(label, cu, rewrite, 6, image);
			proposals.add(proposal);
		}

		if (!hasNoBody && id == PhpProblemIdentifier.BodyForAbstractMethod) {
			ASTRewrite rewrite = ASTRewrite.create(funcDecl.getAST());
			rewrite.remove(funcDecl.getBody(), null);

			String label = CorrectionMessages.ModifierCorrectionSubProcessor_removebody_description;
			Image image = DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_CHANGE);
			ASTRewriteCorrectionProposal proposal2 = new ASTRewriteCorrectionProposal(label, cu, rewrite, 5, image);
			proposals.add(proposal2);
		}

		if (id == PhpProblemIdentifier.AbstractMethodInAbstractClass && parentTypeDecl != null) {
			addMakeTypeAbstractProposal(context, parentTypeDecl, proposals, cu);
		}

	}

	public static void addAbstractTypeProposals(IInvocationContext context, IProblemLocation problem,
			Collection proposals) {
		Program astRoot = context.getASTRoot();

		ASTNode selectedNode = problem.getCoveringNode(astRoot);
		if (selectedNode == null) {
			return;
		}

		ClassDeclaration parentTypeDecl = null;
		if (selectedNode instanceof Identifier) {
			ASTNode parent = selectedNode.getParent();
			if (parent != null) {
				parentTypeDecl = (ClassDeclaration) parent;
			}
		} else if (selectedNode instanceof ClassDeclaration) {
			parentTypeDecl = (ClassDeclaration) selectedNode;
		}

		if (parentTypeDecl == null) {
			return;
		}

		addMakeTypeAbstractProposal(context, parentTypeDecl, proposals, context.getCompilationUnit());
	}

	public static void addMakeTypeAbstractProposal(IInvocationContext context, ClassDeclaration parentTypeDecl,
			Collection proposals, ISourceModule cu) {
		ASTRewrite rewrite = ASTRewrite.create(parentTypeDecl.getAST());
		rewrite.set(parentTypeDecl, ClassDeclaration.MODIFIER_PROPERTY,
				parentTypeDecl.getModifier() | Modifiers.AccAbstract, null);
		String label = Messages.format(CorrectionMessages.ModifierCorrectionSubProcessor_addabstract_description,
				parentTypeDecl.getName().getName());
		Image image = DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_CHANGE);
		ASTRewriteCorrectionProposal proposal = new ASTRewriteCorrectionProposal(label, cu, rewrite, 5, image);
		proposals.add(proposal);
	}

}
