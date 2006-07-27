/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.ResourceBundle;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.phpModel.phpElementData.PHPTask;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.MarkerRulerAction;
import org.eclipse.ui.texteditor.TaskRulerAction;
import org.eclipse.ui.views.tasklist.TaskPropertiesDialog;

/**
 * Adapter for the marker ruler action creating/removing PHP tasks.
 * 
 */
public class PHPTaskRulerAction extends TaskRulerAction {

	/**
	 * Adds a task marker over the ruler context menu. Uses the Workbench's Task properties dialog.
	 */
	static class TaskMarkerRulerAction extends MarkerRulerAction {

		/**
		 * Creates a new action for the given ruler and editor. The action configures
		 * its visual representation from the given resource bundle.
		 *
		 * @param bundle the resource bundle
		 * @param prefix a prefix to be prepended to the various resource keys
		 *   (described in <code>ResourceAction</code> constructor), or
		 *   <code>null</code> if none
		 * @param editor the text editor
		 * @param ruler the vertical ruler info
		 * @see MarkerRulerAction#MarkerRulerAction(ResourceBundle, String, ITextEditor, IVerticalRulerInfo, String, boolean)
		 */
		public TaskMarkerRulerAction(ResourceBundle bundle, String prefix, ITextEditor editor, IVerticalRulerInfo ruler) {
			super(bundle, prefix, editor, ruler, IMarker.TASK, false);
		}

		/*
		 * @see MarkerRulerAction#addMarker()
		 */
		protected void addMarker() {
			IResource resource = getResource();
			if (resource == null)
				return;

			TaskPropertiesDialog dialog = new TaskPropertiesDialog(getTextEditor().getSite().getShell());
			dialog.setResource(resource);
			dialog.setInitialAttributes(getInitialAttributes());
			dialog.open();
			IMarker marker = dialog.getMarker();
			// Add an attribute that will be read in the validation process. 
			// Fix bug #95 (See also PHPProblemsValidator)
			try {
				marker.setAttribute(PHPTask.RULER_PHP_TASK, true);
			} catch (CoreException e) {
			}
		}
	}

	/*
	 * @see AbstractRulerActionDelegate#createAction(ITextEditor, IVerticalRulerInfo)
	 */
	protected IAction createAction(ITextEditor editor, IVerticalRulerInfo rulerInfo) {
		return new TaskMarkerRulerAction(PHPUIMessages.getBundleForConstructedKeys(), "Editor.ManageTasks.", editor, rulerInfo); //$NON-NLS-1$
	}
}
