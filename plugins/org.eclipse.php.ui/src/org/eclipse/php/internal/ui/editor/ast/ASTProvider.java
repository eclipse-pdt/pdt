/*******************************************************************************
 * Copyright (c) 2014, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
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
	 * Internal activation listener.
	 * 
	 * @since 3.0
	 */
	private class ActivationListener implements IPartListener2, IWindowListener {

		/*
		 * @see org.eclipse.ui.IPartListener2#partActivated(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		@Override
		public void partActivated(IWorkbenchPartReference ref) {
			if (isJavaEditor(ref) && !isActiveEditor(ref)) {
				activeJavaEditorChanged(ref.getPart(true));
			}
		}

		/*
		 * @see org.eclipse.ui.IPartListener2#partBroughtToTop(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		@Override
		public void partBroughtToTop(IWorkbenchPartReference ref) {
			if (isJavaEditor(ref) && !isActiveEditor(ref)) {
				activeJavaEditorChanged(ref.getPart(true));
			}
		}

		/*
		 * @see org.eclipse.ui.IPartListener2#partClosed(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		@Override
		public void partClosed(IWorkbenchPartReference ref) {
			if (isActiveEditor(ref)) {
				if (ASTUtils.DEBUG) {
					System.out
							.println(ASTUtils.getThreadName() + " - " + ASTUtils.DEBUG_PREFIX + "closed active editor: " //$NON-NLS-1$ //$NON-NLS-2$
									+ ref.getTitle());
				}
				activeJavaEditorChanged(null);
			}
		}

		/*
		 * @see org.eclipse.ui.IPartListener2#partDeactivated(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		@Override
		public void partDeactivated(IWorkbenchPartReference ref) {
		}

		/*
		 * @see org.eclipse.ui.IPartListener2#partOpened(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		@Override
		public void partOpened(IWorkbenchPartReference ref) {
			if (isJavaEditor(ref) && !isActiveEditor(ref)) {
				activeJavaEditorChanged(ref.getPart(true));
			}
		}

		/*
		 * @see org.eclipse.ui.IPartListener2#partHidden(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		@Override
		public void partHidden(IWorkbenchPartReference ref) {
		}

		/*
		 * @see org.eclipse.ui.IPartListener2#partVisible(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		@Override
		public void partVisible(IWorkbenchPartReference ref) {
			if (isJavaEditor(ref) && !isActiveEditor(ref)) {
				activeJavaEditorChanged(ref.getPart(true));
			}
		}

		/*
		 * @see org.eclipse.ui.IPartListener2#partInputChanged(org.eclipse.ui.
		 * IWorkbenchPartReference)
		 */
		@Override
		public void partInputChanged(IWorkbenchPartReference ref) {
			if (isJavaEditor(ref) && isActiveEditor(ref)) {
				activeJavaEditorChanged(ref.getPart(true));
			}
		}

		/*
		 * @see org.eclipse.ui.IWindowListener#windowActivated(org.eclipse.ui.
		 * IWorkbenchWindow)
		 */
		@Override
		public void windowActivated(IWorkbenchWindow window) {
			IWorkbenchPartReference ref = window.getPartService().getActivePartReference();
			if (isJavaEditor(ref) && !isActiveEditor(ref)) {
				activeJavaEditorChanged(ref.getPart(true));
			}
		}

		/*
		 * @see org.eclipse.ui.IWindowListener#windowDeactivated(org.eclipse.ui.
		 * IWorkbenchWindow)
		 */
		@Override
		public void windowDeactivated(IWorkbenchWindow window) {
		}

		/*
		 * @see org.eclipse.ui.IWindowListener#windowClosed(org.eclipse.ui.
		 * IWorkbenchWindow)
		 */
		@Override
		public void windowClosed(IWorkbenchWindow window) {
			if (fActiveEditor != null && fActiveEditor.getSite() != null
					&& window == fActiveEditor.getSite().getWorkbenchWindow()) {
				if (ASTUtils.DEBUG) {
					System.out
							.println(ASTUtils.getThreadName() + " - " + ASTUtils.DEBUG_PREFIX + "closed active editor: " //$NON-NLS-1$ //$NON-NLS-2$
									+ fActiveEditor.getTitle());
				}
				activeJavaEditorChanged(null);
			}
			window.getPartService().removePartListener(this);
		}

		/*
		 * @see org.eclipse.ui.IWindowListener#windowOpened(org.eclipse.ui.
		 * IWorkbenchWindow)
		 */
		@Override
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
			if (ref == null) {
				return false;
			}

			String id = ref.getId();

			// The instanceof check is not need but helps clients, see
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=84862
			return PHPUiConstants.PHP_EDITOR_ID.equals(id) || ref.getPart(false) instanceof PHPStructuredEditor;
		}
	}

	public static final boolean SHARED_AST_STATEMENT_RECOVERY = true;
	public static final boolean SHARED_BINDING_RECOVERY = true;

	// Optimize memory usage by caching (or not) only one AST
	private static final boolean KEEP_ONLY_ONE_AST = true;

	private final List<ASTCache> cachedASTs = new ArrayList<>();

	private ActivationListener fActivationListener;
	private IWorkbenchPart fActiveEditor;
	private ISourceModule fActiveJavaElement;

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
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (int i = 0, length = windows.length; i < length; i++) {
			windows[i].getPartService().addPartListener(fActivationListener);
		}
	}

	@NonNull
	private ASTCache getCacheFor(@NonNull final ISourceModule javaElement) {
		synchronized (cachedASTs) {
			ASTCache c = null;

			for (int i = cachedASTs.size() - 1; i >= 0; i--) {
				ASTCache current = cachedASTs.get(i);
				ISourceModule input = current.getInput();
				if (input == javaElement) {
					c = current;
				} else if (input == null) {
					cachedASTs.remove(i);
				}
			}

			if (c == null) {
				if (KEEP_ONLY_ONE_AST && cachedASTs.size() > 0) {
					cachedASTs.clear();
				}
				c = new ASTCache(javaElement);
				cachedASTs.add(c);
			}

			return c;
		}
	}

	private void activeJavaEditorChanged(final IWorkbenchPart editor) {

		ISourceModule javaElement = null;
		if (editor instanceof PHPStructuredEditor) {
			javaElement = (ISourceModule) ((PHPStructuredEditor) editor).getModelElement();
		}

		synchronized (this) {
			fActiveEditor = editor;
			fActiveJavaElement = javaElement;
		}

		if (ASTUtils.DEBUG) {
			System.out.println(ASTUtils.getThreadName() + " - " + ASTUtils.DEBUG_PREFIX + "active editor is: " //$NON-NLS-1$ //$NON-NLS-2$
					+ toString(javaElement));
		}
	}

	/**
	 * Returns whether this AST provider is active on the given compilation unit.
	 * 
	 * @param cu
	 *            the compilation unit
	 * @return <code>true</code> if the given compilation unit is the active one
	 * @since 3.1
	 */
	public boolean isActive(final ISourceModule cu) {
		synchronized (this) {
			return cu != null && cu == fActiveJavaElement;
		}
	}

	/**
	 * Informs that reconciling for the given element is about to be started.
	 * 
	 * @param javaElement
	 *            the Java element
	 * @see org.eclipse.jdt.internal.ui.text.java.IJavaReconcilingListener#aboutToBeReconciled()
	 */
	public void aboutToBeReconciled(final ISourceModule javaElement) {
		if (ASTUtils.DEBUG) {
			System.out.println(ASTUtils.getThreadName() + " - " + ASTUtils.DEBUG_PREFIX + "about to reconcile: " //$NON-NLS-1$ //$NON-NLS-2$
					+ toString(javaElement));
		}

		if (javaElement != null) {
			getCacheFor(javaElement).aboutToBeReconciled(javaElement);
		}
	}

	/*
	 * @see
	 * org.eclipse.jdt.internal.ui.text.java.IJavaReconcilingListener#reconciled
	 * (org.eclipse.jdt.core.dom.Program)
	 */
	public void reconciled(final Program ast, final ISourceModule javaElement, final IProgressMonitor progressMonitor) {
		if (ASTUtils.DEBUG) {
			System.out.println(
					ASTUtils.getThreadName() + " - " + ASTUtils.DEBUG_PREFIX + "reconciled: " + toString(javaElement) //$NON-NLS-1$ //$NON-NLS-2$
							+ ", AST: " + toString(ast)); //$NON-NLS-1$
		}

		if (javaElement != null) {
			getCacheFor(javaElement).reconciled(ast, javaElement, progressMonitor);
		}
	}

	/**
	 * Disposes the cached AST.
	 */
	private synchronized void disposeAST() {
		synchronized (cachedASTs) {
			cachedASTs.clear();
		}
	}

	/**
	 * Returns a string for the given Java element used for debugging.
	 * 
	 * @param javaElement
	 *            the compilation unit AST
	 * @return a string used for debugging
	 */
	private String toString(final ISourceModule javaElement) {
		if (javaElement == null) {
			return "null"; //$NON-NLS-1$
		} else {
			return javaElement.getElementName();
		}
	}

	/**
	 * Returns a string for the given AST used for debugging.
	 * 
	 * @param ast
	 *            the compilation unit AST
	 * @return a string used for debugging
	 */
	private String toString(final Program ast) {
		if (ast == null) {
			return "null"; //$NON-NLS-1$
		}
		// List types= ast.types();
		// if (types != null && types.size() > 0)
		// return
		// ((AbstractTypeDeclaration)types.get(0)).getName().getIdentifier();
		// else
		return "AST without any type"; //$NON-NLS-1$
	}

	/**
	 * Returns a shared compilation unit AST for the given Java element.
	 * <p>
	 * Clients are not allowed to modify the AST and must synchronize all access to
	 * its nodes.
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
	public Program getAST(final ISourceModule input, final WAIT_FLAG waitFlag, final IProgressMonitor progressMonitor) {
		if (input == null || waitFlag == null) {
			throw new IllegalArgumentException("input or wait flag are null"); //$NON-NLS-1$
		}

		if (progressMonitor != null && progressMonitor.isCanceled()) {
			return null;
		}

		boolean isActiveElement;
		synchronized (this) {
			isActiveElement = input == fActiveJavaElement;
		}

		Program ast = getCacheFor(input).getAST(input, SharedASTProvider.WAIT_NO, progressMonitor);

		if (ast != null || waitFlag == SharedASTProvider.WAIT_NO
				|| (waitFlag == SharedASTProvider.WAIT_ACTIVE_ONLY && !isActiveElement)) {
			if (progressMonitor != null && progressMonitor.isCanceled()) {
				// Should already be null, but let's be cautious
				assert ast == null;
				ast = null;
				if (ASTUtils.DEBUG) {
					System.out.println(
							ASTUtils.getThreadName() + " - " + ASTUtils.DEBUG_PREFIX + "Ignore created AST for: " //$NON-NLS-1$ //$NON-NLS-2$
									+ input.getElementName() + " - operation has been cancelled"); //$NON-NLS-1$
				}
			}

			return ast;
		}

		ast = getCacheFor(input).getAST(input, SharedASTProvider.WAIT_YES, progressMonitor);

		if (progressMonitor != null && progressMonitor.isCanceled()) {
			// Should already be null, but let's be cautious
			assert ast == null;
			ast = null;
			if (ASTUtils.DEBUG) {
				System.out.println(ASTUtils.getThreadName() + " - " + ASTUtils.DEBUG_PREFIX + "Ignore created AST for: " //$NON-NLS-1$ //$NON-NLS-2$
						+ input.getElementName() + " - operation has been cancelled"); //$NON-NLS-1$
			}
		}

		return ast;
	}

	/**
	 * Disposes this AST provider.
	 */
	public void dispose() {

		// Dispose activation listener
		PlatformUI.getWorkbench().removeWindowListener(fActivationListener);
		fActivationListener = null;

		disposeAST();
	}
}
