/*******************************************************************************
 * Copyright (c) 2005, 2013 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ti.types.IEvaluatedType;

public class GenericClassType extends PHPClassType {

	public static final String GENERATOR = "Generator"; //$NON-NLS-1$

	private List<IEvaluatedType> fTypes = new ArrayList<>();

	public int size() {
		if (this.fTypes != null) {
			return this.fTypes.size();
		}
		return 0;
	}

	public GenericClassType(String typeName) {
		super(typeName);
	}

	@Override
	public boolean subtypeOf(IEvaluatedType type) {
		if (type instanceof PHPClassType && type.getTypeName().compareToIgnoreCase(getTypeName()) == 0) {
			return true;
		}
		return false;
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
		GenericClassType other = (GenericClassType) obj;
		if (!getTypeName().equals(other.getTypeName())) {
			return false;
		}
		if (getTypes() == null) {
			if (getTypes() != null) {
				return false;
			}
		} else if (!fTypes.equals(other.getTypes())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getTypeName().hashCode() + ((fTypes == null) ? 0 : fTypes.hashCode());
		return result;
	}

	public List<IEvaluatedType> getTypes() {
		return fTypes;
	}
}
