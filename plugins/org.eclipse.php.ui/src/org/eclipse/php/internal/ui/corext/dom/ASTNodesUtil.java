package org.eclipse.php.internal.ui.corext.dom;

import org.eclipse.php.internal.core.ast.nodes.ASTNode;

/**
 * Utilities used for Ast nodes
 * @author eden
 *
 */
public class ASTNodesUtil {


	public static ASTNode getParent(ASTNode node, Class parentClass) {
		do {
			node= node.getParent();
		} while (node != null && !parentClass.isInstance(node));
		return node;
	}
	
	public static ASTNode getParent(ASTNode node, int nodeType) {
		do {
			node= node.getParent();
		} while (node != null && node.getType() != nodeType);
		return node;
	}
	
}
