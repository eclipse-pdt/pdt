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
package org.eclipse.php.ui.preferences.includepath;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class IncludePathBasePage {

	public abstract List getSelection();

	public abstract void setSelection(List selection, boolean expand);

	public abstract boolean isEntryKind(int kind);

	protected void filterAndSetSelection(List list) {
		ArrayList res = new ArrayList(list.size());
		for (int i = list.size() - 1; i >= 0; i--) {
			Object curr = list.get(i);
			if (curr instanceof IPListElement) {
				IPListElement elem = (IPListElement) curr;
				if (elem.getParentContainer() == null && isEntryKind(elem.getEntryKind())) {
					res.add(curr);
				}
			}
		}
		setSelection(res, false);
	}

	public static void fixNestingConflicts(List newEntries, List existing, Set modifiedSourceEntries) {
		for (int i = 0; i < newEntries.size(); i++) {
			IPListElement curr = (IPListElement) newEntries.get(i);
			//			addExclusionPatterns(curr, existing, modifiedSourceEntries);
		}
	}

	protected boolean containsOnlyTopLevelEntries(List selElements) {
		if (selElements.size() == 0) {
			return true;
		}
		for (int i = 0; i < selElements.size(); i++) {
			Object elem = selElements.get(i);
			if (elem instanceof IPListElement) {
				if (((IPListElement) elem).getParentContainer() != null) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	public abstract void init(IProject javaProject);

	public abstract Control getControl(Composite parent);

}
