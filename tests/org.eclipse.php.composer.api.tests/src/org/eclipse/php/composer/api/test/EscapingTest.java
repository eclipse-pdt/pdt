/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
