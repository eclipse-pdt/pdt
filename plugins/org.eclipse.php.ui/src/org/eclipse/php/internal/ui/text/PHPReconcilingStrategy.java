/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.text;

import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
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
import org.eclipse.php.internal.ui.editor.IPHPScriptReconcilingListener;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.ast.ASTProvider;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

public class PHPReconcilingStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension {

	private ITextEditor fEditor;

	/**
	 * Short cache to transfer the reconcile AST to the {@link #reconciled()}
	 * method.
	 *
	 */
	private Program fAST;
	private IDocumentProvider fDocumentProvider;
	private IProgressMonitor fProgressMonitor;
	private boolean fNotify = true;
	private IDocument document;

	private IPHPScriptReconcilingListener fPHPReconcilingListener;
	private boolean fIsPHPReconcilingListener;

	public PHPReconcilingStrategy(ITextEditor editor) {
		this.fEditor = editor;
		fDocumentProvider = DLTKUIPlugin.getDefault().getSourceModuleDocumentProvider();
		fIsPHPReconcilingListener = editor instanceof IPHPScriptReconcilingListener;
		if (fIsPHPReconcilingListener) {
			fPHPReconcilingListener = (IPHPScriptReconcilingListener) editor;
		}
	}

	private void reconcile(final boolean initialReconcile) {
		if (!(fEditor instanceof PHPStructuredEditor)) {
			return;
		}

		final IModelElement modelElement = ((PHPStructuredEditor) fEditor).getModelElement();
		if (modelElement != null) {
			SafeRunner.run(new ISafeRunnable() {
				@Override
				public void run() throws ModelException {
					fAST = reconcile((ISourceModule) modelElement, true);
				}

				@Override
				public void handleException(Throwable ex) {
					IStatus status = new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.OK,
							"Error in php Core during reconcile", ex); //$NON-NLS-1$
					PHPCorePlugin.getDefault().getLog().log(status);
				}
			});
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
				if (createdAST != null && document != null) {
					createdAST.setSourceModule(unit);
					createdAST.setSourceRange(0, document.getLength());
					createdAST.setLineEndTable(Util.lineEndTable(document));
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
		if (model instanceof IProblemRequestorExtension)
			return (IProblemRequestorExtension) model;
		return null;
	}

	@Override
	public void reconcile(IRegion partition) {
		reconcile(false);
	}

	@Override
	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		reconcile(false);
	}

	@Override
	public void setDocument(IDocument document) {
		this.document = document;
	}

	@Override
	public void setProgressMonitor(IProgressMonitor monitor) {
		fProgressMonitor = monitor;
	}

	@Override
	public void initialReconcile() {
		reconcile(true);
	}

	/**
	 * Tells this strategy whether to inform its listeners.
	 *
	 * @param notify
	 *            <code>true</code> if listeners should be notified
	 */
	public void notifyListeners(boolean notify) {
		fNotify = notify;
	}

	/**
	 * Called before reconciling is started.
	 *
	 */
	public void aboutToBeReconciled() {
		if (fIsPHPReconcilingListener)
			fPHPReconcilingListener.aboutToBeReconciled();
	}

	/**
	 * Called when reconcile has finished.
	 *
	 */
	public void reconciled() {
		// Always notify listeners, see
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=55969 for the final
		// solution
		try {
			if (fIsPHPReconcilingListener) {
				IProgressMonitor pm = fProgressMonitor;
				if (pm == null)
					pm = new NullProgressMonitor();
				fPHPReconcilingListener.reconciled(fAST, !fNotify, pm);
			}
		} finally {
			fNotify = true;
			fAST = null;
		}
	}
}
