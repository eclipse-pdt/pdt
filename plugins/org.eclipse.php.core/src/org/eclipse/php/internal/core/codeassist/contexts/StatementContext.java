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

/**
 * This context represents state when staying in a regular (non-declaration)
 * code statement. <br/>
 * Examples:
 * 
 * <pre>
 *  1. |
 *  2. $this-&gt;|
 *  3. foo|
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public abstract class StatementContext extends CodeContext {
}
