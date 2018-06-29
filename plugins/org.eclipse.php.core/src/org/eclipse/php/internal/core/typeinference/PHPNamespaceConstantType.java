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
package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;

/**
 * This evaluated type represents PHP constant declared in namespace
 * 
 */
public class PHPNamespaceConstantType implements IEvaluatedType {

	private String namespace;
	private String constantName;
	private IEvaluatedType valueType;
	private boolean global = false;

	/**
	 * Constructs evaluated type for PHP constant declared in namespace. The
	 * constant name can contain namespace part (namespace name must be real, and
	 * not point to the alias or subnamespace under current namespace)
	 */
	public PHPNamespaceConstantType(String constantName) {
		if (constantName == null) {
			throw new IllegalArgumentException();
		}

		int i = constantName.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
		// detect the namespace prefix:
		// global namespace case
		if (i == -1) {
			this.constantName = constantName;
		} else if (i == 0) {
			this.constantName = constantName.substring(1, constantName.length());
			global = true;
		} else if (i > 0) {
			// check is global namespace
			if (constantName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
				// make the constant name fully qualified:
				constantName = new StringBuilder().append(NamespaceReference.NAMESPACE_SEPARATOR).append(constantName)
						.toString();
				i += 1;
			}
			this.namespace = constantName.substring(0, i);
			this.constantName = constantName;
		}
	}

	/**
	 * Constructs evaluated type for PHP class or interface that was declared under
	 * some namespace
	 */
	public PHPNamespaceConstantType(String namespace, String typeName) {
		if (namespace == null || typeName == null) {
			throw new IllegalArgumentException();
		}

		// make the namespace fully qualified
		if (namespace.length() > 0 && namespace.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
			namespace = NamespaceReference.NAMESPACE_SEPARATOR + namespace;
		}

		this.namespace = namespace;
		this.constantName = new StringBuilder(namespace).append(NamespaceReference.NAMESPACE_SEPARATOR).append(typeName)
				.toString();
	}

	public void setValueType(IEvaluatedType valueType) {
		this.valueType = valueType;
	}

	public IEvaluatedType getValueType() {
		return valueType;
	}

	@Override
	public String getTypeName() {
		if (valueType != null) {
			return valueType.getTypeName();
		}
		return "unknown"; //$NON-NLS-1$
	}

	public String getConstantName() {
		return constantName;
	}

	/**
	 * Returns namespace name part of this constant or <code>null</code> if the
	 * constant is not declared under some namespace
	 * 
	 * @return namespace name part or null
	 */
	public String getNamespace() {
		return namespace;
	}

	public boolean isGlobal() {
		return global;
	}

	@Override
	public boolean subtypeOf(IEvaluatedType type) {
		return false;
	}

	public String getModelKey() {
		return constantName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
		result = prime * result + ((constantName == null) ? 0 : constantName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PHPNamespaceConstantType other = (PHPNamespaceConstantType) obj;
		if (!namespace.equals(other.namespace)) {
			return false;
		}
		if (constantName == null) {
			if (other.constantName != null) {
				return false;
			}
		} else if (!constantName.equals(other.constantName)) {
			return false;
		}
		return true;
	}
}
