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
import org.eclipse.php.internal.core.codeassist.CompletionCompanion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;

/**
 * Completion context holds an information about cursor position in time when
 * code assist was invoked. For example: are we trying to complete a class in a
 * NEW statement? Are we trying to complete a function argument? etc...
 * 
 * @author michael
 */
public interface ICompletionContext {

	/**
	 * Returns whether this context is applicable for the position in the
	 * document
	 * 
	 * @param regionCollection
	 *            Text region collection
	 * @param phpScriptRegion
	 *            PHP script region
	 * @param partitionType
	 *            Partition type (see {@link PHPRegionTypes})
	 * @param offset
	 * @return <code>true</code> if this context is valid for the current
	 *         position, otherwise <code>false</code>
	 */
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor);

	/**
	 * Whether this context may be applied only if it's the only context that
	 * was found.
	 * 
	 * @return <code>true</code> if this context is exclusive, otherwise
	 *         <code>false</code>
	 */
	public boolean isExclusive();

	/**
	 * This method is called by the completion engine when initializing this
	 * context
	 */
	public void init(CompletionCompanion companion);
}
