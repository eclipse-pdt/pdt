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

import org.eclipse.dltk.core.IField;

/**
 * PHP Search concrete type for a field name match.
 */
public class PHPSearchFieldNameMatch extends FieldNameMatch implements IElementNameMatch {

	private final IField field;
	private final int modifiers;

	/**
	 * Creates a new Java Search type name match.
	 */
	public PHPSearchFieldNameMatch(IField method, int modifiers) {
		this.field = method;
		this.modifiers = modifiers;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true; // avoid unnecessary calls for identical objects
		if (obj instanceof FieldNameMatch) {
			FieldNameMatch match = (FieldNameMatch) obj;
			if (this.field == null) {
				return match.getField() == null && match.getModifiers() == this.modifiers;
			}
			return this.field.equals(match.getField()) && match.getModifiers() == this.modifiers;
		}
		return false;
	}

	@Override
	public int getModifiers() {
		return this.modifiers;
	}

	@Override
	public IField getField() {
		return this.field;
	}

	@Override
	public int hashCode() {
		if (this.field == null)
			return this.modifiers;
		return this.field.hashCode();
	}

	@Override
	public String toString() {
		if (this.field == null)
			return super.toString();
		return this.field.toString();
	}

	@Override
	public String getSimpleName() {
		return getSimpleFieldName();
	}

	@Override
	public String getContainerName() {
		return getFieldContainerName();
	}

	@Override
	public int getElementType() {
		return T_FIELD;
	}
}
