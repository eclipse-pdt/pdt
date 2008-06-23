/*******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.php.core;

/**
 * @author shachar
 *
 */
public interface IIncludePathVariableInitializer {

    /**
     * Returns a value of the variable with the given name, or <code>null</code> if this cannot be done. 
     * <p>
     * A variable initializer is automatically activated whenever a variable value
     * is needed and none has been recorded so far.
     * 
     * @param name the name of the workspace includePath variable
     *    that requires a binding
     *    
     * @return variable value if it was resolved successfully, otherwise <code>null</code> 
     */
    public String initialize(String name);
}
