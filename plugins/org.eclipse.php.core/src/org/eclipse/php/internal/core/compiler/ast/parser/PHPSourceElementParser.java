package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.compiler.SourceElementRequestVisitor;
import org.eclipse.dltk.core.AbstractSourceElementParser;
import org.eclipse.dltk.core.ISourceModuleInfoCache.ISourceModuleInfo;
import org.eclipse.php.internal.core.project.PHPNature;

public class PHPSourceElementParser extends AbstractSourceElementParser {

	private char[] contents;
	private char[] filename;

	public void parseSourceModule(char[] contents, ISourceModuleInfo astCache, char[] filename) {
		this.contents = contents;
		this.filename = filename;

		super.parseSourceModule(contents, astCache, filename);
	}

	protected SourceElementRequestVisitor createVisitor() {
		return new PHPSourceElementRequestor(getRequestor(), contents, filename);
	}

	protected String getNatureId() {
		return PHPNature.ID;
	}
}
