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

import org.eclipse.php.internal.core.typeinference.IModelAccessCache;

/**
 * This context contains model access cache that can be reused between type
 * inferencer goals and evaluators.
 * 
 * @author Michael
 * 
 */
public interface IModelCacheContext {

	/**
	 * @return cache instance if available, otherwise may return <code>null</code>.
	 */
	IModelAccessCache getCache();

	void setCache(IModelAccessCache cache);
}
