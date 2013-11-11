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
package org.eclipse.php.core.tests.dom_ast.rewrite;

import junit.framework.TestSuite;

import org.eclipse.php.internal.core.PHPVersion;

/**
 * AST rewrite test which tests the ASTRewriteAnalyzer implementation.
 * 
 * @author shalom TODO finally rewrite
 */
public class ASTRewriteTestsPHP55 extends ASTRewriteTestsPHP54 {

	public ASTRewriteTestsPHP55(String name) {
		super(name);
	}

	public static TestSuite suite() {
		return new TestSuite(new Class[] { ASTRewriteTestsPHP55.class,
				NodeDeletionTestsPHP55.class, },
				ASTRewriteTestsPHP55.class.getName());
	}

	@Override
	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP5_5;
	}

}
