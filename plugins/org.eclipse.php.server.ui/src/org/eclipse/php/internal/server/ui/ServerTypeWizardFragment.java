/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.ui.wizards.CompositeWizardFragment;
import org.eclipse.php.internal.ui.wizards.IWizardHandle;
import org.eclipse.php.internal.ui.wizards.WizardControlWrapper;
import org.eclipse.php.internal.ui.wizards.WizardModel;
import org.eclipse.php.server.core.types.IServerType;
import org.eclipse.swt.widgets.Composite;

/**
 * Wizard fragment for server type selection page.
 */
public class ServerTypeWizardFragment extends CompositeWizardFragment {

	private ServerTypeCompositeFragment comp;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.server.ui.task.WizardFragment#createComposite()
	 */
	@Override
	public Composite createComposite(Composite parent, IWizardHandle wizard) {
		comp = new ServerTypeCompositeFragment(parent, new WizardControlWrapper(wizard), false);
		return comp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.server.ui.wizard.WizardFragment#enter()
	 */
	@Override
	public void enter() {
		if (comp != null) {
			try {
				// Always set up new server when entering type choice
				Server server = new Server();
				comp.setData(server);
			} catch (Exception e) {
				Logger.logException(e);
			}
		} else {
			Logger.log(Logger.ERROR, "Could not display the Servers wizard (component is null)."); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.server.ui.wizard.WizardFragment#exit()
	 */
	@Override
	public void exit() {
		if (comp != null) {
			comp.performApply();
			getWizardModel().putObject(WizardModel.SERVER, comp.getData());
		}
	}

	@Override
	public Composite getComposite() {
		return comp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.server.ui.wizard.WizardFragment#isComplete()
	 */
	@Override
	public boolean isComplete() {
		if (comp == null) {
			return super.isComplete();
		}
		return super.isComplete() && comp.isComplete();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.ui.wizard.WizardFragment#performFinish
	 * (org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public boolean performFinish(IProgressMonitor monitor) throws CoreException {
		boolean result = super.performFinish(monitor);
		if (comp != null) {
			result = comp.performOk();
		}
		return result;
	}

	/**
	 * Returns server type for fragment.
	 * 
	 * @return server type
	 */
	public IServerType getType() {
		return comp != null ? comp.getType() : null;
	}

}