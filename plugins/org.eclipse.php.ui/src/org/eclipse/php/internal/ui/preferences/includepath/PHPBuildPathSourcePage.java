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
package org.eclipse.php.internal.ui.preferences.includepath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.internal.corext.buildpath.BuildpathModifier;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElementAttribute;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.ListDialogField;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.internal.IChangeListener;

public class PHPBuildPathSourcePage extends PHPSourceContainerWorkbookPage {

	private List<BPListElement> fRemovedElements = new ArrayList<BPListElement>();
	private boolean removeFromIncludePath = false;

	private List<IChangeListener> removedElementListeners = new ArrayList<IChangeListener>(
			1);

	public List<BPListElement> getRemovedElements() {
		return fRemovedElements;
	}

	public boolean shouldRemoveFromIncludePath() {
		return removeFromIncludePath;
	}

	public PHPBuildPathSourcePage(ListDialogField buildpathList) {
		super(buildpathList);
	}

	@Override
	protected void removeEntry() {

		// clear the list of all removed elements after window closed.
		fRemovedElements.clear();

		List selElements = fFoldersList.getSelectedElements();
		for (int i = selElements.size() - 1; i >= 0; i--) {
			Object elem = selElements.get(i);
			if (elem instanceof BPListElementAttribute) {
				BPListElementAttribute attrib = (BPListElementAttribute) elem;
				String key = attrib.getKey();
				Object value = null;
				if (key.equals(BPListElement.EXCLUSION)
						|| key.equals(BPListElement.INCLUSION)) {
					value = new Path[0];
				}
				attrib.getParent().setAttribute(key, value);
				selElements.remove(i);
			}
		}
		if (selElements.isEmpty()) {
			fFoldersList.refresh();
			fBuildpathList.dialogFieldChanged(); // validate
		} else {
			for (Iterator iter = selElements.iterator(); iter.hasNext();) {
				BPListElement element = (BPListElement) iter.next();
				if (element.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {

					// for each removed source entry, check if it is part of the
					// include path
					// in case it is, add the entry to the removed elements list
					// and ask the user if he would like to remove it to the
					// include path as well
					if (null != IncludePathManager.isInIncludePath(
							fCurrJProject.getProject(), element.getPath())) {
						// add to removed elements list
						fRemovedElements.add(element);
					}
					List list = BuildpathModifier.removeFilters(element
							.getPath(), fCurrJProject, fFoldersList
							.getElements());
					for (Iterator iterator = list.iterator(); iterator
							.hasNext();) {
						BPListElement modified = (BPListElement) iterator
								.next();
						fFoldersList.refresh(modified);
						fFoldersList.expandElement(modified, 3);
					}
				}
			}
			if (fRemovedElements.size() > 0) {
				fFoldersList.removeElements(fRemovedElements);
				removeFromIncludePath = IncludePathUtils
						.openConfirmationDialog(
								getShell(),
								PHPUIMessages.IncludePath_RemoveEntryTitle,
								PHPUIMessages.IncludePath_RemoveEntryFromIncludePathMessage); //
				for (IChangeListener listener : removedElementListeners) {
					listener.update(true);
				}
			}
			fFoldersList.removeElements(selElements);
		}
	}

	public void registerRemovedElementListener(IChangeListener listener) {
		if (listener != null) {
			removedElementListeners.add(listener);
		}
	}

	public void unregisterRemovedElementListener(IChangeListener listener) {
		if (listener != null) {
			removedElementListeners.remove(listener);
		}
	}
}
