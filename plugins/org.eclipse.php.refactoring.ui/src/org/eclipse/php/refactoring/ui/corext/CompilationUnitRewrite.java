/*******************************************************************************
 * Copyright (c) 2009, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.corext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.WorkingCopyOwner;
import org.eclipse.dltk.core.manipulation.SourceModuleChange;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.CategorizedTextEditGroup;
import org.eclipse.ltk.core.refactoring.GroupCategorySet;
import org.eclipse.php.core.ast.nodes.AST;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.refactoring.ui.RefactoringUIPlugin;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.text.edits.TextEditGroup;

/**
 * A {@link ProgramRewrite} holds all data structures that are typically
 * required for non-trivial refactorings. All getters are initialized lazily to
 * avoid lengthy processing in
 * {@link org.eclipse.ltk.core.refactoring.Refactoring#checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)}
 * .
 * <p>
 * Bindings are resolved by default, but can be disabled with
 * <code>setResolveBindings(false)</code>. Statements recovery is enabled by
 * default, but can be disabled with <code>setStatementsRecovery(false)</code>.
 * Bindings recovery is disabled by default, but can be enabled with
 * <code>setBindingRecovery(true)</code>.
 * </p>
 */
public class CompilationUnitRewrite {

	// TODO: add RefactoringStatus fStatus;?
	private ISourceModule fCu;
	private List<TextEditGroup> fTextEditGroups = new ArrayList<>();

	private Program fRoot; // lazily initialized
	private ASTRewrite fRewrite; // lazily initialized
	// private ImportRewrite fImportRewrite; // lazily initialized
	// private ImportRemover fImportRemover; // lazily initialized
	private boolean fResolveBindings = true;
	private boolean fStatementsRecovery = true;
	private boolean fBindingsRecovery = false;
	private final WorkingCopyOwner fOwner;
	private IDocument fRememberContent = null;

	public CompilationUnitRewrite(ISourceModule cu) {
		this(null, cu, null);
	}

	public CompilationUnitRewrite(WorkingCopyOwner owner, ISourceModule cu) {
		this(owner, cu, null);
	}

	public CompilationUnitRewrite(ISourceModule cu, Program root) {
		this(null, cu, root);
	}

	public CompilationUnitRewrite(WorkingCopyOwner owner, ISourceModule cu, Program root) {
		fOwner = owner;
		fCu = cu;
		fRoot = root;
	}

	public void rememberContent() {
		fRememberContent = new Document();
	}

	/**
	 * Controls whether the compiler should provide binding information for the
	 * AST nodes it creates. To be effective, this method must be called before
	 * any of {@link #getRoot()},{@link #getASTRewrite()},
	 * {@link #getImportRemover()}. This method has no effect if the target
	 * object has been created with
	 * {@link #ProgramRewrite(ISourceModule, Program)}.
	 * <p>
	 * Defaults to <b><code>true</code></b> (do resolve bindings).
	 * </p>
	 * 
	 * @param resolve
	 *            <code>true</code> if bindings are wanted, and
	 *            <code>false</code> if bindings are not of interest
	 * @see org.eclipse.jdt.core.dom.ASTParser#setResolveBindings(boolean) Note:
	 *      The default value (<code>true</code>) differs from the one of the
	 *      corresponding method in ASTParser.
	 */
	public void setResolveBindings(boolean resolve) {
		fResolveBindings = resolve;
	}

	/**
	 * Controls whether the compiler should perform statements recovery. To be
	 * effective, this method must be called before any of {@link #getRoot()},
	 * {@link #getASTRewrite()}, {@link #getImportRemover()}. This method has no
	 * effect if the target object has been created with
	 * {@link #ProgramRewrite(ISourceModule, Program)}.
	 * <p>
	 * Defaults to <b><code>true</code></b> (do perform statements recovery).
	 * </p>
	 * 
	 * @param statementsRecovery
	 *            whether statements recovery should be performed
	 * @see org.eclipse.jdt.core.dom.ASTParser#setStatementsRecovery(boolean)
	 */
	public void setStatementsRecovery(boolean statementsRecovery) {
		fStatementsRecovery = statementsRecovery;
	}

