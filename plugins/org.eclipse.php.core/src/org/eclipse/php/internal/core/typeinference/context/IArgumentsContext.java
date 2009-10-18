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

import org.eclipse.dltk.ti.types.IEvaluatedType;

/**
 * This context provides argument type information to the context
 */
public interface IArgumentsContext {

	/**
	 * Returns evaluated type of the method argument by its name
	 * 
	 * @param name
	 *            Argument name
	 * @return
	 */
	IEvaluatedType getArgumentType(String name);
}
