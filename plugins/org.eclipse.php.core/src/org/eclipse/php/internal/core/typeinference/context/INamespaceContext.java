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
package org.eclipse.php.internal.core.typeinference.context;

/**
 * This context contains information about current namespace
 * 
 * @author michael
 */
public interface INamespaceContext {

	/**
	 * Returns current namespace name or <code>null</code> if the context is
	 * outside of any namespaces
	 * 
	 * @return namespace name
	 */
	public String getNamespace();
}
