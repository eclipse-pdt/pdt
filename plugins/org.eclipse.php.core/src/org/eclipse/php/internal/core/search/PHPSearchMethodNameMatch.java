/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.search;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.internal.core.search.DLTKSearchMethodNameMatch;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;

/**
 * PHP Search concrete type for a method name match.
 *
 */
public class PHPSearchMethodNameMatch extends DLTKSearchMethodNameMatch implements IElementNameMatch {

	public PHPSearchMethodNameMatch(IMethod type, int modifiers) {
		super(type, modifiers);
	}

	@Override
	public String getFullyQualifiedName() {
		return getMethod().getFullyQualifiedName(NamespaceReference.NAMESPACE_DELIMITER);
	}

	@Override
	public String getSimpleName() {
		return getSimpleMethodName();
	}

	@Override
	public String getContainerName() {
		return getMethodContainerName();
	}

	@Override
	public int getElementType() {
		return T_METHOD;
	}

}
