package org.eclipse.php.internal.ui.actions;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.util.CodeDataResolver;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.hover.PHPCodeHyperLink;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.IUpdate;
import org.eclipse.ui.texteditor.TextEditorAction;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class OpenDeclarationAction extends TextEditorAction implements IUpdate {

	/** (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#setEnabled(boolean)
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}

	private CodeData[] fCodeDatas;

	public OpenDeclarationAction(ResourceBundle resourceBundle, PHPStructuredEditor editor) {
		super(resourceBundle, "OpenAction_declaration_", editor);
		this.setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		if (!isEnabled()) {
			return;
		}
		if (validAction()) {
			PHPCodeHyperLink link = new PHPCodeHyperLink(null, fCodeDatas);
			link.open();
			return;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.texteditor.IUpdate#update()
	 */
	public void update() {
		super.update();

		if (validAction()) {
			setEnabled(true);
		} else {
			setEnabled(false);
		}
	}

	protected ITextSelection getCurrentSelection() {
		ITextEditor editor = getTextEditor();
		if (editor == null) {
			return null;
		}
		ISelectionProvider provider = editor.getSelectionProvider();
		if (provider == null) {
			return null;
		}
		ISelection selection = provider.getSelection();
		if (selection instanceof ITextSelection) {
			return (ITextSelection) selection;
		}

		return null;
	}

	private boolean validAction() {
		ITextEditor editor = getTextEditor();
		if (editor == null) {
			return false;
		}

		IDocumentProvider docProvider = editor.getDocumentProvider();
		IEditorInput input = editor.getEditorInput();
		if (docProvider == null || input == null) {
			return false;
		}

		IDocument document = docProvider.getDocument(input);
		if (document == null) {
			return false;
		}

		if (!(document instanceof IStructuredDocument)) {
			return false;
		}

		ITextSelection currentSelection = getCurrentSelection();
		int offset = currentSelection.getOffset();
		IStructuredDocument structuredDocument = (IStructuredDocument) document;
		String partitionType;
		try {
			partitionType = structuredDocument.getPartition(offset).getType();
		} catch (BadLocationException e1) {
			Logger.logException(e1);
			return false;
		}
		if (!partitionType.equals(PHPPartitionTypes.PHP_DEFAULT)) {
			return false;
		}

		IStructuredDocumentRegion structuredDocumentRegion = structuredDocument.getRegionAtCharacterOffset(offset);
		ITextRegion textRegion = structuredDocumentRegion.getRegionAtCharacterOffset(offset);
		if (textRegion == null) {
			return false;
		}

		if (textRegion.getType() == PHPRegionContext.PHP_CONTENT) {
			PhpScriptRegion phpScriptRegion = (PhpScriptRegion) textRegion;
			try {
				textRegion = phpScriptRegion.getPhpToken(offset - structuredDocumentRegion.getStartOffset() - phpScriptRegion.getStart());
			} catch (BadLocationException e) {
				textRegion = null;
			}
		}
		if (textRegion == null) {
			return false;
		}

		CodeData[] codeDatas = CodeDataResolver.getInstance().resolve(structuredDocument, offset);

		List userCodeData = new LinkedList();
		for (int i = 0; i < codeDatas.length; ++i) {
			if (codeDatas[i].isUserCode()) {
				userCodeData.add(codeDatas[i]);
			}
		}

		if (userCodeData.size() > 0) {
			fCodeDatas = (CodeData[]) userCodeData.toArray(new CodeData[userCodeData.size()]);
		}

		return true;
	}
}
