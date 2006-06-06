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
package org.eclipse.php.ui.search;

import java.text.Collator;
import java.util.Comparator;

import org.eclipse.ui.IWorkingSet;

public class WorkingSetComparator implements Comparator {

	private Collator fCollator= Collator.getInstance();
	
	/*
	 * @see Comparator#compare(Object, Object)
	 */
	public int compare(Object o1, Object o2) {
		String name1= null;
		String name2= null;
		
		if (o1 instanceof IWorkingSet)
			name1= ((IWorkingSet)o1).getName();

		if (o2 instanceof IWorkingSet)
			name2= ((IWorkingSet)o2).getName();

		return fCollator.compare(name1, name2);
	}
}
