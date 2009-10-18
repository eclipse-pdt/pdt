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
package org.eclipse.php.ui.editor;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.ast.nodes.AST;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.wst.jsdt.core.ITypeRoot;

/**
 * The {@link SharedASTProvider} provides access to the {@link CompilationUnit
 * AST root} used by the current active php editor.
 * 
 * <p>
 * For performance reasons, not more than one AST should be kept in memory at a
 * time. Therefore, clients must not keep any references to the shared AST or
 * its nodes or bindings.
 * </p>
 * <p>
 * Clients can make the following assumptions about the AST:
 * <dl>
 * <li>the AST has a {@link ITypeRoot} as source:
 * {@link CompilationUnit#getTypeRoot()} is not null.</li>
 * <li>the {@link AST#apiLevel() AST API level} is {@link AST#JLS3 API level 3}
 * or higher</li>
 * <li>the AST has bindings resolved ({@link AST#hasResolvedBindings()})</li>
 * <li>{@link AST#hasStatementsRecovery() statement} and
 * {@link AST#hasBindingsRecovery() bindings} recovery are enabled</li>
 * </dl>
 * It is possible that in the future a higher API level is used, or that future
 * options will be enabled.
 * </p>
 * <p>
 * The returned AST is shared. It is marked as {@link ASTNode#PROTECT} and must
 * not be modified. Clients are advised to use the non-modifying
 * {@link ASTRewrite} to get update scripts.
 * </p>
 * 
 * <p>
 * This class is not intended to be subclassed or instantiated by clients.
 * </p>
 * 
 * @since 3.4
 */
public final class SharedASTProvider {

	/**
	 * Wait flag class.
	 */
	public static final class WAIT_FLAG {

		private String fName;

		private WAIT_FLAG(String name) {
			fName = name;
		}

		/*
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return fName;
		}
	}

	/**
	 * Wait flag indicating that a client requesting an AST wants to wait until
	 * an AST is ready.
	 * <p>
	 * An AST will be created by this AST provider if the shared AST is not for
	 * the given php element.
	 * </p>
	 */
	public static final WAIT_FLAG WAIT_YES = new WAIT_FLAG("wait yes"); //$NON-NLS-1$

	/**
	 * Wait flag indicating that a client requesting an AST only wants to wait
	 * for the shared AST of the active editor.
	 * <p>
	 * No AST will be created by the AST provider.
	 * </p>
	 */
	public static final WAIT_FLAG WAIT_ACTIVE_ONLY = new WAIT_FLAG(
			"wait active only"); //$NON-NLS-1$

	/**
	 * Wait flag indicating that a client requesting an AST only wants the
	 * already available shared AST.
	 * <p>
	 * No AST will be created by the AST provider.
	 * </p>
	 */
	public static final WAIT_FLAG WAIT_NO = new WAIT_FLAG("don't wait"); //$NON-NLS-1$

	/**
	 * Returns a compilation unit AST for the given php element. If the element
	 * is the input of the active php editor, the AST is the shared AST.
	 * <p>
	 * Clients are not allowed to modify the AST and must not keep any
	 * references.
	 * </p>
	 * 
	 * @param element
	 *            the {@link ITypeRoot}, must not be <code>null</code>
	 * @param waitFlag
	 *            {@link #WAIT_YES}, {@link #WAIT_NO} or
	 *            {@link #WAIT_ACTIVE_ONLY}
	 * @param progressMonitor
	 *            the progress monitor or <code>null</code>
	 * @return the AST or <code>null</code>.
	 *         <dl>
	 *         <li>if {@link #WAIT_NO} has been specified <code>null</code> is
	 *         returned if the element is not input of the current php editor or
	 *         no AST is available</li>
	 *         <li>if {@link #WAIT_ACTIVE_ONLY} has been specified
	 *         <code>null</code> is returned if the element is not input of the
	 *         current php editor</li>
	 *         <li>if {@link #WAIT_YES} has been specified either the shared AST
	 *         is returned or a new AST is created.</li>
	 *         </dl>
	 * @throws IOException
	 * @throws ModelException
	 */
	public static Program getAST(ISourceModule element, WAIT_FLAG waitFlag,
			IProgressMonitor progressMonitor) throws ModelException,
			IOException {
		return PHPUiPlugin.getDefault().getASTProvider().getAST(element,
				waitFlag, progressMonitor);
	}

	private SharedASTProvider() {
		// Prevent instantiation.
	}

}
