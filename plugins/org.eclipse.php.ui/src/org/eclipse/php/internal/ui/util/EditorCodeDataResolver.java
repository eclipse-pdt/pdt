/**
 * Copyright (c) 2007 Zend Technologies
 * 
 */
package org.eclipse.php.internal.ui.util;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionCollection;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.eclipse.wst.sse.ui.internal.contentassist.ContentAssistUtils;

public class EditorCodeDataResolver extends ACodeDataResolver {

	private static final char SINGLE_QUOTE = '\'';

	protected ITextViewer fileSource;
	private IStructuredDocumentRegion documentRegion;
	private ITextRegion phpToken;
	private String phpTokenType;

	public void initialize(ITextViewer viewer, int offset) {
		super.initialize(viewer, offset);
	}

	protected boolean isSingleQuoted() {
		return PHPPartitionTypes.isPHPQuotesState(getPhpTokenType()) && getDocumentRegion().getText(getPhpToken()).charAt(0) == SINGLE_QUOTE;
	}

	protected boolean isCommented() {
		return PHPPartitionTypes.isPHPCommentState(getPhpTokenType());
	}

	private IStructuredDocumentRegion getDocumentRegion() {
		if (documentRegion != null)
			return documentRegion;
		documentRegion = ContentAssistUtils.getStructuredDocumentRegion((StructuredTextViewer) fileSource, offset);
		if (documentRegion == null)
			throw new CodeUnresolvedException("Unable to resolve document region");
		return documentRegion;
	}

	private ITextRegion getPhpToken() {
		if (phpToken != null)
			return phpToken;
		ITextRegion textRegion = getDocumentRegion().getRegionAtCharacterOffset(offset);
		if (textRegion == null)
			throw new CodeUnresolvedException("Unable to resolve php region");
		ITextRegionCollection phpRegionContainer = documentRegion;
		if (textRegion instanceof ITextRegionContainer) {
			phpRegionContainer = (ITextRegionContainer) textRegion;
			textRegion = phpRegionContainer.getRegionAtCharacterOffset(offset);
			if (textRegion == null)
				throw new CodeUnresolvedException("Unable to resolve contained php region");
		}
		int phpRegionContainerOffset = phpRegionContainer.getStartOffset();

		if (textRegion.getType() != PHPRegionContext.PHP_CONTENT)
			throw new CodeUnresolvedException("Incompatible php region context");
		try {
			phpToken = ((PhpScriptRegion) textRegion).getPhpToken(offset - phpRegionContainerOffset - textRegion.getStart());
		} catch (BadLocationException e) {
			throw new CodeUnresolvedException("Unable to resolve php token");
		}
		return textRegion;
	}

	private String getPhpTokenType() {
		if (phpTokenType != null)
			return phpTokenType;
		phpTokenType = getPhpToken().getType();
		if (phpTokenType == null)
			throw new CodeUnresolvedException("Unable to resolve region type");
		return phpTokenType;
	}
}