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
package org.eclipse.php.internal.ui.actions;

import java.util.ResourceBundle;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.PHPManualFactory;
import org.eclipse.ui.texteditor.IUpdate;

public class OpenFunctionsManualAction extends PHPEditorResolvingAction implements IUpdate {
	
	private String url;

	public OpenFunctionsManualAction(ResourceBundle resourceBundle, PHPStructuredEditor editor) {
		super(resourceBundle, "OpenFunctionsManualAction_", editor); //$NON-NLS-1$
	}

	protected void doRun(IModelElement modelElement) {
		if (url != null) {
			PHPManualFactory.getManual().showFunctionHelp(url);
		}
	}
	
	protected boolean isValid(IModelElement modelElement) {
		if (super.isValid(modelElement)) {
			if (LanguageModelInitializer.isLanguageModelElement(modelElement)) {
				url = PHPManualFactory.getManual().getURLForManual(modelElement);
				return url != null;
			}
		}
		return false;
	}
}
