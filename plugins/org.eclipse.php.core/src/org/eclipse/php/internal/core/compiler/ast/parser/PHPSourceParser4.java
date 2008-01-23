package org.eclipse.php.internal.core.compiler.ast.parser;

import java.io.Reader;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.php.internal.core.compiler.ast.nodes.Program;
import org.eclipse.php.internal.core.phpModel.javacup.runtime.Symbol;

public class PHPSourceParser4 extends AbstractPHPSourceParser {

	public ModuleDeclaration parse(Reader in, IProblemReporter reporter) {
		try {
			PhpAstLexer4 lexer = new PhpAstLexer4(in);
			PhpAstParser4 parser = new PhpAstParser4();
			parser.setScanner(lexer);

			Symbol symbol = parser.parse();
			Program program = (Program) symbol.value;

			return program;

		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

}
