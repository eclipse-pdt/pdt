package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.compiler.ISourceElementRequestor;
import org.eclipse.dltk.compiler.SourceElementRequestVisitor;

public class PHPSourceElementRequestor extends SourceElementRequestVisitor {

	public PHPSourceElementRequestor(ISourceElementRequestor requestor) {
		super(requestor);
	}
}
