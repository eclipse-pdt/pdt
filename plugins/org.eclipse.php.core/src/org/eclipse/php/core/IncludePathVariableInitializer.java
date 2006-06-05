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
/**
 * 
 */
package org.eclipse.php.core;

/**
 * @author shachar
 *
 */
public abstract class IncludePathVariableInitializer {

    /**
     * Creates a new includePath variable initializer.
     */
    public IncludePathVariableInitializer() {
    }

    /**
     * Binds a value to the workspace includePath variable with the given name,
     * or fails silently if this cannot be done. 
     * <p>
     * A variable initializer is automatically activated whenever a variable value
     * is needed and none has been recorded so far. The implementation of
     * the initializer can set the corresponding variable using 
     * <code>PHPCorePlugin#setIncludePathVariable</code>.
     * 
     * @param variable the name of the workspace includePath variable
     *    that requires a binding
     */
    public abstract void initialize(String variable);
}
