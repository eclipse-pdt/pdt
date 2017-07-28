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

		assertTrue(ValidationUtils.validateNamespace("Foo")); //$NON-NLS-1$
		assertTrue(ValidationUtils.validateNamespace("Foo\\Bar")); //$NON-NLS-1$
		assertTrue(ValidationUtils.validateNamespace("Foo\\")); //$NON-NLS-1$
		assertTrue(ValidationUtils.validateNamespace("\\Foo\\Bar")); //$NON-NLS-1$
		assertTrue(ValidationUtils.validateNamespace("foo\\baR_aha")); //$NON-NLS-1$
		assertTrue(ValidationUtils.validateNamespace("Foo_Bar\\Something")); //$NON-NLS-1$
		assertTrue(ValidationUtils.validateNamespace("Fo2Bar")); //$NON-NLS-1$

		assertFalse(ValidationUtils.validateNamespace("Fo baro")); //$NON-NLS-1$
		assertFalse(ValidationUtils.validateNamespace("Fo.Bar")); //$NON-NLS-1$
		assertFalse(ValidationUtils.validateNamespace("Fo  Bar")); //$NON-NLS-1$
		assertFalse(ValidationUtils.validateNamespace("Fo--Bar\\Aha")); //$NON-NLS-1$

	}
}
