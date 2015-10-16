/*******************************************************************************
 * Copyright (c) 2005, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ti.types.IEvaluatedType;

public class GeneratorClassType extends PHPClassType {
	private List<IEvaluatedType> fTypes = new ArrayList<IEvaluatedType>();

	public int size() {
		if (this.fTypes != null) {
			return this.fTypes.size();
		}
		return 0;
	}

	public GeneratorClassType() {
		super("Generator"); //$NON-NLS-1$
	}

	@Override
	public String getTypeName() {
		/*
		 * String names = ""; //$NON-NLS-1$ Iterator<IEvaluatedType> i =
		 * fTypes.iterator(); while (i.hasNext()) { IEvaluatedType type =
		 * (IEvaluatedType) i.next(); names += type.getTypeName() + " ";
		 * //$NON-NLS-1$ } return "generator:" + names; //$NON-NLS-1$
		 */
		return super.getTypeName();
	}

	@Override
	public boolean subtypeOf(IEvaluatedType type) {
		if (type instanceof PHPClassType && type.getTypeName().compareToIgnoreCase("Generator") == 0) { //$NON-NLS-1$
			return true;
		}
		return false;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeneratorClassType other = (GeneratorClassType) obj;
		if (getTypes() == null) {
			if (getTypes() != null)
				return false;
		} else if (!fTypes.equals(other.getTypes()))
			return false;
		return true;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fTypes == null) ? 0 : fTypes.hashCode());
		return result;
	}

	public List<IEvaluatedType> getTypes() {
		return fTypes;
	}
}
