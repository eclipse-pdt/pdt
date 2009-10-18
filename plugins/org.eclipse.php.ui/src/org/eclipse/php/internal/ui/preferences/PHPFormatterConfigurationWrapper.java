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
import org.eclipse.php.ui.preferences.IPHPFormatterConfigurationBlockWrapper;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * Description:
 * 
 * @author moshe, 2007
 */
public class PHPFormatterConfigurationWrapper implements
		IPHPFormatterConfigurationBlockWrapper {
	private PHPFormatterConfigurationBlock pConfigurationBlock;

	public void init(IStatusChangeListener context, IProject project,
			IWorkbenchPreferenceContainer container) {
		pConfigurationBlock = new PHPFormatterConfigurationBlock(context,
				project, container);
	}

	public Control createContents(Composite composite) {
		return pConfigurationBlock.createContents(composite);
	}

	public void dispose() {
		pConfigurationBlock.dispose();
	}

	public boolean hasProjectSpecificOptions(IProject project) {
		return pConfigurationBlock.hasProjectSpecificOptions(project);
	}

	public void performApply() {
		pConfigurationBlock.performApply();
	}

	public void performDefaults() {
		pConfigurationBlock.performDefaults();
	}

	public boolean performOk() {
		return pConfigurationBlock.performOk();
	}

	public void useProjectSpecificSettings(boolean useProjectSpecificSettings) {
		pConfigurationBlock
				.useProjectSpecificSettings(useProjectSpecificSettings);
	}

	public String getDescription() {
		return null;
	}
}
