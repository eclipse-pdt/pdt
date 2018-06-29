/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
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
package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;

public interface IAppliedAutoEditStrategy extends IAutoEditStrategy {
	/**
	 * @return returns true when strategy was successfully applied to current
	 *         command through
	 *         {@link IAutoEditStrategy#customizeDocumentCommand(IDocument, DocumentCommand)}
	 *         , even if command content may stay unchanged
	 */
	boolean wasApplied();
}
