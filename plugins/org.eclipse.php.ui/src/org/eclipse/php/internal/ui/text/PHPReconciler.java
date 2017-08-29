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

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.spelling.SpellingService;

/**
 * A reconciler that is also activated on editor activation.
 */
public class PHPReconciler extends MonoReconciler {

	/**
	 * Internal part listener for activating the reconciler.
	 */
	private class PartListener implements IPartListener {

		/*
		 * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.
		 * IWorkbenchPart)
		 */
		@Override
		public void partActivated(IWorkbenchPart part) {
			if (part == fTextEditor) {
				if (hasJavaModelChanged())
					PHPReconciler.this.forceReconciling();
				setEditorActive(true);
			}
		}

		/*
		 * @see org.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.
		 * IWorkbenchPart)
		 */
		@Override
		public void partBroughtToTop(IWorkbenchPart part) {
		}

		/*
		 * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.
		 * IWorkbenchPart)
		 */
		@Override
		public void partClosed(IWorkbenchPart part) {
		}

		/*
		 * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.
		 * IWorkbenchPart)
		 */
		@Override
		public void partDeactivated(IWorkbenchPart part) {
			if (part == fTextEditor) {
				setJavaModelChanged(false);
				setEditorActive(false);
			}
		}

		/*
		 * @see org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.
		 * IWorkbenchPart)
		 */
		@Override
		public void partOpened(IWorkbenchPart part) {
		}
	}

	/**
	 * Internal Shell activation listener for activating the reconciler.
	 */
	private class ActivationListener extends ShellAdapter {

		private Control fControl;

		public ActivationListener(Control control) {
			Assert.isNotNull(control);
			fControl = control;
		}

		/*
		 * @see
		 * org.eclipse.swt.events.ShellListener#shellActivated(org.eclipse.swt.
		 * events.ShellEvent)
		 */
		@Override
		public void shellActivated(ShellEvent e) {
			if (!fControl.isDisposed() && fControl.isVisible()) {
				if (hasJavaModelChanged())
					PHPReconciler.this.forceReconciling();
				setEditorActive(true);
			}
		}

		/*
		 * @see
		 * org.eclipse.swt.events.ShellListener#shellDeactivated(org.eclipse.swt
		 * .events.ShellEvent)
		 */
		@Override
		public void shellDeactivated(ShellEvent e) {
			if (!fControl.isDisposed() && fControl.getShell() == e.getSource()) {
				setJavaModelChanged(false);
				setEditorActive(false);
			}
		}
	}

	/**
	 * Internal Java element changed listener
	 *
	 */
	private class ElementChangedListener implements IElementChangedListener {
		@Override
		public void elementChanged(ElementChangedEvent event) {
			if (isRunningInReconcilerThread())
				return;

			if (canIgnore(event.getDelta().getAffectedChildren()))
				return;

			if (isEditorActive())
				PHPReconciler.this.forceReconciling();
		}

		/**
		 * Check whether the given delta has been sent when saving this
		 * reconciler's editor.
		 *
		 * @param delta
		 *            the deltas
		 * @return <code>true</code> if the given delta
		 */
		private boolean canIgnore(IModelElementDelta[] delta) {
			if (delta.length != 1)
				return false;

			// become working copy
			if (delta[0].getFlags() == IModelElementDelta.F_PRIMARY_WORKING_COPY)
				return true;

			// save
			if (delta[0].getFlags() == IModelElementDelta.F_PRIMARY_RESOURCE
					&& delta[0].getElement().equals(fReconciledElement))
				return true;

			return canIgnore(delta[0].getAffectedChildren());
		}

	}

	/**
	 * Internal resource change listener.
	 *
	 * @since 3.0
	 */
	class ResourceChangeListener implements IResourceChangeListener {

		private IResource getResource() {
			IEditorInput input = fTextEditor.getEditorInput();
			if (input instanceof IFileEditorInput) {
				IFileEditorInput fileInput = (IFileEditorInput) input;
				return fileInput.getFile();
			}
			return null;
		}

