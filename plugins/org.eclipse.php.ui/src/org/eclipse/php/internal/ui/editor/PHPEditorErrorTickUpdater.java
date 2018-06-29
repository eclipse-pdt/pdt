/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.ProblemsLabelDecorator;
import org.eclipse.dltk.ui.ScriptElementImageProvider;
import org.eclipse.dltk.ui.viewsupport.IProblemChangedListener;
import org.eclipse.dltk.ui.viewsupport.ScriptUILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;

/**
 * The <code>PHPEditorErrorTickUpdater</code> will register as a
 * IProblemChangedListener to listen on problem changes of the editor's input.
 * It updates the title images when the annotation model changed.
 */
public class PHPEditorErrorTickUpdater implements IProblemChangedListener {

	private PHPStructuredEditor fPHPEditor;
	private ScriptUILabelProvider fLabelProvider;

	public PHPEditorErrorTickUpdater(PHPStructuredEditor editor) {
		Assert.isNotNull(editor);
		fPHPEditor = editor;
		fLabelProvider = new ScriptUILabelProvider(0, ScriptElementImageProvider.SMALL_ICONS);
		fLabelProvider.addLabelDecorator(new ProblemsLabelDecorator(null));
		DLTKUIPlugin.getDefault().getProblemMarkerManager().addListener(this);
	}

	@Override
	public void problemsChanged(IResource[] changedResources, boolean isMarkerChange) {
		if (!isMarkerChange) {
			return;
		}

		IEditorInput input = fPHPEditor.getEditorInput();
		if (input != null) {
			IModelElement element = input.getAdapter(IModelElement.class);
			if (element != null) {
				IResource resource = element.getResource();
				for (int i = 0; i < changedResources.length; i++) {
					if (changedResources[i].equals(resource)) {
						updateEditorImage(element);
					}
				}
			}
		}
	}

	public void updateEditorImage(IModelElement element) {
		Image titleImage = fPHPEditor.getTitleImage();
		if (titleImage == null) {
			return;
		}
		Image newImage;
		if (element instanceof ISourceModule && !element.getScriptProject().isOnBuildpath(element)) {
			newImage = fLabelProvider.getImage(element.getResource());
		} else {
			newImage = fLabelProvider.getImage(element);
		}
		if (titleImage != newImage) {
			postImageChange(newImage);
		}
	}

	private void postImageChange(final Image newImage) {
		Shell shell = fPHPEditor.getEditorSite().getShell();
		if (shell != null && !shell.isDisposed()) {
			shell.getDisplay().syncExec(() -> fPHPEditor.updatedTitleImage(newImage));
		}
	}

	public void dispose() {
		fLabelProvider.dispose();
		DLTKUIPlugin.getDefault().getProblemMarkerManager().removeListener(this);
	}

}
