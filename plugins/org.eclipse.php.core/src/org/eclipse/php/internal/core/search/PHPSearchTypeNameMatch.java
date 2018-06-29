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

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.core.search.DLTKSearchTypeNameMatch;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;

/**
 * PHP Search concrete type for a type name match.
 * 
 */
public class PHPSearchTypeNameMatch extends DLTKSearchTypeNameMatch implements IElementNameMatch {

	public PHPSearchTypeNameMatch(IType type, int modifiers) {
		super(type, modifiers);
	}

	@Override
	public String getFullyQualifiedName() {
		return getType().getFullyQualifiedName(NamespaceReference.NAMESPACE_DELIMITER);
	}

	@Override
	public String getContainerName() {
		return getTypeContainerName();
	}

	@Override
	public String getSimpleName() {
		return getSimpleTypeName();
	}

	@Override
	public int getElementType() {
		return T_TYPE;
	}

}
