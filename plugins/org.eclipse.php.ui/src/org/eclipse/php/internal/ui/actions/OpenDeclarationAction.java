/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.ResourceBundle;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.texteditor.IUpdate;
import org.eclipse.wst.xml.core.internal.Logger;

public class OpenDeclarationAction extends PHPEditorResolvingAction implements
		IUpdate {

	public OpenDeclarationAction(ResourceBundle resourceBundle,
			PHPStructuredEditor editor) {
		super(resourceBundle, "OpenAction_declaration_", editor); //$NON-NLS-1$
	}

	protected void doRun(IModelElement modelElement) {
		try {
			OpenActionUtil.open(modelElement);
		} catch (PartInitException e) {
			Logger.logException(e);
		} catch (ModelException e) {
			Logger.logException(e);
		}
	}

	protected boolean isValid(IModelElement modelElement) {
		if (super.isValid(modelElement)) {
			return !LanguageModelInitializer
					.isLanguageModelElement(modelElement);
		}
		return false;
	}
}
