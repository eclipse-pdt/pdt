/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
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
package org.eclipse.php.internal.debug.core.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

/**
 * Implementors of this interface may act as a "container" for grouping
 * variables (i.e. large sets of array members).
 * 
 * @author Bartlomiej Laczkowski
 */
public interface IVirtualPartition extends IVariable, IVariableFacet {

	/**
	 * Partition variables provider.
	 */
	public interface IVariableProvider {

		/**
		 * Returns a set of variables for corresponding partition.
		 * 
		 * @return set of variables for corresponding partition
		 * @throws DebugException
		 */
		IVariable[] getVariables() throws DebugException;

	}

	/**
	 * Sets variable provider for this virtual partition.
	 * 
	 * @param variableProvider
	 */
	public void setProvider(IVariableProvider variableProvider);

}
