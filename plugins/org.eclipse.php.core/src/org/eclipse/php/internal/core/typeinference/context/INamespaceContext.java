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
package org.eclipse.php.internal.core.typeinference.context;

/**
 * This context contains information about current namespace
 * 
 * @author michael
 */
public interface INamespaceContext {

	/**
	 * Returns current namespace name or <code>null</code> if the context is outside
	 * of any namespaces
	 * 
	 * @return namespace name
	 */
	public String getNamespace();
}
