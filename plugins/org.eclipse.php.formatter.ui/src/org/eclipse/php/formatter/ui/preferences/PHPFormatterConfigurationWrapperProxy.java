/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.ui.preferences.IStatusChangeListener;
import org.eclipse.php.ui.preferences.IPHPFormatterConfigurationBlockWrapper;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class PHPFormatterConfigurationWrapperProxy implements IPHPFormatterConfigurationBlockWrapper {
	private IPHPFormatterConfigurationBlockWrapper pConfigurationBlock;

	@Override
	public Control createContents(Composite composite) {
		return pConfigurationBlock.createContents(composite);
	}

	@Override
	public void dispose() {
		pConfigurationBlock.dispose();
	}

	@Override
	public String getDescription() {
		return pConfigurationBlock.getDescription();
	}

	@Override
	public boolean hasProjectSpecificOptions(IProject project) {
		return pConfigurationBlock.hasProjectSpecificOptions(project);
	}

	@Override
	public void init(IStatusChangeListener statusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		pConfigurationBlock = new PHPFormatterConfigurationWrapper();
		pConfigurationBlock.init(statusChangedListener, project, container);
	}

	@Override
	public void performApply() {
		pConfigurationBlock.performApply();
	}

	@Override
	public void performDefaults() {
		pConfigurationBlock.performDefaults();
	}

	@Override
	public boolean performOk() {
		return pConfigurationBlock.performOk();
	}

	@Override
	public void useProjectSpecificSettings(boolean useProjectSpecificSettings) {
		pConfigurationBlock.useProjectSpecificSettings(useProjectSpecificSettings);
	}

}
