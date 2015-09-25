/*******************************************************************************
 * Copyright (c) 2015 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.language;

import java.util.Collection;

public class PHPVariablesInitializerPHP_5 implements IPHPVariablesInitializer {

	@Override
	public void initializeSuperGlobals(Collection<String> superGlobals) {
		superGlobals.add("$_COOKIE"); //$NON-NLS-1$
		superGlobals.add("$_ENV"); //$NON-NLS-1$
		superGlobals.add("$_FILES"); //$NON-NLS-1$
		superGlobals.add("$_GET"); //$NON-NLS-1$
		superGlobals.add("$_POST"); //$NON-NLS-1$
		superGlobals.add("$_REQUEST"); //$NON-NLS-1$
		superGlobals.add("$_SERVER"); //$NON-NLS-1$
		superGlobals.add("$_SESSION"); //$NON-NLS-1$
		superGlobals.add("$GLOBALS"); //$NON-NLS-1$
	}

	@Override
	public void initializeGlobals(Collection<String> globals) {
		globals.add("$HTTP_COOKIE_VARS"); //$NON-NLS-1$
		globals.add("$HTTP_ENV_VARS"); //$NON-NLS-1$
		globals.add("$HTTP_GET_VARS"); //$NON-NLS-1$
		globals.add("$HTTP_POST_FILES"); //$NON-NLS-1$
		globals.add("$HTTP_POST_VARS"); //$NON-NLS-1$
		globals.add("$HTTP_SERVER_VARS"); //$NON-NLS-1$
		globals.add("$HTTP_SESSION_VARS"); //$NON-NLS-1$
	}

}
