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
package org.eclipse.php.composer.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.php.composer.ui.ComposerUIPluginConstants;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.eclipse.php.composer.api.objects.Scripts;

public class ScriptDialog extends Dialog {

	private Combo eventControl;
	private Text handlerControl;

	private String event;
	private String handler;

	private boolean handlerEnabled = true;
	private boolean eventEnabled = true;

	/**
	 * @wbp.parser.constructor
	 */
	public ScriptDialog(Shell parentShell) {
		super(parentShell);
	}

	public ScriptDialog(IShellProvider parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("Edit Script");
		getShell().setImage(ComposerUIPluginImages.EVENT.createImage());

		Composite contents = new Composite(parent, SWT.NONE);
		contents.setLayout(new GridLayout(2, false));

		Label lblEvent = new Label(contents, SWT.NONE);
		GridData gd_lblEvent = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblEvent.widthHint = ComposerUIPluginConstants.DIALOG_LABEL_WIDTH;
		lblEvent.setLayoutData(gd_lblEvent);
		lblEvent.setText("Event");

		eventControl = new Combo(contents, SWT.READ_ONLY);
		eventControl.setEnabled(eventEnabled);
		GridData gd_eventControl = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_eventControl.widthHint = ComposerUIPluginConstants.DIALOG_CONTROL_WIDTH;
		eventControl.setLayoutData(gd_eventControl);
		eventControl.setItems(Scripts.getEvents());
		eventControl.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				event = eventControl.getText();
			}
		});

		if (event != null) {
			eventControl.setText(event);
		}

		Label lblHandler = new Label(contents, SWT.NONE);
		lblHandler.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblHandler.setText("Handler");

		handlerControl = new Text(contents, SWT.BORDER);
		handlerControl.setEnabled(handlerEnabled);
		handlerControl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		handlerControl.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handler = handlerControl.getText();
			}
		});

		if (handler != null) {
			handlerControl.setText(handler);
		}

		return contents;
	}

	public void setHandlerEnabled(boolean enabled) {
		handlerEnabled = enabled;
		if (handlerControl != null) {
			handlerControl.setEnabled(handlerEnabled);
		}
	}

	public void setEventEnabled(boolean enabled) {
		eventEnabled = enabled;
		if (eventControl != null) {
			eventControl.setEnabled(eventEnabled);
		}
	}

	public void setEvent(String event) {
		this.event = event;
		if (eventControl != null) {
			eventControl.setText(event);
		}
	}

	public void setHandler(String handler) {
		this.handler = handler;
		if (handlerControl != null) {
			handlerControl.setText(handler);
		}
	}

	public String getEvent() {
		return event;
	}

	public String getHandler() {
		return handler;
	}
}
