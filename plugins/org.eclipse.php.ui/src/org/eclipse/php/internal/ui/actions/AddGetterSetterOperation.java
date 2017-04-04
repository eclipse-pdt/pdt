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

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.core.ast.rewrite.ListRewrite;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.ui.util.CodeGenerationUtils;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

/**
 * Workspace runnable to add accessor methods to fields.
 * 
 */
public final class AddGetterSetterOperation implements IWorkspaceRunnable {

	/** The accessor fields */
	private final IField[] fAccessorFields;

	/** Should the resulting edit be applied? */
	private boolean fApply = true;

	/** The resulting text edit */
	private TextEdit fEdit = null;

	/** The getter fields */
	private final IField[] fGetterFields;

	/** The insertion point, or <code>null</code> */
	private final IMethod fInsert;

	/** The setter fields */
	private final IField[] fSetterFields;

	/** The code generation settings to use */
	private final CodeGenerationSettings fSettings;

	/** Should all existing members be skipped? */
	private boolean fSkipAllExisting = false;

	/** Should the accessors be sorted? */
	private boolean fSort = false;

	/** The type declaration to add the constructors to */
	private final IType fType;

	/** The compilation unit ast node */
	private final Program fASTRoot;

	/** The visibility flags of the new accessors */
	private int fVisibility = Modifiers.AccPublic;

	private IDocument fDocument;

	/**
	 * Creates a new add getter setter operation.
	 * 
	 * @param type
	 *            the type to add the accessors to
	 * @param getters
	 *            the fields to create getters for
	 * @param setters
	 *            the fields to create setters for
	 * @param accessors
	 *            the fields to create both
	 * @param unit
	 *            the compilation unit ast node
	 * @param skipExistingQuery
	 *            the request query
	 * @param insert
	 *            the insertion point, or <code>null</code>
	 * @param settings
	 *            the code generation settings to use
	 * @param apply
	 *            <code>true</code> if the resulting edit should be applied,
	 *            <code>false</code> otherwise
	 * @param save
	 *            <code>true</code> if the changed compilation unit should be
	 *            saved, <code>false</code> otherwise
	 */
	public AddGetterSetterOperation(final IType type, final IField[] getters, final IField[] setters,
			final IField[] accessors, final Program unit, final IDocument document, final IMethod insert,
			final CodeGenerationSettings settings, final boolean apply) {
		Assert.isNotNull(type);
		Assert.isNotNull(unit);
		Assert.isNotNull(settings);
		fType = type;
		fGetterFields = getters;
		fSetterFields = setters;
		fAccessorFields = accessors;
		fASTRoot = unit;
		fInsert = insert;
		fSettings = settings;
		fApply = apply;
		fDocument = document;

	}

	/**
	 * Generates a new getter method for the specified field
	 * 
	 * @param field
	 *            the field
	 * @param rewrite
	 *            the list rewrite to use
	 * @throws CoreException
	 *             if an error occurs
	 * @throws OperationCanceledException
	 *             if the operation has been cancelled
	 */
	private void generateGetterMethod(final IField field, final ListRewrite rewrite) throws CoreException {
		final IType type = field.getDeclaringType();
		final String name = CodeGenerationUtils.getGetterName(field);
		final IMethod existing = CodeGenerationUtils.findMethod(name, 0, false, type);
		if (existing == null) {
			IModelElement sibling = fInsert;
			ASTNode insertion = CodeGenerationUtils.getNodeToInsertBefore(rewrite, sibling);

			CodeGenerationUtils.createGetterStub(field, name, fSettings.createComments,
					fVisibility | (field.getFlags() & Flags.AccStatic), type, rewrite, insertion);
		}
	}

