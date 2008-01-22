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
package org.eclipse.php.internal.ui.corext.dom.fragments;

import org.eclipse.php.internal.core.ast.nodes.Expression;

/**
 * Represents a fragment (@see IASTFragment) for which the node
 * to which the fragment maps is an Expression.
 */
public interface IExpressionFragment extends IASTFragment {
	
	/** 
	 * Every IASTFragment maps to an ASTNode, although this mapping may
	 * not be straightforward, and more than one fragment may map to the
	 * same node.
	 * An IExpressionFragment maps, specifically, to an Expression.
	 * 
	 * @return Expression	The node to which this fragment maps.
	 */
	public Expression getAssociatedExpression();

}
