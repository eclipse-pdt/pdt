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
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * 
 * @author guy.g
 * 
 */
public abstract class AbstractPHPContentAssistPreferencePageBlock extends AbstractPHPPreferenceBlock {

	private IPreferenceStore preferenceStore;

	@Override
	public void initializeValues(PreferencePage preferencePage) {
		this.preferencePage = preferencePage;
		initializeValues();
	}

	@Override
	protected IPreferenceStore getPreferenceStore() {
		if (preferenceStore == null) {
			preferenceStore = preferencePage.getPreferenceStore();
		}
		return preferenceStore;
	}

	@Override
	public boolean performCancel() {
		return true;
	}

	/**
	 * Creates sub-section group with title
	 */
	@Override
	protected Composite createSubsection(Composite parent, String label) {
		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText(label);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		group.setLayoutData(data);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		group.setLayout(layout);
		return group;
	}

	@Override
	protected PreferencePage getPreferencePage() {
		return preferencePage;
	}

}
