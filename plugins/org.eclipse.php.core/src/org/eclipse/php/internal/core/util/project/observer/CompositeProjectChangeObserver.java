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
package org.eclipse.php.internal.core.util.project.observer;

import java.util.HashSet;
import java.util.Iterator;

public class CompositeProjectChangeObserver extends HashSet<IProjectClosedObserver> implements IProjectClosedObserver {

	private static final long serialVersionUID = 1L;

	@Override
	public void closed() {
		for (Iterator<IProjectClosedObserver> iter = iterator(); iter.hasNext();) {
			IProjectClosedObserver projectChangeObserver = iter.next();
			projectChangeObserver.closed();
		}
	}
}
