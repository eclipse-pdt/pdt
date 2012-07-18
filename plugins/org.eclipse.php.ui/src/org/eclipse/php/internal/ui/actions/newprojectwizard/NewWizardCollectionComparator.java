/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.php.internal.ui.actions.newprojectwizard;

import java.util.Set;

import org.eclipse.jface.viewers.IBasicPropertyConstants;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.ui.internal.dialogs.WizardCollectionElement;
import org.eclipse.ui.internal.dialogs.WorkbenchWizardElement;
import org.eclipse.ui.internal.registry.WizardsRegistryReader;

/**
 * A Viewer element sorter that sorts Elements by their name attribute. Note
 * that capitalization differences are not considered by this sorter, so a < B <
 * c.
 * 
 * NOTE one exception to the above: an element with the system's reserved name
 * for base Wizards will always be sorted such that it will ultimately be placed
 * at the beginning of the sorted result.
 */
class NewWizardCollectionComparator extends ViewerComparator {
	private Set primaryWizards;
	/**
	 * Static instance of this class.
	 */
	public final static NewWizardCollectionComparator INSTANCE = new NewWizardCollectionComparator();

	/**
	 * Creates an instance of <code>NewWizardCollectionSorter</code>. Since this
	 * is a stateless sorter, it is only accessible as a singleton; the private
	 * visibility of this constructor ensures this.
	 */
	private NewWizardCollectionComparator() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ViewerSorter#category(java.lang.Object)
	 */
	public int category(Object element) {
		if (element instanceof WorkbenchWizardElement) {
			return -1;
		}
		if (element instanceof WizardCollectionElement) {
			String id = ((WizardCollectionElement) element).getId();
			if (WizardsRegistryReader.GENERAL_WIZARD_CATEGORY.equals(id)) {
				return 1;
			}
			if (WizardsRegistryReader.UNCATEGORIZED_WIZARD_CATEGORY.equals(id)) {
				return 3;
			}
			if (WizardsRegistryReader.FULL_EXAMPLES_WIZARD_CATEGORY.equals(id)) {
				return 4;
			}
			return 2;
		}
		return super.category(element);
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (primaryWizards != null
				&& (primaryWizards.contains(e1) || primaryWizards.contains(e2))) {
			return -1;
		}
		return super.compare(viewer, e1, e2);
	}

	/**
	 * Return true if this sorter is affected by a property change of
	 * propertyName on the specified element.
	 */
	public boolean isSorterProperty(Object object, String propertyId) {
		return propertyId.equals(IBasicPropertyConstants.P_TEXT);
	}

	public void setPrimaryWizards(Set primaryWizards) {
		this.primaryWizards = primaryWizards;
	}
}
