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
package org.eclipse.php.core.ast.match;

import org.eclipse.core.runtime.Assert;
import org.eclipse.php.core.ast.nodes.ASTNode;

public class PHPASTMatcher extends ASTMatcher {

	public static boolean doNodesMatch(ASTNode one, ASTNode other) {
		Assert.isNotNull(one);
		Assert.isNotNull(other);

		return one.subtreeMatch(new PHPASTMatcher(), other);
	}
}
