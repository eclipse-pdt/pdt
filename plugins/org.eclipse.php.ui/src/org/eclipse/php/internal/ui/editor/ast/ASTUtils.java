/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.ast;

import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.core.corext.ASTNodes;
import org.eclipse.php.internal.ui.PHPUiPlugin;

public class ASTUtils {
	public static final boolean DEBUG = "true" //$NON-NLS-1$
			.equalsIgnoreCase(Platform.getDebugOption("org.eclipse.jdt.ui/debug/ASTProvider")); //$NON-NLS-1$

	public static final String DEBUG_PREFIX = "ASTProvider > "; //$NON-NLS-1$

	/**
	 * Creates a new compilation unit AST.
	 * 
	 * @param input
	 *            the Java element for which to create the AST
	 * @param progressMonitor
	 *            the progress monitor
	 * @return AST
	 */
	public static Program createAST(final ISourceModule input, final IProgressMonitor progressMonitor) {
		if (!hasSource(input)) {
			return null;
		}

		if (progressMonitor != null && progressMonitor.isCanceled()) {
			return null;
		}

		final ASTParser parser = ASTParser.newParser(input);
		if (parser == null) {
			return null;
		}

		if (progressMonitor != null && progressMonitor.isCanceled()) {
			return null;
		}

		final Program root[] = new Program[1];

		SafeRunner.run(new ISafeRunnable() {
			@Override
			public void run() {
				try {
					if (progressMonitor != null && progressMonitor.isCanceled()) {
						return;
					}
					if (DEBUG) {
						System.err.println(
								getThreadName() + " - " + DEBUG_PREFIX + "creating AST for: " + input.getElementName()); //$NON-NLS-1$ //$NON-NLS-2$
					}
					root[0] = parser.createAST(progressMonitor);

					// mark as unmodifiable
					ASTNodes.setFlagsToAST(root[0], ASTNode.PROTECT);
				} catch (OperationCanceledException ex) {
					return;
				} catch (Exception e) {
					PHPUiPlugin.log(e);
					return;
				}
			}

			@Override
			public void handleException(Throwable ex) {
				IStatus status = new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.OK,
						"Error in PDT UI during AST creation", ex); //$NON-NLS-1$
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
	 */
	private static boolean hasSource(ISourceModule je) {
		if (je == null || !je.exists()) {
			return false;
		}

		return true;
		// try {
		// return je.getBuffer() != null;
		// } catch (ModelException ex) {
		// IStatus status= new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.OK,
		// "Error in PDT UI during AST creation", ex); //$NON-NLS-1$
		// PHPUiPlugin.log(status);
		// }
		// return false;
	}

	public static String getThreadName() {
		String name = Thread.currentThread().getName();
		if (name != null) {
			return name;
		} else {
			return Thread.currentThread().toString();
		}
	}
}
