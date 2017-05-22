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

import org.eclipse.php.composer.core.validation.ValidationUtils;
import org.junit.Test;

import junit.framework.TestCase;

public class ValidationTest extends TestCase {

	@Test
	public void testNamespaceValidation() {

		assertTrue(ValidationUtils.validateNamespace("Foo"));
		assertTrue(ValidationUtils.validateNamespace("Foo\\Bar"));
		assertTrue(ValidationUtils.validateNamespace("Foo\\"));
		assertTrue(ValidationUtils.validateNamespace("\\Foo\\Bar"));
		assertTrue(ValidationUtils.validateNamespace("foo\\baR_aha"));
		assertTrue(ValidationUtils.validateNamespace("Foo_Bar\\Something"));
		assertTrue(ValidationUtils.validateNamespace("Fo2Bar"));

		assertFalse(ValidationUtils.validateNamespace("Fo baro"));
		assertFalse(ValidationUtils.validateNamespace("Fo.Bar"));
		assertFalse(ValidationUtils.validateNamespace("Fo  Bar"));
		assertFalse(ValidationUtils.validateNamespace("Fo--Bar\\Aha"));

	}
}
