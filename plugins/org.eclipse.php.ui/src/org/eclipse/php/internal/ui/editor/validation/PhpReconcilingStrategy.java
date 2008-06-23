/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.validation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.text.IProblemRequestorExtension;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.IWorkingCopyManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.ast.nodes.ASTParser;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.IPhpScriptReconcilingListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.UIPlugin;
import org.eclipse.ui.part.FileEditorInput;
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

public class PhpReconcilingStrategy implements IValidator, ISourceValidator {

	private IEditorPart fEditor;
	
	private IWorkingCopyManager fManager;
	private IDocumentProvider fDocumentProvider;
	private IProgressMonitor fProgressMonitor;
	private IPhpScriptReconcilingListener fJavaReconcilingListener;
	
	private boolean fIsScriptReconcilingListener;

	private boolean fNotify = true;

	/**
	 * Short cache to transfer the reconcile AST to
	 * the {@link #reconciled()} method.
	 * 
	 * @since 1.1
	 */
	private Program fAST;

	private IDocument document;
	
	public void connect(IDocument document) {
		this.document = document;
	}
	
	public void install(IValidationContext helper) {
		FileEditorInput fEditorInput = findEditor(helper);
		if (fEditorInput == null) {
			return;
		}
		
		fManager = DLTKUIPlugin.getDefault().getWorkingCopyManager();
		fDocumentProvider = DLTKUIPlugin.getDefault().getSourceModuleDocumentProvider();

		fEditor = getEditor(fEditorInput);
		
		fIsScriptReconcilingListener= fEditor instanceof IPhpScriptReconcilingListener;
		if (fIsScriptReconcilingListener){
			fJavaReconcilingListener=(IPhpScriptReconcilingListener) fEditor;	
		}
		
	}

	private IEditorPart getEditor(FileEditorInput fEditorInput) {
		IWorkbenchWindow[] workbenchWindows = UIPlugin.getDefault().getWorkbench().getWorkbenchWindows();
		IEditorPart findEditor = workbenchWindows[0].getActivePage().findEditor(fEditorInput);

		return findEditor;
	}

	private FileEditorInput findEditor(IValidationContext helper) {
		String[] delta = helper.getURIs();
		if (delta.length > 0) {
			// get the file, model and document:
			IFile file = getFile(delta[0]);
			org.eclipse.ui.part.FileEditorInput fileEditorInput = new org.eclipse.ui.part.FileEditorInput(file);
			return fileEditorInput;
		}
		return null;
	}
	
	private void reconcile(final boolean initialReconcile) {
		// Assert.isTrue(fAST == null); // we'll see how this behaves ;-)
		final ISourceModule unit= fManager.getWorkingCopy(fEditor.getEditorInput(), false);
		if (unit != null) {
			SafeRunner.run(new ISafeRunnable() {
				public void run() throws ModelException {
					fAST= reconcile(unit, initialReconcile);
				}
				public void handleException(Throwable ex) {
					IStatus status= new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.OK, "Error in php Core during reconcile", ex);  //$NON-NLS-1$
					PHPCorePlugin.getDefault().getLog().log(status);
				}
			});
		}
	}

	/**
	 * Performs the reconcile and returns the AST if it was computed.
	 * 
	 * @param unit the compilation unit
	 * @param initialReconcile <code>true</code> if this is the initial reconcile
	 * @return the AST or <code>null</code> if none
	 * @throws JavaModelException if the original Java element does not exist
	 * @since 3.4
	 */
	private Program reconcile(ISourceModule unit, boolean initialReconcile) throws ModelException {
		/* fix for missing cancel flag communication */
		IProblemRequestorExtension extension= getProblemRequestorExtension();
		if (extension != null) {
			extension.setProgressMonitor(fProgressMonitor);
			extension.setIsActive(true);
		}

		try {
			// TODO : create ast if needed
			boolean isASTNeeded= initialReconcile || PHPUiPlugin.getDefault().getASTProvider().isActive(unit);
			// reconcile
			synchronized (unit) {
				unit.reconcile(true, null, fProgressMonitor);
			}
			if (isASTNeeded) {
				ASTParser newParser = ASTParser.newParser(ASTParser.VERSION_PHP5, unit);
				Program createdAST = newParser.createAST(null);
				createdAST.setSourceModule(unit);
				if (document != null) {
					createdAST.setLineEndTable(lineEndTable(document));
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
	

	private int[] lineEndTable(IDocument document) {
		int numberOfLines = document.getNumberOfLines();
		int[] result = new int[numberOfLines];
		int i = 0;
		while (i < numberOfLines) {
			try {
				String lineDelimiter = (i == numberOfLines - 1 ? "" : document.getLineDelimiter(i));				
				IRegion lineInformation = document.getLineInformation(i);
				result[i] = lineInformation.getOffset() + lineInformation.getLength() + lineDelimiter.length(); 
			} catch (BadLocationException e) {
				assert false;
				throw new IllegalStateException("PhpReconcilingStrategy#lineEndTable(document");
			}
			i++;			
		}
		// take care for the last line
		return result;
	}

	public void validate(IRegion dirtyRegion, IValidationContext helper, IReporter reporter) {
		// install editors
		if (fEditor == null ) {
			install(helper);
			if (fEditor == null) {
				return;
			}
		}	
		
		final ISourceModule unit = fManager.getWorkingCopy(fEditor.getEditorInput());
		if (unit == null) {
			return;
		}

		try {
			reconcile(true);
		} finally {
			// Always notify listeners, see https://bugs.eclipse.org/bugs/show_bug.cgi?id=55969 for the final solution
			try {
				if (fIsScriptReconcilingListener) {
					IProgressMonitor pm = fProgressMonitor;
					if (pm == null)
						pm = new NullProgressMonitor();
					fJavaReconcilingListener.reconciled(fAST, !fNotify, pm);
				}
			} finally {
				fNotify = true;
			}
		}
	}	
	
	/**
	 * @param delta
	 *            the IFileDelta containing the file name to get
	 * @return the IFile
	 */
	public IFile getFile(String delta) {
		IResource res = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(delta));
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
		}
		catch (Exception e) {
			// e.printStackTrace();
		}

		return model instanceof IDOMModel ? (IDOMModel) model : null;
	}
	
	private IProblemRequestorExtension getProblemRequestorExtension() {
		IAnnotationModel model = fDocumentProvider.getAnnotationModel(fEditor.getEditorInput());
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

	public void validate(IValidationContext helper, IReporter reporter) throws ValidationException {
		
	}

	public void disconnect(IDocument document) {
	}

}
