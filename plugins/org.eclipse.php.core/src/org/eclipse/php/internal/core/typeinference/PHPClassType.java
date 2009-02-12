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
package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.evaluation.types.IClassType;
import org.eclipse.dltk.ti.types.ClassType;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.mixin.PHPMixinParser;

/**
 * This evaluated type represents PHP class or interface
 * @author michael
 */
public class PHPClassType extends ClassType implements IClassType {

	private String namespace;
	private String typeName;

	/**
	 * Constructs evaluated type for PHP class or interface that was declared under some namespace
	 */
	public PHPClassType(String typeName) {
		if (typeName == null) {
			throw new IllegalArgumentException();
		}
		// detect the namespace prefix:
		int i = typeName.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
		if (i != -1) {
			this.namespace = typeName.substring(0, i);
		}
		this.typeName = typeName;
	}
	
	/**
	 * Constructs evaluated type for PHP class or interface that was declared under some namespace
	 */
	public PHPClassType(String namespace, String typeName) {
		if (namespace == null || typeName == null) {
			throw new IllegalArgumentException();
		}
		
		this.namespace = namespace;
		
		this.typeName = new StringBuilder(namespace)
			.append(NamespaceReference.NAMESPACE_SEPARATOR).append(typeName).toString();
	}

	/**
	 * Returns fully qualified type name (including namespace)
	 * @return type name
	 */
	public String getTypeName() {
		return typeName;
	}
	
	/**
	 * Returns namespace name part of this type or <code>null</code> if the type is not declared under some namespace
	 * @return
	 */
	public String getNamespace() {
		return namespace;
	}

	public boolean subtypeOf(IEvaluatedType type) {
		return false;
	}

	public String getModelKey() {
		return typeName + PHPMixinParser.CLASS_SUFFIX;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PHPClassType other = (PHPClassType) obj;
		if (namespace == null) {
			if (other.namespace != null)
				return false;
		} else if (!namespace.equals(other.namespace))
			return false;
		if (typeName == null) {
			if (other.typeName != null)
				return false;
		} else if (!typeName.equals(other.typeName))
			return false;
		return true;
	}
}
