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
package org.eclipse.php.ui.editor.hover;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.Logger;
import org.eclipse.php.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.util.EditorUtility;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

public class PHPCodeHyperLink implements IHyperlink {

	private IRegion fRegion;
	private CodeData codeData;
	
	public PHPCodeHyperLink(IRegion region, CodeData codeData) {
		fRegion = region;
		this.codeData = codeData;
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
		IEditorPart part = EditorUtility.isOpenInEditor(codeData);
		if (part != null) {
			IWorkbenchPage page = PHPUiPlugin.getActivePage();
			if (page != null) {
				page.bringToTop (part);
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
