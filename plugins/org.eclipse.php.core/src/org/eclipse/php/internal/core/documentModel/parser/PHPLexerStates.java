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
package org.eclipse.php.internal.core.documentModel.parser;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;


public class PHPLexerStates {

	final private static int BASE = 100;

	final public static int ST_PHP_IN_SCRIPTING = 100;
	final public static int ST_PHP_LINE_COMMENT = 101;

	private static final int SIZE = 100;
	private static int[] unifiedToVersioned4 = new int[SIZE];
	private static int[] versionedToUnified4 = new int[SIZE];
	private static int[] unifiedToVersioned5 = new int[SIZE];
	private static int[] versionedToUnified5 = new int[SIZE];

	static{
		buildHash();
	}
	public static int toUniversalState(IProject project, int state) {
		assert (state < BASE) : "State provided is not version dependent state";

		if (state == -1) {
			return -1;
		}
		final String phpVersion = PhpVersionProjectPropertyHandler.getVersion(project);
		if (phpVersion.equals(PHPCoreConstants.PHP5)) {
			return versionedToUnified5[state];
		}

		return versionedToUnified4[state];
	}

	public static int toSpecificVersionState(IProject project, int state) {
		assert (state >= BASE) : "State provided is not universal state";
		final String phpVersion = PhpVersionProjectPropertyHandler.getVersion(project);
		if (phpVersion.equals(PHPCoreConstants.PHP5)) {
			return unifiedToVersioned5[state - BASE];
		}

		return unifiedToVersioned4[state - BASE];
	}

	private static void buildHash(){
		for(int i = 0; i < SIZE; i++){
			unifiedToVersioned4[i] = -1;
			versionedToUnified4[i] = -1;
			unifiedToVersioned5[i] = -1;
			versionedToUnified5[i] = -1;

		}
		//PHP 5 
		unifiedToVersioned5[ST_PHP_IN_SCRIPTING - BASE] = PhpLexer5.ST_PHP_IN_SCRIPTING;
		unifiedToVersioned5[ST_PHP_LINE_COMMENT - BASE] = PhpLexer5.ST_PHP_LINE_COMMENT;

		versionedToUnified5[PhpLexer5.ST_PHP_IN_SCRIPTING] = ST_PHP_IN_SCRIPTING;
		versionedToUnified5[PhpLexer5.ST_PHP_LINE_COMMENT] = ST_PHP_LINE_COMMENT;

		//PHP 4
		unifiedToVersioned4[ST_PHP_IN_SCRIPTING - BASE] = PhpLexer4.ST_PHP_IN_SCRIPTING;
		unifiedToVersioned4[ST_PHP_LINE_COMMENT - BASE] = PhpLexer4.ST_PHP_LINE_COMMENT;
		
		versionedToUnified4[PhpLexer4.ST_PHP_IN_SCRIPTING] = ST_PHP_IN_SCRIPTING;
		versionedToUnified4[PhpLexer4.ST_PHP_LINE_COMMENT] = ST_PHP_LINE_COMMENT;
	}
}
