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
package org.eclipse.php.core.util.project.observer;

import java.util.HashSet;
import java.util.Iterator;

public class CompositeProjectChangeObserver extends HashSet implements IProjectClosedObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void closed() {
		for (Iterator iter = iterator(); iter.hasNext();) {
			IProjectClosedObserver projectChangeObserver = (IProjectClosedObserver)iter.next();
			projectChangeObserver.closed();
		}
	}
}
