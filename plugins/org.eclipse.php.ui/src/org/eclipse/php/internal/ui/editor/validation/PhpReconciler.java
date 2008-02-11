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
import org.eclipse.dltk.internal.ui.text.IScriptReconcilingListener;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.IWorkingCopyManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IFileEditorInput;
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

public class PhpReconciler implements IValidator, ISourceValidator {

	private IFileEditorInput fEditorInput;

	private IWorkingCopyManager fManager;

	private IDocumentProvider fDocumentProvider;

	private IProgressMonitor fProgressMonitor;

	private IScriptReconcilingListener fJavaReconcilingListener;
	private boolean fIsScriptReconcilingListener;

	private boolean fNotify = true;

	private boolean firstLoad = true;

	public void connect(IDocument document) {
	}
	
	public void install(IValidationContext helper) {
		fEditorInput = findEditor(helper);
		if (fEditorInput == null) {
			return;
		}
		
		fManager = DLTKUIPlugin.getDefault().getWorkingCopyManager();
		fDocumentProvider = DLTKUIPlugin.getDefault().getSourceModuleDocumentProvider();
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

	public void validate(IRegion dirtyRegion, IValidationContext helper, IReporter reporter) {
		if (firstLoad ) {
			install(helper);
			firstLoad = false;
		}
		
		if (fEditorInput == null) {
			return;
		}
		
		final ISourceModule unit = fManager.getWorkingCopy(fEditorInput);

		if (unit == null) {
			return;
		}

		try {
			SafeRunner.run(new ISafeRunnable() {
				public void run() {
					try {
						/* fix for missing cancel flag communication */
						IProblemRequestorExtension extension = getProblemRequestorExtension();
						if (extension != null) {
							extension.setProgressMonitor(fProgressMonitor);
							extension.setIsActive(true);
						}

						try {
							// reconcile
							synchronized (unit) {
								unit.reconcile(true, null, fProgressMonitor);
							}
						} catch (OperationCanceledException ex) {
							Assert.isTrue(fProgressMonitor == null || fProgressMonitor.isCanceled());
						} finally {
							/* fix for missing cancel flag communication */
							if (extension != null) {
								extension.setProgressMonitor(null);
								extension.setIsActive(false);
							}
						}
					} catch (ModelException ex) {
						handleException(ex);
					}
				}

				public void handleException(Throwable ex) {
					IStatus status = new Status(IStatus.ERROR, DLTKUIPlugin.PLUGIN_ID, IStatus.OK, "Error in DLTK Core during reconcile", ex); //$NON-NLS-1$
					DLTKUIPlugin.getDefault().getLog().log(status);
				}
			});
		} finally {
			// Always notify listeners, see https://bugs.eclipse.org/bugs/show_bug.cgi?id=55969 for the final solution
			try {
				if (fIsScriptReconcilingListener) {
					IProgressMonitor pm = fProgressMonitor;
					if (pm == null)
						pm = new NullProgressMonitor();
					fJavaReconcilingListener.reconciled(unit, !fNotify, pm);
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
		IAnnotationModel model = fDocumentProvider.getAnnotationModel(fEditorInput);
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
