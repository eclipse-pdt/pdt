package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.statements.Block;

/**
 * Represents a function declaration
 * <pre>e.g.<pre>
 * function foo() {}
 *
 * function &foo() {}
 *
 * function foo($a, int $b, $c = 5, int $d = 6) {}
 *
 * function foo(); -abstract function in class declaration
 */
public class PHPMethodDeclaration extends MethodDeclaration {

	private final boolean isReference;

	public PHPMethodDeclaration(int start, int end, int nameStart, int nameEnd, String functionName, List<FormalParameter> formalParameters, Block body, final boolean isReference) {
		super(functionName, nameStart, nameEnd, start, end);

		acceptArguments(formalParameters);
		acceptBody(body);

		this.isReference = isReference;
	}

	public boolean isReference() {
		return isReference;
	}
}
