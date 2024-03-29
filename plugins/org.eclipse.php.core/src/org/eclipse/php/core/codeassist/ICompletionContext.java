/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.codeassist;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;

/**
 * Completion context holds an information about cursor position in time when
 * code assist was invoked. For example: are we trying to complete a class in a
 * NEW statement? Are we trying to complete a function argument? etc...
 * 
 * @author michael
 */
public interface ICompletionContext {
	static final Class<?>[] EMPTY = new Class[0];

	/**
	 * Returns whether this context is applicable for the position in the
	 * document
	 * 
	 * @param regionCollection
	 *            Text region collection
	 * @param phpScriptRegion
	 *            PHP script region
	 * @param partitionType
	 *            Partition type (see
	 *            {@link org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes})
	 * @param offset
	 * @return <code>true</code> if this context is valid for the current
	 *         position, otherwise <code>false</code>
	 */
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor);

	/**
	 * Whether this context may be applied only if it's the only context that
	 * was found.
	 * 
	 * @return <code>true</code> if this context is exclusive, otherwise
	 *         <code>false</code>
	 */
	public boolean isExclusive();

	default boolean isExclusive(ICompletionContext ctx) {
		return isExclusive();
	};

	/**
	 * This method is called by the completion engine when initializing this
	 * context
	 */
	public void init(CompletionCompanion companion);
}
