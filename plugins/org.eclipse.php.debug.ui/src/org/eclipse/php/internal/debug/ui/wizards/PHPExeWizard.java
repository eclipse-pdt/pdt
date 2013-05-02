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
package org.eclipse.php.internal.debug.ui.wizards;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.ui.wizards.FragmentedWizard;
import org.eclipse.php.internal.ui.wizards.WizardFragment;
import org.eclipse.php.internal.ui.wizards.WizardFragmentsFactoryRegistry;
import org.eclipse.php.internal.ui.wizards.WizardModel;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class PHPExeWizard extends FragmentedWizard implements INewWizard {

	public static final String MODEL = "phpExe"; //$NON-NLS-1$
	protected static final String FRAGMENT_GROUP_ID = "org.eclipse.php.debug.ui.phpExeWizardCompositeFragment"; //$NON-NLS-1$
	private PHPexeItem[] existingItems;

	public PHPExeWizard(PHPexeItem[] existingItems) {
		this(existingItems, Messages.PHPExeWizard_0);
	}

	public PHPExeWizard(PHPexeItem[] existingItems, String title,
			WizardModel taskModel) {
		super(title, null, taskModel);
		this.existingItems = existingItems;
		setRootFragment(createRootFragment());
	}

	public PHPExeWizard(PHPexeItem[] existingItems, String title) {
		super(title, null);
		this.existingItems = existingItems;
		setRootFragment(createRootFragment());
	}

	private WizardFragment createRootFragment() {
		WizardFragment fragment = new WizardFragment() {
			WizardFragment[] children;

			@SuppressWarnings("unchecked")
			protected void createChildFragments(List list) {
				if (children != null) {
					loadChildren(children, list);
					return;
				}
				ICompositeFragmentFactory[] factories = WizardFragmentsFactoryRegistry
						.getFragmentsFactories(FRAGMENT_GROUP_ID);
				children = new WizardFragment[factories.length];
				for (int i = 0; i < factories.length; i++) {
					children[i] = factories[i].createWizardFragment();
					if (children[i] instanceof IPHPExeCompositeFragment) {
						((IPHPExeCompositeFragment) children[i])
								.setExistingItems(existingItems);
					}
				}
				loadChildren(children, list);
			}
		};
		return fragment;
	}

	@SuppressWarnings("unchecked")
	private void loadChildren(WizardFragment[] children, List list) {
		for (WizardFragment element : children) {
			list.add(element);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// Do nothing
	}
}