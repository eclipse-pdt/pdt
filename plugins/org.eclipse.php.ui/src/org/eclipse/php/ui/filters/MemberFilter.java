/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPModifier;
import org.eclipse.php.core.phpModel.phpElementData.PHPVariableData;


/**
 * Filter for the methods viewer.
 * Changing a filter property does not trigger a refiltering of the viewer
 */
public class MemberFilter extends ViewerFilter {

	public static final int FILTER_NONPUBLIC = 1;
	public static final int FILTER_STATIC = 2;
	public static final int FILTER_VARIABLE = 4;

	private int fFilterProperties;

	/**
	 * Modifies filter and add a property to filter for
	 */
	public final void addFilter(int filter) {
		fFilterProperties |= filter;
	}

	/**
	 * Modifies filter and remove a property to filter for
	 */
	public final void removeFilter(int filter) {
		fFilterProperties &= (-1 ^ filter);
	}

	/**
	 * Tests if a property is filtered
	 */
	public final boolean hasFilter(int filter) {
		return (fFilterProperties & filter) != 0;
	}

	/*
	 * @see ViewerFilter#isFilterProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isFilterProperty(Object element, Object property) {
		return false;
	}

	/*
	 * @see ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof PHPCodeData) {
			PHPCodeData member = (PHPCodeData) element;

			if (hasFilter(FILTER_VARIABLE) && member instanceof PHPVariableData) {

				return false;
			}

			int flags = PHPModelUtil.getModifier(member);

			if (hasFilter(FILTER_STATIC) && PHPModifier.isStatic(flags)) {
				return false;
			}
			if (hasFilter(FILTER_NONPUBLIC) && !PHPModifier.isPublic(flags)) {
				return false;
			}
		}

		return true;
	}

}
