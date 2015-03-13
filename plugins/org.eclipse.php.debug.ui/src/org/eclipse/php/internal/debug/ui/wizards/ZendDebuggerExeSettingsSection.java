/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.wizards;

import static org.eclipse.php.internal.debug.core.zend.debugger.ZendDebuggerSettingsConstants.*;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsWorkingCopy;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Zend debugger settings section for PHP executable.
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class ZendDebuggerExeSettingsSection {

	protected IDebuggerSettingsWorkingCopy settingsWorkingCopy;
	protected CompositeFragment compositeFragment;

	protected Composite create(CompositeFragment compositeFragment,
			final IDebuggerSettingsWorkingCopy settingsWorkingCopy) {
		this.settingsWorkingCopy = settingsWorkingCopy;
		this.compositeFragment = compositeFragment;
		// Main composite
		Composite settingsComposite = new Composite(compositeFragment, SWT.NONE);
		GridLayout sLayout = new GridLayout();
		sLayout.marginHeight = 0;
		sLayout.marginWidth = 0;
		settingsComposite.setLayout(sLayout);
		GridData sGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		sGridData.horizontalSpan = 2;
		settingsComposite.setLayoutData(sGridData);
		// Connection group
		Group connectionGroup = new Group(settingsComposite, SWT.NONE);
		connectionGroup.setFont(compositeFragment.getFont());
		GridLayout cgLayout = new GridLayout(2, false);
		cgLayout.marginTop = 5;
		connectionGroup.setLayout(cgLayout);
		GridData cgGridData = new GridData(GridData.FILL_HORIZONTAL);
		connectionGroup.setLayoutData(cgGridData);
		connectionGroup
				.setText(Messages.ZendDebuggerExeSettingsSection_Connection_settings);
		// Client port
		Label clientPortLabel = new Label(connectionGroup, SWT.None);
		clientPortLabel
				.setText(Messages.ZendDebuggerExeSettingsSection_Client_port);
		final Text clientPortText = new Text(connectionGroup, SWT.BORDER);
		GridData cptLayoutData = new GridData(GridData.FILL_HORIZONTAL);
		clientPortText.setLayoutData(cptLayoutData);
		clientPortText.setText(settingsWorkingCopy
				.getAttribute(PROP_CLIENT_PORT));
		clientPortText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String port = clientPortText.getText();
				settingsWorkingCopy.setAttribute(PROP_CLIENT_PORT, port);
				validate();
			}
		});
		// Initial validate
		validate();
		return settingsComposite;
	}

	/**
	 * Validates section.
	 */
	protected void validate() {
		String clientPort = (String) settingsWorkingCopy
				.getAttribute(PROP_CLIENT_PORT);
		if (clientPort == null || clientPort.isEmpty()) {
			compositeFragment
					.setMessage(
							Messages.ZendDebuggerExeSettingsSection_Client_port_is_missing,
							IMessageProvider.ERROR);
			return;
		}
		compositeFragment.setMessage(compositeFragment.getDescription(),
				IMessageProvider.NONE);
	}

}
