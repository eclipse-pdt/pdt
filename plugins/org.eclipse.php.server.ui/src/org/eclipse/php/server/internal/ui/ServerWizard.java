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
package org.eclipse.php.server.internal.ui;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.server.ui.ICompositeFragmentFactory;
import org.eclipse.php.server.ui.ServerFragmentsFactoryRegistry;
import org.eclipse.php.server.ui.wizard.FragmentedWizard;
import org.eclipse.php.server.ui.wizard.WizardFragment;
import org.eclipse.php.server.ui.wizard.WizardModel;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * A Server wizard.
 * 
 * @author shalom
 */

public class ServerWizard extends FragmentedWizard implements INewWizard {

	public ServerWizard() {
		this("PHP Server Creation");
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
				ICompositeFragmentFactory[] factories = ServerFragmentsFactoryRegistry.getFragmentsFactories("");
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
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// Do nothing
	}
}