		/*
		 * @see
		 * IResourceChangeListener#resourceChanged(org.eclipse.core.resources.
		 * IResourceChangeEvent)
		 */
		@Override
		public void resourceChanged(IResourceChangeEvent e) {
			if (isRunningInReconcilerThread())
				return;

			IResourceDelta delta = e.getDelta();
			IResource resource = getResource();
			if (delta != null && resource != null) {
				IResourceDelta child = delta.findMember(resource.getFullPath());
				if (child != null) {
					IMarkerDelta[] deltas = child.getMarkerDeltas();
					int i = deltas.length;
					while (--i >= 0) {
						if (deltas[i].isSubtypeOf(IMarker.PROBLEM)) {
							forceReconciling();
							return;
						}
					}
				}
			}
		}
	}

	/** The reconciler's editor */
	private ITextEditor fTextEditor;
	/** The part listener */
	private IPartListener fPartListener;
	/** The shell listener */
	private ShellListener fActivationListener;
	/**
	 * The mutex that keeps us from running multiple reconcilers on one editor.
	 */
	private Object fMutex;
	/**
	 * The Java element changed listener.
	 * 
	 * @since 3.0
	 */
	private IElementChangedListener fJavaElementChangedListener;
	/**
	 * Tells whether the Java model sent out a changed event.
	 * 
	 * @since 3.0
	 */
	private volatile boolean fHasJavaModelChanged = false;
	/**
	 * Tells whether this reconciler's editor is active.
	 * 
	 * @since 3.1
	 */
	private volatile boolean fIsEditorActive = true;
	/**
	 * The resource change listener.
	 * 
	 * @since 3.0
	 */
	private IResourceChangeListener fResourceChangeListener;
	/**
	 * The property change listener.
	 * 
	 * @since 3.3
	 */
	private IPropertyChangeListener fPropertyChangeListener;

	private boolean fIninitalProcessDone = false;

	/**
	 * The element that this reconciler reconciles.
	 * 
	 */
	private IModelElement fReconciledElement;

	/**
	 * Creates a new reconciler.
	 *
	 * @param editor
	 *            the editor
	 * @param strategy
	 *            the reconcile strategy
	 * @param isIncremental
	 *            <code>true</code> if this is an incremental reconciler
	 */
	public PHPReconciler(ITextEditor editor, PHPCompositeReconcilingStrategy strategy, boolean isIncremental) {
		super(strategy, isIncremental);
		fTextEditor = editor;

		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=63898
		// when re-using editors, a new reconciler is set up by the source
		// viewer
		// and the old one uninstalled. However, the old reconciler may still be
		// running.
		// To avoid having to reconcilers calling
		// CompilationUnitEditor.reconciled,
		// we synchronized on a lock object provided by the editor.
		// The critical section is really the entire run() method of the
		// reconciler
		// thread, but synchronizing process() only will keep
		// JavaReconcilingStrategy
		// from running concurrently on the same editor.
		// TODO remove once we have ensured that there is only one reconciler
		// per editor.
		if (editor instanceof PHPStructuredEditor)
			fMutex = ((PHPStructuredEditor) editor).getReconcilerLock();
		else
			fMutex = new Object(); // Null Object
	}

