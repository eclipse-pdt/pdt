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
