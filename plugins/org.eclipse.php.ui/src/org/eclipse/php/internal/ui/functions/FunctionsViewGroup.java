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
package org.eclipse.php.internal.ui.functions;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.core.language.PHPVersion;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionGroup;

public class FunctionsViewGroup extends ActionGroup {

	public static final int PHP4 = 1;
	public static final int PHP5 = 2;

	private PHPFunctionsPart fPart;
	private ViewAction showPHP4FunctionsAction;
	private ViewAction showPHP5FunctionsAction;
	private int currentMode;

	public FunctionsViewGroup(PHPFunctionsPart part) {
		this.fPart = part;
		fillContextMenu(fPart.getViewSite().getActionBars().getMenuManager());
	}

	public void dispose() {
		super.dispose();
	}

	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		showPHP4FunctionsAction = new ViewAction(PHP4); //$NON-NLS-1$
		showPHP4FunctionsAction.setText("PHP 4"); //$NON-NLS-1$
		showPHP5FunctionsAction = new ViewAction(PHP5); //$NON-NLS-1$
		showPHP5FunctionsAction.setText("PHP 5"); //$NON-NLS-1$

		menu.add(showPHP4FunctionsAction);
		menu.add(showPHP5FunctionsAction);
	}

	public void setMode(final int mode) throws ModelException {
		if (mode == currentMode) {
			return;
		}
		
		fPart.getViewer().setInput(mode == PHP4 ? getLanguageModel() : getLanguageModel());
		currentMode = mode;
		updateActions();
	}

	private Object getLanguageModel() throws ModelException {
		final IEditorPart activeEditor = PHPUiPlugin.getActiveEditor();
		if (activeEditor == null) {
			return null;
		}
		
		final IModelElement activeElement = EditorUtility.getEditorInputPhpElement(activeEditor, false);
		if (activeElement == null) {
			return null;
		}
		final IScriptProject scriptProject = activeElement.getScriptProject();
		IPath languagePath = new Path(LanguageModelInitializer.CONTAINER_PATH);
		final IBuildpathContainer buildpathContainer = DLTKCore.getBuildpathContainer(languagePath, scriptProject);
		
		if (buildpathContainer == null) {
			return null;
		}
		
		final IBuildpathEntry[] buildpathEntries = buildpathContainer.getBuildpathEntries(scriptProject);
		return buildpathEntries;
	}

	private void updateActions() {
		if (currentMode == PHP4) {
			showPHP4FunctionsAction.setChecked(true);
			showPHP5FunctionsAction.setChecked(false);
		} else {
			showPHP5FunctionsAction.setChecked(true);
			showPHP4FunctionsAction.setChecked(false);
		}
	}

	public void handleUpdateInput(IEditorPart editorPart) throws ModelException {
		if (editorPart == null) {
			setMode(getVersion(PhpVersionProjectPropertyHandler.getVersion()));
			return;
		}

		final PHPStructuredEditor phpEditor = EditorUtility.getPHPStructuredEditor(editorPart);
		if (phpEditor != null) {
			IScriptProject project = phpEditor.getProject();
			if (project != null) {
				final String version = PhpVersionProjectPropertyHandler.getVersion(project.getProject());
				setMode(getVersion(version));
			}
		}
	}

	private int getVersion(String s) {
		if (PHPVersion.PHP4.equals(s)) {
			return PHP4;
		}

		return PHP5;
	}
	
	class ViewAction extends Action {

		private final int fMode;

		public ViewAction(int mode) {
			super("", AS_RADIO_BUTTON); //$NON-NLS-1$
			fMode= mode;
		}
		
		/**
		 * {@inheritDoc}
		 */
		public void run() {
			if (isChecked())
				try {
					setMode(fMode);
				} catch (ModelException e) {
					Logger.logException(e);
				}
		}
	}
}
