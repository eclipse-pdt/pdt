package org.eclipse.php.internal.ui.actions;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.hover.PHPCodeHyperLink;
import org.eclipse.ui.texteditor.IUpdate;

public class OpenDeclarationAction extends PHPEditorResolvingAction implements IUpdate {

	public OpenDeclarationAction(ResourceBundle resourceBundle, PHPStructuredEditor editor) {
		super(resourceBundle, "OpenAction_declaration_", editor); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.actions.PHPEditorResolvingAction#doRun()
	 */
	@Override
	protected void doRun() {
		PHPCodeHyperLink link = new PHPCodeHyperLink(null, getCodeDatas());
		link.open();
	}

	public void update() {
		setEnabled (getTextEditor() != null && isValid());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.actions.PHPEditorResolvingAction#filterCodeDatas(org.eclipse.php.internal.core.phpModel.phpElementData.CodeData[])
	 */
	@Override
	protected CodeData[] filterCodeDatas(CodeData[] codeDatas) {
		// only operate on user data (resources, include paths and external files; no language model etc.)
		List<CodeData> userCodeData = new LinkedList();
		for (CodeData codeData : codeDatas) {
			if (codeData.isUserCode()) {
				userCodeData.add(codeData);
			}
		}
		return userCodeData.toArray(new CodeData[userCodeData.size()]);
	}
}
