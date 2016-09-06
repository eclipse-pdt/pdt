/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.ComposerPreferenceConstants;
import org.eclipse.php.internal.ui.preferences.IStatusChangeListener;
import org.eclipse.php.internal.ui.preferences.OptionsConfigurationBlock;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class SaveActionsConfigurationBlock extends OptionsConfigurationBlock {

	private static final Key buildpathKey = new Key(ComposerPlugin.ID,
			ComposerPreferenceConstants.SAVEACTION_BUILDPATH);
	private static final Key updateKey = new Key(ComposerPlugin.ID, ComposerPreferenceConstants.SAVEACTION_UPDATE);

	public SaveActionsConfigurationBlock(IStatusChangeListener context, IProject project,
			IWorkbenchPreferenceContainer container) {

		super(context, project, getKeys(), container);
	}

	private static Key[] getKeys() {
		return new Key[] { buildpathKey, updateKey };
	}

	@Override
	protected Control createContents(Composite parent) {

		addCheckBox(parent, "Update Buildpath", buildpathKey, new String[] { "True", "False" }, 0);
		addCheckBox(parent, "Run Composer Update", updateKey, new String[] { "True", "False" }, 0);

		return parent;
	}

	@Override
	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
		return null;
	}

	@Override
	protected void validateSettings(Key changedKey, String oldValue, String newValue) {
	}

}
