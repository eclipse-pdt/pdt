/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * 
 * @author guy.g
 *
 */

public interface IAfterNewLineAutoEditStrategy {
	/**
	 * Appending to the buffer the text required
	 * @return the position the caret should be at in the end of the command execution. return -1 if no need to change the caret location 
	 */
	public int autoEditAfterNewLine(IStructuredDocument document, DocumentCommand command, StringBuffer buffer);

}
