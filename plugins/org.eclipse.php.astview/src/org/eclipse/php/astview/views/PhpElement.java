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

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.swt.graphics.Image;


public class PhpElement extends ASTAttribute {

	private static final long LABEL_OPTIONS=
		ScriptElementLabels.F_APP_TYPE_SIGNATURE | ScriptElementLabels.M_PARAMETER_TYPES | 
		ScriptElementLabels.M_APP_RETURNTYPE | ScriptElementLabels.ALL_FULLY_QUALIFIED |
		ScriptElementLabels.T_TYPE_PARAMETERS |ScriptElementLabels.USE_RESOLVED;
	
	private final ISourceModule fPhpElement;
	private final Object fParent;

	public PhpElement(Object parent, ISourceModule javaElement) {
		fParent= parent;
		fPhpElement= javaElement;
	}
	
	public ISourceModule getPhpElement() {
		return fPhpElement;
	}
	
	public Object getParent() {
		return fParent;
	}

	public Object[] getChildren() {
		return EMPTY;
	}

	public String getLabel() {
		if (fPhpElement == null) {
			return ">java element: null"; //$NON-NLS-1$
		} else {
			String classname= fPhpElement.getClass().getName();
			return "> " + classname.substring(classname.lastIndexOf('.') + 1) + ": " //$NON-NLS-1$ //$NON-NLS-2$
					/* +  ScriptElementLabels.getElementLabel(fPhpElement, LABEL_OPTIONS)*/ // TODO check this label provider
					+ (fPhpElement.exists() ? "" : " (does not exist)");  //$NON-NLS-1$//$NON-NLS-2$
		}
	}
	
	public Image getImage() {
		return null;
		//TODO: looks ugly when not all nodes have an icon
//		return new JavaElementImageProvider().getImageLabel(fJavaElement, JavaElementImageProvider.SMALL_ICONS | JavaElementImageProvider.OVERLAY_ICONS);
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
		
		PhpElement other= (PhpElement) obj;
		if (fParent == null) {
			if (other.fParent != null)
				return false;
		} else if (! fParent.equals(other.fParent)) {
			return false;
		}
		
		if (fPhpElement == null) {
			if (other.fPhpElement != null)
				return false;
		} else if (! fPhpElement.equals(other.fPhpElement)) {
			return false;
		}
		
		return true;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return (fParent != null ? fParent.hashCode() : 0) + (fPhpElement != null ? fPhpElement.hashCode() : 0);
	}
}
