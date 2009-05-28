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

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class NonRelevantFilter extends ViewerFilter {
	
	private boolean fShowNonRelevant;
	
	public boolean isShowNonRelevant() {
		return fShowNonRelevant;
	}
	
	public void setShowNonRelevant(boolean showNonRelevant) {
		fShowNonRelevant= showNonRelevant;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (fShowNonRelevant)
			return true;
		
		if (element instanceof Binding) {
			return ((Binding) element).isRelevant();
		}
		if (element instanceof BindingProperty) {
			return ((BindingProperty) element).isRelevant();
		}
		return true;
	}
	

}
