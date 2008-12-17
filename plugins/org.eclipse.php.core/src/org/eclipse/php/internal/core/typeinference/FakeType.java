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

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.dltk.internal.core.SourceType;

public class FakeType extends SourceType {

	private int flags = Modifiers.AccPublic;
	private int offset;
	private int length;
	private boolean hasSpecialOffsets = false;
	private int nameOffset;
	private int nameLength;

	public FakeType(ModelElement sourceModule, String name, int flags) {
		super(sourceModule, name);
		this.flags = flags;
	}

	public FakeType(ModelElement parent, String name, int flags, int offset, int length, int nameOffset, int nameLength) {
		super(parent, name);
		this.flags = flags;
		this.offset = offset;
		this.length = length;
		this.nameOffset = nameOffset;
		this.nameLength = nameLength;
		hasSpecialOffsets = true;
	}

	public ISourceRange getNameRange() throws ModelException {
		if (hasSpecialOffsets)
			return new SourceRange(nameOffset, nameLength);
		return super.getNameRange();
	}

	public ISourceRange getSourceRange() throws ModelException {
		if (hasSpecialOffsets)
			return new SourceRange(offset, length);
		return super.getSourceRange();
	}

	public IScriptProject getScriptProject() {
		return parent.getScriptProject();
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}
}
