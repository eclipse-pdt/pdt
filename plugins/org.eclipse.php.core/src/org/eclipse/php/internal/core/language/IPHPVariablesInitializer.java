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

public interface IPHPVariablesInitializer {
	public void initializeSuperGlobals(Collection<String> superGlobals);

	public void initializeGlobals(Collection<String> globals);
}
