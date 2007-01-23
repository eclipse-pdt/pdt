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
package org.eclipse.php.internal.ui.preferences.includepath;

import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.php.internal.core.project.IIncludePathEntry;

public class IPListElementSorter extends ViewerSorter {

	private static final int SOURCE = 0;
	private static final int PROJECT = 1;
	private static final int LIBRARY = 2;
	private static final int VARIABLE = 3;
	private static final int CONTAINER = 4;

	private static final int ATTRIBUTE = 5;
	private static final int CONTAINER_ENTRY = 6;

	private static final int OTHER = 7;

	/*
	 * @see ViewerSorter#category(Object)
	 */
	public int category(Object obj) {
		if (obj instanceof IPListElement) {
			IPListElement element = (IPListElement) obj;
			if (element.getParentContainer() != null) {
				return CONTAINER_ENTRY;
			}
			switch (element.getEntryKind()) {
				case IIncludePathEntry.IPE_LIBRARY:
				case IIncludePathEntry.IPE_JRE:
					return LIBRARY;
				case IIncludePathEntry.IPE_PROJECT:
					return PROJECT;
				case IIncludePathEntry.IPE_SOURCE:
					return SOURCE;
				case IIncludePathEntry.IPE_VARIABLE:
					return VARIABLE;
				case IIncludePathEntry.IPE_CONTAINER:
					return CONTAINER;
			}
		} else if (obj instanceof IPListElementAttribute) {
			return ATTRIBUTE;
		}
		return OTHER;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerSorter#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public int compare(Viewer viewer, Object e1, Object e2) {

		int cat1 = category(e1);
		int cat2 = category(e2);

		if (cat1 != cat2)
			return cat1 - cat2;

		if (cat1 == ATTRIBUTE || cat1 == CONTAINER_ENTRY) {
			return 0; // do not sort attributes or container entries
		}

		if (viewer instanceof ContentViewer) {
			IBaseLabelProvider prov = ((ContentViewer) viewer).getLabelProvider();
			if (prov instanceof ILabelProvider) {
				ILabelProvider lprov = (ILabelProvider) prov;
				String name1 = lprov.getText(e1);
				String name2 = lprov.getText(e2);
				return collator.compare(name1, name2);
			}
		}
		return 0;
	}

}
