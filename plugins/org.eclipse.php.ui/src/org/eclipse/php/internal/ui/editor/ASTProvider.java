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
package org.eclipse.php.internal.ui.editor;

import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.ASTParser;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.corext.ASTNodes;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.php.ui.editor.SharedASTProvider.WAIT_FLAG;
import org.eclipse.ui.*;

/**
 * Provides a shared AST for clients. The shared AST is the AST of the active
 * Java editor's input element.
 * 
 * @since 3.0
 */
public final class ASTProvider {

	/**
	 * @deprecated Use {@link SharedASTProvider#WAIT_YES} instead.
	 */
	public static final WAIT_FLAG WAIT_YES = SharedASTProvider.WAIT_YES;

	/**
	 * @deprecated Use {@link SharedASTProvider#WAIT_ACTIVE_ONLY} instead.
	 */
	public static final WAIT_FLAG WAIT_ACTIVE_ONLY = SharedASTProvider.WAIT_ACTIVE_ONLY;

	/**
	 * @deprecated Use {@link SharedASTProvider#WAIT_NO} instead.
	 */
	public static final WAIT_FLAG WAIT_NO = SharedASTProvider.WAIT_NO;

	/**
	 * Tells whether this class is in debug mode.
	 * 
	 * @since 3.0
	 */
	private static final boolean DEBUG = "true".equalsIgnoreCase(Platform.getDebugOption("org.eclipse.jdt.ui/debug/ASTProvider")); //$NON-NLS-1$//$NON-NLS-2$

	/**
	 * Internal activation listener.
	 * 
	 * @since 3.0
	 */
	private class ActivationListener implements IPartListener2, IWindowListener {

		/*
		 * @seeorg.eclipse.ui.IPartListener2#partActivated(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		public void partActivated(IWorkbenchPartReference ref) {
			if (isJavaEditor(ref) && !isActiveEditor(ref))
				activeJavaEditorChanged(ref.getPart(true));
		}

		/*
		 * @seeorg.eclipse.ui.IPartListener2#partBroughtToTop(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		public void partBroughtToTop(IWorkbenchPartReference ref) {
			if (isJavaEditor(ref) && !isActiveEditor(ref))
				activeJavaEditorChanged(ref.getPart(true));
		}

		/*
		 * @seeorg.eclipse.ui.IPartListener2#partClosed(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		public void partClosed(IWorkbenchPartReference ref) {
			if (isActiveEditor(ref)) {
				if (DEBUG)
					System.out
							.println(getThreadName()
									+ " - " + DEBUG_PREFIX + "closed active editor: " + ref.getTitle()); //$NON-NLS-1$ //$NON-NLS-2$

				activeJavaEditorChanged(null);
			}
		}

		/*
		 * @seeorg.eclipse.ui.IPartListener2#partDeactivated(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		public void partDeactivated(IWorkbenchPartReference ref) {
		}

		/*
		 * @seeorg.eclipse.ui.IPartListener2#partOpened(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		public void partOpened(IWorkbenchPartReference ref) {
			if (isJavaEditor(ref) && !isActiveEditor(ref))
				activeJavaEditorChanged(ref.getPart(true));
		}

		/*
		 * @seeorg.eclipse.ui.IPartListener2#partHidden(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		public void partHidden(IWorkbenchPartReference ref) {
		}

		/*
		 * @seeorg.eclipse.ui.IPartListener2#partVisible(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		public void partVisible(IWorkbenchPartReference ref) {
			if (isJavaEditor(ref) && !isActiveEditor(ref))
				activeJavaEditorChanged(ref.getPart(true));
		}

		/*
		 * @seeorg.eclipse.ui.IPartListener2#partInputChanged(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		public void partInputChanged(IWorkbenchPartReference ref) {
			if (isJavaEditor(ref) && isActiveEditor(ref))
				activeJavaEditorChanged(ref.getPart(true));
		}

		/*
		 * @seeorg.eclipse.ui.IWindowListener#windowActivated(org.eclipse.ui.
		 * IWorkbenchWindow)
		 */
		public void windowActivated(IWorkbenchWindow window) {
			IWorkbenchPartReference ref = window.getPartService()
					.getActivePartReference();
			if (isJavaEditor(ref) && !isActiveEditor(ref))
				activeJavaEditorChanged(ref.getPart(true));
		}

