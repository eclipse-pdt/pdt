package org.eclipse.php.internal.core.compiler.ast.parser;

import java.io.Reader;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.php.internal.core.phpModel.javacup.runtime.Symbol;

public class PHPSourceParser4 extends AbstractPHPSourceParser {

	public ModuleDeclaration parse(Reader in, IProblemReporter reporter) throws Exception {
		PhpAstLexer4 lexer = new PhpAstLexer4(in);
		PhpAstParser4 parser = new PhpAstParser4();
		parser.setScanner(lexer);

		Symbol symbol = parser.parse();
		ModuleDeclaration program = (ModuleDeclaration) symbol.value;

		return program;
	}
}
