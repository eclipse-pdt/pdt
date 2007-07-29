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

	public OpenFunctionsManualAction(ResourceBundle resourceBundle, PHPStructuredEditor editor) {
		super(resourceBundle, "OpenFunctionsManualAction_", editor);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.actions.PHPEditorResolvingAction#doRun()
	 */
	@Override
	protected void doRun() {
		PHPManualFactory.getManual().showFunctionHelp((PHPCodeData) getCodeDatas()[0]); // XXX handle multiple elements
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.actions.PHPEditorResolvingAction#filterCodeDatas(org.eclipse.php.internal.core.phpModel.phpElementData.CodeData[])
	 */
	@Override
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
