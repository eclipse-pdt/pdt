/*******************************************************************************
 * Copyright (c) 2014 Dawid Pakuła
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dawid Pakuła - initial api and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.validation;

import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.IProblemIdentifierExtension;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;

public enum ProblemIdentifier implements IProblemIdentifier,
		IProblemIdentifierExtension {
	TASK;

	public String contributor() {
		return PHPCorePlugin.ID;
	}

	public String getMarkerType() {
		return PHPCoreConstants.PHP_MARKER_TYPE;
	}
}
