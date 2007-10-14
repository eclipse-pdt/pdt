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
package org.eclipse.php.internal.ui.search;

import java.util.*;

import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.search.decorators.IPHPDataLeafMarker;

public class PHPSearchTreeContentProvider extends PHPSearchContentProvider implements ITreeContentProvider {

	public static final int LEVEL_PROJECT = 0;
	public static final int LEVEL_FILE = 1;
	public static final int LEVEL_TYPE = 2;

	protected final Object[] EMPTY_ARR = new Object[0];
	private Map fChildrenMap;
	protected PHPSearchResult fResult;
	private int groupingLevel;

	PHPSearchTreeContentProvider(PHPSearchResultPage page, int groupingLevel) {
		super(page);
		this.groupingLevel = groupingLevel;
	}

	public void setLevel(int level) {
		groupingLevel = level;
		initialize(fResult);
		getPage().getViewer().refresh();
	}

	protected void initialize(PHPSearchResult result) {
		fResult = result;
		fChildrenMap = new HashMap();
		if (result != null) {
			Object[] elements = result.getElements();
			for (int i = 0; i < elements.length; i++) {
				if (getPage().getDisplayedMatchCount(elements[i]) > 0) {
					insert(null, null, elements[i]);
				}
			}
		}
	}

	public synchronized void elementsChanged(Object[] updatedElements) {
		AbstractTreeViewer viewer = (AbstractTreeViewer) getPage().getViewer();
		if (fResult == null)
			return;
		Set toRemove = new HashSet();
		Set toUpdate = new HashSet();
		Map toAdd = new HashMap();
		for (int i = 0; i < updatedElements.length; i++) {
			if (getPage().getDisplayedMatchCount(updatedElements[i]) > 0)
				insert(toAdd, toUpdate, updatedElements[i]);
			else
				remove(toRemove, toUpdate, updatedElements[i]);
		}

		viewer.remove(toRemove.toArray());
		for (Iterator iter = toAdd.keySet().iterator(); iter.hasNext();) {
			Object parent = iter.next();
			HashSet children = (HashSet) toAdd.get(parent);
			viewer.add(parent, children.toArray());
		}
		for (Iterator elementsToUpdate = toUpdate.iterator(); elementsToUpdate.hasNext();) {
			viewer.refresh(elementsToUpdate.next());
		}

	}

	protected void remove(Set toRemove, Set toUpdate, Object element) {
		// precondition here:  fResult.getMatchCount(child) <= 0

		if (hasChildren(element)) {
			if (toUpdate != null)
				toUpdate.add(element);
		} else {
			if (getPage().getDisplayedMatchCount(element) == 0) {
				fChildrenMap.remove(element);
				Object parent = getParent(element);
				if (parent != null) {
					if (removeFromSiblings(element, parent)) {
						remove(toRemove, toUpdate, parent);
					}
				} else {
					if (removeFromSiblings(element, fResult)) {
						if (toRemove != null)
							toRemove.add(element);
					}
				}
			} else {
				if (toUpdate != null) {
					toUpdate.add(element);
				}
			}
		}
	}

	/**
	 * @param element
	 * @param parent
	 * @return returns true if it really was a remove (i.e. element was a child of parent).
	 */
	private boolean removeFromSiblings(Object element, Object parent) {
		Set siblings = (Set) fChildrenMap.get(parent);
		if (siblings != null) {
			return siblings.remove(element);
		} else {
			return false;
		}
	}

	protected void insert(Map toAdd, Set toUpdate, Object child) {
		Object parent = getParent(child);
		while (parent != null) {
			if (insertChild(parent, child)) {
				if (toAdd != null)
					insertInto(parent, child, toAdd);
			} else {
				if (toUpdate != null)
					toUpdate.add(parent);
				return;
			}
			child = parent;
			parent = getParent(child);
		}
		if (insertChild(fResult, child)) {
			if (toAdd != null)
				insertInto(fResult, child, toAdd);
		}
	}

	private boolean insertChild(Object parent, Object child) {
		return insertInto(parent, child, fChildrenMap);
	}

	private boolean insertInto(Object parent, Object child, Map map) {
		Set children = (Set) map.get(parent);
		if (children == null) {
			children = new HashSet();
			map.put(parent, children);
		}
		return children.add(child);
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void clear() {
		initialize(fResult);
		getPage().getViewer().refresh();
	}

	public Object[] getChildren(Object parentElement) {
		Set children = (Set) fChildrenMap.get(parentElement);
		if (children == null)
			return EMPTY_ARR;
		return children.toArray();
	}

	public Object getParent(Object element) {
		if (element instanceof PHPCodeData) {
			PHPCodeData cd = (PHPCodeData) element;
			Object res = cd.getContainer();
			if (res != null) {
				if (groupingLevel == LEVEL_TYPE) {
					if (res instanceof PHPFileData) {
						return null;
					}
				}
				return res;
			} else {
				if (groupingLevel == LEVEL_PROJECT && cd instanceof IPHPDataLeafMarker) {
					return ((IPHPDataLeafMarker) cd).getProject();
				}
			}
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

}
