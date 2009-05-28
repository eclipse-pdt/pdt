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
import org.eclipse.dltk.internal.core.SourceMethod;
import org.eclipse.dltk.internal.core.SourceRange;

/**
 * Fake group method is used in grouped completion proposals.
 * It contains a trailing '*' character meaning that this proposal is actually a group of functions/methods.
 * 
 * @author michael
 */
public class FakeGroupMethod extends SourceMethod {
	
	public FakeGroupMethod(ModelElement parent, String name) {
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
}
