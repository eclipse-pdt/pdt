package org.eclipse.php.internal.core.compiler.ast.parser;

import java.io.Reader;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.php.internal.core.compiler.ast.nodes.Program;
import org.eclipse.php.internal.core.phpModel.javacup.runtime.Symbol;

public class PHPSourceParser5 extends AbstractPHPSourceParser {

	public ModuleDeclaration parse(Reader in, IProblemReporter reporter) throws Exception {
		PhpAstLexer5 lexer = new PhpAstLexer5(in);
		PhpAstParser5 parser = new PhpAstParser5();
		parser.setScanner(lexer);

		Symbol symbol = parser.parse();
		Program program = (Program) symbol.value;

		return program;
	}
}
