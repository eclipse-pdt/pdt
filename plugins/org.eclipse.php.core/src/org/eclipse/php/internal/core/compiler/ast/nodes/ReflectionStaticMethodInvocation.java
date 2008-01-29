package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.TypeReference;

public class ReflectionStaticMethodInvocation extends ReflectionCallExpression {

	public ReflectionStaticMethodInvocation(int start, int end, TypeReference receiver, Expression name, CallArgumentsList args) {
		super(start, end, receiver, name, args);
	}

	public int getKind() {
		return ASTNodeKinds.REFLECTION_STATIC_METHOD_INVOCATION;
	}
}
