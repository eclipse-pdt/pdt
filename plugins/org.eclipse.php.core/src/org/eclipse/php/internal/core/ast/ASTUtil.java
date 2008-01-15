package org.eclipse.php.internal.core.ast;

import org.eclipse.php.internal.core.ast.nodes.ASTNode;

public class ASTUtil {

	/**
	 * For a given node, returns the outer node that surrounds it
	 * @param node AST node
	 * @return the enclosing node for the given node
	 */
	public static ASTNode getEnclosingBodyNode(ASTNode node) {
		do {
			switch (node.getType()) {
				case ASTNode.FUNCTION_DECLARATION:
					return node;

				case ASTNode.FIELD_DECLARATION:
					return null;

				case ASTNode.PROGRAM:
					return node;

			}
			node = node.getParent();
		} while (node != null);
		return null;
	}
}
