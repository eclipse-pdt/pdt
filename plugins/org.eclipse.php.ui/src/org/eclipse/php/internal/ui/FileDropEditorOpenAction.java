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
package org.eclipse.php.internal.ui;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.sse.ui.internal.FileDropAction;

public class FileDropEditorOpenAction extends FileDropAction {

	public boolean run(DropTargetEvent event, IEditorPart targetEditor) {

		if (!(targetEditor instanceof PHPStructuredEditor))
			return super.run(event, targetEditor);

		final String[] fileNames = (String[]) event.data;
		if (fileNames == null || fileNames.length == 0) {
			return false;
		}

		// default behavior
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				for (int i = 0; i < fileNames.length; ++i) {
					try {
						IFile file = ResourcesPlugin.getWorkspace().getRoot()
								.getFileForLocation(new Path(fileNames[i]));
						if (file != null) {
							org.eclipse.dltk.internal.ui.editor.EditorUtility
									.openInEditor(file, true);
						} else {
							if (new File(fileNames[i]).exists()) {
								EditorUtility.openLocalFile(fileNames[i], 0);
							}
						}
					} catch (PartInitException e) {
						Logger.logException(e);
					} catch (CoreException e) {
						Logger.logException(e);
					}
				}
			}
		});
		return true;
	}
}
