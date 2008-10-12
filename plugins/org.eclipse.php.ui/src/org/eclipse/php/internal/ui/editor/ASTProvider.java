/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.ASTParser;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.language.PHPVersion;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.corext.ASTNodes;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.php.ui.editor.SharedASTProvider.WAIT_FLAG;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Provides a shared AST for clients. The shared AST is
 * the AST of the active Php editor's input element.
 *
 * @since 3.0
 */
public final class ASTProvider {

	/**
	 * @deprecated Use {@link SharedASTProvider#WAIT_YES} instead.
	 */
	public static final WAIT_FLAG WAIT_YES= SharedASTProvider.WAIT_YES;

	/**
	 * @deprecated Use {@link SharedASTProvider#WAIT_ACTIVE_ONLY} instead.
	 */
	public static final WAIT_FLAG WAIT_ACTIVE_ONLY= SharedASTProvider.WAIT_ACTIVE_ONLY;
	
	/**
	 * @deprecated Use {@link SharedASTProvider#WAIT_NO} instead.
	 */
	public static final WAIT_FLAG WAIT_NO= SharedASTProvider.WAIT_NO;
	
	
	/**
	 * Tells whether this class is in debug mode.
	 * @since 3.0
	 */
	private static final boolean DEBUG= "true".equalsIgnoreCase(Platform.getDebugOption("org.eclipse.jdt.ui/debug/ASTProvider"));  //$NON-NLS-1$//$NON-NLS-2$


	/**
	 * Internal activation listener.
	 *
	 * @since 3.0
	 */
	private class ActivationListener implements IPartListener2, IWindowListener {


		/*
		 * @see org.eclipse.ui.IPartListener2#partActivated(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partActivated(IWorkbenchPartReference ref) {
			if (isPhpEditor(ref) && !isActiveEditor(ref))
				activePhpEditorChanged(ref.getPart(true));
		}

		/*
		 * @see org.eclipse.ui.IPartListener2#partBroughtToTop(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partBroughtToTop(IWorkbenchPartReference ref) {
			if (isPhpEditor(ref) && !isActiveEditor(ref))
				activePhpEditorChanged(ref.getPart(true));
		}

		/*
		 * @see org.eclipse.ui.IPartListener2#partClosed(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partClosed(IWorkbenchPartReference ref) {
			if (isActiveEditor(ref)) {
				if (DEBUG)
					System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "closed active editor: " + ref.getTitle()); //$NON-NLS-1$ //$NON-NLS-2$

				activePhpEditorChanged(null);
			}
		}

		/*
		 * @see org.eclipse.ui.IPartListener2#partDeactivated(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partDeactivated(IWorkbenchPartReference ref) {
		}

		/*
		 * @see org.eclipse.ui.IPartListener2#partOpened(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partOpened(IWorkbenchPartReference ref) {
			if (isPhpEditor(ref) && !isActiveEditor(ref))
				activePhpEditorChanged(ref.getPart(true));
		}

		/*
		 * @see org.eclipse.ui.IPartListener2#partHidden(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partHidden(IWorkbenchPartReference ref) {
		}

		/*
		 * @see org.eclipse.ui.IPartListener2#partVisible(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partVisible(IWorkbenchPartReference ref) {
			if (isPhpEditor(ref) && !isActiveEditor(ref))
				activePhpEditorChanged(ref.getPart(true));
		}

		/*
		 * @see org.eclipse.ui.IPartListener2#partInputChanged(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partInputChanged(IWorkbenchPartReference ref) {
			if (isPhpEditor(ref) && isActiveEditor(ref))
				activePhpEditorChanged(ref.getPart(true));
		}

		/*
		 * @see org.eclipse.ui.IWindowListener#windowActivated(org.eclipse.ui.IWorkbenchWindow)
		 */
		public void windowActivated(IWorkbenchWindow window) {
			IWorkbenchPartReference ref= window.getPartService().getActivePartReference();
			if (isPhpEditor(ref) && !isActiveEditor(ref))
				activePhpEditorChanged(ref.getPart(true));
		}

		/*
		 * @see org.eclipse.ui.IWindowListener#windowDeactivated(org.eclipse.ui.IWorkbenchWindow)
		 */
		public void windowDeactivated(IWorkbenchWindow window) {
		}

		/*
		 * @see org.eclipse.ui.IWindowListener#windowClosed(org.eclipse.ui.IWorkbenchWindow)
		 */
		public void windowClosed(IWorkbenchWindow window) {
			if (fActiveEditor != null && fActiveEditor.getSite() != null && window == fActiveEditor.getSite().getWorkbenchWindow()) {
				if (DEBUG)
					System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "closed active editor: " + fActiveEditor.getTitle()); //$NON-NLS-1$ //$NON-NLS-2$

				activePhpEditorChanged(null);
			}
			window.getPartService().removePartListener(this);
		}

