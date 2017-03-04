/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
public class PHPSearchTypeNameMatch extends DLTKSearchTypeNameMatch {

	public PHPSearchTypeNameMatch(IType type, int modifiers) {
		super(type, modifiers);
	}

	@Override
	public String getFullyQualifiedName() {
		return getType().getFullyQualifiedName(NamespaceReference.NAMESPACE_DELIMITER);
	}

	@Override
	public String getTypeContainerName() {
		IType outerType = getType().getDeclaringType();
		if (outerType != null) {
			return outerType.getTypeQualifiedName(NamespaceReference.NAMESPACE_DELIMITER);
		} else {
			return getType().getScriptFolder().getElementName();
		}
	}

}
