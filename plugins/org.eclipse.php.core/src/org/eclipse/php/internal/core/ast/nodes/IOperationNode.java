/**
 * 
 */
package org.eclipse.php.internal.core.ast.nodes;

/**
 * An interface that should be implement by any node that defines an unary or binary operation.
 * 
 * @author shalom
 */
public interface IOperationNode {

	/**
	 * Returns the string representation of the operation (e.g. +, -, ~, ++, etc.).
	 * 
	 * @return The string representation of the operation
	 */
	public String getOperationString();

	/**
	 * Translate the given operation id to the string operation that it describes.
	 * 
	 * @param op The operation code.
	 * @return A string of the operation.
	 */
	public String getOperationString(int op);
}
