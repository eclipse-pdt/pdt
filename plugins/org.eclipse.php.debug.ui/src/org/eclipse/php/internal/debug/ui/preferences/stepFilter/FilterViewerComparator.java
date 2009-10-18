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
package org.eclipse.php.internal.debug.ui.preferences.stepFilter;

import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.debug.core.preferences.stepFilters.DebugStepFilter;
import org.eclipse.php.internal.debug.core.preferences.stepFilters.IStepFilterTypes;
import org.eclipse.ui.model.WorkbenchViewerComparator;

/**
 * A comperator for Debug Step Filter objects
 * 
 * @author yaronm
 */
public class FilterViewerComparator extends WorkbenchViewerComparator {
	public int compare(Viewer viewer, Object e1, Object e2) {
		DebugStepFilter f1 = (DebugStepFilter) e1;
		DebugStepFilter f2 = (DebugStepFilter) e2;

		if ((f1.getType() == IStepFilterTypes.PHP_PROJECT)
				&& (f2.getType() != (IStepFilterTypes.PHP_PROJECT))) {
			return 1;
		}

		if ((f1.getType() != IStepFilterTypes.PHP_PROJECT)
				&& (f2.getType() == IStepFilterTypes.PHP_PROJECT)) {
			return 1;
		}

		ILabelProvider lprov = (ILabelProvider) ((ContentViewer) viewer)
				.getLabelProvider();
		String name1 = lprov.getText(e1);
		String name2 = lprov.getText(e2);
		if (name1 == null) {
			name1 = ""; //$NON-NLS-1$
		}
		if (name2 == null) {
			name2 = ""; //$NON-NLS-1$
		}

		return name1.compareTo(name2);
	}
}
