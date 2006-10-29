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
package org.eclipse.php.ui.workingset;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.php.core.phpModel.parser.IPhpModel;
import org.eclipse.php.core.phpModel.parser.PHPLanguageManagerProvider;
import org.eclipse.php.core.phpModel.parser.PHPVersion;
import org.eclipse.php.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.functions.PHPFunctionsPart;
import org.eclipse.php.ui.util.EditorUtility;
import org.eclipse.ui.IEditorPart;

public class FunctionsViewGroup extends ViewActionGroup {

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
		showPHP4FunctionsAction = new ViewAction(this, PHP4); //$NON-NLS-1$
		showPHP4FunctionsAction.setText("PHP 4");
		showPHP5FunctionsAction = new ViewAction(this, PHP5); //$NON-NLS-1$
		showPHP5FunctionsAction.setText("PHP 5");

		menu.add(showPHP4FunctionsAction);
		menu.add(showPHP5FunctionsAction);
	}

	public void setMode(final int mode) {
		if (mode == currentMode) {
			return;
		}
		//		BusyIndicator.showWhile(fPart.getSite().getShell().getDisplay(), new Runnable() {
		//
		//			public void run() {
		//				((PHPFunctionsContentProvider) fPart.getViewer().getContentProvider()).setRoot(PHPLanguageManagerProvider.instance().getPHPLanguageManager(currentMode == PHP4 ? PHPVersion.PHP4 : PHPVersion.PHP5).getModel());
		IPhpModel model = PHPLanguageManagerProvider.instance().getPHPLanguageManager(currentMode == PHP4 ? PHPVersion.PHP4 : PHPVersion.PHP5).getModel();
		fPart.getViewer().setInput(model);
		//				fPart.getViewer().refresh();
		//				fPart.getViewer().getControl().redraw();
		//			}
		//			
		//		});
		currentMode = mode;
		//		if (updateViewJob == null) {
		//			updateViewJob = new UpdateViewJob();
		//		}
		//		updateViewJob.schedule();
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
}
