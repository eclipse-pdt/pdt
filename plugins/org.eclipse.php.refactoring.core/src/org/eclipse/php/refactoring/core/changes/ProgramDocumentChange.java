/*******************************************************************************
 * Copyright (c) 2008, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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

	public ProgramDocumentChange(String name, IDocument document, Program program) {
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == Program.class) {
			return program;
		}
		return super.getAdapter(adapter);
	}

}
