package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.ast.ASTNode;

/**
 * This is a container for variable declaration
 * 
 * @author michael
 */
public class Declaration {

	private boolean global;
	private ASTNode declNode;

	public Declaration(boolean global, ASTNode declNode) {
		this.global = global;
		this.declNode = declNode;
	}

	/**
	 * Whether this declaration actually belongs to global scope - global
	 * $var was specified earlier.
	 */
	public boolean isGlobal() {
		return global;
	}

	/**
	 * Sets whether this declaration actually belongs to global scope -
	 * global $var was specified earlier.
	 */
	public void setGlobal(boolean global) {
		this.global = global;
	}

	/**
	 * Returns the declaration node itself.
	 */
	public ASTNode getNode() {
		return declNode;
	}

	/**
	 * Sets the declaration node itself.
	 */
	public void setNode(ASTNode declNode) {
		this.declNode = declNode;
	}
}