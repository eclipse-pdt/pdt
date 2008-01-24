package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.ast.parser.ISourceParser;
import org.eclipse.dltk.ast.parser.ISourceParserFactory;

public class PHPSourceParserFactory implements ISourceParserFactory {

	public ISourceParser createSourceParser() {
		// XXX: Need to select relevant parser (PHP4 or PHP5):
		return new PHPSourceParser5();
	}

}
