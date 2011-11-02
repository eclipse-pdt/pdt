/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.validation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.text.IProblemRequestorExtension;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.ASTParser;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.util.Util;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.IPhpScriptReconcilingListener;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.ui.internal.reconcile.validator.ISourceValidator;
import org.eclipse.wst.validation.internal.core.ValidationException;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;

/**
 * "As You Type" validation for PHP content This class reconciles php content to
 * the DLTK model
 */
public class PhpReconcilingStrategy implements IValidator, ISourceValidator {

	private IEditorPart fEditor;
	private IDocumentProvider fDocumentProvider;
	private IProgressMonitor fProgressMonitor;
	private IPhpScriptReconcilingListener fJavaReconcilingListener;
	private boolean fIsScriptReconcilingListener;
	private boolean fNotify = true;
	private IDocument document;

	public void connect(IDocument document) {
		this.document = document;
	}

	public void install(IValidationContext helper) {

		IEditorInput fEditorInput = findEditor(helper);

		fDocumentProvider = DLTKUIPlugin.getDefault()
				.getSourceModuleDocumentProvider();

		fEditor = getEditor(fEditorInput);

		fIsScriptReconcilingListener = fEditor instanceof IPhpScriptReconcilingListener;
		if (fIsScriptReconcilingListener) {
			fJavaReconcilingListener = (IPhpScriptReconcilingListener) fEditor;
		}
	}

	private IEditorPart getEditor(final IEditorInput editorInput) {
		final IEditorPart editor[] = new IEditorPart[1];
		Display.getDefault().syncExec(new Runnable() { // needs UI thread to
					// retrieve active page
					public void run() {
						IWorkbenchPage activePage = DLTKUIPlugin
								.getActivePage();
						if (activePage != null) {
							if (editorInput != null) {
								editor[0] = activePage.findEditor(editorInput);
							} else {
								editor[0] = activePage.getActiveEditor(); // workaround
								// for
								// external
								// files
								// editor
							}
						}
					}
				});
		return editor[0];
	}

	private IEditorInput findEditor(IValidationContext helper) {
		String[] delta = helper.getURIs();
		if (delta.length > 0) {
			// get the file, model and document:
			IFile file = getFile(delta[0]);
			if (file == null) {
				return null;
			}
			org.eclipse.ui.part.FileEditorInput fileEditorInput = new org.eclipse.ui.part.FileEditorInput(
					file);
			return fileEditorInput;
		}
		return null;
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
	private Program reconcile(ISourceModule unit, boolean initialReconcile)
			throws ModelException {
		/* fix for missing cancel flag communication */
		IProblemRequestorExtension extension = getProblemRequestorExtension();
		if (extension != null) {
			extension.setProgressMonitor(fProgressMonitor);
			extension.setIsActive(true);
		}

		try {
			// TODO : create ast if needed
			boolean isASTNeeded = initialReconcile
					|| PHPUiPlugin.getDefault().getASTProvider().isActive(unit);
			// reconcile
			synchronized (unit) {
				unit.reconcile(true, null, fProgressMonitor);
			}
			if (isASTNeeded) {
				PHPVersion phpVersion = ProjectOptions.getPhpVersion(unit
						.getScriptProject().getProject());
				ASTParser newParser = ASTParser.newParser(phpVersion, unit);
				Program createdAST = newParser.createAST(null);
				if (createdAST != null && document != null) {
					createdAST.setSourceModule(unit);
					createdAST.setSourceRange(0, document.getLength());
					createdAST.setLineEndTable(Util.lineEndTable(document));
				}
				return createdAST;
			}

		} catch (OperationCanceledException ex) {
			Assert.isTrue(fProgressMonitor == null
					|| fProgressMonitor.isCanceled());

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

	public void validate(IValidationContext helper, IReporter reporter)
			throws ValidationException {
		validate(null, helper, reporter);
	}

	public void validate(IRegion dirtyRegion, IValidationContext helper,
			IReporter reporter) {

		// install editors
		if (fEditor == null) {
			install(helper);
			if (fEditor == null) {
				return;
			}
		}

		if (!(fEditor instanceof PHPStructuredEditor)) {
			return;
		}

		final IModelElement modelElement = ((PHPStructuredEditor) fEditor)
				.getModelElement();
		if (modelElement instanceof ISourceModule) {
			final Program ast[] = new Program[1];
			try {
				SafeRunner.run(new ISafeRunnable() {
					public void run() throws ModelException {
						ast[0] = reconcile((ISourceModule) modelElement, true);
					}

					public void handleException(Throwable ex) {
						IStatus status = new Status(IStatus.ERROR,
								PHPUiPlugin.ID, IStatus.OK,
								"Error in php Core during reconcile", ex); //$NON-NLS-1$
						PHPCorePlugin.getDefault().getLog().log(status);
					}
				});
			} finally {
				// Always notify listeners, see
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=55969 for the
				// final solution
				try {
					if (fIsScriptReconcilingListener) {
						IProgressMonitor pm = fProgressMonitor;
						if (pm == null)
							pm = new NullProgressMonitor();
						fJavaReconcilingListener.reconciled(ast[0], !fNotify,
								pm);
					}
				} finally {
					fNotify = true;
				}
			}
		}
	}

	/**
	 * @param delta
	 *            the IFileDelta containing the file name to get
	 * @return the IFile
	 */
	public IFile getFile(String delta) {
		IResource res = null;
		try {
			res = ResourcesPlugin.getWorkspace().getRoot()
					.getFile(new Path(delta));
		} catch (Exception e) {
		}
		return res instanceof IFile ? (IFile) res : null;
	}

	/**
	 * 
	 * @param file
	 *            the file to get the model for
	 * @return the file's XMLModel
	 */
	protected IDOMModel getModelForResource(IFile file) {
		IStructuredModel model = null;
		IModelManager manager = StructuredModelManager.getModelManager();

		try {
			model = manager.getModelForRead(file);
			// TODO.. HTML validator tries again to get a model a 2nd way
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return model instanceof IDOMModel ? (IDOMModel) model : null;
	}

	private IProblemRequestorExtension getProblemRequestorExtension() {
		IAnnotationModel model = fDocumentProvider.getAnnotationModel(fEditor
				.getEditorInput());
		if (model instanceof IProblemRequestorExtension)
			return (IProblemRequestorExtension) model;
		return null;
	}

	public void aboutToBeReconciled() {
		if (fIsScriptReconcilingListener) {
			fJavaReconcilingListener.aboutToBeReconciled();
		}
	}

	public void notifyListeners(boolean notify) {
		fNotify = notify;
	}

	public void cleanup(IReporter reporter) {
	}

	public void disconnect(IDocument document) {
		this.document = null;
	}
}
