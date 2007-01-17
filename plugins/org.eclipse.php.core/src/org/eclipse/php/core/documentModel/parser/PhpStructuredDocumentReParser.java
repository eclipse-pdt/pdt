package org.eclipse.php.core.documentModel.parser;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.Logger;
import org.eclipse.php.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.wst.sse.core.internal.provisional.events.RegionChangedEvent;
import org.eclipse.wst.sse.core.internal.provisional.events.StructuredDocumentEvent;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredTextReParser;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.parser.XMLStructuredDocumentReParser;

/**
 * Handles the php region when reparsing an XML/PHP structured document
 * @author Roy, 2006
 */
public class PhpStructuredDocumentReParser extends XMLStructuredDocumentReParser {

	public PhpStructuredDocumentReParser() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.core.internal.parser.XMLStructuredDocumentReParser#newInstance()
	 */
	public IStructuredTextReParser newInstance() {
		return new PhpStructuredDocumentReParser();
	}

	/**
	 * Adding the support to php comments
	 */
	protected StructuredDocumentEvent checkForComments() {
		StructuredDocumentEvent result = checkForCriticalKey("/*");
		if (result == null) {
			result = checkForCriticalKey("*/");
		}
		return result != null ? result : super.checkForComments();
	}

	/**
	 * This function was added in order to support asp tags in PHP (bug fix #150363)
	 */
	protected StructuredDocumentEvent checkForCrossStructuredDocumentRegionSyntax() {
		StructuredDocumentEvent result = super.checkForCrossStructuredDocumentRegionSyntax();
		if (result == null) {
			result = checkForCriticalKey("<%"); //$NON-NLS-1$
			if (result == null)
				result = checkForCriticalKey("%>"); //$NON-NLS-1$

		}
		return result;
	}

	/**
	 * This implementation updates the php tokens model after updating WST editor model
	 */
	public StructuredDocumentEvent reparse() {
		final StructuredDocumentEvent documentEvent = super.reparse();
		
		if (documentEvent instanceof RegionChangedEvent) {
			// safe cast
			RegionChangedEvent event = (RegionChangedEvent) documentEvent;
			final ITextRegion region = event.getRegion();
			final int startOffset = event.getStructuredDocumentRegion().getStartOffset();

			// if it is a php script region - reparse the php tokens according to the new text 
			if (region.getType() == PHPRegionContext.PHP_CONTENT) {

				try {
					PhpScriptRegion phpRegion = (PhpScriptRegion) region;
					final String newText = documentEvent.getDocument().get(startOffset + region.getStart(), region.getLength());
					phpRegion.reparse(newText);

				} catch (BadLocationException e) {
					Logger.logException(e);
				}
			}
		}

		return documentEvent;
	}
}
