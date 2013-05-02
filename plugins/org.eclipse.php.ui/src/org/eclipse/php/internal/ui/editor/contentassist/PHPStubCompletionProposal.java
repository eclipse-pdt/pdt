package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.utils.TextUtils;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.swt.graphics.Image;

public class PHPStubCompletionProposal extends PHPCompletionProposal implements
		IPHPCompletionProposalExtension {
	private CompletionProposal typeProposal;
	private IDocument document;

	public PHPStubCompletionProposal(String replacementString,
			int replacementOffset, int replacementLength, Image image,
			String displayString, int relevance,
			CompletionProposal typeProposal, IDocument document) {
		super(replacementString, replacementOffset, replacementLength, image,
				displayString, relevance);
		this.typeProposal = typeProposal;
		this.document = document;
	}

	private boolean fReplacementStringComputed = false;

	public String getReplacementString() {
		if (!fReplacementStringComputed)
			setReplacementString(computeReplacementString());
		return super.getReplacementString();
	}

	private String computeReplacementString() {
		fReplacementStringComputed = true;
		// IType type = (IType) typeProposal.getModelElement();
		// type.getElementName();
		String result = "class " //$NON-NLS-1$
				+ typeProposal.getModelElement().getElementName()
				+ "{\r\n\t\r\n}"; //$NON-NLS-1$
		result = addComment(result);
		result = addIndent(result, typeProposal.getReplaceStart());

		return result;
	}

	private String addComment(String result) {
		// TODO Auto-generated method stub
		// return "/**\r\n *\r\n */\r\n" + result;
		return result;
	}

	private String addIndent(String result, int offset) {
		final String[] lines = TextUtils.splitLines(result);
		if (lines.length > 1) {
			final String delimeter = TextUtilities
					.getDefaultLineDelimiter(document);
			final String indent = calculateIndent(document, offset);
			final StringBuffer buffer = new StringBuffer(lines[0]);

			// Except first line
			for (int i = 1; i < lines.length; i++) {
				buffer.append(delimeter);
				buffer.append(indent);
				buffer.append(lines[i]);
			}
			return buffer.toString();
		}
		return result;
	}

	protected String calculateIndent(IDocument document, int offset) {
		try {
			final IRegion region = document.getLineInformationOfOffset(offset);
			String indent = document.get(region.getOffset(), offset
					- region.getOffset());
			int i = 0;
			while (i < indent.length() && isSpaceOrTab(indent.charAt(i))) {
				++i;
			}
			if (i > 0) {
				return indent.substring(0, i);
			}
		} catch (BadLocationException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}

		return ""; //$NON-NLS-1$
	}

	/**
	 * Tests if specified char is tab or space
	 * 
	 * @param ch
	 * @return
	 */
	private boolean isSpaceOrTab(char ch) {
		return ch == ' ' || ch == '\t';
	}

	public Object getExtraInfo() {
		return typeProposal.getExtraInfo();
	}

}
