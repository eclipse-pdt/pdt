/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		Andrew McCullough - initial API and implementation
 *		IBM Corporation  - general improvement and bug fixes, partial reimplementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.dltk.ui.text.completion.ReplacementBuffer;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalCollector;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.dltk.ui.text.completion.ScriptMethodCompletionProposal;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.link.*;
import org.eclipse.jface.text.link.LinkedModeUI.ExitFlags;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.*;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.EditorHighlightingSynchronizer;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.text.template.contentassist.PositionBasedCompletionProposal;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.link.EditorLinkedModeUI;

/**
 * This is a
 * {@link org.eclipse.dltk.ui.text.completion.AbstractScriptCompletionProposal}
 * which includes templates that represent the best guess completion for each
 * parameter of a method.
 */
public final class ParameterGuessingProposal extends ScriptMethodCompletionProposal implements IProcessableProposal {
	private static final char[] NO_TRIGGERS = new char[0];
	protected static final String COMMA = ", "; //$NON-NLS-1$
	private CompletionProposal fProposal;
	protected static final String THIS = "$this->";//$NON-NLS-1$
	protected static final String DOUBLE_COLON = "::";//$NON-NLS-1$
	protected static final String PARENT_DOUBLE_COLON = "parent::";//$NON-NLS-1$
	protected static final String SELF_DOUBLE_COLON = "self::";//$NON-NLS-1$
	protected static final String SEMICOLON = ";"; //$NON-NLS-1$

	private String fQualifiedName;
	private IMethod method;
	private IMethod guessingMethod;
	private final boolean fFillBestGuess;
	private String alias = null;
	private int prefixLength = 0;

	private ICompletionProposal[][] fChoices; // initialized by
												// guessParameters()
	private Position[] fPositions; // initialized by guessParameters()

	private IRegion fSelectedRegion; // initialized by apply()
	private IPositionUpdater fUpdater;
	private int fContextInformationPosition;
	private LazyPHPTypeCompletionProposal fTypeProsoal;
	private String prefixWithoutProcessing;

	private ProposalProcessorManager mgr;
	private String lastPrefix;
	private String lastPrefixStyled;
	private StyledString initialDisplayString;

	public ParameterGuessingProposal(CompletionProposal proposal, ScriptContentAssistInvocationContext context,
			boolean fillBestGuess) {
		super(proposal, context);
		fFillBestGuess = fillBestGuess;
		guessingMethod = (IMethod) proposal.getModelElement();
		method = getProperMethod(guessingMethod);

		Object subProposal = proposal.getAttribute(PHPCompletionEngine.ATTR_SUB_PROPOSAL);
		if (subProposal instanceof CompletionProposal) {
			fTypeProsoal = new LazyPHPTypeCompletionProposal((CompletionProposal) subProposal, context);
		}
	}

