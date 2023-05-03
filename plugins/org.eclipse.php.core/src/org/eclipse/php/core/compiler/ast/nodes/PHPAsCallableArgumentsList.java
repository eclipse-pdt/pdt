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

import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

public class PHPAsCallableArgumentsList extends PHPCallArgumentsList {

	public PHPAsCallableArgumentsList() {
	}

	public PHPAsCallableArgumentsList(int start, int end) {
		super(start, end);
	}

	@Override
	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
