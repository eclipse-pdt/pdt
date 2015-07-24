/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Janko Richter
 *******************************************************************************/
package org.eclipse.php.internal.ui.quickassist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.internal.ui.DLTKUIMessages;
import org.eclipse.dltk.internal.ui.dialogs.OpenTypeSelectionDialog2;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.*;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.nodes.Variable;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.PHPUILanguageToolkit;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.text.correction.IInvocationContext;
import org.eclipse.php.internal.ui.text.correction.IProblemLocation;
import org.eclipse.php.internal.ui.text.correction.IQuickAssistProcessor;
import org.eclipse.php.internal.ui.text.correction.proposals.AbstractCorrectionProposal;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;

public class VarCommentQuickAssistProcessor implements IQuickAssistProcessor {

	/**
	 * Proposal implementation
	 */
	private static class VarCommentCorrectionProposal
			extends AbstractCorrectionProposal {

		private static final String COMMAND_ID = "org.eclipse.php.ui.insertVarComment"; //$NON-NLS-1$
		private static final Pattern noneSpacePattern = Pattern.compile("\\S+"); //$NON-NLS-1$

		public VarCommentCorrectionProposal() {
			super(Messages.VarCommentQuickAssistProcessor_name, 0,
					DLTKPluginImages.get(DLTKPluginImages.IMG_CORRECTION_ADD),
					COMMAND_ID);
		}

		@Override
		public void apply(IDocument document) {
			PHPStructuredEditor editor = (PHPStructuredEditor) PHPUiPlugin
					.getActivePage().getActiveEditor();
			ISelection iTextSelection = editor.getSite().getSelectionProvider()
					.getSelection();
			if (!(iTextSelection instanceof ITextSelection)) {
				displayErrorDialog(editor);
				return;
			}

			IDocument doc = editor.getDocument();
			ITextSelection textSelection = (ITextSelection) iTextSelection;
			IRegion varRegion = null;
			if (editor.getModelElement() instanceof ISourceModule) {
				varRegion = getVariableRegion(textSelection,
						(ISourceModule) editor.getModelElement());
			}

			if (varRegion == null) {
				displayErrorDialog(editor);
				return;
			}

			TextEdit textEdit = null;
			SelectionDialog dialog = createTypeDialog();

			int result = dialog.open();
			if (result != IDialogConstants.OK_ID) {
				return;
			}

			Object[] types = dialog.getResult();
			if (types != null && types.length == 1
					&& types[0] instanceof IModelElement) {
				try {
					textEdit = createTextEditForType(doc, varRegion,
							(IModelElement) types[0]);
				} catch (BadLocationException e) {
				}
			}

			if (null != textEdit) {
				try {
					textEdit.apply(doc);
					// setCursorPosition(offset + textEdit.getLength());
					return;
				} catch (MalformedTreeException e) {
				} catch (BadLocationException e) {
				}
			}
		}

		/**
		 * Creates the variable comment for given type
		 */
		private TextEdit createTextEditForType(IDocument document,
				IRegion varRegion, IModelElement selectedType)
						throws BadLocationException {

			String typeName = selectedType.getElementName();
			IModelElement parent = selectedType.getParent();
			try {
				if (parent instanceof IMember && PHPFlags
						.isNamespace(((IMember) parent).getFlags())) {
					typeName = parent.getElementName() + '\\' + typeName;
				}
			} catch (ModelException e) {
				return null;
			}

			typeName = typeName.trim();
			if (typeName.length() > 0) {
				typeName = '\\' + typeName;
			}

			int varOffset = varRegion.getOffset();
			int varLength = varRegion.getLength();
			String varName = document.get(varOffset, varLength);

			// inserts type hint
			int selectionStartLine = document.getLineOfOffset(varOffset);
			int selectionLineOffset = document
					.getLineOffset(selectionStartLine);
			IRegion selectionLineInfo = document
					.getLineInformation(selectionStartLine);
			int selectionLineLength = selectionLineInfo.getLength();
			String selectionLineText = document.get(selectionLineOffset,
					selectionLineLength);

			Matcher nonSpaceMatch = noneSpacePattern.matcher(selectionLineText);
			String varTypeHint = ""; //$NON-NLS-1$
			if (nonSpaceMatch.find()) {
				varTypeHint = selectionLineText.substring(0,
						nonSpaceMatch.start());
			}

			varTypeHint += "/* @var "; //$NON-NLS-1$
			varTypeHint += typeName;
			varTypeHint += " "; //$NON-NLS-1$
			varTypeHint += varName;
			varTypeHint += " */"; //$NON-NLS-1$
			varTypeHint += TextUtilities.getDefaultLineDelimiter(document);

			return new InsertEdit(selectionLineOffset, varTypeHint);
		}

		/**
		 * Creates the dialog to select type
		 */
		private SelectionDialog createTypeDialog() {

			IDLTKUILanguageToolkit languageToolkit = PHPUILanguageToolkit
					.getInstance();
			String languageName = languageToolkit.getCoreToolkit()
					.getLanguageName();

			final Shell parent = DLTKUIPlugin.getActiveWorkbenchShell();
			OpenTypeSelectionDialog2 dialog = new OpenTypeSelectionDialog2(
					parent, true,
					PlatformUI.getWorkbench().getProgressService(), null,
					IDLTKSearchConstants.TYPE, languageToolkit);
			dialog.setTitle(NLS.bind(DLTKUIMessages.OpenTypeAction_dialogTitle,
					languageName));
			dialog.setMessage(DLTKUIMessages.OpenTypeAction_dialogMessage);
			dialog.setFilter(""); //$NON-NLS-1$

			return dialog;
		}

		/**
		 * Dialog, if comment insertion is not possible
		 */
		private void displayErrorDialog(IEditorPart editor) {
			MessageDialog.openError(editor.getSite().getShell(),
					Messages.VarCommentQuickAssistProcessor_Dialog_title,
					Messages.VarCommentQuickAssistProcessor_Dialog_error);
		}

		@Override
		public String getAdditionalProposalInfo() {
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
							textSelection.getOffset(),
							textSelection.getLength());
					ASTNode variableNode = VarCommentQuickAssistProcessor
							.getVariableNode(selectedNode);

					if (variableNode != null) {
						return new Region(variableNode.getStart(),
								variableNode.getLength());
					}
				}
			} catch (Exception e) {
			}

			return null;
		}

	}

	/**
	 * Returns the variable for selected node
	 * 
	 * @param selectedNode
	 * @return Variable
	 */
	private static ASTNode getVariableNode(ASTNode selectedNode) {

		// state machine to find PHP variable

		ASTNode variableNode = null;
		final int START = 1;
		final int PROCESS_PARENT = 2;
		final int PROCESS_EXPRESSION = 3;
		final int FINISH = 4;
		int state = START;

		while (selectedNode != null && state != FINISH) {

			switch (selectedNode.getType()) {
			case ASTNode.VARIABLE:
				if (((Variable) selectedNode).isDollared()) {
					// variable found!
					state = FINISH;
					variableNode = selectedNode;
				} else if (PROCESS_EXPRESSION == state) {
					// expression doesn't start with a variable
					state = FINISH;
				} else {
					state = PROCESS_PARENT;
					selectedNode = selectedNode.getParent();
				}
				break;

			case ASTNode.EXPRESSION_STATEMENT:
				if (PROCESS_EXPRESSION == state) {
					// prevent endless loop
					state = FINISH;
				} else {
					// does expression start with a variable?
					selectedNode = NodeFinder.perform(selectedNode,
							selectedNode.getStart(), 0);
					state = PROCESS_EXPRESSION;
				}
				break;

			case ASTNode.FIELD_ACCESS:
			case ASTNode.IDENTIFIER:
			case ASTNode.FUNCTION_NAME:
			case ASTNode.FUNCTION_INVOCATION:
			case ASTNode.METHOD_INVOCATION:
				if (PROCESS_EXPRESSION == state) {
					// expression doesn't start with a variable
					state = FINISH;
				} else {
					state = PROCESS_PARENT;
					selectedNode = selectedNode.getParent();
				}
				break;

			default:
				// not expected node; stop here
				state = FINISH;
			}
		}

		return variableNode;
	}

	/**
	 * @see IQuickAssistProcessor#getAssists(IInvocationContext,
	 *      IProblemLocation[])
	 */
	@Override
	public IScriptCompletionProposal[] getAssists(IInvocationContext context,
			IProblemLocation[] locations) throws CoreException {
		if (hasAssists(context)) {
			return new IScriptCompletionProposal[] {
					new VarCommentCorrectionProposal() };
		}
		return null;
	}

	/**
	 * @see IQuickAssistProcessor#hasAssists(IInvocationContext)
	 */
	@Override
	public boolean hasAssists(IInvocationContext context) throws CoreException {
		return (null != getVariableNode(context.getCoveringNode()));
	}

}
