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

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.dltk.internal.core.SourceType;

/**
 * This is a fake model element that can live independently from the DLTK model
 * manager.
 * 
 * @author michael
 */
public class FakeType extends SourceType {

	private int flags = Modifiers.AccPublic;
	private int offset;
	private int length;
	private boolean hasSpecialOffsets = false;
	private int nameOffset;
	private int nameLength;
	private String[] superClassNames;

	public FakeType(ModelElement sourceModule, String name, int flags,
			String[] superClassNames) {
		super(sourceModule, name);
		this.flags = flags;
		this.superClassNames = superClassNames;
	}

	public FakeType(ModelElement parent, String name, int flags,
			String[] superClassNames, int offset, int length, int nameOffset,
			int nameLength) {
		super(parent, name);
		this.flags = flags;
		this.offset = offset;
		this.length = length;
		this.nameOffset = nameOffset;
		this.nameLength = nameLength;
		this.superClassNames = superClassNames;
		hasSpecialOffsets = true;
	}

	public String[] getSuperClasses() throws ModelException {
		return superClassNames;
	}

	public ISourceRange getNameRange() throws ModelException {
		if (hasSpecialOffsets)
			return new SourceRange(nameOffset, nameLength);
		if (getElementInfo() != null)
			return super.getNameRange();
		return new SourceRange(0, 0);
	}

	public ISourceRange getSourceRange() throws ModelException {
		if (hasSpecialOffsets)
			return new SourceRange(offset, length);
		if (getElementInfo() != null)
			return super.getSourceRange();
		return new SourceRange(0, 0);
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
