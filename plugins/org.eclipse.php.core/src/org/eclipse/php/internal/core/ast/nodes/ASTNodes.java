package org.eclipse.php.internal.core.ast.nodes;

;


/**
 * Utilities used for Ast nodes
 * @author Eden
 *
 */
public class ASTNodes {


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
	
	/** 
	 * @param node
	 * @return whether the given node is the only statement of a control statement
	 */
	public static boolean isControlStatement(ASTNode node) {
		assert node != null;		
		int type = node.getType();
		
		return  (type == ASTNode.IF_STATEMENT
		|| type == ASTNode.FOR_STATEMENT
		|| type == ASTNode.FOR_EACH_STATEMENT
		|| type == ASTNode.WHILE_STATEMENT
		|| type == ASTNode.DO_STATEMENT
		);
	}
	
	
}
