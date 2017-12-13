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
package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.evaluation.types.IClassType;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;

/**
 * This evaluated type represents PHP namespace
 */
public class PHPNamespaceType implements IClassType {

	private String namespace;

	/**
	 * Constructs evaluated type for PHP namespace.
	 */
	public PHPNamespaceType(String typeName) {
		if (typeName != null && !typeName.startsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
			// make the namespace fully qualified:
			typeName = new StringBuilder().append(NamespaceReference.NAMESPACE_DELIMITER).append(typeName).toString();
		}
		this.namespace = typeName;
	}

	/**
	 * Returns namespace name
	 * 
	 * @return namespace name
	 */
	@Override
	public String getTypeName() {
		return namespace;
	}

	@Override
	public boolean subtypeOf(IEvaluatedType type) {
		return false;
	}

	public String getModelKey() {
		return namespace;
	}

	/**
	 * Creates evaluated type from the given IType.
	 * 
	 * @param type
	 * @return evaluated type
	 */
	public static PHPNamespaceType fromIType(IType type) {
		String elementName = type.getElementName();
		try {
			if (PHPFlags.isNamespace(type.getFlags())) {
				return new PHPNamespaceType(elementName);
			}
		} catch (ModelException e) {
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PHPNamespaceType other = (PHPNamespaceType) obj;
		if (namespace == null) {
			if (other.namespace != null)
				return false;
		} else if (!namespace.equals(other.namespace))
			return false;
		return true;
	}
}
