/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.preferences;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.ui.preferences.launcher.LauncherConfigurationBlock;
import org.eclipse.php.composer.ui.preferences.launcher.LauncherKeyBag;
import org.eclipse.php.internal.ui.preferences.IStatusChangeListener;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class ComposerConfigurationBlock extends LauncherConfigurationBlock {

	public ComposerConfigurationBlock(IStatusChangeListener context, IProject project,
			IWorkbenchPreferenceContainer container, LauncherKeyBag keyBag) {
		super(context, project, container, keyBag);

	}

	protected String getPluginId() {
		return ComposerPlugin.ID;
	}

	@Override
	protected void afterSave() {
		// ScriptLauncher.resetEnvironment();
	}

	@Override
	protected void beforeSave() {

	}

	@Override
	protected String getHeaderLabel() {
		return Messages.ComposerConfigurationBlock_HeaderLabel;
	}

	@Override
	protected String getProjectChoiceLabel() {
		return Messages.ComposerConfigurationBlock_ProjectChoiceLabel;
	}

	@Override
	protected String getGlobalChoiceLabel() {
		return Messages.ComposerConfigurationBlock_GlobalChoiceLabel;
	}

	@Override
	protected String getScriptLabel() {
		return Messages.ComposerConfigurationBlock_BinaryLabel;
	}

	@Override
	protected String getButtonGroupLabel() {
		return Messages.ComposerConfigurationBlock_SelectionLabel;
	}

	@Override
	protected String getScriptFieldLabel() {
		return Messages.ComposerConfigurationBlock_BinaryLocationLabel;
	}

	@Override
	protected boolean validateScript(String text) {
		File file = new File(text);
		return file.exists() /* && file.canExecute() */;
	}
}
