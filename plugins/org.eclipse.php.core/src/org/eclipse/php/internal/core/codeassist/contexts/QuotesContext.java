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
package org.eclipse.php.internal.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;

/**
 * This context represents the state when staying in a single or double quotes.
 * 
 * @author michael
 */
public class QuotesContext extends AbstractCompletionContext {
	private IncludeStatementContext includeStatementContext = new IncludeStatementContext();

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		if (includeStatementContext.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		return (getPartitionType() == PHPPartitionTypes.PHP_QUOTED_STRING);
	}
}
