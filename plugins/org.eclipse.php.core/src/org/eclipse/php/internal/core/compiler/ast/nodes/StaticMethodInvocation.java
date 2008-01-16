package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.SimpleReference;

/**
 * Represents static function invocation.
 * Holds the function invocation and the class name.
 * <pre>e.g.<pre>
 * MyClass::foo($a)
 */
public class StaticMethodInvocation extends CallExpression {

	public StaticMethodInvocation(ASTNode receiver, String name, CallArgumentsList args) {
		super(receiver, name, args);
	}

	public StaticMethodInvocation(int start, int end, ASTNode receiver, SimpleReference name, CallArgumentsList args) {
		super(start, end, receiver, name, args);
	}

	public StaticMethodInvocation(int start, int end, ASTNode receiver, String name, CallArgumentsList args) {
		super(start, end, receiver, name, args);
	}

	public int getKind() {
		return ASTNodeKinds.STATIC_METHOD_INVOCATION;
	}
}
