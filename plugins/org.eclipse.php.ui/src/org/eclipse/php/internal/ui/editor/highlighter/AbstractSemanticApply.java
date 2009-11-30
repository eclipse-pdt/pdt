/*******************************************************************************
 * Copyright (c) 2006, 2009 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   William Candillon {wcandillon@gmail.com} - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighter;

import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.visitor.ApplyAll;

public abstract class AbstractSemanticApply extends ApplyAll {

	@Override
	protected boolean apply(ASTNode node) {
		return true;
	}
}
