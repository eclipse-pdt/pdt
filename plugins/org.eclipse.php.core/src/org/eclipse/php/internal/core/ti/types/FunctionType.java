package org.eclipse.php.internal.core.ti.types;

import org.eclipse.php.internal.core.ast.nodes.BodyDeclaration;
import org.eclipse.php.internal.core.ast.nodes.FunctionDeclaration;

public class FunctionType implements IFunctionType {
	
	private FunctionDeclaration functionDecl;
	private BodyDeclaration bodyDecl;
	
	public FunctionType(FunctionDeclaration functionDecl, BodyDeclaration bodyDecl) {
		if (functionDecl == null || bodyDecl == null) {
			throw new NullPointerException();
		}
		this.functionDecl = functionDecl;
		this.bodyDecl = bodyDecl;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof FunctionType)) {
			return false;
		}
		FunctionType other = (FunctionType)obj;
		return other.functionDecl == functionDecl && other.bodyDecl == bodyDecl;
	}

	public int hashCode() {
		return functionDecl.hashCode() + 127 * bodyDecl.hashCode();
	}

	public String toString() {
		return "Function " + functionDecl.getFunctionName().getName();
	}
}
