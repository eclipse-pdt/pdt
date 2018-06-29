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
package org.eclipse.php.internal.server.ui.wizard;

import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.ui.Logger;
import org.eclipse.php.internal.server.ui.PathMapperCompositeFragment;
import org.eclipse.php.internal.ui.wizards.CompositeWizardFragment;
import org.eclipse.php.internal.ui.wizards.IWizardHandle;
import org.eclipse.php.internal.ui.wizards.WizardControlWrapper;
import org.eclipse.php.internal.ui.wizards.WizardModel;
import org.eclipse.swt.widgets.Composite;

/**
 * @author michael
 */
public class PathMapperWizardFragment extends CompositeWizardFragment {

	private PathMapperCompositeFragment compositeFragment;
	private Server server;

	@Override
	public Composite getComposite() {
		return compositeFragment;
	}

	@Override
	public Composite createComposite(Composite parent, IWizardHandle handle) {
		compositeFragment = new PathMapperCompositeFragment(parent, new WizardControlWrapper(handle), false);
		return compositeFragment;
	}

	@Override
	public void enter() {
		if (compositeFragment != null) {
			try {
				server = (Server) getWizardModel().getObject(WizardModel.SERVER);
				if (server != null) {
					compositeFragment.setData(server);
				}
			} catch (Exception e) {
				Logger.logException(e);
			}
		} else {
			Logger.log(Logger.ERROR, "Could not display the Servers wizard (component is null)."); //$NON-NLS-1$
		}
	}

	@Override
	public boolean isComplete() {
		if (compositeFragment == null) {
			return super.isComplete();
		}
		return super.isComplete() && compositeFragment.isComplete();
	}

	@Override
	public void exit() {
		try {
			if (compositeFragment != null) {
				compositeFragment.performOk();
			}
		} catch (Exception e) {
		}
	}
}
