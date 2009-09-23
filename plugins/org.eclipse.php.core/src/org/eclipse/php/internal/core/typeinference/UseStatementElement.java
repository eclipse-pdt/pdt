/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;

/**
 * This class represents USE statement as a "fake" model element.
 * 
 * @author michael
 * 
 */
public class UseStatementElement extends FakeField {

	private UsePart usePart;

	public UseStatementElement(ModelElement parent, UsePart usePart) {
		super(parent, usePart.getNamespace().getFullyQualifiedName(), usePart
				.getNamespace().sourceStart(), usePart.getNamespace()
				.sourceEnd()
				- usePart.getNamespace().sourceStart());
		this.usePart = usePart;
	}

	public UsePart getUsePart() {
		return usePart;
	}

	public boolean exists() {
		return true;
	}
}
