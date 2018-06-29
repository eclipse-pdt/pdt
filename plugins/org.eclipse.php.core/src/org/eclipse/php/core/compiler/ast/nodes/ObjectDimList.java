/*******************************************************************************
 * Copyright (c) 2015 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.expressions.Expression;

/**
 * Helper class to collect VariableName and ArrayAccess for php < 7
 * 
 * @author zulus
 */
public class ObjectDimList {

	public final Expression variable;

	public DimList list;

	public int refCount = 0;

	public ObjectDimList(Expression variable) {
		this.variable = variable;
	}

	public void add(Expression index, int type, int right) {
		if (list == null) {
			list = new DimList();
		}

		list.add(index, type, right);
	}
}
