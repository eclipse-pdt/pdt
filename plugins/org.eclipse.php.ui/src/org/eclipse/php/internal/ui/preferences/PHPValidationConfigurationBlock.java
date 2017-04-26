/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.php.internal.ui.util.ScrolledPageContent;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class PHPValidationConfigurationBlock extends PHPCoreOptionsConfigurationBlock {

	private static final Key PREF_BIND_NAMESPACE = new Key(PHPCorePlugin.ID,
			PHPCoreConstants.VALIDATION_BIND_NAMESPACE);
	private static final Key PREF_BIND_TYPENAME = new Key(PHPCorePlugin.ID, PHPCoreConstants.VALIDATION_BIND_TYPENAME);

	private IStatus fSaveActionsStatus;

	private static Key[] getKeys() {
		return new Key[] { PREF_BIND_NAMESPACE, PREF_BIND_TYPENAME };
	}

	public PHPValidationConfigurationBlock(IStatusChangeListener context, IProject project,
			IWorkbenchPreferenceContainer container) {
		super(context, project, getKeys(), container);
		fSaveActionsStatus = new StatusInfo();
	}

	@Override
	protected Control createContents(Composite parent) {
		ScrolledPageContent scrolled = new ScrolledPageContent(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolled.setExpandHorizontal(true);
		scrolled.setExpandVertical(true);

		Composite control = new Composite(scrolled, SWT.NONE);
		GridLayout layout = new GridLayout();
		control.setLayout(layout);

		Composite optionsComposite = createSubsection(control, PHPUIMessages.validationPage_validator_options_title);
		addOptionsSection(optionsComposite);

		scrolled.setContent(control);
		final Point size = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolled.setMinSize(size.x, size.y);
		return scrolled;
	}

	private void addOptionsSection(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);

		String label;

		label = PHPUIMessages.validationPage_validator_options_bindNamespace;
		addCheckBox(composite, label, PREF_BIND_NAMESPACE, new String[] { "True", "False" }, 0); //$NON-NLS-1$ //$NON-NLS-2$

		label = PHPUIMessages.validationPage_validator_options_bindTypeName;
		addCheckBox(composite, label, PREF_BIND_TYPENAME, new String[] { "True", "False" }, 0); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private Composite createSubsection(Composite parent, String label) {
		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText(label);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		group.setLayoutData(data);
		return group;
	}

	@Override
	protected void validateSettings(Key changedKey, String oldValue, String newValue) {
		if (!areSettingsEnabled()) {
			return;
		}

		if (changedKey != null) {
		} else {
			fSaveActionsStatus = new StatusInfo();
		}

		fContext.statusChanged(fSaveActionsStatus);
	}

	@Override
	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
		String title = PHPUIMessages.validationPage_needsbuild_title;
		String message;
		if (workspaceSettings) {
			message = PHPUIMessages.validationPage_needsfullbuild_message;
		} else {
			message = PHPUIMessages.validationPage_needsprojectbuild_message;
		}
		return new String[] { title, message };
	}

}
