/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.organizeIncludes;

import org.eclipse.core.resources.IFile;

class ElementDialogOpener implements Runnable {
	// private CodeData[] initialElements;

	private String elementName;

	// private CodeData result;

	private IFile file;

	// ElementDialogOpener(CodeData[] initialElements, String elementName, IFile
	// file) {
	// this.initialElements = initialElements;
	// this.elementName = elementName;
	// this.file = file;
	// }
	//
	// public CodeData getResult() {
	// return result;
	// }
	//
	@Override
	public void run() {
		// OpenPhpElementDialog dialog = new
		// OpenPhpElementDialog(Display.getDefault().getActiveShell(),
		// MessageFormat.format(PHPRefactoringCoreMessages.getString("ElementDialogOpener.Choose"),
		// new String[] { file.getFullPath().toString() })); //$NON-NLS-1$
		// dialog.setInitialElements(initialElements);
		// dialog.setInitFilterText(elementName);
		// int status = dialog.open();
		// if (Dialog.OK != status)
		// return;
		// result = dialog.getSelectedElement();
	}
}