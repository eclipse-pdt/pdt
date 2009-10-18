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
 * This context represents state when staying in a PHP element declaration. <br/>
 * Examples:
 * 
 * <pre>
 *  1. function |
 *  2. function foo(|) {}
 *  3. class A |
 *  4. namespace |
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public abstract class DeclarationContext extends AbstractCompletionContext {

}
