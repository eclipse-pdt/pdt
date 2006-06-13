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

import org.eclipse.php.server.ui.ICompositeFragmentFactory;
import org.eclipse.php.server.ui.ServerFragmentsFactoryRegistry;
import org.eclipse.php.server.ui.wizard.WizardFragment;

public class ServerWizardFragment extends WizardFragment {
	protected void createChildFragments(List list) {
		ICompositeFragmentFactory[] fragmentFactories = ServerFragmentsFactoryRegistry.getFragmentsFactories("org.eclipse.php.server.apache.13");
		for (int i = 0; i < fragmentFactories.length; i++) {
			list.add(fragmentFactories[i].createWizardFragment());
		}
	}
}