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
package org.eclipse.php.composer.test;

import junit.framework.TestCase;

import org.eclipse.php.composer.core.util.StringUtil;
import org.junit.Test;

public class StringUtilTest extends TestCase {
	
	@Test
	public void testLinkReplacement() {
		
		String input = "dfoo bar ofoijsdf aha <http://foobar.com> soidjfoija aoosjd f aha <http://google.com> asdf oaisjdofjais df";
		String expected = "dfoo bar ofoijsdf aha <a>http://foobar.com</a> soidjfoija aoosjd f aha <a>http://google.com</a> asdf oaisjdofjais df";
		assertEquals(expected, StringUtil.replaceLinksInComposerMessage(input));
		

		input = "dfoo bar ofoijsdf aha soidjfoija aoosjd f aha  asdf oaisjdofjais df";
		assertEquals(input, StringUtil.replaceLinksInComposerMessage(input));

		input = "dfoo bar ofoijsdf aha <=4 soidjfoija aoosjd f aha <http://google.com> asdf oaisjdofjais df";
		expected = "dfoo bar ofoijsdf aha <=4 soidjfoija aoosjd f aha <a>http://google.com</a> asdf oaisjdofjais df";
		assertEquals(expected, StringUtil.replaceLinksInComposerMessage(input));
		
		
	}
}
