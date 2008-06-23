/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.compiler.ISourceElementRequestor;

public class PHPSourceElementRequestorExtension extends ASTVisitor {

	private String filename;
	private char[] contents;
	protected ISourceElementRequestor fRequestor;

	public PHPSourceElementRequestorExtension() {
	}

	public void setRequestor(ISourceElementRequestor requesor) {
		fRequestor = requesor;
	}
	
	public void setContents(char[] contents) {
		this.contents = contents;
	}

	public char[] getContents() {
		return contents;
	}

	public void setFilename(String file) {
		this.filename = file;
	}

	public String getFilename() {
		return filename;
	}
}
