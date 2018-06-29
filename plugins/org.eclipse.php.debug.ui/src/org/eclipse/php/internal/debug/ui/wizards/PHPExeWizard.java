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
package org.eclipse.php.internal.debug.ui.wizards;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

	public PHPExeWizard(PHPexeItem[] existingItems, String title, WizardModel taskModel) {
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

			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			protected void createChildFragments(List list) {
				if (children != null) {
					loadChildren(children, list);
					return;
				}
				Map<String, ICompositeFragmentFactory> factories = WizardFragmentsFactoryRegistry
						.getFragmentsFactories(FRAGMENT_GROUP_ID);
				children = new WizardFragment[factories.size()];
				Set<String> factoryIds = factories.keySet();
				int index = 0;
				for (String factoryId : factoryIds) {
					children[index] = factories.get(factoryId).createWizardFragment();
					if (children[index] instanceof IPHPExeCompositeFragment) {
						((IPHPExeCompositeFragment) children[index]).setExistingItems(existingItems);
					}
					index++;
				}
				loadChildren(children, list);
			}
		};
		return fragment;
	}

	private void loadChildren(WizardFragment[] children, List<WizardFragment> list) {
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
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// Do nothing
	}
}