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
package org.eclipse.php.internal.server.ui;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.server.PHPServerUIMessages;
import org.eclipse.php.internal.ui.wizards.FragmentedWizard;
import org.eclipse.php.internal.ui.wizards.WizardFragment;
import org.eclipse.php.internal.ui.wizards.WizardFragmentsFactoryRegistry;
import org.eclipse.php.internal.ui.wizards.WizardModel;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * A Server wizard.
 * 
 * @author shalom
 */

public class ServerWizard extends FragmentedWizard implements INewWizard {

	protected static final String FRAGMENT_GROUP_ID = "org.eclipse.php.server.ui.serverWizardAndComposite"; //$NON-NLS-1$

	public ServerWizard() {
		this(PHPServerUIMessages.getString("ServerWizard.serverCreation")); //$NON-NLS-1$
	}

	public ServerWizard(String title, WizardModel taskModel) {
		super(title, null, taskModel);
		setRootFragment(createRootFragment());
	}

	public ServerWizard(String title) {
		super(title, null);
		setRootFragment(createRootFragment());
	}

	private WizardFragment createRootFragment() {
		WizardFragment fragment = new WizardFragment() {
			private WizardFragment[] children;

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
				}
				loadChildren(children, list);
			}
		};
		return fragment;
	}

	private void loadChildren(WizardFragment[] children, List list) {
		for (int i = 0; i < children.length; i++) {
			list.add(children[i]);
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
