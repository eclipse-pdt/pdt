/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
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
package org.eclipse.php.refactoring.core.extract.function;

public interface IParameterListChangeListener {

	/**
	 * Gets fired when the given parameter has changed
	 * 
	 * @param parameter
	 *            the parameter that has changed.
	 */
	public void parameterChanged(ParameterInfo parameter);

	/**
	 * Gets fired when the given parameter has been added
	 * 
	 * @param parameter
	 *            the parameter that has been added.
	 */
	public void parameterAdded(ParameterInfo parameter);

	/**
	 * Gets fired if the parameter list got modified by reordering or removing
	 * parameters (note that adding is handled by <code>parameterAdded</code>))
	 */
	public void parameterListChanged();
}