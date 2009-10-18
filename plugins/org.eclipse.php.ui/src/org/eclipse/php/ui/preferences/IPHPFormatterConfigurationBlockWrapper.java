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
package org.eclipse.php.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.ui.preferences.IStatusChangeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * Description:
 * 
 * @author moshe, 2007
 */
public interface IPHPFormatterConfigurationBlockWrapper {

	Control createContents(Composite composite);

	boolean hasProjectSpecificOptions(IProject project);

	void useProjectSpecificSettings(boolean useProjectSpecificSettings);

	void performDefaults();

	boolean performOk();

	void performApply();

	void dispose();

	void init(IStatusChangeListener statusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container);

	/**
	 * The Description for this configuration block that will be presented in
	 * the dialog
	 */
	String getDescription();
}
