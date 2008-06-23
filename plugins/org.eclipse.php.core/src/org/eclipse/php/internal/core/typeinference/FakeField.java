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
package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceField;
import org.eclipse.dltk.internal.core.SourceRange;

public class FakeField extends SourceField {
	private int offset;
	private int length;

	public FakeField(ModelElement parent, String name, int offset, int length) {
		super(parent, name);
		this.offset = offset;
		this.length = length;
	}

	public ISourceRange getNameRange() throws ModelException {
		return new SourceRange(offset, length);
	}

	public ISourceRange getSourceRange() throws ModelException {
		return new SourceRange(offset, length);
	}

	public boolean equals(Object o) {
		if (super.equals(o)) {
			FakeField other = (FakeField)o;
			return other.offset == offset && other.length == length;
		}
		return false;
	}
}