	@Override
	public void apply(final IDocument document, char trigger, int offset) {
		try {
			super.apply(document, trigger, offset);
			int baseOffset = getReplacementOffset();
			String replacement = getReplacementString();

			if (fPositions != null && fPositions.length > 0 && getTextViewer() != null) {

				LinkedModeModel model = new LinkedModeModel();

				for (int i = 0; i < fPositions.length; i++) {
					LinkedPositionGroup group = new LinkedPositionGroup();
					int positionOffset = fPositions[i].getOffset();
					int positionLength = fPositions[i].getLength();

					if (fChoices[i].length < 2) {
						group.addPosition(new LinkedPosition(document, positionOffset, positionLength,
								LinkedPositionGroup.NO_STOP));
					} else {
						ensurePositionCategoryInstalled(document, model);
						document.addPosition(getCategory(), fPositions[i]);
						group.addPosition(new ProposalPosition(document, positionOffset, positionLength,
								LinkedPositionGroup.NO_STOP, fChoices[i]));
					}
					model.addGroup(group);
				}

				model.forceInstall();
				PHPStructuredEditor editor = getPHPEditor();
				if (editor != null) {
					model.addLinkingListener(new EditorHighlightingSynchronizer(editor));
				}

				LinkedModeUI ui = new EditorLinkedModeUI(model, getTextViewer());
				ui.setExitPosition(getTextViewer(), baseOffset + replacement.length(), 0, Integer.MAX_VALUE);
				// exit character can be either ')' or ';'
				final char exitChar = replacement.charAt(replacement.length() - 1);
				ui.setExitPolicy(new ExitPolicy(exitChar, document) {
					@Override
					public ExitFlags doExit(LinkedModeModel model2, VerifyEvent event, int offset2, int length) {
						if (event.character == ',') {
							for (int i = 0; i < fPositions.length - 1; i++) {
								Position position = fPositions[i];
								if (position.offset <= offset2
										&& offset2 + length <= position.offset + position.length) {
									try {
										ITypedRegion partition = TextUtilities.getPartition(document,
												"___java_partitioning", offset2 + length, false);
										if (IDocument.DEFAULT_CONTENT_TYPE.equals(partition.getType())
												|| offset2 + length == partition.getOffset() + partition.getLength()) {
											event.character = '\t';
											event.keyCode = SWT.TAB;
											return null;
										}
									} catch (BadLocationException e) {
										// continue; not serious enough to log
									}
								}
							}
						} else if (event.character == ')' && exitChar != ')') {
							// exit from link mode when user is in the last ')'
							// position.
							Position position = fPositions[fPositions.length - 1];
							if (position.offset <= offset2 && offset2 + length <= position.offset + position.length) {
								return new ExitFlags(ILinkedModeListener.UPDATE_CARET, false);
							}
						}
						return super.doExit(model2, event, offset2, length);
					}
				});
				ui.setCyclingMode(LinkedModeUI.CYCLE_WHEN_NO_PARENT);
				ui.setDoContextInfo(true);
				ui.enter();
				fSelectedRegion = ui.getSelectedRegion();

			} else {
				fSelectedRegion = new Region(baseOffset + replacement.length(), 0);
			}

		} catch (BadLocationException | BadPositionCategoryException e) {
			ensurePositionCategoryRemoved(document);
			PHPUiPlugin.log(e);
			openErrorDialog(e);
		}

	}

