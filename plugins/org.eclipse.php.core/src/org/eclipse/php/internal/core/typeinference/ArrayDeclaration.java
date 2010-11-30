package org.eclipse.php.internal.core.typeinference;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayCreation;
import org.eclipse.php.internal.core.compiler.ast.nodes.Assignment;
import org.eclipse.php.internal.core.compiler.ast.nodes.GlobalStatement;

/**
 * This is a container for array declaration
 * 
 */
public class ArrayDeclaration extends Declaration {
	private LinkedList<Declaration> decls = new LinkedList<Declaration>();

	public ArrayDeclaration(boolean global, ASTNode declNode) {
		super(global, declNode);
	}

	/**
	 * Adds possible variable declaration
	 * 
	 * @param declNode
	 *            AST declaration statement node
	 */
	public void addDeclaration(ASTNode declNode) {
		// add new declaration
		if (declNode instanceof Assignment
				&& (((Assignment) declNode).getValue() instanceof ArrayCreation)) {
			decls.addLast(new ArrayDeclaration(
					declNode instanceof GlobalStatement, declNode));

		} else {
			decls.addLast(new Declaration(declNode instanceof GlobalStatement,
					declNode));
		}

	}

	public List<Declaration> getDeclarations() {
		return decls;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((decls == null) ? 0 : decls.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArrayDeclaration other = (ArrayDeclaration) obj;
		if (decls == null) {
			if (other.decls != null)
				return false;
		} else if (!decls.equals(other.decls))
			return false;
		return true;
	}

}
