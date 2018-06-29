/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.dom.fragments;

import org.eclipse.php.core.ast.nodes.ASTNode;

/**
 * @see org.eclipse.jdt.internal.corext.dom.fragments.IASTFragment
 * @see org.eclipse.jdt.internal.corext.dom.fragments.ASTFragmentFactory
 */
abstract class ASTFragment implements IASTFragment {

	/**
	 * Tries to create or find as many fragments as possible such that each fragment
	 * f matches this fragment and f.getNode() is <code>node</code>
	 */
	abstract IASTFragment[] getMatchingFragmentsWithNode(ASTNode node);
}
