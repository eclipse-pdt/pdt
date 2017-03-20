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
package org.eclipse.php.core.codeassist;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;

/**
 * This resolver analyzes the text and the cursor position, and determines the
 * context where code assist has been invoked.
 * 
 * @author michael
 */
public interface ICompletionContextResolver {

	/**
	 * Returns completion contexts for the given offset in the document.
	 * 
	 * @param sourceModule
	 *            Source module of the file where code assist was requested
	 * @param offset
	 *            Cursor offset where code assist was requested
	 * @param requestor
	 *            Completion requestor ({@link CompletionRequestor})
	 * @param companion
	 *            Shared instance of completion companion between all contexts
	 * @return completion contexts or empty list in case no completion context
	 *         could be found
	 */
	public ICompletionContext[] resolve(ISourceModule sourceModule, int offset, CompletionRequestor requestor,
			CompletionCompanion companion);

	/**
	 * Creates known completion contexts
	 * 
	 * @param contexts
	 */
	public ICompletionContext[] createContexts();
}
