package org.eclipse.php.internal.core.index;

import org.eclipse.dltk.core.index2.IElementResolver;
import org.eclipse.dltk.core.index2.IIndexerParticipant;
import org.eclipse.dltk.core.index2.IIndexingParser;

/**
 * H2 Database indexer participant for PHP.
 * 
 * @author michael
 * 
 */
public class PhpIndexerParticipant implements IIndexerParticipant {

	private static final String QUALIFIER_SEP = "\\";
	private PhpElementResolver elementResolver;
	private PhpIndexingParser parser;

	public IElementResolver getElementResolver() {
		if (elementResolver == null) {
			elementResolver = new PhpElementResolver();
		}
		return elementResolver;
	}

	public IIndexingParser getIndexingParser() {
		if (parser == null) {
			parser = new PhpIndexingParser();
		}
		return parser;
	}

	public String getQualifierSeparator() {
		return QUALIFIER_SEP;
	}
}
