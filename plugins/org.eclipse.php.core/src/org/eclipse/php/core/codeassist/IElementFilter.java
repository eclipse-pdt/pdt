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

import org.eclipse.dltk.core.IModelElement;

/**
 * This is a model element filter that filters out model elements from adding
 * them to code assist list
 * 
 * @author michael
 */
public interface IElementFilter {

	/**
	 * @param element
	 *            Model element
	 * @return <code>true</code> if given element must be filtered out from code
	 *         assist, otherwise <code>false</code>
	 */
	public boolean filter(IModelElement element);
}