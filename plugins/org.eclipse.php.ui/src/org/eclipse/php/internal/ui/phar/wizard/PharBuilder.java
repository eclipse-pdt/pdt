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
package org.eclipse.php.internal.ui.phar.wizard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.internal.ui.IDLTKStatusConstants;
import org.eclipse.php.internal.core.phar.PharPackage;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.widgets.Shell;

public abstract class PharBuilder implements IPharBuilder {

	private MultiStatus fStatus;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void open(PharPackage jarPackage, Shell shell, MultiStatus status) throws CoreException {
		fStatus = status;
	}

	// some methods for convenience
	protected final void addInfo(String message, Throwable error) {
		fStatus.add(new Status(IStatus.INFO, PHPUiPlugin.getPluginId(), IDLTKStatusConstants.INTERNAL_ERROR, message,
				error));
	}

	protected final void addWarning(String message, Throwable error) {
		fStatus.add(new Status(IStatus.WARNING, PHPUiPlugin.getPluginId(), IDLTKStatusConstants.INTERNAL_ERROR, message,
				error));
	}

	protected final void addError(String message, Throwable error) {
		fStatus.add(new Status(IStatus.ERROR, PHPUiPlugin.getPluginId(), IDLTKStatusConstants.INTERNAL_ERROR, message,
				error));
	}

	protected final void addToStatus(CoreException ex) {
		IStatus status = ex.getStatus();
		String message = ex.getLocalizedMessage();
		if (message == null || message.length() < 1) {
			message = ""; //$NON-NLS-1$
			status = new Status(status.getSeverity(), status.getPlugin(), status.getCode(), message, ex);
		}
		fStatus.add(status);
	}
}
