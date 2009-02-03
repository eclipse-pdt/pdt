/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.contexts;


/**
 * This context represents the state when staying in a class or interface declaration.
 * <br/>Examples:
 * <pre>
 *  1. class |
 *  2. class A |
 *  3. class A extends |
 *  4. class A extends B implements |
 *  5. interface A extends |
 *  6. interface A extends B|
 *  etc... 
 * </pre>
 * @author michael
 */
public abstract class TypeDeclarationContext extends DeclarationContext {
}
