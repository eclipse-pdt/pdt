/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.graphics.Image;

public class PHPCompletionProposal extends ScriptCompletionProposal {

	public PHPCompletionProposal(String replacementString, int replacementOffset, int replacementLength, Image image, String displayString, int relevance) {
		super(replacementString, replacementOffset, replacementLength, image, displayString, relevance);
	}

	public PHPCompletionProposal(String replacementString, int replacementOffset, int replacementLength, Image image, String displayString, int relevance, boolean indoc) {
		super(replacementString, replacementOffset, replacementLength, image, displayString, relevance, indoc);
	}

	protected boolean isSmartTrigger(char trigger) {
		return trigger == '$';
	}

	public void apply(IDocument document, char trigger, int offset) {
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
		Preferences pluginPreferences = PHPCorePlugin.getDefault().getPluginPreferences();
		return pluginPreferences.getBoolean(PHPCoreConstants.CODEASSIST_INSERT_COMPLETION);
	}

	protected ScriptTextTools getTextTools() {
		return PHPUiPlugin.getDefault().getTextTools();
	}
}