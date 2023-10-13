/*******************************************************************************
 * Copyright (c) 2015 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.language;

import java.util.Collection;

public class PHPVariablesInitializerPHP_8_0 extends PHPVariablesInitializerPHP_5_4 {

	@Override
	public void initializeSuperGlobals(Collection<String> superGlobals) {
		super.initializeSuperGlobals(superGlobals);
		superGlobals.remove("$php_errormsg");
	}

	@Override
	public void initializeGlobals(Collection<String> globals) {
		super.initializeGlobals(globals);
	}

}
