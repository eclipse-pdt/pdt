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
package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.IProblemIdentifierFactory;

public class PHPProblemIdentifierFactory implements IProblemIdentifierFactory {

	@Override
	public IProblemIdentifier valueOf(String localName) throws IllegalArgumentException {
		return PHPProblemIdentifier.valueOf(localName);
	}

	@Override
	public IProblemIdentifier[] values() {
		return PHPProblemIdentifier.values();
	}

}
