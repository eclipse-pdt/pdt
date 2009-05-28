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

import org.eclipse.swt.graphics.Image;


public class GeneralAttribute extends ASTAttribute {
	
	private final Object fParent;
	private final String fLabel;
	private final Object[] fChildren;
	
	public GeneralAttribute(Object parent, String name, Object value) {
		fParent= parent;
		fLabel= name + ": " + String.valueOf(value);
		fChildren= EMPTY;
	}
	
	public GeneralAttribute(Object parent, String label) {
		fParent= parent;
		fLabel= label;
		fChildren= EMPTY;
	}
	
	public GeneralAttribute(Object parent, String name, Object[] children) {
		fParent= parent;
		if (children == null) {
			fLabel= name + ": null";
			fChildren= EMPTY;
		} else if (children.length == 0) {
			fLabel= name + " (0)";
			fChildren= EMPTY;
		} else {
			fChildren= createChildren(children);
			fLabel= name + " (" + String.valueOf(fChildren.length) + ')';
		}
	}
	
	private Object[] createChildren(Object[] children) {
		ASTAttribute[] res= new ASTAttribute[children.length];
		for (int i= 0; i < res.length; i++) {
			Object child= children[i];
			String name= String.valueOf(i);
			res[i]= Binding.createValueAttribute(this, name, child);
		}
		return res;
	}

	public Object getParent() {
		return fParent;
	}

	public Object[] getChildren() {
		return fChildren;
	}

	public String getLabel() {
		return fLabel;
	}
	
	public Image getImage() {
		return null;
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
		
		GeneralAttribute other= (GeneralAttribute) obj;
		if (fParent == null) {
			if (other.fParent != null)
				return false;
		} else if (! fParent.equals(other.fParent)) {
			return false;
		}
		
		if (fLabel == null) {
			if (other.fLabel != null)
				return false;
		} else if (! fLabel.equals(other.fLabel)) {
			return false;
		}
		
		return true;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return (fParent != null ? fParent.hashCode() : 0)
				+ (fLabel != null ? fLabel.hashCode() : 0);
	}
}
