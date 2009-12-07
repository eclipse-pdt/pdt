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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.ui.wizards.CompositeWizardFragment;
import org.eclipse.php.internal.ui.wizards.IWizardHandle;
import org.eclipse.php.internal.ui.wizards.WizardControlWrapper;
import org.eclipse.php.internal.ui.wizards.WizardModel;
import org.eclipse.swt.widgets.Composite;

public class ServerWizardFragment extends CompositeWizardFragment {

	private ServerCompositeFragment comp;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.server.ui.task.WizardFragment#createComposite()
	 */
	public Composite createComposite(Composite parent, IWizardHandle wizard) {
		comp = new ServerCompositeFragment(parent, new WizardControlWrapper(
				wizard), false);
		return comp;
	}

	public Composite getComposite() {
		return comp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.server.ui.wizard.WizardFragment#enter()
	 */
	public void enter() {
		if (comp != null) {
			try {

				Server server = (Server) getWizardModel().getObject(
						WizardModel.SERVER);
				if (server == null) {
					server = new Server();
					comp.setData(server);
				}
			} catch (Exception e) {
				Logger.logException(e);
			}
		} else {
			Logger
					.log(Logger.ERROR,
							"Could not display the Servers wizard (component is null)."); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * 
	 * @see org.eclipse.wst.server.ui.wizard.WizardFragment#exit()
	 */
	public void exit() {
		if (comp != null) {
			comp.performApply();
			WizardModel model = getWizardModel();
			model.putObject(WizardModel.SERVER, comp.getServer());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.server.ui.wizard.WizardFragment#performFinish
	 * (org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void performFinish(IProgressMonitor monitor) throws CoreException {
		super.performFinish(monitor);
		if (comp != null) {
			comp.performOk();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.server.ui.wizard.WizardFragment#performCancel
	 * (org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void performCancel(IProgressMonitor monitor) throws CoreException {
		super.performCancel(monitor);
		// Clear any added server
		if (getWizardModel().getObject(WizardModel.SERVER) != null) {
			getWizardModel().putObject(WizardModel.SERVER, null);
			ServersManager.save();
		}
	}
}