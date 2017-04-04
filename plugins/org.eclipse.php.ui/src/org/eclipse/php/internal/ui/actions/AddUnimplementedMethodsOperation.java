/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.core.ast.rewrite.ListRewrite;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.ui.util.CodeGenerationUtils;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

/**
 * Workspace runnable to add unimplemented methods.
 * 
 * @since 3.1
 */
public final class AddUnimplementedMethodsOperation implements IWorkspaceRunnable {

	/** Should the resulting edit be applied? */
	private final boolean fApply;

	/** The method binding keys for which a method was generated */
	private final List<String> fCreatedMethods = new ArrayList<>();

	/** The insertion point, or <code>-1</code> */
	private final int fInsertPos;

	/** The method bindings to implement */
	private final IMethodBinding[] fMethodsToImplement;

	/** Specified if comments should be created */
	private boolean fDoCreateComments;

	/** The type declaration to add the methods to */
	private final ITypeBinding fType;

	/** The compilation unit AST node */
	private final Program fASTRoot;

	private IDocument fDocument;

	private IType fElement;

	/**
	 * Creates a new add unimplemented methods operation.
	 * 
	 * @param astRoot
	 *            the compilation unit AST node
	 * @param type
	 *            the type to add the methods to
	 * @param methodsToImplement
	 *            the method bindings to implement or <code>null</code> to
	 *            implement all unimplemented methods
	 * @param insertPos
	 *            the insertion point, or <code>-1</code>
	 * @param imports
	 *            <code>true</code> if the import edits should be applied,
	 *            <code>false</code> otherwise
	 * @param apply
	 *            <code>true</code> if the resulting edit should be applied,
	 *            <code>false</code> otherwise
	 * @param save
	 *            <code>true</code> if the changed compilation unit should be
	 *            saved, <code>false</code> otherwise
	 * @param doc
	 */
	public AddUnimplementedMethodsOperation(Program astRoot, IType element, ITypeBinding type,
			IMethodBinding[] methodsToImplement, int insertPos, final boolean apply, IDocument doc) {
		if (astRoot == null) {
			throw new IllegalArgumentException("AST must not be null and has to be created from a ICompilationUnit"); //$NON-NLS-1$
		}
		if (type == null) {
			throw new IllegalArgumentException("The type must not be null"); //$NON-NLS-1$
		}

		fDocument = doc;
		fType = type;
		fInsertPos = insertPos;
		fASTRoot = astRoot;
		fMethodsToImplement = methodsToImplement;
		fApply = apply;
		fElement = element;
	}

	@Override
	public final void run(IProgressMonitor monitor) throws CoreException {
		if (monitor == null)
			monitor = new NullProgressMonitor();
		try {
			monitor.beginTask("", 2); //$NON-NLS-1$
			monitor.setTaskName("AddUnimplementedMethodsOperation_description"); //$NON-NLS-1$
			fCreatedMethods.clear();
			Program cu = fASTRoot.getProgramRoot();

			AST ast = fASTRoot.getAST();

			ASTRewrite astRewrite = ASTRewrite.create(ast);

			ITypeBinding currTypeBinding = fType;
			ListRewrite memberRewriter = null;

			try {
				ASTNode node = null;
				if (PHPFlags.isAnonymous(fElement.getFlags())) {
					node = fASTRoot.getElementAt(fElement.getSourceRange().getOffset());
					while (!(node instanceof Program)) {
						node = fASTRoot.getElementAt(node.getEnd());
						if (node instanceof ClassInstanceCreation) {
							if (((ClassInstanceCreation) node).getAnonymousClassDeclaration() != null) {
								node = ((ClassInstanceCreation) node).getAnonymousClassDeclaration();
								break;
							}
						}
					}
				} else {
					node = fASTRoot.getElementAt(fElement.getNameRange().getOffset()).getParent();
				}
				if (node instanceof ClassDeclaration) {
					memberRewriter = astRewrite.getListRewrite(((ClassDeclaration) node).getBody(),
							Block.STATEMENTS_PROPERTY);
				} else if (node instanceof AnonymousClassDeclaration) {
					memberRewriter = astRewrite.getListRewrite(((AnonymousClassDeclaration) node).getBody(),
							Block.STATEMENTS_PROPERTY);
				} else {
					throw new IllegalArgumentException();
					// not possible, we checked this in the constructor
				}

			} catch (ModelException e) {
				throw e;
			}

			final CodeGenerationSettings settings = new CodeGenerationSettings();
			settings.createComments = fDoCreateComments;

			ASTNode insertion = null;
			if (fInsertPos != -1) {
				insertion = CodeGenerationUtils.getNodeToInsertBefore(memberRewriter, fInsertPos);
			}

			IMethodBinding[] methodsToImplement = fMethodsToImplement;
			if (methodsToImplement == null) {
				methodsToImplement = CodeGenerationUtils.getUnimplementedMethods(currTypeBinding);
			}

			for (IMethodBinding curr : methodsToImplement) {
				MethodDeclaration stub = CodeGenerationUtils.createImplementationStub(cu, astRewrite, curr,
						currTypeBinding.getName(), settings, currTypeBinding.isInterface());
				if (stub != null) {
					fCreatedMethods.add(curr.getKey());
					if (insertion != null) {
						memberRewriter.insertBefore(stub, insertion, null);
					} else {
						memberRewriter.insertLast(stub, null);
					}
				}
			}

			TextEdit fEdit = astRewrite.rewriteAST(fDocument, null);
			if (fApply) {
				try {
					fEdit.apply(fDocument);
				} catch (MalformedTreeException | BadLocationException e) {
					throw new CoreException(
							new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.ERROR, "error file content", null)); //$NON-NLS-1$
				}
			}

		} finally {
			monitor.done();
		}
	}

	public void setCreateComments(boolean createComments) {
		fDoCreateComments = createComments;
	}

	/**
	 * Returns the method binding keys for which a method has been generated.
	 * 
	 * @return the method binding keys
	 */
	public final String[] getCreatedMethods() {
		final String[] keys = new String[fCreatedMethods.size()];
		fCreatedMethods.toArray(keys);
		return keys;
	}

	/**
	 * Returns the scheduling rule for this operation.
	 * 
	 * @return the scheduling rule
	 */
	public final ISchedulingRule getSchedulingRule() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
}
