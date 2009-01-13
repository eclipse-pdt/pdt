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
package org.eclipse.php.internal.core.compiler.ast.parser.php53;

import java.io.InputStream;

public class PhpCompilerAstLexer extends org.eclipse.php.internal.core.compiler.ast.parser.php5.PhpCompilerAstLexer {

	public PhpCompilerAstLexer(InputStream in) {
		super(in);
	}
	
	public PhpCompilerAstLexer(java.io.Reader in) {
		super(in);
	}
}
