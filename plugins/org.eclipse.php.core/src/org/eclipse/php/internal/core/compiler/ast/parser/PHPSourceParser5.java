package org.eclipse.php.internal.core.compiler.ast.parser;

import java.io.Reader;

import java_cup.runtime.Symbol;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblemReporter;

public class PHPSourceParser5 extends AbstractPHPSourceParser {

	public ModuleDeclaration parse(Reader in, IProblemReporter reporter) throws Exception {
		PhpAstLexer5 lexer = new PhpAstLexer5(in);
		PhpAstParser5 parser = new PhpAstParser5(lexer);

		Symbol symbol = parser.parse();
		ModuleDeclaration program = (ModuleDeclaration) symbol.value;

		return program;
	}
}
