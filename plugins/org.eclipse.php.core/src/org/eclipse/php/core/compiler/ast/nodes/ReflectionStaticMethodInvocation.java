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
package org.eclipse.php.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.Expression;

public class ReflectionStaticMethodInvocation extends ReflectionCallExpression {

	public ReflectionStaticMethodInvocation(int start, int end, Expression receiver, Expression name,
			CallArgumentsList args) {
		super(start, end, receiver, name, args);
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.REFLECTION_STATIC_METHOD_INVOCATION;
	}
}
