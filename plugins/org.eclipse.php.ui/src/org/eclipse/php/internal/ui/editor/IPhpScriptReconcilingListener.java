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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.internal.core.ast.nodes.Program;

/**
 * Interface of an object listening to Java reconciling.
 * 
 */
public interface IPhpScriptReconcilingListener {

	/**
	 * Called before reconciling is started.
	 */
	void aboutToBeReconciled();

	/**
	 * Called after reconciling has been finished.
	 * 
	 * @param ast
	 *            the Program AST or <code>null</code> if the working copy was
	 *            consistent or reconciliation has been cancelled
	 * @param forced
	 *            <code>true</code> iff this reconciliation was forced
	 * @param progressMonitor
	 *            the progress monitor
	 */
	void reconciled(Program program, boolean forced,
			IProgressMonitor progressMonitor);
}