		/*
		 * @seeorg.eclipse.ui.IWindowListener#windowDeactivated(org.eclipse.ui.
		 * IWorkbenchWindow)
		 */
		public void windowDeactivated(IWorkbenchWindow window) {
		}

		/*
		 * @seeorg.eclipse.ui.IWindowListener#windowClosed(org.eclipse.ui.
		 * IWorkbenchWindow)
		 */
		public void windowClosed(IWorkbenchWindow window) {
			if (fActiveEditor != null && fActiveEditor.getSite() != null
					&& window == fActiveEditor.getSite().getWorkbenchWindow()) {
				if (DEBUG)
					System.out
							.println(getThreadName()
									+ " - " + DEBUG_PREFIX + "closed active editor: " + fActiveEditor.getTitle()); //$NON-NLS-1$ //$NON-NLS-2$

				activeJavaEditorChanged(null);
			}
			window.getPartService().removePartListener(this);
		}

		/*
		 * @seeorg.eclipse.ui.IWindowListener#windowOpened(org.eclipse.ui.
		 * IWorkbenchWindow)
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

		private boolean isJavaEditor(IWorkbenchPartReference ref) {
			if (ref == null)
				return false;

			String id = ref.getId();

			// The instanceof check is not need but helps clients, see
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=84862
			return PHPUiConstants.PHP_EDITOR_ID.equals(id)
					|| PHPUiConstants.PHP_EDITOR_ID.equals(id)
					|| ref.getPart(false) instanceof PHPStructuredEditor;
		}
	}

	public static final PHPVersion SHARED_AST_LEVEL = PHPVersion.PHP5_4;
	public static final boolean SHARED_AST_STATEMENT_RECOVERY = true;
	public static final boolean SHARED_BINDING_RECOVERY = true;

	private static final String DEBUG_PREFIX = "ASTProvider > "; //$NON-NLS-1$

	private ISourceModule fReconcilingJavaElement;
	private ISourceModule fActiveJavaElement;
	private Program fAST;
	private ActivationListener fActivationListener;
	private Object fReconcileLock = new Object();
	private Object fWaitLock = new Object();
	private boolean fIsReconciling;
	private IWorkbenchPart fActiveEditor;

	private boolean isASTDirty;

	/**
	 * Returns the Java plug-in's AST provider.
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
		fActivationListener = new ActivationListener();
		PlatformUI.getWorkbench().addWindowListener(fActivationListener);

		// Ensure existing windows get connected
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench()
				.getWorkbenchWindows();
		for (int i = 0, length = windows.length; i < length; i++)
			windows[i].getPartService().addPartListener(fActivationListener);
	}

	private void activeJavaEditorChanged(IWorkbenchPart editor) {

		ISourceModule javaElement = null;
		if (editor instanceof PHPStructuredEditor)
			javaElement = (ISourceModule) ((PHPStructuredEditor) editor)
					.getModelElement();

		synchronized (this) {
			fActiveEditor = editor;
			fActiveJavaElement = javaElement;
			cache(null, javaElement);
		}

		if (DEBUG)
			System.out
					.println(getThreadName()
							+ " - " + DEBUG_PREFIX + "active editor is: " + toString(javaElement)); //$NON-NLS-1$ //$NON-NLS-2$

		synchronized (fReconcileLock) {
			if (fIsReconciling
					&& (fReconcilingJavaElement == null || !fReconcilingJavaElement
							.equals(javaElement))) {
				fIsReconciling = false;
				fReconcilingJavaElement = null;
			} else if (javaElement == null) {
				fIsReconciling = false;
				fReconcilingJavaElement = null;
			}
		}
	}

	/**
	 * Returns whether the given compilation unit AST is cached by this AST
	 * provided.
	 * 
	 * @param ast
	 *            the compilation unit AST
	 * @return <code>true</code> if the given AST is the cached one
	 */
	public boolean isCached(Program ast) {
		return ast != null && fAST == ast;
	}

