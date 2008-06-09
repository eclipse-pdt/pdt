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
package org.eclipse.php.internal.ui.functions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.php.internal.core.phpModel.parser.IPhpModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPLanguageManagerProvider;
import org.eclipse.php.internal.core.phpModel.parser.PHPVersion;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionGroup;

public class FunctionsViewGroup extends ActionGroup {

	public static final int PHP4 = 1;
	public static final int PHP5 = 2;

	public static final IPhpModel php4Model = PHPLanguageManagerProvider.instance().getPHPLanguageManager(PHPVersion.PHP4).getModel();
	public static final IPhpModel php5Model = PHPLanguageManagerProvider.instance().getPHPLanguageManager(PHPVersion.PHP5).getModel();

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

	public void setMode(final int mode) {
		if (mode == currentMode) {
			return;
		}
		fPart.getViewer().setInput(mode == PHP4 ? php4Model : php5Model);
		currentMode = mode;
		updateActions();
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

	public void handleUpdateInput(IEditorPart editorPart) {
		if (editorPart == null) {
			setMode(getVersion(PhpVersionProjectPropertyHandler.getVersion()));
			return;
		}

		final PHPStructuredEditor phpEditor = EditorUtility.getPHPStructuredEditor(editorPart);
		if (phpEditor != null) {
			final IFile file = phpEditor.getFile();
			if(file == null)
				return;
			final String version = PhpVersionProjectPropertyHandler.getVersion(file.getProject());
			setMode(getVersion(version));
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
				setMode(fMode);
		}
	}
}
