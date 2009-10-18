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

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;

/**
 * This context represents the state when staying after 'implements' keyword in
 * a class declaration. <br/>
 * Examples:
 * 
 * <pre>
 *  1. class A implements |
 *  2. class A implements B|
 * </pre>
 * 
 * @author michael
 */
public class ClassImplementsContext extends ClassDeclarationContext {

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		if (hasExtends() && hasImplements()
				&& extendsMatcher.start() < implementsMatcher.start()) {
			return true;
		}
		if (hasImplements()) {
			return true;
		}
		return false;
	}

	public String getPrefix() throws BadLocationException {
		String prefix = super.getPrefix();
		if (getPhpVersion().isGreaterThan(PHPVersion.PHP5)) {
			if (prefix.length() > 0
					&& prefix.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
				prefix = prefix.substring(1);
			}
		}
		return prefix;
	}
}
