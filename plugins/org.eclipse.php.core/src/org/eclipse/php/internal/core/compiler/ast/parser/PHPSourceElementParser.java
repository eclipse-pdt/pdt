package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.compiler.SourceElementRequestVisitor;
import org.eclipse.dltk.core.AbstractSourceElementParser;
import org.eclipse.php.internal.core.project.PHPNature;

public class PHPSourceElementParser extends AbstractSourceElementParser {

	protected SourceElementRequestVisitor createVisitor() {
		return new PHPSourceElementRequestor(getRequestor());
	}

	protected String getNatureId() {
		return PHPNature.ID;
	}
}
