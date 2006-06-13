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

import org.eclipse.php.server.core.Server;
import org.eclipse.php.server.ui.Logger;
import org.eclipse.php.server.ui.ServerCompositeFragment;
import org.eclipse.php.server.ui.wizard.CompositeWizardFragment;
import org.eclipse.php.server.ui.wizard.IWizardHandle;
import org.eclipse.php.server.ui.wizard.WizardControlWrapper;
import org.eclipse.php.server.ui.wizard.WizardModel;
import org.eclipse.swt.widgets.Composite;

public class ServerWizardFragment extends CompositeWizardFragment {

	private ServerCompositeFragment comp;

	//	protected void createChildFragments(List list) {
	//		ICompositeFragmentFactory[] fragmentFactories = ServerFragmentsFactoryRegistry.getFragmentsFactories("org.eclipse.php.server.apache.13");
	//		for (int i = 0; i < fragmentFactories.length; i++) {
	//			list.add(fragmentFactories[i].createWizardFragment());
	//		}
	//	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.server.ui.task.WizardFragment#createComposite()
	 */
	public Composite createComposite(Composite parent, IWizardHandle wizard) {
		comp = new ServerCompositeFragment(parent, new WizardControlWrapper(wizard), false);
		return comp;
	}

	public Composite getComposite() {
		return comp;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.wst.server.ui.wizard.WizardFragment#enter()
	 */
	public void enter() {
		if (comp != null) {
			try {
				Server server = new Server();
				comp.setServer(server);
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			Logger.log(Logger.ERROR, "Could not display the Servers wizard (component is null).");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.wst.server.ui.wizard.WizardFragment#isComplete()
	 */
	public boolean isComplete() {
		if (comp == null) {
			return super.isComplete();
		}
		return super.isComplete() && comp.isComplete();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.wst.server.ui.wizard.WizardFragment#exit()
	 */
	public void exit() {
		try {
			if (comp != null) {
				if (comp.performOk()) {
					WizardModel model = getWizardModel();
					model.putObject(WizardModel.SERVER, comp.getServer());
				}
			}
		} catch (Exception e) {
		}
	}
}