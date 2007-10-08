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

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.PHPManualFactory;
import org.eclipse.ui.texteditor.IUpdate;

public class OpenFunctionsManualAction extends PHPEditorResolvingAction implements IUpdate {
	
	private String url;

	public OpenFunctionsManualAction(ResourceBundle resourceBundle, PHPStructuredEditor editor) {
		super(resourceBundle, "OpenFunctionsManualAction_", editor);
	}

	protected void doRun() {
		if (url != null) {
			PHPManualFactory.getManual().showFunctionHelp(url); // XXX handle multiple elements
		}
	}
	
	protected boolean isValid() {
		if (super.isValid()) {
			url = PHPManualFactory.getManual().getURLForManual((PHPCodeData) getCodeDatas()[0]);
			return url != null;
		}
		return false;
	}
	
	public void update() {
		setEnabled (getTextEditor() != null && isValid());
	}

	protected CodeData[] filterCodeDatas(CodeData[] codeDatas) {
		// only show help for built-in elements
		List<CodeData> nonUserCodeData = new LinkedList();
		for (CodeData codeData : codeDatas) {
			if (!codeData.isUserCode() && codeData.getUserData() == null) {
				nonUserCodeData.add(codeData);
			}
		}
		return nonUserCodeData.toArray(new CodeData[nonUserCodeData.size()]);
	}

}
