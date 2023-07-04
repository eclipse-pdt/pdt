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

public class PHPVariablesInitializerPHP_5_4 extends PHPVariablesInitializerPHP_5 {

	@Override
	public void initializeSuperGlobals(Collection<String> superGlobals) {
		super.initializeSuperGlobals(superGlobals);
	}

	@Override
	public void initializeGlobals(Collection<String> globals) {
		globals.add("$argc"); //$NON-NLS-1$
		globals.add("$argv"); //$NON-NLS-1$
	}

}
