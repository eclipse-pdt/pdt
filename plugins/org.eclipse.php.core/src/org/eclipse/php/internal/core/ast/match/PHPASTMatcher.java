/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.match;

import org.eclipse.core.runtime.Assert;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;


public class PHPASTMatcher extends ASTMatcher {

	//TODO - ??
//	public boolean match(SimpleName node, Object other) {
//		boolean isomorphic= super.match(node, other);
//		if (! isomorphic || !(other instanceof SimpleName))
//			return false;
//		SimpleName name= (SimpleName)other;
//		IBinding nodeBinding= node.resolveBinding();
//		IBinding otherBinding= name.resolveBinding();
//		if (nodeBinding == null) {
//			if (otherBinding != null) {
//				return false;
//			}
//		} else {
//			if (nodeBinding != otherBinding) {
//				return false;
//			}
//		}
//		if (node.resolveTypeBinding() != name.resolveTypeBinding())
//			return false;
//		return true;	
//	}
	
	public static boolean doNodesMatch(ASTNode one, ASTNode other) {
		Assert.isNotNull(one);
		Assert.isNotNull(other);
		
		return one.subtreeMatch(new PHPASTMatcher(), other);
	}
}
