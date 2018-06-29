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
package org.eclipse.php.astview.views;

import org.eclipse.php.core.ast.nodes.IBinding;
import org.eclipse.swt.graphics.Image;

/**
 *
 */
public class BindingProperty extends ASTAttribute {

	private final String fName;
	private final Binding fParent;
	private final Binding[] fValues;
	private final boolean fIsRelevant;

	public BindingProperty(Binding parent, String name, String value, boolean isRelevant) {
		fParent = parent;
		if (value == null) {
			fName = name + ": null"; //$NON-NLS-1$
		} else if (value.length() > 0) {
			fName = name + ": '" + value + "'"; //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			fName = name + ": (empty string)"; //$NON-NLS-1$
		}
		fValues = null;
		fIsRelevant = isRelevant;
	}

	public BindingProperty(Binding parent, String name, boolean value, boolean isRelevant) {
		fParent = parent;
		fName = name + ": " + value; //$NON-NLS-1$
		fValues = null;
		fIsRelevant = isRelevant;
	}

	public BindingProperty(Binding parent, String name, int value, boolean isRelevant) {
		fParent = parent;
		fName = name + ": " + value; //$NON-NLS-1$
		fValues = null;
		fIsRelevant = isRelevant;
	}

	public BindingProperty(Binding parent, String name, IBinding[] bindings, boolean isRelevant) {
		fParent = parent;
		if (bindings == null || bindings.length == 0) {
			fName = name + " (0)"; //$NON-NLS-1$
			fValues = null;
		} else {
			fValues = createBindings(bindings, isRelevant);
			fName = name + " (" + fValues.length + ')'; //$NON-NLS-1$
		}
		fIsRelevant = isRelevant;
	}

	public BindingProperty(Binding parent, StringBuilder label, boolean isRelevant) {
		fParent = parent;
		fName = label.toString();
		fValues = null;
		fIsRelevant = isRelevant;
	}

	private Binding[] createBindings(IBinding[] bindings, boolean isRelevant) {
		Binding[] res = new Binding[bindings.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = new Binding(this, String.valueOf(i), bindings[i], isRelevant);
		}
		return res;
	}

	@Override
	public Object getParent() {
		return fParent;
	}

	@Override
	public Object[] getChildren() {
		if (fValues != null) {
			return fValues;
		}
		return EMPTY;
	}

	@Override
	public String getLabel() {
		return fName;
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public String toString() {
		return getLabel();
	}

	public boolean isRelevant() {
		return fIsRelevant;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !obj.getClass().equals(getClass())) {
			return false;
		}

		BindingProperty other = (BindingProperty) obj;
		if (fParent == null) {
			if (other.fParent != null) {
				return false;
			}
		} else if (!fParent.equals(other.fParent)) {
			return false;
		}

		if (fName == null) {
			if (other.fName != null) {
				return false;
			}
		} else if (!fName.equals(other.fName)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return (fParent != null ? fParent.hashCode() : 0) + (fName != null ? fName.hashCode() : 0);
	}
}
