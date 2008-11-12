/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences.includepath;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ScriptModelUtil;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class BuildPathBasePage {

	protected String fTitle = null;

	public abstract List getSelection();

	public abstract void setSelection(List selection, boolean expand);

	public void addElement(BPListElement element) {

	}

	public abstract boolean isEntryKind(int kind);

	protected void filterAndSetSelection(List list) {
		ArrayList res = new ArrayList(list.size());
		for (int i = list.size() - 1; i >= 0; i--) {
			Object curr = list.get(i);
			if (curr instanceof BPListElement) {
				BPListElement elem = (BPListElement) curr;
				if (elem.getParentContainer() == null
						&& isEntryKind(elem.getEntryKind())) {
					res.add(curr);
				}
			}
		}
		setSelection(res, false);
	}

	public static void fixNestingConflicts(BPListElement[] newEntries,
			BPListElement[] existing, Set modifiedSourceEntries) {
		for (int i = 0; i < newEntries.length; i++) {
			addExclusionPatterns(newEntries[i], existing, modifiedSourceEntries);
		}
	}

	/**
	 * Update page title. Needs to be called before getControl()
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		fTitle = title;
	}

	private static void addExclusionPatterns(BPListElement newEntry,
			BPListElement[] existing, Set modifiedEntries) {
		IPath entryPath = newEntry.getPath();
		for (int i = 0; i < existing.length; i++) {
			BPListElement curr = existing[i];
			if (curr.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
				IPath currPath = curr.getPath();
				if (!currPath.equals(entryPath)) {
					if (currPath.isPrefixOf(entryPath)) {
						if (addToExclusions(entryPath, curr)) {
							modifiedEntries.add(curr);
						}
					} else if (entryPath.isPrefixOf(currPath)) {
						if (addToExclusions(currPath, newEntry)) {
							modifiedEntries.add(curr);
						}
					}
				}
			}
		}
	}

	private static boolean addToExclusions(IPath entryPath, BPListElement curr) {
		IPath[] exclusionFilters = (IPath[]) curr
				.getAttribute(BPListElement.EXCLUSION);
		if (!ScriptModelUtil.isExcludedPath(entryPath, exclusionFilters)) {
			IPath pathToExclude = entryPath.removeFirstSegments(
					curr.getPath().segmentCount()).addTrailingSeparator();
			IPath[] newExclusionFilters = new IPath[exclusionFilters.length + 1];
			System.arraycopy(exclusionFilters, 0, newExclusionFilters, 0,
					exclusionFilters.length);
			newExclusionFilters[exclusionFilters.length] = pathToExclude;
			curr.setAttribute(BPListElement.EXCLUSION, newExclusionFilters);
			return true;
		}
		return false;
	}

	protected boolean containsOnlyTopLevelEntries(List selElements) {
		if (selElements.size() == 0) {
			return true;
		}
		for (int i = 0; i < selElements.size(); i++) {
			Object elem = selElements.get(i);
			if (elem instanceof BPListElement) {
				if (((BPListElement) elem).getParentContainer() != null) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	public abstract void init(IScriptProject scriptProject);

	public abstract Control getControl(Composite parent);

}
