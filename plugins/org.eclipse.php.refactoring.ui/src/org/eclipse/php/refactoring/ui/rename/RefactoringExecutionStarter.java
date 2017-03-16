/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.rename;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.swt.widgets.Shell;

/**
 * Starts the process of refactoring rename
 * 
 * @author Roy, 2007
 * @inspiredby JDT
 */
public class RefactoringExecutionStarter {

	/**
	 * Not for instantiation
	 */
	private RefactoringExecutionStarter() {
	}

	public static void startRenameRefactoring(IResource resource, ASTNode locateNode, final Shell shell)
			throws CoreException {
		final RenameSupport support = createRenameSupport(resource, locateNode, null, RenameSupport.UPDATE_REFERENCES);
		if (support != null && support.preCheck().isOK())
			support.openDialog(shell);
		else {
			MessageDialog.openInformation(shell, Messages.RefactoringExecutionStarter_0,
					Messages.RefactoringExecutionStarter_1);
		}
	}

	private static RenameSupport createRenameSupport(IResource resource, ASTNode locateNode, String newName, int flags)
			throws CoreException {
		final int elementType = PhpElementConciliator.concile(locateNode);
		return RenameSupport.create(resource, elementType, locateNode, newName, flags);
	}

}
