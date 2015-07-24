package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.*;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.quickassist.VarCommentQuickAssistProcessor.VarCommentBuilder;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class InsertVarCommentAction extends AbstractHandler {

	/**
	 * Dialog, if comment insertion is not possible
	 */
	private void displayErrorDialog(IEditorPart editor) {
		MessageDialog.openError(editor.getSite().getShell(),
				Messages.InsertVarComment_Dialog_title,
				Messages.InsertVarComment_Dialog_error);
	}

	/**
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = (IEditorPart) HandlerUtil
				.getActiveWorkbenchWindow(event).getActivePage()
				.getActiveEditor();

		ISelection iTextSelection = editor.getSite().getSelectionProvider()
				.getSelection();
		if (!(iTextSelection instanceof ITextSelection)) {
			displayErrorDialog(editor);
			return null;
		}

		if (!(editor instanceof PHPStructuredEditor)) {
			displayErrorDialog(editor);
			return null;
		}

		ITextSelection textSelection = (ITextSelection) iTextSelection;
		PHPStructuredEditor phpEditor = (PHPStructuredEditor) editor;
		IRegion varRegion = null;
		if (phpEditor.getModelElement() instanceof ISourceModule) {
			varRegion = getVariableRegion(textSelection,
					(ISourceModule) phpEditor.getModelElement());
		}

		if (varRegion == null) {
			displayErrorDialog(editor);
			return null;
		}

		IDocument doc = phpEditor.getDocument();
		VarCommentBuilder varCommentBuilder = new VarCommentBuilder(doc,
				varRegion);
		TextEdit textEdit = varCommentBuilder.createTextEdit();
		try {
			textEdit.apply(doc);
		} catch (MalformedTreeException e) {
		} catch (BadLocationException e) {
		}

		return null;
	}

	/**
	 * Returns the region of a selected variable
	 * 
	 * @param textSelection
	 * @param sourceModule
	 * @return Region, if selection is a variable; otherwise null.
	 */
	private IRegion getVariableRegion(ITextSelection textSelection,
			ISourceModule sourceModule) {

		try {
			Program ast = SharedASTProvider.getAST(sourceModule,
					SharedASTProvider.WAIT_YES, null);
			if (ast != null) {
				ASTNode selectedNode = NodeFinder.perform(ast,
						textSelection.getOffset(), textSelection.getLength());

				if (selectedNode != null
						&& selectedNode.getType() == ASTNode.VARIABLE) {
					return new Region(selectedNode.getStart(),
							selectedNode.getLength());
				}
			}
		} catch (Exception e) {
		}

		return null;
	}

}
