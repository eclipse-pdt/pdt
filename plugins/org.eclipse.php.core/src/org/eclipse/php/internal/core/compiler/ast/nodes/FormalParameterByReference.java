package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;

public class FormalParameterByReference extends FormalParameter {

	public FormalParameterByReference(int start, int end, SimpleReference type, VariableReference parameterName, Expression defaultValue) {
		super(start, end, type, parameterName, defaultValue);
	}

	public FormalParameterByReference(int start, int end, SimpleReference type, VariableReference parameterName, boolean isMandatory) {
		super(start, end, type, parameterName, isMandatory);
	}

	public FormalParameterByReference(int start, int end, SimpleReference type, VariableReference parameterName) {
		super(start, end, type, parameterName);
	}

	public int getKind() {
		return ASTNodeKinds.FORMAL_PARAMETER_BYREF;
	}
}
