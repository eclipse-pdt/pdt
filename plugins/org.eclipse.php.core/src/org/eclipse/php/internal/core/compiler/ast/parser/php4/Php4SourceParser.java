package org.eclipse.php.internal.core.compiler.ast.parser.php4;

import java.io.Reader;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.php.internal.core.compiler.ast.parser.AbstractPHPSourceParser;

public class Php4SourceParser extends AbstractPHPSourceParser {
	
	public Php4SourceParser() {
		super();
	}

	public Php4SourceParser(String fileName) {
		super(fileName);
	}

	public ModuleDeclaration parse(Reader in, IProblemReporter reporter) throws Exception {
		Php4AstLexer lexer = new Php4AstLexer(in);
		Php4AstParser parser = new Php4AstParser(lexer);
		parser.setProblemReporter(reporter);

		return parse(parser);
	}
}
