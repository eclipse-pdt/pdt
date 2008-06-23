/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.nodes;

public interface IDocumentorLexer {
	
	public Object parse ();
	
	public void reset(java.io.Reader  reader, char[] buffer, int[] parameters);
	
	public int[] getParamenters();
	
    public char[] getBuffer();
}