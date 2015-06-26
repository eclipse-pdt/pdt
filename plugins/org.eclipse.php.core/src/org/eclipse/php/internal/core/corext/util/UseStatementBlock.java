/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Yannick de Lange <yannickl88@gmail.com>
 *******************************************************************************/
package org.eclipse.php.internal.core.corext.util;

import java.util.List;
import java.util.Vector;

import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;

/**
 * @author Yannick de Lange <yannickl88@gmail.com>
 */
public class UseStatementBlock {
	public final int start, end, length;
	private final Vector<UseStatement> statements;

	public UseStatementBlock(int start, int end,
			Vector<UseStatement> statements) {
		this.start = start;
		this.end = end;
		this.length = end - start;
		this.statements = statements;
	}

	public List<UseStatement> getStatements() {
		return new Vector<UseStatement>(statements);
	}
}