	/**
	 * Returns whether this AST provider is active on the given compilation
	 * unit.
	 * 
	 * @param cu
	 *            the compilation unit
	 * @return <code>true</code> if the given compilation unit is the active one
	 * @since 3.1
	 */
	public boolean isActive(ISourceModule cu) {
		return cu != null && cu.equals(fActiveJavaElement);
	}

	/**
	 * Informs that reconciling for the given element is about to be started.
	 * 
	 * @param javaElement
	 *            the Java element
	 * @see org.eclipse.jdt.internal.ui.text.java.IJavaReconcilingListener#aboutToBeReconciled()
	 */
	void aboutToBeReconciled(ISourceModule javaElement) {

		if (javaElement == null)
			return;

		if (DEBUG)
			System.out
					.println(getThreadName()
							+ " - " + DEBUG_PREFIX + "about to reconcile: " + toString(javaElement)); //$NON-NLS-1$ //$NON-NLS-2$

		synchronized (fReconcileLock) {
			fIsReconciling = true;
			fReconcilingJavaElement = javaElement;
		}
		cache(null, javaElement);
	}

	/**
	 * Disposes the cached AST.
	 */
	private synchronized void disposeAST() {

		if (fAST == null)
			return;

		if (DEBUG)
			System.out
					.println(getThreadName()
							+ " - " + DEBUG_PREFIX + "disposing AST: " + toString(fAST) + " for: " + toString(fActiveJavaElement)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		fAST = null;

		cache(null, null);
	}

	/**
	 * Returns a string for the given Java element used for debugging.
	 * 
	 * @param javaElement
	 *            the compilation unit AST
	 * @return a string used for debugging
	 */
	private String toString(ISourceModule javaElement) {
		if (javaElement == null)
			return "null"; //$NON-NLS-1$
		else
			return javaElement.getElementName();

	}

	/**
	 * Returns a string for the given AST used for debugging.
	 * 
	 * @param ast
	 *            the compilation unit AST
	 * @return a string used for debugging
	 */
	private String toString(Program ast) {
		if (ast == null)
			return "null"; //$NON-NLS-1$

		// List types= ast.types();
		// if (types != null && types.size() > 0)
		// return
		// ((AbstractTypeDeclaration)types.get(0)).getName().getIdentifier();
		// else
		return "AST without any type"; //$NON-NLS-1$
	}

	/**
	 * Caches the given compilation unit AST for the given Java element.
	 * 
	 * @param ast
	 *            the ast
	 * @param javaElement
	 *            the java element
	 */
	private synchronized void cache(Program ast, ISourceModule javaElement) {

		if (fActiveJavaElement != null
				&& !fActiveJavaElement.equals(javaElement)) {
			if (DEBUG && javaElement != null) // don't report call from
												// disposeAST()
				System.out
						.println(getThreadName()
								+ " - " + DEBUG_PREFIX + "don't cache AST for inactive: " + toString(javaElement)); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}

		if (DEBUG && (javaElement != null || ast != null)) // don't report call
															// from disposeAST()
			System.out
					.println(getThreadName()
							+ " - " + DEBUG_PREFIX + "caching AST: " + toString(ast) + " for: " + toString(javaElement)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		if (fAST != null)
			disposeAST();

		fAST = ast;
		isASTDirty = false;

		// Signal AST change
		synchronized (fWaitLock) {
			fWaitLock.notifyAll();
		}
	}

	/**
	 * Returns a shared compilation unit AST for the given Java element.
	 * <p>
	 * Clients are not allowed to modify the AST and must synchronize all access
	 * to its nodes.
	 * </p>
	 * 
	 * @param input
	 *            the Java element, must not be <code>null</code>
	 * @param waitFlag
	 *            {@link SharedASTProvider#WAIT_YES},
	 *            {@link SharedASTProvider#WAIT_NO} or
	 *            {@link SharedASTProvider#WAIT_ACTIVE_ONLY}
	 * @param progressMonitor
	 *            the progress monitor or <code>null</code>
	 * @return the AST or <code>null</code> if the AST is not available
	 */
	public Program getAST(ISourceModule input, WAIT_FLAG waitFlag,
			IProgressMonitor progressMonitor) {
		if (input == null || waitFlag == null)
			throw new IllegalArgumentException("input or wait flag are null"); //$NON-NLS-1$

		if (progressMonitor != null && progressMonitor.isCanceled())
			return null;

		boolean isActiveElement;
		synchronized (this) {
			isActiveElement = input.equals(fActiveJavaElement);
			if (isActiveElement) {
				if (fAST != null && !isASTDirty) {
					if (DEBUG)
						System.out
								.println(getThreadName()
										+ " - " + DEBUG_PREFIX + "returning cached AST:" + toString(fAST) + " for: " + input.getElementName()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

					return fAST;
				}
				if (waitFlag == SharedASTProvider.WAIT_NO) {
					if (DEBUG)
						System.out
								.println(getThreadName()
										+ " - " + DEBUG_PREFIX + "returning null (WAIT_NO) for: " + input.getElementName()); //$NON-NLS-1$ //$NON-NLS-2$

					return null;

				}
			}
		}

		final boolean canReturnNull = waitFlag == SharedASTProvider.WAIT_NO
				|| (waitFlag == SharedASTProvider.WAIT_ACTIVE_ONLY && !(isActiveElement && fAST == null));
		boolean isReconciling = false;
		if (isActiveElement) {
			synchronized (fReconcileLock) {
				isReconciling = isReconciling(input);
				if (!isReconciling && !canReturnNull)
					aboutToBeReconciled(input);
			}
		}

		if (isReconciling) {
			try {
				final ISourceModule activeElement = fReconcilingJavaElement;

				// Wait for AST
				synchronized (fWaitLock) {
					if (DEBUG)
						System.out
								.println(getThreadName()
										+ " - " + DEBUG_PREFIX + "waiting for AST for: " + input.getElementName()); //$NON-NLS-1$ //$NON-NLS-2$

					fWaitLock.wait();
				}

				// Check whether active element is still valid
				synchronized (this) {
					if (activeElement == fActiveJavaElement && fAST != null) {
						if (DEBUG)
							System.out
									.println(getThreadName()
											+ " - " + DEBUG_PREFIX + "...got AST for: " + input.getElementName()); //$NON-NLS-1$ //$NON-NLS-2$

						return fAST;
					}
				}
				return getAST(input, waitFlag, progressMonitor);
			} catch (InterruptedException e) {
				return null; // thread has been interrupted don't compute AST
			}
		} else if (canReturnNull)
			return null;

		Program ast = null;
		try {
			ast = createAST(input, progressMonitor);
			if (progressMonitor != null && progressMonitor.isCanceled()) {
				ast = null;
				if (DEBUG)
					System.out
							.println(getThreadName()
									+ " - " + DEBUG_PREFIX + "Ignore created AST for: " + input.getElementName() + " - operation has been cancelled"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		} finally {
			if (isActiveElement) {
				if (fAST != null) {
					// in the meantime, reconcile created a new AST. Return that
					// one
					if (DEBUG)
						System.out
								.println(getThreadName()
										+ " - " + DEBUG_PREFIX + "Ignore created AST for " + input.getElementName() + " - AST from reconciler is newer"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					reconciled(fAST, input, null);
					return fAST;
				} else
					reconciled(ast, input, null);
			}
		}
		return ast;
	}

	/**
	 * Tells whether the given Java element is the one reported as currently
	 * being reconciled.
	 * 
	 * @param javaElement
	 *            the Java element
	 * @return <code>true</code> if reported as currently being reconciled
	 */
	private boolean isReconciling(ISourceModule javaElement) {
		synchronized (fReconcileLock) {
			return javaElement != null
					&& javaElement.equals(fReconcilingJavaElement)
					&& fIsReconciling;
		}
	}

	/**
	 * Creates a new compilation unit AST.
	 * 
	 * @param input
	 *            the Java element for which to create the AST
	 * @param progressMonitor
	 *            the progress monitor
	 * @return AST
	 */
	private static Program createAST(final ISourceModule input,
			final IProgressMonitor progressMonitor) {
		if (!hasSource(input))
			return null;

		if (progressMonitor != null && progressMonitor.isCanceled())
			return null;

		final ASTParser parser = ASTParser.newParser(SHARED_AST_LEVEL, input);
		if (parser == null) {
			return null;
		}

		if (progressMonitor != null && progressMonitor.isCanceled())
			return null;

		final Program root[] = new Program[1];

		SafeRunner.run(new ISafeRunnable() {
			public void run() {
				try {
					if (progressMonitor != null && progressMonitor.isCanceled())
						return;
					if (DEBUG)
						System.err
								.println(getThreadName()
										+ " - " + DEBUG_PREFIX + "creating AST for: " + input.getElementName()); //$NON-NLS-1$ //$NON-NLS-2$
					root[0] = (Program) parser.createAST(progressMonitor);

					// mark as unmodifiable
					ASTNodes.setFlagsToAST(root[0], ASTNode.PROTECT);
				} catch (OperationCanceledException ex) {
					return;
				} catch (Exception e) {
					PHPUiPlugin.log(e);
					return;
				}
			}

			public void handleException(Throwable ex) {
				IStatus status = new Status(IStatus.ERROR, PHPUiPlugin.ID,
						IStatus.OK, "Error in PDT UI during AST creation", ex); //$NON-NLS-1$
				PHPUiPlugin.log(status);
			}
		});
		return root[0];
	}

	/**
	 * Checks whether the given Java element has accessible source.
	 * 
	 * @param je
	 *            the Java element to test
	 * @return <code>true</code> if the element has source
	 * @since 3.2
	 */
	private static boolean hasSource(ISourceModule je) {
		if (je == null || !je.exists())
			return false;

		return true;
		// try {
		// return je.getBuffer() != null;
		// } catch (ModelException ex) {
		//			IStatus status= new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.OK, "Error in PDT UI during AST creation", ex);  //$NON-NLS-1$
		// PHPUiPlugin.log(status);
		// }
		// return false;
	}

	/**
	 * Disposes this AST provider.
	 */
	public void dispose() {

		// Dispose activation listener
		PlatformUI.getWorkbench().removeWindowListener(fActivationListener);
		fActivationListener = null;

		disposeAST();

		synchronized (fWaitLock) {
			fWaitLock.notifyAll();
		}
	}

	/*
	 * @see
	 * org.eclipse.jdt.internal.ui.text.java.IJavaReconcilingListener#reconciled
	 * (org.eclipse.jdt.core.dom.Program)
	 */
	void reconciled(Program ast, ISourceModule javaElement,
			IProgressMonitor progressMonitor) {
		if (DEBUG)
			System.out
					.println(getThreadName()
							+ " - " + DEBUG_PREFIX + "reconciled: " + toString(javaElement) + ", AST: " + toString(ast)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		synchronized (fReconcileLock) {
			if (javaElement == null
					|| !javaElement.equals(fReconcilingJavaElement)) {

				if (DEBUG)
					System.out
							.println(getThreadName()
									+ " - " + DEBUG_PREFIX + "  ignoring AST of out-dated editor"); //$NON-NLS-1$ //$NON-NLS-2$

				// Signal - threads might wait for wrong element
				synchronized (fWaitLock) {
					fWaitLock.notifyAll();
				}

				return;
			}
			fIsReconciling = progressMonitor != null
					&& progressMonitor.isCanceled();
			cache(ast, javaElement);
		}
	}

	private static String getThreadName() {
		String name = Thread.currentThread().getName();
		if (name != null)
			return name;
		else
			return Thread.currentThread().toString();
	}

	public void markASTDirty() {
		isASTDirty = true;
	}
}
