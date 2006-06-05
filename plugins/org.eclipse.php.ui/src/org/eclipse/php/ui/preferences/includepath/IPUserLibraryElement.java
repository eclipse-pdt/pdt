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
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.core.project.IIncludePathContainer;
import org.eclipse.php.core.project.IIncludePathEntry;
import org.eclipse.php.core.project.options.PHPProjectOptions;

public class IPUserLibraryElement {

	private class UpdatedIncludePathContainer implements IIncludePathContainer {

		/* (non-Javadoc)
		 * @see org.eclipse.jdt.core.IIncludePathContainer#getIncludePathEntries()
		 */
		public IIncludePathEntry[] getIncludePathEntries() {
			IPListElement[] children = getChildren();
			IIncludePathEntry[] entries = new IIncludePathEntry[children.length];
			for (int i = 0; i < entries.length; i++) {
				entries[i] = children[i].getIncludePathEntry();
			}
			return entries;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jdt.core.IIncludePathContainer#getDescription()
		 */
		public String getDescription() {
			return getName();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jdt.core.IIncludePathContainer#getKind()
		 */
		public int getKind() {
			return isSystemLibrary() ? IIncludePathContainer.K_SYSTEM : K_APPLICATION;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jdt.core.IIncludePathContainer#getPath()
		 */
		public IPath getPath() {
			return IPUserLibraryElement.this.getPath();
		}
	}

	private String fName;
	private List fChildren;
	private boolean fIsSystemLibrary;

	public IPUserLibraryElement(String name, IIncludePathContainer container, IProject project) {
		fName = name;
		fChildren = new ArrayList();
		if (container != null) {
			IIncludePathEntry[] entries = container.getIncludePathEntries();
			IPListElement[] res = new IPListElement[entries.length];
			for (int i = 0; i < res.length; i++) {
				IIncludePathEntry curr = entries[i];
				IPListElement elem = IPListElement.createFromExisting(this, curr, project);
				//elem.setAttribute(CPListElement.SOURCEATTACHMENT, curr.getSourceAttachmentPath());
				//elem.setAttribute(CPListElement.JAVADOC, JavaUI.getLibraryJavadocLocation(curr.getPath()));
				fChildren.add(elem);
			}
			fIsSystemLibrary = container.getKind() == IIncludePathContainer.K_SYSTEM;
		} else {
			fIsSystemLibrary = false;
		}
	}

	public IPUserLibraryElement(String name, boolean isSystemLibrary, IPListElement[] children) {
		fName = name;
		fChildren = new ArrayList();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				fChildren.add(children[i]);
			}
		}
		fIsSystemLibrary = isSystemLibrary;
	}

	public IPListElement[] getChildren() {
		return (IPListElement[]) fChildren.toArray(new IPListElement[fChildren.size()]);
	}

	public String getName() {
		return fName;
	}

	public IPath getPath() {
		return new Path(PHPProjectOptions.USER_LIBRARY_CONTAINER_ID).append(fName);
	}

	public boolean isSystemLibrary() {
		return fIsSystemLibrary;
	}

	public void add(IPListElement element) {
		if (!fChildren.contains(element)) {
			fChildren.add(element);
		}
	}

	private List moveUp(List elements, List move) {
		int nElements = elements.size();
		List res = new ArrayList(nElements);
		Object floating = null;
		for (int i = 0; i < nElements; i++) {
			Object curr = elements.get(i);
			if (move.contains(curr)) {
				res.add(curr);
			} else {
				if (floating != null) {
					res.add(floating);
				}
				floating = curr;
			}
		}
		if (floating != null) {
			res.add(floating);
		}
		return res;
	}

	public void moveUp(List toMoveUp) {
		if (toMoveUp.size() > 0) {
			fChildren = moveUp(fChildren, toMoveUp);
		}
	}

	public void moveDown(List toMoveDown) {
		if (toMoveDown.size() > 0) {
			Collections.reverse(fChildren);
			fChildren = moveUp(fChildren, toMoveDown);
			Collections.reverse(fChildren);
		}
	}

	public void remove(IPListElement element) {
		fChildren.remove(element);
	}

	public void replace(IPListElement existingElement, IPListElement element) {
		if (fChildren.contains(element)) {
			fChildren.remove(existingElement);
		} else {
			int index = fChildren.indexOf(existingElement);
			if (index != -1) {
				fChildren.set(index, element);
			} else {
				fChildren.add(element);
			}
		}
	}

	public IIncludePathContainer getUpdatedContainer() {
		return new UpdatedIncludePathContainer();
	}

	public boolean hasChanges(IIncludePathContainer oldContainer) {
		if (oldContainer == null || (oldContainer.getKind() == IIncludePathContainer.K_SYSTEM) != fIsSystemLibrary) {
			return true;
		}
		IIncludePathEntry[] oldEntries = oldContainer.getIncludePathEntries();
		if (fChildren.size() != oldEntries.length) {
			return true;
		}
		for (int i = 0; i < oldEntries.length; i++) {
			IPListElement child = (IPListElement) fChildren.get(i);
			if (!child.getIncludePathEntry().equals(oldEntries[i])) {
				return true;
			}
		}
		return false;
	}

}
