package org.eclipse.php.internal.core.format;

import org.eclipse.jface.text.IDocument;

public interface IFormatterCommonPrferences {
	public int getIndentationWrappedLineSize(IDocument document);

	public int getIndentationArrayInitSize(IDocument document);

	public int getIndentationSize(IDocument document);

	public char getIndentationChar(IDocument document);

	public int getTabSize(IDocument document);

	public boolean useTab(IDocument document);
}