	/**
	 * Generates a new setter method for the specified field
	 * 
	 * @param field
	 *            the field
	 * @param astRewrite
	 * @param rewrite
	 *            the list rewrite to use
	 * @throws CoreException
	 *             if an error occurs
	 * @throws OperationCanceledException
	 *             if the operation has been cancelled
	 */
	private void generateSetterMethod(final IField field, ASTRewrite astRewrite, final ListRewrite rewrite)
			throws CoreException {
		final IType type = field.getDeclaringType();
		final String name = CodeGenerationUtils.getSetterName(field);
		final IMethod existing = CodeGenerationUtils.findMethod(name, 1, false, type);
		if (existing == null) {
			IModelElement sibling = fInsert;
			ASTNode insertion = CodeGenerationUtils.getNodeToInsertBefore(rewrite, sibling);
			CodeGenerationUtils.createSetterStub(field, name, fSettings.createComments,
					fVisibility | (field.getFlags() & Flags.AccStatic), rewrite, insertion);
		}
	}

	/**
	 * Returns the resulting text edit.
	 * 
	 * @return the resulting text edit
	 */
	public final TextEdit getResultingEdit() {
		return fEdit;
	}

	/**
	 * Returns the scheduling rule for this operation.
	 * 
	 * @return the scheduling rule
	 */
	public final ISchedulingRule getSchedulingRule() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	/**
	 * Returns the visibility modifier of the generated constructors.
	 * 
	 * @return the visibility modifier
	 */
	public final int getVisibility() {
		return fVisibility;
	}

	/**
	 * Should all existing members be skipped?
	 * 
	 * @return <code>true</code> if they should be skipped, <code>false</code>
	 *         otherwise
	 */
	public final boolean isSkipAllExisting() {
		return fSkipAllExisting;
	}

	@Override
	public final void run(IProgressMonitor monitor) throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor.setTaskName("AddGetterSetterOperation_description"); //$NON-NLS-1$
		monitor.beginTask("", fGetterFields.length + fSetterFields.length); //$NON-NLS-1$
		final ASTRewrite astRewrite = ASTRewrite.create(fASTRoot.getAST());
		ListRewrite listRewriter = null;

		final ClassDeclaration declaration = (ClassDeclaration) ASTNodes.getParent(
				NodeFinder.perform(fASTRoot, fType.getNameRange().getOffset(), fType.getNameRange().getLength()),
				ClassDeclaration.class);

		if (declaration != null) {
			listRewriter = astRewrite.getListRewrite(declaration.getBody(), Block.STATEMENTS_PROPERTY);
		}

		if (listRewriter == null) {
			throw new CoreException(
					new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.ERROR, "error_input_type_not_found", null)); //$NON-NLS-1$
		}
		if (!fSort) {
			for (IField accessorField : fAccessorFields) {
				generateGetterMethod(accessorField, listRewriter);
				generateSetterMethod(accessorField, astRewrite, listRewriter);
				monitor.worked(1);
				if (monitor.isCanceled()) {
					throw new OperationCanceledException();
				}
			}
		}
		for (IField getterField : fGetterFields) {
			generateGetterMethod(getterField, listRewriter);
			monitor.worked(1);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
		}
		for (IField setterField : fSetterFields) {
			generateSetterMethod(setterField, astRewrite, listRewriter);
			monitor.worked(1);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
		}
		fEdit = astRewrite.rewriteAST(fDocument, null);
		if (fApply) {
			try {
				fEdit.apply(fDocument);
			} catch (MalformedTreeException | BadLocationException e) {
				throw new CoreException(
						new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.ERROR, "error file content", null)); //$NON-NLS-1$
			}
		}

		monitor.done();
	}

	/**
	 * Determines whether existing members should be skipped.
	 * 
	 * @param skip
	 *            <code>true</code> to skip existing members, <code>false</code>
	 *            otherwise
	 */
	public final void setSkipAllExisting(final boolean skip) {
		fSkipAllExisting = skip;
	}

	public void setSort(boolean sort) {
		fSort = sort;
	}

	/**
	 * Sets the visibility modifier of the generated constructors.
	 * 
	 * @param visibility
	 *            the visibility modifier
	 */
	public final void setVisibility(final int visibility) {
		fVisibility = visibility;
	}
}
