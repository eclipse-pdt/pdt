/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.ui.wizards.CompositeWizardFragment;
import org.eclipse.php.internal.ui.wizards.IWizardHandle;
import org.eclipse.php.internal.ui.wizards.WizardControlWrapper;
import org.eclipse.php.internal.ui.wizards.WizardModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Wizard fragment for debugger settings.
 * 
 * @author Bartlomiej Laczkowski
 */
public class DebuggerWizardFragment extends CompositeWizardFragment {

	private DebuggerCompositeFragment compositeFragment;
	private Server server;
	private PHPexeItem phpExeItem;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.wizards.CompositeWizardFragment#getComposite
	 * ()
	 */
	@Override
	public Composite getComposite() {
		return compositeFragment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.wizards.WizardFragment#createComposite(org
	 * .eclipse.swt.widgets.Composite,
	 * org.eclipse.php.internal.ui.wizards.IWizardHandle)
	 */
	@Override
	public Composite createComposite(Composite parent, IWizardHandle handle) {
		compositeFragment = new DebuggerCompositeFragment(parent, new WizardControlWrapper(handle), false);
		return compositeFragment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.wizards.WizardFragment#enter()
	 */
	@Override
	public void enter() {
		if (compositeFragment != null) {
			try {
				server = (Server) getWizardModel().getObject(WizardModel.SERVER);
				if (server != null) {
					compositeFragment.setData(server);
					return;
				}
				phpExeItem = (PHPexeItem) getWizardModel().getObject(PHPExeWizard.MODEL);
				if (phpExeItem == null) {
					phpExeItem = new PHPexeItem();
				}
				compositeFragment.setData(phpExeItem);
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
	 * @see org.eclipse.php.internal.ui.wizards.WizardFragment#isComplete()
	 */
	@Override
	public boolean isComplete() {
		if (compositeFragment == null) {
			return super.isComplete();
		}
		return super.isComplete() && compositeFragment.isComplete();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.wizards.WizardFragment#performFinish(org.
	 * eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public boolean performFinish(IProgressMonitor monitor) throws CoreException {
		try {
			if (compositeFragment != null) {
				return compositeFragment.performOk();
			}
		} catch (Exception e) {
		}
		return false;
	}

}
