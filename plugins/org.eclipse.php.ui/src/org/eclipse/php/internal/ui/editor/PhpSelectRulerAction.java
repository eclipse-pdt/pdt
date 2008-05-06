/**
 * 
 */
package org.eclipse.php.internal.ui.editor;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.texteditor.AbstractRulerActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * PHP select ruler action that generates a PhpSelectAnnotationRulerAction for the annotations selection.
 * 
 * @author Shalom
 */
public class PhpSelectRulerAction extends AbstractRulerActionDelegate {

	protected IAction createAction(ITextEditor editor, IVerticalRulerInfo rulerInfo) {
		return new PhpSelectAnnotationRulerAction(PHPUIMessages.getResourceBundle(), "PhpSelectAnnotationRulerAction.", editor, rulerInfo); //$NON-NLS-1$
	}
}
