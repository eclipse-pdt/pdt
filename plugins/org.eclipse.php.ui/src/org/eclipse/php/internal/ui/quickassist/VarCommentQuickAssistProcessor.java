package org.eclipse.php.internal.ui.quickassist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.internal.ui.DLTKUIMessages;
import org.eclipse.dltk.internal.ui.dialogs.OpenTypeSelectionDialog2;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension5;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.ui.PHPUILanguageToolkit;
import org.eclipse.php.internal.ui.text.correction.IInvocationContext;
import org.eclipse.php.internal.ui.text.correction.IProblemLocation;
import org.eclipse.php.internal.ui.text.correction.IQuickAssistProcessor;
import org.eclipse.php.internal.ui.text.correction.proposals.CUCorrectionProposal;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;

public class VarCommentQuickAssistProcessor implements IQuickAssistProcessor {

	/**
	 * Creates the variable comment
	 */
	public static class VarCommentBuilder {

		final private Pattern noneSpacePattern = Pattern.compile("\\S+"); //$NON-NLS-1$

		/**
		 * Document
		 */
		IDocument document;

		/**
		 * Variable region in document
		 */
		IRegion varRegion;

		/**
		 * Text modification
		 */
		TextEdit edit;

		public VarCommentBuilder(IDocument document, IRegion varRegion) {
			this.document = document;
			this.varRegion = varRegion;
			this.edit = null;
		}

		/**
		 * Creates the dialog to select type
		 */
		private SelectionDialog createDialog() {

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
		 * Creates the variable comment
		 */
		public TextEdit createTextEdit() {
			edit = null;
			SelectionDialog dialog = createDialog();

			int result = dialog.open();
			if (result != IDialogConstants.OK_ID) {
				return null;
			}

			Object[] types = dialog.getResult();
			if (types != null && types.length == 1
					&& types[0] instanceof IModelElement) {
				try {
					createTextEditForType((IModelElement) types[0]);
				} catch (BadLocationException e) {
				}
			}
			return edit;
		}

		/**
		 * Creates the variable comment for given type
		 */
		private void createTextEditForType(IModelElement selectedType)
				throws BadLocationException {

			String typeName = selectedType.getElementName();
			IModelElement parent = selectedType.getParent();
			try {
				if (parent instanceof IMember && PHPFlags
						.isNamespace(((IMember) parent).getFlags())) {
					typeName = parent.getElementName() + '\\' + typeName;
				}
			} catch (ModelException e) {
				return;
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

			edit = new InsertEdit(selectionLineOffset, varTypeHint);
		}

	}

	/**
	 * Proposal implementation
	 */
	private static class VarCommentCorrectionProposal
			extends CUCorrectionProposal {

		/**
		 * Variable node
		 */
		private ASTNode varNode;

		public VarCommentCorrectionProposal(ISourceModule compilationUnit,
				ASTNode varNode) {
			super(org.eclipse.php.internal.ui.quickassist.Messages.VarCommentQuickAssistProcessor_name,
					compilationUnit, 0, null);
			this.varNode = varNode;
		}

		/**
		 * @see CUCorrectionProposal#addEdits(IDocument,TextEdit)
		 */
		@Override
		protected void addEdits(IDocument document, TextEdit editRoot)
				throws CoreException {

			super.addEdits(document, editRoot);
			Region varRegion = new Region(varNode.getStart(),
					varNode.getLength());
			VarCommentBuilder varCommentBuilder = new VarCommentBuilder(
					document, varRegion);

			TextEdit edit = varCommentBuilder.createTextEdit();
			if (edit != null) {
				editRoot.addChild(edit);
			}
		}

		/**
		 * @see ICompletionProposalExtension5#getAdditionalProposalInfo(IProgressMonitor)
		 */
		@Override
		public Object getAdditionalProposalInfo(IProgressMonitor monitor) {
			return null;
		}

	}

	/**
	 * @see IQuickAssistProcessor#getAssists(IInvocationContext,
	 *      IProblemLocation[])
	 */
	@Override
	public IScriptCompletionProposal[] getAssists(IInvocationContext context,
			IProblemLocation[] locations) throws CoreException {
		final ASTNode coveringNode = context.getCoveringNode();
		if (hasAssists(context)) {
			return new IScriptCompletionProposal[] {
					new VarCommentCorrectionProposal(
							context.getCompilationUnit(), coveringNode) };
		}

		return null;
	}

	/**
	 * @see IQuickAssistProcessor#hasAssists(IInvocationContext)
	 */
	@Override
	public boolean hasAssists(IInvocationContext context) throws CoreException {
		ASTNode node = context.getCoveringNode();
		if (node == null) {
			return false;
		}

		return (node.getType() == ASTNode.VARIABLE);
	}

}
