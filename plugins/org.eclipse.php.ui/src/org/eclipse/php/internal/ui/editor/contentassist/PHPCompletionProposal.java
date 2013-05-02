/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.ui.text.hover.CompletionHoverControlCreator;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.jface.internal.text.html.BrowserInformationControl;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.strategies.IncludeStatementStrategy;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

@SuppressWarnings("restriction")
public class PHPCompletionProposal extends ScriptCompletionProposal {

	/**
	 * The control creator.
	 */
	private IInformationControlCreator fCreator;

	public PHPCompletionProposal(String replacementString,
			int replacementOffset, int replacementLength, Image image,
			String displayString, int relevance) {
		super(replacementString, replacementOffset, replacementLength, image,
				displayString, relevance);
	}

	public PHPCompletionProposal(String replacementString,
			int replacementOffset, int replacementLength, Image image,
			String displayString, int relevance, boolean indoc) {
		super(replacementString, replacementOffset, replacementLength, image,
				displayString, relevance, indoc);
	}

	protected boolean isValidPrefix(String prefix) {
		String word = getDisplayString();
		if (word.startsWith("$") && !prefix.startsWith("$")) { //$NON-NLS-1$ //$NON-NLS-2$
			word = word.substring(1);
		}
		boolean result = isPrefix(prefix, word);
		if (!result && ProposalExtraInfo.isClassInNamespace(getExtraInfo())
				&& (getModelElement() instanceof IType)) {
			IType type = (IType) getModelElement();
			result = isPrefix(prefix, PHPModelUtils.getFullName(type));
		}
		// int index = word.indexOf(" - ");
		// if (!result && index >= 0) {
		// StringBuffer sb = new StringBuffer();
		// sb.append(word.substring(index + " - ".length()));
		// sb.append('\\');
		// sb.append(word.substring(0, index));
		// result = isPrefix(prefix, sb.toString());
		// }
		return result;
	}

	protected boolean isSmartTrigger(char trigger) {
		return trigger == '$';
	}

	public void apply(IDocument document, char trigger, int offset) {
		boolean toggleEating = isToggleEating();
		boolean instertCompletion = insertCompletion();
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=395654
		// workaround for GlobalTypesStrategy.getReplacementRange()
		if (instertCompletion && toggleEating) {
			setReplacementLength(getReplacementLength() + 1);
		}

		IModelElement modelElement = getModelElement();

		boolean activateCodeAssist = false;
		String replacementString = getReplacementString();
		if (modelElement instanceof IScriptProject
				&& replacementString
						.endsWith(IncludeStatementStrategy.FOLDER_SEPARATOR)) {
			// workaround for:
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=269634
			activateCodeAssist = true;
		} else {
			IPreferencesService preferencesService = Platform
					.getPreferencesService();
			boolean enableAutoactivation = preferencesService.getBoolean(
					PHPCorePlugin.ID,
					PHPCoreConstants.CODEASSIST_AUTOACTIVATION, false, null);
			if (enableAutoactivation) {
				char lastChar = replacementString.charAt(replacementString
						.length() - 1);
				for (char autoActivationChar : PHPCompletionProcessor.completionAutoActivationChars) {
					if (autoActivationChar == lastChar) {
						activateCodeAssist = true;
						break;
					}
				}
			}
		}
		if (activateCodeAssist) {
			AutoActivationTrigger.register(document);
		}

		UseStatementInjector injector = new UseStatementInjector(this);
		offset = injector.inject(document, getTextViewer(), offset);

		super.apply(document, trigger, offset);

		setCursorPosition(calcCursorPosition());
	}

	private int calcCursorPosition() {
		String replacementString = getReplacementString();
		int i = replacementString.lastIndexOf('(');
		if (i != -1) {
			return i + 1;
		}
		i = replacementString.lastIndexOf('\'');
		if (i != -1) {
			return i;
		}
		i = replacementString.lastIndexOf('\"');
		if (i != -1) {
			return i;
		}
		return replacementString.length();
	}

	public IContextInformation getContextInformation() {
		String displayString = getDisplayString();
		if (displayString.indexOf('(') == -1) {
			return null;
		}
		return super.getContextInformation();
	}

	protected boolean isCamelCaseMatching() {
		return true;
	}

	protected boolean insertCompletion() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID,
				PHPCoreConstants.CODEASSIST_INSERT_COMPLETION, true, null);
	}

	protected ScriptTextTools getTextTools() {
		return PHPUiPlugin.getDefault().getTextTools();
	}

	public IInformationControlCreator getInformationControlCreator() {
		if (fCreator == null) {
			fCreator = new CompletionHoverControlCreator(
					new IInformationControlCreator() {
						public IInformationControl createInformationControl(
								Shell parent) {
							if (BrowserInformationControl.isAvailable(parent)) {
								return new BrowserInformationControl(
										parent,
										PreferenceConstants.APPEARANCE_DOCUMENTATION_FONT,
										true);
							} else {
								return new DefaultInformationControl(parent,
										true);
							}
						}
					}, true);
		}
		return fCreator;
	}

	public Object getExtraInfo() {
		return ProposalExtraInfo.DEFAULT;
	}
}