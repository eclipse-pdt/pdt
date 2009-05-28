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
package org.eclipse.php.internal.core.codeassist;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.dltk.internal.core.SourceType;

/**
 * Fake group type is used in groupped completion proposals.
 * It contains a trailing '*' character meaning that this proposal is actually a group of types.
 * 
 * @author michael
 */
public class FakeGroupType extends SourceType {

	public FakeGroupType(ModelElement parent, String name) {
		super(parent, name);
	}

	public int getFlags() throws ModelException {
		return Modifiers.AccPublic;
	}

	public ISourceRange getSourceRange() throws ModelException {
		return new SourceRange(0, 0);
	}

	public boolean exists() {
		return false;
	}

	public IField[] getFields() throws ModelException {
		return new IField[0];
	}

	public IMethod[] getMethods() throws ModelException {
		return new IMethod[0];
	}

	public IType[] getTypes() throws ModelException {
		return new IType[0];
	}
}
