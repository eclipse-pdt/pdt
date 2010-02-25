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
package org.eclipse.php.internal.ui.editor.hover;

import org.eclipse.core.resources.IStorage;
import org.eclipse.dltk.core.ICodeAssist;
import org.eclipse.dltk.internal.core.ExternalSourceModule;
import org.eclipse.dltk.internal.ui.editor.ExternalStorageEditorInput;
import org.eclipse.dltk.internal.ui.text.hover.DocumentationHover;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.IWorkingCopyManager;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

public class PHPDocumentationHover extends DocumentationHover implements
		IPHPTextHover {

	public IHoverMessageDecorator getMessageDecorator() {
		return null;
	}

	protected ICodeAssist getCodeAssist() {
		IEditorPart editor = getEditor();
		if (editor != null) {
			IEditorInput input = editor.getEditorInput();

			if (input instanceof ExternalStorageEditorInput) {
				ExternalStorageEditorInput external = (ExternalStorageEditorInput) input;
				IStorage storage = external.getStorage();
				if (storage != null) {
					if (storage instanceof ExternalSourceModule) {
						ExternalSourceModule externalSourceModule = (ExternalSourceModule) storage;
						return externalSourceModule;
					}
				}
			}

			IWorkingCopyManager manager = DLTKUIPlugin.getDefault()
					.getWorkingCopyManager();
			return manager.getWorkingCopy(input, false);
		}

		return null;
	}
}
