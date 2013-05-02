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

/**
 * This context represents the state when staying after 'param' tag in PHPDoc
 * block <br/>
 * Examples:
 * 
 * <pre>
 *   1. /**
 *       * @param |
 *   2. /**
 *       * @param $a|
 * </pre>
 * 
 * @author michael
 */
public class PHPDocParamTagContext extends PHPDocTagContext {

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		return "param".equalsIgnoreCase(getTagName()); //$NON-NLS-1$
	}
}
