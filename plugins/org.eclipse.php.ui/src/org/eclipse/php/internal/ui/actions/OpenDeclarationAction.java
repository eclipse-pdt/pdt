package org.eclipse.php.internal.ui.actions;

import java.util.ResourceBundle;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.internal.core.phpModel.LanguageModelInitializer;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.texteditor.IUpdate;
import org.eclipse.wst.xml.core.internal.Logger;

public class OpenDeclarationAction extends PHPEditorResolvingAction implements IUpdate {

	public OpenDeclarationAction(ResourceBundle resourceBundle, PHPStructuredEditor editor) {
		super(resourceBundle, "OpenAction_declaration_", editor); //$NON-NLS-1$
	}

	protected void doRun(IModelElement modelElement) {
		try {
			OpenActionUtil.open(modelElement);
		} catch (PartInitException e) {
			Logger.logException(e);
		}
	}

	protected boolean isValid(IModelElement modelElement) {
		if (super.isValid(modelElement)) {
			return !LanguageModelInitializer.isLanguageModelElement(modelElement);
		}
		return false;
	}
}
