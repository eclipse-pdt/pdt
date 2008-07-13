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
package org.eclipse.php.internal.ui.editor.input;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IPath;
import org.eclipse.php.internal.core.ast.util.Util;
import org.eclipse.ui.internal.editors.text.NonExistingFileEditorInput;

/**
 * This Editor Input class should be used mainly for Untitled PHP Documents
 * @see org.eclipse.ui.internal.editors.text.NonExistingFileEditorInput
 */
//public class NonExistingPHPFileEditorInput implements IEditorInput, ILocationProvider {
public class NonExistingPHPFileEditorInput extends NonExistingFileEditorInput {

	public NonExistingPHPFileEditorInput(IFileStore fileStore, String namePrefix) {
		super(fileStore, namePrefix);
	}

	@Override
	public IPath getPath(Object element) {
		IPath path = super.getPath(element);
		return path.addFileExtension(Util.defaultPhpExtension());
	}

	@Override
	public String getName() {
		String result = super.getName();
		return result + "." + Util.defaultPhpExtension();
	}
}