	/**
	 * Controls whether the compiler should perform bindings recovery. To be
	 * effective, this method must be called before any of {@link #getRoot()},
	 * {@link #getASTRewrite()}, {@link #getImportRemover()}. This method has no
	 * effect if the target object has been created with
	 * {@link #ProgramRewrite(ISourceModule, Program)}.
	 * <p>
	 * Defaults to <b><code>false</code></b> (do not perform bindings recovery).
	 * </p>
	 * 
	 * @param bindingsRecovery
	 *            whether bindings recovery should be performed
	 * @see org.eclipse.jdt.core.dom.ASTParser#setBindingsRecovery(boolean)
	 */
	public void setBindingRecovery(boolean bindingsRecovery) {
		fBindingsRecovery = bindingsRecovery;
	}

	public void clearASTRewrite() {
		fRewrite = null;
		fTextEditGroups = new ArrayList<>();
	}

	public void clearImportRewrites() {
		// fImportRewrite= null;
	}

	public void clearASTAndImportRewrites() {
		clearASTRewrite();
		// fImportRewrite= null;
	}

	public CategorizedTextEditGroup createCategorizedGroupDescription(String name, GroupCategorySet set) {
		CategorizedTextEditGroup result = new CategorizedTextEditGroup(name, set);
		fTextEditGroups.add(result);
		return result;
	}

	public TextEditGroup createGroupDescription(String name) {
		TextEditGroup result = new TextEditGroup(name);
		fTextEditGroups.add(result);
		return result;
	}

	/**
	 * Creates a compilation unit change based on the events recorded by this
	 * compilation unit rewrite.
	 * 
	 * @return a {@link SourceModuleChange}, or <code>null</code> for an empty
	 *         change
	 * @throws CoreException
	 *             when text buffer acquisition or import rewrite text edit
	 *             creation fails
	 * @throws IllegalArgumentException
	 *             when the AST rewrite encounters problems
	 */
	public SourceModuleChange createChange() throws CoreException {
		return createChange(true, null);
	}

	/**
	 * Creates a compilation unit change based on the events recorded by this
	 * compilation unit rewrite.
	 * 
	 * @param generateGroups
	 *            <code>true</code> to generate text edit groups,
	 *            <code>false</code> otherwise
	 * @param monitor
	 *            the progress monitor or <code>null</code>
	 * @return a {@link SourceModuleChange}, or <code>null</code> for an empty
	 *         change
	 * @throws CoreException
	 *             when text buffer acquisition or import rewrite text edit
	 *             creation fails
	 * @throws IllegalArgumentException
	 *             when the AST rewrite encounters problems
	 */
	public SourceModuleChange createChange(boolean generateGroups, IProgressMonitor monitor) throws CoreException {
		return createChange(fCu.getElementName(), generateGroups, monitor);
	}

	/**
	 * Creates a compilation unit change based on the events recorded by this
	 * compilation unit rewrite.
	 * 
	 * @param name
	 *            the name of the change to create
	 * @param generateGroups
	 *            <code>true</code> to generate text edit groups,
	 *            <code>false</code> otherwise
	 * @param monitor
	 *            the progress monitor or <code>null</code>
	 * @return a {@link SourceModuleChange}, or <code>null</code> for an empty
	 *         change
	 * @throws CoreException
	 *             when text buffer acquisition or import rewrite text edit
	 *             creation fails
	 * @throws IllegalArgumentException
	 *             when the AST rewrite encounters problems
	 */
	public SourceModuleChange createChange(String name, boolean generateGroups, IProgressMonitor monitor)
			throws CoreException {
		SourceModuleChange cuChange = new SourceModuleChange(name, fCu);
		MultiTextEdit multiEdit = new MultiTextEdit();
		cuChange.setEdit(multiEdit);
		return attachChange(cuChange, generateGroups, monitor);
	}

