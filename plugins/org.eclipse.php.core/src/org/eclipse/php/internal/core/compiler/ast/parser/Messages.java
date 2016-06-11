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
package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.core.compiler.ast.parser.messages"; //$NON-NLS-1$
	public static String AbstractASTParser_0;
	public static String AbstractASTParser_1;
	public static String AbstractASTParser_4;
	public static String AbstractASTParser_5;
	public static String AbstractASTParser_6;
	public static String AbstractASTParser_7;
	public static String AbstractASTParser_AbstractAsFinalError;
	public static String AbstractASTParser_AbstractPropertyError;
	public static String AbstractASTParser_FinalPropertyError;
	public static String AbstractASTParser_MultipleAbstractModifierError;
	public static String AbstractASTParser_MultipleAccessModifiersError;
	public static String AbstractASTParser_MultipleFinalModifierError;
	public static String AbstractASTParser_MultipleStaticModifiersError;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
