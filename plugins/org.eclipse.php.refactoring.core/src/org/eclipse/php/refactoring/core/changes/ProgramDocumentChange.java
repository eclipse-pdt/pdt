/*******************************************************************************
 * Copyright (c) 2008, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.changes;

import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.DocumentChange;
import org.eclipse.php.core.ast.nodes.Program;

public class ProgramDocumentChange extends DocumentChange {

	private final Program program;

	public ProgramDocumentChange(String name, IDocument document,
			Program program) {
		super(name, document);
		this.program = program;
	}

	public Program getProgram() {
		return program;
	}

	/**
	 * Adapt to Program
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Change#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		if (adapter == Program.class) {
			return program;
		}
		return super.getAdapter(adapter);
	}

}
