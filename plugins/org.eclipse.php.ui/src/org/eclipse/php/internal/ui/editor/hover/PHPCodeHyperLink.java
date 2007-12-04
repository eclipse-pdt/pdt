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
package org.eclipse.php.internal.ui.editor.hover;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.dialogs.openType.OpenPhpElementDialog;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

public class PHPCodeHyperLink implements IHyperlink {

	private IRegion fRegion;
	private CodeData[] codeDatas;

	public PHPCodeHyperLink(IRegion region, CodeData[] codeDatas) {
		fRegion = region;
		this.codeDatas = codeDatas;
	}

	public IRegion getHyperlinkRegion() {
		return fRegion;
	}

	public String getHyperlinkText() {
		return null;
	}

	public String getTypeLabel() {
		return null;
	}

	public void open() {
		if (codeDatas == null) {
			return;
		}

		CodeData codeData = null;
		CodeData[] initialElements = codeDatas;
		String initialText = codeDatas[0].getName();
		if (codeDatas.length > 1) {
			OpenPhpElementDialog dialog = new OpenPhpElementDialog(Display.getDefault().getActiveShell());
			dialog.getFilter().setSelectClasses(false);
			dialog.getFilter().setSelectFunctions(false);
			dialog.getFilter().setSelectConstants(false);
			if (codeDatas[0] instanceof PHPClassData) {
				dialog.getFilter().setSelectClasses(true);
			} else if (codeDatas[0] instanceof PHPConstantData) {
				dialog.getFilter().setSelectConstants(true);
			} else if (codeDatas[0] instanceof PHPFunctionData) {
				dialog.getFilter().setSelectFunctions(true);
			} else if (codeDatas[0] instanceof PHPClassVarData) {
				codeData = codeDatas[0];
			}
			if (codeData == null) {
				dialog.setInitialElements(codeDatas);
				dialog.setInitFilterText(codeDatas[0].getName());
				if (dialog.open() == Dialog.CANCEL) {
					return;
				}
				codeData = dialog.getSelectedElement();
			}
		} else {
			codeData = codeDatas[0];
		}

		IEditorPart part = EditorUtility.isOpenInEditor(codeData);
		if (part != null) {
			IWorkbenchPage page = PHPUiPlugin.getActivePage();
			if (page != null) {
				page.bringToTop(part);
			}
			EditorUtility.revealInEditor(part, (PHPCodeData) codeData);
		} else {
			try {
				part = EditorUtility.openInEditor(codeData);
				EditorUtility.revealInEditor(part, (PHPCodeData) codeData);
			} catch (PartInitException e) {
				Logger.logException(e);
			}
		}
	}
}
