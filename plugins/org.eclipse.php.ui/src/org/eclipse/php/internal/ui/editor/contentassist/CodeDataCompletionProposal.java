/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.contentassist.*;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.util.PHPCodeDataHTMLDescriptionUtilities;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

public class CodeDataCompletionProposal implements ICompletionProposal, ICompletionProposalExtension2, ICompletionProposalExtension3, ICompletionProposalExtension4 {

	private static final PHPCompletionRendererVisitor rendererVisitor = new PHPCompletionRendererVisitor();
	private static final PHPContextInfoRendererVisitor contextRendererVisitor = new PHPContextInfoRendererVisitor();

	protected static final char[] phpDelimiters = new char[] { '?', ':', ';', '|', '^', '&', '<', '>', '+', '-', '.', '*', '/', '%', '!', '~', '[', ']', '(', ')', '{', '}', '@', '\n', '\t', ' ', ',', '$', '\'', '\"' };

	private String displayText;
	private Image displayImage;
	private CodeData codeData;
	protected int replacementOffset;
	protected int replacementLength;
	protected int selectionLength;
	private String replacementString;
	private String prefix;
	private String suffix;
	private int caretOffsetInSuffix;
	private PHPProjectModel projectModel;
	private ContextInformation contextInfo;
	private boolean showTypeHints;
	private int offset;

	public CodeDataCompletionProposal(PHPProjectModel projectModel, CodeData codeData, int offset, int length, int selectionLength, String prefix, String suffix, int caretOffsetInSuffix, boolean showTypeHints) {
		this.codeData = codeData;
		this.replacementOffset = offset;
		this.replacementLength = length;
		this.selectionLength = selectionLength;
		this.prefix = prefix;
		this.suffix = suffix;
		this.caretOffsetInSuffix = caretOffsetInSuffix;
		this.showTypeHints = showTypeHints;
		this.projectModel = projectModel;
		this.offset = offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	protected String getReplacementString() {
		if (replacementString == null) {
			replacementString = prefix + codeData.getName() + suffix;
		}
		return replacementString;
	}

	public void apply(IDocument document) {
		try {
			boolean insertCompletion = PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.CODEASSIST_INSERT_COMPLETION);
			if (!insertCompletion) { //need to override the text after the cursor
				removeTrailingCharacters(document);
			}
			document.replace(replacementOffset, replacementLength + selectionLength, getReplacementString());
		} catch (BadLocationException e) {
			// ignore
		}
	}

	protected void removeTrailingCharacters(IDocument document) {
		try {
			int position = replacementOffset + replacementLength + selectionLength;
			IRegion line = document.getLineInformationOfOffset(position);
			int end = line.getOffset() + line.getLength();
			while (position < end) {
				char ch = document.getChar(position);
				if (isDelimeter(ch, phpDelimiters)) {
					// solve bug #139028 - avoid case of double suffix, in case there already is one.
					if (suffix.startsWith(String.valueOf(ch))) {
						suffix = ""; //$NON-NLS-1$
					}
					break;
				}
				replacementLength++;
				position++;
			}
		} catch (BadLocationException e) {
			// ignore
		}
	}

	private static final boolean isDelimeter(char ch, char[] delimeters) {
		for (char element : delimeters) {
			if (ch == element) {
				return true;
			}
		}
		return false;
	}

	public void apply(ITextViewer viewer, char trigger, int stateMask, int offset) {
		IDocument document = viewer.getDocument();
		apply(document);
		int replacementPosition = replacementOffset + getReplacementString().length() - suffix.length() + caretOffsetInSuffix;
		viewer.getSelectionProvider().setSelection(new TextSelection(document, replacementPosition, 0));
	}

	public void selected(ITextViewer viewer, boolean smartToggle) {
	}

	public void unselected(ITextViewer viewer) {
	}

	public boolean validate(IDocument document, int offset, DocumentEvent event) {
		String enteredText = ""; //$NON-NLS-1$
		try {
			enteredText = document.get(replacementOffset, offset - replacementOffset);
		} catch (BadLocationException e) {
			// ignore
		}
		boolean valid = codeData.getName().toLowerCase().startsWith(enteredText.toLowerCase());
		if (valid) {
			this.replacementLength = enteredText.length();
		}
		return valid;
	}

	public boolean isAutoInsertable() {
		return true;
	}

	public Point getSelection(IDocument document) {
		return null;
	}

	public String getAdditionalProposalInfo() {
		if (projectModel != null) {
			return PHPCodeDataHTMLDescriptionUtilities.getHTMLHyperlinkDescriptionText(codeData, projectModel);
		}
		return null;
	}

	public IContextInformation getContextInformation() {
		if (!showTypeHints) {
			return null;
		}
		if (contextInfo == null) {
			contextRendererVisitor.init(projectModel, codeData);
			String contextInfoString = contextRendererVisitor.getDisplayString().trim();
			if (contextInfoString.length() > 0) {
				contextInfo = new ContextInformation(null, contextRendererVisitor.getDisplayString());
			}
		}
		return contextInfo;
	}

	public String getDisplayString() {
		initialize();
		return displayText;
	}

	public Image getImage() {
		initialize();
		return displayImage;
	}

	private void initialize() {
		if (displayText != null) {
			return;
		}
		rendererVisitor.init(codeData);
		displayText = rendererVisitor.getDisplayString();
		displayImage = rendererVisitor.getImage();
	}

	public IInformationControlCreator getInformationControlCreator() {
		return null;
	}

	public CharSequence getPrefixCompletionText(IDocument document, int completionOffset) {
		return prefix;
	}

	public int getPrefixCompletionStart(IDocument document, int completionOffset) {
		return replacementOffset;
	}

	public CodeData getCodeData() {
		return codeData;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("proposal: ").append(displayText); //$NON-NLS-1$
		buffer.append(" name: ").append(codeData != null ? codeData.getName() : "empty"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(" type: ").append(codeData != null ? codeData.getClass().toString() : "empty"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(" prefix: ").append(prefix); //$NON-NLS-1$
		buffer.append(" suffix: ").append(suffix); //$NON-NLS-1$
		return buffer.toString();
	}
}