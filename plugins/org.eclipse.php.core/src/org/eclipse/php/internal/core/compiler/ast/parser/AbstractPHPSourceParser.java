package org.eclipse.php.internal.core.compiler.ast.parser;

import java.io.CharArrayReader;
import java.io.Reader;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.parser.AbstractSourceParser;
import org.eclipse.dltk.ast.parser.ISourceParser;
import org.eclipse.dltk.compiler.problem.IProblemReporter;

public abstract class AbstractPHPSourceParser extends AbstractSourceParser implements ISourceParser {
	
	private String fileName;
	
	public AbstractPHPSourceParser(String fileName) {
		this.fileName = fileName;
	}

	public AbstractPHPSourceParser() {
		this(null);
	}

	public ModuleDeclaration parse(char[] fileName, char[] source, IProblemReporter reporter) {
		try {
			return parse(new CharArrayReader(source), reporter);

		} catch (Exception e) {
			e.printStackTrace();
			// XXX: add recovery
			return new ModuleDeclaration(source.length);
		}
	}

	public abstract ModuleDeclaration parse(Reader in, IProblemReporter reporter) throws Exception;

	protected ModuleDeclaration parse(AbstractASTParser parser) throws Exception {
		parser.setFileName(fileName);
		parser.parse();
		return parser.getModuleDeclaration();
	}

}
