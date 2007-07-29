package org.eclipse.php.internal.ui.actions;

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
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.TextEditorAction;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public abstract class PHPEditorResolvingAction extends TextEditorAction {

	private CodeData[] fCodeDatas;

	public PHPEditorResolvingAction(ResourceBundle bundle, String prefix, ITextEditor editor) {
		super(bundle, prefix, editor);
		setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		if (isValid()) {
			doRun();
		}
	}

	/**
	 * run the action
	 */
	abstract protected void doRun();

	/**
	 * @return text selection in the editor
	 */
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

	/**
	 * @return is action valid and can be run
	 */
	protected boolean isValid() {
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

		fCodeDatas = filterCodeDatas(codeDatas);

		if (fCodeDatas.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * @param codeDatas elements to filter
	 * @return array of filtered elements
	 */
	abstract protected CodeData[] filterCodeDatas(CodeData[] codeDatas);

	/**
	 * @return resolved elements
	 */
	protected CodeData[] getCodeDatas() {
		return fCodeDatas;
	}

}