		/*
		 * @see org.eclipse.ui.IWindowListener#windowOpened(org.eclipse.ui.IWorkbenchWindow)
		 */
		public void windowOpened(IWorkbenchWindow window) {
			window.getPartService().addPartListener(this);
		}

		private boolean isActiveEditor(IWorkbenchPartReference ref) {
			return ref != null && isActiveEditor(ref.getPart(false));
		}

		private boolean isActiveEditor(IWorkbenchPart part) {
			return part != null && (part == fActiveEditor);
		}

		private boolean isPhpEditor(IWorkbenchPartReference ref) {
			if (ref == null)
				return false;

			String id= ref.getId();

			// The instanceof check is not need but helps clients, see https://bugs.eclipse.org/bugs/show_bug.cgi?id=84862
			// return PhpUI.ID_CF_EDITOR.equals(id) || PhpUI.ID_CU_EDITOR.equals(id) || ref.getPart(false) instanceof PhpEditor;
			return true;
		}
	}

	public static final String SHARED_AST_LEVEL= PHPVersion.PHP5;
	public static final boolean SHARED_AST_STATEMENT_RECOVERY= true;
	public static final boolean SHARED_BINDING_RECOVERY= true;

	private static final String DEBUG_PREFIX= "ASTProvider > "; //$NON-NLS-1$


	private ISourceModule fReconcilingPhpElement;
	private ISourceModule fActivePhpElement;
	private Program fAST;
	private ActivationListener fActivationListener;
	private Object fReconcileLock= new Object();
	private Object fWaitLock= new Object();
	private boolean fIsReconciling;
	private IWorkbenchPart fActiveEditor;

	
	/**
	 * Returns the Php plug-in's AST provider.
	 * 
	 * @return the AST provider
	 * @since 3.2
	 */
	public static ASTProvider getASTProvider() {
		return PHPUiPlugin.getDefault().getASTProvider();
	}
	
	/**
	 * Creates a new AST provider.
	 */
	public ASTProvider() {
		install();
	}

	/**
	 * Installs this AST provider.
	 */
	void install() {
		// Create and register activation listener
		fActivationListener= new ActivationListener();
		PlatformUI.getWorkbench().addWindowListener(fActivationListener);

		// Ensure existing windows get connected
		IWorkbenchWindow[] windows= PlatformUI.getWorkbench().getWorkbenchWindows();
		for (int i= 0, length= windows.length; i < length; i++)
			windows[i].getPartService().addPartListener(fActivationListener);
	}

	private void activePhpEditorChanged(IWorkbenchPart editor) {

		ISourceModule phpElement= null;
		if (editor instanceof PHPStructuredEditor) {
			IModelElement inputModelElement = ((PHPStructuredEditor) editor).getModelElement();
			if (inputModelElement != null && inputModelElement.getElementType() == IModelElement.SOURCE_MODULE) {
				phpElement= (ISourceModule) inputModelElement;
			}			
		}

		synchronized (this) {
			fActiveEditor= editor;
			fActivePhpElement= phpElement;
			cache(null, phpElement);
		}

		if (DEBUG)
			System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "active editor is: " + toString(phpElement)); //$NON-NLS-1$ //$NON-NLS-2$

		synchronized (fReconcileLock) {
			if (fIsReconciling && (fReconcilingPhpElement == null || !fReconcilingPhpElement.equals(phpElement))) {
				fIsReconciling= false;
				fReconcilingPhpElement= null;
			} else if (phpElement == null) {
				fIsReconciling= false;
				fReconcilingPhpElement= null;
			}
		}
	}

	/**
	 * Returns whether the given compilation unit AST is
	 * cached by this AST provided.
	 *
	 * @param ast the compilation unit AST
	 * @return <code>true</code> if the given AST is the cached one
	 */
	public boolean isCached(Program ast) {
		return ast != null && fAST == ast;
	}

	/**
	 * Returns whether this AST provider is active on the given
	 * compilation unit.
	 *
	 * @param cu the compilation unit
	 * @return <code>true</code> if the given compilation unit is the active one
	 * @since 3.1
	 */
	public boolean isActive(ISourceModule cu) {
		return cu != null && cu.equals(fActivePhpElement);
	}

	/**
	 * Informs that reconciling for the given element is about to be started.
	 *
	 * @param phpElement the Php element
	 * @see org.eclipse.jdt.internal.ui.text.java.IPhpReconcilingListener#aboutToBeReconciled()
	 */
	void aboutToBeReconciled(ISourceModule phpElement) {

		if (phpElement == null)
			return;

		if (DEBUG)
			System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "about to reconcile: " + toString(phpElement)); //$NON-NLS-1$ //$NON-NLS-2$

		synchronized (fReconcileLock) {
			fIsReconciling= true;
			fReconcilingPhpElement= phpElement;
		}
		cache(null, phpElement);
	}

	/**
	 * Disposes the cached AST.
	 */
	private synchronized void disposeAST() {

		if (fAST == null)
			return;

		if (DEBUG)
			System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "disposing AST: " + toString(fAST) + " for: " + toString(fActivePhpElement)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		fAST= null;

		cache(null, null);
	}

	/**
	 * Returns a string for the given Php element used for debugging.
	 *
	 * @param phpElement the compilation unit AST
	 * @return a string used for debugging
	 */
	private String toString(ISourceModule phpElement) {
		if (phpElement == null)
			return "null"; //$NON-NLS-1$
		else
			return phpElement.getElementName();

	}

	/**
	 * Returns a string for the given AST used for debugging.
	 *
	 * @param ast the compilation unit AST
	 * @return a string used for debugging
	 */
	private String toString(Program ast) {
		if (ast == null)
			return "null"; //$NON-NLS-1$

		return ast.toString();
	}

	/**
	 * Caches the given compilation unit AST for the given Php element.
	 *
	 * @param ast
	 * @param phpElement
	 */
	private synchronized void cache(Program ast, ISourceModule phpElement) {

		if (fActivePhpElement != null && !fActivePhpElement.equals(phpElement)) {
			if (DEBUG && phpElement != null) // don't report call from disposeAST()
				System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "don't cache AST for inactive: " + toString(phpElement)); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}

		if (DEBUG && (phpElement != null || ast != null)) // don't report call from disposeAST()
			System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "caching AST: " + toString(ast) + " for: " + toString(phpElement)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		if (fAST != null)
			disposeAST();

		fAST= ast;

		// Signal AST change
		synchronized (fWaitLock) {
			fWaitLock.notifyAll();
		}
	}

	/**
	 * Returns a shared compilation unit AST for the given
	 * Php element.
	 * <p>
	 * Clients are not allowed to modify the AST and must
	 * synchronize all access to its nodes.
	 * </p>
	 *
	 * @param input
	 * 			the Php element, must not be <code>null</code>
	 * @param waitFlag
	 * 			{@link SharedASTProvider#WAIT_YES}, {@link SharedASTProvider#WAIT_NO} or {@link SharedASTProvider#WAIT_ACTIVE_ONLY}
	 * @param progressMonitor
	 * 			the progress monitor or <code>null</code>
	 * @return
	 * 			the AST or <code>null</code> if the AST is not available
	 * @throws IOException 
	 * @throws ModelException 
	 */
	public Program getAST(ISourceModule input, WAIT_FLAG waitFlag, IProgressMonitor progressMonitor) throws ModelException, IOException {
		if (input == null || waitFlag == null)
			throw new IllegalArgumentException("input or wait flag are null"); //$NON-NLS-1$
		
		if (progressMonitor != null && progressMonitor.isCanceled())
			return null;

		boolean isActiveElement;
		synchronized (this) {
			isActiveElement= input.equals(fActivePhpElement);
			if (isActiveElement) {
				if (fAST != null) {
					if (DEBUG)
						System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "returning cached AST:" + toString(fAST) + " for: " + input.getElementName()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

					return fAST;
				}
				if (waitFlag == SharedASTProvider.WAIT_NO) {
					if (DEBUG)
						System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "returning null (WAIT_NO) for: " + input.getElementName()); //$NON-NLS-1$ //$NON-NLS-2$

					return null;

				}
			}
		}
		if (isActiveElement && isReconciling(input)) {
			try {
				final ISourceModule activeElement= fReconcilingPhpElement;

				// Wait for AST
				synchronized (fWaitLock) {
					if (DEBUG)
						System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "waiting for AST for: " + input.getElementName()); //$NON-NLS-1$ //$NON-NLS-2$

					fWaitLock.wait();
				}

				// Check whether active element is still valid
				synchronized (this) {
					if (activeElement == fActivePhpElement && fAST != null) {
						if (DEBUG)
							System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "...got AST for: " + input.getElementName()); //$NON-NLS-1$ //$NON-NLS-2$

						return fAST;
					}
				}
				return getAST(input, waitFlag, progressMonitor);
			} catch (InterruptedException e) {
				return null; // thread has been interrupted don't compute AST
			}
		} else if (waitFlag == SharedASTProvider.WAIT_NO || (waitFlag == SharedASTProvider.WAIT_ACTIVE_ONLY && !(isActiveElement && fAST == null)))
			return null;

		if (isActiveElement)
			aboutToBeReconciled(input);

		Program ast= null;
		try {
			ast= createAST(input, progressMonitor);
			if (progressMonitor != null && progressMonitor.isCanceled()) {
				ast= null;
				if (DEBUG)
					System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "Ignore created AST for: " + input.getElementName() + " - operation has been cancelled"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		} finally {
			if (isActiveElement) {
				if (fAST != null) {
					// in the meantime, reconcile created a new AST. Return that one
					if (DEBUG)
						System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "Ignore created AST for " + input.getElementName() + " - AST from reconciler is newer"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					reconciled(fAST, input, null);
					return fAST;
				} else
					reconciled(ast, input, null);
			}
		}
		return ast;
	}

	/**
	 * Tells whether the given Php element is the one
	 * reported as currently being reconciled.
	 *
	 * @param phpElement the Php element
	 * @return <code>true</code> if reported as currently being reconciled
	 */
	private boolean isReconciling(ISourceModule phpElement) {
		synchronized (fReconcileLock) {
			return phpElement != null && phpElement.equals(fReconcilingPhpElement) && fIsReconciling;
		}
	}

	/**
	 * Creates a new compilation unit AST.
	 *
	 * @param input the Php element for which to create the AST
	 * @param progressMonitor the progress monitor
	 * @return AST
	 * @throws IOException 
	 * @throws ModelException 
	 */
	private static Program createAST(final ISourceModule input, final IProgressMonitor progressMonitor) throws ModelException, IOException {
		if (!hasSource(input))
			return null;
		
		if (progressMonitor != null && progressMonitor.isCanceled())
			return null;
		
		final ASTParser parser = ASTParser.newParser(SHARED_AST_LEVEL, input);
		// parser.setResolveBindings(true);
		// parser.setStatementsRecovery(SHARED_AST_STATEMENT_RECOVERY);
		// parser.setBindingsRecovery(SHARED_BINDING_RECOVERY);

		if (progressMonitor != null && progressMonitor.isCanceled())
			return null;

		final Program root[]= new Program[1]; 
		
		SafeRunner.run(new ISafeRunnable() {
			public void run() {
				try {
					if (progressMonitor != null && progressMonitor.isCanceled())
						return;
					if (DEBUG)
						System.err.println(getThreadName() + " - " + DEBUG_PREFIX + "creating AST for: " + input.getElementName()); //$NON-NLS-1$ //$NON-NLS-2$
					root[0]= parser.createAST(progressMonitor);
					
					//mark as unmodifiable
					ASTNodes.setFlagsToAST(root[0], ASTNode.PROTECT);
				} catch (OperationCanceledException ex) {
					return;
				} catch (Exception e) {
					return;
				}
			}
			public void handleException(Throwable ex) {
				IStatus status= new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.OK, "Error in JDT Core during AST creation", ex);  //$NON-NLS-1$
				PHPUiPlugin.getDefault().getLog().log(status);
			}
		});
		return root[0];
	}
	
	/**
	 * Checks whether the given Php element has accessible source.
	 * 
	 * @param je the Php element to test
	 * @return <code>true</code> if the element has source
	 * @since 3.2
	 */
	private static boolean hasSource(ISourceModule je) {
		if (je == null || !je.exists())
			return false;
		
		try {
			return je.getBuffer() != null;
		} catch (ModelException ex) {
			PHPUiPlugin.logErrorMessage("Error in PDT Core during AST creation");
		}
		return false;
	}
	
	/**
	 * Disposes this AST provider.
	 */
	public void dispose() {

		// Dispose activation listener
		PlatformUI.getWorkbench().removeWindowListener(fActivationListener);
		fActivationListener= null;

		disposeAST();

		synchronized (fWaitLock) {
			fWaitLock.notifyAll();
		}
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.text.java.IPhpReconcilingListener#reconciled(org.eclipse.jdt.core.dom.Program)
	 */
	public void reconciled(Program ast, ISourceModule phpElement, IProgressMonitor progressMonitor) {
		if (DEBUG)
			System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "reconciled: " + toString(phpElement) + ", AST: " + toString(ast)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		synchronized (fReconcileLock) {
			fIsReconciling= progressMonitor != null && progressMonitor.isCanceled();
			if (phpElement == null || !phpElement.equals(fReconcilingPhpElement)) {

				if (DEBUG)
					System.out.println(getThreadName() + " - " + DEBUG_PREFIX + "  ignoring AST of out-dated editor"); //$NON-NLS-1$ //$NON-NLS-2$

				// Signal - threads might wait for wrong element
				synchronized (fWaitLock) {
					fWaitLock.notifyAll();
				}

				return;
			}

			cache(ast, phpElement);
		}
	}

	private static String getThreadName() {
		String name= Thread.currentThread().getName();
		if (name != null)
			return name;
		else
			return Thread.currentThread().toString();
	}
	
}

