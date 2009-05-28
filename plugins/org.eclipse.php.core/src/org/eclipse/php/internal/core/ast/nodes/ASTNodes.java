/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.nodes;

import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;

;


/**
 * Utilities used for Ast nodes
 * @author Eden
 *
 */
public class ASTNodes {


	public static ASTNode getParent(ASTNode node, Class parentClass) {
		if (node == null) 
			return null;
		
		do {
			node= node.getParent();
		} while (node != null && !parentClass.isInstance(node));
		return node;
	}
	
	public static ASTNode getParent(ASTNode node, int nodeType) {
		if (node == null) 
			return null;
		
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
	
	/**
	 * Aggregates the strings for a given node 
	 * @param node
	 * @return the aggregated strings for a given node 
	 */
	public static String getScalars(ASTNode node) {
		final StringBuilder builder = new StringBuilder();
		node.accept(new AbstractVisitor() {

			@Override
			public boolean visit(Scalar scalar) {
				builder.append(scalar.getStringValue());
				return true;
			}
				
		});
		
		return builder.toString();
	}
	
	
}
