/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.test;

import junit.framework.TestCase;

import org.junit.Test;

import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.json.ParseException;

public class EscapingTest extends TestCase {

	@Test
	public void testScriptBackslashEscaping() {
		
		String json = "{\n" +"	\"autoload\" : {\n" + 
				"		\"psr-0\" : {\n" + 
				"			\"Dubture\\\\FFmpegBundle\" : \"\"\n" + 
				"		}\n" + 
				"	}\n"+ 
				"}";
		
		try {
			ComposerPackage pkg = new ComposerPackage(json);
			assertEquals(json, pkg.toJson());
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
		
	}
}
