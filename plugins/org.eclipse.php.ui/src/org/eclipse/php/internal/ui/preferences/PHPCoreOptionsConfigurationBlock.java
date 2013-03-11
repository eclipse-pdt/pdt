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
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.facet.PHPFacets;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants.Keys;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public abstract class PHPCoreOptionsConfigurationBlock extends
		OptionsConfigurationBlock {

	public PHPCoreOptionsConfigurationBlock(IStatusChangeListener context,
			IProject project, Key[] allKeys,
			IWorkbenchPreferenceContainer container) {
		super(context, project, allKeys, container);
	}

	protected abstract Control createContents(Composite parent);

	protected abstract void validateSettings(Key changedKey, String oldValue,
			String newValue);

	protected abstract String[] getFullBuildDialogStrings(
			boolean workspaceSettings);

	protected final static Key getPHPCoreKey(String key) {
		return getKey(PHPCorePlugin.ID, key);
	}

	protected boolean checkChanges(IScopeContext currContext) {
		if (fProject != null) {
			final Key versionKey = getPHPCoreKey(Keys.PHP_VERSION);
			// synch the php facets version if needed
			String newVersion = versionKey
					.getStoredValue(currContext, fManager);
			if (newVersion == null) {
				newVersion = getValue(versionKey);
			}
			final IStatus status = PHPFacets.setFacetedVersion(fProject,
					PHPVersion.byAlias(newVersion));
			if (!status.isOK()) {
				MessageDialog dialog = new MessageDialog(
						getShell(),
						PreferencesMessages.PHPCoreOptionsConfigurationBlock_SettingVersionFailed_Title,
						null, status.getMessage(), MessageDialog.ERROR,
						new String[] { IDialogConstants.CANCEL_LABEL }, 0);
				dialog.open();
			}
			return status.isOK();
		}

		return super.checkChanges(currContext);
	}
}