	/*
	 * @see
	 * org.eclipse.jface.text.reconciler.IReconciler#install(org.eclipse.jface.
	 * text.ITextViewer)
	 */
	@Override
	public void install(ITextViewer textViewer) {
		super.install(textViewer);

		fPartListener = new PartListener();
		IWorkbenchPartSite site = fTextEditor.getSite();
		IWorkbenchWindow window = site.getWorkbenchWindow();
		window.getPartService().addPartListener(fPartListener);

		fActivationListener = new ActivationListener(textViewer.getTextWidget());
		Shell shell = window.getShell();
		shell.addShellListener(fActivationListener);

		fJavaElementChangedListener = new ElementChangedListener();
		DLTKCore.addElementChangedListener(fJavaElementChangedListener);

		fResourceChangeListener = new ResourceChangeListener();
		IWorkspace workspace = PHPUiPlugin.getWorkspace();
		workspace.addResourceChangeListener(fResourceChangeListener);

		fPropertyChangeListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (SpellingService.PREFERENCE_SPELLING_ENABLED.equals(event.getProperty())
						|| SpellingService.PREFERENCE_SPELLING_ENGINE.equals(event.getProperty()))
					forceReconciling();
			}
		};
		// JavaPlugin.getDefault().getCombinedPreferenceStore().addPropertyChangeListener(fPropertyChangeListener);

		fReconciledElement = EditorUtility.getEditorInputModelElement(fTextEditor, false);
	}

	/*
	 * @see org.eclipse.jface.text.reconciler.IReconciler#uninstall()
	 */
	@Override
	public void uninstall() {

		IWorkbenchPartSite site = fTextEditor.getSite();
		IWorkbenchWindow window = site.getWorkbenchWindow();
		window.getPartService().removePartListener(fPartListener);
		fPartListener = null;

		Shell shell = window.getShell();
		if (shell != null && !shell.isDisposed())
			shell.removeShellListener(fActivationListener);
		fActivationListener = null;

		DLTKCore.removeElementChangedListener(fJavaElementChangedListener);
		fJavaElementChangedListener = null;

		IWorkspace workspace = PHPUiPlugin.getWorkspace();
		workspace.removeResourceChangeListener(fResourceChangeListener);
		fResourceChangeListener = null;

		// DLTKCore.getDefault().getCombinedPreferenceStore().removePropertyChangeListener(fPropertyChangeListener);
		fPropertyChangeListener = null;

		super.uninstall();
	}

	/*
	 * @see
	 * org.eclipse.jface.text.reconciler.AbstractReconciler#forceReconciling()
	 */
	@Override
	protected void forceReconciling() {
		if (!fIninitalProcessDone)
			return;

		super.forceReconciling();
		PHPCompositeReconcilingStrategy strategy = (PHPCompositeReconcilingStrategy) getReconcilingStrategy(
				IDocument.DEFAULT_CONTENT_TYPE);
		strategy.notifyListeners(false);
	}

	/*
	 * @see
	 * org.eclipse.jface.text.reconciler.AbstractReconciler#aboutToReconcile()
	 * 
	 * @since 3.0
	 */
	@Override
	protected void aboutToBeReconciled() {
		PHPCompositeReconcilingStrategy strategy = (PHPCompositeReconcilingStrategy) getReconcilingStrategy(
				IDocument.DEFAULT_CONTENT_TYPE);
		strategy.aboutToBeReconciled();
	}

	/*
	 * @see
	 * org.eclipse.jface.text.reconciler.AbstractReconciler#reconcilerReset()
	 */
	@Override
	protected void reconcilerReset() {
		super.reconcilerReset();
		PHPCompositeReconcilingStrategy strategy = (PHPCompositeReconcilingStrategy) getReconcilingStrategy(
				IDocument.DEFAULT_CONTENT_TYPE);
		strategy.notifyListeners(true);
	}

	/*
	 * @see org.eclipse.jface.text.reconciler.MonoReconciler#initialProcess()
	 */
	@Override
	protected void initialProcess() {
		synchronized (fMutex) {
			super.initialProcess();
		}
		fIninitalProcessDone = true;
	}

	/**
	 * Tells whether the Java Model has changed or not.
	 *
	 * @return <code>true</code> iff the Java Model has changed
	 * @since 3.0
	 */
	private synchronized boolean hasJavaModelChanged() {
		return fHasJavaModelChanged;
	}

	/**
	 * Sets whether the Java Model has changed or not.
	 *
	 * @param state
	 *            <code>true</code> iff the java model has changed
	 * @since 3.0
	 */
	private synchronized void setJavaModelChanged(boolean state) {
		fHasJavaModelChanged = state;
	}

	/**
	 * Tells whether this reconciler's editor is active.
	 *
	 * @return <code>true</code> iff the editor is active
	 * @since 3.1
	 */
	private synchronized boolean isEditorActive() {
		return fIsEditorActive;
	}

	/**
	 * Sets whether this reconciler's editor is active.
	 *
	 * @param state
	 *            <code>true</code> iff the editor is active
	 * @since 3.1
	 */
	private synchronized void setEditorActive(boolean state) {
		fIsEditorActive = state;
	}
}
