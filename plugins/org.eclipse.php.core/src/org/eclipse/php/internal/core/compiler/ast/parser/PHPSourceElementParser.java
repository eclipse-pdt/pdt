package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.SourceElementRequestVisitor;
import org.eclipse.dltk.core.AbstractSourceElementParser;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.ISourceModuleInfoCache.ISourceModuleInfo;
import org.eclipse.php.internal.core.project.PHPNature;

public class PHPSourceElementParser extends AbstractSourceElementParser {

	protected SourceElementRequestVisitor createVisitor() {
		return new PHPSourceElementRequestor(getRequestor());
	}

	protected String getNatureId() {
		return PHPNature.ID;
	}
	
	public void parseSourceModule(char[] contents, ISourceModuleInfo astCache, char[] filename) {
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(filename, contents, PHPNature.ID, getProblemReporter(), astCache);
		PHPSourceElementRequestor requestor = new PHPSourceElementRequestor(getRequestor());
		try {
			moduleDeclaration.traverse(requestor);
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}		
}
