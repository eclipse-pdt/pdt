/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.link.*;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.AliasMethod;
import org.eclipse.php.internal.core.codeassist.AliasType;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.text.template.contentassist.PositionBasedCompletionProposal;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.texteditor.link.EditorLinkedModeUI;

/**
 * This is a
 * {@link org.eclipse.jdt.internal.ui.text.java.JavaCompletionProposal} which
 * includes templates that represent the best guess completion for each
 * parameter of a method.
 */
public final class ParameterGuessingProposal extends PHPOverrideCompletionProposal
		implements IPHPCompletionProposalExtension {
	private static final char[] NO_TRIGGERS = new char[0];
	protected static final char LPAREN = '('; // $NON-NLS-1$
	protected static final char RPAREN = ')'; // $NON-NLS-1$
	protected static final String COMMA = ", "; //$NON-NLS-1$
	private CompletionProposal fProposal;
	private IMethod method;
	private IMethod guessingMethod;
	private final boolean fFillBestGuess;
	private boolean fReplacementStringComputed = false;
	private Object extraInfo;
	private boolean fReplacementLengthComputed;
	private String alias = null;
	private IDocument document = null;
	private IScriptProject sProject = null;

	private ICompletionProposal[][] fChoices; // initialized by
	// guessParameters()
	private Position[] fPositions; // initialized by guessParameters()

	private IRegion fSelectedRegion; // initialized by apply()
	private IPositionUpdater fUpdater;

	public ParameterGuessingProposal(CompletionProposal proposal, IScriptProject jproject, ISourceModule cu,
			String methodName, String[] paramTypes, int start, int length, StyledString displayName,
			String completionProposal, boolean fillBestGuess, Object extraInfo, IDocument document) {
		super(jproject, cu, methodName, paramTypes, start, length, displayName, completionProposal);
		this.fProposal = proposal;
		method = (IMethod) fProposal.getModelElement();
		guessingMethod = method;
		this.fFillBestGuess = fillBestGuess;
		this.extraInfo = extraInfo;
		this.document = document;
		this.sProject = jproject;
	}

	/*
	 * @see ICompletionProposalExtension#apply(IDocument, char)
	 */
	@Override
	public void apply(IDocument document, char trigger, int offset) {
		try {
			dealPrefix();
			dealSuffix(document, offset);

			// TODO lengthChange is workaround for replacement string changed by
			// super class, this needs better solution
			int lengthChange = getReplacementString().length();
			super.apply(document, trigger, offset);
			lengthChange = Math.max(0, getReplacementString().length() - lengthChange);

			int baseOffset = getReplacementOffset();
			String replacement = getReplacementString();
			boolean hasParameters = false;
			try {
				hasParameters = method.getParameters().length != 0;
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}

			if (hasParameters && getTextViewer() != null) {
				LinkedModeModel model = new LinkedModeModel();

				if ((fPositions != null && fPositions.length > 0)) {
					for (int i = 0; i < fPositions.length; i++) {
						LinkedPositionGroup group = new LinkedPositionGroup();
						int positionOffset = fPositions[i].getOffset() + lengthChange;
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
				} else {
					LinkedPositionGroup group = new LinkedPositionGroup();
					group.addPosition(new LinkedPosition(document, getReplacementOffset() + getCursorPosition(), 0,
							LinkedPositionGroup.NO_STOP));
					model.addGroup(group);
				}

				model.forceInstall();

				LinkedModeUI ui = new EditorLinkedModeUI(model, getTextViewer());
				ui.setExitPosition(getTextViewer(), baseOffset + replacement.length(), 0, Integer.MAX_VALUE);
				ui.setCyclingMode(LinkedModeUI.CYCLE_WHEN_NO_PARENT);
				ui.setDoContextInfo(true);
				ui.enter();
				fSelectedRegion = ui.getSelectedRegion();
			} else {
				fSelectedRegion = new Region(baseOffset + getCursorPosition(), 0);
			}

		} catch (BadLocationException e) {
			ensurePositionCategoryRemoved(document);
			PHPUiPlugin.log(e);
			openErrorDialog(e);
		} catch (BadPositionCategoryException e) {
			ensurePositionCategoryRemoved(document);
			PHPUiPlugin.log(e);
			openErrorDialog(e);
		}
	}

	private void dealPrefix() {
		String prefix = ""; //$NON-NLS-1$
		if (shouldHaveGlobalNamespace()) {
			prefix += NamespaceReference.NAMESPACE_SEPARATOR;
		}

		if (ProposalExtraInfo.isMethodOnly(extraInfo)) {
			setReplacementString(prefix + method.getElementName());
			return;
		}

		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(PHPCorePlugin.ID);
		boolean fileArgumentNames = prefs.getBoolean(PHPCoreConstants.CODEASSIST_FILL_ARGUMENT_NAMES, true);
		if (fileArgumentNames && !fReplacementStringComputed)
			setReplacementString(computeReplacementString(prefix));
		if (!fileArgumentNames)
			setReplacementString(prefix + super.getReplacementString());
	}

	private boolean shouldHaveGlobalNamespace() {
		if (ProjectOptions.getPHPVersion(sProject.getProject()).isLessThan(PHPVersion.PHP5_3)) {
			return false;
		}
		IType type = method.getDeclaringType();
		boolean isInNamespace = PHPModelUtils.getCurrentNamespaceIfAny(fSourceModule, getReplacementOffset()) != null;

		boolean isNotAlias = !(type instanceof AliasType);
		boolean isNamespacedType = PHPModelUtils.getCurrentNamespace(type) != null;

		try {
			boolean globalMethod = (type == null && method.getNamespace() == null);
			boolean globalConstructor = type != null && !isNamespacedType && method.isConstructor();
			if (((globalMethod && prefixGlobalFunctionCall()) || globalConstructor) && isInNamespace && isNotAlias
					&& document.getChar(getReplacementOffset() - 1) != NamespaceReference.NAMESPACE_SEPARATOR) {
				return true;
			}
		} catch (ModelException e) {
			PHPUiPlugin.log(e);
		} catch (BadLocationException e) {
			PHPUiPlugin.log(e);
		}
		return false;
	}

	private boolean prefixGlobalFunctionCall() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID,
				PHPCoreConstants.CODEASSIST_PREFIX_GLOBAL_FUNCTION_CALL, false, null);
	}

	private void dealSuffix(IDocument document, int offset) {
		boolean toggleEating = isToggleEating();
		boolean insertCompletion = insertCompletion();
		String replacement = getReplacementString();
		int posReplacementLP = replacement.indexOf(LPAREN);
		if (posReplacementLP >= 0 && replacement.endsWith(String.valueOf(RPAREN))) {
			int searchOffset;
			if (!insertCompletion || toggleEating) {
				searchOffset = getReplacementOffset() + getReplacementLength();
			} else {
				searchOffset = offset;
			}
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=459377
			int posLP = getRelativePositionOf(document, searchOffset, LPAREN);
			if (posLP >= 0) {
				int posRP = getRelativePositionOf(document, searchOffset + (posLP + 1), RPAREN);
				if (posRP < 0) {
					// unset all parameters that have to be written in the
					// document, they should not collide with the text
					// already written in the document after left parenthesis
					fPositions = null;
					fChoices = null;
					// we truncate the replacement text starting
					// from left parenthesis (included)
					replacement = replacement.substring(0, posReplacementLP);
					setReplacementString(replacement);
					// put the cursor before left parenthesis in document,
					// it will be put after left parenthesis through
					// PHPOverrideCompletionProposal#calculateCursorPosition()
					setReplacementLength(getReplacementLength() + posLP);
				} else {
					// put the cursor after right parenthesis in document
					setReplacementLength(getReplacementLength() + (posLP + 1) + (posRP + 1));
				}
			}
		} else {
			// unset all existing parameters (if any), they are useless now
			fPositions = null;
			fChoices = null;
			int searchOffset;
			if (!insertCompletion || toggleEating) {
				searchOffset = getReplacementOffset() + getReplacementLength();
			} else {
				searchOffset = offset;
			}
			int posLP = getRelativePositionOf(document, searchOffset, LPAREN);
			if (posLP < 0) {
				// append missing parentheses in insert and overwrite mode
				replacement = replacement + LPAREN + RPAREN;
				setReplacementString(replacement);
			} else {
				// put the cursor before left parenthesis in document,
				// it will be put after left parenthesis through
				// PHPOverrideCompletionProposal#calculateCursorPosition()
				setReplacementLength(getReplacementLength() + posLP);
			}
		}
	}

	/**
	 * Retrieves the position of the first occurrence of a "search" character,
	 * starting from "offset" position and until end of line.
	 * 
	 * @param document
	 * @param offset
	 * @param search
	 *            character to search
	 * @return position of "search" character relative to offset, -1 if not
	 *         found or if there are non-whitespace characters between "offset"
	 *         position and the first occurrence of the "search" character
	 */
	private int getRelativePositionOf(IDocument document, int offset, char search) {
		try {
			IRegion line = document.getLineInformationOfOffset(offset);
			int lineEnd = line.getOffset() + line.getLength();
			if (offset >= lineEnd) {
				// end of line
				return -1;
			}
			int pos = 0;
			while (offset + pos < lineEnd - 1 // 1 = "search" length
					&& Character.isWhitespace(document.getChar(offset + pos))) {
				pos++;
			}
			return document.getChar(offset + pos) == search ? pos : -1;
		} catch (BadLocationException e) {
		}
		return -1;
	}

	/**
	 * Gets the replacement length.
	 * 
	 * @return Returns a int
	 */
	@Override
	public final int getReplacementLength() {
		if (!fReplacementLengthComputed)
			setReplacementLength(fProposal.getReplaceEnd() - fProposal.getReplaceStart());
		return super.getReplacementLength();
	}

	/**
	 * Sets the replacement length.
	 * 
	 * @param replacementLength
	 *            The replacementLength to set
	 */
	@Override
	public final void setReplacementLength(int replacementLength) {
		fReplacementLengthComputed = true;
		super.setReplacementLength(replacementLength);
	}

	/*
	 * @seeorg.eclipse.jdt.internal.ui.text.java.JavaMethodCompletionProposal#
	 * needsLinkedMode()
	 */
	protected boolean needsLinkedMode() {
		return false; // we handle it ourselves
	}

	private String computeReplacementString(String prefix) {
		fReplacementStringComputed = true;
		try {
			// we should get the real constructor here
			method = getProperMethod(guessingMethod);
			if (alias != null || (hasParameters() && hasArgumentList())) {
				return computeGuessingCompletion(prefix);
			}
		} catch (ModelException e) {
			if (!e.isDoesNotExist()) {
				PHPCorePlugin.log(e);
			}
		}
		return prefix + super.getReplacementString();
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
	protected boolean hasArgumentList() {
		if (CompletionProposal.METHOD_NAME_REFERENCE == fProposal.getKind())
			return false;
		String completion = fProposal.getCompletion();
		return !isInDoc() && completion.length() > 0;
	}

	@Override
	protected boolean isValidPrefix(String prefix) {
		initAlias();
		String replacementString = null;
		if (alias != null) {
			replacementString = alias + LPAREN + RPAREN;
		} else {
			replacementString = super.getReplacementString();
		}
		return isPrefix(prefix, replacementString);
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

	private boolean hasParameters() throws ModelException {
		return method.getParameters() != null && hasNondefaultValues(method.getParameters());
	}

	private boolean hasNondefaultValues(IParameter[] parameters) {
		for (int i = 0; i < parameters.length; i++) {
			IParameter parameter = parameters[i];
			if (parameter.getDefaultValue() == null) {
				return true;
			}
		}
		return false;
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
	private String computeGuessingCompletion(String prefix) throws ModelException {
		StringBuilder buffer = new StringBuilder(prefix);
		appendMethodNameReplacement(buffer);

		setCursorPosition(buffer.length());
		// show method parameter names:
		IParameter[] parameters = method.getParameters();
		List<String> paramList = new ArrayList<>();
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				IParameter parameter = parameters[i];
				if (parameter.getDefaultValue() == null) {
					paramList.add(parameter.getName());
				}
			}
		}
		char[][] parameterNames = new char[paramList.size()][];
		for (int i = 0; i < paramList.size(); ++i) {
			parameterNames[i] = paramList.get(i).toCharArray();
		}

		fChoices = guessParameters(parameterNames);
		int count = fChoices.length;
		int replacementOffset = getReplacementOffset();

		for (int i = 0; i < count; i++) {
			if (i != 0) {
				buffer.append(COMMA);
			}

			ICompletionProposal proposal = fChoices[i][0];
			String argument = proposal.getDisplayString();

			Position position = fPositions[i];
			position.setOffset(replacementOffset + buffer.length());
			position.setLength(argument.length());

			buffer.append(argument);
		}

		buffer.append(RPAREN);

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
	 * @since 3.4
	 */
	protected void appendMethodNameReplacement(StringBuilder buffer) {
		if (alias != null) {
			buffer.append(alias);
			buffer.append(LPAREN);
		} else {
			buffer.append(fProposal.getName());
			buffer.append(LPAREN);
		}
	}

	private ICompletionProposal[][] guessParameters(char[][] parameterNames) throws ModelException {
		// find matches in reverse order. Do this because people tend to declare
		// the variable meant for the last
		// parameter last. That is, local variables for the last parameter in
		// the method completion are more
		// likely to be closer to the point of code completion. As an example
		// consider a "delegation" completion:
		//
		// public void myMethod(int param1, int param2, int param3) {
		// someOtherObject.yourMethod(param1, param2, param3);
		// }
		//
		// The other consideration is giving preference to variables that have
		// not previously been used in this
		// code completion (which avoids
		// "someOtherObject.yourMethod(param1, param1, param1)";

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
		if (fSelectedRegion == null)
			return new Point(getReplacementOffset(), 0);

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

				/*
				 * @see
				 * org.eclipse.jface.text.link.ILinkedModeListener#left(org.
				 * eclipse.jface.text.link.LinkedModeModel, int)
				 */
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
	public void setReplacementOffset(int replacementOffset) {
		int oldReplacementOffset = getReplacementOffset();
		if (fPositions != null && fPositions.length > 0) {
			for (Position position : fPositions) {
				position.offset = position.offset + (replacementOffset - oldReplacementOffset);
			}
		}

		super.setReplacementOffset(replacementOffset);
	}

	@Override
	public IModelElement getModelElement() {
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=469377
		// be sure to return the "unchanged" method
		return guessingMethod;
	}

	@Override
	public Object getExtraInfo() {
		return extraInfo;
	}
}
