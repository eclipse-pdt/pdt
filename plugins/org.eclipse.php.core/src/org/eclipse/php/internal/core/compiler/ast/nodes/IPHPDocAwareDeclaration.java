package org.eclipse.php.internal.core.compiler.ast.nodes;

public interface IPHPDocAwareDeclaration {

	/**
	 * Returns PHPDoc block relevant to this node
	 * @return PHPDoc block
	 */
	public PHPDocBlock getPHPDoc();
}
