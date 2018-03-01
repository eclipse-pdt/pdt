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


public class Error extends ExceptionAttribute {
	
	private final Object fParent;
	private final String fLabel;
	
	public Error(Object parent, String label, Throwable thrownException) {
		fParent= parent;
		fLabel= label;
		fException= thrownException;
	}

	@Override
	public Object[] getChildren() {
		return EMPTY;
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public String getLabel() {
		return fLabel;
	}

	@Override
	public Object getParent() {
		return fParent;
	}
	
	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !obj.getClass().equals(getClass())) {
			return false;
		}
		
		Error other= (Error) obj;
		if (fParent == null) {
			if (other.fParent != null) {
				return false;
			}
		} else if (! fParent.equals(other.fParent)) {
			return false;
		}
		
		if (fLabel == null) {
			if (other.fLabel != null) {
				return false;
			}
		} else if (! fLabel.equals(other.fLabel)) {
			return false;
		}
		
		return true;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (fParent != null ? fParent.hashCode() : 0)
				+ (fLabel != null ? fLabel.hashCode() : 0);
	}

}
