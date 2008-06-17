package org.eclipse.php.internal.core.compiler.ast.parser.php5;

import java.io.Reader;


import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.php.internal.core.compiler.ast.parser.AbstractPHPSourceParser;

public class Php5SourceParser extends AbstractPHPSourceParser {

	public ModuleDeclaration parse(Reader in, IProblemReporter reporter) throws Exception {
		Php5AstLexer lexer = new Php5AstLexer(in);
		Php5AstParser parser = new Php5AstParser(lexer);

		return parse(parser);
	}
}
