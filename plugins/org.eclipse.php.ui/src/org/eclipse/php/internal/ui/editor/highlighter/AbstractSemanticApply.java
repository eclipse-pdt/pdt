/*******************************************************************************
 * Copyright (c) 2006, 2009 Zend Corporation and IBM Corporation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   William Candillon {wcandillon@gmail.com} - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighter;

import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.visitor.ApplyAll;

public abstract class AbstractSemanticApply extends ApplyAll {

	@Override
	protected boolean apply(ASTNode node) {
		return true;
	}
}
