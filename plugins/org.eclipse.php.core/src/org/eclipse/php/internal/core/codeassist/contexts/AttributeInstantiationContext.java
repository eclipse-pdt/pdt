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

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.codeassist.ICompletionScope;
import org.eclipse.php.core.codeassist.ICompletionScope.Type;

/**
 * This context represents state when staying in a class instantiation
 * statement. <br/>
 * Examples:
 * 
 * <pre>
 *  #[|]
 *  #[Attribute, |]
 * </pre>
 * 
 * @author michael
 */
public class AttributeInstantiationContext extends StatementContext {

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (getCompanion().getPHPVersion().isLessThan(PHPVersion.PHP8_0)) {
			return false;
		}
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		ICompletionScope scope = getCompanion().getScope();

		return scope.getType() == Type.ATTRIBUTE;
	}

}
