/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.core.tests.dom_ast.rewrite;

import org.eclipse.php.core.PHPVersion;

public class NodeDeletionTestsPHP56 extends NodeDeletionTests {

	@Override
	protected PHPVersion getPHPVersion() {
		return PHPVersion.PHP5_5;
	}
}
