/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.mixin;

import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceField;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;

/**
 * Fake member that represents PHPDoc
 */
public class PHPDocField extends SourceField {

	public static final String NAME = "__phpdoc__"; //$NON-NLS-1$
	private PHPDocBlock docBlock;

	public PHPDocField(ModelElement parent, PHPDocBlock docBlock) {
		super(parent, NAME);

		assert docBlock != null;

		this.docBlock = docBlock;
	}

	public PHPDocBlock getDocBlock() {
		return docBlock;
	}

	public boolean equals(Object o) {
		if (super.equals(o)) {
			PHPDocField other = (PHPDocField)o;
			return docBlock.sourceStart() == other.docBlock.sourceStart() && docBlock.sourceEnd() == other.docBlock.sourceEnd();
		}
		return false;
	}
}
