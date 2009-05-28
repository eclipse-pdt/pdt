/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.astview.views;

import org.eclipse.php.internal.core.ast.nodes.IBinding;
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
		fParent= parent;
		if (value == null) {
			fName= name + ": null"; //$NON-NLS-1$
		} else if (value.length() > 0) {
			fName= name + ": '" + value + "'"; //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			fName= name + ": (empty string)"; //$NON-NLS-1$
		}
		fValues= null;
		fIsRelevant= isRelevant;
	}
	
	public BindingProperty(Binding parent, String name, boolean value, boolean isRelevant) {
		fParent= parent;
		fName= name + ": " + String.valueOf(value); //$NON-NLS-1$
		fValues= null;
		fIsRelevant= isRelevant;
	}
	
	public BindingProperty(Binding parent, String name, int value, boolean isRelevant) {
		fParent= parent;
		fName= name + ": " + String.valueOf(value); //$NON-NLS-1$
		fValues= null;
		fIsRelevant= isRelevant;
	}
	
	public BindingProperty(Binding parent, String name, IBinding[] bindings, boolean isRelevant) {
		fParent= parent;
		if (bindings == null || bindings.length == 0) {
			fName= name + " (0)"; //$NON-NLS-1$
			fValues= null;
		} else {
			fValues= createBindings(bindings, isRelevant);
			fName= name + " (" + String.valueOf(fValues.length) + ')'; //$NON-NLS-1$
		}
		fIsRelevant= isRelevant;
	}
	
	public BindingProperty(Binding parent, StringBuffer label, boolean isRelevant) {
		fParent= parent;
		fName= label.toString();
		fValues= null;
		fIsRelevant= isRelevant;
	}
	
	private Binding[] createBindings(IBinding[] bindings, boolean isRelevant) {
		Binding[] res= new Binding[bindings.length];
		for (int i= 0; i < res.length; i++) {
			res[i]= new Binding(this, String.valueOf(i), bindings[i], isRelevant);
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.astview.views.ASTAttribute#getParent()
	 */
	public Object getParent() {
		return fParent;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.php.astview.views.ASTAttribute#getChildren()
	 */
	public Object[] getChildren() {
		if (fValues != null) {
			return fValues;
		}
		return EMPTY;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.php.astview.views.ASTAttribute#getLabel()
	 */
	public String getLabel() {
		return fName;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.astview.views.ASTAttribute#getImage()
	 */
	public Image getImage() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getLabel();
	}
	
	public boolean isRelevant() {
		return fIsRelevant;
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !obj.getClass().equals(getClass())) {
			return false;
		}
		
		BindingProperty other= (BindingProperty) obj;
		if (fParent == null) {
			if (other.fParent != null)
				return false;
		} else if (! fParent.equals(other.fParent)) {
			return false;
		}
		
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (! fName.equals(other.fName)) {
			return false;
		}
		
		return true;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return (fParent != null ? fParent.hashCode() : 0)
				+ (fName != null ? fName.hashCode() : 0);
	}
}
