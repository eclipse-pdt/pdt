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
import org.eclipse.dltk.compiler.problem.IProblemIdentifierFactory;

public class ProblemIdentifierFactory implements IProblemIdentifierFactory {

	public IProblemIdentifier valueOf(String localName)
			throws IllegalArgumentException {
		return ProblemIdentifier.valueOf(localName);
	}

	public IProblemIdentifier[] values() {
		return ProblemIdentifier.values();
	}
}
