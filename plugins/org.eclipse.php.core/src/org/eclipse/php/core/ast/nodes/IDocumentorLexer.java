/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.ast.nodes;

import org.eclipse.php.core.compiler.ast.nodes.PHPDocBlock;

public interface IDocumentorLexer {

	public PHPDocBlock parse();

	public void reset(java.io.Reader reader, char[] buffer, int[] parameters);

	public int[] getParameters();

	public char[] getBuffer();
}