	/**
	 * Attaches the changes of this compilation unit rewrite to the given CU
	 * Change. The given change <b>must</b> either have no root edit, or a
	 * MultiTextEdit as a root edit. The edits in the given change <b>must
	 * not</b> overlap with the changes of this compilation unit.
	 * 
	 * @param cuChange
	 *            existing SourceModuleChange with a MultiTextEdit root or no
	 *            root at all.
	 * @param generateGroups
	 *            <code>true</code> to generate text edit groups,
	 *            <code>false</code> otherwise
	 * @param monitor
	 *            the progress monitor or <code>null</code>
	 * @return a change combining the changes of this rewrite and the given
	 *         rewrite, or <code>null</code> for an empty change
	 * @throws CoreException
	 *             when text buffer acquisition or import rewrite text edit
	 *             creation fails
	 */
	public SourceModuleChange attachChange(SourceModuleChange cuChange, boolean generateGroups,
			IProgressMonitor monitor) throws CoreException {
		try {
			boolean needsAstRewrite = fRewrite != null; // TODO: do we need
														// something like
														// ASTRewrite#hasChanges()
														// here?
			// boolean needsImportRemoval= fImportRemover != null &&
			// fImportRemover.hasRemovedNodes();
			// boolean needsImportRewrite= fImportRewrite != null &&
			// fImportRewrite.hasRecordedChanges() || needsImportRemoval;
			if (!needsAstRewrite/*
								 * && !needsImportRemoval && !needsImportRewrite
								 */)
				return null;

			MultiTextEdit multiEdit = (MultiTextEdit) cuChange.getEdit();
			if (multiEdit == null) {
				multiEdit = new MultiTextEdit();
				cuChange.setEdit(multiEdit);
			}

			if (needsAstRewrite) {
				TextEdit rewriteEdit;
				if (fRememberContent != null) {
					rewriteEdit = fRewrite.rewriteAST(fRememberContent, fCu.getScriptProject().getOptions(true));
				} else {
					rewriteEdit = fRewrite.rewriteAST();
				}
				if (!isEmptyEdit(rewriteEdit)) {
					multiEdit.addChild(rewriteEdit);
					if (generateGroups) {
						for (Iterator<TextEditGroup> iter = fTextEditGroups.iterator(); iter.hasNext();) {
							TextEditGroup group = iter.next();
							cuChange.addTextEditGroup(group);
						}
					}
				}
			}
			// if (needsImportRemoval) {
			// fImportRemover.applyRemoves(getImportRewrite());
			// }
			// if (needsImportRewrite) {
			// TextEdit importsEdit= fImportRewrite.rewriteImports(monitor);
			// if (!isEmptyEdit(importsEdit)) {
			// multiEdit.addChild(importsEdit);
			// String importUpdateName=
			// RefactoringCoreMessages.ASTData_update_imports;
			// cuChange.addTextEditGroup(new TextEditGroup(importUpdateName,
			// importsEdit));
			// }
			// } else {
			//
			// }
			if (isEmptyEdit(multiEdit))
				return null;
			return cuChange;
		} finally {
			if (monitor != null)
				monitor.done();
		}
	}

	private static boolean isEmptyEdit(TextEdit edit) {
		return edit.getClass() == MultiTextEdit.class && !edit.hasChildren();
	}

	public ISourceModule getCu() {
		return fCu;
	}

	public Program getRoot() {
		if (fRoot == null) {
			try {
				fRoot = new RefactoringASTParser(ProjectOptions.getPHPVersion(fCu), ProjectOptions.useShortTags(fCu))
						.parse(fCu, fOwner, fResolveBindings, fStatementsRecovery, fBindingsRecovery, null);
			} catch (Exception e) {
				RefactoringUIPlugin.log(e);
			}
		}
		return fRoot;
	}

	public AST getAST() {
		return getRoot().getAST();
	}

	public ASTRewrite getASTRewrite() {
		if (fRewrite == null) {
			fRewrite = ASTRewrite.create(getRoot().getAST());
			if (fRememberContent != null) { // wain until ast rewrite is
											// accessed first
				try {
					fRememberContent.set(fCu.getSource());
				} catch (ModelException e) {
					fRememberContent = null;
				}
			}
		}
		return fRewrite;
	}

	public void clearGroupDescriptions() {
		for (Iterator<TextEditGroup> iter = fTextEditGroups.iterator(); iter.hasNext();) {
			TextEditGroup group = iter.next();
			group.clearTextEdits();
		}
	}
}
