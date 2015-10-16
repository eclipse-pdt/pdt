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
package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.IProblemIdentifierFactory;

public class PhpProblemIdentifierFactory implements IProblemIdentifierFactory {

	@Override
	public IProblemIdentifier valueOf(String localName) throws IllegalArgumentException {
		return PhpProblemIdentifier.valueOf(localName);
	}

	@Override
	public IProblemIdentifier[] values() {
		return PhpProblemIdentifier.values();
	}

}
