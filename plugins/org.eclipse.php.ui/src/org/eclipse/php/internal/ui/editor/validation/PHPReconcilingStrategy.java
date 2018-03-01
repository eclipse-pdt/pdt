/*******************************************************************************
 * Copyright (c) 2009, 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.validation;

import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.editor.ISourceModuleDocumentProvider;
import org.eclipse.dltk.internal.ui.text.IProblemRequestorExtension;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.ast.util.Util;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.ast.ASTProvider;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.ui.texteditor.ITextEditor;

public class PHPReconcilingStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension {

	private ITextEditor fEditor;

	private IDocument fDocument;

	private IProgressMonitor fProgressMonitor;

	private ISourceModuleDocumentProvider fDocumentProvider;

	public PHPReconcilingStrategy(ITextEditor editor) {
		fEditor = editor;
		fDocumentProvider = DLTKUIPlugin.getDefault().getSourceModuleDocumentProvider();
	}

	@Override
	public void setProgressMonitor(IProgressMonitor monitor) {
		fProgressMonitor = monitor;
	}

	@Override
	public void initialReconcile() {
		reconcile(true);
	}

	@Override
	public void setDocument(IDocument document) {
		fDocument = document;
	}

	@Override
	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		reconcile(false);
	}

	@Override
	public void reconcile(IRegion partition) {
		reconcile(false);
	}

	private void reconcile(boolean initialReconcile) {
		if (!(fEditor instanceof PHPStructuredEditor)) {
			return;
		}
		if (fProgressMonitor == null) {
			fProgressMonitor = new NullProgressMonitor();
		}
		PHPStructuredEditor editor = ((PHPStructuredEditor) fEditor);
		final IModelElement modelElement = editor.getModelElement();
		if (modelElement instanceof ISourceModule) {

			final Program ast[] = new Program[1];
			SafeRunner.run(new ISafeRunnable() {
				@Override
				public void run() throws ModelException {
					ast[0] = reconcile((ISourceModule) modelElement, initialReconcile);
				}

				@Override
				public void handleException(Throwable ex) {
					IStatus status = new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.OK,
							"Error in php Core during reconcile", ex); //$NON-NLS-1$
					PHPCorePlugin.getDefault().getLog().log(status);
				}
			});
			editor.reconciled(ast[0], initialReconcile, fProgressMonitor);
		}
	}

	/**
	 * Performs the reconcile and returns the AST if it was computed.
	 * 
	 * @param unit
	 *            the compilation unit
	 * @param initialReconcile
	 *            <code>true</code> if this is the initial reconcile
	 * @return the AST or <code>null</code> if none
	 * @throws JavaModelException
	 *             if the original Java element does not exist
	 * @since 3.4
	 */
	private Program reconcile(ISourceModule unit, boolean initialReconcile) throws ModelException {
		/* fix for missing cancel flag communication */
		IProblemRequestorExtension extension = getProblemRequestorExtension();
		if (extension != null) {
			extension.setProgressMonitor(fProgressMonitor);
			extension.setIsActive(true);
		}

		try {
			final ASTProvider astProvider = PHPUiPlugin.getDefault().getASTProvider();

			synchronized (unit) {
				unit.reconcile(true, null, fProgressMonitor);
			}

			// read DOM AST from provider if available
			Program createdAST = astProvider.getAST(unit, SharedASTProvider.WAIT_NO, fProgressMonitor);
			if (astProvider.isActive(unit) && createdAST != null) {
				return createdAST;
			}

			if (initialReconcile || astProvider.isActive(unit)) {
				PHPVersion phpVersion = ProjectOptions.getPHPVersion(unit.getScriptProject().getProject());
				ASTParser newParser = ASTParser.newParser(phpVersion, unit);
				createdAST = newParser != null ? newParser.createAST(null) : null;
				if (createdAST != null && fDocument != null) {
					createdAST.setSourceModule(unit);
					createdAST.setSourceRange(0, fDocument.getLength());
					createdAST.setLineEndTable(Util.lineEndTable(fDocument));
				}
				return createdAST;
			}

		} catch (OperationCanceledException ex) {
			Assert.isTrue(fProgressMonitor == null || fProgressMonitor.isCanceled());

		} catch (Exception e) {
			throw new ModelException(e, IStatus.ERROR);

		} finally {
			/* fix for missing cancel flag communication */
			if (extension != null) {
				extension.setProgressMonitor(null);
				extension.setIsActive(false);
			}
		}

		return null;
	}

	private IProblemRequestorExtension getProblemRequestorExtension() {
		IAnnotationModel model = fDocumentProvider.getAnnotationModel(fEditor.getEditorInput());
		if (model instanceof IProblemRequestorExtension) {
			return (IProblemRequestorExtension) model;
		}
		return null;
	}

}