	private boolean prefixGlobalFunctionCall() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID,
				PHPCoreConstants.CODEASSIST_PREFIX_GLOBAL_FUNCTION_CALL, false, null);
	}

	/**
	 * Returns the currently active java editor, or <code>null</code> if it
	 * cannot be determined.
	 *
	 * @return the currently active java editor, or <code>null</code>
	 */
	private PHPStructuredEditor getPHPEditor() {
		IEditorPart part = PHPUiPlugin.getActivePage().getActiveEditor();
		if (part instanceof PHPStructuredEditor)
			return (PHPStructuredEditor) part;
		else
			return null;
	}

	@Override
	protected void computeReplacement(ReplacementBuffer buffer) {
		try {
			if (!hasArgumentList()) {
				if (!fProposal.isConstructor()) {
					super.computeReplacement(buffer);
				}
				return;
			}

			IMember element = (IMember) fInvocationContext.getSourceModule().getElementAt(getReplacementOffset());
			if (element != null) {
				if (ProposalExtraInfo.isInsertThis(fProposal.getExtraInfo())) {
					IType proposalMethodParent = (IType) method.getParent();
					IModelElement currentType = element;
					while (currentType instanceof IMethod) {
						currentType = currentType.getParent();
					}
					if ((PHPFlags.isClass(proposalMethodParent.getFlags())
							|| PHPFlags.isTrait(proposalMethodParent.getFlags())) && currentType instanceof IType) {
						String prefix = ""; //$NON-NLS-1$
						if (PHPFlags.isStatic(method.getFlags())) {
							IMethod m = ((IType) currentType).getMethod(method.getElementName());
							if (m.exists()) {
								prefix = SELF_DOUBLE_COLON;
							} else {
								prefix = PARENT_DOUBLE_COLON;
							}
						} else {
							prefix = THIS;
						}
						prefixLength = prefix.length();
						buffer.append(prefix);
					}
				}
			}
			buffer.append(computeGuessingCompletion());
			return;
		} catch (ModelException e) {
			if (!e.isDoesNotExist()) {
				PHPCorePlugin.log(e);
			}
		}
	}

	/**
	 * if modelElement is an instance of FakeConstructor, we need to get the
	 * real constructor
	 * 
	 * @param modelElement
	 * @return
	 */
	private IMethod getProperMethod(IMethod modelElement) {
		if (modelElement instanceof FakeConstructor) {
			FakeConstructor fc = (FakeConstructor) modelElement;
			if (fc.getParent() instanceof AliasType) {
				AliasType aliasType = (AliasType) fc.getParent();
				alias = aliasType.getAlias();
				if (aliasType.getParent() instanceof IType) {
					fc = FakeConstructor.createFakeConstructor(null, (IType) aliasType.getParent(), false);
				}
			}
			IType type = fc.getDeclaringType();
			IMethod[] ctors = FakeConstructor.getConstructors(type, fc.isEnclosingClass());
			// here we must make sure ctors[1] != null,
			// it means there is an available FakeConstructor for ctors[0]
			if (ctors != null && ctors.length == 2 && ctors[0] != null && ctors[1] != null) {
				return ctors[0];
			}
			return fc;
		} else if (modelElement instanceof FakeMethodForProposal) {
			return ((FakeMethodForProposal) modelElement).getMethod();
		}

		return modelElement;
	}

	/**
	 * Returns <code>true</code> if the argument list should be inserted by the
	 * proposal, <code>false</code> if not.
	 * 
	 * @return <code>true</code> when the proposal is not in javadoc nor within
	 *         an import and comprises the parameter list
	 */
	@Override
	protected boolean hasArgumentList() {
		if (CompletionProposal.METHOD_NAME_REFERENCE == fProposal.getKind()) {
			return false;
		}
		boolean noOverwrite = insertCompletion() ^ isToggleEating();
		String completion = fProposal.getCompletion();
		return !isInDoc() && completion.length() > 0
				&& (noOverwrite || completion.charAt(completion.length() - 1) == ')');
	}

	@Override
	protected boolean isValidPrefix(String prefix) {
		prefixWithoutProcessing = prefix;
		initAlias();
		String replacementString = null;

		int index = prefix.lastIndexOf(NamespaceReference.NAMESPACE_DELIMITER);
		if (index > 0) {
			prefix = prefix.substring(index + 1);
		}

		if (fProposal.isConstructor()) {
			String word = TextProcessor.deprocess(getDisplayString());
			int start = word.indexOf(ScriptElementLabels.CONCAT_STRING) + ScriptElementLabels.CONCAT_STRING.length();
			word = word.substring(start);
			return isPrefix(prefix, word) || isPrefix(prefix, new String(fProposal.getName()));
		}

		if (alias != null) {
			replacementString = alias + LPAREN + RPAREN;
		} else {
			if (prefix.length() > 0 && prefix.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
				prefix = prefix.substring(1);
				replacementString = getQualifiedMethodName();
			} else {
				replacementString = super.getReplacementString();
				if (prefixLength > 0) {
					replacementString = replacementString.substring(prefixLength);
				}
			}
		}
		return isPrefix(prefix, replacementString);
	}

	public final String getQualifiedMethodName() {
		if (fQualifiedName == null) {
			fQualifiedName = ((IMethod) getModelElement())
					.getFullyQualifiedName(NamespaceReference.NAMESPACE_DELIMITER);
		}
		return fQualifiedName;
	}

	private void initAlias() {
		alias = null;
		if (method instanceof FakeConstructor) {
			FakeConstructor fc = (FakeConstructor) method;
			if (fc.getParent() instanceof AliasType) {
				alias = ((AliasType) fc.getParent()).getAlias();
			}
		} else if (method instanceof AliasMethod) {
			alias = ((AliasMethod) method).getAlias();
		}
	}

	@Override
	protected boolean isPrefix(String prefix, String string) {
		lastPrefix = prefix;
		boolean res = mgr.prefixChanged(prefix) || super.isPrefix(prefix, string);
		return res;
	}

	@Override
	public StyledString getStyledDisplayString() {
		if (initialDisplayString == null) {
			initialDisplayString = super.getStyledDisplayString();
			StyledString copy = copyStyledString(initialDisplayString);
			StyledString decorated = mgr.decorateStyledDisplayString(copy);
			setStyledDisplayString(decorated);
		}
		if (lastPrefixStyled != lastPrefix) {
			lastPrefixStyled = lastPrefix;
			StyledString copy = copyStyledString(initialDisplayString);
			StyledString decorated = mgr.decorateStyledDisplayString(copy);
			setStyledDisplayString(decorated);
		}
		return super.getStyledDisplayString();
	}

	/**
	 * Creates the completion string. Offsets and Lengths are set to the offsets
	 * and lengths of the parameters.
	 * 
	 * @param prefix
	 *            completion prefix
	 * @return the completion string
	 * @throws ModelException
	 *             if parameter guessing failed
	 */
	private String computeGuessingCompletion() throws ModelException {
		StringBuilder buffer = new StringBuilder();
		appendMethodNameReplacement(buffer);

		setCursorPosition(buffer.length());
		// show method parameter names:
		IParameter[] parameters = method.getParameters();
		List<String> paramList = new ArrayList<>();
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				IParameter parameter = parameters[i];
				if (parameter.getDefaultValue() != null) {
					break;
				}
				paramList.add(parameter.getName());
			}
		}

		fProposal.setAttribute(ScriptCompletionProposalCollector.ATTR_PARAM_LIMIT, paramList.size());

		char[][] parameterNames = new char[paramList.size()][];
		for (int i = 0; i < paramList.size(); ++i) {
			parameterNames[i] = paramList.get(i).toCharArray();
		}

		fChoices = guessParameters(parameterNames);
		int count = fChoices.length;
		int replacementOffset = getReplacementOffset() + prefixLength;

		for (int i = 0; i < count; i++) {
			if (i != 0) {
				buffer.append(COMMA);
				buffer.append(SPACE);
			}

			ICompletionProposal proposal = fChoices[i][0];
			String argument = proposal.getDisplayString();

			Position position = fPositions[i];
			position.setOffset(replacementOffset + buffer.length());
			position.setLength(argument.length());

			buffer.append(argument);
		}

		buffer.append(RPAREN);

		if (canAutomaticallyAppendSemicolon())
			buffer.append(SEMICOLON);

		return buffer.toString();
	}

	/**
	 * Appends everything up to the method name including the opening
	 * parenthesis.
	 * <p>
	 * In case of {@link CompletionProposal#METHOD_REF_WITH_CASTED_RECEIVER} it
	 * add cast.
	 * </p>
	 * 
	 * @param buffer
	 *            the string buffer
	 */
	protected void appendMethodNameReplacement(StringBuilder buffer) {
		if (!fProposal.isConstructor()) {
			if (alias != null) {
				buffer.append(alias);
			} else {
				if (ProposalExtraInfo.isPrefixHasNamespace(fProposal.getExtraInfo())) {
					int index = prefixWithoutProcessing.lastIndexOf(NamespaceReference.NAMESPACE_DELIMITER);
					StringBuilder namespace = new StringBuilder();
					if (index > 0) {
						namespace.append(prefixWithoutProcessing.substring(0, index));
					}
					buffer.append(namespace.append(NamespaceReference.NAMESPACE_DELIMITER).toString());
				} else if (ProposalExtraInfo.isFullName(fProposal.getExtraInfo())) {
					buffer.append(NamespaceReference.NAMESPACE_DELIMITER)
							.append(method.getFullyQualifiedName(NamespaceReference.NAMESPACE_DELIMITER));
				} else {
					buffer.append(fProposal.getName());
				}
			}
		}
		buffer.append(LPAREN);
	}

	private ICompletionProposal[][] guessParameters(char[][] parameterNames) throws ModelException {
		int count = parameterNames.length;
		fPositions = new Position[count];
		fChoices = new ICompletionProposal[count][];

		IParameter[] parameters = method.getParameters();

		for (int i = count - 1; i >= 0; i--) {
			String paramName = new String(parameterNames[i]);
			Position position = new Position(0, 0);

			ICompletionProposal[] argumentProposals = parameterProposals(parameters[i].getDefaultValue(), paramName,
					position, fFillBestGuess);

			fPositions[i] = position;
			fChoices[i] = argumentProposals;
		}

		return fChoices;
	}

	/*
	 * @see ICompletionProposal#getSelection(IDocument)
	 */
	@Override
	public Point getSelection(IDocument document) {
		if (fSelectedRegion == null) {
			return new Point(getReplacementOffset(), 0);
		}

		return new Point(fSelectedRegion.getOffset(), fSelectedRegion.getLength());
	}

	private void openErrorDialog(Exception e) {
		Shell shell = getTextViewer().getTextWidget().getShell();
		MessageDialog.openError(shell, Messages.ParameterGuessingProposal_0, e.getMessage());
	}

	private void ensurePositionCategoryInstalled(final IDocument document, LinkedModeModel model) {
		if (!document.containsPositionCategory(getCategory())) {
			document.addPositionCategory(getCategory());
			fUpdater = new InclusivePositionUpdater(getCategory());
			document.addPositionUpdater(fUpdater);

			model.addLinkingListener(new ILinkedModeListener() {

				@Override
				public void left(LinkedModeModel environment, int flags) {
					ensurePositionCategoryRemoved(document);
				}

				@Override
				public void suspend(LinkedModeModel environment) {
				}

				@Override
				public void resume(LinkedModeModel environment, int flags) {
				}
			});
		}
	}

	private void ensurePositionCategoryRemoved(IDocument document) {
		if (document.containsPositionCategory(getCategory())) {
			try {
				document.removePositionCategory(getCategory());
			} catch (BadPositionCategoryException e) {
				// ignore
			}
			document.removePositionUpdater(fUpdater);
		}
	}

	private String getCategory() {
		return "ParameterGuessingProposal_" + toString(); //$NON-NLS-1$
	}

	/**
	 * Returns the matches for the type and name argument, ordered by match
	 * quality.
	 * 
	 * @param expectedType
	 *            - the qualified type of the parameter we are trying to match
	 * @param paramName
	 *            - the name of the parameter (used to find similarly named
	 *            matches)
	 * @param pos
	 * @param suggestions
	 *            the suggestions or <code>null</code>
	 * @param fillBestGuess
	 * @return returns the name of the best match, or <code>null</code> if no
	 *         match found
	 * @throws JavaModelException
	 *             if it fails
	 */
	public ICompletionProposal[] parameterProposals(String initialValue, String paramName, Position pos,
			boolean fillBestGuess) throws ModelException {
		List<String> typeMatches = new ArrayList<>();
		if (initialValue != null) {
			typeMatches.add(initialValue);
		}
		ICompletionProposal[] ret = new ICompletionProposal[typeMatches.size()];
		int i = 0;
		int replacementLength = 0;
		for (Iterator<String> it = typeMatches.iterator(); it.hasNext();) {
			String name = it.next();
			if (i == 0) {
				replacementLength = name.length();
			}

			final char[] triggers = new char[1];
			triggers[triggers.length - 1] = ';';

			ret[i++] = new PositionBasedCompletionProposal(name, pos, replacementLength, getImage(), name, null, null,
					triggers);
		}
		if (!fillBestGuess) {
			// insert a proposal with the argument name
			ICompletionProposal[] extended = new ICompletionProposal[ret.length + 1];
			System.arraycopy(ret, 0, extended, 1, ret.length);
			extended[0] = new PositionBasedCompletionProposal(paramName, pos,
					replacementLength/* paramName.length() */, null, paramName, null, null, NO_TRIGGERS);
			return extended;
		}
		return ret;
	}

	@Override
	public boolean validate(IDocument document, int offset, DocumentEvent event) {

		if (!isOffsetValid(offset))
			return false;

		boolean isValidated = isValidPrefix(getPrefix(document, offset));

		if (isValidated && event != null) {
			// adapt replacement range to document change
			int delta = (event.fText == null ? 0 : event.fText.length()) - event.fLength;
			final int newLength = Math.max(getReplacementLength() + delta, 0);
			setReplacementLength(newLength);
		}

		return isValidated;
	}

	@Override
	protected String getPrefix(IDocument document, int offset) {
		if (!fProposal.isConstructor()) {
			return super.getPrefix(document, offset);
		}

		int replacementOffset = fTypeProsoal.getReplacementOffset();

		try {
			int length = offset - replacementOffset;
			if (length > 0)
				return document.get(replacementOffset, length);
		} catch (BadLocationException x) {
		}
		return ""; //$NON-NLS-1$
	}

	protected boolean isOffsetValid(int offset) {
		if (!fProposal.isConstructor())
			return getReplacementOffset() <= offset;

		return fTypeProsoal.getReplacementOffset() <= offset;
	}

	@Override
	public IModelElement getModelElement() {
		return guessingMethod;
	}

	/**
	 * Creates a {@link ParameterGuessingProposal} or <code>null</code> if the
	 * core context isn't available or extended.
	 *
	 * @param proposal
	 *            the original completion proposal
	 * @param context
	 *            the currrent context
	 * @param fillBestGuess
	 *            if set, the best guess will be filled in
	 *
	 * @return a proposal or <code>null</code>
	 */
	public static ParameterGuessingProposal createProposal(CompletionProposal proposal,
			ScriptContentAssistInvocationContext context, boolean fillBestGuess) {
		return new ParameterGuessingProposal(proposal, context, fillBestGuess);
	}

	@Override
	protected IContextInformation computeContextInformation() {
		if (fProposal.getKind() == CompletionProposal.METHOD_REF && fPositions != null && fPositions.length > 0
				&& (getReplacementString().endsWith(RPAREN) || getReplacementString().endsWith(SEMICOLON)
						|| getReplacementString().length() == 0)) {
			ProposalContextInformation contextInformation = new ProposalContextInformation(fProposal);
			if (fContextInformationPosition != 0 && fProposal.getCompletion().length() == 0)
				contextInformation.setContextInformationPosition(fContextInformationPosition);
			return contextInformation;
		}
		return null;
	}

	@Override
	public int getPrefixCompletionStart(IDocument document, int completionOffset) {
		if (fProposal.isConstructor())
			return fTypeProsoal.getReplacementOffset();
		return super.getPrefixCompletionStart(document, completionOffset);
	}

	@Override
	protected boolean insertCompletion() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID,
				PHPCoreConstants.CODEASSIST_INSERT_COMPLETION, true, null);
	}

	/**
	 * Returns whether we automatically complete the method with a semicolon.
	 * 
	 * @return <code>true</code> if the return type of the method is void,
	 *         <code>false</code> otherwise
	 * @throws ModelException
	 */
	protected final boolean canAutomaticallyAppendSemicolon() throws ModelException {
		return !fProposal.isConstructor()
				&& (guessingMethod.getType() != null && guessingMethod.getType().equals("void")); //$NON-NLS-1$
	}

	/**
	 * Overrides the default context information position. Ignored if set to
	 * zero.
	 *
	 * @param contextInformationPosition
	 *            the replaced position.
	 */
	@Override
	public void setContextInformationPosition(int contextInformationPosition) {
		fContextInformationPosition = contextInformationPosition;
	}

	@Override
	protected ScriptTextTools getTextTools() {
		return PHPUiPlugin.getDefault().getTextTools();
	}

	@Override
	public ProposalProcessorManager getProposalProcessorManager() {
		return mgr;
	}

	@Override
	public void setProposalProcessorManager(ProposalProcessorManager mgr) {
		this.mgr = mgr;
	}

}
