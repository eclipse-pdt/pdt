/*******************************************************************************
 * Copyright (c) 2016 Janko Richter and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Janko Richter - initial API and implementation
 *     Kaloyan Raev - Bug 485550 - Improve insert variable comment quick assist
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
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.ui.dialogs.OpenTypeSelectionDialog2;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Variable;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.PHPUILanguageToolkit;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.text.correction.proposals.AbstractCorrectionProposal;
import org.eclipse.php.ui.text.correction.IInvocationContext;
import org.eclipse.php.ui.text.correction.IProblemLocation;
import org.eclipse.php.ui.text.correction.IQuickAssistProcessor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;

public class VarCommentQuickAssistProcessor implements IQuickAssistProcessor {

	/**
	 * Proposal implementation
	 */
	private static class VarCommentCorrectionProposal extends AbstractCorrectionProposal {

		private static final String COMMAND_ID = "org.eclipse.php.ui.insertVarComment"; //$NON-NLS-1$
		private static final Pattern noneSpacePattern = Pattern.compile("[^\\p{javaWhitespace}]+"); //$NON-NLS-1$
		private ASTNode variableNode;
		private ISourceModule sourceModule;

		public VarCommentCorrectionProposal(ASTNode variableNode, ISourceModule sourceModule) {
			super(Messages.VarCommentQuickAssistProcessor_name, 0,
					DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_ANNOTATION), COMMAND_ID);
			this.variableNode = variableNode;
			this.sourceModule = sourceModule;
		}

		@Override
		public void apply(IDocument document) {
			try {
				SelectionDialog dialog = createTypeDialog(document);
				int result = dialog.open();
				if (result != IDialogConstants.OK_ID) {
					return;
				}

				TextEdit textEdit = null;
				Object[] types = dialog.getResult();
				if (types != null && types.length == 1 && types[0] instanceof IModelElement) {
					textEdit = createTextEditForType(document, (IModelElement) types[0]);
				}

				if (null != textEdit) {
					textEdit.apply(document);
				}
			} catch (BadLocationException | ModelException | MalformedTreeException e) {
				PHPUiPlugin.log(e);
			}
		}

		/**
		 * Creates the variable comment for given type
		 */
		private TextEdit createTextEditForType(IDocument document, IModelElement selectedType)
				throws BadLocationException, ModelException {

			String typeName = selectedType.getElementName();
			IModelElement parent = selectedType.getParent();
			if (parent instanceof IMember && PHPFlags.isNamespace(((IMember) parent).getFlags())) {
				typeName = parent.getElementName() + '\\' + typeName;
			}

			typeName = typeName.trim();
			if (typeName.length() > 0) {
				typeName = '\\' + typeName;
			}

			int varOffset = variableNode.getStart();
			int varLength = variableNode.getLength();
			String varName = document.get(varOffset, varLength);

			// inserts type hint
			int selectionStartLine = document.getLineOfOffset(varOffset);
			int selectionLineOffset = document.getLineOffset(selectionStartLine);
			IRegion selectionLineInfo = document.getLineInformation(selectionStartLine);
			int selectionLineLength = selectionLineInfo.getLength();
			String selectionLineText = document.get(selectionLineOffset, selectionLineLength);

			Matcher nonSpaceMatch = noneSpacePattern.matcher(selectionLineText);
			StringBuilder varTypeHint = new StringBuilder(); // $NON-NLS-1$
			if (nonSpaceMatch.find()) {
				varTypeHint.append(selectionLineText.substring(0, nonSpaceMatch.start()));
			}

			varTypeHint.append("/** @var "); //$NON-NLS-1$
			varTypeHint.append(typeName);
			varTypeHint.append(" "); //$NON-NLS-1$
			if (variableNode instanceof Variable && !((Variable) variableNode).isDollared()) {
				varTypeHint.append("$"); //$NON-NLS-1$
			}
			varTypeHint.append(varName);
			varTypeHint.append(" */"); //$NON-NLS-1$
			varTypeHint.append(TextUtilities.getDefaultLineDelimiter(document));

			return new InsertEdit(selectionLineOffset, varTypeHint.toString());
		}

		/**
		 * Creates the dialog to select type
		 * 
		 * @throws BadLocationException
		 *             if variable name cannot be determined
		 */
		private SelectionDialog createTypeDialog(IDocument document) throws BadLocationException {

			String varName = document.get(variableNode.getStart(), variableNode.getLength());
			IDLTKUILanguageToolkit languageToolkit = PHPUILanguageToolkit.getInstance();

			final Shell parent = DLTKUIPlugin.getActiveWorkbenchShell();
			IDLTKSearchScope searchScope = SearchEngine.createSearchScope(sourceModule.getScriptProject());
			OpenTypeSelectionDialog2 dialog = new OpenTypeSelectionDialog2(parent, true,
					PlatformUI.getWorkbench().getProgressService(), searchScope, IDLTKSearchConstants.TYPE,
					languageToolkit);
			dialog.setTitle(Messages.VarCommentQuickAssistProcessor_OpenTypeAction_dialogTitle);
			dialog.setMessage(NLS.bind(Messages.VarCommentQuickAssistProcessor_OpenTypeAction_dialogMessage, varName));
			dialog.setFilter(""); //$NON-NLS-1$

			return dialog;
		}

		@Override
		public String getAdditionalProposalInfo() {
			return Messages.VarCommentQuickAssistProcessor_AdditionalProposalInfo;
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
				if (((Variable) selectedNode).isDollared() || org.eclipse.php.internal.core.corext.ASTNodes
						.isQuotedDollaredCurlied((Variable) selectedNode)) {
					ASTNode parent = selectedNode.getParent();
					if (parent != null && (parent.getType() == ASTNode.SINGLE_FIELD_DECLARATION
							|| parent.getType() == ASTNode.FORMAL_PARAMETER)) {
						state = FINISH;
						break;
					}

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
					selectedNode = NodeFinder.perform(selectedNode, selectedNode.getStart(), 0);
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
	public IScriptCompletionProposal[] getAssists(IInvocationContext context, IProblemLocation[] locations)
			throws CoreException {
		ASTNode variableNode = getVariableNode(context.getCoveringNode());
		if (null != variableNode) {
			return new IScriptCompletionProposal[] {
					new VarCommentCorrectionProposal(variableNode, context.getCompilationUnit()) };
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
